package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/*
    CONTROLS:
    gamepad1:
        left stick:             Driving
        right stick:            Rotation
        left bumper (hold):     slower driving
        a (x):                  close grabber
        b (circle):             open grabber
        x (square):             manual slide down
        y (triangle):           manual slide up
        dpad down:              base
        dpad right:             low
        dpad left:              med
        dpad up:                high
 */

@TeleOp(name = "Jake", group = "TeleOp")
public class Teleop extends LinearOpMode {
    DcMotorEx FrontLeft, FrontRight, BackLeft, BackRight, Slider;
    Servo grabber;

    double verticalControl, horizontalControl, rotateControl, verticalSlowControl, horizontalSlowControl, sliderControl;

    //state machine enums
    enum DRIVESTATE{
        joyDrive,
        dpadDrive
    }
    enum SLIDESTATE{
        autoSlide,
        manualSlide
    }

    DRIVESTATE driveState = DRIVESTATE.joyDrive;
    SLIDESTATE slideState = SLIDESTATE.autoSlide;

    double driveSpeed = 0.6;
    double slowerDriveSpeed = 0.4;
    double currentDriveSpeed = driveSpeed;
    double slideSpeed = 0.3;
    double autoSlideSpeed = 0.6;
    double sens = 0.2;

    public void runOpMode() throws InterruptedException {
        //initialize motors and servos
        FrontLeft = hardwareMap.get(DcMotorEx.class, "FrontLeft");
        FrontRight = hardwareMap.get(DcMotorEx.class, "FrontRight");
        BackLeft = hardwareMap.get(DcMotorEx.class, "RearLeft");
        BackRight = hardwareMap.get(DcMotorEx.class, "RearRight");

        Slider = hardwareMap.get(DcMotorEx.class, "Slider");

        grabber = hardwareMap.get(Servo.class, "Grabber");

        //reverse motors properly
        BackLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        FrontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        Slider.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {
            //driving
            {
                //get gamepad joystick and dpad variables for control
                verticalControl = -1 * clipJoyInput(gamepad1.left_stick_y);
                horizontalControl = clipJoyInput(gamepad1.left_stick_x);
                rotateControl = clipJoyInput(gamepad1.right_stick_x);

                //hold for slow mode
                if(gamepad1.left_bumper){
                    currentDriveSpeed = slowerDriveSpeed;
                }
                else{
                    currentDriveSpeed = driveSpeed;
                }

                //apply control
                Drive(verticalControl, horizontalControl, rotateControl, currentDriveSpeed);
            }

            //sliding
            {
                sliderControl = -1 * clipJoyInput(gamepad2.right_stick_y);

                switch(slideState){
                    case autoSlide:
                        //check each button and set target position
                        if(gamepad1.dpad_down){
                            autoSlide(0);
                        }
                        if(gamepad1.dpad_right){
                            autoSlide(1500);
                        }
                        if(gamepad1.dpad_left){
                            autoSlide(2100);
                        }
                        if(gamepad1.dpad_up){
                            autoSlide(3000);
                        }
                        //case to change state
                        if(gamepad1.x || gamepad1.y){
                            slideState = SLIDESTATE.manualSlide;
                        }
                        break;
                    case manualSlide:
                        //control slider manually
                        if(gamepad1.x){
                            manualSlide(-slideSpeed);
                        }
                        else if(gamepad1.y){
                            manualSlide(slideSpeed);
                        }
                        else{
                            manualSlide(0);
                        }

                        //case to change state (any button are pressed)
                        if(gamepad1.dpad_left || gamepad1.dpad_right || gamepad1.dpad_up || gamepad1.dpad_down){
                            slideState = SLIDESTATE.autoSlide;
                        }
                        break;
                }
            }

            //grabbing
            {
                if(gamepad1.a){
                    grabber.setPosition(.33);
                }
                if(gamepad1.b){
                    grabber.setPosition(.53);
                }
            }

            //reset slider
            if(gamepad1.right_bumper){
                Slider.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                slideState = SLIDESTATE.manualSlide;
            }

            //telemetry
            telemetry.addData("gamepad1leftstickx", gamepad1.left_stick_x);
            telemetry.addData("horz control", horizontalControl);
            telemetry.addData("gamepad1leftsticky", gamepad1.left_stick_y);
            telemetry.addData("vert control", verticalControl);
            telemetry.addData("gamepad1rightstickx", gamepad1.right_stick_x);
            telemetry.addData("rotate control", rotateControl);
            telemetry.addData("DriveState", driveState);
            telemetry.addData("SlideState", slideState);
            telemetry.addData("Slide position", Slider.getCurrentPosition());

            telemetry.update();
        }
    }

    public void Drive(double vert, double horz, double rotate, double power) {
        double frdrive = vert - horz - rotate;
        double fldrive = vert + horz + rotate;
        double brdrive = vert + horz - rotate;
        double bldrive = vert - horz + rotate;

        double max = Math.abs(Math.max(Math.abs(frdrive), Math.max(Math.abs(fldrive), Math.max(Math.abs(brdrive), Math.abs(bldrive)))));

        FrontRight.setPower(power * frdrive / max);
        FrontLeft.setPower(power * fldrive / max);
        BackRight.setPower(power * brdrive / max);
        BackLeft.setPower(power * bldrive / max);
    }

    public void autoSlide(int position){
        Slider.setPower(autoSlideSpeed);

        if(Slider.getMode() != DcMotor.RunMode.RUN_TO_POSITION){
            Slider.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        Slider.setTargetPosition(position);
    }

    public void manualSlide(double control){
        if(Slider.getMode() != DcMotor.RunMode.RUN_USING_ENCODER){
            Slider.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        Slider.setPower(control);
    }



    //method to make clip joystick input if it is less than sensitivity constant
    public double clipJoyInput(double input){
        if(Math.abs(input) < sens){
            return 0;
        }

        return Range.clip(input, -1, 1);
    }
}
