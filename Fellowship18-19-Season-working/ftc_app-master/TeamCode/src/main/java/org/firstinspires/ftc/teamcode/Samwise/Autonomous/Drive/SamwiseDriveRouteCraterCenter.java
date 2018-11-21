package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Kyla v1.0 CraterCenter", group = "Samwise")
public class SamwiseDriveRouteCraterCenter extends SamwiseAutoDriveWithTensorflow90 {

    String route = "crater center";

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

        encoderDrive(DRIVE_SPEED, 22, 22, 5);
        telemetry.addData(route, "finish second drive");
        telemetry.update();

        encoderDrive(DRIVE_SPEED, -8, -7, 4);
        telemetry.addData(route, "finish third drive");
        telemetry.update();

        turnDrive(TurnDirection.RIGHT, 90, 5);
        telemetry.addData(route, "finish fourth turn");
        telemetry.update();

        encoderDrive(DRIVE_SPEED, -45,-45,20);
        telemetry.addData(route, "finish fifth drive");
        telemetry.update();

        turnDrive(TurnDirection.LEFT, 48, 3);
        telemetry.addData(route, "finish sixth turn");
        telemetry.update();

        encoderDrive(DRIVE_SPEED, -48,-48, 20);
        telemetry.addData(route, "finish seventh drive");
        telemetry.update();

        md.move(1);

        encoderDrive(DRIVE_SPEED, 68, 68,20);
        telemetry.addData(route, "finish eighth drive");
        telemetry.update();


    }

}
