package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoControllerEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;


@Config
public class Wrist {
    public static final double depositPos = 0.89;
    public static final double intakePos = 0.17;
    public static final double midPos = 0.25;


    // 0.65 is down (intake position)
    // 0.12 is deposit position
    private Servo wrist;
    private ServoControllerEx wristController;


    public Wrist(HardwareMap hardwareMap) {
        wrist = hardwareMap.get(Servo.class, "wrist"); //1 -> down
    }
    public void initOuttake(){
        wrist.setPosition(depositPos);
    }
    public void init() {
        wristController = (ServoControllerEx) wrist.getController();
    }

    public void wristSetPos(double pos){
        wristController.setServoPwmEnable(wrist.getPortNumber());
        wrist.setPosition(pos);
    }
    public void telemetry(Telemetry telemetry) {
        telemetry.addData("wrist current pos", wrist.getPosition());

    }
}
