package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Config
@TeleOp(name = "motor test", group = "TeleOp")
public class MotorTest extends LinearOpMode {
    public static String name = "leftFront";

    @Override
    public void runOpMode() throws InterruptedException{
        DcMotorEx motor = hardwareMap.get(DcMotorEx.class, name);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        double vert;
        motor.setPower(0);

        waitForStart();

        while(opModeIsActive()) {
            motor = hardwareMap.get(DcMotorEx.class, name);

            vert = -gamepad1.left_stick_y;

            if(Math.abs(vert) > 0.1) {
                motor.setPower(vert);
            } else {
                motor.setPower(0);
            }

            telemetry.addData("name", name);
            telemetry.addData("power", motor.getPower());
            telemetry.addData("current position", motor.getCurrentPosition());
            telemetry.update();
        }
    }
}
