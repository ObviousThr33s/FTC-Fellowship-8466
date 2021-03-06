package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class OctoSamwiseCollection
{
    private CRServo servoJ4;
    private CRServo servoC1;
    private CRServo servoC2;

    private boolean isCollecting = false;
    private boolean isInMotionJ4 = false;

    OctoSamwiseCollection(HardwareMap hwm)
    {
        servoJ4 = hwm.crservo.get("J4");
        servoC1 = hwm.crservo.get("C1");
        servoC2 = hwm.crservo.get("C2");
    }

    /**
     * Spin the two claw servos inward to drop minerals:
     * 1. Save currentJ2, J3 position
     * 2. Lower the gripper
     * 3. Spin to deposit
     * 4. Return to previously saved position
     */
    public void collectMinerals()
    {
        servoC1.setDirection(DcMotorSimple.Direction.REVERSE);
        servoC1.setPower(1);
        servoC2.setDirection(DcMotorSimple.Direction.REVERSE);
        servoC2.setPower(1);
        this.isCollecting = true;
    }

    /**
     * Spin the two claw servos outward to collect minerals:
     * 1. Save current J2, J3 position.
     * 2. Lower the gripper
     * 3. Spin to collect
     * 4. Stop and return to previously saved position
     */
    public void depositMinerals()
    {
        servoC1.setDirection(DcMotorSimple.Direction.FORWARD);
        servoC1.setPower(1);
        servoC2.setDirection(DcMotorSimple.Direction.FORWARD);
        servoC2.setPower(1);
        this.isCollecting = true;
    }

    /**
     * Stop spinning of the claw servos
     */
    public void stopCollecting()
    {
        if (this.isCollecting)
        {
            servoC1.setPower(0);
            servoC2.setPower(0);
            this.isCollecting = false;
        }
    }


    public void moveJ4Up()
    {
        servoJ4.setDirection(DcMotorSimple.Direction.FORWARD);
        servoJ4.setPower(1);
        this.isInMotionJ4 = true;
    }

    public void moveJ4Down()
    {
        servoJ4.setDirection(DcMotorSimple.Direction.REVERSE);
        servoJ4.setPower(1);
        this.isInMotionJ4 = true;
    }

    public void stopJ4()
    {
        if (this.isInMotionJ4)
        {
            servoJ4.setPower(0);
            this.isInMotionJ4 = false;
        }
    }

}
