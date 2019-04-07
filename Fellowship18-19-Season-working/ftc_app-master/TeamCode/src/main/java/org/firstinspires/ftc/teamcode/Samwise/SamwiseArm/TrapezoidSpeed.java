package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.util.concurrent.TimeUnit;

public class TrapezoidSpeed
{
    private ElapsedTime runTime = new ElapsedTime();

    public void setTrapezoidTarget(DcMotor motor, double targetPower, int target)
    {
        int    distance         = target - motor.getCurrentPosition();
        int    plateauInterval  = distance / 2;
        int    changingInterval = distance / 4;
        double divisor          = (targetPower * 10) * 8;
        double interval         = changingInterval / divisor;
        int    lastPosition;
        double power            = 0.1;

        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(power);
        motor.setTargetPosition(target);
        lastPosition = motor.getCurrentPosition();

        runTime.reset();

        while (lastPosition != target && runTime.time(TimeUnit.SECONDS) < 4)
        {
            if ((Math.abs(lastPosition + interval) <= Math.abs(motor.getCurrentPosition()) + 50 && Math.abs(lastPosition + interval) >= Math.abs(motor.getCurrentPosition()) - 50) && power != targetPower)
            {
                power = power + 0.0125;
                lastPosition = motor.getCurrentPosition();
                motor.setPower(power);
            }
            else if ((power == targetPower) && (lastPosition + plateauInterval != motor.getCurrentPosition()))
            {
            }
            else if ((Math.abs(lastPosition + plateauInterval + interval) <= Math.abs(motor.getCurrentPosition()) + 50 && Math.abs(lastPosition + plateauInterval + interval) >= Math.abs(motor.getCurrentPosition()) - 50) && target != motor.getCurrentPosition())
            {
                power = power - 0.0125;
                lastPosition = motor.getCurrentPosition();
                motor.setPower(power);
            }
            else if (Math.abs(lastPosition) <= Math.abs(target) + 10 && Math.abs(lastPosition) >= Math.abs(target) - 10)
            {
                motor.setPower(0);
            }
        }

        if (runTime.time(TimeUnit.SECONDS) > 4)
        {
            motor.setPower(0);
        }
    }

