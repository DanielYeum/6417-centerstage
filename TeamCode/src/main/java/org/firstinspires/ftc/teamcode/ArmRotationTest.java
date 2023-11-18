package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "arm rotation test", group = "TeleOp")
public class ArmRotationTest extends LinearOpMode {

    DcMotorEx armRotate;
    double power;
    public void runOpMode() throws InterruptedException {

        armRotate = hardwareMap.get(DcMotorEx.class, "armRotate");
        armRotate.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        armRotate.setPower(0);
        power = 0;
        armRotate.setDirection(DcMotorSimple.Direction.FORWARD);
        waitForStart();

        while(opModeIsActive()) {
            power = -gamepad1.left_stick_y;
            armRotate.setPower(power);

            telemetry.addData("Extend power:", power);
            telemetry.update();

        }
    }

}
