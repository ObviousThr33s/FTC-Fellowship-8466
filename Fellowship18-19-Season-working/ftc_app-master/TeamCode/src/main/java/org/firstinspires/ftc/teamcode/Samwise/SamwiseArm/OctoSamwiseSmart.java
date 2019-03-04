package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.teamcode.Samwise.DriveTrain.SamwiseDriveTrainIMU;

import java.util.concurrent.TimeUnit;


public class OctoSamwiseSmart extends OctoSamwiseArm
{
    SamwiseDriveTrainIMU robot = new SamwiseDriveTrainIMU();   // Use a drivetrain's hardware
    private static final int OFFSET_TOLERANCE = 10;
    private boolean isStop = false;


    boolean isCollectionPlane = false;

    boolean isManual = false;

    boolean isCollecting = false;


    static final int TICKS_PER_REVOLUTION_J1 = 1680;
    static final int TICKS_PER_REVOLUTION_J2 = 1440;
    static final double TICKS_PER_REVOLUTION_J3 = 1993.6;
    static final double TICKS_PER_DEGREE_J1 = TICKS_PER_REVOLUTION_J1 / 360.0;
    static final double TICKS_PER_DEGREE_J2 = (TICKS_PER_REVOLUTION_J2 / 360.0) * 4;
    static final double TICKS_PER_DEGREE_J3 = (TICKS_PER_REVOLUTION_J3 / 360.0) * 2;

    static final int GOLD_DROP_J1 = -1294;
    static final int SILVER_DROP_J1 = -955;
    static final int J1_LANDER = -1095;
    static final int J2_LANDER = -221;
    static final int J3_LANDER = -2449;

    static final double INITIAL_DEGREES_J2 = 240;
    static final double INITIAL_DEGREES_J3 = 50;
    static final int INITIAL_TICKS_J3 = (int) (INITIAL_DEGREES_J3 * TICKS_PER_DEGREE_J3);
    static final int INITIAL_TICKS_J2 = (int) (INITIAL_DEGREES_J2 * TICKS_PER_DEGREE_J2);

    static final double HEIGHT_PLANE_OF_MOTION = 12; //height of plane of motion
    static final double J4_COLLECTION_HEIGHT = 2.5;
    static final double ARM_L1 = 14.5; //length between motorJoint1 and motorJoint2
    static final double ARM_L2 = 17.1; //length between motorJoint2 and servoJ4

    public int initialCollectionPosJ1 = 1114;
    public int initialCollectionPos1J2 = -1401;
    public int initialCollectionPos2J2 = -1401;
    public int initialCollectionPosJ3 = -1559;

    int previousPositionJ1 = initialCollectionPosJ1;
    int previousPosition1J2 = initialCollectionPos1J2;
    int previousPosition2J2 = initialCollectionPos2J2;
    int previousPositionJ3 = initialCollectionPosJ3;

    static final double J1_POWER = 0.4;
    static final double J2_POWER = 0.3;
    static final double J3_POWER = 0.4;

    static final int SAFE_MARGIN = 100;

    private static final int SAFE_POS_J1 = -200;
    private static final int SAFE_POS_J2 = -861;
    private static final int SAFE_POS_J3 = -700;

    ElapsedTime runTime = new ElapsedTime();

    public OctoSamwiseSmart(HardwareMap hwm)
    {
        super(hwm);

        robot.init(hwm);

        motorJ1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor1J2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor2J2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorJ3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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
//        toPositionWithSam(J1_POWER, J2_POWER, J3_POWER, previousPositionJ1, previousPosition1J2, previousPosition2J2, previousPositionJ3, false);
        toPositionReverse(J1_POWER, J2_POWER, J3_POWER, previousPositionJ1, previousPosition2J2, previousPosition1J2, previousPositionJ3);
    }

