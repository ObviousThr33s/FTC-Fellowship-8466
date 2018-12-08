package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "Kyla v1.0 CraterCenter", group = "Samwise")
public  class SamwiseDriveRouteCraterCenter extends SamwiseDriveRouteTest {

    @Override
    protected void drive()
    {
        ISamwiseDriveRoute route=SamwiseDriveRouteFactory.createCraterCenter(this);
        route.drive();
    }
}
