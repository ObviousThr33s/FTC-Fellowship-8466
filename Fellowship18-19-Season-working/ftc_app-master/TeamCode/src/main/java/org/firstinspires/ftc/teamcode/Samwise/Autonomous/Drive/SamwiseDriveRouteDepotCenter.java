package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name = "Depot Center", group = "Samwise")
//@Disabled
public class SamwiseDriveRouteDepotCenter extends SamwiseDriveRouteTest {


    String route = "depot center";

    /**
     * Construct from parent class
     *
     * @param parent
     */
    public SamwiseDriveRouteDepotCenter(SamwiseAutoDrive parent)
    {
        super(parent);
    }
    protected void drive() {

        //common drive defined by the parent
        //telemetryNow(route, "starting parent common drive ...");
        System.out.println("==>Driving route "+route);

        super.drive();

        // specific drive for this route

        this.robot.encoderDrive(this, 60,60,5);
        this.robot.turnDrive(this,-125,3);

        md.move(1);

        this.robot.encoderDrive(this, 70,70,5);

    }

}
