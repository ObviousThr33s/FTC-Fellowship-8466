package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class SamwiseArm extends OctoSamwiseCollection
{
    DcMotor motorJ1;
    DcMotor motor1J2;
    DcMotor motor2J2;
    DcMotor motorJ3;
    DcMotor motorE1;
    CRServo servoE2;

    static final double JOYSTICK_SENSITIVITY = 0.1;

    public static final double MANUAL_POWER_J1 = 0.5;
    public static final double MANUAL_POWER_J2 = 0.4;
    public static final double MANUAL_POWER_J3 = 0.5;
    public static final double E1_POWER = 0.8;
    public static final double E2_POWER = 0.8;

    boolean isManualJ1 = false;
    boolean isManualJ2 = false;
    boolean isManualJ3 = false;

    boolean isInMotionL1 = false;
    boolean isInMotionL2 = false;

    boolean isPOM = false;

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
    }

    public void manualDriveJ1(double power)
    {
        if (this.isPOM) return;

        if (Math.abs(power) > JOYSTICK_SENSITIVITY)
        {
            this.isManualJ1 = true;
            motorJ1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            TrapezoidHelper.trapezoidDriveJ1(motorJ1, -MANUAL_POWER_J1 * power);
        }
    }

    public void manualStopJ1()
    {
        if (this.isPOM) return;

        if (this.isManualJ1)
        {
            TrapezoidHelper.trapezoidStopeJ1(motorJ1);
            this.isManualJ1 = false;
        }
    }


    public void manualDriveJ3(double power)
    {
        if (this.isPOM) return;

        if (Math.abs(power) > JOYSTICK_SENSITIVITY)  // reduce joystick sensitivity
        {
            this.isManualJ3 = true;
            motorJ3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            TrapezoidHelper.trapezoidDriveJ3(motorJ3, -MANUAL_POWER_J3 * power);
        }
    }

    public void manualStopJ3()
    {
        if (this.isPOM) return;

        if (this.isManualJ3)
        {
            TrapezoidHelper.trapezoidStopJ3(motorJ3);
            this.isManualJ3 = false;
        }
    }

    public void manualDriveJ2(double power)
    {
        if (this.isPOM) return;

        if (Math.abs(power) > JOYSTICK_SENSITIVITY)
        {
            this.isManualJ2 = true;
            motor1J2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor2J2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            TrapezoidHelper.trapezoidDriveJ2(motor1J2, motor2J2, -MANUAL_POWER_J2 * power);
        }
    }

    public void manualStopJ2()
    {
        if (this.isPOM) return;

        if (this.isManualJ2)
        {
            TrapezoidHelper.trapezoidStopJ2(motor1J2, motor2J2);
            this.isManualJ2 = false;
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


    public void stopExtendL1()
    {
        if (this.isInMotionL1)
        {
            motorE1.setPower(0);
            this.isInMotionL1 = false;
        }
    }

    public void stopExtendL2()
    {
        if (this.isInMotionL2)
        {
            servoE2.setPower(0);
            this.isInMotionL2 = false;
        }
    }

    public void extendL1()
    {
        motorE1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorE1.setDirection(DcMotorSimple.Direction.REVERSE);
        motorE1.setPower(E1_POWER);
        this.isInMotionL1 = true;
    }

    public void retractL1()
    {
        motorE1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorE1.setDirection(DcMotorSimple.Direction.FORWARD);
        motorE1.setPower(0.2);
        this.isInMotionL1 = true;
    }

    public void retractL2()
    {
        servoE2.setDirection(DcMotorSimple.Direction.REVERSE);
        servoE2.setPower(E2_POWER);
        this.isInMotionL2 = true;
    }

    public void extendL2()
    {
        servoE2.setDirection(DcMotorSimple.Direction.FORWARD);
        servoE2.setPower(E2_POWER);
        this.isInMotionL2 = true;
    }
}