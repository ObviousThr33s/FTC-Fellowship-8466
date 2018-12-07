package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name = "Depot Left", group = "Samwise")
@Disabled
public class SamwiseDriveRouteDepotLeft extends SamwiseAutoDrive {

    String route = "depot left";

    SamwiseAutoDrive parent;

    public SamwiseDriveRouteDepotLeft(){
        //default
    }

    /**
     * Construct from parent class
     * @param parent
     */
    public SamwiseDriveRouteDepotLeft(SamwiseAutoDrive parent){
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
        //telemetryNow(route, "starting parent common drive ...");        System.out.println("==>Driving route "+route);
        System.out.println("==>Driving route "+route);

        super.drive();

        // specific drive for this route

        this.robot.turnDrive(this, 28, 3);

        this.robot.encoderDrive(this,30, 30, 4);

        this.robot.turnDrive(this,-50,3);

        this.robot.encoderDrive(this, 38,38,4);

        this.robot.turnDrive(this, -107, 3);

        md.move(1);

        this.robot.encoderDrive(this,73,73, 5);

    }

}
