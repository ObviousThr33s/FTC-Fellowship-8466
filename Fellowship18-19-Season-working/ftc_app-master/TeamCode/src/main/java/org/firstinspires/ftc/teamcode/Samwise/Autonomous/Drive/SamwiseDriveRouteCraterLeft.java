package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name = "Kyla v1.0 CraterLeft", group = "Samwise")
//@Disabled
public class SamwiseDriveRouteCraterLeft extends SamwiseAutoDrive {

    String route = "crater left";


    SamwiseAutoDrive parent;


    /**
     * Construct from parent class
     * @param parent
     */
    public SamwiseDriveRouteCraterLeft(SamwiseAutoDrive parent){
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

        this.robot.turnDrive(this, 32.429725, 2);
        telemetry.addData(route, "finish first turn");
        //telemetry.update();

        this.robot.encoderDrive(this, 28, 28, 3);
        telemetry.addData(route, "finish second drive");
        //telemetry.update();

        this.robot.turnDrive(this, -120, 3);
        telemetry.addData(route, "finish third turn");
        //telemetry.update();

        this.robot.encoderDrive(this, -31,-31,3);
        telemetry.addData(route, "finish fourth drive");
        //telemetry.update();

        this.robot.turnDrive(this, 35, 3);
        telemetry.addData(route, "finish fifth turn");
        //telemetry.update();

        this.robot.encoderDrive(this, -45,-45,5);
        telemetry.addData(route, "finish sixth drive");
        //telemetry.update();

        md.move(1);

        this.robot.encoderDrive(this, 64, 64,15);
        telemetry.addData(route, "finish seventh drive");
        telemetry.update();

    }

}
