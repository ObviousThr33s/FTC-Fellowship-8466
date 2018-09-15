package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by 28761 on 11/11/2017.
 */

@TeleOp(name="DistanceTest", group="Iterative Opmode")
public class DistanceTest extends LinearOpMode {

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

        double drivePower = 0.25;
        double spinValue = 15;

        while (!gamepad1.x) {}

        ds.Move(drivePower, 0, 0, 1000, 0);

        while (!gamepad1.x) {}
    }
}
