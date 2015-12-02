package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by ethan on 11/2/15.
 */
public class TheFinalJoystick extends OpMode{
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

    //servos
    Servo boxRotateServo;
    Servo putterServo;
    Servo trapdoorServo;
    Servo sweeperServo;

    //color sensor servo doesn't need to be here
    
    static double sweeperVal = 0.5;

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
        
        //raise/lower arm using dpad up/down
        if(gamepad2.dpad_up){
            raiseArm.setPower(1.0);
        }else if(gamepad2.dpad_down){
            raiseArm.setPower(-1.0);
        }else{
            raiseArm.setPower(0.0);
        }
        
        //rotate box using dpad L/R
        if(gamepad2.dpad_left){
            boxRotateServo.setPosition(0);
        }else if(gamepad2.dpad_right){
            boxRotateServo.setPosition(1);
        }else{
            boxRotateServo.setPosition(0.5);
        }
        
        final double extRetPwr = 0.5;
        
        //extend/retract arm using joystick
        double joystickPwr = Range.clip(gamepad2.left_stick);
        if(gamepad2.left_stick_y > 0){
            extendArm1.setPower(extRetPwr);
            extendArm2.setPower(extRetPwr);
            retractArm.setPower(0);
        }else if(gamepad2.left_stick_y < 0){
            extendArm1.setPower(0);
            extendArm2.setPower(0);
            retractArm.setPower(extRetPwr);
        }else{
            extendArm1.setPower(0);
            extendArm2.setPower(0);
            retractArm.setPower(0);
        }

        //drive robot around using gamepad 1 joysticks
        double powerL = Range.clip(gamepad1.left_stick_y, -1.00, 1.00);
        double powerR = Range.clip(gamepad1.right_stick_y, -1.00, 1.00);
        motorLB.setPower(powerL);
        motorLF.setPower(powerL);
        motorRB.setPower(powerR);
        motorRF.setPower(powerR);
        
        //control trap door with y and a
        if(gamepad2.y){
            trapdoorServo.setPosition(1);
        }else if(gamepad2.a){
            trapdoorServo.setPosition(0);
        }else{
            trapdoorServo.setPosition(0.5);
        }

        //control putter with x and b
        if(gamepad2.x){
            putterServo.setPosition(0);
        }else if(gamepad2.b){
            putterServo.setPosition(1);
        }else{
            putterServo.setPosition(0.5);
        }

        //control state of sweeper using bumpers
        if(gamepad1.x) sweeperVal = 0.5;
        else if(gamepad1.right_bumper || gamepad2.right_bumper) sweeperVal = 1;
        else if(gamepad1.left_bumper || gamepad2.left_bumper) sweeperVal = 0;
        sweeperServo.setPosition(sweeperVal);
    }

    @Override
    public void stop(){

    }

}

