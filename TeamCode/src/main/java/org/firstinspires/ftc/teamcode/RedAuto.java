package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryVelocityConstraint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Config
@Autonomous(name = "red auto")
public class RedAuto extends LinearOpMode {
    Drivetrain drivetrain;

    public static double forwardDistance = 24;
    public static double turnRightDeg = -90; // degrees
    public static double turnLeftDeg = 90; // degrees

    TrajectoryVelocityConstraint slowVelocity = SampleMecanumDrive.getVelocityConstraint(15, Math.toRadians(180), 13);


    @Override
    public void runOpMode() throws InterruptedException {
        drivetrain = new Drivetrain(hardwareMap);
        TrajectorySequence forward = drivetrain.trajectorySequenceBuilder(new Pose2d(0,0,0))
                .setVelConstraint(slowVelocity)
                .forward(forwardDistance)
                .build();

        TrajectorySequence turnRight = drivetrain.trajectorySequenceBuilder(new Pose2d(0,0,0))
                .setVelConstraint(slowVelocity)
                .turn(Math.toRadians(turnRightDeg))
                .build();

        TrajectorySequence turnLeft = drivetrain.trajectorySequenceBuilder(new Pose2d(0,0,0))
                .setVelConstraint(slowVelocity)
                .turn(Math.toRadians(turnLeftDeg))
                .build();
        while(!isStopRequested() && opModeInInit()) {

        }
        waitForStart();

        drivetrain.followTrajectorySequence(forward);
        drivetrain.followTrajectorySequence(turnRight);

        telemetry.update();
    }
}
