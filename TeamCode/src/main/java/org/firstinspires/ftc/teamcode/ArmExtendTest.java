package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "arm extend test", group = "TeleOp")
public class ArmExtendTest extends LinearOpMode {
    DcMotorEx armExtend;
    Servo wrist;
    Servo leftGrabber;
    Servo rightGrabber;
    double power;
    public void runOpMode() throws InterruptedException {

        armExtend = hardwareMap.get(DcMotorEx.class, "armExtend");

        leftGrabber = hardwareMap.get(Servo.class, "leftGrabber");
        rightGrabber = hardwareMap.get(Servo.class, "rightGrabber");
        wrist = hardwareMap.get(Servo.class, "wrist");

        armExtend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        armExtend.setPower(0);

        power = 0;
        armExtend.setDirection(DcMotorSimple.Direction.FORWARD);
        armExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armExtend.setTargetPosition(0);
        armExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        waitForStart();

        while(opModeIsActive()) {
            power = -gamepad1.left_stick_y;
            armExtend.setPower(power);

            if (gamepad1.a) { //X button
                power = 0.3;
                armExtend.setPower(power);
                armExtend.setTargetPosition(0); //int value is the tick value
            }

            if (gamepad1.b) { //circle
                power = 0.3;
                armExtend.setPower(power);
                armExtend.setTargetPosition(55); //int value is the tick value
            }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //grabbing

            //left Grabber
            if(gamepad1.left_bumper){
                leftGrabber.setPosition(.33);
            }
            if(!gamepad1.left_bumper){
                leftGrabber.setPosition(0);
            }

            //right Grabber
            if(gamepad1.right_bumper){ //square
                rightGrabber.setPosition(.33);
            }
            if(!gamepad1.right_bumper){ //triangle
                rightGrabber.setPosition(0);
            }

            //Wrist



            telemetry.addData("Extend power:", power);
            telemetry.update();

        }
    }

}