    public void toPosition(double j1Power, double j2Power, double j3Power, int j1Position, int j2Position1, int j2Position2, int j3Position)
    {
        runTime.reset();


        isStop = false;

        motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ3.setTargetPosition(j3Position);
        motorJ3.setPower(j3Power);

        while (motorJ3.getCurrentPosition() > SAFE_POS_J3 && runTime.time(TimeUnit.SECONDS) < 3)
        {
            try
            {
                Thread.sleep(10);
            }
            catch (Exception e) {}
        }


        motor1J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor1J2.setTargetPosition(j2Position1);
        motor2J2.setTargetPosition(j2Position2);
        motor1J2.setPower(j2Power);
        motor2J2.setPower(j2Power);


        while (motor2J2.getCurrentPosition() > SAFE_POS_J2 && runTime.time(TimeUnit.SECONDS) < 3)
        {
            try
            {
                Thread.sleep(10);
            }
            catch (Exception e) {}
        }

        motorJ1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ1.setTargetPosition(j1Position);
        motorJ1.setPower(j1Power);

        System.out.println("toPosition Time1: " + runTime.time(TimeUnit.SECONDS));
        ;
//        while ((motor1J2.isBusy() || motor2J2.isBusy() || motorJ3.isBusy() || motorJ1.isBusy()) && !isStop && runTime.time(TimeUnit.SECONDS) < 4)
        while ((Math.abs(motorJ1.getCurrentPosition() - j1Position) > SAFE_MARGIN ||
                Math.abs(motor1J2.getCurrentPosition() - j2Position1) > SAFE_MARGIN ||
                Math.abs(motorJ3.getCurrentPosition() - j3Position) > SAFE_MARGIN) &&
               !isStop && runTime.time(TimeUnit.SECONDS) < 4)
        {
            try
            {
                Thread.sleep(10);
            }
            catch (Exception e)
            {
            }
        }
        System.out.println("toPosition Time2: " + runTime.time(TimeUnit.SECONDS));
    }

    public void toPositionWithSam(double j1Power, double j2Power, double j3Power, int j1Position, int j2Position1, int j2Position2, int j3Position, boolean left)
    {
        runTime.reset();
        robot.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //restart imu movement tracking.
        robot.resetAngle(AxesOrder.ZYX);

        // set power to rotate.
        if (left)
        {
            robot.leftDrive.setPower(0.5);
            robot.rightDrive.setPower(-0.5);
        }
        else
        {
            robot.leftDrive.setPower(-0.5);
            robot.rightDrive.setPower(0.5);
        }

        motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ3.setTargetPosition(j3Position);
        motorJ3.setPower(j3Power);
        isStop = false;
        motor1J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor1J2.setTargetPosition(j2Position1);
        motor2J2.setTargetPosition(j2Position2);
        motor1J2.setPower(j2Power);
        motor2J2.setPower(j2Power);

        while (motor2J2.getCurrentPosition() > SAFE_POS_J2 && runTime.time(TimeUnit.SECONDS) < 3)
        {
            try
            {
                Thread.sleep(10);
            }
            catch (Exception e) {}
        }

        System.out.println("toPositionWithSam Time1: " + runTime.time(TimeUnit.SECONDS));
        motorJ1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ1.setTargetPosition(j1Position);
        motorJ1.setPower(j1Power);

//        while ((motor1J2.isBusy() || motor2J2.isBusy() || motorJ3.isBusy() || motorJ1.isBusy()) && !isStop && runTime.time(TimeUnit.SECONDS) < 4)
        while ((Math.abs(motorJ1.getCurrentPosition() - j1Position) > SAFE_MARGIN ||
                Math.abs(motor1J2.getCurrentPosition() - j2Position1) > SAFE_MARGIN ||
                Math.abs(motorJ3.getCurrentPosition() - j3Position) > SAFE_MARGIN) &&
               !isStop && runTime.time(TimeUnit.SECONDS) < 4)
        {
            try
            {
                Thread.sleep(10);
            }
            catch (Exception e)
            {
            }

            double currAngle = robot.getAngle(AxesOrder.ZYX);
            if (left)
            {
                if (currAngle >= 90)
                {
                    robot.rightDrive.setPower(0);
                    robot.leftDrive.setPower(0);
                }
            }
            else
            {
                if (currAngle <= -90)
                {
                    robot.rightDrive.setPower(0);
                    robot.leftDrive.setPower(0);
                }
            }
        }
        System.out.println("toPositionWithSam Time2: " + runTime.time(TimeUnit.SECONDS));
    }

