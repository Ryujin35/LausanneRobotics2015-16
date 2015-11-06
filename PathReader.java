package com.qualcomm.ftcrobotcontroller.opmodes;


import android.content.Context;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import motors and shit

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Created by ethan on 10/23/15.
 */
public class PathReader extends OpMode {

    float xVal = 0, yVal = 0;

    private ArrayList<PathMaker.path> robotPath;

    //constructor does nothing
    public PathReader(){}
    
    //grabs contents of RobotPath file then puts them into an array
    void getRobotPath(){
        Scanner sc = new Scanner(new File("robotPath"));
        while(sc.hasNext()){
            String temp = sc.nextLine();
            String vals[] = temp.split("\\s");
            path newPath = new path(vals[0],vals[1]);
            robotPath.add(newPath);
        }
    }


    public void init(){
        getRobotPath();
    }

    @Override
    public void loop(){
        if(!robotPath.isEmpty()){
            PathMaker.path pathNode = new path(0,0);
            PathMaker.path pathNode = robotPath.get((robotPath.size()));//don't think this will work
            xVal = pathNode.getX();
            yVal = pathNode.getY();
            //set motor values
        }
    }

    @Override
    public void stop(){

    }

}
