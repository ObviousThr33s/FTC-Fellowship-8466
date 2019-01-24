package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name = "Crater Rim Test", group = "Samwise")
//@Disabled
public class SamwiseDriveCraterRimTest extends SamwiseDriveRouteTest {

    @Override
    protected void drive()
    {
        ISamwiseDriveRoute route=SamwiseDriveRouteFactory.createCraterRimTest(this);
        route.drive();
    }

}
