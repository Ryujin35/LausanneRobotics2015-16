package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by ethan on 11/2/15.
 */
public class TheFinalJoystick extends OpMode{
    //shitty enum below
    double motorPower;
    double armPosition = 0;
    final static double FULLPOWER = 1.00;
    final static double CRAWL = 0.05;
    public boolean counterclockwise = false;
    public boolean clockwise = false;

    DcMotor motorRF;
    DcMotor motorRB;
    DcMotor motorLF;
    DcMotor motorLB;

    Servo boxRotateServo;
    Servo boxLowerServo;
    Servo sweeperServo;

    public TheFinalJoystick(){}

    @Override
    public void init(){
        motorRF = hardwareMap.dcMotor.get("motor_1");
        motorRB = hardwareMap.dcMotor.get("motor_2");
        motorLF = hardwareMap.dcMotor.get("motor_3");
        motorLB = hardwareMap.dcMotor.get("motor_4");

        boxRotateServo = hardwareMap.servo.get("servo_1");
        //boxLowerServo = hardwareMap.servo.get("servo_2");
        //sweeperServo = hardwareMap.servo.get("servo_3");
    }

    @Override
    public void loop(){
        //I did this in a kinda stupid way but we need some kind of hierarchy or shit will be fucked
        motorPower = 0.00;
        double powerR = 0.00;
        double powerL = 0.00;

        if(gamepad2.dpad_up) motorPower = CRAWL;
        else if(gamepad2.dpad_down) motorPower = -CRAWL;
        else if(gamepad2.dpad_left){
            powerL = -FULLPOWER;
            powerR = FULLPOWER;
        }else if(gamepad2.dpad_right){
            powerL = FULLPOWER;
            powerR = -FULLPOWER;
        }else{
            powerL = Range.clip(gamepad1.left_stick_y, -1.00, 1.00);
            powerR = Range.clip(gamepad1.right_stick_y, -1.00, 1.00);
        }

        if(motorPower == 0.00){
            motorLB.setPower(powerL);
            motorLF.setPower(powerL);
            motorRB.setPower(powerR);
            motorRF.setPower(powerR);
        }else{
            motorLB.setPower(motorPower);
            motorLF.setPower(motorPower);
            motorRB.setPower(motorPower);
            motorRF.setPower(motorPower);
        }

        if(gamepad1.x){
            rotateClockwise();
        }else if(gamepad1.y){
            rotateCounterClockwise();
        }else if(gamepad1.a){
            stopRotation();
        }

    }

    public void stopRotation(){
        clockwise = false;
        counterclockwise = false;
    }

    public void rotateClockwise(){
        armPosition = 0;
        clockwise = true;
        counterclockwise = false;
    }

    public void rotateCounterClockwise(){
        armPosition = 1;
        clockwise = false;
        counterclockwise = true;
    }

    @Override
    public void stop(){
        stopRotation();
    }

}