    public void toPositionReverse(double j1Power, double j2Power, double j3Power, int j1Position, int j2Position1, int j2Position2, int j3Position)
    {
        runTime.reset();
        motorJ1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ1.setTargetPosition(j1Position);
        motorJ1.setPower(j1Power);
        isStop = false;

        while (motorJ1.getCurrentPosition() < SAFE_POS_J1 && runTime.time(TimeUnit.SECONDS) < 4)
        {
            try
            {
                Thread.sleep(10);
            }
            catch (Exception e) {}
        }

        System.out.println("toPositionReverse Time1: " + runTime.time(TimeUnit.SECONDS));
        motor1J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor1J2.setTargetPosition(j2Position1);
        motor2J2.setTargetPosition(j2Position2);
        motor1J2.setPower(j2Power);
        motor2J2.setPower(j2Power);

        motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ3.setTargetPosition(j3Position);
        motorJ3.setPower(j3Power);

//        while ((motor1J2.isBusy() || motor2J2.isBusy() || motorJ3.isBusy() || motorJ1.isBusy()) && !isStop && runTime.time(TimeUnit.SECONDS) < 4)
        while ((Math.abs(motorJ1.getCurrentPosition() - j1Position) > SAFE_MARGIN ||
                Math.abs(motor1J2.getCurrentPosition() - j2Position1) > SAFE_MARGIN ||
                Math.abs(motorJ3.getCurrentPosition() - j3Position) > SAFE_MARGIN) &&
               !isStop && runTime.time(TimeUnit.SECONDS) < 4)
        {
            try
            {
                Thread.sleep(10);
            }
            catch (Exception e)
            {
            }
        }
        System.out.println("toPositionReverse Time2: " + runTime.time(TimeUnit.SECONDS));
    }

    public void toInitialPosition()
    {
        System.out.println("Going To Initial Position");
        setManual(false);
        setIsCollectionPlane(false);
        toPositionReverse(J1_POWER, J2_POWER, J3_POWER, 0, 0, 0, 0);
        System.out.println("Reached Initial Position");
    }


    /**
     * Transition from other positions to initial collection position
     */
    public void toCollectionPlane()
    {
        System.out.println("toCollectionPlane");
        setManual(false);
        toPosition(J1_POWER, J2_POWER, J3_POWER, initialCollectionPosJ1, initialCollectionPos1J2, initialCollectionPos2J2, initialCollectionPosJ3);
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
        toPosition(J1_POWER, J2_POWER, J3_POWER, GOLD_DROP_J1, J2_LANDER, J2_LANDER, J3_LANDER);
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
        toPosition(J1_POWER, J2_POWER, J3_POWER, SILVER_DROP_J1, J2_LANDER, J2_LANDER, J3_LANDER);
    }

    public void toLander()
    {
        savePreviousPosition();

        //        toPositionWithSam(J1_POWER, J2_POWER, J3_POWER, J1_LANDER, J2_LANDER, J2_LANDER, J3_LANDER,true);
        toPosition(J1_POWER, J2_POWER, J3_POWER, J1_LANDER, J2_LANDER, J2_LANDER, J3_LANDER);
    }

    public void testDrive()
    {
        toPositionWithSam(0, 0, 0.05, getJ1CurrentPosition(), getJ2CurrentPosition(), getJ2CurrentPosition(), 100, true);
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
        motorJ3.setPower(J3_POWER);
        motor1J2.setTargetPosition((int) ((collectingJ2 - INITIAL_DEGREES_J2) * TICKS_PER_DEGREE_J2));
        motor2J2.setTargetPosition((int) ((collectingJ2 - INITIAL_DEGREES_J2) * TICKS_PER_DEGREE_J2));
        motorJ3.setTargetPosition((int) ((collectingJ3 - INITIAL_TICKS_J3) * TICKS_PER_DEGREE_J3));
        while (motor2J2.isBusy() || motor1J2.isBusy() || motorJ3.isBusy() && !isStop)
        {
            //            Thread.yield();
        }
    }

    public void stopSam()
    {
        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);
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
