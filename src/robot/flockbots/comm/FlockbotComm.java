/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package robot.flockbots.comm;

import robot.comm.Parse;
import robot.comm.Communication;
import java.io.IOException;

/**
 *
 * @author drew
 */
public class FlockbotComm extends Communication{

    
    public FlockbotComm(String host, int port, Parse parser, byte readDelayMS) throws IOException {
        super(host, port, parser, readDelayMS);
    }
    private final byte[] startmsgs = new byte[] {3,'S','S', readDelayMS};
    private final byte [] stopmsgs = {2, 'S', 'F'};
    @Override
    public void startSending() {
        
        try {
            write(startmsgs);
        } catch (IOException ex) {
            System.err.println("Failed to start sending");
        }
    }

    @Override
    public void terminateSending() {
        try {
            write(stopmsgs);
        } catch (IOException ex) {
            System.err.println("Failed to stop ending");
        }
    }
    
}
