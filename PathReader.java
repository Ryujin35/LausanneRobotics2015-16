//Press A, B, X, or Y to run a file. It will queue more shit if you press more things.

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

    private class path {
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

    //constructor does nothing
    public PathReader(){}

    //grabs contents of RobotPath file then puts them into an array
    void getRobotPath(String filename, ArrayList<path> pathList){
        Scanner sc = null;
        try {
            sc = new Scanner(new File(filename));
        }catch(Exception e){
            System.out.println(e);
        }
        while(sc.hasNext()){
            String temp = sc.nextLine();
            String vals[] = temp.split("\\s");
            path newPath = new path(Integer.parseInt(vals[0]),Integer.parseInt(vals[1]));
            pathList.add(newPath);
        }
    }
    
    void driveFromPath(ArrayList<path> pathList){
        path pathNode = pathList.get(0);//get item at top of list
        float motorL = pathNode.getL();
        float motorR = pathNode.getR();

        //set motor values
        motorLF.setPower(motorL);
        motorLB.setPower(motorL);
        motorRF.setPower(motorR);
        motorRB.setPower(motorR);
        
        pathList.remove(0);//the item at the top of the list is now the next set of joystick inputs
    }
    

    public void init(){
        motorLF = hardwareMap.dcMotor.get("motor_LF");
        motorLB = hardwareMap.dcMotor.get("motor_LB");
        motorRF = hardwareMap.dcMotor.get("motor_RF");
        motorRB = hardwareMap.dcMotor.get("motor_RB");
        
        //todo: get arm servos and set position of thing
    }
    
    
    private ArrayList<path> pathA, pathB, pathX, pathY;
    
    @Override
    public void loop(){
        if(gamepad2.a){
            getRobotPath("pathA", pathA);
        }else if(gamepad2.b){
            getRobotPath("pathB", pathB);
        }else if(gamepad2.x){
            getRobotPath("pathX", pathX);
        }else if(gamepad2.y){
            getRobotPath("pathY", pathY);
        }
        
        if(!pathA.isEmpty()){
            driveFromPath(pathA);
        }else if(!pathB.isEmpty()){
            driveFromPath(pathB);
        }else if(!pathX.isEmpty()){
            driveFromPath(pathX);
        }else if(!pathY.isEmpty()){
            driveFromPath(pathY);
        }else{
            telemetry.addData("The dark deed you requested has been done", "");
        }
    }
    
    
    @Override
    public void stop(){

    }

}

