package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoControllerEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class PlaneLauncher {
    public static final double launcherPos = 0.1;

    private Servo launcher;
    private ServoControllerEx launcherController;


    public PlaneLauncher(HardwareMap hardwareMap) {
        launcher = hardwareMap.get(Servo.class, "launcher");


    }
    public void init() {
        launcherController = (ServoControllerEx) launcher.getController();
        launcherController.setServoPwmDisable(launcher.getPortNumber());
    }

    public void LauncherSetPos(double pos){
        launcherController.setServoPwmEnable(launcher.getPortNumber());
        launcher.setPosition(pos);
    }

    public void LauncherDisabled() {
        launcherController.setServoPwmDisable(launcher.getPortNumber());
    }
    public void telemetry(Telemetry telemetry) {
        telemetry.addData("launcher current pos", launcher.getPosition());

    }
}
