package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class OctoSamwiseSmart extends OctoSamwiseArm
{
    public boolean holdJ2 = true;
    public boolean holdJ3 = true;
    private int motor_position_1j2;
    private int motor_position_2j2;
    private int motor_position_j3;
    private boolean isStop = false;


    boolean isCollectionPlane = false;
    //true if using manual control of J2/J3; false if using plane of motion math
    boolean isManual = false;

    boolean isCollecting = false;

    static final int TICKS_PER_REVOLUTION_J1 = 1680;
    static final int TICKS_PER_REVOLUTION_J2 = 1440;
    static final double TICKS_PER_REVOLUTION_J3 = 1993.6;
    static final double TICKS_PER_DEGREE_J1 = TICKS_PER_REVOLUTION_J1 / 360.0;
    static final double TICKS_PER_DEGREE_J2 = (TICKS_PER_REVOLUTION_J2 / 360.0) * 4;
    static final double TICKS_PER_DEGREE_J3 = (TICKS_PER_REVOLUTION_J3 / 360.0) * 2;

    static final double INITIAL_DEGREES_J2 = 240;
    static final double INITIAL_DEGREES_J3 = 50;
    static final int INITIAL_TICKS_J3 = (int) (INITIAL_DEGREES_J3 * TICKS_PER_DEGREE_J3);
    static final int INITIAL_TICKS_J2 = (int) (INITIAL_DEGREES_J2 * TICKS_PER_DEGREE_J2);

    static final double HEIGHT_PLANE_OF_MOTION = 12; //height of plane of motion
    static final double J4_COLLECTION_HEIGHT = 2.5;
    static final double ARM_L1 = 14.5; //length between motorJoint1 and motorJoint2
    static final double ARM_L2 = 17.1; //length between motorJoint2 and servoJ4

    static final double INITIAL_COL_ARM_L3 = 2 * (ARM_L1 + ARM_L2) / 3;

    public int initialCollectionPosJ1 = -842;
    public int initialCollectionPos1J2 = 773;
    public int initialCollectionPos2J2 = 773;
    public int initialCollectionPosJ3 = -70;
    int previousPositionJ1;
    int previousPosition1J2;
    int previousPosition2J2;
    int previousPositionJ3;

    static final double J1_POWER = 0.3;
    static final double J2_POWER = 0.1;
    static final double UP_POWER_J3 = 0.15;
    static final double DOWN_POWER_J3 = 0.05;
    static final double HOLD_POWER = 0.3;

    double j1Power = 0;
    double j2Power = 0;
    double j3Power = 0;

    static final int GOLD_DROP_J1 = 20;
    static final int GOLD_DROP_J2 = 270;
    static final int GOLD_DROP_J3 = 90;
    static final int SILVER_DROP_J1 = -20;
    static final int SILVER_DROP_J2 = 270;
    static final int SILVER_DROP_J3 = 90;

    public OctoSamwiseSmart(HardwareMap hwm)
    {
        super(hwm);

        double length_J2_J4 = Math.sqrt(Math.pow(HEIGHT_PLANE_OF_MOTION, 2) + Math.pow(INITIAL_COL_ARM_L3, 2));
        double angle1       = Math.toDegrees(Math.asin(HEIGHT_PLANE_OF_MOTION / length_J2_J4));
        double angle2       = Math.toDegrees(Math.acos((Math.pow(ARM_L1, 2) + Math.pow(length_J2_J4, 2) - Math.pow(ARM_L2, 2)) / (2 * ARM_L1 * length_J2_J4)));
        double angle3       = Math.toDegrees(Math.acos((Math.pow(ARM_L1, 2) + Math.pow(ARM_L2, 2) - Math.pow(length_J2_J4, 2)) / (2 * ARM_L1 * ARM_L2)));
        double j2_degrees   = INITIAL_DEGREES_J2 - (angle1 + angle2 + 90);
        double j3_degrees   = angle3 - INITIAL_DEGREES_J3;
        //        initialCollectionPosJ2 = TICKS_PER_DEGREE_J2 * j2_degrees;
        //        initialCollectionPosJ3 = TICKS_PER_DEGREE_J3 * j3_degrees;
        previousPosition1J2 = initialCollectionPos1J2;
        previousPosition2J2 = initialCollectionPos2J2;
        previousPositionJ3 = initialCollectionPosJ3;
    }

    public void stop()
    {
        isStop = true;
    }

    public void savePreviousPosition()
    {
        previousPositionJ1 = motorJ1.getCurrentPosition();
        previousPosition1J2 = motor1J2.getCurrentPosition();
        previousPosition2J2 = motor2J2.getCurrentPosition();
        previousPositionJ3 = motorJ3.getCurrentPosition();
    }

    public void toPreviousPosition()
    {
        if (motorJ1.getCurrentPosition() > previousPositionJ1)
        {
            j1Power = -J1_POWER;
        }
        else if (motorJ1.getCurrentPosition() < previousPositionJ1)
        {
            j1Power = J1_POWER;
        }

        if (motor1J2.getCurrentPosition() > previousPosition1J2)
        {
            j2Power = -J2_POWER;
        }
        else if (motor1J2.getCurrentPosition() < previousPosition1J2)
        {
            j2Power = J2_POWER;
        }

        if (motor2J2.getCurrentPosition() > previousPosition2J2)
        {
            j2Power = -J2_POWER;
        }
        else if (motor2J2.getCurrentPosition() < previousPosition2J2)
        {
            j2Power = J2_POWER;
        }

        if (motorJ3.getCurrentPosition() > previousPositionJ3)
        {
            j3Power = -UP_POWER_J3;
        }
        else if (motorJ3.getCurrentPosition() < previousPositionJ3)
        {
            j3Power = UP_POWER_J3;
        }
        toPositionReverse(j1Power, j2Power, j3Power, previousPositionJ1, previousPosition1J2, previousPosition2J2, previousPositionJ3);
        j1Power = 0;
        j2Power = 0;
        j3Power = 0;
    }

    public void holdPositionJ2(boolean hold)
    {

        if (hold)
        {
            motor_position_1j2 = motor1J2.getCurrentPosition();
            motor_position_2j2 = motor2J2.getCurrentPosition();
        }
        else
        {
            motor1J2.setTargetPosition(motor_position_1j2);
            motor2J2.setTargetPosition(motor_position_2j2);
            motor1J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor2J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor1J2.setPower(HOLD_POWER);
            motor2J2.setPower(HOLD_POWER);
        }
    }


    public void holdPositionJ3(boolean hold)
    {
        if (hold)
        {
            motor_position_j3 = motorJ3.getCurrentPosition();
        }
        else
        {
            motorJ3.setTargetPosition(motor_position_j3);
            motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorJ3.setPower(HOLD_POWER);
        }
    }

    public void toPosition(double j1Power, double j2Power, double j3Power, int j1Position, int j2Position1, int j2Position2, int j3Position)
    {

        motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ3.setPower(j3Power);
        motorJ3.setTargetPosition(j3Position);
        isStop = false;
        while (motorJ3.isBusy() && !isStop)
        {
            Thread.yield();
        }
        motor1J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor1J2.setPower(j2Power);
        motor2J2.setPower(j2Power);
        motor1J2.setTargetPosition(j2Position1);
        motor2J2.setTargetPosition(j2Position2);
        while ((motor1J2.isBusy() || motor2J2.isBusy()) && !isStop)
        {
            Thread.yield();
        }
        motorJ1.setPower(j1Power);
        motorJ1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ1.setTargetPosition(j1Position);
        while (motorJ1.isBusy() && !isStop)
        {
            Thread.yield();
        }
    }

    public void toPositionReverse(double j1Power, double j2Power, double j3Power, int j1Position, int j2Position1, int j2Position2, int j3Position)
    {
        motorJ1.setPower(j1Power);
        motorJ1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ1.setTargetPosition(j1Position);
        while (motorJ1.isBusy() && !isStop)
        {
            Thread.yield();
        }

        motor1J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor1J2.setPower(j2Power);
        motor2J2.setPower(j2Power);
        motor1J2.setTargetPosition(j2Position1);
        motor2J2.setTargetPosition(j2Position2);
        while ((motor1J2.isBusy() || motor2J2.isBusy()) && !isStop)
        {
            Thread.yield();
        }

        motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ3.setPower(j3Power);
        motorJ3.setTargetPosition(j3Position);
        isStop = false;
        while (motorJ3.isBusy() && !isStop)
        {
            Thread.yield();
        }
    }

    public void toInitialPosition()
    {
        System.out.println("Going To Initial Position");
        setManual(false);
        setIsCollectionPlane(false);
        if (motorJ1.getCurrentPosition() > 0)
        {
            j1Power = -J1_POWER;
        }
        else if (motorJ1.getCurrentPosition() < 0)
        {
            j1Power = J1_POWER;
        }

        if (motor1J2.getCurrentPosition() > 0)
        {
            j2Power = -J2_POWER;
        }
        else if (motor1J2.getCurrentPosition() < 0)
        {
            j2Power = J2_POWER;
        }

        if (motor2J2.getCurrentPosition() > 0)
        {
            j2Power = -J2_POWER;
        }
        else if (motor2J2.getCurrentPosition() < 0)
        {
            j2Power = J2_POWER;
        }

        if (motorJ3.getCurrentPosition() > 0)
        {
            j3Power = -UP_POWER_J3;
        }
        else if (motorJ3.getCurrentPosition() < 0)
        {
            j3Power = UP_POWER_J3;
        }
        toPositionReverse(j1Power, j2Power, j3Power, 0, 0, 0, 0);
        System.out.println("Reached Initial Position");
        j1Power = 0;
        j2Power = 0;
        j3Power = 0;
    }


    /**
     * Transition from other positions to initial collection position
     */
    public void toCollectionPlane()
    {
        System.out.println("toCollectionPlane");
        setManual(false);
        if (motorJ1.getCurrentPosition() > initialCollectionPosJ1)
        {
            j1Power = -J1_POWER;
        }
        else if (motorJ1.getCurrentPosition() < initialCollectionPosJ1)
        {
            j1Power = J1_POWER;
        }

        if (motor1J2.getCurrentPosition() > initialCollectionPos1J2)
        {
            j2Power = -J2_POWER;
        }
        else if (motor1J2.getCurrentPosition() < initialCollectionPos1J2)
        {
            j2Power = J2_POWER;
        }

        if (motor2J2.getCurrentPosition() > initialCollectionPos2J2)
        {
            j2Power = -J2_POWER;
        }
        else if (motor2J2.getCurrentPosition() < initialCollectionPos2J2)
        {
            j2Power = J2_POWER;
        }

        if (motorJ3.getCurrentPosition() > initialCollectionPosJ3)
        {
            j3Power = -UP_POWER_J3;
        }
        else if (motorJ3.getCurrentPosition() < initialCollectionPosJ3)
        {
            j3Power = UP_POWER_J3;
        }
        toPosition(j1Power, j2Power, j3Power, initialCollectionPosJ1, initialCollectionPos1J2, initialCollectionPos2J2, initialCollectionPosJ3);
        setIsCollectionPlane(true);
        j1Power = 0;
        j2Power = 0;
        j3Power = 0;
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
        if (motorJ1.getCurrentPosition() > GOLD_DROP_J1)
        {
            j1Power = -J1_POWER;
        }
        else if (motorJ1.getCurrentPosition() < GOLD_DROP_J1)
        {
            j1Power = J1_POWER;
        }

        if (motor1J2.getCurrentPosition() > GOLD_DROP_J2)
        {
            j2Power = -J2_POWER;
        }
        else if (motor1J2.getCurrentPosition() < GOLD_DROP_J2)
        {
            j2Power = J2_POWER;
        }

        if (motor2J2.getCurrentPosition() > GOLD_DROP_J2)
        {
            j2Power = -J2_POWER;
        }
        else if (motor2J2.getCurrentPosition() < GOLD_DROP_J2)
        {
            j2Power = J2_POWER;
        }

        if (motorJ3.getCurrentPosition() > GOLD_DROP_J3)
        {
            j3Power = -UP_POWER_J3;
        }
        else if (motorJ3.getCurrentPosition() < GOLD_DROP_J3)
        {
            j3Power = UP_POWER_J3;
        }
        toPosition(j1Power, j2Power, j3Power, GOLD_DROP_J1, GOLD_DROP_J2, GOLD_DROP_J2, GOLD_DROP_J3);
        j1Power = 0;
        j2Power = 0;
        j3Power = 0;
    }

    /**
     * Transition from other positions to silver deposit position
     */
    public void silverDropPoint()
    {
        System.out.println("silverDropPoint");
        if (isCollectionPlane)
        {
            savePreviousPosition();
            setIsCollectionPlane(false);
        }
        if (motorJ1.getCurrentPosition() > SILVER_DROP_J1)
        {
            j1Power = -J1_POWER;
        }
        else if (motorJ1.getCurrentPosition() < SILVER_DROP_J1)
        {
            j1Power = J1_POWER;
        }

        if (motor1J2.getCurrentPosition() > SILVER_DROP_J2)
        {
            j2Power = -J2_POWER;
        }
        else if (motor1J2.getCurrentPosition() < SILVER_DROP_J2)
        {
            j2Power = J2_POWER;
        }

        if (motor2J2.getCurrentPosition() > SILVER_DROP_J2)
        {
            j2Power = -J2_POWER;
        }
        else if (motor2J2.getCurrentPosition() < SILVER_DROP_J2)
        {
            j2Power = J2_POWER;
        }

        if (motorJ3.getCurrentPosition() > SILVER_DROP_J3)
        {
            j3Power = -UP_POWER_J3;
        }
        else if (motorJ3.getCurrentPosition() < SILVER_DROP_J3)
        {
            j3Power = UP_POWER_J3;
        }
        toPosition(j1Power, j2Power, j3Power, SILVER_DROP_J1, SILVER_DROP_J2, SILVER_DROP_J2, SILVER_DROP_J3);
        j1Power = 0;
        j2Power = 0;
        j3Power = 0;
    }

    public void toLander()
    {
        if (motorJ1.getCurrentPosition() > -842)
        {
            j1Power = -J1_POWER;
        }
        else if (motorJ1.getCurrentPosition() < -842)
        {
            j1Power = J1_POWER;
        }

        if (motor1J2.getCurrentPosition() > 531)
        {
            j2Power = -J2_POWER;
        }
        else if (motor1J2.getCurrentPosition() < 531)
        {
            j2Power = J2_POWER;
        }

        if (motor2J2.getCurrentPosition() > 531)
        {
            j2Power = -J2_POWER;
        }
        else if (motor2J2.getCurrentPosition() < 531)
        {
            j2Power = J2_POWER;
        }

        if (motorJ3.getCurrentPosition() > -911)
        {
            j3Power = -UP_POWER_J3;
        }
        else if (motorJ3.getCurrentPosition() < -911)
        {
            j3Power = UP_POWER_J3;
        }
        toPosition(j1Power, j2Power, j3Power, -842, 531, 531, -911);
    }

    public void lowerJ4()
    {
        double tempJ3 = Math.toRadians(Math.abs((motorJ3.getCurrentPosition()) + INITIAL_TICKS_J3) / TICKS_PER_DEGREE_J3);
        double k      = Math.sqrt(Math.pow(ARM_L1, 2) + Math.pow(ARM_L2, 2) - Math.cos(tempJ3) * 2 * ARM_L1 * ARM_L2);
        double L3     = Math.sqrt(Math.pow(k, 2) - Math.pow(HEIGHT_PLANE_OF_MOTION, 2));
        k = Math.sqrt(Math.pow(L3, 2) + Math.pow(J4_COLLECTION_HEIGHT, 2));
        double collectingJ21 = Math.toDegrees(Math.acos(J4_COLLECTION_HEIGHT / k));
        double collectingJ22 = Math.toDegrees(Math.acos((Math.pow(ARM_L1, 2) + Math.pow(k, 2) - Math.pow(ARM_L2, 2)) / (2 * ARM_L1 * k)));
        double collectingJ2  = collectingJ21 + collectingJ22;
        double collectingJ3  = Math.toDegrees(Math.acos((Math.pow(ARM_L1, 2) + Math.pow(ARM_L2, 2) - Math.pow(k, 2)) / (2 * ARM_L1 * ARM_L2)));
        motor1J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor1J2.setPower(J2_POWER);
        motor2J2.setPower(J2_POWER);
        motorJ3.setPower(UP_POWER_J3);
        motor1J2.setTargetPosition((int) ((collectingJ2 - INITIAL_DEGREES_J2) * TICKS_PER_DEGREE_J2));
        motor2J2.setTargetPosition((int) ((collectingJ2 - INITIAL_DEGREES_J2) * TICKS_PER_DEGREE_J2));
        motorJ3.setTargetPosition((int) ((collectingJ3 - INITIAL_TICKS_J3) * TICKS_PER_DEGREE_J3));
        while (motor2J2.isBusy() || motor1J2.isBusy() || motorJ3.isBusy() && !isStop)
        {
            Thread.yield();
        }
    }


    public boolean isCollectionPlane()
    {
        return isCollectionPlane;
    }

    public void setIsCollectionPlane(boolean collectionPlane)
    {
        isCollectionPlane = collectionPlane;
    }

    public void setManual(boolean manual)
    {
        isManual = manual;
    }

    public boolean isManual()
    {
        return isManual;
    }

    public boolean isCollecting()
    {
        return isCollecting;
    }

    public void setCollecting(boolean collecting)
    {
        isCollecting = collecting;
    }
}
