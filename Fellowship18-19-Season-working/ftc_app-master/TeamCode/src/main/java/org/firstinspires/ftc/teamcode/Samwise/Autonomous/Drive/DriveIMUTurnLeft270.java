package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "IMU left 270", group = "Exercises")
public class DriveIMUTurnLeft270 extends DriveIMUTurns
{
    @Override
    protected void testDrive()
    {
        imuTrain.turnDrive(this,270,5);
    }
}
