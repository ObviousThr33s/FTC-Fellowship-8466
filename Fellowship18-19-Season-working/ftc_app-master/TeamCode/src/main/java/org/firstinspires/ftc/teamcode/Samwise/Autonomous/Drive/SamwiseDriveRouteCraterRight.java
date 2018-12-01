package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Samwise.Autonomous.MarkerDeposit.SamwiseMarkerDeposit;

@Autonomous(name = "Kyla v1.1 CraterRight", group = "Samwise")
public class SamwiseDriveRouteCraterRight extends SamwiseAutoDrive {

    String route = "crater right";

    SamwiseAutoDrive parent;

    public SamwiseDriveRouteCraterRight(){
        //default
    }

    /**
     * Construct from parent class
     * @param parent
     */
    public SamwiseDriveRouteCraterRight(SamwiseAutoDrive parent){
        this.parent = parent;

        this.robot = parent.robot;
        this.md = parent.md;

        this.telemetry = parent.telemetry;
    }

    @Override
    protected boolean isOpModeActive(){
        return parent.opModeIsActive();
    }

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
        //telemetryNow(route, "starting parent common drive ...");
        super.drive();

        turnDrive(TurnDirection.RIGHT, 30.429725, 2);
        telemetry.addData(route, "finish first turn");
        //telemetry.update();

        encoderDrive(DRIVE_SPEED, 28, 28, 5);
        telemetry.addData(route, "finish second drive");
        //telemetry.update();

        encoderDrive(DRIVE_SPEED, -8, -8, 4);
        telemetry.addData(route, "finish third drive");
        //telemetry.update();

        turnDrive(TurnDirection.RIGHT, 65, 5);
        telemetry.addData(route, "finish fourth turn");
        //telemetry.update();

        encoderDrive(DRIVE_SPEED, -58,-58,20);
        telemetry.addData(route, "finish fifth drive");
        //telemetry.update();

        turnDrive(TurnDirection.LEFT, 48, 3);
        telemetry.addData(route, "finish sixth turn");
        //telemetry.update();

        encoderDrive(DRIVE_SPEED, -48,-48, 20);
        telemetry.addData(route, "finish seventh drive");
        //telemetry.update();

        md.move(1);

        encoderDrive(DRIVE_SPEED, 69, 69,20);
        telemetry.addData(route, "finish eighth drive");
        telemetry.update();

    }

}
