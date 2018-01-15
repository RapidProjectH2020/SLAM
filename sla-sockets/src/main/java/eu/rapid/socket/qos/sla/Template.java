/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.rapid.socket.qos.sla;

import java.util.List;


import org.apache.commons.httpclient.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.rapid.socket.qos.beans.QoSItem;
import eu.rapid.socket.qos.beans.QoSItemList;



public class Template {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(Template.class);
	RestProxy rapid = new RestProxy();
	String targetUrl = null;
	String path = "/templates";
	
	public Template(String url, String user, String password) {
		
		targetUrl = url + path;		
		rapid.setBasicAuthenticationUser(user);
		rapid.setBasicAuthenticationPassword(password);	
		
	}
	
	
	public String getTemplate(String id, String AcceptedType) {
				
		String response = null;
		String Url = targetUrl+'/'+id;
		rapid.setMethod("GET");
		rapid.setUrl(Url);

		LOGGER.info("URL build: " + Url);
		
		if (AcceptedType == "JSON")	
			rapid.setAcceptedTypes("application/json");
		else if (AcceptedType == "XML")
			rapid.setAcceptedTypes("application/xml");
		
		try {
			rapid.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (rapid.getStatusResponse() == HttpStatus.SC_OK){
			response = rapid.getContentResponse();
			LOGGER.info("Template.getTemplate: " + response);	
			//System.out.println(response);
		}
		else {
			LOGGER.warn("Execution of GET method to: " + Url + " failed: " + rapid.getStatusResponse());
			//System.out.println(rapid.getStatusResponse());
		}
		
		return response;
		
	}	
	
	public String createTemplate(String SP, String CustomerId, QoSItemList qosItemList, String AcceptedType, String ContentType) {
		
		String response = null;
		String Url = targetUrl;
		
		rapid.setMethod("POST");
		rapid.setUrl(Url);
		
		if (AcceptedType == "JSON")	
			rapid.setAcceptedTypes("application/json");
		else if (AcceptedType == "XML")
			rapid.setAcceptedTypes("application/xml");
		if (ContentType == "JSON")	
			rapid.setContentType("application/json");
		else if (ContentType == "XML")
			rapid.setContentType("application/xml");
		
		int countItem = ((List<QoSItem>)qosItemList.getQoSList()).size();
		
		/*
		for (QoSItem item:qosItemList.getQoSList()){
			item.getVariable();  //CPU_UTIL
			item.getCondition();  //LT
			item.getValue();  //60
		}
		*/
		
		// InfoTemplate
		String TemplateUUID = null;
		String TemplateName = "TemplateName";
		String AgreementInitiator = CustomerId;
		String ServiceProvider = SP;
		String ServiceId = "Service3";
		String cadena = null;
		
		// Cabecera
		cadena = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<wsag:Template xmlns:wsag=\"http://www.ggf.org/namespaces/ws-agreement\" xmlns:sla=\"http://sla.atos.eu\" ";
						
		     //System.out.printf("%nTemplateUUID :%s", TemplateUUID);
			//System.out.printf("%nTemplateName :%s", TemplateName);
	
			if ( TemplateUUID!=null )		
				cadena += "wsag:TemplateId=\"" + TemplateUUID + "\"";
			
			cadena += ">";
			
			cadena += "<wsag:Name>"+TemplateName+"</wsag:Name>";	
			cadena += "<wsag:Context>";
			cadena += "<wsag:AgreementInitiator>" + AgreementInitiator + "</wsag:AgreementInitiator>";
			cadena += "<wsag:ServiceProvider>" + ServiceProvider + "</wsag:ServiceProvider>";
			cadena += "<wsag:ServiceProvider>AgreementInitiator</wsag:ServiceProvider>";		
			cadena += "<wsag:ExpirationTime>2030-03-07T12:00:00+0100</wsag:ExpirationTime>";
			cadena += "<sla:Service>" + ServiceId + "</sla:Service>";	  
			cadena += "</wsag:Context>";
			cadena += "<wsag:Terms>";
			cadena += "<wsag:All>";
			
			//FUNCTIONAL DESCRIPTION
			
			for (int cont=1; cont <= countItem; cont++) {
				cadena += "<wsag:ServiceDescriptionTerm wsag:Name=\"SDTName"+cont+"\" wsag:ServiceName=\"ServiceName\">";
				cadena += "DSL expression";
				cadena += "</wsag:ServiceDescriptionTerm>";
			}
			
				
			//OPTIONAL SERVICE REFERENCE 
				
			//OPTIONAL SERVICE PROPERTIES : non funcional properties
			cadena += "<wsag:ServiceProperties wsag:Name=\"NonFunctional\" wsag:ServiceName=\"ServiceName\">";
			cadena += "<wsag:Variables>";
			for (QoSItem item:qosItemList.getQoSList()){
				cadena += "<wsag:Variable wsag:Name=\""+item.getTerm()+"\" wsag:Metric=\"xs:double\">";
				cadena += "<wsag:Location>qos:"+item.getTerm()+"</wsag:Location>";
				cadena += "</wsag:Variable>";
			}
			cadena += "</wsag:Variables>";
			cadena += "</wsag:ServiceProperties>";
			
			for (QoSItem item:qosItemList.getQoSList()){
				cadena += "<wsag:GuaranteeTerm wsag:Name=\"GT_"+item.getTerm()+"\">";
				cadena += "<wsag:ServiceScope>ServiceName</wsag:ServiceScope>";
				cadena += "<wsag:ServiceLevelObjective>";
					cadena += "<wsag:KPITarget>";
						cadena += "<wsag:KPIName>"+item.getTerm()+"</wsag:KPIName>";
						cadena += "<wsag:CustomServiceLevel>{\"constraint\" : \""+item.getTerm()+" "+item.getOperator()+" qos:"+item.getTerm()+"\"}</wsag:CustomServiceLevel>";
					cadena += "</wsag:KPITarget>";
				cadena += "</wsag:ServiceLevelObjective>";
				cadena += "</wsag:GuaranteeTerm>";
			}
			cadena += "</wsag:All>";
			cadena += "</wsag:Terms>";
			cadena += "</wsag:Template>";

		System.out.printf("%ncadena :%s", cadena);
		
		rapid.setContent(cadena);
		
		try {
			rapid.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (rapid.getStatusResponse() == HttpStatus.SC_CREATED){
			response = rapid.getContentResponse();
			LOGGER.info("Template.createTemplate: " + response);	

		}
		else {
			LOGGER.warn("Execution of POST method to: " + Url + " failed: " + rapid.getStatusResponse());
			//System.out.println(rapid.getStatusResponse());
		}
		
		return response;
		
				
	}	

	public String deleteTemplate(String id, String AcceptedType) {
		String response = null;
		return response;				
	}
	
	

	
}
