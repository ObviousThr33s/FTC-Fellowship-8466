package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Depot Right", group = "Samwise")
public class SamwiseDriveRouteDepotRight extends SamwiseAutoDriveWithTensorflow90 {

    String route = "depot right";

    @Override
    public void runOpMode()
    {
        /**
         * call parent to start:
         * 1. init
         * 2. waitforStart
         * 3. start drive
         */
        super.runOpMode();
    }

    protected void drive() {

        //common drive defined by the parent
        telemetryNow(route, "starting parent common drive ...");
        super.drive();

        // specific drive for this route

        turnDrive(TurnDirection.RIGHT, 29, 3);
        encoderDrive(DRIVE_SPEED, 30,30, 4);
        turnDrive(TurnDirection.LEFT, 42.6684716, 3);
        encoderDrive(DRIVE_SPEED, 30, 30, 4);
        turnDrive(TurnDirection.RIGHT, 147.75, 3);
        //TODO: Drop Team Marker here
        md.move(1);
        encoderDrive(DRIVE_SPEED, 66, 66,5);
    }

}
