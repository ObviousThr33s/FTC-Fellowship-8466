package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/*
TODO: Add state controls
 */
public class SamwiseArm
{
    //Control retraction and extension of the arm
    //AndyMark Neverest Classic 40: 1120 cpr (https://www.andymark.com/products/neverest-classic-40-gearmotor)
    //AndyMark Neverest Classic 20: 560 cpr (https://www.servocity.com/neverest-20-gear-motor)
    private DcMotor motorJ1 = null;
    private DcMotor motorJ2 = null;
    private DcMotor motorJ3 = null;
    private Servo servoWrist = null;
    //Control collecting or depositing of minerals
    private Servo servoClaw1 = null;
    private Servo servoClaw2 = null;
    //true if using manual control of J2/J3; false if using plane of motion math
    boolean isManual = false;
    boolean isCollectionPlane = false;

    private static final int HEIGHT_PLANE_OF_MOTION = -6; //height of plane of motion
    private static final double ARM_LENGTH_1 = 24.09; //length between motorJoint1 and motorJoint2
    private static final double ARM_LENGTH_2 = 27.75; //length between motorJoint2 and servoWrist
    private static final double MANUAL_POWER_J1 = 0.2;
    private static final double MANUAL_POWER_J2 = 0.2;
    private static final double MANUAL_POWER_J3 = 0.2;
    private static final double AUTO_POWER_J1 = 0.2;
    private static final double AUTO_POWER_J2 = 0.2;
    private static final double AUTO_POWER_J3 = 0.2;
    //TODO: Change all these numbers
    private static final int INITIAL_COLLECTION_POSITION_J2 = 90;
    private static final int INITIAL_COLLECTION_POSITION_J3 = 130;
    private static final int GOLD_DROP_J1 = 20;
    private static final int GOLD_DROP_J2 = 270;
    private static final int GOLD_DROP_J3 = 90;
    private static final int SILVER_DROP_J1 = -20;
    private static final int SILVER_DROP_J2 = 270;
    private static final int SILVER_DROP_J3 = 90;
    public int previousPositionJ1 = 0;
    public int previousPositionJ2 = INITIAL_COLLECTION_POSITION_J2;
    public int previousPositionJ3 = INITIAL_COLLECTION_POSITION_J3;

    //TODO: need to find out and assi
    private int collection_minimum_position = 0;
    private int collection_maximum_position = 0;

    public SamwiseArm(HardwareMap hwm)
    {
        //TODO: Find out minimum and maximum position
        motorJ1 = hwm.dcMotor.get("J1");
        motorJ1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorJ2 = hwm.dcMotor.get("J2");
        motorJ2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorJ3 = hwm.dcMotor.get("J3");
        motorJ3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //servoWrist = hwm.servo.get("Wrist");
        servoClaw1 = hwm.servo.get("ServoClaw1");
        servoClaw2 = hwm.servo.get("ServoClaw2");
    }

    /************************************************************************************************
     *                                Transition to Positions                                       *
     ************************************************************************************************/

    public boolean getIsCollectionPlane()
    {
        return isCollectionPlane;
    }

    public void setIsCollectionPlane(boolean collectionPlane)
    {
        isCollectionPlane = collectionPlane;
    }

    /**
     * Transition from any other positions to initial position
     */


    public void toInitialPosition()
    {
        setManual(false);
        setIsCollectionPlane(false);
        motorJ2.setTargetPosition(0);
        motorJ3.setTargetPosition(0);
        motorJ2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ2.setPower(AUTO_POWER_J2);
        motorJ3.setPower(AUTO_POWER_J3);
        while (motorJ2.isBusy() || motorJ3.isBusy())
        {
        }
    }

