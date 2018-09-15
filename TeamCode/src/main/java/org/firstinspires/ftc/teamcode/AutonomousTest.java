package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by 28761 on 11/11/2017.
 */

@TeleOp(name="AutonomousTest", group="Iterative Opmode")
public class AutonomousTest extends LinearOpMode {

    KeithRobot keith;
    public DriveSystem ds = null;

    @Override
    public void runOpMode() {
        //initialize hardwareMap
        HardwareMap hwMap = hardwareMap;
        telemetry.addLine("DcMotors Set...");

        keith = new KeithRobot(hwMap, telemetry);
        //initialize Mecanum Driving System
        ds = keith.GetDriveSystem();
        waitForStart();

        double movePower = 0.3;
        double moveDistance = 2000;
        double spinPower = 0.1;
        double spinValue = 8;

        while (true) {
            if (gamepad1.y) {
                break;
            }
            if (gamepad1.x) {
                //ds.Move(spinPower, 0, 15, 0, 0);
                //telemetry.addLine("Completed Step 1");
                //telemetry.update();
                //ds.Move(spinPower, 0, -15, 0, 0);
                //telemetry.addLine("Completed Step 2");
                //telemetry.update();
                //ds.Move(movePower, 0, 0, 4000, 0);
                //telemetry.addLine("Completed Step 3");
                //telemetry.update();
                ds.Move(movePower, -90, 0, 2700, 0);
                telemetry.addLine("Completed Step 4");
                telemetry.update();
                //ds.Move(movePower, 0, 0, 1800, 0);
                //telemetry.addLine("Completed Step 5");
                //telemetry.update();
                break;
            }
            if (gamepad1.b) {
                ds.Move(spinPower, 0, -15, 0, 0);
                ds.Move(spinPower, 0, 15, 0, 0);
                ds.Move(movePower, 0, 0, 4000, 0);
                ds.Move(movePower, -90, 0, 2700, 0);
                ds.Move(movePower, 0, 0, 1800, 0);
                break;
            }
        }
    }
}