    public void set2TrapezoidTarget(DcMotor motor1, DcMotor motor2, double targetPower1, double targetPower2, int target1, int target2)
    {
        int    distance1         = target1 - motor1.getCurrentPosition();
        int    plateauInterval1  = distance1 / 2;
        int    changingInterval1 = distance1 / 4;
        double divisor1          = (targetPower1 * 10) * 8;
        double interval1         = changingInterval1 / divisor1;
        int    lastPosition1;
        double power1            = 0.1;

        int    distance2         = target2 - motor2.getCurrentPosition();
        int    plateauInterval2  = distance2 / 2;
        int    changingInterval2 = distance2 / 4;
        double divisor2          = (targetPower2 * 10) * 8;
        double interval2         = changingInterval2 / divisor2;
        int    lastPosition2;
        double power2            = 0.1;

        motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor1.setPower(power1);
        motor1.setTargetPosition(target1);
        lastPosition1 = motor1.getCurrentPosition();

        motor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2.setPower(power2);
        motor2.setTargetPosition(target2);
        lastPosition2 = motor2.getCurrentPosition();

        runTime.reset();

        while (runTime.time(TimeUnit.SECONDS) < 4)
        {
            if (lastPosition1 != target1)
            {
                if ((Math.abs(lastPosition1 + interval1) <= Math.abs(motor1.getCurrentPosition()) + 50 && Math.abs(lastPosition1 + interval1) >= Math.abs(motor1.getCurrentPosition()) - 50) && power1 != targetPower1)
                {
                    power1 = power1 + 0.0125;
                    lastPosition1 = motor1.getCurrentPosition();
                    motor1.setPower(power1);
                }
                else if ((power1 == targetPower1) && (lastPosition1 + plateauInterval1 != motor1.getCurrentPosition()))
                {
                }
                else if ((Math.abs(lastPosition1 + plateauInterval1 + interval1) <= Math.abs(motor1.getCurrentPosition()) + 50 && Math.abs(lastPosition1 + plateauInterval1 + interval1) >= Math.abs(motor1.getCurrentPosition()) - 50) && target1 != motor1.getCurrentPosition())
                {
                    power1 = power1 - 0.0125;
                    lastPosition1 = motor1.getCurrentPosition();
                    motor1.setPower(power1);
                }
                else if (Math.abs(lastPosition1) <= Math.abs(target1) + 10 && Math.abs(lastPosition1) >= Math.abs(target1) - 10)
                {
                    motor1.setPower(0);
                }
            }

            if (lastPosition2 != target2)
            {
                if ((Math.abs(lastPosition2 + interval2) <= Math.abs(motor2.getCurrentPosition()) + 50 && Math.abs(lastPosition2 + interval2) >= Math.abs(motor2.getCurrentPosition()) - 50) && power2 != targetPower2)
                {
                    power2 = power2 + 0.0125;
                    lastPosition2 = motor2.getCurrentPosition();
                    motor2.setPower(power2);
                }
                else if ((power2 == targetPower2) && (lastPosition2 + plateauInterval2 != motor2.getCurrentPosition()))
                {
                }
                else if ((Math.abs(lastPosition2 + plateauInterval2 + interval2) <= Math.abs(motor2.getCurrentPosition()) + 50 && Math.abs(lastPosition2 + plateauInterval2 + interval2) >= Math.abs(motor2.getCurrentPosition()) - 50) && target2 != motor2.getCurrentPosition())
                {
                    power2 = power2 - 0.0125;
                    lastPosition2 = motor2.getCurrentPosition();
                    motor2.setPower(power2);
                }
                else if (Math.abs(lastPosition2) <= Math.abs(target2) + 10 && Math.abs(lastPosition2) >= Math.abs(target2) - 10)
                {
                    motor2.setPower(0);
                }
            }
        }

        if (runTime.time(TimeUnit.SECONDS) > 4)
        {
            motor1.setPower(0);
            motor2.setPower(0);
        }
    }

