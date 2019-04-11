package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;


import java.util.concurrent.TimeUnit;

public class SamwiseArm extends OctoSamwiseCollection
{
    DcMotor motorJ1;
    DcMotor motor1J2;
    DcMotor motor2J2;
    DcMotor motorJ3;
    DcMotor motorE1;
    CRServo servoE2;

    static final int E1_MAX_COUNT = 4661;

    public static final double MANUAL_POWER_J1 = 0.8;
    public static final double MANUAL_POWER_J2 = 0.4;
    public static final double MANUAL_POWER_J3 = 0.4;
    public static final double E1_POWER = 0.8;
    public static final double E2_POWER = 0.8;

    static final int TIME_POWER_RAMP_UP = 100;
    static final int TIME_POWER_RAMP_DOWN = 100;

    private ElapsedTime runtime = new ElapsedTime();

    static final double ZERO_MANUAL_POWER = 0.01;

    boolean isManualJ1 = false;
    boolean isManualJ2 = false;
    boolean isManualJ3 = false;

    public SamwiseArm(HardwareMap hwm)
    {
        super(hwm);

        //Instantiate hardware
        motorJ1 = hwm.dcMotor.get("J1");
        motor1J2 = hwm.dcMotor.get("1J2");
        motor2J2 = hwm.dcMotor.get("2J2");
        motorJ3 = hwm.dcMotor.get("J3");
        motorE1 = hwm.dcMotor.get("E1");
        servoE2 = hwm.crservo.get("E2");

        motorJ1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorJ1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor1J2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor1J2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor1J2.setDirection(DcMotorSimple.Direction.REVERSE);
        motor2J2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor2J2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor2J2.setDirection(DcMotorSimple.Direction.REVERSE);
        motorJ3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorJ3.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorE1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorJ3.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void manualDriveJ1(double power)
    {
        if (Math.abs(power) > 0.1)
        {
            this.isManualJ1 = true;

            motorJ1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //smooth start
            if (Math.abs(motorJ1.getPower()) < 0.1)
            {
                runtime.reset();
                long milliTime = runtime.time(TimeUnit.MILLISECONDS);
                while (milliTime < TIME_POWER_RAMP_UP)
                {
                    motorJ1.setPower(-MANUAL_POWER_J1 * power * (Double.valueOf(milliTime) / TIME_POWER_RAMP_UP));
                    milliTime = runtime.time(TimeUnit.MILLISECONDS);
                }
            }
            else
            {
                double newPower = -MANUAL_POWER_J1 * power;
                motorJ1.setPower(newPower);
            }
        }
    }

    public void manualStopJ1()
    {
        if (!this.isManualJ1) return;

        if (Math.abs(motorJ1.getPower()) > ZERO_MANUAL_POWER)
        {
            runtime.reset();
            long milliTime = runtime.time(TimeUnit.MILLISECONDS);
            while (milliTime < TIME_POWER_RAMP_DOWN)
            {
                if (milliTime > 1)
                {
                    motorJ1.setPower(-MANUAL_POWER_J1 * motorJ1.getPower() * (1.0 / milliTime));
                }
                milliTime = runtime.time(TimeUnit.MILLISECONDS);
            }
            motorJ1.setPower(0);
            this.isManualJ1 = false;
            System.out.println("manualStopJ1");
        }
    }


    public void manualDriveJ3(double power)
    {
        if (Math.abs(power) > 0.1)
        {
            this.isManualJ3 = true;

            motorJ3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            if (Math.abs(motorJ3.getPower()) < 0.1)
            {
                runtime.reset();
                long milliTime = runtime.time(TimeUnit.MILLISECONDS);
                while (milliTime < TIME_POWER_RAMP_UP)
                {
                    motorJ3.setPower(-MANUAL_POWER_J3 * power * (Double.valueOf(milliTime) / TIME_POWER_RAMP_UP));
                    milliTime = runtime.time(TimeUnit.MILLISECONDS);
                }
            }
            else
            {
                double newPower = -MANUAL_POWER_J3 * power;
                motorJ3.setPower(newPower);
            }
            System.out.println("manual driveJ3");
        }
    }

    public void manualStopJ3()
    {
        if (!this.isManualJ3) return;

        if (Math.abs(motorJ3.getPower()) > ZERO_MANUAL_POWER)
        {
            runtime.reset();
            long milliTime = runtime.time(TimeUnit.MILLISECONDS);
            while (milliTime < TIME_POWER_RAMP_DOWN)
            {
                if (milliTime > 1)
                {
                    motorJ3.setPower(-MANUAL_POWER_J1 * motorJ3.getPower() * (1.0 / milliTime));
                }
                milliTime = runtime.time(TimeUnit.MILLISECONDS);
            }
            motorJ3.setPower(0);
            this.isManualJ3=false;
            System.out.println("manualStopJ3");
        }
    }

    public void manualDriveJ2(double power)
    {
        if (Math.abs(power) > 0.1)
        {
            this.isManualJ2 = true;

            motor1J2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor2J2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            if (Math.abs(motor1J2.getPower()) < 0.1 || Math.abs(motor2J2.getPower()) < 0.1)
            {
                runtime.reset();
                long milliTime = runtime.time(TimeUnit.MILLISECONDS);
                while (milliTime < TIME_POWER_RAMP_UP)
                {
                    motor1J2.setPower(-MANUAL_POWER_J2 * power * (Double.valueOf(milliTime) / TIME_POWER_RAMP_UP));
                    motor2J2.setPower(-MANUAL_POWER_J2 * power * (Double.valueOf(milliTime) / TIME_POWER_RAMP_UP));
                    milliTime = runtime.time(TimeUnit.MILLISECONDS);
                }
            }
            else
            {
                double newPower = -MANUAL_POWER_J2 * power;
                motor1J2.setPower(newPower);
                motor2J2.setPower(newPower);
            }
            System.out.println("manual driveJ2");
        }
    }

    public void manualStopJ2()
    {
        if (!this.isManualJ2) return;

        if (Math.abs(motor1J2.getPower()) > ZERO_MANUAL_POWER && Math.abs(motor2J2.getPower()) > ZERO_MANUAL_POWER)
        {
            runtime.reset();
            long milliTime = runtime.time(TimeUnit.MILLISECONDS);
            while (milliTime < TIME_POWER_RAMP_DOWN)
            {
                if (milliTime > 1)
                {
                    motor1J2.setPower(-MANUAL_POWER_J1 * motor1J2.getPower() * (1.0 / milliTime));
                    motor2J2.setPower(-MANUAL_POWER_J1 * motor2J2.getPower() * (1.0 / milliTime));
                }
                milliTime = runtime.time(TimeUnit.MILLISECONDS);
            }
            motor1J2.setPower(0);
            motor2J2.setPower(0);
            this.isManualJ2 = false;
            System.out.println("manualStopJ2");
        }
    }

    public int getJ1CurrentPosition()
    {
        return motorJ1.getCurrentPosition();
    }

    public int getJ2CurrentPosition()
    {
        return motor1J2.getCurrentPosition();
    }

    public int get2J2CurrentPosition()
    {
        return motor2J2.getCurrentPosition();
    }

    public int getJ3CurrentPosition()
    {
        return motorJ3.getCurrentPosition();
    }

    public int getE1CurrentPosition()
    {
        return motorE1.getCurrentPosition();
    }

    /************************************************************************************************
     *               For OCTO Arm Only: Arm Extension/Retraction                                    *
     ************************************************************************************************/

    public void retractL1Auto()
    {
        motorE1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorE1.setDirection(DcMotorSimple.Direction.FORWARD);
        motorE1.setPower(0.2);
        motorE1.setTargetPosition(0);
    }

    public void extendL2Auto()
    {
        servoE2.setDirection(DcMotorSimple.Direction.FORWARD);
        servoE2.setPower(E2_POWER);
        try
        {
            Thread.sleep(25000);
        }
        catch (Exception e) {}
    }

    public void stopExtendL1()
    {
        motorE1.setPower(0);
    }

    public void stopExtendL2()
    {
        servoE2.setPower(0);
    }

    public void extendL1Auto()
    {
        motorE1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorE1.setDirection(DcMotorSimple.Direction.REVERSE);
        motorE1.setPower(0.8);
        motorE1.setTargetPosition(E1_MAX_COUNT);
    }

    public void retractL2Auto()
    {
        servoE2.setDirection(DcMotorSimple.Direction.REVERSE);
        servoE2.setPower(E2_POWER);
        try
        {
            Thread.sleep(25000);
        }
        catch (Exception e) {}
    }

    public void extendL1()
    {
        motorE1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorE1.setDirection(DcMotorSimple.Direction.REVERSE);
        motorE1.setPower(E1_POWER);
    }

    public void retractL1()
    {
        motorE1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorE1.setDirection(DcMotorSimple.Direction.FORWARD);
        motorE1.setPower(0.2);
    }

    public void retractL2()
    {
        servoE2.setDirection(DcMotorSimple.Direction.REVERSE);
        servoE2.setPower(E2_POWER);
    }

    public void extendL2()
    {
        servoE2.setDirection(DcMotorSimple.Direction.FORWARD);
        servoE2.setPower(E2_POWER);
    }
}