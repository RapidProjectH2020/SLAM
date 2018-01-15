/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
 package eu.rapid.monitoring;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import eu.atos.sla.core.MainSLAM;
//import eu.atos.sla.core.ThreadPoolServer;
import eu.atos.sla.enforcement.AgreementEnforcement;
import eu.atos.sla.monitoring.IMetricsRetriever;
import eu.atos.sla.monitoring.IMonitoringMetric;
import eu.atos.sla.monitoring.simple.MonitoringMetric;
import eu.project.rapid.common.RapidUtils;
import eu.project.rapid.common.RapidMessages.AnimationMsg;
import eu.project.rapid.common.RapidMessages;

public class RetrieveMetrics implements IMetricsRetriever {

	private final Random rnd = new Random();
	private final static Logger logger = LoggerFactory.getLogger(RetrieveMetrics.class);
    private static final String CONFIG_PROPERTIES = "config.properties";
    private static final String VMM_SERVER_IP = "vmm.server.ip";
    private static final String VMM_SERVER_PORT = "vmm.server.port";
    String vmmAddress;
    int vmmPort;
    
	public RetrieveMetrics() {
		
        Properties prop = new Properties();
        InputStream input = null;

        try {
            String filename = CONFIG_PROPERTIES;
            input = RetrieveMetrics.class.getClassLoader().getResourceAsStream(filename);
            if(input==null){
                logger.error("Sorry, unable to find " + filename);
                return;
            }

            logger.debug("Reading properties from config.properties");
            //load a properties file from class path, inside static method
            prop.load(input);

            //get the property value
            vmmAddress = prop.getProperty(VMM_SERVER_IP);
            vmmPort = Integer.parseInt(prop.getProperty(VMM_SERVER_PORT));

            logger.debug("**************** Initializing VMM server on port " + vmmAddress + " ****************");
            logger.debug("**************** Initializing VMM server on ip " + vmmPort + " ****************");
            logger.debug("**************** Properties've been initialized.. ****************");
            
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
	
	@Override
	public List<IMonitoringMetric>  getMetrics(String agreementId, String serviceScope, String variable, Date begin, Date end, int maxResults){
		

		
		List<IMonitoringMetric> result = new ArrayList<IMonitoringMetric>();
		if (begin == null) {
			
			begin = new Date(end.getTime() - 1000);
		}
		
		String sUserId = null;
		StringTokenizer st = new StringTokenizer(serviceScope,",");
		while (st.hasMoreTokens()) {
			String cadena = st.nextToken();
			String[] val= cadena.split("=");
			if (val[0].equals("userID")) sUserId=val[1];
		}
		
		logger.debug("[Flow-"+sUserId+"] ******** List<IMonitoringMetric> getMetrics *******");
		logger.debug("[Flow-"+sUserId+"] agreementId=" + agreementId +", serviceScope="+serviceScope+", variable="+variable+", begin="+begin+", end="+end+", maxResults="+maxResults);
		
	try {		
		Socket vmmSocket;
		vmmSocket = new Socket(vmmAddress, vmmPort);
		
		if ( variable.equals("cpu_util") )
		{	
			// RETRIEVE CPU
			logger.debug("RapidMessages.SLAM_GET_VMCPU_VMM");
			ObjectOutputStream vmmOut = new ObjectOutputStream(vmmSocket.getOutputStream());
			vmmOut.flush();
			ObjectInputStream vmmIn = new ObjectInputStream(vmmSocket.getInputStream());
	
			// Send message format: command (java byte)
			
			logger.debug("[Flow-"+sUserId+"] RapidMessages.SLAM_GET_VMCPU_VMM"); 
			vmmOut.writeByte(RapidMessages.SLAM_GET_VMCPU_VMM);
			vmmOut.writeLong(Long.parseLong(sUserId)); // userId
			vmmOut.flush();
	
			// 	Receive message format: status (java byte), vmcpuList (java object)
			
			Byte status= null;
			
	
				status = vmmIn.readByte();
				logger.debug("[Flow-"+sUserId+"] RapidMessages.SLAM_GET_VMCPU_VMM status: " + status); 
				logger.debug("SLAM_GET_VMCPU_VMM Return Status: " + (status == RapidMessages.OK ? "OK" : "ERROR"));
	
			if (status == RapidMessages.OK) {
				ArrayList<String> vmcpuList = (ArrayList<String>) vmmIn.readObject();
				Iterator<String> vmcpuListIterator = vmcpuList.iterator();
				logger.debug("Received CPU Util List: ");
				int i = 0;
				while (vmcpuListIterator.hasNext()) {
					String userId = vmcpuListIterator.next();
					String vmId  = vmcpuListIterator.next();
					String cpuUtil  = vmcpuListIterator.next();
					logger.debug("[Flow-"+sUserId+"] Received CPU Util List: userId="+userId+", vmId="+vmId+", cpuUtil=" + cpuUtil);
					
					result.add(getMonitoringMetric(variable, (new Double(cpuUtil)).doubleValue(), i == 0? begin : end ));
					i++;	
													
				}
			}
			vmmOut.close();
			vmmIn.close();
			vmmSocket.close();
		}
		
		if ( variable.equals("mem_util") )
		{
			// RETRIEVE MEM
			vmmSocket = new Socket(vmmAddress, vmmPort);
			ObjectOutputStream  vmmOut = new ObjectOutputStream(vmmSocket.getOutputStream());
			vmmOut.flush();
			ObjectInputStream vmmIn = new ObjectInputStream(vmmSocket.getInputStream());
	
			// Send message format: command (java byte)
			logger.debug("[Flow-"+sUserId+"] RapidMessages.SLAM_GET_VMMEM_VMM"); 
			vmmOut.writeByte(RapidMessages.SLAM_GET_VMMEM_VMM);
			vmmOut.writeLong(Long.parseLong(sUserId)); // userId
			vmmOut.flush();
	
			// 	Receive message format: status (java byte), vmcpuList (java object)
			Byte status= null;
			status = vmmIn.readByte();
			logger.debug("[Flow-"+sUserId+"] RapidMessages.SLAM_GET_VMMEM_VMM status: " + status); 
			logger.debug("SLAM_GET_VMMEM_VMM Return Status: " + (status == RapidMessages.OK ? "OK" : "ERROR"));
	
			if (status == RapidMessages.OK) {
				ArrayList<String> vmmemList = (ArrayList<String>) vmmIn.readObject();
				Iterator<String> vmmemListIterator = vmmemList.iterator();
				logger.debug("Received MEMORY Util List: ");
				int i = 0;
				while (vmmemListIterator.hasNext()) {
					String userId = vmmemListIterator.next();
					String vmId  = vmmemListIterator.next();
					String memory  = vmmemListIterator.next();
					String memoryusage  = vmmemListIterator.next();
					
					
					Double percent_memvalue = new Double((new Double(memoryusage).doubleValue()/new Double(memory).doubleValue())*100);
	
					logger.debug("[Flow-"+sUserId+"] Received MEMORY Util List: userId="+userId+", vmId="+vmId+", memory=" + memory+", memoryusage=" + memoryusage +", percent_memvalue=" + percent_memvalue+ " varianble = "+variable);
				
					result.add(getMonitoringMetric(variable, percent_memvalue, i == 0? begin : end ));
					i++;	
													
				}
			}
			
			
			vmmOut.close();
			vmmIn.close();
			vmmSocket.close();
		}

		if ( variable.equals("disk_util") )
		{		
			// RETRIEVE DISK
			vmmSocket = new Socket(vmmAddress, vmmPort);
			logger.debug("RapidMessages.SLAM_GET_VMDISK_VMM");
			ObjectOutputStream vmmOut = new ObjectOutputStream(vmmSocket.getOutputStream());
			vmmOut.flush();
			ObjectInputStream vmmIn = new ObjectInputStream(vmmSocket.getInputStream());
	
			// Send message format: command (java byte)
			logger.debug("[Flow-"+sUserId+"] RapidMessages.SLAM_GET_VMDISK_VMM"); 
			vmmOut.writeByte(RapidMessages.SLAM_GET_VMDISK_VMM);
			vmmOut.writeLong(Long.parseLong(sUserId)); // userId
			vmmOut.flush();
	
			// 	Receive message format: status (java byte), vmcpuList (java object)
			Byte status= null;
			status = vmmIn.readByte();
			logger.debug("[Flow-"+sUserId+"] RapidMessages.SLAM_GET_VMDISK_VMM status: " + status); 
			System.out.println("Return Status: " + (status == RapidMessages.OK ? "OK" : "ERROR"));
			logger.debug("SLAM_GET_VMDISK_VMM Return Status: " + (status == RapidMessages.OK ? "OK" : "ERROR"));
	
			if (status == RapidMessages.OK) {
				ArrayList<String> vmdiskList = (ArrayList<String>) vmmIn.readObject();
				Iterator<String> vmdiskListIterator = vmdiskList.iterator();
				logger.debug("Received DISK Util List: ");
				System.out.println("Received DISK Util List: ");
				int i = 0;
				while (vmdiskListIterator.hasNext()) {
					String userId = vmdiskListIterator.next();
					String vmId  = vmdiskListIterator.next();
					String diskcapacity  = vmdiskListIterator.next();
					String diskallocation  = vmdiskListIterator.next();
					
					Double percent_diskvalue = new Double((new Double(diskallocation).doubleValue()/new Double(diskcapacity).doubleValue())*100);
					
					logger.debug("[Flow-"+sUserId+"] Received DISK Util List: userId="+userId+", vmId="+vmId+", diskcapacity=" + diskcapacity+", diskallocation=" + diskallocation +", percent_diskvalue=" + percent_diskvalue+ " variable = "+variable);
	
					result.add(getMonitoringMetric(variable, percent_diskvalue, i == 0? begin : end ));
					i++;	
													
				}
			}
			
			
			vmmOut.close();
			vmmIn.close();
			vmmSocket.close();
		}
		
		
		
		
		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}	
	
		return result;
	}
	
	public IMonitoringMetric getMonitoringMetric(String metricName, double metricValue, Date date) {
		
		return new MonitoringMetric(metricName, metricValue, date);
	}

}