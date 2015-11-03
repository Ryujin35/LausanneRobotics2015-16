package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by ethan on 11/2/15.
 */
public class TheFinalJoystick extends DoubleJoystickBoogaloo{
    //shitty enum below
    double motorPower;
    final static double FULLPOWER = 1.00;
    final static double CRAWL = 0.05;

    DcMotor motorRF;
    DcMotor motorRB;
    DcMotor motorLF;
    DcMotor motorLB;

    public TheFinalJoystick(){}

    @Override
    public void init(){


    }

    @Override
    public void loop(){
        //I did this in a kinda stupid way but we need some kind of hierarchy or shit will be fucked
        motorPower = 0.00;
        double powerR = 0.00;
        double powerL = 0.00;

        //controller 1
        if(!gamepad1.y){
            powerR = Range.clip(gamepad1.left_stick_y, -1.00, 1.00);
            powerL = Range.clip(gamepad1.right_stick_y, -1.00, 1.00);
        }

        if(gamepad1.left_bumper) motorPower = FULLPOWER;
        else if(gamepad1.right_bumper) motorPower = -FULLPOWER;
        else if(gamepad2.dpad_up) motorPower = CRAWL;
        else if(gamepad2.dpad_down) motorPower = -CRAWL;
        else if(gamepad2.dpad_left){
            powerL = -FULLPOWER;
            powerR = FULLPOWER;
        }else if(gamepad2.dpad_right){
            powerL = FULLPOWER;
            powerR = -FULLPOWER;
        }

        //controller 2

        motorLB.setPower(motorPower);
        motorLF.setPower(motorPower);
        motorRB.setPower(motorPower);
        motorRF.setPower(motorPower);
    }

    @Override
    public void stop(){

    }

}
