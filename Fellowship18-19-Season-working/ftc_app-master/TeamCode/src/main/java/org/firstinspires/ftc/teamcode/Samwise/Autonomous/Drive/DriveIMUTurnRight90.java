package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "IMU right 90", group = "Exercises")
public class DriveIMUTurnRight90 extends DriveIMUTurns
{
    @Override
    protected void testDrive()
    {
        imuTrain.turnDrive(this, -90, 5);
    }
}
