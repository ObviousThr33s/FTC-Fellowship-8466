package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "IMU left 360", group = "Exercises")
public class DriveIMUTurnLeft360 extends DriveIMUTurns
{

    @Override
    protected void testDrive()
    {
        imuTrain.turnDrive(this, 360, 5);
    }

}
