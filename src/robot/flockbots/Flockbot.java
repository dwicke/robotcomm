/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package robot.flockbots;

import java.io.IOException;
import robot.Robot;
import robot.behaviors.CommandMotions;
import robot.comm.Communication;
import robot.comm.Parse;
import robot.flockbots.comm.FlockbotComm;
import robot.flockbots.comm.FlockbotParser;

/**
 * Models the actual flockbot has the comm. to send and receive msgs.
 * @author drew
 */
public class Flockbot implements Robot{

    private Communication comm;
    private final FlockbotParser parser = new FlockbotParser();
    private final byte readDelayMS = 30;
    private Thread th;
    private final byte speed;
    
    public Flockbot(String host, int port, byte speed) {
        this.speed = speed;
        try {
            // must connect to the flockbot in a seperate thread I may want to add parser
            // to the FlockbotEnum... depending on if I need to parse the sensor data
            // differently for each bot.
            comm = new FlockbotComm(host, port, parser, readDelayMS);
            th = new Thread(comm);
            th.start();
        } catch (IOException ex) {
            System.out.println("Error creating FlockbotComm.");
        }
    }
    
    @Override
    public void sendCommand(CommandMotions cms) {
        try {
            comm.write(cms.command(speed));
        } catch (IOException ex) {
            System.out.println("Error sending command " + cms.toString() + " to " + comm.getIP() + ":" + comm.getPort());
        }
        
    }
    
    public void sendCommand(CommandMotions cms, byte customSpeed) {
        try {
            comm.write(cms.command(customSpeed));
        } catch (IOException ex) {
            System.out.println("Error sending command " + cms.toString() + " to " + comm.getIP() + ":" + comm.getPort());
        }
    }
    
    @Override
    public Parse getParser() {
        return parser;
    }
    
    
}
