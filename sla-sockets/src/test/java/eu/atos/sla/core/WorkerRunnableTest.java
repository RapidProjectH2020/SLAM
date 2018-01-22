/*******************************************************************************
 * Copyright (c) 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Request;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;

import eu.atos.sla.test.TestSlam;
import eu.project.rapid.common.RapidMessages;

public class WorkerRunnableTest {
	
    private final static Logger logger = Logger.getLogger(TestSlam.class);
    private static final String CONFIG_PROPERTIES = "config.properties";
    private static final String SERVER_PORT = "server.port";
    private static final String SERVER_IP = "server.ip";
    ServerSocket mockServerSocket = null;
    Socket mockTestClientSocket = null;
    		
    private ServerSocket serverSocket = null;
    private boolean isStopped = false;
    private int serverPort = 0;
    private static int VMMserverPort;
    
    private static final int PUERTO_HTTP = 8080;
    private static final String URL_SERVICIO = "http://localhost:" + PUERTO_HTTP;
 
    private Server httpServer;
 
    
	@Before
	public void setup() throws Throwable{


        httpServer = new Server(PUERTO_HTTP);
        httpServer.start();
        
		System.out.println("Start VMM emulating Socket");
		TPoolServer server = new TPoolServer(0, 2);
		new Thread(server).start();
	    
		try {Thread.sleep(5000);} catch (Exception ex) {ex.printStackTrace();}
	    VMMserverPort = server.getserverPort();
		System.out.println("Started VMM emulating Socket in port : " + VMMserverPort);
	
	}
	
    @After
    public void tearDown() throws Exception {
        httpServer.stop();
    }
	
	@Test
	public void testWorkerRunnable() throws Throwable {
		int serverPort = 0;
		int threadNumber = 2;
        String animationServerIp = "0.0.0.0";
        int animationServerPort = 0;
        
        ThreadPoolServer server = new ThreadPoolServer(serverPort, threadNumber,
                animationServerIp, animationServerPort) {

        	 protected void openServerSocket() {
                 try {
                     	this.serverSocket = new ServerSocket(this.serverPort);
                	 	this.serverPort = serverSocket.getLocalPort();
            	  
                     System.out.println("Socket SERVER started at port for listening at: " + serverSocket.getLocalSocketAddress().toString());

                 } catch (IOException e) {
                     throw new RuntimeException("Cannot open server port", e);
                 }
             }
        	
        	
        };
  	  	
        new Thread(server).start();
  	  	try {Thread.sleep(5000);} catch (Exception ex) {ex.printStackTrace();}
    	

  	  	String newtemp="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><message code=\"201\" message=\"The template has been stored successfully in the SLA Repository Database. It has location http://127.0.0.1:8080/sla-service/templates/31be4d34-5655-4606-b33e-51229d7e08b3\" elementId=\"31be4d34-5655-4606-b33e-51229d7e08b3\"/>";
        
  	  	Handler handler = new AbstractHandler() {
        	
        	@Override 
            public void handle(String target, 
                    HttpServletRequest request, 
                    HttpServletResponse response, 
                    int dispatch)
                    throws IOException, ServletException {
                response.setContentType("text/html");
                String sUri = request.getRequestURI();
                System.out.println("request.getRequestURI: " + sUri);

                switch (sUri) {
	                case "/sla-service/providers/1234567890":
	                    response.setStatus(HttpServletResponse.SC_OK);
	                	response.getWriter().print("Rapid");
	                    break;
	                case "/sla-service/templates":
	                    response.setStatus(HttpServletResponse.SC_CREATED);
	                    response.getWriter().print(newtemp);
	                    break;
	                case "/sla-service/agreements":
	                    response.setStatus(HttpServletResponse.SC_CREATED);
	                    response.getWriter().print(newtemp);
	                    break;
	                case "/sla-service/enforcements/31be4d34-5655-4606-b33e-51229d7e08b3/start":
	                    response.setStatus(HttpServletResponse.SC_ACCEPTED);
	                    response.getWriter().print(newtemp);
	                    break;
                }


                ((Request) request).setHandled(true);
            }

        };
        httpServer.setHandler(handler);
  	  	
  	  	acRegisterSlam("0.0.0.0", server.serverPort);

  	  
	}

	
    private synchronized boolean isStopped() {
        return this.isStopped;
    }
    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
            logger.debug("Socket server started at port for listening at: " + this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open server port", e);
        }
    }
	

	private static void acRegisterSlam(String ip, int port) throws Throwable{
        Socket vmmSocket;
	
        try {
        	System.out.println("vmmSocket = new Socket("+ip+", "+port+")");
            vmmSocket = new Socket(ip, port);
        	
        	
            ObjectOutputStream vmmOut = new ObjectOutputStream(vmmSocket.getOutputStream());
            
            vmmOut.writeByte(RapidMessages.AC_REGISTER_SLAM);
            vmmOut.writeLong(1234567890);//userId
            vmmOut.writeInt(1);//osType  0=Linux
            //vmmOut.writeUTF("127.0.0.1");//ip
            //vmmOut.writeInt(9002);//port
            //vmmOut.writeUTF("83.235.169.221");//ip
            vmmOut.writeUTF("0.0.0.0");//ip
            //vmmOut.writeUTF("TEST");//ip
            //vmmOut.writeInt(9000);//port
            //vmmOut.writeInt(9000);//port
            vmmOut.writeInt(VMMserverPort);//port
            vmmOut.writeInt(1);//vcpunum
            vmmOut.writeInt(512);//memsize
            vmmOut.writeInt(1200);//gpucores
            vmmOut.writeUTF("{\"QoS\":[{\"term\":\"cpu_util\", \"operator\":\"lt\", \"threshold\":50}, {\"term\":\"mem_util\", \"operator\":\"lt\", \"threshold\":45}, {\"term\":\"disk_util\", \"operator\":\"lt\", \"threshold\":40} ]}");            	
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
