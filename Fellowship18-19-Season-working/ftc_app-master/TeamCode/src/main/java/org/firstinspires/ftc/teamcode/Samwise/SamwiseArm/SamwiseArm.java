package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class SamwiseArm extends SamwiseCollection
{
    DcMotor motorJ1;
    DcMotor motorJ2;
    DcMotor motorJ3;

    // for Octo Arm
    DcMotor motorE1;
    CRServo servoE2;

    ElapsedTime runTime = new ElapsedTime();
    static final int TIMEOUT = 100;

    static final double MANUAL_POWER_J1 = 0.15;
    static final double MANUAL_POWER_J2 = 0.1;
    public static final double MAX_POWER_J3 = 0.15;
    public static final double MID_POWER_J3 = 0.1;
    public static final double MIN_POWER_J3 = 0.05;

    public SamwiseArm(HardwareMap hwm)
    {
        super(hwm);

        //Instantiate hardware
        motorJ1 = hwm.dcMotor.get("J1");
        motorJ2 = hwm.dcMotor.get("J2");
        motorJ3 = hwm.dcMotor.get("J3");

        motorJ1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorJ1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorJ2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorJ2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorJ3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorJ3.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void driveJ1(boolean isLeft)
    {
        motorJ1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        if (isLeft)
        {
            motorJ1.setPower(-0.4);
        }
        else
        {
            motorJ1.setPower(0.4);
        }
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
        motorJ2.setPower(0);
    }

    /**
     * When mapped to DPad
     *
     * @param isUp
     */
    public void driveJ2(boolean isUp)
    {
        motorJ2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        if (isUp)
        {
            motorJ2.setPower(-MANUAL_POWER_J2);
        }
        else
        {
            motorJ2.setPower(MANUAL_POWER_J2);
        }
    }

    /**
     * When mapped to DPad
     *
     * @param isUp
     */
    public void driveJ3(boolean isUp)
    {
        motorJ3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        if (isUp)
        {
            if (Math.abs(motorJ3.getCurrentPosition()) <= 996.8)
            {
                motorJ3.setPower(MAX_POWER_J3);
            }
            else if (Math.abs(motorJ3.getCurrentPosition()) > 996.8 && Math.abs(motorJ3.getCurrentPosition()) <= 1993.6)
            {
                motorJ3.setPower(MID_POWER_J3);
            }
            else if (Math.abs(motorJ3.getCurrentPosition()) > 1993.6)
            {
                motorJ3.setPower(MIN_POWER_J3);
            }
        }
        else
        {
            if (Math.abs(motorJ3.getCurrentPosition()) <= 996.8)
            {
                motorJ3.setPower(MIN_POWER_J3);
            }
            else if (Math.abs(motorJ3.getCurrentPosition()) > 996.8 && Math.abs(motorJ3.getCurrentPosition()) <= 1993.6)
            {
                motorJ3.setPower(MID_POWER_J3);
            }
            else if (Math.abs(motorJ3.getCurrentPosition()) > 1993.6)
            {
                motorJ3.setPower(MAX_POWER_J3);
            }
        }
    }


    public int getJ1CurrentPosition()
    {
        return motorJ1.getCurrentPosition();
    }

    public int getJ2CurrentPosition()
    {
        return motorJ2.getCurrentPosition();
    }

    public int getJ3CurrentPosition()
    {
        return motorJ3.getCurrentPosition();
    }

    /************************************************************************************************
     *               For OCTO Arm Only: Arm Extension/Retraction                                    *
     ************************************************************************************************/
    public void extendArms()
    {
        //TODO: may need to add back when OCTO is in use
        /*runTime.reset();
        while (runTime.milliseconds() < TIMEOUT)
        {
            motorE1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorE1.setDirection(DcMotorSimple.Direction.FORWARD);
            servoE2.setDirection(DcMotorSimple.Direction.FORWARD);
            motorE1.setPower(0.2);
            servoE2.setPower(0.8);
        }
        stopExtendServos();*/
    }

    public void stopExtendServos()
    {
        //TODO: may need to add back when OCTO is in use
        /*motorE1.setPower(0);
        servoE2.setPower(0);*/
    }

    public void extendL1()
    {
        //TODO: may need to add back when OCTO is in use
        /*motorE1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorE1.setDirection(DcMotorSimple.Direction.REVERSE);
        motorE1.setPower(0.15);*/
    }

    public void extendL2()
    {
        //TODO: may need to add back when OCTO is in use
        servoE2.setDirection(DcMotorSimple.Direction.FORWARD);
        servoE2.setPower(0.8);
    }

    public void stopExtendL1()
    {
        //TODO: may need to add back when OCTO is in use
        /*motorE1.setPower(0);*/
    }

    public void stopExtendL2()
    {
        //TODO: may need to add back when OCTO is in use
        servoE2.setPower(0);
    }

    public void retractArm()
    {
        //TODO: may need to add back when OCTO is in use
        /*motorE1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorE1.setDirection(DcMotorSimple.Direction.REVERSE);*/
        servoE2.setDirection(DcMotorSimple.Direction.REVERSE);
        //        motorE1.setPower(0.2);
        servoE2.setPower(0.8);
    }

    public int getE1CurrentPosition()
    {
        //TODO: may need to add back when OCTO is in use
        /*return motorE1.getCurrentPosition();*/
        return 0;
    }

    public void Arm90DegreePositioning()
    {
        motorJ1.setTargetPosition(motorJ1.getCurrentPosition());
    }
}

