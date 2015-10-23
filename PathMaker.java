/* How to use:
    1. Press and hold B
    2. Drive the robot around using the left joystick until you get wherever you need to go
    3. Release B so you don't have a bunch of stuff that does nothing
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
public class PathMaker extends OpMode {

    //Data structure for a single point on the robot's path, divided into x and y components (of joystick)
    public class path {
        private float x;
        private float y;

        public path(float _x, float _y){
            x = _x;
            y = _y;
        }

        public float getX(){
            return x;
        }
        public float getY(){
            return y;
        }
    }

    private ArrayList<path> robotPath; //array of joystick values
    private JoystickL stickL; //lets you drive

    public void makePath(){
        path newPathPoint = new path(gamepad1.left_stick_x, gamepad1.left_stick_y);
        robotPath.add(newPathPoint);
    }


    public PathMaker(){} //We need a constructor for some reason even though everything goes in init() usually

    public void init(){
        robotPath = new ArrayList<path>();
        stickL = new JoystickL();
    }

    @Override
    public void loop(){
        if(gamepad1.b == true){
            makePath();
            stickL.driveFromJoystickL();
        }
    }

    @Override //not even sure what these do but they're everywhere
    public void stop(){
        /*Convert robotPath into a string, then  save it to a file
        Formatting:
            X1,Y1
            X2,Y2
            X3,Y3
            etc.*/
        String filename = "robotPath";
        String toSave = "";

        for(int i = 0; i < robotPath.size(); i++){
            path currentPath = robotPath.get(i);
            toSave += currentPath.getX();
            toSave += ',';
            toSave += currentPath.getY();
            toSave += '\n';
        }
        Context ctx = null;//null because there was a warning

        try{
            FileOutputStream saveFile = ctx.openFileOutput(filename, Context.MODE_PRIVATE);
            //file is private; i don't want this but it's hard to change for some reason

            saveFile.write(toSave.getBytes());
            saveFile.close();

        }catch (Exception e){//gotta catch 'em all
            e.printStackTrace();
        }
    } //We also need this even if it does nothing
}
