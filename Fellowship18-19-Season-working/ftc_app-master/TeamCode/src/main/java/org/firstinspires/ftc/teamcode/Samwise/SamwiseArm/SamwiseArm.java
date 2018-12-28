package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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
    DcMotor motorJ1 = null;
    DcMotor motorJ2 = null;
    DcMotor motorJ3 = null;
    private Servo servoWrist = null;
    //Control collecting or depositing of minerals
    private CRServo servo = null;
    //private CRServo servoClaw2 = null;
    //true if using manual control of J2/J3; false if using plane of motion math
    boolean isManual = false;
    boolean isCollectionPlane = false;

    static final int HEIGHT_PLANE_OF_MOTION = 6; //height of plane of motion
    static final double ARM_L1 = 24.09; //length between motorJoint1 and motorJoint2
    static final double ARM_L2 = 27.75; //length between motorJoint2 and servoWrist
    static final int TICKS_PER_REVOLUTION_J1 = 1680;
    static final int TICKS_PER_REVOLUTION_J2 = 560;
    static final int TICKS_PER_REVOLUTION_J3 = 560;
    static final int TICKS_PER_DEGREE_J1 = TICKS_PER_REVOLUTION_J1/360;
    static final int TICKS_PER_DEGREE_J2 = TICKS_PER_REVOLUTION_J2/360;
    static final int TICKS_PER_DEGREE_J3 = TICKS_PER_REVOLUTION_J3/360;
    static final double MANUAL_POWER_J1 = 0.2;
    static final double MANUAL_POWER_J2 = 0.2;
    static final double MANUAL_POWER_J3 = 0.2;
    static final double AUTO_POWER_J1 = 0.2;
    static final double AUTO_POWER_J2 = 0.2;
    static final double AUTO_POWER_J3 = 0.2;

    //TODO: Change all these numbers
    static final double INITIAL_COL_ARM_L3 = 2 * (ARM_L1 + ARM_L2) / 3;
    static final int GOLD_DROP_J1 = 20;
    static final int GOLD_DROP_J2 = 270;
    static final int GOLD_DROP_J3 = 90;
    static final int SILVER_DROP_J1 = -20;
    static final int SILVER_DROP_J2 = 270;
    static final int SILVER_DROP_J3 = 90;
    static final int INITIAL_DEGREES_J2 = 10;
    static final int INITIAL_DEGREES_J3 = 10;
    static final int INITIAL_TICKS_J3 = INITIAL_DEGREES_J3 * TICKS_PER_DEGREE_J3;
    static final int INITIAL_TICKS_J2 = INITIAL_DEGREES_J2 * TICKS_PER_DEGREE_J2;

    double initialCollectionPosJ2;
    double initialCollectionPosJ3;
    int previousPositionJ1 = 0;
    double previousPositionJ2 = initialCollectionPosJ2;
    double previousPositionJ3 = initialCollectionPosJ3;
    double maxDegreesJ2;
    static double maxTicksJ2;

    //TODO: need to find out and assi
    private int collection_minimum_position = 0;
    private static final double collection_maximum_position_J2 = 0;
    private static final double collection_maximum_position_J3 = 0;

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
        servo = hwm.crservo.get("servo");
        //servoClaw2 = hwm.crservo.get("ServoClaw2");

        //Initial Collection Position Math
        double a      = Math.sqrt(Math.pow(HEIGHT_PLANE_OF_MOTION, 2) + Math.pow(INITIAL_COL_ARM_L3, 2));
        double angle1 = Math.asin(HEIGHT_PLANE_OF_MOTION / a);
        double angle2 = Math.acos((Math.pow(ARM_L1, 2) + Math.pow(a, 2) - Math.pow(ARM_L2, 2)) / (2 * ARM_L1 * a));
        double angle3 = Math.acos((Math.pow(ARM_L1, 2) + Math.pow(ARM_L2, 2) - Math.pow(a, 2)) / (2 * ARM_L1 * ARM_L2));
        double J2_DEGREES = (angle1 + angle2 + 90) - INITIAL_DEGREES_J2;
        double J3_DEGREES = angle3 - INITIAL_DEGREES_J3;
        initialCollectionPosJ2 = TICKS_PER_DEGREE_J2 * J2_DEGREES;
        initialCollectionPosJ3 = TICKS_PER_DEGREE_J3 * J3_DEGREES;
        previousPositionJ2 = initialCollectionPosJ2;
        previousPositionJ3 = initialCollectionPosJ3;

        //Max/Min Position Math

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
        motorJ2.setTargetPosition((int) initialCollectionPosJ2);
        motorJ3.setTargetPosition((int) initialCollectionPosJ3);
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
        motorJ2.setTargetPosition((int)previousPositionJ2);
        motorJ3.setTargetPosition((int)previousPositionJ3);
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

    /***********************************************************************************************
     *                                Collecting/Depositing Minerals                               *
     ***********************************************************************************************/

    public void servoTesting()
    {
        servo.setPower(.8);
    }

    public void servoStop()
    {
        servo.setPower(0);
    }

    /**
     * Spin the two claw servos inward to drop minerals
     */

    //-0.8 is the greatest speed
    public void depositMinerals()
    {
        servo.setPower(-0.5);
    }

    /**
     * Spin the two claw servos outward to collect minerals
     */

    //0.8 is the greatest speed
    public void collectMinerals()
    {
        servo.setPower(0.5);
    }

    /**
     * Stop spinning of the claw servos
     */
    public void stopServo()
    {
        servo.setPower(0);
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