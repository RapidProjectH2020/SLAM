/*******************************************************************************
 * Copyright (c) 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.core;

import eu.project.rapid.common.RapidMessages;
import eu.project.rapid.common.RapidMessages.AnimationMsg;
import eu.project.rapid.common.RapidUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

public class MainSLAM {

    private final static Logger logger = Logger.getLogger(MainSLAM.class);

    private static final String CONFIG_PROPERTIES = "config.properties";
    private static final String SERVER_PORT = "server.port";
    private static final String SERVER_IP = "server.ip";
    private static final String DIRECTORY_SERVER_IP = "directory.server.ip";
    private static final String DIRECTORY_SERVER_PORT = "directory.server.port";
    private static final String THREAD_NUMBER = "thread.number";
    private static final String ANIMATION_SERVER_IP = "animation.server.ip";
    private static final String ANIMATION_SERVER_PORT = "animation.server.port";

    public static void main(String args[]){
        Properties prop = new Properties();
        InputStream input = null;

        try {
            String filename = CONFIG_PROPERTIES;
            if (args.length > 0 && args[0] !=null) filename = args[0];
            	
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

            int threadNumber = Integer.parseInt(prop.getProperty(THREAD_NUMBER));
            String animationServerIp = prop.getProperty(ANIMATION_SERVER_IP);
            int animationServerPort = Integer.parseInt(prop.getProperty(ANIMATION_SERVER_PORT));

            String directoryServerIp = prop.getProperty(DIRECTORY_SERVER_IP);
            int directoryServerPort = Integer.parseInt(prop.getProperty(DIRECTORY_SERVER_PORT));

            logger.debug("**************** Initializing server on port " + serverPort + " ****************");
            logger.debug("**************** Initializing server on ip " + serverIp + " ****************");
            logger.debug("**************** number of active threads: " + threadNumber + " ****************");
            logger.debug("**************** Animation Server Ip: " + animationServerIp + " ****************");
            logger.debug("**************** Animation Server Port: " + animationServerPort + " ****************");
            logger.debug("**************** Directory Server Ip: " + directoryServerIp + " ****************");
            logger.debug("**************** Directory Server Port: " + directoryServerPort + " ****************");
            logger.debug("**************** Properties've been initialized.. ****************");
            //RapidUtils.sendAnimationMsg(animationServerIp, animationServerPort, String.valueOf(AnimationMsg.SLAM_UP));
            ThreadPoolServer server = new ThreadPoolServer(serverPort, threadNumber,
                    animationServerIp, animationServerPort);
            SLAMRegister slamreg = new SLAMRegister();
            
            slamreg.slamRegisterDS(serverIp, serverPort,
                    directoryServerIp, directoryServerPort);
            new Thread(server).start();
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

//    protected static void slamRegisterDS(String serverIp, int serverPort,
//                                       String dirServerIP, int dirServerPort) {
//        Socket vmmSocket;
//        try {
//            //vmmSocket = new Socket(dirServerIP, dirServerPort);
//        	vmmSocket = createSocket(dirServerIP, dirServerPort);
//
//            ObjectOutputStream vmmOut = new ObjectOutputStream(vmmSocket.getOutputStream());
//            vmmOut.flush();
//            ObjectInputStream vmmIn = new ObjectInputStream(vmmSocket.getInputStream());
//            
//            logger.debug("[Flow] RapidMessages.SLAM_REGISTER_DS");
//            vmmOut.writeByte(RapidMessages.SLAM_REGISTER_DS);
//            vmmOut.writeUTF(serverIp);
//            vmmOut.writeInt(serverPort);
//            vmmOut.flush();
//            byte status = vmmIn.readByte();
//            logger.debug("[Flow] RapidMessages.SLAM_REGISTER_DS status: " + status);
//            if (status == RapidMessages.OK) {
//                logger.debug("SLAM has been registered successfully in DS!");
//            } else {
//                logger.error("SLAM registration error to DS");
//            }
//            vmmOut.close();
//            vmmIn.close();
//            vmmSocket.close();
//
//        } catch (IOException e) {
//            logger.error("SLAM registration error to DS", e);
//        }
//
//    }
//    
//    protected static Socket createSocket(String dirServerIP, int dirServerPort) throws UnknownHostException, IOException {
//        return new Socket(dirServerIP, dirServerPort);
//    }

}