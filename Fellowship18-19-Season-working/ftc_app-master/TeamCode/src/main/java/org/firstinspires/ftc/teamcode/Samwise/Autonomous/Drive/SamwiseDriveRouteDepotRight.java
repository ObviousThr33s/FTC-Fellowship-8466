package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name = "Depot Right", group = "Samwise")
@Disabled
public class SamwiseDriveRouteDepotRight extends SamwiseAutoDrive {

    String route = "depot right";

    SamwiseAutoDrive parent;

    public SamwiseDriveRouteDepotRight(){
        //default
    }

    /**
     * Construct from parent class
     * @param parent
     */
    public SamwiseDriveRouteDepotRight(SamwiseAutoDrive parent){
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
        System.out.println("==>Driving route "+route);

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
