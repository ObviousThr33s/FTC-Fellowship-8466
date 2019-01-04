package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
public class SampleAndDepositTestCrater extends LinearOpMode
{
    public SamwiseClaw clawStuff = null;
    private ElapsedTime runTime = new ElapsedTime();
    double timeoutS = 5;


    @Override
    public void runOpMode() throws InterruptedException
    {
        clawStuff = new SamwiseClaw(this.hardwareMap);
        waitForStart();

        runTime.reset();
        //armStuff.toSamplePositionRight();

        while (runTime.seconds() < timeoutS)
        {
            clawStuff.collectMinerals();
        }
        // armStuff.goldDropPoint();
        runTime.reset();
        while (runTime.seconds() < timeoutS)
        {
            clawStuff.depositMinerals();
        }
        // armStuff.toInitialPosition();
    }
}
