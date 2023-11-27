package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "arm rotation test", group = "TeleOp")
public class ArmRotationTest extends LinearOpMode {

    DcMotorEx armRotate;
    boolean scorePositionBoolean;
    boolean homePositionBoolean;

    double power;
    public void runOpMode() throws InterruptedException {

        armRotate = hardwareMap.get(DcMotorEx.class, "armRotate");
        armRotate.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        armRotate.setPower(0);
        power = 0;
        armRotate.setDirection(DcMotorSimple.Direction.REVERSE);
        armRotate.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRotate.setTargetPosition(0);
        armRotate.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        waitForStart();

        while(opModeIsActive()) {
            power = -gamepad1.left_stick_y;
            armRotate.setPower(power / 2);
            //armRotate.setTargetPosition(10);

            //Home position
            if (gamepad1.a) { //X button
                power = 0.3;
                armRotate.setPower(power);
                armRotate.setTargetPosition(10); //int value is the tick value
            }

            //Makes the arm parallel to the ground
            if (gamepad1.b) { //circle
                power = 0.3;
                armRotate.setPower(power);
                armRotate.setTargetPosition(655); //int value is the tick value
            }

            //Positioning the arm to put pixels on the board
            if (gamepad1.y) { //triangle
                power = 0.3;
                armRotate.setPower(power);
                armRotate.setTargetPosition(800); //int value is the tick value

            }


            telemetry.addData("Rotation power:", power);
            telemetry.addData("Arm Position:", armRotate.getCurrentPosition());
            telemetry.addData("gamepad1.b:", gamepad1.b);
            telemetry.update();

        }

    }

}
