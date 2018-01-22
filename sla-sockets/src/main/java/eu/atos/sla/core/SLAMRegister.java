/*******************************************************************************
 * Copyright (c) 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import eu.project.rapid.common.RapidMessages;

public class SLAMRegister {
	
    private final static Logger logger = Logger.getLogger(SLAMRegister.class);
    
    void slamRegisterDS()
    {
    	
    }
	boolean slamRegisterDS(String serverIp, int serverPort, String dirServerIP, int dirServerPort) {
		Socket vmmSocket;
		boolean result = false;
		try 
		{
			vmmSocket = createSocket(dirServerIP, dirServerPort);
		
			OutputStream oStream = vmmSocket.getOutputStream();
			String name = (oStream.getClass()).getName();
			ObjectOutputStream vmmOut = new ObjectOutputStream(vmmSocket.getOutputStream());
			vmmOut.flush();
			InputStream iStream = vmmSocket.getInputStream();
			ObjectInputStream vmmIn = new ObjectInputStream(vmmSocket.getInputStream());
		
			logger.debug("[Flow] RapidMessages.SLAM_REGISTER_DS");
			vmmOut.writeByte(RapidMessages.SLAM_REGISTER_DS);
			vmmOut.writeUTF(serverIp);
			vmmOut.writeInt(serverPort);
			vmmOut.flush();
			byte status = vmmIn.readByte();
			logger.debug("[Flow] RapidMessages.SLAM_REGISTER_DS status: " + status);
			if (status == RapidMessages.OK) {
				logger.debug("SLAM has been registered successfully in DS!");
				result=true;
			} else {
				logger.error("SLAM registration error to DS");
			}
			vmmOut.close();
			vmmIn.close();
			vmmSocket.close();
		
		} catch (IOException e) {
			logger.error("SLAM registration error to DS", e);
		}
	
		return result;
	}
	
	protected Socket createSocket(String dirServerIP, int dirServerPort) throws UnknownHostException, IOException 
	{
		return new Socket(dirServerIP, dirServerPort);
	}


}
