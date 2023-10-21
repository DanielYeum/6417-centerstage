package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class TestTeleOp extends LinearOpMode {
    DcMotorEx leftFront, leftBack, rightBack, rightFront;
    @Override
    public void runOpMode() throws InterruptedException {
        //Define the names on the screen to assign motors to the hub
        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        leftBack = hardwareMap.get(DcMotorEx.class, "leftBack");
        rightBack = hardwareMap.get(DcMotorEx.class, "rightBack");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Uses ticks to run to the position
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBack.setTargetPosition(500); //int value is the ticks

        //Uses set power to run the motors AND track the position; Run endlessly with constant velocity
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setPower(0.5);

        //
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Completely resets the encoder; used to make sure no encoder is left
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);



        while(opModeInInit()) {

        }

        waitForStart();

        while(opModeIsActive()) {

        }
    }
}
