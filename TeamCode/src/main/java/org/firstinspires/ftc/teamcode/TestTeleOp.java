package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Grabber;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;


@Config
@TeleOp(name = "TestTeleOp",group = "TeleOp")
public class TestTeleOp extends LinearOpMode {
    public static int armRotateOuttakePos = 1400;
    public static double wristOuttakePos = 0;
    public static int hangUp = 1400;
    public static int hangDown = 1400;


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
                wrist.wristSetPos(0.5); //ground level
                arm.armRotateTargetPos = 0;
            }

            //home position with wrist up
            if (gamepad2.a) { //X button
                arm.armRotateTargetPos = 200;
                wrist.wristSetPos(0.1);
            }

            //Makes the arm parallel to the ground
            if (gamepad2.b) { //circle
                arm.armRotateTargetPos = 700;
                wrist.wristSetPos(0.1);
            }

            //Positioning the arm to put pixels on the board
            if (gamepad2.y) { //triangle
                arm.armRotateTargetPos = armRotateOuttakePos;
                wrist.wristSetPos(wristOuttakePos);

            }
            //the grabbers are closed until the buttons are pushed
            if (gamepad2.left_bumper) {
                leftGrabber.leftGrabberSetPos(0.75);
            }
            else {
                leftGrabber.leftGrabberSetPos(0.58); //closed
            }

            if (gamepad2.right_bumper) {
                rightGrabber.rightGrabberSetPos(0.18);
            }
            else {
                rightGrabber.rightGrabberSetPos(0.35); //closed
            }

            //Hang
            if(gamepad1.y) { //triangle
                wrist.wristSetPos(0.25);
                arm.armRotateTargetPos = 1300;
                arm.autoArmExtend(0.6, hangUp);
            }
            if (gamepad1.b) { //circle
                arm.autoArmExtend(0.3, hangDown);
            }

            if(Math.abs(gamepad2.left_stick_y) > 0.1) {
                arm.setArmExtendPower(-gamepad2.left_stick_y);
            } else {
                arm.setArmExtendPower(0);
            }

            arm.update();

            arm.telemetry(telemetry);
            telemetry.addData("left_trigger", gamepad1.left_trigger);
            telemetry.addData("Rotation power:", power);
            telemetry.addData("gamepad1.b:", gamepad1.b);
            telemetry.addData("gamepad2.y:", gamepad2.y);
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
