/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gmu.robot.darwin.features;

/**
 *
 * @author drew
 */
public class DarwinFeature {
       private static final long serialVersionUID = 1;

    public double ballX, ballY;
    public int ballDetect, doneApproach, yelledReady, yelledKick, yelledFail;
    public double particleX[];
    public double particleY[];
    public double particleA[];
    public int playerID, role;// same value
    // robot 1: {otherRobotsX[0], otherRobotsY[0], otherRobotA[0]}
    public double poseX[];
    public double poseY[];
    public double poseA[];
    
    
    public DarwinFeature() {
        }
    
    }
