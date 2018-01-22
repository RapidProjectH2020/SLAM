/*******************************************************************************
 * Copyright (c) 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.core;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import static org.mockito.Mockito.*;

public class MainSLAMTest {
	
    private static final String CONFIG_PROPERTIES = "config.properties";
    private static final String SERVER_PORT = "server.port";
    private static final String SERVER_IP = "server.ip";
    ServerSocket mockServerSocket = null;
    Socket mockTestClientSocket = null;
    
    private MainSLAM mainslam = Mockito.mock(MainSLAM.class);
    
	@Before
	public void setup() throws Throwable{
		    mockServerSocket = mock(ServerSocket.class);
		   
			try {
		        when(mockServerSocket.accept()).thenReturn(mockTestClientSocket);
		    } catch (IOException e) {
		        fail(e.getMessage());
		    }

		    mockTestClientSocket = mock(Socket.class);
		    try {
		        ByteArrayOutputStream bs= new ByteArrayOutputStream();
		        
		        //PipedOutputStream oStream = new PipedOutputStream();
		    	//ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		    	ObjectOutputStream oStream = new ObjectOutputStream(bs);
		    	//FilterOutputStream oStream = new FilterOutputStream(outStream); 
		        when(mockTestClientSocket.getOutputStream()).thenReturn(oStream);
		        
		        //SocketInputStream oStream = new SocketInputStream();
		        //PipedInputStream iStream = new PipedInputStream(oStream);


		        byte[] mybytearray =  bs.toByteArray();
				ByteArrayInputStream iStream = new ByteArrayInputStream(mybytearray);
		    	//ObjectInputStream iStream = new ObjectInputStream(bs);
				//FilterInputStream iStream = new FilterInputStream(inputStream); 
				
		        when(mockTestClientSocket.getInputStream()).thenReturn(iStream);

		        when(mockTestClientSocket.isClosed()).thenReturn(false);
		    } catch (IOException e) {
		        fail(e.getMessage());
		    }
	
		    //MainSLAM ms = mock(MainSLAM.class);
		    PowerMockito.mockStatic(MainSLAM.class);
			//PowerMockito.doNothing().when(ms);
			//when(ms.createSocket(Matchers.anyString(), Matchers.anyInt())).thenReturn(mockTestClientSocket);
		    
		    //Mockito.when(mainslam.createSocket(Matchers.anyString(), Matchers.anyInt())).thenReturn(mockTestClientSocket);
	}
	
	@Test
	public void main() throws Throwable {
		
		
		//PowerMockito.mockStatic(MainSLAM.class);
		
		//PowerMockito.when(Main.doSomething() ).thenReturn(something);		
		//PowerMockito.verifyNew(MainSLAM.class).withNoArguments();
		
		//PowerMockito.when(MainSLAM.slamRegisterDS(Matchers.anyString(), Matchers.anyInt(), Matchers.anyString(), Matchers.anyInt())).thenReturn(something);
	
		//Mockito.when(MainSLAM.slamRegisterDS(Matchers.anyString(), Matchers.anyInt(), Matchers.anyString(), Matchers.anyInt())).thenReturn(null);
		
		String[] args = {};
		MainSLAM.main(args);
		
		//MainSLAM.slamRegisterDS(Matchers.anyString(), Matchers.anyInt(), Matchers.anyString(), Matchers.anyInt());
		//MainSLAM.slamRegisterDS("127.0.0.1", 9001, "127.0.0.1", 9002);
		
		//        Properties prop = new Properties();
//        InputStream input = null;
//        // Using Mockito
//        final Socket socket = mock(Socket.class);
//        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);
//        
//        String filename = CONFIG_PROPERTIES;
//        input = MainSLAM.class.getClassLoader().getResourceAsStream(filename);
//        
//        //Assert.assertTrue("Message sent successfully", sendTo("localhost", "1234"));
//        Assert.assertEquals("whatever you wanted to send".getBytes(), byteArrayOutputStream.toByteArray());

		
	}

}
