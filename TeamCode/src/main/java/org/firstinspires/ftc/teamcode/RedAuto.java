package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryVelocityConstraint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.states.Alliance;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Grabber;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.vision.CVMaster;

@Config
@Autonomous(name = "red auto")
public class RedAuto extends LinearOpMode {
    Drivetrain drivetrain;
    Arm arm;
    Grabber rightGrabber;
    Wrist wrist;
    CVMaster cvMaster;
    int position;

    public static double forwardDistance = 24;
    public static double turnRightDeg = -45; // degrees
    public static double turnLeftDeg = 45; // degrees

    TrajectoryVelocityConstraint slowVelocity = SampleMecanumDrive.getVelocityConstraint(15, Math.toRadians(180), 13);


    @Override
    public void runOpMode() throws InterruptedException {
        drivetrain = new Drivetrain(hardwareMap);
        wrist = new Wrist(hardwareMap);
        arm = new Arm(hardwareMap);
        rightGrabber = new Grabber(hardwareMap);
        cvMaster = new CVMaster(hardwareMap, Alliance.RED);

        TrajectorySequence forward = drivetrain.trajectorySequenceBuilder(new Pose2d(0,0,0))
                .setVelConstraint(slowVelocity)
                .forward(forwardDistance)
                .build();
                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
            robot.lift.liftState = LiftState.AUTO;
            robot.lift.setTargetPos(Constants.sliderAutoPos);
        })

        TrajectorySequence turnRight = drivetrain.trajectorySequenceBuilder(new Pose2d(0,0,0))
                .setVelConstraint(slowVelocity)
                .turn(Math.toRadians(turnRightDeg))
                .build();

        TrajectorySequence turnLeft = drivetrain.trajectorySequenceBuilder(new Pose2d(0,0,0))
                .setVelConstraint(slowVelocity)
                .turn(Math.toRadians(turnLeftDeg))
                .build();


        cvMaster.detectProp();

        while(!isStopRequested() && opModeInInit()) {
            position = cvMaster.pipeline.position;
        }


        waitForStart();



        drivetrain.followTrajectorySequence(forward);

        switch(position) {
            case 0:
                drivetrain.followTrajectorySequence(turnRight);

                break;
            case 1:
                break;
            case 2:
                drivetrain.followTrajectorySequence(turnLeft);
                break;
        }

        wrist.wristSetPos(0.1); //down
        rightGrabber.rightGrabberSetPos(0.18);


        telemetry.update();
    }
}