    public void set3TrapezoidTarget(DcMotor motor1, DcMotor motor2, DcMotor motor3, double targetPower1, double targetPower2, double targetPower3, int target1, int target2, int target3)
    {
        int    distance1         = target1 - motor1.getCurrentPosition();
        int    plateauInterval1  = distance1 / 2;
        int    changingInterval1 = distance1 / 4;
        double divisor1          = (targetPower1 * 10) * 8;
        double interval1         = changingInterval1 / divisor1;
        int    lastPosition1;
        double power1            = 0.1;

        int    distance2         = target2 - motor2.getCurrentPosition();
        int    plateauInterval2  = distance2 / 2;
        int    changingInterval2 = distance2 / 4;
        double divisor2          = (targetPower2 * 10) * 8;
        double interval2         = changingInterval2 / divisor2;
        int    lastPosition2;
        double power2            = 0.1;

        int    distance3         = target3 - motor3.getCurrentPosition();
        int    plateauInterval3  = distance3 / 2;
        int    changingInterval3 = distance3 / 4;
        double divisor3          = (targetPower3 * 10) * 8;
        double interval3         = changingInterval3 / divisor3;
        int    lastPosition3;
        double power3            = 0.1;

        motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor1.setPower(power1);
        motor1.setTargetPosition(target1);
        lastPosition1 = motor1.getCurrentPosition();

        motor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2.setPower(power2);
        motor2.setTargetPosition(target2);
        lastPosition2 = motor2.getCurrentPosition();

        motor3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor3.setPower(power3);
        motor3.setTargetPosition(target3);
        lastPosition3 = motor3.getCurrentPosition();

        runTime.reset();

        while (runTime.time(TimeUnit.SECONDS) < 4)
        {
            if (lastPosition1 != target1)
            {
                if ((Math.abs(lastPosition1 + interval1) <= Math.abs(motor1.getCurrentPosition()) + 50 && Math.abs(lastPosition1 + interval1) >= Math.abs(motor1.getCurrentPosition()) - 50) && power1 != targetPower1)
                {
                    power1 = power1 + 0.0125;
                    lastPosition1 = motor1.getCurrentPosition();
                    motor1.setPower(power1);
                }
                else if ((power1 == targetPower1) && (lastPosition1 + plateauInterval1 != motor1.getCurrentPosition()))
                {
                }
                else if ((Math.abs(lastPosition1 + plateauInterval1 + interval1) <= Math.abs(motor1.getCurrentPosition()) + 50 && Math.abs(lastPosition1 + plateauInterval1 + interval1) >= Math.abs(motor1.getCurrentPosition()) - 50) && target1 != motor1.getCurrentPosition())
                {
                    power1 = power1 - 0.0125;
                    lastPosition1 = motor1.getCurrentPosition();
                    motor1.setPower(power1);
                }
                else if (Math.abs(lastPosition1) <= Math.abs(target1) + 10 && Math.abs(lastPosition1) >= Math.abs(target1) - 10)
                {
                    motor1.setPower(0);
                }
            }

            if (lastPosition2 != target2 && runTime.time(TimeUnit.SECONDS) < 4)
            {
                if ((Math.abs(lastPosition2 + interval2) <= Math.abs(motor2.getCurrentPosition()) + 50 && Math.abs(lastPosition2 + interval2) >= Math.abs(motor2.getCurrentPosition()) - 50) && power2 != targetPower2)
                {
                    power2 = power2 + 0.0125;
                    lastPosition2 = motor2.getCurrentPosition();
                    motor2.setPower(power2);
                }
                else if ((power2 == targetPower2) && (lastPosition2 + plateauInterval2 != motor2.getCurrentPosition()))
                {
                }
                else if ((Math.abs(lastPosition2 + plateauInterval2 + interval2) <= Math.abs(motor2.getCurrentPosition()) + 50 && Math.abs(lastPosition2 + plateauInterval2 + interval2) >= Math.abs(motor2.getCurrentPosition()) - 50) && target2 != motor2.getCurrentPosition())
                {
                    power2 = power2 - 0.0125;
                    lastPosition2 = motor2.getCurrentPosition();
                    motor2.setPower(power2);
                }
                else if (Math.abs(lastPosition2) <= Math.abs(target2) + 10 && Math.abs(lastPosition2) >= Math.abs(target2) - 10)
                {
                    motor2.setPower(0);
                }
            }

            if (lastPosition3 != target3 && runTime.time(TimeUnit.SECONDS) < 4)
            {
                if ((Math.abs(lastPosition3 + interval3) <= Math.abs(motor3.getCurrentPosition()) + 50 && Math.abs(lastPosition3 + interval3) >= Math.abs(motor3.getCurrentPosition()) - 50) && power3 != targetPower3)
                {
                    power3 = power3 + 0.0125;
                    lastPosition3 = motor3.getCurrentPosition();
                    motor3.setPower(power3);
                }
                else if ((power3 == targetPower3) && (lastPosition3 + plateauInterval3 != motor3.getCurrentPosition()))
                {
                }
                else if ((Math.abs(lastPosition3 + plateauInterval3 + interval3) <= Math.abs(motor3.getCurrentPosition()) + 50 && Math.abs(lastPosition3 + plateauInterval3 + interval3) >= Math.abs(motor3.getCurrentPosition()) - 50) && target3 != motor3.getCurrentPosition())
                {
                    power3 = power3 - 0.0125;
                    lastPosition3 = motor3.getCurrentPosition();
                    motor3.setPower(power3);
                }
                else if (Math.abs(lastPosition3) <= Math.abs(target3) + 10 && Math.abs(lastPosition3) >= Math.abs(target3) - 10)
                {
                    motor3.setPower(0);
                }
            }
        }

        if (runTime.time(TimeUnit.SECONDS) > 4)
        {
            motor1.setPower(0);
            motor2.setPower(0);
            motor3.setPower(0);
        }
    }
}
