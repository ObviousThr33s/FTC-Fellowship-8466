package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.teamcode.Samwise.DriveTrain.SamwiseDriveTrainIMU;
import java.util.concurrent.TimeUnit;

public class OctoSamwiseSmart extends OctoSamwiseArm
{
    SamwiseDriveTrainIMU robot = new SamwiseDriveTrainIMU();

    private static final int OFFSET_TOLERANCE = 10;
    boolean isStop = false;

    static int J1_LANDER = -600;
    static int J2_LANDER = 459;
    static int J3_LANDER = 2541;


    int initialCollectionPosJ1 = 1450;
    int initialCollectionPosJ2 = 1462;
    int initialCollectionPosJ3 = 1387;

    public int pos2J2 = 1813;
    public int pos2J3 = 1836;

    public int pos3J2 = 2046;
    public int pos3J3 = 2546;

    public int pos4J2 = 2307;
    public int pos4J3 = 3230;

    int previousPositionJ1 = initialCollectionPosJ1;
    int previousPositionJ2 = initialCollectionPosJ2;
    int previousPositionJ3 = initialCollectionPosJ3;

    static final double J1_POWER = 0.4;
    static final double J2_POWER = 0.3;
    static final double J3_POWER = 0.4;

    static final int SAFE_MARGIN = 100;

    private static final int SAFE_POS_J1 = -200;
    private static final int SAFE_POS_J2 = 861;
    private static final int SAFE_POS_J3 = 700;

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

    public void toInitialPosition()
    {
        toPositionReverse(J1_POWER, J2_POWER, J3_POWER, 0, 0, 0, 0);
    }

    public void toCollectionPlane()
    {
        toPosition(J1_POWER, J2_POWER, J3_POWER, initialCollectionPosJ1, initialCollectionPosJ2, initialCollectionPosJ2, initialCollectionPosJ3);
    }

    public void toLander()
    {
        System.out.println("------------------------- toLander start -----------------");
        stopSam();
        savePreviousPosition();
        //        toPositionWithSam(J1_POWER, J2_POWER, J3_POWER, J1_LANDER, J2_LANDER, J2_LANDER, J3_LANDER, true);
//        robot.makeTurnWithoutWait(90);
        toPosition(J1_POWER, J2_POWER, J3_POWER, J1_LANDER, J2_LANDER, J2_LANDER, J3_LANDER);
        System.out.println("------------------------- toLander end -----------------");
    }

    public void backFromLander()
    {
        System.out.println("------------------------- backFromLander start -----------------");
        //        toPositionWithSam(J1_POWER, J2_POWER, J3_POWER, previousPositionJ1, previousPositionJ2, previousPositionJ2, previousPositionJ3, false);

//        robot.makeTurnWithoutWait(-90);
        stopSam();
        toPositionReverse(J1_POWER, J2_POWER, J3_POWER, previousPositionJ1, previousPositionJ2, previousPositionJ2, previousPositionJ3);
        System.out.println("------------------------- backFromLander end -----------------");
    }

    public void stopSam()
    {
        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);
        this.stopJ4();
        this.stopCollecting();
    }

    public void stopAll()
    {
        stop();
        stopJ1();
        stopJ2();
        stopJ3();
        stopJ4();
        stopExtendL1();
        stopExtendL2();
    }
    public void saveLanderPosition()
    {
        J1_LANDER = motorJ1.getCurrentPosition();
        J2_LANDER = motor1J2.getCurrentPosition();
        J3_LANDER = motorJ3.getCurrentPosition();
    }

    public void savePreviousPosition()
    {
        previousPositionJ1 = motorJ1.getCurrentPosition();
        previousPositionJ2 = motor1J2.getCurrentPosition();
        previousPositionJ2 = motor2J2.getCurrentPosition();
        previousPositionJ3 = motorJ3.getCurrentPosition();
    }

    public void toPosition(double j1Power, double j2Power, double j3Power, int j1Position, int j2Position1, int j2Position2, int j3Position)
    {
        runTime.reset();


        isStop = false;

        motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ3.setTargetPosition(j3Position);
        motorJ3.setPower(j3Power);
/*
        while (motorJ3.getCurrentPosition() > SAFE_POS_J3 && runTime.time(TimeUnit.SECONDS) < 3)
        {
            try
            {
                Thread.sleep(10);
            }
            catch (Exception e) {}
        }*/

        motor1J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor1J2.setTargetPosition(j2Position1);
        motor2J2.setTargetPosition(j2Position2);
        motor1J2.setPower(j2Power);
        motor2J2.setPower(j2Power);

/*
        while (motor2J2.getCurrentPosition() > SAFE_POS_J2 && runTime.time(TimeUnit.SECONDS) < 3)
    {
        try
        {
            Thread.sleep(10);
        }
        catch (Exception e) {}
    }*/

        motorJ1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ1.setTargetPosition(j1Position);
        motorJ1.setPower(j1Power);

        System.out.println("toPosition Time1: " + runTime.time(TimeUnit.SECONDS));

        while ((motor1J2.isBusy() || motor2J2.isBusy() || motorJ3.isBusy() || motorJ1.isBusy()) && !isStop && runTime.time(TimeUnit.SECONDS) < 4)
            while ((Math.abs(motorJ1.getCurrentPosition() - j1Position) > SAFE_MARGIN || Math.abs(motor1J2.getCurrentPosition() - j2Position1) > SAFE_MARGIN || Math.abs(motorJ3.getCurrentPosition() - j3Position) > SAFE_MARGIN) && !isStop && runTime.time(TimeUnit.SECONDS) < 4)
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

    public void toPositionReverse(double j1Power, double j2Power, double j3Power, int j1Position, int j2Position1, int j2Position2, int j3Position)
    {
        runTime.reset();
        motorJ1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ1.setTargetPosition(j1Position);
        motorJ1.setPower(j1Power);
        isStop = false;

        while (motorJ1.getCurrentPosition() < SAFE_POS_J1 && runTime.time(TimeUnit.SECONDS) < 3)
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
        while ((Math.abs(motorJ1.getCurrentPosition() - j1Position) > SAFE_MARGIN || Math.abs(motor1J2.getCurrentPosition() - j2Position1) > SAFE_MARGIN || Math.abs(motorJ3.getCurrentPosition() - j3Position) > SAFE_MARGIN) && !isStop && runTime.time(TimeUnit.SECONDS) < 4)
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
            robot.leftDrive.setPower(-0.5);
            robot.rightDrive.setPower(0.5);
        }
        else
        {
            robot.leftDrive.setPower(0.5);
            robot.rightDrive.setPower(-0.5);
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
        while ((Math.abs(motorJ1.getCurrentPosition() - j1Position) > SAFE_MARGIN || Math.abs(motor1J2.getCurrentPosition() - j2Position1) > SAFE_MARGIN || Math.abs(motorJ3.getCurrentPosition() - j3Position) > SAFE_MARGIN) && !isStop && runTime.time(TimeUnit.SECONDS) < 4)
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
                if (currAngle <= -90)
                {
                    robot.rightDrive.setPower(0);
                    robot.leftDrive.setPower(0);
                }
            }
            else
            {
                if (currAngle >= 90)
                {
                    robot.rightDrive.setPower(0);
                    robot.leftDrive.setPower(0);
                }
            }
        }
        System.out.println("toPositionWithSam Time2: " + runTime.time(TimeUnit.SECONDS));
    }

}
