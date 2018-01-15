/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Properties;

import org.apache.log4j.Logger;

import eu.atos.sla.core.MainSLAM;
import eu.project.rapid.common.RapidMessages;

public class TestSlam {
	
    private final static Logger logger = Logger.getLogger(TestSlam.class);
    
    private static final String CONFIG_PROPERTIES = "config.properties";
    private static final String SERVER_PORT = "server.port";
    private static final String SERVER_IP = "server.ip";


	public static void main(String[] args) {

        Properties prop = new Properties();
        InputStream input = null;

        try {
            String filename = CONFIG_PROPERTIES;
            input = MainSLAM.class.getClassLoader().getResourceAsStream(filename);
            if(input==null){
                logger.error("Sorry, unable to find " + filename);
                return;
            }

            logger.debug("Reading properties from config.properties");
            //load a properties file from class path, inside static method
            prop.load(input);

            //get the property value
            String serverIp = prop.getProperty(SERVER_IP);
            int serverPort = Integer.parseInt(prop.getProperty(SERVER_PORT));
        
    		
            
            TestSlam.acRegisterSlam("127.0.0.1", serverPort);
    		//TestSlam.acRegisterSlam("83.235.169.221", 9010);
    		
    		
        } catch (Exception e) {
            logger.error("Exception", e);
        }finally{
            if(input!=null){
                try {
                    input.close();
                } catch (IOException e) {
                    logger.error("Exception", e);
                }
            }
        }  
            

	}
	
	private static void acRegisterSlam(String ip, int port){
        Socket vmmSocket;
        try {
        	System.out.println("vmmSocket = new Socket("+ip+", "+port+")");
            vmmSocket = new Socket(ip, port);

            ObjectOutputStream vmmOut = new ObjectOutputStream(vmmSocket.getOutputStream());
            
            // ** Case 1 **            
            // Send message format: command (java byte)
            vmmOut.writeByte(RapidMessages.AC_REGISTER_SLAM);

//            vmmOut.writeLong(2);//userId
//            vmmOut.writeInt(0);//osType  0=Linux
//            //vmmOut.writeUTF("127.0.0.1");//ip
//            //vmmOut.writeInt(9002);//port
//            vmmOut.writeUTF("83.235.169.221");//ip
//            vmmOut.writeInt(9000);//port
//            vmmOut.writeInt(3);//vcpunum
//            vmmOut.writeInt(4);//memsize
//            vmmOut.writeInt(5);//gpucores
//            
//            //vmmOut.writeUTF("{\"QoS\":[{\"variable\":\"cput_util\", \"condition\":\"LT\", \"value\":60}]}");
//            vmmOut.writeUTF("{\"QoS\":[{\"operator\":\"cpu_util\", \"term\":\"LT\", \"threshold\":60}]}");
//            //vmmOut.writeUTF("{\"QoS\":[{\"operator\":\"memory.usage\", \"term\":\"LT\", \"threshold\":60}]}");
//            //vmmOut.writeUTF("{\"QoS\":[{\"operator\":\"disk.capacity\", \"term\":\"LT\", \"threshold\":60}]}");
            

            // ** Case 2 **                       
            vmmOut.writeLong(56109);//userId
            vmmOut.writeInt(1);//osType  0=Linux
            //vmmOut.writeUTF("127.0.0.1");//ip
            //vmmOut.writeInt(9002);//port
            vmmOut.writeUTF("83.235.169.221");//ip
            vmmOut.writeInt(9000);//port
            vmmOut.writeInt(1);//vcpunum
            vmmOut.writeInt(512);//memsize
            vmmOut.writeInt(1200);//gpucores
            vmmOut.writeUTF("{\"QoS\":[{\"term\":\"cpu_util\", \"operator\":\"lt\", \"threshold\":50}, {\"term\":\"mem_util\", \"operator\":\"lt\", \"threshold\":45}, {\"term\":\"disk_util\", \"operator\":\"lt\", \"threshold\":40} ]}");            	
            //vmmOut.writeUTF("{\"QoS\":[{\"term\":\"cpu_util\", \"operator\":\"lt\", \"threshold\":50}]}");            	
            vmmOut.flush();          
            
            // Receive message format: status (java byte), vmcpuList (java object)

            ObjectInputStream vmmIn = new ObjectInputStream(vmmSocket.getInputStream());
            byte status = vmmIn.readByte();
            System.out.println("Return Status: " + (status == RapidMessages.OK ? "OK" : "ERROR"));
            if (status == RapidMessages.OK) {

            }
            vmmOut.close();
            vmmIn.close();
            vmmSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("SLAM registration error to DS");
        }
    }


}
