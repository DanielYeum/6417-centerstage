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
        scorePositionBoolean = false;
        homePositionBoolean = false;
        armRotate.setDirection(DcMotorSimple.Direction.REVERSE);
        armRotate.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRotate.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        waitForStart();

        while(opModeIsActive()) {
            power = -gamepad1.left_stick_y;
            armRotate.setPower(power/2);
            armRotate.setTargetPosition(10);

            //Home position
            while(gamepad1.a || homePositionBoolean) {
                homePositionBoolean = true;
                power = 0.3;
                armRotate.setPower(power);

                armRotate.setTargetPosition(0); //int value is the tick value
                if(armRotate.getCurrentPosition() == 0){
                    homePositionBoolean = false;
                }
            }


            //Makes the arm parallel to the ground
            while(gamepad1.left_trigger > 0.1) {
                power = 0.3;
                armRotate.setPower(power);
                armRotate.setTargetPosition(655); //int value is the tick value
            }

            }

            //Positioning the arm to put pixels on the board
            while(gamepad1.y || scorePositionBoolean) {
                scorePositionBoolean = true;
                power = 0.3;
                armRotate.setPower(power);
                armRotate.setTargetPosition(800); //int value is the tick value
                if(armRotate.getCurrentPosition() == 800){
                    scorePositionBoolean = false;
                }

            }





            telemetry.addData("Rotation power:", power);
            telemetry.addData("Arm Position:", armRotate.getCurrentPosition());
            telemetry.addData("gamepad1.b:", gamepad1.b);
            telemetry.update();


        }
    }

}
