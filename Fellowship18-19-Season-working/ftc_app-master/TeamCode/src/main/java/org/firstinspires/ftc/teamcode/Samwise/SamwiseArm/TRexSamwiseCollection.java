package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class TRexSamwiseCollection
{
//    private Servo servoJ4;
    private CRServo servoC1;
    private CRServo servoC2;

    TRexSamwiseCollection(HardwareMap hwm)
    {
//        servoJ4 = hwm.servo.get("J4");
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
    public void depositMinerals()
    {
        servoC1.setDirection(DcMotorSimple.Direction.FORWARD);
        servoC1.setPower(0.8);
        servoC2.setDirection(DcMotorSimple.Direction.REVERSE);
        servoC2.setPower(0.8);
    }

    /**
     * Spin the two claw servos outward to collect minerals:
     * 1. Save current J2, J3 position.
     * 2. Lower the gripper
     * 3. Spin to collect
     * 4. Stop and return to previously saved position
     */
    public void collectMinerals()
    {
        servoC1.setDirection(DcMotorSimple.Direction.REVERSE);
        servoC1.setPower(0.8);
        servoC2.setDirection(DcMotorSimple.Direction.FORWARD);
        servoC2.setPower(0.8);
    }

    /**
     * Stop spinning of the claw servos
     */
    public void stopCollecting()
    {
        servoC1.setPower(0);
        servoC2.setPower(0);
    }

//    public void moveJ4Up()
//    {
////        servoJ4.setDirection(DcMotorSimple.Direction.FORWARD);
////        servoJ4.setPower(0.8);
//    }
//
//    public void moveJ4Down()
//    {
////        servoJ4.setDirection(DcMotorSimple.Direction.REVERSE);
////        servoJ4.setPower(0.8);
//    }

    public void moveJ4Up()
    {
//        servoJ4.setDirection(Servo.Direction.FORWARD);
//        servoJ4.setPosition(180);
    }
////
    public void moveJ4Down()
    {
//        servoJ4.setDirection(Servo.Direction.REVERSE);
//        servoJ4.setPosition(0);
    }
    public void stopJ4()
    {
//        servoJ4.(0);
    }

}
