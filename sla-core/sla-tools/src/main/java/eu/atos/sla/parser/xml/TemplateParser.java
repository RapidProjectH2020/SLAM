/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.parser.xml;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.atos.sla.parser.IParser;
import eu.atos.sla.parser.ParserException;
import eu.atos.sla.parser.ValidationHandler;
import eu.atos.sla.parser.data.wsag.Template;
public class TemplateParser implements IParser<Template> {
	private static Logger logger = LoggerFactory.getLogger(TemplateParser.class);


	/*
	 * getWsagObject receives in serializedData the object information in xml 
	 * must returns a eu.atos.sla.parser.data.wsag.Template
	 */
	@Override
	public Template getWsagObject(String serializedData) throws ParserException{
		Template templateXML = null;
		try{
			logger.info("Will parse {}", serializedData);
			JAXBContext jaxbContext = JAXBContext.newInstance(Template.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			jaxbUnmarshaller.setEventHandler(new ValidationHandler());			
			templateXML = (Template)jaxbUnmarshaller.unmarshal(new StringReader(serializedData));
			logger.info("Template parsed {}", templateXML);
		}catch(JAXBException e){
			throw new ParserException(e);
		}
		return templateXML;

	}

	/*
	 * getWsagAsSerializedData receives in serializedData xml  
	 * must return information following and xml in wsag standard.
	 */
	@Override
	public String getWsagAsSerializedData(String serializedData) throws ParserException {
		return serializedData;
	}

	/*
	 * getSerializedData receives in wsagSerialized the information in wsag standard as it was generated with the
	 * getWsagAsSerializedData method and returns it in xml
	 */
	@Override
	public String getSerializedData(String wsagSerialized) throws ParserException{
		return wsagSerialized;
	}
	
	
}
