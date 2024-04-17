package org.firstinspires.ftc.teamcode.allison;


import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public class DemoModeDriveTrain extends SampleMecanumDrive {

    public static double drivePower = 0.25; //prev .5
    public DemoModeDriveTrain(HardwareMap hardwareMap) {
        super(hardwareMap);
    }

    public void drive(double x, double y, double rotate) {
        Vector2d input = new Vector2d(x,y);
        Vector2d output = input.rotated(Math.toRadians(-90));

        x = output.getX();
        y = output.getY();

        setWeightedDrivePower(new Pose2d(x * drivePower, y * drivePower, rotate * drivePower));
    }
}

