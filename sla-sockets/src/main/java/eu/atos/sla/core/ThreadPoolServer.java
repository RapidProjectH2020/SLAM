/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.core;

import eu.project.rapid.common.RapidMessages;
import eu.project.rapid.common.RapidUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ThreadPoolServer implements Runnable {

    private final static Logger logger = Logger.getLogger(ThreadPoolServer.class);

    private int serverPort;
    private ServerSocket serverSocket = null;

    static List<String> vmmIPList = new ArrayList<>();

    private boolean isStopped = false;
    private Thread runningThread = null;
    private ExecutorService threadPool = null;

    ThreadPoolServer(int serverPort, int threadNumber,
                     String animationServerIp, int animationServerPort) {
        this.serverPort = serverPort;
        RapidUtils.sendAnimationMsg(animationServerIp, animationServerPort, String.valueOf(RapidMessages.AnimationMsg.SLAM_REGISTER_DS));
        threadPool = Executors.newFixedThreadPool(threadNumber);
    }

    public void run() {
        synchronized (this) {
            this.runningThread = Thread.currentThread();
        }
        openServerSocket();
        while (!isStopped()) {
            Socket clientSocket;
            try {
                clientSocket = this.serverSocket.accept();
                logger.debug("Socket at: " + this.serverPort+ " received communication");

            } catch (IOException e) {
                if (isStopped()) {
                    logger.error("Server Stopped.", e);
                    break;
                }
                throw new RuntimeException("Error accepting client connection", e);
            }
            this.threadPool.execute(new WorkerRunnable(clientSocket));
        }
        this.threadPool.shutdown();
        logger.debug("Thread pool has been shutdown...");
    }


    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop() {
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
            logger.debug("Socket server started at port for listening at: " + this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open server port", e);
        }
    }
}
