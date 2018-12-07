package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.Samwise.Autonomous.MarkerDeposit.SamwiseMarkerDeposit;

@Autonomous(name = "Kyla v1.1 CraterRight", group = "Samwise")
//@Disabled
public class SamwiseDriveRouteCraterRight extends SamwiseDriveRouteTest {

    String route = "crater right";

    /**
     * Construct from parent class
     *
     * @param parent
     */
    public SamwiseDriveRouteCraterRight(SamwiseAutoDrive parent)
    {
        super(parent);
    }

    protected void drive() {

        //common drive defined by the parent
        //telemetryNow(route, "starting parent common drive ...");
        System.out.println("==>Driving route "+route);

        super.drive();

        this.robot.turnDrive( this,-30.429725, 2);
        telemetry.addData(route, "finish first turn");
        //telemetry.update();

        this.robot.encoderDrive(this, 28, 28, 5);
        telemetry.addData(route, "finish second drive");
        //telemetry.update();

        this.robot.encoderDrive(this, -8, -8, 4);
        telemetry.addData(route, "finish third drive");
        //telemetry.update();

        this.robot.turnDrive(this, -65, 5);
        telemetry.addData(route, "finish fourth turn");
        //telemetry.update();

        this.robot.encoderDrive(this, -58,-58,20);
        telemetry.addData(route, "finish fifth drive");
        //telemetry.update();

        this.robot.turnDrive(this, 48, 3);
        telemetry.addData(route, "finish sixth turn");
        //telemetry.update();

        this.robot.encoderDrive(this, -48,-48, 20);
        telemetry.addData(route, "finish seventh drive");
        //telemetry.update();

        md.move(1);

        this.robot.encoderDrive(this, 69, 69,20);
        telemetry.addData(route, "finish eighth drive");
        telemetry.update();

    }

}
