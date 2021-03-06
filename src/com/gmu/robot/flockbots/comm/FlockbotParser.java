/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gmu.robot.flockbots.comm;

import com.gmu.robot.comm.DefaultParser;

/**
 *
 * @author drew
 */
public class FlockbotParser extends DefaultParser{

    public enum IRSensorLocation {
        RIGHTFORWARD,// 0
        RIGHT,//1
        FRONT,//2
        LEFT,//3
        LEFTFRONT;//4
    }
    private final short irsensors[] = new short[5];
    private byte lowBump, highBump;
    // left and right wheel speed (relative to each flockbot
    private short lws, rws,
            // total wheel speed (lws + rws) / 2
            tws,
            // number of left and right wheel ticks resets to zero when motion is stoped
            lwwt, rwwt;
    private final Object lock = new Object[0];
    
    
    @Override
    public void setInput(byte[] input) {
        super.setInput(input);
        System.out.println(input.toString() + " length " + input.length);
        // I have to parse out each of the values in the 'S' 'P'
        if (input[0] == 'S' && input[1] == 'P') {
            synchronized(lock) {
                int curpos = 4;
                for (int i = 0; i < irsensors.length; i++) {
                    irsensors[i] = twoBytesToShort(input[curpos + 1], input[curpos]);// since little endian
                    curpos += 2;// go to the next ir sensor
                }
                lowBump = input[curpos++];
                highBump = input[curpos++];
                lws = twoBytesToShort(input[curpos + 1], input[curpos]);
                curpos += 2;
                rws = twoBytesToShort(input[curpos + 1], input[curpos]);
                curpos += 2;
                tws = twoBytesToShort(input[curpos + 1], input[curpos]);
                curpos += 2;
                lwwt = twoBytesToShort(input[curpos + 1], input[curpos]);
                curpos += 2;
                rwwt = twoBytesToShort(input[curpos + 1], input[curpos]);
                curpos += 2;
            }
            
        }
    }
    
    /**
     * 
     * @param b1 msb
     * @param b2 lsb
     * @return 
     */
    private short twoBytesToShort(byte b1, byte b2) {
          return (short) ((b1 << 8) | (b2 & 0xFF));
    }

    @Override
    public String toString() {
        /*StringBuilder buf = new StringBuilder();
        buf.append("IR sensors:\n");
        for (int i = 0 ; i < irsensors.length; i++) {
            buf.append(IRSensorLocation.values()[i].toString()).append(": ").append(irsensors[i]).append("\n");
        }
        buf.append("Low Bump: ").append(lowBump);
        buf.append("  High Bump:").append(highBump).append("\n");
        buf.append("Total wheel speed: ").append(tws).append("\n");
        buf.append("Wheel speed- Right: ").append(rws).append("  Left: ").append(lws).append("\n");
        buf.append("Wheel ticks - Right: ").append(rwwt).append("  Left: ").append(lwwt).append("\n");
        
        
        return buf.toString();
        */
        return "";
    }

    
    
    
    @Override
    public String getInput() {
       // must figure out what to do...
        return toString();
    }

    public byte getLowBump() {
       synchronized(lock){ return lowBump;}
    }

    public byte getHighBump() {
        synchronized(lock) {return highBump; }
    }

    public short getLws() {
         synchronized(lock) {return lws; }
    }

    public short getRws() {
         synchronized(lock) {return rws; }
    }

    public short getTws() {
         synchronized(lock) {return tws; }
    }

    public short getLwwt() {
         synchronized(lock) {return lwwt; }
    }

    public short getRwwt() {
         synchronized(lock) {return rwwt; }
    }
    
    public short getForwardIR() {
        synchronized(lock) { return irsensors[IRSensorLocation.FRONT.ordinal()]; }
    }
    
    public short getIRByID(IRSensorLocation irloc) {
        synchronized(lock) { return irsensors[irloc.ordinal()]; }
    }
    
    
}
