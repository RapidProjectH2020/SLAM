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
import java.io.StringReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.atos.sla.datamodel.IAgreement;
import eu.atos.sla.datamodel.IGuaranteeTerm;
import eu.atos.sla.datamodel.IViolation;
import eu.atos.sla.evaluation.guarantee.GuaranteeTermEvaluator.GuaranteeTermEvaluationResult;
import eu.atos.sla.notification.IAgreementEnforcementNotifier;
import eu.project.rapid.common.RapidMessages;

public class EnforcementNotifier implements IAgreementEnforcementNotifier {
	private static Logger logger = LoggerFactory.getLogger(EnforcementNotifier.class);
	
    private static final String CONFIG_PROPERTIES = "config.properties";
    private static final String VMM_SERVER_IP = "vmm.server.ip";
    private static final String VMM_SERVER_PORT = "vmm.server.port";
    private static final String MAX_CPU_NRO = "max.cpu.nro";
    private static final String MAX_MEM_RAM = "max.mem.ram";
    private static final String MAX_DISK_GB = "max.disk.gb";
    String vmmAddress;
    int vmmPort;
	int imax_cpu;
	int imax_mem;
	int imax_disk;
	
	List<String> lSerScop = new ArrayList<String>();
	
	public EnforcementNotifier() {
		
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
        	imax_cpu = Integer.parseInt(prop.getProperty(MAX_CPU_NRO));
        	imax_mem = Integer.parseInt(prop.getProperty(MAX_MEM_RAM));
        	imax_disk = Integer.parseInt(prop.getProperty(MAX_DISK_GB));

            logger.debug("**************** Initializing VMM server on port " + vmmAddress + " ****************");
            logger.debug("**************** Initializing VMM server on ip " + vmmPort + " ****************");
            logger.debug("**************** Initializing imax_cpu " + imax_cpu + " ****************");
            logger.debug("**************** Initializing imax_mem " + imax_mem + " ****************");
            logger.debug("**************** Initializing imax_disk " + imax_disk + " ****************");
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
	public void onFinishEvaluation(IAgreement agreement,
			Map<IGuaranteeTerm, GuaranteeTermEvaluationResult>  guaranteeTermEvaluationMap) {
		
		logger.debug("Notifying onFinishEvaluation {}", agreement.getId());
		lSerScop = new ArrayList<String>();
		

		
		for (Entry<IGuaranteeTerm, GuaranteeTermEvaluationResult> e:guaranteeTermEvaluationMap.entrySet()) {
			IGuaranteeTerm gt = e.getKey();
			GuaranteeTermEvaluationResult gtresult = e.getValue();
			List<IViolation> lViolations = gtresult.getViolations();
			logger.debug("[Flow-"+agreement.getConsumer()+"] GT to be fullfilled= ["+gt.getServiceLevel()+"]");
			
			logger.debug("[Flow-"+agreement.getConsumer()+"] lViolations.size()= ["+lViolations.size()+"]");

			if (lViolations.size() > 0)
			{
				
				try {		
					Socket vmmSocket;
					vmmSocket = new Socket(vmmAddress, vmmPort);
					ObjectOutputStream vmmOut = null;
					ObjectInputStream vmmIn = null;

				
				for (int x=0; x < lViolations.size(); x++)
				{
					IViolation violation = lViolations.get(x);
					String skpiName =  violation.getKpiName();
					String sServiceScope = violation.getServiceScope();
					logger.debug("[Flow-"+agreement.getConsumer()+"]  agreement"+violation.getContractUuid()+" violation value ["+violation.getActualValue()+"]");
					
					
					
					String sUserId=null;
					String snumCPU=null;
					String smemSize=null;
					
					String vm_vcpuNum=null;
					String vm_memSize=null;
					String vm_diskSize=null;
					
					// De aqui sacamos la info del userId o ServerId para hacer la llamada al resize
					// 2017-04-26T12:09:33.247Z [Thread-1] INFO  eu.rapid.monitoring.EnforcementNotifier 
					// sServiceScope: userID=2,osType=0,vmmIP=83.235.169.221,vmmPort=9000,vcpuNum=3,memSize=4,gpuCores=5,vmIp=10.0.0.14

					StringTokenizer st = new StringTokenizer(sServiceScope,",");
					while (st.hasMoreTokens()) {
						String cadena = st.nextToken();
						String[] val= cadena.split("=");
						if (val[0].equals("userID")) sUserId=val[1];
						else if	(val[0].equals("vcpuNum")) snumCPU=val[1];
						else if	(val[0].equals("memSize")) smemSize=val[1];

					}
					
					logger.debug("[Flow-"+sUserId+"] KPI Name = ["+skpiName+"]");
					logger.debug("[Flow-"+sUserId+"] Service Scope: [" + sServiceScope + "]");

				
					Byte status= null;
					
					if (notlSerScop(sUserId, sServiceScope) )
					{						
	
						logger.debug("sUserId: " + sUserId);
						logger.debug("snumCPU Agreement: " + snumCPU);	
							
						vmmSocket = new Socket(vmmAddress, vmmPort);
						vmmOut = new ObjectOutputStream(vmmSocket.getOutputStream());
						vmmOut.flush();
						vmmIn = new ObjectInputStream(vmmSocket.getInputStream());
						// Send message format: command (java byte)
							
						logger.debug("[Flow-"+sUserId+"] RapidMessages.SLAM_GET_VMINFO_VMM"); 
						vmmOut.writeByte(RapidMessages.SLAM_GET_VMINFO_VMM);
						vmmOut.writeLong(Long.parseLong(sUserId));
						vmmOut.flush();
	
						status = vmmIn.readByte();
						logger.debug("[Flow-"+sUserId+"] RapidMessages.SLAM_GET_VMINFO_VMM status: " + status); 
						System.out.println("Return Status: " + (status == RapidMessages.OK ? "OK" : "ERROR"));
						if (status == RapidMessages.OK) {
							ArrayList<String> vminfoList = (ArrayList<String>) vmmIn.readObject();
							Iterator<String> vminfoListIterator = vminfoList.iterator();
							while (vminfoListIterator.hasNext()) {
								String vm_userId = vminfoListIterator.next();
								String vm_vmId = vminfoListIterator.next();
								vm_vcpuNum = vminfoListIterator.next();
								vm_memSize = vminfoListIterator.next();
								vm_diskSize = vminfoListIterator.next();
								String vm_gpuCores = vminfoListIterator.next();
																
								logger.debug("[Flow-"+sUserId+"] Received CPU info: vm_userId="+vm_userId+", vm_vmId="+vm_vmId+", vm_vcpuNum="+vm_vcpuNum+", vm_memSize="+vm_memSize+", vm_gpuCores=" + vm_gpuCores);
							}
						}
	
							// Send message format: command (java byte)
						vmmOut.close();
						vmmIn.close();
						vmmSocket.close();
							
						// incluir cpu_util y el resto de metricas en el fichero de Rapid.
						if (skpiName.equals("cpu_util") && notlSerScop(sUserId, sServiceScope) )
						{
						//*************************
							
							int inumCPU = (Integer.parseInt(vm_vcpuNum)); 
							//inumCPU	= inumCPU + (int) Math.pow(2, inumCPU-1);
							inumCPU	= inumCPU * 2;
							
							if (inumCPU < imax_cpu)
							{								
								
								// vcpuNum
								logger.debug("[Flow-"+sUserId+"] RapidMessages.SLAM_CHANGE_VMFLV_VMM parameters: sUserId="+sUserId+", New inumCPU="+inumCPU); 

								status = sendVMM(vmmAddress, vmmPort, sUserId, RapidMessages.SLAM_CHANGE_VMFLV_VMM, "RapidMessages.SLAM_CHANGE_VMFLV_VMM",inumCPU, 0, 0);
								
								logger.debug("[Flow-"+sUserId+"] RapidMessages.SLAM_CHANGE_VMFLV_VMM status: " + status);
								
								//System.out.println("Return Status: " + (status == RapidMessages.OK ? "OK" : "ERROR"));
								if (status == RapidMessages.OK)
								{
									logger.debug("[Flow-"+sUserId+"] SLAM_CHANGE_VMFLV_VMM OK");	
									// Pause for wait to reboot
									try {Thread.sleep(30000);} catch (Exception ex) {ex.printStackTrace();}
								}
								else
									logger.debug("[Flow-"+sUserId+"] SLAM_CHANGE_VMFLV_VMM ERROR");		
								
						
								if (status == RapidMessages.OK)
								{						
									logger.debug("[Flow-"+sUserId+"] RapidMessages.SLAM_CONFIRM_VMFLV_VMM parameters: sUserId="+sUserId+", New inumCPU="+inumCPU); 

									status = sendVMM(vmmAddress, vmmPort, sUserId, RapidMessages.SLAM_CONFIRM_VMFLV_VMM, "RapidMessages.SLAM_CONFIRM_VMFLV_VMM",inumCPU, 0, 0);
									
									logger.debug("[Flow-"+sUserId+"] RapidMessages.SLAM_CONFIRM_VMFLV_VMM status: " + status);
									if (status == RapidMessages.OK)
										logger.debug("[Flow-"+sUserId+"] SLAM_CONFIRM_VMFLV_VMM OK");	
									else
									{
										logger.debug("[Flow-"+sUserId+"] SLAM_CONFIRM_VMFLV_VMM ERROR");	
										
										status = sendVMM(vmmAddress, vmmPort, sUserId, RapidMessages.SLAM_CONFIRM_VMFLV_VMM, "RapidMessages.SLAM_CONFIRM_VMFLV_VMM",inumCPU, 0, 0);
										
										logger.debug("[Flow-"+sUserId+"] RETRY RapidMessages.SLAM_CONFIRM_VMFLV_VMM status: " + status);
										while (status != RapidMessages.OK)
										{
											status = sendVMM(vmmAddress, vmmPort, sUserId, RapidMessages.SLAM_CONFIRM_VMFLV_VMM, "RapidMessages.SLAM_CONFIRM_VMFLV_VMM",inumCPU, 0, 0);
											logger.debug("[Flow-"+sUserId+"] RETRY RapidMessages.SLAM_CONFIRM_VMFLV_VMM status: " + status);
										}
										logger.debug("[Flow-"+sUserId+"] SLAM_CONFIRM_VMFLV_VMM OK");	
									}
								}
							//**********************
							}
							else
							{
								//else num_cpu	
								logger.debug("[Flow-"+sUserId+"] MAXIMUM Num. of CPUs reached: " + imax_cpu);
							}	
							
						} // end if skpiName.equals("cpu_util")
						
						if (skpiName.equals("mem_util"))
						{
						//*************************
							
							int inumMEM = (Integer.parseInt(vm_memSize)); 
							//inumCPU	= inumCPU + (int) Math.pow(2, inumCPU-1);
							inumMEM	= inumMEM * 2;
							
							if (inumMEM < imax_mem)
							{								
							
								
								logger.debug("[Flow-"+sUserId+"] "+skpiName+" RapidMessages.SLAM_CHANGE_VMFLV_VMM parameters: sUserId="+sUserId+", New inumMEM="+inumMEM); 								

								status = sendVMM(vmmAddress, vmmPort, sUserId, RapidMessages.SLAM_CHANGE_VMFLV_VMM, "RapidMessages.SLAM_CHANGE_VMFLV_VMM", 0, inumMEM, 0);

								logger.debug("[Flow-"+sUserId+"] "+skpiName+" RapidMessages.SLAM_CHANGE_VMFLV_VMM status: " + status);
								
								if (status == RapidMessages.OK)
								{
									logger.debug("SLAM_CHANGE_VMFLV_VMM OK");	
									// Pause for wait to reboot
									try {Thread.sleep(30000);} catch (Exception ex) {ex.printStackTrace();}
								}
								else
									logger.debug("SLAM_CHANGE_VMFLV_VMM ERROR");		
								
								
								if (status == RapidMessages.OK)
								{						
		
									logger.debug("[Flow-"+sUserId+"] RapidMessages.SLAM_CONFIRM_VMFLV_VMM parameters: sUserId="+sUserId+", New inumCPU="+inumMEM); 
									
									status = sendVMM(vmmAddress, vmmPort, sUserId, RapidMessages.SLAM_CONFIRM_VMFLV_VMM, "RapidMessages.SLAM_CONFIRM_VMFLV_VMM", 0, inumMEM, 0);								
									
									logger.debug("[Flow-"+sUserId+"] RapidMessages.SLAM_CONFIRM_VMFLV_VMM status: " + status);

									if (status == RapidMessages.OK)
										logger.debug("SLAM_CONFIRM_VMFLV_VMM OK");	
									else
									{
										logger.debug("[Flow-"+sUserId+"] SLAM_CONFIRM_VMFLV_VMM ERROR");
										
										// volver a enviar el confirm
										status = sendVMM(vmmAddress, vmmPort, sUserId, RapidMessages.SLAM_CONFIRM_VMFLV_VMM, "RapidMessages.SLAM_CONFIRM_VMFLV_VMM", 0, inumMEM, 0);								
										
										logger.debug("[Flow-"+sUserId+"] RETRY RapidMessages.SLAM_CONFIRM_VMFLV_VMM status: " + status);
										while (status != RapidMessages.OK)
										{
											status = sendVMM(vmmAddress, vmmPort, sUserId, RapidMessages.SLAM_CONFIRM_VMFLV_VMM, "RapidMessages.SLAM_CONFIRM_VMFLV_VMM", 0, inumMEM, 0);								
											logger.debug("[Flow-"+sUserId+"] RETRY RapidMessages.SLAM_CONFIRM_VMFLV_VMM status: " + status);
										}
									}

								}
							//**********************
							}
							else
							{
								//else num_mem	
								logger.debug("[Flow-"+sUserId+"] MAXIMUM Num. of MEM reached: " + imax_mem);
							}	
							
						} // end if skpiName.equals("mem_util")
						
						if (skpiName.equals("disk_util") )
						{
						//*************************
							
							int inumDISK = (Integer.parseInt(vm_diskSize)); 

							inumDISK	= inumDISK + 5; // add 5GB
							
							if (inumDISK < imax_disk)
							{								
							
								
								logger.debug("[Flow-"+sUserId+"] "+skpiName+" RapidMessages.SLAM_CHANGE_VMFLV_VMM parameters: sUserId="+sUserId+", New inumMEM="+inumDISK); 								

								status = sendVMM(vmmAddress, vmmPort, sUserId, RapidMessages.SLAM_CHANGE_VMFLV_VMM, "RapidMessages.SLAM_CHANGE_VMFLV_VMM", 0, 0, inumDISK);

								logger.debug("[Flow-"+sUserId+"] "+skpiName+" RapidMessages.SLAM_CHANGE_VMFLV_VMM status: " + status);
								
								if (status == RapidMessages.OK)
								{
									logger.debug("SLAM_CHANGE_VMFLV_VMM OK");	
									// Pause for wait to reboot
									try {Thread.sleep(30000);} catch (Exception ex) {ex.printStackTrace();}
								}
								else
									logger.debug("SLAM_CHANGE_VMFLV_VMM ERROR");		
								
								
								if (status == RapidMessages.OK)
								{						
		
									logger.debug("[Flow-"+sUserId+"] RapidMessages.SLAM_CONFIRM_VMFLV_VMM parameters: sUserId="+sUserId+", New inumCPU="+inumDISK); 
									
									status = sendVMM(vmmAddress, vmmPort, sUserId, RapidMessages.SLAM_CONFIRM_VMFLV_VMM, "RapidMessages.SLAM_CONFIRM_VMFLV_VMM", 0, 0, inumDISK);								
									
									logger.debug("[Flow-"+sUserId+"] RapidMessages.SLAM_CONFIRM_VMFLV_VMM status: " + status);

									if (status == RapidMessages.OK)
										logger.debug("SLAM_CONFIRM_VMFLV_VMM OK");	
									else
									{
										logger.debug("[Flow-"+sUserId+"] SLAM_CONFIRM_VMFLV_VMM ERROR");
										
										// volver a enviar el confirm
										status = sendVMM(vmmAddress, vmmPort, sUserId, RapidMessages.SLAM_CONFIRM_VMFLV_VMM, "RapidMessages.SLAM_CONFIRM_VMFLV_VMM", 0, 0, inumDISK);								
										
										logger.debug("[Flow-"+sUserId+"] RETRY RapidMessages.SLAM_CONFIRM_VMFLV_VMM status: " + status);
										while (status != RapidMessages.OK)
										{
											status = sendVMM(vmmAddress, vmmPort, sUserId, RapidMessages.SLAM_CONFIRM_VMFLV_VMM, "RapidMessages.SLAM_CONFIRM_VMFLV_VMM", 0, 0, inumDISK);								
											logger.debug("[Flow-"+sUserId+"] RETRY RapidMessages.SLAM_CONFIRM_VMFLV_VMM status: " + status);
										}
									}

								}
							//**********************
							}
							else
							{
								//else num_disk	
								logger.debug("[Flow-"+sUserId+"] MAXIMUM Num. of DISK reached: " + imax_disk);
							}	
							
						} // end if skpiName.equals("disk_util")

						
						
					}
					lSerScop.add(sServiceScope);
				}
				

				
				} catch (IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
					logger.error("Flow  exception while executing enforcement" + ex.getMessage());	
				}	
				catch (Throwable t) {
				// TODO Auto-generated catch block
				t.printStackTrace();
				logger.error("Flow  exception while executing enforcement" + t.getMessage());	

				}
				
			}

			
			// Start nueva VM?
			/*
            String vmIp = slamStartVmVmm(userID, osType, vmmIP, vmmPort,
                    vcpuNum, memSize, gpuCores);
            //String vmIp = "LOCALHOST";
            if (!"".equals(vmIp)) {
                out.writeByte(RapidMessages.OK);
                out.writeUTF(vmIp);
            } else {
                out.writeByte(RapidMessages.ERROR);
            }
            */
			
			
			
			
			logger.debug("Notifying onFinishEvaluation GuaranteeTerm:{} Num violations:{} Num compensations:{} ", gt.getId(), gtresult.getViolations().size(), gtresult.getCompensations().size());
		}
		logger.debug("  onFinishEvaluation", agreement.getId());
	}

private Byte sendVMM(String vmmAddress, int vmmPort, String sUserId, int RapidMessage, String sRapidMessage, int newCPU, int newMEM, int newDISK ) throws Throwable{
	
	Byte status= null;	
	
	Socket vmmSocket = new Socket(vmmAddress, vmmPort);
	ObjectOutputStream vmmOut = new ObjectOutputStream(vmmSocket.getOutputStream());
	vmmOut.flush();
	ObjectInputStream vmmIn = new ObjectInputStream(vmmSocket.getInputStream());
	// Send message format: command (java byte)

	vmmOut.writeByte(RapidMessages.SLAM_CONFIRM_VMFLV_VMM);		
	// userId
	vmmOut.writeLong(Long.parseLong(sUserId));

	vmmOut.writeInt(newCPU);
	vmmOut.writeInt(newMEM);
	vmmOut.writeInt(newDISK);
	
	vmmOut.flush();
	
	status = vmmIn.readByte();
	
	vmmOut.close();
	vmmIn.close();
	vmmSocket.close();
	
	return status;
}
	
	
	
	
	private boolean notlSerScop(String sUserId, String sServiceScope) throws Throwable{
		// TODO Auto-generated method stub
		try {
		logger.debug("[Flow-"+sUserId+"] lSerScop.size"+ lSerScop.size()  );
		for( int i = 0 ; i < lSerScop.size() ; i++ ){
			  logger.debug("[Flow-"+sUserId+"] lSerScop.get("+i+")"+ lSerScop.get(i) +" -- "+sServiceScope);
			  if(lSerScop.get(i).equals(sServiceScope)) {
					logger.debug("[Flow-"+sUserId+"] return true");

					  return false;
			  }
			}
		logger.debug("[Flow-"+sUserId+"] return false");
		}catch(Throwable t) {
			logger.error("[Flow-"+sUserId+"] Error in notSerScop", t);
		}
		return true;
	}

	
	
	
}
