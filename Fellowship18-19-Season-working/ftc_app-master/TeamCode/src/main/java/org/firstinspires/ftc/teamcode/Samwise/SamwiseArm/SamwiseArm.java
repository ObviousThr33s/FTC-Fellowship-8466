package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/*
TODO: Add state controls
 */
public class SamwiseArm
{
    //Control retraction and extension of the arm
    //AndyMark Neverest Classic 40: 1120 cpr (https://www.andymark.com/products/neverest-classic-40-gearmotor)
    //AndyMark Neverest Classic 20: 560 cpr (https://www.servocity.com/neverest-20-gear-motor)
    //    DcMotor motorJ1 = null;
    DcMotor motorJ2 = null;
    DcMotor motorJ3 = null;
    //private Servo servoWrist = null;
    //Control collecting or depositing of minerals
    private CRServo servoClaw1 = null;
    private CRServo servoClaw2 = null;

    //true if using manual control of J2/J3; false if using plane of motion math
    boolean isManual = false;
    //true if positioning in collection plane. Help save position before deposit so as to come back after
    boolean isCollectionPlane = false;

    static final int HEIGHT_PLANE_OF_MOTION = 6; //height of plane of motion
    static final double ARM_L1 = 24.09; //length between motorJoint1 and motorJoint2
    static final double ARM_L2 = 27.75; //length between motorJoint2 and servoWrist

    static final int TICKS_PER_REVOLUTION_J1 = 1680;
    static final int TICKS_PER_REVOLUTION_J2 = 1440;
    static final int TICKS_PER_REVOLUTION_J3 = 1440;
    static final int TICKS_PER_DEGREE_J1 = TICKS_PER_REVOLUTION_J1 / 360;
    static final int TICKS_PER_DEGREE_J2 = (TICKS_PER_REVOLUTION_J2 / 360) * 4;
    static final int TICKS_PER_DEGREE_J3 = (TICKS_PER_REVOLUTION_J3 / 360) * 2;

    static final double MANUAL_POWER_J1 = 0.4;
    static final double MANUAL_POWER_J2 = 0.05;
    static final double MANUAL_POWER_J3 = 0.05;
    static final double AUTO_POWER_J1 = 0.4;
    static final double AUTO_POWER_J2 = 0.05;
    static final double AUTO_POWER_J3 = 0.05;

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
    static final int SAMPLING_J2 = 90;
    static final int SAMPLING_J3 = 100;
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

    //Empty constructor for the arm modes
    public SamwiseArm() {}

    public SamwiseArm(HardwareMap hwm)
    {
        //TODO: Find out minimum and maximum position from initial position (ticks count==0)
        //        motorJ1 = hwm.dcMotor.get("J1");
        //        motorJ1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorJ2 = hwm.dcMotor.get("J2");
        motorJ2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorJ3 = hwm.dcMotor.get("J3");
        motorJ3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //servoWrist = hwm.servo.get("Wrist");
                servoClaw1 = hwm.crservo.get("ServoClaw1");
                servoClaw2 = hwm.crservo.get("ServoClaw2");

        //Initial Collection Position Math
        double a          = Math.sqrt(Math.pow(HEIGHT_PLANE_OF_MOTION, 2) + Math.pow(INITIAL_COL_ARM_L3, 2));
        double angle1     = Math.asin(HEIGHT_PLANE_OF_MOTION / a);
        double angle2     = Math.acos((Math.pow(ARM_L1, 2) + Math.pow(a, 2) - Math.pow(ARM_L2, 2)) / (2 * ARM_L1 * a));
        double angle3     = Math.acos((Math.pow(ARM_L1, 2) + Math.pow(ARM_L2, 2) - Math.pow(a, 2)) / (2 * ARM_L1 * ARM_L2));
        double j2_degrees = (angle1 + angle2 + 90) - INITIAL_DEGREES_J2;
        double j3_degrees = angle3 - INITIAL_DEGREES_J3;
        initialCollectionPosJ2 = TICKS_PER_DEGREE_J2 * j2_degrees;
        initialCollectionPosJ3 = TICKS_PER_DEGREE_J3 * j3_degrees;
        previousPositionJ2 = initialCollectionPosJ2;
        previousPositionJ3 = initialCollectionPosJ3;

        //        motorJ2.setTargetPosition(20);
        //        motorJ2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //        motorJ2.setPower(0.05);
        //        while (motorJ2.isBusy())
        //        {
        //        }
        //Max/Min Position Math

        System.out.println("initial collection J2: "+this.initialCollectionPosJ2);
        System.out.println("initial collection J3: "+this.initialCollectionPosJ3);

    }

    /************************************************************************************************
     *                                Transition to Positions                                       *
     ************************************************************************************************/


