package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Samwise.Autonomous.MarkerDeposit.SamwiseMarkerDeposit;

@Autonomous(name = "Kyla v1.0 CraterLeft", group = "Samwise")
public class SamwiseDriveRouteCraterLeft extends SamwiseAutoDriveWithTensorflow90 {

    String route = "crater left";

    @Override
    public void runOpMode()
    {
        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        waitForStart();

        driveRouteCraterLeft();
    }

    private void driveRouteCraterLeft() {

        telemetry.addData(route, "starting");
        telemetry.update();

        turnDrive(TurnDirection.LEFT, 32.429725, 2);
        telemetry.addData(route, "finish first turn");
        telemetry.update();

        encoderDrive(DRIVE_SPEED, 28, 28, 3);
        telemetry.addData(route, "finish second drive");
        telemetry.update();

        turnDrive(TurnDirection.RIGHT, 120, 3);
        telemetry.addData(route, "finish third turn");
        telemetry.update();

        encoderDrive(DRIVE_SPEED, -29,-29,3);
        telemetry.addData(route, "finish fourth drive");
        telemetry.update();

        turnDrive(TurnDirection.LEFT, 31, 3);
        telemetry.addData(route, "finish fifth turn");
        telemetry.update();

        encoderDrive(DRIVE_SPEED, -45,-45,5);
        telemetry.addData(route, "finish sixth drive");
        telemetry.update();

        encoderDrive(DRIVE_SPEED, 64, 64,15);
        telemetry.addData(route, "finish seventh drive");
        telemetry.update();

    }

}
