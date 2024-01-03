package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Arm;

@Config
@TeleOp(name = "arm pid tuner")
public class ArmRotationPIDTuner extends LinearOpMode {
    private PIDController controller;
    public static double p = 0, i = 0, d = 0, f = 0;
    public static int target = 0;

    Arm arm;
    public void runOpMode() throws InterruptedException {
        controller = new PIDController(p,i,d);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        arm = new Arm(hardwareMap);
        arm.init();

        waitForStart();
        while(opModeIsActive()) {
            controller.setPID(p, i, d);
            int pos = arm.getArmRotatePosition();
            double power = controller.calculate(pos, target);

            arm.setArmRotatePower(power);

            telemetry.addData("target", target);
            telemetry.addData("current", pos);
            telemetry.addData("power", power);
            telemetry.update();

        }
    }
}
