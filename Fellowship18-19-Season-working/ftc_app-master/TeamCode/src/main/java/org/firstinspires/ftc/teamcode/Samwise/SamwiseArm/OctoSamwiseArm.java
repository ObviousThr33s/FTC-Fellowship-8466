package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class OctoSamwiseArm extends OctoSamwiseCollection
{
    DcMotor motorJ1;
    DcMotor motor1J2;
    DcMotor motor2J2;
    DcMotor motorJ3;
    DcMotor motorE1;
    CRServo servoE2;

    public int e2Count = 0;

    static final int E1_MAX_COUNT = 4661;

    public static final double MANUAL_POWER_J1 = 0.6;
    public static final double MANUAL_POWER_J2 = 0.2;
    public static final double MANUAL_POWER_J3 = 0.2;
    public static final double E1_POWER = 0.8;
    public static final double E2_POWER = 0.8;

    public OctoSamwiseArm(HardwareMap hwm)
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
        motor2J2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor2J2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorJ3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorJ3.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorE1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void driveJ1(double power)
    {
        motorJ1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motorJ1.setPower(-MANUAL_POWER_J1 * power);
    }



    public void stopJ1()
    {
        motorJ1.setPower(0);
    }

    public void stopJ3()
    {
        motorJ3.setPower(0);
    }

    public void stopJ2()
    {
        motor1J2.setPower(0);
        motor2J2.setPower(0);
    }

    /**
     * When mapped to DPad
     *
     * @param
     */
    public void driveJ2(double power)
    {
        motor1J2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor2J2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motor1J2.setPower(MANUAL_POWER_J2 * power);
        motor2J2.setPower(MANUAL_POWER_J2 * power);
    }


    /**
     * When mapped to DPad
     */
    public void driveJ3(double power)
    {
        motorJ3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motorJ3.setPower(-MANUAL_POWER_J3 * power);
    }


    public int getJ1CurrentPosition()
    {
        return motorJ1.getCurrentPosition();
    }

    public int getJ2CurrentPosition()
    {
        return motor1J2.getCurrentPosition();
    }

    public int getJ3CurrentPosition()
    {
        return motorJ3.getCurrentPosition();
    }

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
        motorE1.setPower(0.8);
    }

    public void retractL2()
    {
        servoE2.setDirection(DcMotorSimple.Direction.REVERSE);
        servoE2.setPower(E2_POWER);
        e2Count--;
    }

    public void retractL1()
    {
        motorE1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorE1.setDirection(DcMotorSimple.Direction.FORWARD);
        motorE1.setPower(0.2);
    }

    public void extendL2()
    {
        servoE2.setDirection(DcMotorSimple.Direction.FORWARD);
        servoE2.setPower(E2_POWER);
        e2Count++;
    }

    public int getE1CurrentPosition()
    {
        return motorE1.getCurrentPosition();
    }
}