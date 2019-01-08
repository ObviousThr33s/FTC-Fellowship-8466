package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Sampling;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Samwise.SamwiseArm.SamwiseArm;

@Autonomous
public class SampleAndDepositTestDepot extends LinearOpMode
{
    public SamwiseArm armStuff = null;
    private ElapsedTime runTime = null;
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

        armStuff.goldSampleDropDepot();

        runTime.reset();

        while (runTime.seconds() < timeoutS)
        {
            armStuff.depositMinerals();
        }

        armStuff.toInitialPosition();
    }
}
