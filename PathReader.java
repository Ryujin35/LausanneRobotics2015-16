package com.qualcomm.ftcrobotcontroller.opmodes;


import android.content.Context;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
//import motors and shit

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Created by ethan on 10/23/15.
 */
public class PathReader extends OpMode {

    DcMotor motorLF, motorLB, motorRF, motorRB;

    protected class path {
        private float L;
        private float R;

        public path(float _L, float _R){
            L = _L;
            R = _R;
        }

        public float getL(){
            return L;
        }
        public float getR(){
            return R;
        }
    }

    float motorL = 0, motorR = 0;

    protected ArrayList<path> robotPath = null;
    protected ArrayList<path> currentPath = null;
    protected int cpProgress = 0; //what line in the current path the program is on
    protected boolean cpIsDone = true;

    //constructor does nothing
    public PathReader(){}

    //grabs contents of RobotPath file then puts them into an array
    void getRobotPath(){
        Scanner sc = null;
        try {
            sc = new Scanner(new File("robotPath"));
        }catch(Exception e){
            System.out.println(e);
        }
        while(sc.hasNext()){
            String temp = sc.nextLine();
            String vals[] = temp.split("\\s");
            path newPath = new path(Integer.parseInt(vals[0]),Integer.parseInt(vals[1]));
            robotPath.add(newPath);
        }
    }
    ArrayList<path> getRobotPathFromFile(String s){
        ArrayList<path> ptemp = new ArrayList<path>();
        Scanner sc = null;
        try {
            sc = new Scanner(s); //dunno how this works, this is just a guess
        }catch(Exception e){
            System.out.println(e);
        }
        while(sc.hasNext()){
            String temp = sc.nextLine();
            String vals[] = temp.split("\\s");
            path newPath = new path(Integer.parseInt(vals[0]),Integer.parseInt(vals[1]));
            ptemp.add(newPath);
        }
        return ptemp;
    }
    public void runPath(ArrayList<path> pList) { //function to set up path to be run by the loop function
        currentPath = pList;
        cpProgress = 0;
        cpIsDone = false;
    }

    public void init(){
        motorLF = hardwareMap.dcMotor.get("motor_LF");
        motorLB = hardwareMap.dcMotor.get("motor_LB");
        motorRF = hardwareMap.dcMotor.get("motor_RF");
        motorRB = hardwareMap.dcMotor.get("motor_RB");

        getRobotPath();
    }

    @Override
    public void loop(){
        /*if(!robotPath.isEmpty()){
            path pathNode = robotPath.get(0);//don't think this will work
            motorL = pathNode.getL();
            motorR = pathNode.getR();

            //set motor values
            motorLF.setPower(motorL);
            motorLB.setPower(motorL);
            motorRF.setPower(motorR);
            motorRB.setPower(motorR);
        }*/

        //rewriting this for you ethan for compatability with JSAutonomous, might not work so care
        if((!currentPath.isEmpty()) || cpIsDone) {
            if(robotPath.size() == cpProgress - 1) {//idk if it should be -1, i think it should just if theres an error yeah
                //cpIsDone = true; //will be done in child class
                currentPath = null;
                cpProgress = 0;
            } else {
                path pathNode = robotPath.get(cpProgress);//don't think this will work
                motorL = pathNode.getL();
                motorR = pathNode.getR();

                motorLF.setPower(motorL);
                motorLB.setPower(motorL);
                motorRF.setPower(motorR);
                motorRB.setPower(motorR);
                cpProgress++; //hopefully this loops around the same time as path reader, otherwise it might not be on a node for the correct time
            }
        }

    }
    public void travelMeters(float j) {
        //we need to hardcode this
    }
    public void rotateDegrees(int j) {
        //we need to hardcode this
    }
    @Override
    public void stop(){

    }

}

