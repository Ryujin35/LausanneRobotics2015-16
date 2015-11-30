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
    private ArrayList<path> robotPathA;
    private ArrayList<path> robotPathB;
    private ArrayList<path> robotPathX;
    private ArrayList<path> robotPathY;

    public void makePath(){
        path newPathPoint = new path(gamepad1.left_stick_y, -gamepad1.right_stick_y);
        robotPath.add(newPathPoint);
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
            //save to file a
        }else if(gamepad2.b){
            //save to file b
        }else if(gamepad2.x){
            //save to file x
        }else if(gamepad2.y){
            //save to file y
        }
    }

    @Override //not even sure what these do but they're everywhere
    public void stop(){
        /*Convert robotPath into a string, then  save it to a file
        Formatting:
            L1 R1
            L2 R2
            L3 R3
            etc.*/
        String filename = "robotPath";
        String toSave = "";

        for(int i = 0; i < robotPath.size(); i++){
            path currentPath = robotPath.get(i);
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
    } //We also need this even if it does nothing
}

