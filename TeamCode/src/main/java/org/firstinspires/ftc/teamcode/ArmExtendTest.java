package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@TeleOp(name = "arm extend test", group = "TeleOp")
public class ArmExtendTest extends LinearOpMode {
    DcMotorEx armExtend;
    double power;

    public static int position = 30;


    public void runOpMode() throws InterruptedException {

        armExtend = hardwareMap.get(DcMotorEx.class, "armExtend");


        armExtend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        armExtend.setPower(0);
        armExtend.setDirection(DcMotorSimple.Direction.FORWARD);
        armExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armExtend.setTargetPosition(position);
        armExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        waitForStart();

        while(opModeIsActive()) {
            power = -gamepad1.left_stick_y;
            armExtend.setPower(power);

            if (gamepad1.a) { //X button
                power = 0.8;
                armExtend.setPower(power);
                armExtend.setTargetPosition(0); //int value is the tick value
            }

            if (gamepad1.b) { //circle
                power = 0.8;
                armExtend.setPower(power);
                armExtend.setTargetPosition(position); //int value is the tick value
            }


            telemetry.addData("Extend power:", power);
            telemetry.addData("position", position);
            telemetry.update();

        }
    }

}
