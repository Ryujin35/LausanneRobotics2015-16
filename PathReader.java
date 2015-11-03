package com.qualcomm.ftcrobotcontroller.opmodes;


import android.content.Context;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Created by ethan on 10/23/15.
 */
public class PathReader extends OpMode {

    float xVal = 0, yVal = 0;
    int posInSteps = 0;

    private ArrayList<PathMaker.path> robotPath;

    public PathReader(){}

    void getRobotPath(){
        String contents = "";
        //get dem contents
        Scanner sc = new Scanner(contents);
        while(sc.hasNext()){
            sc.nextLine();
        }
    }


    public void init(){

    }

    @Override
    public void loop(){
        if(!robotPath.isEmpty()){
            final PathMaker.path pathNode = robotPath.get((robotPath.size()));
            xVal = pathNode.getX();
            yVal = pathNode.getY();
        }
    }

    @Override
    public void stop(){

    }

}
