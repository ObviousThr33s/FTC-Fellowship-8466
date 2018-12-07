package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name = "Kyla v2.0 CraterCenter", group = "Samwise")
@Disabled
public class SamwiseDriveRouteCraterCenter extends SamwiseAutoDrive {

    String route = "crater center";

    SamwiseAutoDrive parent;

    public SamwiseDriveRouteCraterCenter(){
        //default
    }

    /**
     * Construct from parent class
     * @param parent
     */
    public SamwiseDriveRouteCraterCenter(SamwiseAutoDrive parent){
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
        init(false);

        waitForStart();

        drive();

        //super.runOpMode();
    }

    protected void drive() {

        //common drive defined by the parent
        //telemetryNow(route, "starting parent common drive ...");
        System.out.println("==>Driving route "+route);

        super.drive();

        this.robot.encoderDrive(this, 22, 22, 5);
        //telemetry.addData(route, "finish second drive");
        //telemetry.update();

        this.robot.encoderDrive(this, -8, -7, 4);
        //telemetry.addData(route, "finish third drive");
        //telemetry.update();

        this.robot.turnDrive(this, -90, 5);
        telemetry.addData(route, "finish fourth turn");
        //telemetry.update();

        this.robot.encoderDrive(this, -49,-49,20);
        telemetry.addData(route, "finish fifth drive");
        //telemetry.update();

        this.robot.turnDrive(this, 52 , 3);
        telemetry.addData(route, "finish sixth turn");
        //telemetry.update();

        this.robot.encoderDrive(this, -49,-49, 20);
        telemetry.addData(route, "finish seventh drive");
        //telemetry.update();

        md.move(1);

        this.robot.encoderDrive(this, 70, 70,20);
        telemetry.addData(route, "finish eighth drive");
        telemetry.update();


    }

}
