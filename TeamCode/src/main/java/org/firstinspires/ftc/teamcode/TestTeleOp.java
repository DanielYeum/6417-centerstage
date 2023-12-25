package org.firstinspires.ftc.teamcode;

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

        arm.init();
        leftGrabber.init();
        rightGrabber.init();
        wrist.init();

/*        //Define the names on the screen to assign motors to the hub
//        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
//        leftBack = hardwareMap.get(DcMotorEx.class, "leftBack");
//        rightBack = hardwareMap.get(DcMotorEx.class, "rightBack");
//        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
//        armRotate = hardwareMap.get(DcMotorEx.class, "armRotate");
//        armExtend = hardwareMap.get(DcMotorEx.class, "armExtend");
//
//        //grabber = hardwareMap.get(Servo.class, "grabber");
//
//        //BRAKE and FLOAT
//        //When setPower(0),
//        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        armRotate.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
//        armExtend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
//
//
//        leftFront.setPower(0);
//        leftBack.setPower(0);
//        rightBack.setPower(0);
//        rightFront.setPower(0);
//        armRotate.setPower(0);
//        armExtend.setPower(0);
//
//        leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
//        leftBack.setDirection(DcMotorSimple.Direction.FORWARD);
//        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
//        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);
//
//        armRotate.setDirection(DcMotorSimple.Direction.REVERSE);
//        armExtend.setDirection(DcMotorSimple.Direction.FORWARD);

        //Uses ticks to run to the position
        //leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //leftBack.setTargetPosition(500); //int value is the ticks

        //Uses set power to run the motors AND track the position; Run endlessly with constant velocity
        //Encoder keeps track of the ticks
        //leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        //Only runs on the power assigned and doesn't keep track of ticks
        //leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Completely resets the encoder; used to make sure no encoder is left
        //leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

*/
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
            } else {
                Drivetrain.drivePower = 0.5;
            }

            drivetrain.drive(leftHorzControl, leftVertControl, rotate);


            // DRIVE METHODS
            //home position
            if (gamepad2.a) { //X button
                arm.autoArmRotate(0.15, 40);
                wrist.wristSetPos(0.68);
            }

            //Makes the arm parallel to the ground
            if (gamepad2.b) { //circle
                arm.autoArmRotate(0.3, 600);
            }

            //Positioning the arm to put pixels on the board
            if (gamepad2.y) { //triangle
                arm.autoArmRotate(0.3, 1600);
                wrist.wristSetPos(0.25);

            }
            //the grabbers are closed until the buttons are pushed
            if (gamepad2.left_bumper) {
                leftGrabber.leftGrabberSetPos(0.3);
            }
            else {
                leftGrabber.leftGrabberSetPos(0.6);
            }
            
            if (gamepad2.right_bumper) {
                rightGrabber.leftGrabberSetPos(0.3);
            }
            else {
                rightGrabber.leftGrabberSetPos(0.6);
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
