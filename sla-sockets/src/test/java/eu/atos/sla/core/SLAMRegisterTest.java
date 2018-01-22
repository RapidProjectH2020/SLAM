/*******************************************************************************
 * Copyright (c) 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.core;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.project.rapid.common.RapidMessages;
import eu.project.rapid.common.RapidUtils;

import static org.mockito.Mockito.*;

public class SLAMRegisterTest {
	
	 private ServerSocket serverSocket;
	 private int serverPort;
	 private String ip;
	 private boolean isStopped = false;
	 private ExecutorService threadPool = null;
	 Socket localSocket;
	 ArrayList<ObjectOutputStream> clientstreams = new ArrayList<ObjectOutputStream>();
	 
	 @Before
	 public void beforeClass() throws IOException {
      
	  TPoolServer server = new TPoolServer(0, 2);
	  new Thread(server).start();
    
	  try {Thread.sleep(5000);} catch (Exception ex) {ex.printStackTrace();}
	  
      int serverPort = server.getserverPort();
	  System.out.println("Socket CLIENT send data at port: " + serverPort);
	  localSocket = new Socket("0.0.0.0",serverPort);
	 }
	 
	 @After
	 public void afterClass() throws IOException {
	  //some cleanup
	  if (serverSocket != null && !serverSocket.isClosed()) {
	   serverSocket.close();
	  }
	 }

	@Test
	public void testSlamRegisterDSStringIntStringInt() throws Throwable {

		
	    String serverIp = "0.0.0.0";
	    int serverPort = this.serverPort;
	    String directoryServerIp = "0.0.0.0";
	    int directoryServerPort = serverPort;
        int threadNumber = 2;
        String animationServerIp = "0.0.0.0";
        int animationServerPort = serverPort;
	    
	    SLAMRegister slamreg = new SLAMRegister() 
        {

	        @Override
            protected Socket createSocket(String dirServerIP, int dirServerPort) {
                return localSocket;
            }
        	
        };
       
        Assert.assertTrue("slamRegisterDS successfully", slamreg.slamRegisterDS(serverIp, serverPort, directoryServerIp, directoryServerPort));
	}

}
