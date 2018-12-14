package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "IMU left 180", group = "Exercises")
public class DriveIMUTurnLeft180 extends DriveIMUTurns
{
    @Override
    protected void testDrive()
    {
        imuTrain.turnDrive(this, 180, 5);
    }
}
