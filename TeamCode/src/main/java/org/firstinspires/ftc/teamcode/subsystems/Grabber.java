package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoControllerEx;
import com.qualcomm.robotcore.hardware.HardwareMap;


import org.firstinspires.ftc.robotcore.external.Telemetry;


public class Grabber {

    private Servo leftGrabber;
    private Servo rightGrabber;

    public Grabber(HardwareMap hardwareMap) {
        leftGrabber = hardwareMap.get(Servo.class, "leftGrabber"); //1 -> open; 0.8 -> closes

        rightGrabber = hardwareMap.get(Servo.class, "rightGrabber"); //0 -> open
    }

    public void init() {
        ServoControllerEx leftController = (ServoControllerEx) leftGrabber.getController();
        ServoControllerEx rightController = (ServoControllerEx) rightGrabber.getController();
        leftController.setServoPwmDisable(leftGrabber.getPortNumber());
        rightController.setServoPwmDisable(rightGrabber.getPortNumber());
    }

    public void leftGrabberSetPos(double pos){
        leftGrabber.setPosition(pos);
    }
    public void rightGrabberSetPos(double pos){
        rightGrabber.setPosition(pos);
    }

    public void telemetry(Telemetry telemetry) {
        telemetry.addData("left grabber current pos", leftGrabber.getPosition());
        telemetry.addData("right grabber current pos", rightGrabber.getPosition());
    }
}
