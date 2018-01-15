/*******************************************************************************
 * Copyright � 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED �AS IS�, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.service.messagebodyserializers;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import eu.atos.sla.parser.IParser;
import eu.atos.sla.parser.NullParser;
import eu.atos.sla.parser.ParserException;
import eu.atos.sla.parser.data.wsag.Template;
import eu.atos.sla.service.types.TemplateParam;


/**
 * 
 * @author Elena Garrido
 */

@Component
@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class TemplateParamJsonMessageBodyReader implements MessageBodyReader<TemplateParam> {
	private static Logger logger = LoggerFactory.getLogger(TemplateParamJsonMessageBodyReader.class);
	@Resource(name="templateJsonParser")
	IParser<Template> jsonParser;
	Throwable catchedException;

	
	private void initParser() {
		if (jsonParser instanceof NullParser) jsonParser=null;		
	}	
	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		initParser();
		boolean isUsed = (type == TemplateParam.class) && 
				mediaType.toString().contains(MediaType.APPLICATION_JSON) && 
				jsonParser!=null;
		if (isUsed)
			logger.debug("isReadable: {} -->type:{} genericType:{} mediaType:{} with parser:{}",
					isUsed, type, genericType, mediaType, jsonParser);
		return isUsed;
	}


	@Override
	public TemplateParam readFrom(Class<TemplateParam> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
			throws IOException, WebApplicationException {
			String str = MessageBodyUtils.getStringFromInputStream(entityStream);
			try {
				Template templateWSAG = jsonParser.getWsagObject(str);
				String serializedWsagData = jsonParser.getWsagAsSerializedData(str);
				TemplateParam agreementParam = new TemplateParam();
				agreementParam.setTemplate(templateWSAG);
				agreementParam.setOriginalSerialzedTemplate(removeXMLHeader(serializedWsagData));
				return agreementParam;
			} catch (ParserException e) {
				logger.error("Error parsing"+e.getMessage());
				throw new WebApplicationException(e,Response.Status.NOT_ACCEPTABLE);
			}
	}


	private String removeXMLHeader(String originalXML){
    	return originalXML.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();		
	}

}
