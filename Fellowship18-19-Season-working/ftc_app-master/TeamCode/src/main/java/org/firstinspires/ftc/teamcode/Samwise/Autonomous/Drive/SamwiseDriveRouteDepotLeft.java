package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name = "Depot Left", group = "Samwise")
@Disabled
public class SamwiseDriveRouteDepotLeft extends SamwiseDriveRouteTest {

    @Override
    protected void drive()
    {
        ISamwiseDriveRoute route=SamwiseDriveRouteFactory.createDepotLeft(this);
        route.drive();
    }

}
