/*******************************************************************************
 * Copyright (c) 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.rapid.socket.qos.sla;

import org.apache.commons.httpclient.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Enforcement {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(Enforcement.class);
	RestProxy rapid = new RestProxy();
	String targetUrl = null;
	String path = "/enforcements";
	
	public Enforcement(String url, String user, String password) {
		
	
		targetUrl = url + path;		
		rapid.setBasicAuthenticationUser(user);
		rapid.setBasicAuthenticationPassword(password);	
		
	}
	
	public String getEnforcement(String id, String AcceptedType) {
				
		String response = null;
		String Url = targetUrl+'/'+id;
		rapid.setMethod("GET");
		rapid.setUrl(Url);
		//System.out.println(Url);
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
			LOGGER.info("Enforcement.getEnforcement: " + response);	
			//System.out.println(response);
		}
		else {
			LOGGER.warn("Execution of GET method to: " + Url + " failed: " + rapid.getStatusResponse());
			//System.out.println(rapid.getStatusResponse());
		}
		
		return response;
		
	}	
	
	public String startEnforcement(String id, String AcceptedType) {
		
		String response = null;
		String Url = targetUrl+"/"+id+"/start";
		rapid.setMethod("PUT");
		rapid.setUrl(Url);
		//System.out.println(Url);
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
		
		if (rapid.getStatusResponse() == HttpStatus.SC_ACCEPTED){
			response = rapid.getContentResponse();
			LOGGER.info("Enforcement.startEnforcement: " + response);	
			//System.out.println(response);
		}
		else {
			LOGGER.warn("Execution of PUT method to: " + Url + " failed: " + rapid.getStatusResponse());
			//System.out.println(rapid.getStatusResponse());
		}
		
		return response;
		
	}
	public String stopEnforcement(String id, String AcceptedType) {
		
		String response = null;
		String Url = targetUrl+"/"+id+"/stop";
		rapid.setMethod("PUT");
		rapid.setUrl(Url);
		//System.out.println(Url);
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
		
		if (rapid.getStatusResponse() == HttpStatus.SC_ACCEPTED){
			response = rapid.getContentResponse();
			LOGGER.info("Enforcement.stopEnforcement: " + response);	
			//System.out.println(response);
		}
		else {
			LOGGER.warn("Execution of PUT method to: " + Url + " failed: " + rapid.getStatusResponse());
			//System.out.println(rapid.getStatusResponse());
		}
		
		return response;
		
	}
	
	public String deleteProvider(String id, String AcceptedType) {
		
		String response = null;
		String Url = targetUrl+'/'+id;
		rapid.setMethod("DELETE");  // No implementado el DELETE en RestProxy
		rapid.setUrl(Url);
		//System.out.println(Url);
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
			LOGGER.info("Provider.deleteProvider: " + response);	
			//System.out.println(response);
		}
		else {
			LOGGER.warn("Execution of DELETE method to: " + Url + " failed: " + rapid.getStatusResponse());
			//System.out.println(rapid.getStatusResponse());
		}
		
		return response;
		
	}
	
	

	
}
