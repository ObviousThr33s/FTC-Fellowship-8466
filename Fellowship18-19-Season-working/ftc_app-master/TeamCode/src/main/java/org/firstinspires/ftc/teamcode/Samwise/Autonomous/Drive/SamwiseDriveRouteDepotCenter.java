package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Depot Center", group = "Samwise")
public class SamwiseDriveRouteDepotCenter extends SamwiseAutoDriveWithTensorflow90 {

    String route = "depot center";

    @Override
    public void runOpMode()
    {
        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        waitForStart();

        this.drive();
    }

    protected void drive() {

        //common drive defined by the parent
        telemetryNow(route, "starting parent common drive ...");
        super.drive();

        // specific drive for this route

        encoderDrive(DRIVE_SPEED, 60,60,5);
        turnDrive(TurnDirection.RIGHT,125,3);
        //TODO: Drop Team Marker here
        encoderDrive(DRIVE_SPEED, 70,70,5);

    }

}
