package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/*
TODO: Add state controls
 */
public class SamwiseArm
{
    //Control spin/rotate of arm
    private DcMotor motorSpin = null;

    //Control retraction and extension of the arm
    //AndyMark Neverest Classic 40: 1120 cpr (https://www.andymark.com/products/neverest-classic-40-gearmotor)
    //AndyMark Neverest Classic 20: 560 cpr (https://www.servocity.com/neverest-20-gear-motor)
    private DcMotor motorJoint1 = null;
    private DcMotor motorJoint2 = null;

    private static final int HEIGHT_PLANE_OF_MOTION = 2; //height of plane of motion
    private static final int ARM_LENGTH_1 = 26; //length between motorJoint1 and motorJoint2
    private static final int ARM_LENGTH_2 = 29; //length between motorJoint2 and servoWrist

    //TODO: need to find out and assi
    private int collection_minimum_position=0;
    private int collection_maximum_position=0;

    public SamwiseArm(HardwareMap hwm)
    {
        //TODO: 1. Find all arm motors from hardwaremap and assign to our class states variables.
        //      2. Find out minimum and maximum position
    }

    /****************************************************************************************************
     *                                Transition to Positions                                           *
     ****************************************************************************************************/

    /**
     * Transition from any other positions to initial position
     */
    public void toInitialPosition()
    {
        //TODO
    }

    /**
     * Transition from initial or deposit to collection position
     */
    public void toCollectionPlane()
    {
        //TODO
    }

    /**
     * Transition from collection to deposit position
     */
    public void toDepositPlane()
    {
        //TODO
    }

    /****************************************************************************************************
     *                                Stay on Plane of Motions                                          *
     ****************************************************************************************************/

    /**
     * Stay on plane of motion by moving forward or backward and how fast according to the provided speed.
     * @param speed Positive to expand, negative to retract, and zero to stop/break. The value is between -1 and 1 taken straight
     *              from the gamepad input.
     */
    public void hoverPlaneOfMotion(float speed)
    {
        //TODO:
        // Suggested procedures:
        // 1. Find out minimum and maximum position. (should be done in constructor)
        // 2. On positive speed: set motorJoint1 to go maximum position
        //    On negative speed: set motorJoint1 to go minimum position
        //    On zero speed: stop motorJoint1
        // 3. Read motorJoint1 current position, calculate to gather the according position motorJoint2 should
        //    be, and set motorJoint2 to head for it.
    }

}