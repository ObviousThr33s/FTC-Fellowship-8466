package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

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
    //private Servo servoJ4 = null;
    //Control collecting or depositing of minerals
    private CRServo servoC1 = null;
    private CRServo servoC2 = null;
    private CRServo servoE1 = null;
    private CRServo servoE2 = null;

    //true if using manual control of J2/J3; false if using plane of motion math
    boolean isManual = false;
    //true if positioning in collection plane. Help save position before deposit so as to come back after
    boolean isCollectionPlane = false;

    static final double HEIGHT_PLANE_OF_MOTION = 6; //height of plane of motion
    static final double ARM_L1 = 24.09; //length between motorJoint1 and motorJoint2
    static final double ARM_L2 = 27.75; //length between motorJoint2 and servoJ4

    static final int TICKS_PER_REVOLUTION_J1 = 1680;
    static final int TICKS_PER_REVOLUTION_J2 = 1440;
    static final int TICKS_PER_REVOLUTION_J3 = 1440;
    static final double TICKS_PER_DEGREE_J1 = TICKS_PER_REVOLUTION_J1 / 360.0;
    static final double TICKS_PER_DEGREE_J2 = (TICKS_PER_REVOLUTION_J2 / 360.0) * 4;
    static final double TICKS_PER_DEGREE_J3 = (TICKS_PER_REVOLUTION_J3 / 360.0) * 2;

    static final double MANUAL_POWER_J1 = 0.4;
    static final double MANUAL_POWER_J2 = 0.05;
    static final double MANUAL_POWER_J3 = 0.05;
    static final double AUTO_POWER_J1 = 0.4;
    static final double AUTO_POWER_J2 = 0.05;
    static final double AUTO_POWER_J3 = 0.05;
    static final int TIMEOUT = 100;

    //TODO: Change all these numbers
    static final double INITIAL_COL_ARM_L3 = 2 * (ARM_L1 + ARM_L2) / 3;
    static final int GOLD_DROP_J1 = 20;
    static final int GOLD_DROP_J2 = 270;
    static final int GOLD_DROP_J3 = 90;
    static final int SILVER_DROP_J1 = -20;
    static final int SILVER_DROP_J2 = 270;
    static final int SILVER_DROP_J3 = 90;

    static final double INITIAL_DEGREES_J2 = 10;
    static final double INITIAL_DEGREES_J3 = 10;
    static final int INITIAL_TICKS_J3 = (int) (INITIAL_DEGREES_J3 * TICKS_PER_DEGREE_J3);
    static final int INITIAL_TICKS_J2 = (int) (INITIAL_DEGREES_J2 * TICKS_PER_DEGREE_J2);

    static final int SAMPLING_RIGHT_J1 = -20;
    static final int SAMPLING_CENTER_J1 = 0;
    static final int SAMPLING_LEFT_J1 = 20;
    static final int SAMPLING_CENTER_J2 = 90;
    static final int SAMPLING_CENTER_J3 = 100;
    static final int SAMPLING_LEFT_RIGHT_J2 = 100;
    static final int SAMPLING_LEFT_RIGHT_J3 = 110;
    static final int SAMPLING_J1_CRATER = 40;
    static final int SAMPLING_J2_CRATER = 100;
    static final int SAMPLING_J3_CRATER = 200;
    static final int SAMPLING_J1_DEPOT = 40;
    static final int SAMPLING_J2_DEPOT = 100;
    static final int SAMPLING_J3_DEPOT = 300;


    double initialCollectionPosJ2 = 0;
    double initialCollectionPosJ3 = 0;

    int previousPositionJ1 = 0;
    double previousPositionJ2 = initialCollectionPosJ2;
    double previousPositionJ3 = initialCollectionPosJ3;

    ElapsedTime runTime = new ElapsedTime();

    public SamwiseArm(HardwareMap hwm)
    {
        //Instantiate hardware
        //        motorJ1 = hwm.dcMotor.get("J1");
        motorJ2 = hwm.dcMotor.get("J2");
        motorJ3 = hwm.dcMotor.get("J3");
        //servoJ4 = hwm.servo.get("J4");
        servoC1 = hwm.crservo.get("C1");
        servoC2 = hwm.crservo.get("C2");
        servoE1 = hwm.crservo.get("E1");
        servoE2 = hwm.crservo.get("E2");

        //        motorJ1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorJ2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorJ2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorJ3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorJ3.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Initial Collection Position Math
        double length_J2_J4 = Math.sqrt(Math.pow(HEIGHT_PLANE_OF_MOTION, 2) + Math.pow(INITIAL_COL_ARM_L3, 2));
        double angle1       = Math.toDegrees(Math.asin(HEIGHT_PLANE_OF_MOTION / length_J2_J4));
        double angle2       = Math.toDegrees(Math.acos((Math.pow(ARM_L1, 2) + Math.pow(length_J2_J4, 2) - Math.pow(ARM_L2, 2)) / (2 * ARM_L1 * length_J2_J4)));
        double angle3       = Math.toDegrees(Math.acos((Math.pow(ARM_L1, 2) + Math.pow(ARM_L2, 2) - Math.pow(length_J2_J4, 2)) / (2 * ARM_L1 * ARM_L2)));
        double j2_degrees   = (angle1 + angle2 + 90) - INITIAL_DEGREES_J2;
        double j3_degrees   = angle3 - INITIAL_DEGREES_J3;
        initialCollectionPosJ2 = TICKS_PER_DEGREE_J2 * j2_degrees;
        initialCollectionPosJ3 = TICKS_PER_DEGREE_J3 * j3_degrees;
        previousPositionJ2 = initialCollectionPosJ2;
        previousPositionJ3 = initialCollectionPosJ3;

        System.out.println("initial collection J2: " + this.initialCollectionPosJ2);
        System.out.println("initial collection J3: " + this.initialCollectionPosJ3);

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
        retractArm();
        toPosition(0, 0);
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
        extendArm();
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
        toPositionWithJ1(/*previousPositionJ1,*/ (int) previousPositionJ2, (int) previousPositionJ3);
        setIsCollectionPlane(true);
    }

    /************************************************************************************************
     *                                Transition to Sampling Positions                              *
     ************************************************************************************************/

    //TODO: Figure out if we need to extend the arm or not for sampling
    public void toSamplePositionRight()
    {
        System.out.println("toSamplePositionRight");
        toPositionWithJ1(/*SAMPLING_RIGHT_J1,*/SAMPLING_LEFT_RIGHT_J2, SAMPLING_LEFT_RIGHT_J3);
    }

    public void toSamplePositionLeft()
    {
        System.out.println("toSamplePositionLeft");
        toPositionWithJ1(/*SAMPLING_LEFT_J1,*/SAMPLING_CENTER_J2, SAMPLING_CENTER_J3);
    }

    public void toSamplePositionCenter()
    {
        System.out.println("toSamplePositionCenter");
        toPositionWithJ1(/*SAMPLING_CENTER_J1,*/SAMPLING_LEFT_RIGHT_J2, SAMPLING_LEFT_RIGHT_J3);
    }

    public void goldSampleDropDepot()
    {
        toPositionWithJ1(/*SAMPLE_J1_DEPOT,*/ SAMPLING_J2_DEPOT, SAMPLING_J3_DEPOT);
    }

    public void goldSampleDropCrater()
    {
        toPositionWithJ1(/*SAMPLE_J1_CRATER,*/ SAMPLING_J2_CRATER, SAMPLING_J3_CRATER);
    }

    /************************************************************************************************
     *                                Arm Extension/Retraction                                      *
     ************************************************************************************************/

    public void extendArm()
    {
        setIsCollectionPlane(true);
        runTime.reset();
        servoE1.setPower(0.2);
        servoE2.setPower(0.2);
        while (runTime.milliseconds() < TIMEOUT)
        {
        }
        stopExtendServos();
    }

    public void retractArm()
    {
        setIsCollectionPlane(true);
        runTime.reset();
        servoE2.setPower(-0.2);
        servoE1.setPower(-0.2);
        while (runTime.milliseconds() < TIMEOUT)
        {
        }
        stopExtendServos();
    }

    private void stopExtendServos()
    {
        servoE1.setPower(0);
        servoE2.setPower(0);
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

    /****************************************************************************************************
     *                                Collecting/Depositing                                             *
     ****************************************************************************************************/

    public double getJ4Degrees()
    {
        int j2Ticks = getJ2CurrentPosition();
        int j3Ticks = getJ3CurrentPosition();
        double j2Degrees = (j2Ticks / TICKS_PER_DEGREE_J2) + INITIAL_DEGREES_J2;
        double j3Degrees = (j3Ticks / TICKS_PER_DEGREE_J3) + INITIAL_DEGREES_J3;
        double angle1 = j2Degrees - 90;
        double j4Degrees = 180 - (angle1 + j3Degrees);
        return j4Degrees;
    }


    /**
     * Spin the two claw servos inward to drop minerals
     */
    public void depositMinerals()
    {
        //TODO: Find out if servos move 180 degrees from their current position or from 0 to 180. Ex: A servo is currently at five degrees. Does it move to 180 degrees or 185?
        //servoJ4.setPosition(1);
        double j4Degrees = getJ4Degrees();
        //servoJ4.setPosition(j4Degrees/180);
        servoC1.setDirection(DcMotorSimple.Direction.REVERSE);
        servoC1.setPower(0.7);
        servoC2.setDirection(DcMotorSimple.Direction.FORWARD);
        servoC2.setPower(0.7);
    }

    /**
     * Spin the two claw servos outward to collect minerals
     */
    public void collectMinerals()
    {
        double j4Degrees = getJ4Degrees();
        //servoJ4.setPosition(j4Degrees/180);
        servoC1.setDirection(DcMotorSimple.Direction.FORWARD);
        servoC1.setPower(0.7);
        servoC2.setDirection(DcMotorSimple.Direction.REVERSE);
        servoC2.setPower(0.7);
    }

    /**
     * Stop spinning of the claw servos
     */
    public void stopServo()
    {
        servoC1.setPower(0);
        servoC2.setPower(0);
    }

    private void lowerJ4(int height)
    {
        
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
