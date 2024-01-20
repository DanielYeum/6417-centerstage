package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoControllerEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class PlaneLauncher {
    public static final double launcherPos = 1;

    private CRServo launcher;


    public PlaneLauncher(HardwareMap hardwareMap) {
        launcher = hardwareMap.get(CRServo.class, "launcher");
    }

    public void init() {
        launcher.setPower(0);
    }

    public void LauncherSetPower(double power){
        launcher.setPower(power);
    }

    public void telemetry(Telemetry telemetry) {
        telemetry.addData("launcher power", launcher.getPower());

    }
}
