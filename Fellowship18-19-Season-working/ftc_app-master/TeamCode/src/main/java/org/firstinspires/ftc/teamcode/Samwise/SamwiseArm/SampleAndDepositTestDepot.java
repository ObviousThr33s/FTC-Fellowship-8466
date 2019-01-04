package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
public class SampleAndDepositTestDepot extends LinearOpMode
{
    public SamwiseArm armStuff = null;
    private ElapsedTime runTime = null;
    double timeoutS = 5;

    @Override
    public void runOpMode() throws InterruptedException
    {
        armStuff.toSamplePositionRight();

        while (runTime.seconds() < timeoutS)
                    {
                        armStuff.collectMinerals();
                    }
            armStuff.silverDropPoint();

        while (runTime.seconds() < timeoutS)
            //        {
            //            armStuff.depositMinerals();
            //        }
            armStuff.toInitialPosition();
    }
}