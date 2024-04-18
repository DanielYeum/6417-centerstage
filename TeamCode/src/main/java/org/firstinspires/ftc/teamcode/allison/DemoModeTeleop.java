// Practice mode teleop for demos

package org.firstinspires.ftc.teamcode.allison;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Grabber;
import org.firstinspires.ftc.teamcode.subsystems.PlaneLauncher;
import org.firstinspires.ftc.teamcode.subsystems.Wrist;


// to do: set default position to wrist up after init


@Config
@TeleOp (name = "PracticeModeTeleop", group = "TeleOp")
public class DemoModeTeleop extends LinearOpMode {
    DemoModeDriveTrain drivetrain;
    public double leftVertControl;
    public double leftHorzControl;
    public double rotate;

    @Override
    public void runOpMode() throws InterruptedException {

        drivetrain = new DemoModeDriveTrain(hardwareMap);


        while (opModeInInit()){
            telemetry.update();
        }

        waitForStart();

        while (opModeIsActive()){

            leftVertControl = Math.pow(-gamepad1.left_stick_y, 3);
            leftHorzControl = Math.pow(gamepad1.left_stick_x, 3);
            rotate = Math.pow(-gamepad1.right_stick_x, 3);

            if(gamepad1.right_trigger > 0.4) {
                Drivetrain.drivePower = 0.2; //prev .3
            }
            else {
                Drivetrain.drivePower = 0.4; // prev .6
            }

            drivetrain.drive(leftHorzControl, leftVertControl, rotate);

        }
    }

}
