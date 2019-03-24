package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.TimeUnit;

public class TrapezoidSpeed
{
    private ElapsedTime runTime = new ElapsedTime();

    public void setTrapezoidTarget(DcMotor thisMotor, double targetPower, int target)
    {
        int distance = target - thisMotor.getCurrentPosition();
        int plateauInterval = distance / 2;
        int changingInterval = distance / 4;
        double divisor = (targetPower * 10) * 8;
        double interval = changingInterval / divisor;
        int lastPosition;
        double power = 0.1;

        thisMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        thisMotor.setPower(power);
        thisMotor.setTargetPosition(target);
        lastPosition = thisMotor.getCurrentPosition();

        runTime.reset();
        while (lastPosition != target && runTime.time(TimeUnit.SECONDS) < 4)
        {
            if ((Math.abs(lastPosition + interval) <= Math.abs(thisMotor.getCurrentPosition()) + 50 && Math.abs(lastPosition + interval) >= Math.abs(thisMotor.getCurrentPosition()) - 50) && power != targetPower)
            {
                power = power + 0.0125;
                lastPosition = thisMotor.getCurrentPosition();
                thisMotor.setPower(power);
            }
            else if ((power == targetPower) && (lastPosition + plateauInterval != thisMotor.getCurrentPosition()))
            {
            }
            else if ((Math.abs(lastPosition + plateauInterval + interval) <= Math.abs(thisMotor.getCurrentPosition()) + 50 && Math.abs(lastPosition + plateauInterval + interval) >= Math.abs(thisMotor.getCurrentPosition()) - 50) && target != thisMotor.getCurrentPosition())
            {
                power = power - 0.0125;
                lastPosition = thisMotor.getCurrentPosition();
                thisMotor.setPower(power);
            }
            else if (Math.abs(lastPosition) <= Math.abs(target) + 10 && Math.abs(lastPosition) >= Math.abs(target) - 10)
            {
                thisMotor.setPower(0);
            }
        }

        if (runTime.time(TimeUnit.SECONDS) > 4)
        {
            thisMotor.setPower(0);
        }
    }
}
