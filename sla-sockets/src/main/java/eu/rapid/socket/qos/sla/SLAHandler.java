/*******************************************************************************
 * Copyright (c) 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.rapid.socket.qos.sla;

import eu.atos.sla.core.MainSLAM;
import eu.atos.sla.test.TestSlam;
import eu.rapid.socket.qos.beans.QoSItem;
import eu.rapid.socket.qos.beans.QoSItemList;

import org.apache.commons.httpclient.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Properties;

import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder; 

public class SLAHandler {

	protected static final Logger LOGGER = LoggerFactory.getLogger(SLAHandler.class);
	
	private static final String CONFIG_PROPERTIES = "config.properties";
	private static final String SERVER_SLA_PORT = "serversla.port";
    //private static final String VMM_SERVER_IP = "vmm.server.ip";
    //private static final String VMM_SERVER_PORT = "vmm.server.port";
	public static String filename=null;
	
	public static void receivedRegisterRequest(long userID, int osType, String vmmIP, int vmmPort, int vcpuNum,
			int memSize, int gpuCores, QoSItemList qosItemList, String vmIp) {
		// TODO Auto-generated method stub
		
	    // qosinjson = "{\"QoS\":[{\"variable\":\"CPU_UTIL\", \"condition\":\"LT\", \"value\":60}]}";
		Properties prop = new Properties();
        InputStream input = null;
        
        LOGGER.info("receivedRegisterRequest parameters:");
        LOGGER.info("userID= " + userID);
        LOGGER.info("osType= " + osType);
        LOGGER.info("vmmIP= " + vmmIP);
        LOGGER.info("vmmPort= " + vmmPort);
        LOGGER.info("vcpuNum= " + vcpuNum);
        LOGGER.info("memSize= " + memSize);
        LOGGER.info("gpuCores= " + gpuCores);
        LOGGER.info("qosItemList= " + qosItemList);
        LOGGER.info("vmIp= " + vmIp);
        
        String infoVm = ("userID=" +userID 
        				+",osType="+osType
        				+",vmmIP="+vmmIP
        				+",vmmPort="+vmmPort
        				+",vcpuNum="+vcpuNum
        				+",memSize="+memSize
        				+",gpuCores="+gpuCores
        				+",vmIp="+vmIp);
      
        //infoVm="ScopeNameContent";
        
        try {
        	if (filename==null) filename = CONFIG_PROPERTIES;
            input = MainSLAM.class.getClassLoader().getResourceAsStream(filename);
            if(input==null){
                LOGGER.error("Sorry, unable to find " + filename);
                return;
            }

            LOGGER.debug("Reading properties from config.properties");
            //load a properties file from class path, inside static method
            prop.load(input);

            //get the property value
            int serverSLAPort = Integer.parseInt(prop.getProperty(SERVER_SLA_PORT));
        
    		
    		// Configuracion Servidor SLAM
    		String SLAUrl = "http://127.0.0.1:"+serverSLAPort+"/sla-service";
    		String SLAuser = "user";
    		String SLApwd = "password";
    	        
    		
		
		//comprobar que existe PROVIDER RAPID, si no existe crearlo
		String uuid = "Rapid";
		String name = "Rapid Provider";
		Provider prov = new Provider(SLAUrl, SLAuser, SLApwd);
        String resprov = prov.getProvider(uuid, "XML");
		
        if ( resprov == null)
        {
        	LOGGER.info("No existe Provider" + uuid);
        	String newprov = prov.createProvider(uuid, name, "JSON", "JSON");
        	if (newprov != null) 
        		LOGGER.info("Provider: " + uuid + " Creado");
        }
        else
        	LOGGER.info("Existe Provider: " + uuid);
					
		//comprobar que existe un provider con el userID, si no existe crearlo
        String s_userID = (Long.valueOf(userID)).toString();
        LOGGER.info("Comprobar que existe Provider" + s_userID);
        String res_provcli = prov.getProvider(s_userID, "XML");	
        if ( res_provcli == null)
        {
        	LOGGER.info("No existe Provider: " + s_userID);
        	String newprovcli = prov.createProvider(s_userID, "provider"+s_userID, "JSON", "JSON");
        	if (newprovcli != null) 
        		LOGGER.info("Provider: " + s_userID + " Creado");
        }
        else
        	LOGGER.info("Existe Provider: " + userID);
        
           
        //montar el template provider RAPID, guarantee terms los que  están en qosItemList 
		Template temp = new Template(SLAUrl, SLAuser, SLApwd);
		
		// Crear un nuevo template. El ServiceProvider por defecto sera Rapid.
        String newtemp = temp.createTemplate(uuid, s_userID, qosItemList, "XML", "XML");
    	if (newtemp != null) {
    		
    		String sTemplateId = null;
    		LOGGER.info("Template Creado: userID: " + s_userID);
	    	
			org.jdom.input.SAXBuilder saxBuilder = new SAXBuilder();
			try {
				org.jdom.Document doc = saxBuilder.build(new StringReader(newtemp));
				sTemplateId = doc.getRootElement().getAttributeValue("elementId");
				LOGGER.info("Template Id: " + sTemplateId);
			} catch (JDOMException e) {
				e.printStackTrace(); // handle JDOMException
			} catch (IOException e) {
				e.printStackTrace();// handle IOException
			}
    		   		
    		
    		// Crear un nuevo Agreement. El ServiceProvider por defecto sera Rapid.
    		Agreement agre = new Agreement(SLAUrl, SLAuser, SLApwd);
    		String newagre = agre.createAgreement(sTemplateId,uuid, s_userID, qosItemList, "XML", "XML", infoVm);
	     	if (newagre != null) 
	     	{
	     		
	     		String sAgreementId = null;
	     		LOGGER.info("Agreement Creado: userID: " + s_userID);
	     		org.jdom.input.SAXBuilder saxBuilder_agre = new SAXBuilder();
				try {
					org.jdom.Document doc = saxBuilder_agre.build(new StringReader(newagre));
					sAgreementId = doc.getRootElement().getAttributeValue("elementId");
					LOGGER.info("AgreementId: " + sAgreementId);
				} catch (JDOMException e) {
					e.printStackTrace(); // handle JDOMException
				} catch (IOException e) {
					e.printStackTrace();// handle IOException
				}
				
		     	//Activar el Agreement.
				Enforcement enf = new Enforcement(SLAUrl, SLAuser, SLApwd);
				
				// Start Nuevo Agreement
		        String senf = enf.startEnforcement(sAgreementId, "XML");
		        
		    	if (senf != null) {
		    		LOGGER.info("Agreement Activado: userID: " + sAgreementId);
		    		
		    	}
		    	else
		    	{
		    		LOGGER.info("No se pudo Activar el Agreement userID: " + sAgreementId);
		    		
		    	}
		    	
		    	// Start Nueva VMM
	            //get the property value
		        //String vmmAddress = prop.getProperty(VMM_SERVER_IP);
		        //int vmmPort1 = Integer.parseInt(prop.getProperty(VMM_SERVER_PORT));
	     			     		
	     	}
	     	else
	    		LOGGER.error("No se pudo crear el Agreement: userID: " + s_userID);
            	
    	}
    	else
    		LOGGER.error("No se pudo crear el Template : userID: " + s_userID);
    	
   

    	
    	
        } catch (Exception e) {
            LOGGER.error("Exception", e);
        }finally{
            if(input!=null){
                try {
                    input.close();
                } catch (IOException e) {
                    LOGGER.error("Exception", e);
                }
            }
        }   	
    	
	}

        
        
}
