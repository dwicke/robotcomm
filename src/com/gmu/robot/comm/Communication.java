/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gmu.robot.comm;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A threadable bi-directional socket communication object.
 * @author drew
 */
public abstract class Communication implements Runnable{
    
    protected Parse myParser;
    protected DataOutputStream outd;
    protected DataInputStream in;
    protected byte readDelayMS;
    protected boolean shouldStop = false;
    protected Socket socket;
    protected int port;
    protected String host;
    protected volatile boolean connected = false;
    protected boolean client = true;
    
    /**
     * create Communication a client style communication object.
     * @param host the ip address of the host you are going to connect to.
     * @param port the port to connect with
     * @param parser parser to store/parse received msgs
     * @param readDelayMS how long to wait to get a new msg from server
     * @throws IOException  thrown if socket creation goes bad.
     */
    public Communication(String host, int port, Parse parser, byte readDelayMS) throws IOException {
        
        client = true;
        this.host = host;
        this.port = port;
        myParser = parser;
        this.readDelayMS = readDelayMS;
        
    }
    
    
    /**
     * create Communication a server style communication object.
     * @param port the port to connect with
     * @param parser parser to store/parse received msgs
     * @param readDelayMS how long to wait to get a new msg from server
     * @throws IOException  thrown if socket creation goes bad.
     */
    public Communication(int port, Parse parser, byte readDelayMS) throws IOException {
        this.port = port;
        myParser = parser;
        this.readDelayMS = readDelayMS;
        client = false;
        
    }
    
    protected void makeClientConnection() throws IOException {
        
        System.err.println("Making client connection");
        socket = new Socket(host, port);
        System.err.println("Socket" + socket);
        outd = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
        System.err.println("In and out are created.");
        
        connected = true;
    }
    
     void makeServerConnection() throws IOException {
        final ServerSocket serv = new ServerSocket(port);
        socket = serv.accept();
        outd = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
        connected = true;
                    
                
    }
    
    public Parse getParser() {
        return myParser;
    }
    
    
    public String getIP() {
        return host;
    }
    
    public int getPort() {
        return port;
    }
    
    public void stopComm() {
        shouldStop = true;
    }
    
    
    public boolean isConnected() {
        return connected;
    }
    
    /**
     * Write the string to the socket
     * @param value 
     */
    public void write(byte[] value) throws IOException {
        System.out.println(new String(value));

        outd.write(value);
        outd.flush();
        System.out.println("finished sending");
    }
    
    
    
    /**
     * Reads data from socket and sets the data to the parser
     * 
     */
    protected void read() {
        try {
            //System.out.println("Starting to read");

            // number of following bytes to read
            byte followingBytes = in.readByte();

            //System.out.println(followingBytes);
            byte[] bytesRead = new byte[followingBytes];
            in.read(bytesRead);
            
            myParser.setInput(bytesRead);    
            System.out.print(myParser.toString());
        } catch (IOException ex) {
            System.out.println("Error reading line");
        }
        
        
        
    }
    
    /**
     * This method is called to tell the server to start sending info to me
     */
    public abstract void startSending();
    
    /**
     * This method tells the server to stop sending info to me.
     */
    public abstract void terminateSending();
    
    
    @Override
    public void run() {
        if (client) {
            try {
                makeClientConnection();
            } catch (IOException ex) {
                System.err.println("error creating client connection in Communication.java" + ex);
            }
        }else {
            try {
                makeServerConnection();
            } catch (IOException ex) {
                System.err.println("error creating server connection in Communication.java" + ex);
            }
        }
        
        // must have a way to ask for info from the host I have connected to
        startSending();
        
        while(!shouldStop) {
            read();
        }
        try {
            // shutdown the socket
            terminateSending();
            socket.close();
        } catch (IOException ex) {
            System.err.println("Failed to close connection to socket");
        }
        
    }
    
}
