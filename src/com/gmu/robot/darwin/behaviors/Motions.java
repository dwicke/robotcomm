/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gmu.robot.darwin.behaviors;

import com.google.gson.Gson;
import com.gmu.robot.behaviors.CommandMotions;

/**
 *
 * @author drew
 */
public class Motions implements CommandMotions {
     private static final long serialVersionUID = 1;
   
    public static class Pose {
        double x,y,a;
        public Pose() {}
        public Pose(double x, double y, double a) {
            this.x = x;
            this.y = y;
            this.a = a;
            }
        }
    
    static final Gson gson = new Gson();
    public static Motions GOTO_BALL = new Motions("gotoBall", "",1);
    public static Motions APPROACH_BALL = new Motions("approachBall", "",2);
    public static Motions WALK_FORWARD = new Motions("walkForward", "",3);
    public static Motions MOVE_X = new Motions("moveX", "",4);
    public static Motions MOVE_Y = new Motions("moveY", "",5);
    public static Motions MOVE_THETA = new Motions("moveTheta", "",6);
    public static Motions STOP = new Motions("stop", "",7);
    public static Motions SIDE_KICK_RIGHT = new Motions("kickRight","",8);
    public static Motions SIDE_KICK_LEFT = new Motions("kickLeft","",9);
    public static Motions KICK_BALL = new Motions("kickBall","",10);
    
    // used as states in the robot to know when to transition
    public static Motions YELL_READY = new Motions("yellReady", "",11);
    public static Motions CLEAR_READY = new Motions("clearReady", "",12);
    public static Motions YELL_KICK = new Motions("yellKick", "",13);
    public static Motions CLEAR_KICK = new Motions("clearKick", "",14);
    
    
    public static Motions YELL_FAIL = new Motions("yellFail", "",15);
    
    public static Motions getGotoPose(double x, double y, double theta) {
        return new Motions("gotoPose", gson.toJson(new Pose(x,y,theta)),16);
    }
    
    public static Motions getBodyApproachTargetMotion(double x, double y, double theta) {
        return new Motions("approachTarget", gson.toJson(new Pose(x, y, theta)),17);
    }
    
    
    DoMotion motion;
    int id;

    private Motions(String action, String args, int id) {
        motion = new DoMotion(action, args);
        this.id = id;
        }
    
    
    
    public class DoMotion {
        String action;
        String args;
        public DoMotion(String action, String args) {
            this.action = action;
            this.args = args;
            }
        }
    
    
    @Override
    public byte[] command(byte speed) {
        System.err.println("Sending command in Motions.java: " + (gson.toJson(motion) + "\n"));
        return (gson.toJson(motion) + "\n").getBytes();
        }
    
    public DoMotion getMotion() {
        return motion;
    }
    public int getID() {
        return id;
    }
    
    }
