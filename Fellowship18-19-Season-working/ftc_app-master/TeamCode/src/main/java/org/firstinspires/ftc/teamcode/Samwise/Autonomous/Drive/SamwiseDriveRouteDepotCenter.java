package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name = "Depot Center", group = "Samwise")
//@Disabled
public class SamwiseDriveRouteDepotCenter extends SamwiseDriveRouteTest {

    @Override
    protected void drive()
    {
        ISamwiseDriveRoute route=SamwiseDriveRouteFactory.createDepotCenter(this);
        route.drive();
    }

}
