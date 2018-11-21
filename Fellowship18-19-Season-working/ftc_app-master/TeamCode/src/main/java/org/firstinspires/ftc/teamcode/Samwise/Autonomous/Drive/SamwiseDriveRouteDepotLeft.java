package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Kyla v1.0 CraterLeft", group = "Samwise")
public class SamwiseDriveRouteDepotLeft extends SamwiseAutoDriveWithTensorflow90 {

    String route = "depot left";

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

        turnDrive(TurnDirection.LEFT, 28, 3);

        encoderDrive(DRIVE_SPEED,30, 30, 4);

        turnDrive(TurnDirection.RIGHT,50,3);

        encoderDrive(DRIVE_SPEED, 38,38,4);

        turnDrive(TurnDirection.RIGHT, 99, 3);

        //TODO: Drop Team Marker here
        md.move(1);

        encoderDrive(DRIVE_SPEED,73,73, 5);

    }

}