    private void toPosition(int targetJ2Position, int targetJ3Position)
    {
        motorJ2.setTargetPosition(targetJ2Position);
        motorJ3.setTargetPosition(targetJ3Position);
        motorJ2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ2.setPower(AUTO_POWER_J2);
        motorJ3.setPower(AUTO_POWER_J3);
        while (motorJ2.isBusy() || motorJ3.isBusy())
        {
        }
    }

    private void toPositionWithJ1(/*int targetJ1Position,*/ int targetJ2Position, int targetJ3Position)
    {
        //motorJ1.setTargetPosition(targetJ1Position);
        motorJ2.setTargetPosition(targetJ2Position);
        motorJ3.setTargetPosition(targetJ3Position);
        //motorJ1.setMode(DcMotor.RunMode.RUN_TO_POSITION
        motorJ2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //motorJ1.setPower(AUTO_POWER_J1)
        motorJ2.setPower(AUTO_POWER_J2);
        motorJ3.setPower(AUTO_POWER_J3);
        while (/*motorJ1.isBusy() ||*/motorJ2.isBusy() || motorJ3.isBusy())
        {
        }
    }
    /**
     * Transition from any other positions to initial position
     */
    public void toInitialPosition()
    {
        System.out.println("Going To Initial Position");
        setManual(false);
        setIsCollectionPlane(false);
        toPosition(0,0);
        System.out.println("Reached Initial Position");
    }

    /**
     * Transition from other positions to initial collection position
     */
    public void toCollectionPlane()
    {
        System.out.println("toCollectionPlane");
        setManual(false);
        toPosition((int) initialCollectionPosJ2, (int) initialCollectionPosJ3);
        setIsCollectionPlane(true);
    }

    /**
     * Transition from other positions to gold deposit position
     */
    public void goldDropPoint()
    {
        System.out.println("goldDropPoint");
        setManual(false);
        if (isCollectionPlane)
        {
            savePreviousPosition();
            setIsCollectionPlane(false);
        }
        toPositionWithJ1(/*GOLD_DROP_J1,*/ GOLD_DROP_J2, GOLD_DROP_J3);
    }

    /**
     * Transition from other positions to silver deposit position
     */
    public void silverDropPoint()
    {
        System.out.println("silverDropPoint");
        setManual(false);
        if (isCollectionPlane)
        {
            savePreviousPosition();
            setIsCollectionPlane(false);
        }
        toPositionWithJ1(/*SILVER_DROP_J1,*/ SILVER_DROP_J2, SILVER_DROP_J3);
    }

    /**
     * Save previous position in order to come back later
     */
    public void savePreviousPosition()
    {
        //previousPositionJ1 = getJ1CurrentPosition();
        previousPositionJ2 = getJ2CurrentPosition();
        previousPositionJ3 = getJ3CurrentPosition();
    }

    /**
     * Go back to previously saved position
     */
    public void toPreviousCollectionPosition()
    {
        System.out.println("toPreviousCollectionPosition");
        setManual(false);
        toPositionWithJ1(/*previousPositionJ1,*/ (int)previousPositionJ2, (int)previousPositionJ3);
        setIsCollectionPlane(true);
    }

    public void samplePosition()
    {
        System.out.println("samplePosition");
        toPosition(SAMPLING_J2, SAMPLING_J3);
    }

    /***********************************************************************************************
     *                                Collecting/Depositing Minerals                               *
     ***********************************************************************************************/

    /**
     * Spin the two claw servos inward to drop minerals
     */

    //-0.8 is the greatest speed
        public void collectMinerals()
        {
            servoClaw1.setDirection(DcMotorSimple.Direction.FORWARD);
            servoClaw1.setPower(0.7);
            servoClaw2.setDirection(DcMotorSimple.Direction.REVERSE);
            servoClaw2.setPower(0.7);
        }

    /**
     * Spin the two claw servos outward to collect minerals
     */

    //0.8 is the greatest speed
        public void depositMinerals()
        {
            servoClaw1.setDirection(DcMotorSimple.Direction.REVERSE);
            servoClaw1.setPower(0.7);
            servoClaw2.setDirection(DcMotorSimple.Direction.FORWARD);
            servoClaw2.setPower(0.7);
        }

    /**
     * Stop spinning of the claw servos
     */
        public void stopServo()
        {
            servoClaw1.setPower(0);
            servoClaw2.setPower(0);
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
    public void hoverPlaneOfMotion(double speed)
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
        motorJ2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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
        motorJ3.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorJ3.setPower(0);
    }

    /****************************************************************************************************
     *                                Others                                                            *
     ****************************************************************************************************/

    public void setIsCollectionPlane(boolean collectionPlane)
    {
        isCollectionPlane = collectionPlane;
    }

    //    public int getJ1CurrentPosition()
    //    {
    //        return motorJ1.getCurrentPosition();
    //    }


    public int getJ2CurrentPosition()
    {
        return motorJ2.getCurrentPosition();
    }

    public int getJ3CurrentPosition()
    {
        return motorJ3.getCurrentPosition();
    }
}
