package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by ethan on 10/5/15.
 */
public class DoubleJoystickBoogaloo extends OpMode {
    // Right/Left, Front/Back
    DcMotor motorRF;
    DcMotor motorRB;
    DcMotor motorLF;
    DcMotor motorLB;

    public void runFromDJB(){
        float motorL;
        float motorR;

        motorL = ScalePower(gamepad1.left_stick_y);
        motorR = ScalePower(gamepad1.right_stick_y);

        motorLF.setPower(motorL);
        motorLB.setPower(motorL);
        motorRF.setPower(motorR);
        motorRB.setPower(motorR);

        writeToTelemetry(motorL, motorR); //comment out when done testing
    }

    public float ScalePower(float power){
        Range.clip(power, -1.00, 1.00);
        final float conversionFactor = 1f;
        return power*conversionFactor;
    }

    public void writeToTelemetry(float ml, float mr){
        telemetry.addData("Motor L: ", ml);
        telemetry.addData("Motor R: ", mr);
    }

    public DoubleJoystickBoogaloo(){
        //it aint do shizzle mah nizzle
    }

    @Override
    public void init(){
        motorRF = hardwareMap.dcMotor.get("motor_1");
        motorRB = hardwareMap.dcMotor.get("motor_2");
        motorLF = hardwareMap.dcMotor.get("motor_3");
        motorLB = hardwareMap.dcMotor.get("motor_4");

    }
    /*two joystick command
    motorLF.setPower(ScalePower(Range.clip(gamepad1.left_stick_y)));
    motorLB.setPower(ScalePower(Range.clip(gamepad1.left_stick_y)));
    motorRB.setPower(ScalePower(Range.clip(gamepad1.right_stick_y)));
    motorRF.setPower(ScalePower(Range.clip(gamepad1.right_stick_y)));
    * */
    @Override
    public void loop(){ runFromDJB(); }

    @Override
    public void stop(){

    }
}

