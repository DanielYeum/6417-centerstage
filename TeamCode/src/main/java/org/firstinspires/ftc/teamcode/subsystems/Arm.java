package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Arm {
    private DcMotorEx armExtend;
    private DcMotorEx armRotate;
    public final static double TICKS_PER_REVOLUTION = 3895.9;
    public static double armP = 0.003, armI = 0, armD = 0.0001, armF = 0;

    private PIDController armRotatePID;
    public int armRotateTargetPos;
    private int armRotateCurrentPos;
    public double armRotatePower;


    public Arm(HardwareMap hardwareMap) {
        armExtend = hardwareMap.get(DcMotorEx.class, "armExtend");
        armRotate = hardwareMap.get(DcMotorEx.class, "armRotate");

        armRotatePID = new PIDController(armP, armI, armD);
        armRotateTargetPos = 0;
        armRotatePower = 0;
    }

    public void init() {
        armRotate.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        armRotate.setPower(0);
        armRotate.setDirection(DcMotorSimple.Direction.REVERSE);
        armRotate.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRotate.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        armExtend.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        armExtend.setPower(0);
        armExtend.setDirection(DcMotorSimple.Direction.FORWARD);
        armExtend.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armExtend.setTargetPosition(0);
        armExtend.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        armRotate.setTargetPosition(50);
        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        new WaitCommand(1000),
                        new InstantCommand(() -> armExtend.setTargetPosition(3500))
                )
        );
    }

    public void update() {
        armRotateCurrentPos = getArmRotatePosition();
        armRotatePower = Range.clip(armRotatePID.calculate(armRotateCurrentPos, armRotateTargetPos), -0.5, 0.5);
        setArmRotatePower(armRotatePower);
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

    public int getArmRotatePosition() {
        return armRotate.getCurrentPosition();
    }

    public void setArmRotatePower(double power) {
        armRotate.setPower(power);
    }

    public void setArmExtendPower(double power) {
        armExtend.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armExtend.setPower(power);
    }

    public void telemetry(Telemetry telemetry) {
        telemetry.addData("arm rotate current pos", armRotate.getCurrentPosition());
        telemetry.addData("arm extend current pos", armExtend.getCurrentPosition());
    }
}
