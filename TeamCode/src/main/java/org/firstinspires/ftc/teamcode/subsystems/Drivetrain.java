package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Const;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public class Drivetrain extends SampleMecanumDrive {

    public static double drivePower;
    public Drivetrain (HardwareMap hardwareMap) {
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
