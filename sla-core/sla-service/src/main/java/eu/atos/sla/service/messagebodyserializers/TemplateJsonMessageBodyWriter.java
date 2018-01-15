/*******************************************************************************
 * Copyright � 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED �AS IS�, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.service.messagebodyserializers;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.annotation.Resource;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import eu.atos.sla.datamodel.ITemplate;
import eu.atos.sla.parser.IParser;
import eu.atos.sla.parser.NullParser;
import eu.atos.sla.parser.ParserException;
import eu.atos.sla.parser.data.wsag.Template;


/**
 * 
 * @author Elena Garrido
 */

@Component
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class TemplateJsonMessageBodyWriter implements MessageBodyWriter<ITemplate> {
	private static Logger logger = LoggerFactory.getLogger(TemplateJsonMessageBodyWriter.class);

	@Resource(name="templateJsonParser")
	IParser<Template> jsonParser;
	Throwable catchedException;
	
	private void initParser() {
		if (jsonParser instanceof NullParser) jsonParser=null;		
		//logger.debug("setting parser:"+jsonParser);		
	}
	
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		initParser();
		boolean isUsed = (genericType == ITemplate.class) && 
				(mediaType.toString().contains(MediaType.APPLICATION_JSON)) && 
				jsonParser!=null ;
		if (isUsed)
			logger.debug("isWritable:{} --> type:{} genericType:{} mediaType:{} with parser:{}",
				isUsed, type, genericType, mediaType, jsonParser);
		return isUsed;
	}
	
	byte[] serializedData = null;
	@Override
	public long getSize(ITemplate template , Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		if (template.getText()!=null){
			try {
				String jsonData = jsonParser.getSerializedData(template.getText());
				logger.info("jsonData "+jsonData);
				serializedData = jsonData.getBytes();
				return serializedData.length;
			} catch (ParserException e) {
				logger.error(e.getMessage());
				catchedException = e;
			} catch (Throwable e) {
				logger.error(e.getMessage());
				catchedException = e;
			}
		}else {
			logger.error("Error marshalling data agreement text is null");
			throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
		}
		return 0;
	}

	@Override
	public void writeTo(ITemplate template, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> multivaluedMap, OutputStream entityStream)
			throws IOException, WebApplicationException {
		if (catchedException!=null) {
			throw new WebApplicationException(catchedException, Response.Status.INTERNAL_SERVER_ERROR);
		}else
			entityStream.write(serializedData);
		
	}
}
