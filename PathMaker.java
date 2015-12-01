/* How to use:
    1. Press and hold A/B/X/Y on gamepad 2
    2. Drive the robot around using gamepad 1
    3. Release A/B/X/Y to stop recording at any time
    4. Stop program to save path
 */

//todo: allow use of multiple files
//todo: add function that writes things to telemetry


package com.qualcomm.ftcrobotcontroller.opmodes;


import android.content.Context;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by ethan on 10/13/15.
 */
public class PathMaker extends DoubleJoystickBoogaloo {

    //Data structure for a single point on the robot's path
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

    private ArrayList<path> robotPath; //array of joystick values
    private ArrayList<path> pathA, pathB, pathX, pathY;

    private void makePath(ArrayList<path> pathList){
        path newPathPoint = new path(gamepad1.left_stick_y, -gamepad1.right_stick_y);
        pathList.add(newPathPoint);
    }
    
    private void savePathToFile(String filename, ArrayList<path> pathList){
        /*Convert robotPath into a string, then  save it to a file
        Formatting:
            L1 R1
            L2 R2
            L3 R3
            etc.*/
        String toSave = "";

        for(int i = 0; i < pathList.size(); i++){
            path currentPath = pathList.get(i);
            toSave += currentPath.getL();
            toSave += ' ';
            toSave += currentPath.getR();
            toSave += '\n';
        }
        Context ctx = null;//null because there was a warning

        //todo: make this work for A/B/X/Y
        try{
            FileOutputStream saveFile = ctx.openFileOutput(filename, Context.MODE_PRIVATE);

            saveFile.write(toSave.getBytes());
            saveFile.close();

        }catch (Exception e){//gotta catch 'em all
            e.printStackTrace();
        }
    }


    public PathMaker(){} //We need a constructor for some reason even though everything goes in init() usually


    @Override
    public void init(){
        robotPath = new ArrayList<path>();
    }


    @Override
    public void loop(){
        runFromDJB();
        if(gamepad2.a){
            makePath(pathA);
            
        }else if(gamepad2.b){
            makePath(pathB);
            
        }else if(gamepad2.x){
            makePath(pathX);
            
        }else if(gamepad2.y){
            makePath(pathY);
            
        }
    }

    @Override //not even sure what these do but they're everywhere
    public void stop(){
        savePathToFile("pathA", pathA);
        savePathToFile("pathB", pathB);
        savePathToFile("pathX", pathX);
        savePathToFile("pathY", pathY);
    }
}

