package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Grabber;
import org.firstinspires.ftc.teamcode.subsystems.PlaneLauncher;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;


@Config
@TeleOp(name = "TestTeleOp",group = "TeleOp")
public class TestTeleOp extends LinearOpMode {
    public static int armRotateOuttakePos = 1300;
    public static int hangUp = 3620;
    public static int hangDown = -250;
    public static int armExtendIntakePos = 100;


    Arm arm;
    Drivetrain drivetrain;
    Grabber grabber;
    Wrist wrist;
    PlaneLauncher launcher;

    double leftVertControl;
    double leftHorzControl;
    double rotate, power;



    @Override
    public void runOpMode() throws InterruptedException {
        power = 0;

        drivetrain = new Drivetrain(hardwareMap);
        arm = new Arm(hardwareMap);
        grabber = new Grabber(hardwareMap);
        wrist = new Wrist(hardwareMap);

        grabber.init();
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

            //2nd driver
            //home position with wrist up
            if (gamepad2.a) { //X button
                arm.armRotateTargetPos = 0;
                wrist.wristSetPos(wrist.depositPos);
            }

            //Makes the arm parallel to the ground
            if (gamepad2.b) { //circle
                arm.armRotateTargetPos = 700;
                wrist.wristSetPos(wrist.depositPos);
            }

            //Positioning the arm to put pixels on the board
            if (gamepad2.y) { //triangle
                arm.armRotateTargetPos = armRotateOuttakePos;
                wrist.wristSetPos(wrist.depositPos);
            }
            //the grabbers are closed until the buttons are pushed
            if (gamepad2.left_bumper) {
                grabber.leftGrabberSetPos(0.75);
            }
            else {
                grabber.leftGrabberSetPos(0.58); //closed
            }

            if (gamepad2.right_bumper) {
                grabber.rightGrabberSetPos(0.7);
            }
            else {
                grabber.rightGrabberSetPos(1); //closed
            }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //1st driver
            //Intake position for the wrist
            if(gamepad1.right_bumper) {
                wrist.wristSetPos(Wrist.intakePos); //ground level
                arm.armRotateTargetPos = 0;
            }
            //Extend Arm in front of the Board
            if(gamepad1.x) { //square
                wrist.wristSetPos(Wrist.depositPos);
                arm.autoArmExtend(0.6, hangUp);
            }
            if(gamepad1.a) { //Controller X
                wrist.wristSetPos(Wrist.depositPos);
                arm.autoArmExtend(0.6, 2200);
            }
            //Hang
            if(gamepad1.y) { //triangle
                wrist.wristSetPos(Wrist.depositPos);
                arm.armRotateTargetPos = 1200;
                arm.autoArmExtend(0.6, hangUp);
            }
            if (gamepad1.b) { //circle
                arm.autoArmExtend(0.3, hangDown);
                arm.armRotateTargetPos = 1200;
                // EXTEND ENCODER NOT WORKING
//                arm.autoArmExtend(0.6, 350);
            }

            if(gamepad1.left_bumper) {
                launcher.LauncherSetPos(launcher.launcherPos);
            }
            else{
                launcher.LauncherDisabled();
            }

            // MANUAL EXTENSION
//            if(Math.abs(gamepad2.left_stick_y) > 0.1) {
//                // if joystick is being used, set extend power to joystick y
//                arm.setArmExtendPower(-gamepad2.left_stick_y);
//            } else {
//                // otherwise set extend power to 0
//                arm.setArmExtendPower(0);
//            }

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
