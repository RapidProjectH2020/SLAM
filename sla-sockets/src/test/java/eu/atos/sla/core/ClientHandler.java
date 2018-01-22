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
import java.net.Socket;

import eu.project.rapid.common.RapidMessages;

class ClientHandler implements Runnable{
    Socket clientSocket;

    public ClientHandler(Socket clientSocket){
    	
    	System.out.println("Creating new ClientHandler to handle communication");
        this.clientSocket = clientSocket;
    }
    
    public void run() {
        try {
        	System.out.println("Starting run ClientHandler");

            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());       
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            int command = (int) in.readByte();

            long threadId = Thread.currentThread().getId();
            System.out.println("Thread Id: " + threadId + " | Incoming request type: " + command);


            //out.writeByte(RapidMessages.ERROR);
            out.writeByte(RapidMessages.OK);

            switch (command) {
                case RapidMessages.SLAM_REGISTER_DS:
                	System.out.println("[ClientHandler]-RapidMessages.SLAM_REGISTER_DS");
                case RapidMessages.SLAM_START_VM_VMM:
                	System.out.println("[ClientHandler]-RapidMessages.SLAM_START_VM_VMM");
                    out.writeLong(123); // userId
                    out.writeUTF("9.9.9.9.");//ip
            }
            out.flush();
            in.close();
            out.close();
            System.out.println("Thread Id: " + threadId + " | Closing socket..");
            clientSocket.close();


        } catch (IOException e) {
        	System.out.println("Exception:"+e);
        }        
    }
}
