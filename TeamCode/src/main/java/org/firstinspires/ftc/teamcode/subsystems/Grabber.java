package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoControllerEx;
import com.qualcomm.robotcore.hardware.HardwareMap;


import org.firstinspires.ftc.robotcore.external.Telemetry;


public class Grabber {

    private Servo leftGrabber;
    private Servo rightGrabber;
    private ServoControllerEx leftController;
    private ServoControllerEx rightController;

    public Grabber(HardwareMap hardwareMap) {
        leftGrabber = hardwareMap.get(Servo.class, "leftGrabber"); //1 -> open; 0.8 -> closes

        rightGrabber = hardwareMap.get(Servo.class, "rightGrabber"); //0 -> open
    }

    public void init() {
        leftController = (ServoControllerEx) leftGrabber.getController();
        rightController = (ServoControllerEx) rightGrabber.getController();
        rightController.setServoPwmDisable(rightGrabber.getPortNumber());
        leftGrabber.setPosition(0.5);
    }

    public void leftGrabberSetPos(double pos){
        leftController.setServoPwmEnable(leftGrabber.getPortNumber()); // enables servo before set position
        leftGrabber.setPosition(pos);
    }
    public void rightGrabberSetPos(double pos){
        rightController.setServoPwmEnable(rightGrabber.getPortNumber()); // enables the servo before set position
        rightGrabber.setPosition(pos);
    }

    public void telemetry(Telemetry telemetry) {
        telemetry.addData("left grabber current pos", leftGrabber.getPosition());
        telemetry.addData("right grabber current pos", rightGrabber.getPosition());
    }
}
