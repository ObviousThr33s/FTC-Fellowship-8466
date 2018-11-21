package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "Kyla v1.0 CraterLeft", group = "Samwise")
public class SamwiseDriveRouteCraterLeft extends SamwiseAutoDriveWithTensorflow90 {

    String route = "crater left";

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

        turnDrive(TurnDirection.LEFT, 32.429725, 2);
        telemetry.addData(route, "finish first turn");
        telemetry.update();

        encoderDrive(DRIVE_SPEED, 28, 28, 3);
        telemetry.addData(route, "finish second drive");
        telemetry.update();

        turnDrive(TurnDirection.RIGHT, 120, 3);
        telemetry.addData(route, "finish third turn");
        telemetry.update();

        encoderDrive(DRIVE_SPEED, -31,-31,3);
        telemetry.addData(route, "finish fourth drive");
        telemetry.update();

        turnDrive(TurnDirection.LEFT, 35, 3);
        telemetry.addData(route, "finish fifth turn");
        telemetry.update();

        encoderDrive(DRIVE_SPEED, -45,-45,5);
        telemetry.addData(route, "finish sixth drive");
        telemetry.update();

        md.move(1);

        encoderDrive(DRIVE_SPEED, 64, 64,15);
        telemetry.addData(route, "finish seventh drive");
        telemetry.update();

    }

}
