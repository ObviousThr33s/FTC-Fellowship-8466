package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name = "Depot Right", group = "Samwise")
@Disabled
public class SamwiseDriveRouteDepotRight extends SamwiseDriveRouteTest {

    @Override
    protected void drive()
    {
        ISamwiseDriveRoute route=SamwiseDriveRouteFactory.createDepotRight(this);
        route.drive();
    }

}
