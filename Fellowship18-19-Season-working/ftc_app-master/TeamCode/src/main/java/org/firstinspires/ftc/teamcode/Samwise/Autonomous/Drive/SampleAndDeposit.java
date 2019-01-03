package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Samwise.SamwiseArm.SamwiseArm;
import org.firstinspires.ftc.teamcode.Samwise.SamwiseArm.SamwiseClaw;

public class SampleAndDeposit
{
    public SamwiseClaw clawStuff = null;
    public SamwiseArm armStuff = null;
    private ElapsedTime runTime = new ElapsedTime();
    double timeoutS = 5;

    public SampleAndDeposit(HardwareMap hwm)
    {
        armStuff = new SamwiseArm(hwm);
        clawStuff = new SamwiseClaw(hwm);
    }

    public void sampleAndDepositDepotLeft()
    {
        runTime.reset();
        armStuff.toSamplePositionLeft();

        while (runTime.seconds() < timeoutS)
        {
            clawStuff.collectMinerals();
        }
        armStuff.goldSampleDropDepot();
        runTime.reset();
        while (runTime.seconds() < timeoutS)
        {
            clawStuff.depositMinerals();
        }
        armStuff.toInitialPosition();
    }

    public void sampleAndDepositDepotCenter()
    {
        runTime.reset();
        armStuff.toSamplePositionCenter();
        while (runTime.seconds() < timeoutS)
        {
            clawStuff.collectMinerals();
        }
        armStuff.goldSampleDropDepot();
        runTime.reset();
        while (runTime.seconds() < timeoutS)
        {
            clawStuff.depositMinerals();
        }
        armStuff.toInitialPosition();
    }

    public void sampleAndDepositDepotRight()
    {
        runTime.reset();
        armStuff.toSamplePositionRight();

        while (runTime.seconds() < timeoutS)
        {
            clawStuff.collectMinerals();
        }
        armStuff.goldSampleDropDepot();
        runTime.reset();
        while (runTime.seconds() < timeoutS)
        {
            clawStuff.depositMinerals();
        }
        armStuff.toInitialPosition();
    }

    public void sampleAndDepositCraterLeft()
    {
        runTime.reset();
        armStuff.toSamplePositionLeft();

        while (runTime.seconds() < timeoutS)
        {
            clawStuff.collectMinerals();
        }
        armStuff.goldSampleDropCrater();
        runTime.reset();
        while (runTime.seconds() < timeoutS)
        {
            clawStuff.depositMinerals();
        }
        armStuff.toInitialPosition();
    }

    public void sampleAndDepositCraterCenter()
    {
        runTime.reset();
        armStuff.toSamplePositionCenter();

        while (runTime.seconds() < timeoutS)
        {
            clawStuff.collectMinerals();
        }
        armStuff.goldSampleDropCrater();
        runTime.reset();
        while (runTime.seconds() < timeoutS)
        {
            clawStuff.depositMinerals();
        }
        armStuff.toInitialPosition();
    }

    public void sampleAndDepositCraterRight()
    {
        runTime.reset();
        armStuff.toSamplePositionRight();

        while (runTime.seconds() < timeoutS)
        {
            clawStuff.collectMinerals();
        }
        armStuff.goldSampleDropCrater();
        runTime.reset();
        while (runTime.seconds() < timeoutS)
        {
            clawStuff.depositMinerals();
        }
        armStuff.toInitialPosition();
    }
}
