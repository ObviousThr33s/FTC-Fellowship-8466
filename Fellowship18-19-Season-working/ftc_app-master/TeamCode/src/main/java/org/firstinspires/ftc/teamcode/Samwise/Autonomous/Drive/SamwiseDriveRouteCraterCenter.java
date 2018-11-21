package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Kyla v1.0 CraterCenter", group = "Samwise")
public class SamwiseDriveRouteCraterCenter extends SamwiseAutoDriveWithTensorflow90 {

    String route = "crater center";

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

        encoderDrive(DRIVE_SPEED, 26, 26, 5);
        telemetry.addData(route, "finish second drive");
        telemetry.update();

        encoderDrive(DRIVE_SPEED, -8, -8, 4);
        telemetry.addData(route, "finish third drive");
        telemetry.update();

        turnDrive(TurnDirection.RIGHT, 65, 5);
        telemetry.addData(route, "finish fourth turn");
        telemetry.update();

        encoderDrive(DRIVE_SPEED, -39,-39,20);
        telemetry.addData(route, "finish fifth drive");
        telemetry.update();

        turnDrive(TurnDirection.LEFT, 48, 3);
        telemetry.addData(route, "finish sixth turn");
        telemetry.update();

        encoderDrive(DRIVE_SPEED, -48,-48, 20);
        telemetry.addData(route, "finish seventh drive");
        telemetry.update();

        encoderDrive(DRIVE_SPEED, 69, 69,20);
        telemetry.addData(route, "finish eighth drive");
        telemetry.update();


    }

}
