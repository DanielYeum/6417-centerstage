package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Arm {
    private DcMotorEx armExtend;
    private DcMotorEx armRotate;


    public Arm(HardwareMap hardwareMap) {
        armExtend = hardwareMap.get(DcMotorEx.class, "armExtend");
        armRotate = hardwareMap.get(DcMotorEx.class, "armRotate");
    }

    public void init() {
        armRotate.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        armRotate.setPower(0);
        armRotate.setDirection(DcMotorSimple.Direction.REVERSE);
        armRotate.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRotate.setTargetPosition(0);
        armRotate.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        armExtend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        armExtend.setPower(0);
        armExtend.setDirection(DcMotorSimple.Direction.FORWARD);
        armExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armExtend.setTargetPosition(0);
        armExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        armRotate.setTargetPosition(50);
            if(armRotate.getCurrentPosition() == 30){
        armExtend.setTargetPosition(3500);
            }

    }

    public void autoArmRotate(double power, int targetPos) {
        armRotate.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armRotate.setPower(power);
        armRotate.setTargetPosition(targetPos);
    }

    public void autoArmExtend(double power, int targetPos) {
        armExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armExtend.setPower(power);
        armExtend.setTargetPosition(targetPos);
    }
    public void telemetry(Telemetry telemetry) {
        telemetry.addData("arm rotate current pos", armRotate.getCurrentPosition());
    }
}
