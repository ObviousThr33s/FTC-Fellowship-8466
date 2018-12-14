package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class SamwiseClaw
{
    //Control twist/bending of wrist to collect or deposit
    private Servo servoWrist = null;

    //Control collecting or depositing of minerals
    private Servo servoClaw1 = null;
    private Servo servoClaw2 = null;

    public SamwiseClaw(HardwareMap hwm)
    {
        //TODO: Find all claw servos from hardwaremap and assign to our class states variables.
    }

    /**
     * Spin the two claw servos inward to drop minerals
     */
    public void depositMinerals()
    {
        //TODO
    }

    /**
     * Spin the two claw servos outward to collect minerals
     */
    public void collectMinerals()
    {
        //TODO
    }

    /**
     * Stop spinning of the claw servos
     */
    public void stopClaws()
    {
        //TODO
    }

    /**
     * Move wrist up or down according to provided speed.
     * @param speed Positive to move upward and negative to move downward.
     */
    public void moveWrist(float speed)
    {
        //TODO
    }
}
