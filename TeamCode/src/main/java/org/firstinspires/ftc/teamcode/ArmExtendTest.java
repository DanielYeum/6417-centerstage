package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "arm extend test", group = "TeleOp")
public class ArmExtendTest extends LinearOpMode {
    DcMotorEx armExtend;
    double power;
    public void runOpMode() throws InterruptedException {

        armExtend = hardwareMap.get(DcMotorEx.class, "armExtend");
        armExtend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        armExtend.setPower(0);
        power = 0;
        armExtend.setDirection(DcMotorSimple.Direction.FORWARD);
        waitForStart();
        while(opModeIsActive()) {
            power = -gamepad1.left_stick_y;
            armExtend.setPower(power);

            telemetry.addData("Extend power:", power);
            telemetry.update();

        }
    }

}
