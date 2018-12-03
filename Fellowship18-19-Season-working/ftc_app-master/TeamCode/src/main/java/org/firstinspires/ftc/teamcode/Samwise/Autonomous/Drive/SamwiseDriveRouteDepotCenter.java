package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name = "Depot Center", group = "Samwise")
@Disabled
public class SamwiseDriveRouteDepotCenter extends SamwiseAutoDrive {

    String route = "depot center";

    SamwiseAutoDrive parent;

    public SamwiseDriveRouteDepotCenter(){
        //default
    }

    /**
     * Construct from parent class
     * @param parent
     */
    public SamwiseDriveRouteDepotCenter(SamwiseAutoDrive parent){
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
         * call parent to start\
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

        encoderDrive(DRIVE_SPEED, 60,60,5);
        turnDrive(TurnDirection.RIGHT,125,3);

        md.move(1);
        encoderDrive(DRIVE_SPEED, 70,70,5);

    }

}
