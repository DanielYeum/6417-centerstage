package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "TestTeleOp",group = "TeleOp")
public class TestTeleOp extends LinearOpMode {
    DcMotorEx leftFront, leftBack, rightBack, rightFront, armRotate, armExtend;

    Servo grabber;
    double leftVertControl;
    double leftHorzControl;
    double rotate, power;


    @Override
    public void runOpMode() throws InterruptedException {
        power = 0;

        //Define the names on the screen to assign motors to the hub
        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        leftBack = hardwareMap.get(DcMotorEx.class, "leftBack");
        rightBack = hardwareMap.get(DcMotorEx.class, "rightBack");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
        armRotate = hardwareMap.get(DcMotorEx.class, "armRotate");
        armExtend = hardwareMap.get(DcMotorEx.class, "armExtend");

        //grabber = hardwareMap.get(Servo.class, "grabber");

        //BRAKE and FLOAT
        //When setPower(0),
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armRotate.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        armExtend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);


        leftFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
        rightFront.setPower(0);
        armRotate.setPower(0);
        armExtend.setPower(0);

        leftFront.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBack.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);

        armRotate.setDirection(DcMotorSimple.Direction.FORWARD);
        armExtend.setDirection(DcMotorSimple.Direction.FORWARD);

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


        while(opModeInInit()) {

            telemetry.addData("leftFront power: ", leftFront.getPower());
            telemetry.update();
        }

        waitForStart();

        while(opModeIsActive()) {

            leftVertControl = Math.pow(-gamepad1.left_stick_y, 3);
            leftHorzControl = Math.pow(-gamepad1.left_stick_x, 3);
            rotate = Math.pow(gamepad1.right_stick_x, 3);

            if (gamepad1.a) {
                leftFront.setPower(0);
            }

            clipBotMecanumDrive(leftVertControl, leftHorzControl, rotate, 0.5);

            // DRIVE METHODS

            // sets powers to drive motors




            //Display
            telemetry.addData("leftVertControl:", leftVertControl);
            telemetry.addData("LeftHorzControl:", leftVertControl);
            telemetry.addData("rotate:", rotate);
            telemetry.update();
        }
    }

    //Calculates for Drivetrain
    public void clipBotMecanumDrive ( double vert, double horz, double rotate, double driveSpeed){
        Vector2d input = new Vector2d(vert, horz);
        double inputMag = Math.sqrt(vert*vert + horz*horz);

        double frDrive = (vert + horz + rotate);
        double flDrive = (vert - horz - rotate);
        double brDrive = (vert - horz + rotate);
        double blDrive = (vert + horz - rotate);

        // finding maximum drive for division below
        double max = Math.abs(Math.max(Math.abs(frDrive), Math.max(Math.abs(flDrive), Math.max(Math.abs(brDrive), Math.abs(blDrive)))));

        // power calculations
        if (Math.abs(vert) > .1 || Math.abs(horz) > .1 || Math.abs(rotate) > .1) {
            leftFront.setPower(inputMag * driveSpeed * frDrive / max);
            leftBack.setPower(inputMag * driveSpeed * flDrive / max);
            rightFront.setPower(inputMag * driveSpeed * brDrive / max);
            rightBack.setPower(inputMag * driveSpeed * blDrive / max);
        } else {
            leftFront.setPower(0);
            leftBack.setPower(0);
            rightFront.setPower(0);
            rightBack.setPower(0);


        }
    }
}