    /**
     * Transition from initial or deposit to collection position
     */
    public void toCollectionPlane()
    {
        setManual(false);
        motorJ2.setTargetPosition(INITIAL_COLLECTION_POSITION_J2);
        motorJ3.setTargetPosition(INITIAL_COLLECTION_POSITION_J3);
        motorJ2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ3.setPower(AUTO_POWER_J3);
        motorJ2.setPower(AUTO_POWER_J2);
        while (motorJ2.isBusy() || motorJ3.isBusy())
        {
        }
        setIsCollectionPlane(true);
    }

    /**
     * Transition from collection to deposit position
     */
    public void goldDropPoint()
    {
        setManual(false);
        if (isCollectionPlane)
        {
            savePreviousPosition();
            setIsCollectionPlane(false);
        }
        motorJ1.setTargetPosition(GOLD_DROP_J1);
        motorJ2.setTargetPosition(GOLD_DROP_J2);
        motorJ3.setTargetPosition(GOLD_DROP_J3);
        motorJ1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ1.setPower(AUTO_POWER_J1);
        motorJ2.setPower(AUTO_POWER_J2);
        motorJ3.setPower(AUTO_POWER_J3);
        while (motorJ1.isBusy() || motorJ2.isBusy() || motorJ3.isBusy())
        {
        }
        //TODO: Add depositing method
    }

    public void silverDropPoint()
    {
        setManual(false);
        if (isCollectionPlane)
        {
            savePreviousPosition();
            setIsCollectionPlane(false);
        }
        motorJ1.setTargetPosition(SILVER_DROP_J1);
        motorJ2.setTargetPosition(SILVER_DROP_J2);
        motorJ3.setTargetPosition(SILVER_DROP_J3);
        motorJ1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ1.setPower(AUTO_POWER_J1);
        motorJ2.setPower(AUTO_POWER_J2);
        motorJ3.setPower(AUTO_POWER_J3);
        while (motorJ1.isBusy() || motorJ2.isBusy() || motorJ3.isBusy())
        {
        }
        //TODO: Add depositing method
    }

    public void savePreviousPosition()
    {
        previousPositionJ1 = getJ1CurrentPosition();
        previousPositionJ2 = getJ2CurrentPosition();
        previousPositionJ3 = getJ3CurrentPosition();
    }

    public void toPreviousCollectionPosition()
    {
        setManual(false);
        motorJ1.setTargetPosition(previousPositionJ1);
        motorJ2.setTargetPosition(previousPositionJ2);
        motorJ3.setTargetPosition(previousPositionJ3);
        motorJ1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ3.setPower(AUTO_POWER_J3);
        motorJ2.setPower(AUTO_POWER_J2);
        motorJ1.setPower(AUTO_POWER_J1);
        while (motorJ1.isBusy() || motorJ2.isBusy() || motorJ3.isBusy())
        {
        }
        setIsCollectionPlane(true);
    }

    public void servoTesting()
    {
       servoClaw1.setPosition(0.6);
       servoClaw2.setPosition(0.6);
    }
    /***********************************************************************************************
     *                                Stay on Plane of Motion                                      *
     ***********************************************************************************************/

    /**
     * Stay on plane of motion by moving forward or backward and how fast according to the provided speed.
     *
     * @param speed Positive to expand, negative to retract, and zero to stop/break. The value is between -1 and 1 taken straight
     *              from the gamepad input.
     */
    public void hoverPlaneOfMotion(float speed)
    {
        setManual(false);
        //TODO:
    }

    /****************************************************************************************************
     *                                J1, J2 and J3 Manual Control                                      *
     ****************************************************************************************************/

    public void setManual(boolean manual)
    {
        isManual = manual;
    }

    public boolean getIsManual()
    {
        return isManual;
    }

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

    public void stopJ2()
    {
        motorJ2.setPower(0);
    }

    public void driveJ3(boolean isUp)
    {
        motorJ3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        if (isUp)
        {
            motorJ3.setPower(-MANUAL_POWER_J3);
        }
        else
        {
            motorJ3.setPower(MANUAL_POWER_J3);
        }
    }

    public void stopJ3()
    {
        motorJ3.setPower(0);
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
}
