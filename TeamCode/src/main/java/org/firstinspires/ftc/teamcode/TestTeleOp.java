package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoControllerEx;

import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Grabber;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;


@TeleOp(name = "TestTeleOp",group = "TeleOp")
public class TestTeleOp extends LinearOpMode {
    Arm arm;
    Drivetrain drivetrain;
    Grabber leftGrabber;
    Grabber rightGrabber;
    Wrist wrist;

    double leftVertControl;
    double leftHorzControl;
    double rotate, power;



    @Override
    public void runOpMode() throws InterruptedException {
        power = 0;

        drivetrain = new Drivetrain(hardwareMap);
        arm = new Arm(hardwareMap);
        leftGrabber = new Grabber(hardwareMap);
        rightGrabber = new Grabber(hardwareMap);
        wrist = new Wrist(hardwareMap);

        leftGrabber.init();
        rightGrabber.init();
        wrist.init();
        arm.init();


        while(opModeInInit()) {
            telemetry.update();
        }

        waitForStart();

        while(opModeIsActive()) {

            leftVertControl = Math.pow(-gamepad1.left_stick_y, 3);
            leftHorzControl = Math.pow(gamepad1.left_stick_x, 3);
            rotate = Math.pow(-gamepad1.right_stick_x, 3);

            if(gamepad1.right_trigger > 0.4) {
                Drivetrain.drivePower = 0.3;
            }
            else {
                Drivetrain.drivePower = 0.6;
            }

            drivetrain.drive(leftHorzControl, leftVertControl, rotate);


            // DRIVE METHODS

            //Intake position
            if(gamepad1.x) { //square
                wrist.wristSetPos(0.71); //ground level
                arm.autoArmRotate(0.2, 0);

            //home position with wrist up
            if (gamepad2.a) { //X button
                arm.autoArmRotate(0.15, 30);
                wrist.wristSetPos(0.25);
            }

            //Makes the arm parallel to the ground
            if (gamepad2.b) { //circle
                arm.autoArmRotate(0.3, 700);
                wrist.wristSetPos(0.25);
            }

            //Positioning the arm to put pixels on the board
            if (gamepad2.y) { //triangle
                arm.autoArmRotate(0.3, 1600);
                wrist.wristSetPos(0.25);

            }
            //the grabbers are closed until the buttons are pushed
            if (gamepad2.left_bumper) {
                leftGrabber.leftGrabberSetPos(1);
            }
            else {
                leftGrabber.leftGrabberSetPos(0.8);
            }

            if (gamepad2.right_bumper) {
                rightGrabber.rightGrabberSetPos(0.3);
            }
            else {
                rightGrabber.rightGrabberSetPos(0.4);
            }

            }

            //Hang
            if(gamepad1.y) { //triangle
                wrist.wristSetPos(0.25);
                arm.autoArmRotate(0.15, 30);
                arm.autoArmExtend(0.6, 2000);
            }
            if (gamepad1.b) { //circle
                arm.autoArmExtend(0.3, 1000);
            }


            arm.telemetry(telemetry);
            telemetry.addData("left_trigger", gamepad1.left_trigger);
            telemetry.addData("Rotation power:", power);
            telemetry.addData("gamepad1.b:", gamepad1.b);
            telemetry.update();
        }
    }

    //Calculates for Drivetrain
//    public void clipBotMecanumDrive ( double vert, double horz, double rotate, double driveSpeed){
//        Vector2d input = new Vector2d(vert, horz);
//        double inputMag = Math.sqrt(vert*vert + horz*horz);
//
//        double frDrive = (vert + horz + rotate);
//        double flDrive = (vert - horz - rotate);
//        double brDrive = (vert - horz + rotate);
//        double blDrive = (vert + horz - rotate);
//
//        // finding maximum drive for division below
//        double max = Math.abs(Math.max(Math.abs(frDrive), Math.max(Math.abs(flDrive), Math.max(Math.abs(brDrive), Math.abs(blDrive)))));
//
//        // power calculations
//        if (Math.abs(vert) > .1 || Math.abs(horz) > .1 || Math.abs(rotate) > .1) {
//            leftFront.setPower(inputMag * driveSpeed * flDrive / max);
//            leftBack.setPower(inputMag * driveSpeed * blDrive / max);
//            rightFront.setPower(inputMag * driveSpeed * frDrive / max);
//            rightBack.setPower(inputMag * driveSpeed * brDrive / max);
//        } else {
//            leftFront.setPower(0);
//            leftBack.setPower(0);
//            rightFront.setPower(0);
//            rightBack.setPower(0);
//
//
//        }
//    }
}
