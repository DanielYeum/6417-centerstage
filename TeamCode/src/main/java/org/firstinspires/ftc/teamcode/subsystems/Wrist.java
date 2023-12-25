package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoControllerEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class Wrist {

    private Servo wrist;
    private ServoControllerEx wristController;


    public Wrist(HardwareMap hardwareMap) {
        wrist = hardwareMap.get(Servo.class, "wrist"); //1 -> down


    }
    public void init() {
        wristController = (ServoControllerEx) wrist.getController();
        wristController.setServoPwmDisable(wrist.getPortNumber());
    }

    public void wristSetPos(double pos){
        wristController.setServoPwmEnable(wrist.getPortNumber());
        wrist.setPosition(pos);
    }
    public void telemetry(Telemetry telemetry) {
        telemetry.addData("wrist current pos", wrist.getPosition());

    }
}
