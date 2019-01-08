package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Sampling;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Samwise.SamwiseArm.SamwiseArm;

@Autonomous
public class SampleAndDepositTestCrater extends LinearOpMode
{
    public SamwiseArm armStuff = null;
    private ElapsedTime runTime = new ElapsedTime();
    double timeoutS = 5;


    @Override
    public void runOpMode() throws InterruptedException
    {
        armStuff = new SamwiseArm(this.hardwareMap);

        waitForStart();

        runTime.reset();

        armStuff.toSamplePositionRight();

        while (runTime.seconds() < timeoutS)
        {
            armStuff.collectMinerals();
        }

         armStuff.goldSampleDropCrater();

        runTime.reset();

        while (runTime.seconds() < timeoutS)
        {
            armStuff.depositMinerals();
        }

         armStuff.toInitialPosition();
    }
}
