package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
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
@Autonomous(name = "right red auto")
public class RightRedAuto extends LinearOpMode {
    Drivetrain drivetrain;
    Arm arm;
    Grabber grabber;
    Wrist wrist;
    CVMaster cvMaster;
    int position;

    public static double forwardDistance = 22; //21 before
    public static double turnRightDeg = -45; // degrees
    public static double turnLeftDeg = 120; // degrees

    TrajectoryVelocityConstraint slowVelocity = SampleMecanumDrive.getVelocityConstraint(15, Math.toRadians(180), 13);
    TrajectoryVelocityConstraint fastVelocity = SampleMecanumDrive.getVelocityConstraint(30, Math.toRadians(180), 13);



    @Override
    public void runOpMode() throws InterruptedException {
        drivetrain = new Drivetrain(hardwareMap);
        wrist = new Wrist(hardwareMap);
        arm = new Arm(hardwareMap);
        grabber = new Grabber(hardwareMap);
        cvMaster = new CVMaster(hardwareMap, Alliance.RED);

        TrajectorySequence forward = drivetrain.trajectorySequenceBuilder(new Pose2d(0,0,0))
                .setVelConstraint(slowVelocity)
                .forward(forwardDistance)
                .build();

        TrajectorySequence center = drivetrain.trajectorySequenceBuilder(new Pose2d(0,0,0))
                .setVelConstraint(slowVelocity)
                .forward(28.3)
                .setVelConstraint(fastVelocity)
                .back(10)
                .build();

        TrajectorySequence strafeLeft = drivetrain.trajectorySequenceBuilder(new Pose2d(0,0,0))
                .setVelConstraint(slowVelocity)
                .strafeLeft(7.1)
                .back(8)
                .build();


        TrajectorySequence diagonalRight = drivetrain.trajectorySequenceBuilder(new Pose2d(0,0,Math.PI / 2))
                .setVelConstraint(slowVelocity)
                .forward(22)
                .lineTo(new Vector2d(9.6, 26))
                .back(14)
                .build();

        TrajectorySequence turnRight = drivetrain.trajectorySequenceBuilder(new Pose2d(0,0,0))
                .setVelConstraint(slowVelocity)
                .turn(Math.toRadians(turnRightDeg))
                .UNSTABLE_addTemporalMarkerOffset(2, () -> {
                    wrist.wristSetPos(wrist.intakePos);
                })
                .UNSTABLE_addTemporalMarkerOffset(4, () -> {
                    grabber.leftGrabberSetPos(0.75);
                })
                .waitSeconds(6)
                .back(8)
                .build();

        TrajectorySequence turnLeft = drivetrain.trajectorySequenceBuilder(new Pose2d(0,0,0))
                .setVelConstraint(slowVelocity)
                .turn(Math.toRadians(turnLeftDeg))
                .UNSTABLE_addTemporalMarkerOffset(2, () -> {
                    wrist.wristSetPos(wrist.intakePos);
                })
                .UNSTABLE_addTemporalMarkerOffset(4, () -> {
                    grabber.leftGrabberSetPos(0.75);
                })

                .waitSeconds(5)
                .build();

//        TrajectorySequence setHomePosition = drivetrain.trajectorySequenceBuilder(new Pose2d(0,0,0))
//                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                    wrist.wristSetPos(0.4); //Lifted slightly off of the ground
//                })
//                .UNSTABLE_addTemporalMarkerOffset(1, () -> {
//                    arm.autoArmExtend(0.6, 100);
//                })
//                .waitSeconds(1)
//                .build();
//
//        TrajectorySequence outtake = drivetrain.trajectorySequenceBuilder(new Pose2d(0,0,0))
//                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                    wrist.wristSetPos(0.5); //Lifted slightly off of the ground
//                    rightGrabber.rightGrabberSetPos(0.18); //open
//                })
//                .build();
//
//        TrajectorySequence resetPosition = drivetrain.trajectorySequenceBuilder(new Pose2d(0,0,0))
//                .UNSTABLE_addTemporalMarkerOffset(0, () -> {
//                    rightGrabber.rightGrabberSetPos(0.35); //closed
//                    wrist.wristSetPos(0.35); //Gives room for arm to retract
//                })
//                .UNSTABLE_addTemporalMarkerOffset(1, () -> {
//                    arm.autoArmExtend(0.6, 0);
//                })
//                .build();

        cvMaster.detectProp();
        grabber.init();
        wrist.init();
        arm.init();


        while(!isStopRequested() && opModeInInit()) {
            position = cvMaster.pipeline.position;

            telemetry.addData("build trajectories", "complete");
            telemetry.addData("average0", cvMaster.pipeline.average0);
            telemetry.addData("average1", cvMaster.pipeline.average1);
            telemetry.addData("average2", cvMaster.pipeline.average2);
            telemetry.addData("position", position);
            telemetry.update();
        }
        waitForStart();


        switch(position) {
            case 0: //left
                drivetrain.followTrajectorySequence(forward);
                drivetrain.followTrajectorySequence(turnLeft);
                break;
            case 1: //center
                drivetrain.followTrajectorySequence(forward);
                break;
            case 2: //right
                drivetrain.followTrajectorySequence(forward);
                drivetrain.followTrajectorySequence(turnRight);
                break;
        }

        telemetry.update();
    }
}
