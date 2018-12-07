package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name = "Kyla v1.0 CraterLeft", group = "Samwise")
//@Disabled
public class SamwiseDriveRouteCraterLeft extends SamwiseDriveRouteTest {

    String route = "crater left";

    /**
     * Construct from parent class
     *
     * @param parent
     */
    public SamwiseDriveRouteCraterLeft(SamwiseAutoDrive parent)
    {
        super(parent);
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
