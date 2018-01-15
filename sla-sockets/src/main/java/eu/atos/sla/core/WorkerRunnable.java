/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import eu.project.rapid.common.RapidMessages;
import eu.rapid.socket.qos.beans.QoSItemList;
import eu.rapid.socket.qos.sla.SLAHandler;


public class WorkerRunnable implements Runnable {

    private final static Logger logger = Logger.getLogger(WorkerRunnable.class);

    private Socket clientSocket = null;

    WorkerRunnable(Socket clientSocket) {
        logger.debug("Creating new workerRunnable to handle communication");

        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            logger.debug("Starting run workable");

            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            out.flush();
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

            int command = (int) in.readByte();

            long threadId = Thread.currentThread().getId();
            logger.debug("Thread Id: " + threadId + " | Incoming request type: " + command);
            switch (command) {
                case RapidMessages.AC_REGISTER_SLAM:
                	logger.debug("[Flow] RapidMessages.AC_REGISTER_SLAM");
                    acRegisterSlam(out, in, threadId);
                    break;
                case RapidMessages.VMM_REGISTER_SLAM:
                	logger.debug("[Flow] RapidMessages.VMM_REGISTER_SLAM");
                    vmmRegisterSlam(out, in);
                    break;
            }
            in.close();
            out.close();
            logger.debug("Thread Id: " + threadId + " | Closing socket..");
            clientSocket.close();


        } catch (IOException e) {
            logger.error("Exception", e);
        }
    }

    private void vmmRegisterSlam(ObjectOutputStream out, ObjectInputStream in) throws IOException {
        long threadId = Thread.currentThread().getId();
        try {
            logger.debug("Thread Id: " + threadId + " | VMM_REGISTER_SLAM() start");
            String vmmIP = in.readUTF();
            int vmmPort = in.readInt();
            logger.debug("A vmm has been registered with ip: " + vmmIP + " port: " + vmmPort);
            ThreadPoolServer.vmmIPList.add(vmmIP + "|" + String.valueOf(vmmPort));
            out.writeByte(RapidMessages.OK);
        } catch (Exception e) {
            logger.error("Exception", e);
            out.writeByte(RapidMessages.ERROR);
        }
        logger.debug("Thread Id: " + threadId + " | VMM_REGISTER_SLAM() end");
        out.flush();
    }

    private void acRegisterSlam(ObjectOutputStream out, ObjectInputStream in, long threadId) throws IOException {
        logger.debug("Thread Id: " + threadId + " | AC_REGISTER_SLAM()");
        try {
            //Thread.sleep(2000);
            long userID = in.readLong();
            int osType = in.readInt();
            String vmmIP = in.readUTF();
            int vmmPort = in.readInt();
            int vcpuNum = in.readInt();
            int memSize = in.readInt();
            int gpuCores = in.readInt();
            //TODO **Sokol, qos params will be parsed as last item, next days**
            String qosinjson = in.readUTF();
            
            logger.debug("[Flow-"+userID+"] ************ PARAMETERS RECEIVED ************"); 
            logger.debug("[Flow-"+userID+"] userID:[" + userID + "],"+
            "osType:[" + osType + "]," +
            "vmmIP:[" + vmmIP + "],"+
            "vmmPort:[" + vmmPort + "],"+
            "vcpuNum:[" + vcpuNum + "],"+
            "memSize:[" + memSize + "],"+
            "gpuCores:[" + gpuCores + "],"+
            "qosinjson:[" + qosinjson + "]");
            logger.debug("[Flow-"+userID+"] ********************************************");
            
            
            
           // qosinjson = "{\"QoS\":[{\"variable\":\"CPU_UTIL\", \"condition\":\"LT\", \"value\":60}]}";
           //New: {"QoS":[{"term":"CPU_UTIL", "operator":"LT", "threshold":60}	
    		QoSItemList qosItemList = null;
    		try{
    			ObjectMapper mapper = new ObjectMapper();
    			qosItemList = mapper.readValue(qosinjson, QoSItemList.class);
    		} catch (Throwable e) {
                logger.error("[Flow] exception parsing qos json", e);
    		}
    		
    		
    		// 1. Creo la maquina  Vm
    		
    		String vmIp = slamStartVmVmm(userID, osType, vmmIP, vmmPort,
                    vcpuNum, memSize, gpuCores);
            //String vmIp = "LOCALHOST";
            // Grabar en una tabla las Ips y datos de la Vm
            
            
            if (!"".equals(vmIp)) {
                out.writeByte(RapidMessages.OK);
                out.writeUTF(vmIp);
            } else {
                out.writeByte(RapidMessages.ERROR);
            }
    		
    		
    		
    		// 2. Solicito la creacion del Agreement
            SLAHandler.receivedRegisterRequest(userID, osType, vmmIP, vmmPort, vcpuNum, memSize, gpuCores, qosItemList, vmIp);
            
            logger.debug("[Flow] User id: " + userID + "vmmPort: " + vmmPort +
                    " osType: " + osType + " vmmIP: " + vmmIP +
                    " vcpuNum: " + vcpuNum + " memSize: " + memSize +
                    " gpuCores: " + gpuCores + " vmIp: " + vmIp
            );
            
            /*
            String vmIp = slamStartVmVmm(userID, osType, vmmIP, vmmPort,
                    vcpuNum, memSize, gpuCores);
            //String vmIp = "LOCALHOST";
            // Grabar en una tabla las Ips y datos de la Vm
            
            
            if (!"".equals(vmIp)) {
                out.writeByte(RapidMessages.OK);
                out.writeUTF(vmIp);
            } else {
                out.writeByte(RapidMessages.ERROR);
            }
            */
            
            logger.debug("[Flow] Thread Id: " + threadId + " | Finished processing AC_REGISTER_SLAM()");
        } catch (Exception e) {
            logger.error("[Flow] socket worker runnable Exception", e);
        }
        out.flush();
    }

    private String slamStartVmVmm(long userID, int osType, String vmmIp, int vmmPort,
                                  int vcpuNum, int memSize, int gpuCores
    ) throws IOException {
        logger.debug("slamStartVmVmm() start userID: " + userID + " osType: " + osType);
        logger.debug("Calling VMM manager socket server running at: " + vmmIp + ":" + Integer.toString(vmmPort));
        Socket vmmSocket = new Socket(vmmIp, vmmPort);

        ObjectOutputStream vmmOut = new ObjectOutputStream(vmmSocket.getOutputStream());
        vmmOut.flush();
        ObjectInputStream vmmIn = new ObjectInputStream(vmmSocket.getInputStream());

        logger.debug("[Flow-"+userID+"] RapidMessages.SLAM_START_VM_VMM params: userID="+userID+", osType="+osType+", vcpuNum="+vcpuNum+", memSize="+memSize+", gpuCores="+gpuCores); 
        vmmOut.writeByte(RapidMessages.SLAM_START_VM_VMM);
        vmmOut.writeLong(userID); // userId
        vmmOut.writeInt(osType);
        vmmOut.writeInt(vcpuNum);
        vmmOut.writeInt(memSize);
        vmmOut.writeInt(gpuCores);
        vmmOut.flush();

        byte status = vmmIn.readByte();
        logger.debug("[Flow-"+userID+"] RapidMessages.SLAM_START_VM_VMM status: " + status); 
        logger.debug("Return Status: " + (status == RapidMessages.OK ? "OK" : "ERROR"));

        String ip;
        if (status == RapidMessages.OK) {
            long user_id = vmmIn.readLong();//not used
            ip = vmmIn.readUTF();
            logger.debug("Successfully retrieved VM ip: " + ip);
        } else {
            logger.error("Error! returning null..");
            ip = "";
        }
        logger.debug("[Flow-"+userID+"] RapidMessages.SLAM_START_VM_VMM ip: " + ip);
        
        vmmOut.close();
        vmmIn.close();
        vmmSocket.close();
        logger.debug("SlamStartVmVmm() end");
        return ip;
    }
}