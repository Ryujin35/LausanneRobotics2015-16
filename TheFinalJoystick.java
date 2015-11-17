package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by ethan on 11/2/15.
 */
public class TheFinalJoystick extends OpMode{
    //shitty servoState enum
    final static short STOP = 0;//literally does nothing
    final static short CLOCKWISE = 1;
    final static short COUNTERCLOCKWISE = 2;

    //motors for driving
    DcMotor motorRF;
    DcMotor motorRB;
    DcMotor motorLF;
    DcMotor motorLB;

    //motors for arm
    DcMotor extendArm1;
    DcMotor extendArm2;
    DcMotor retractArm;
    DcMotor raiseArm;

    //servos for generic shit
    Servo boxRotateServo;
    Servo putterServo;
    Servo trapdoorServo;
    Servo sweeperServo;

    //color sensor servo doesn't need to be here

    short sweeperState = 0;

    private void driveServoFromState(Servo servo, short state){
        if(state == CLOCKWISE){
            //servo.setPosition(Range.clip(servo.getPosition() + 0.00001, 0, 1));
            servo.setPosition(0);
        }else if(state == COUNTERCLOCKWISE){
//          //servo.setPosition(Range.clip(servo.getPosition() - 0.00001, 0, 1));
            servo.setPosition(1);
        }else if(state == STOP){
            servo.setPosition(0.5);
        }
    }

    public TheFinalJoystick(){}

    @Override
    public void init(){
        motorRF = hardwareMap.dcMotor.get("motorRF");
        motorRB = hardwareMap.dcMotor.get("motorRB");
        motorLF = hardwareMap.dcMotor.get("motorLF");
        motorLB = hardwareMap.dcMotor.get("motorLB");

        extendArm1 = hardwareMap.dcMotor.get("extend1");
        extendArm2 = hardwareMap.dcMotor.get("extend2");

        retractArm = hardwareMap.dcMotor.get("retract");

        raiseArm = hardwareMap.dcMotor.get("raise");

        boxRotateServo = hardwareMap.servo.get("boxRotate");
        sweeperServo = hardwareMap.servo.get("sweeper");
        trapdoorServo = hardwareMap.servo.get("trapdoor");
        putterServo = hardwareMap.servo.get("putter");

        boxRotateServo.setPosition(0.5);
        sweeperServo.setPosition(0.5);
    }

    @Override
    public void loop(){
        //I did everything in a kinda stupid way but we need some kind of hierarchy or shit will be fucked

        /*Buttons that do stuff:
        gamepad 1 joysticks (both)
        gamepad 1 X
        gamepad 1 bumpers (both)
        gamepad 2 dpad (all)
        gamepad 2 bumpers (both)
        gamepad 2 x/y/a/b
        */
        double powerR;
        double powerL;

        if(gamepad2.dpad_up){
            //extend
        }else if(gamepad2.dpad_down){
            //retract
        }

        if(gamepad2.dpad_left){
            boxRotateServo.setPosition(0);
        }else if(gamepad2.dpad_right){
            boxRotateServo.setPosition(1);
        }else{
            boxRotateServo.setPosition(0.5);
        }

        powerL = Range.clip(gamepad1.left_stick_y, -1.00, 1.00);
        powerR = Range.clip(gamepad1.right_stick_y, -1.00, 1.00);

        motorLB.setPower(powerL);
        motorLF.setPower(powerL);
        motorRB.setPower(powerR);
        motorRF.setPower(powerR);

        //set state of boxLowerServo
        if(gamepad2.y){
            //do some shit
        }else if(gamepad2.a){
            //opposite
        }else{
            //stahp
        }

        //boxRotateServo
        if(gamepad2.x){
            //do some shit
        }else if(gamepad2.b){
            //opposite
        }else{
            //stahp
        }

        //sweeperServo
        if(gamepad1.x) sweeperState = STOP;
        else if(gamepad1.right_bumper || gamepad2.right_bumper) sweeperState = CLOCKWISE;
        else if(gamepad1.left_bumper || gamepad2.left_bumper) sweeperState = COUNTERCLOCKWISE;

        driveServoFromState(sweeperServo, sweeperState);

        telemetry.addData("Sweeper Position", sweeperServo.getPosition());
        telemetry.addData("Rotate Servo Position", boxRotateServo.getPosition());
    }

    @Override
    public void stop(){

    }

}

