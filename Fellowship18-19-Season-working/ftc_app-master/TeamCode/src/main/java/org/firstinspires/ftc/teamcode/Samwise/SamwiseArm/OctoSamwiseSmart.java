package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Samwise.DriveTrain.SamwiseDriveTrain;

import java.util.concurrent.TimeUnit;

public class OctoSamwiseSmart extends OctoSamwiseArm
{
    SamwiseDriveTrain robot = new SamwiseDriveTrain();
    TrapezoidSpeed trapezoid = new TrapezoidSpeed();

    private static final int OFFSET_TOLERANCE = 10;
    boolean isStop = false;

    /********************** 90/90 turn deposit collapsed initial ***********************/

    int J1_LANDER_GOLD = -900;
    int J2_LANDER_GOLD = 459;
    int J3_LANDER_GOLD = 2541;

    int J1_LANDER_SILVER = -600;
    int J2_LANDER_SILVER = 459;
    int J3_LANDER_SILVER = 2541;

    int initialCollectionPosJ1 = 1450;
    int initialCollectionPosJ2 = 1462;
    int initialCollectionPosJ3 = 1387;

    int previousPositionJ1 = initialCollectionPosJ1;
    int previousPositionJ2 = initialCollectionPosJ2;
    int previousPositionJ3 = initialCollectionPosJ3;

    /********************** Shared ***********************/
    static final double J1_POWER = 0.6;
    static final double J2_POWER = 0.6;
    static final double J3_POWER = 0.6;

    private boolean inGoldDeposit = false;
    private boolean inSilverDeposit = false;

    public boolean isInCollectionPlane()
    {
        return inCollectionPlane;
    }

    private boolean inCollectionPlane = false;

    public int pos2J2 = 1813;
    public int pos2J3 = 1836;

    public int pos3J2 = 2046;
    public int pos3J3 = 2546;

    public int pos4J2 = 2307;
    public int pos4J3 = 3230;

    static final int SAFE_MARGIN = 20;

    ElapsedTime runTime = new ElapsedTime();

    private static final int SAFE_POS_J1 = -200;
    private static final int SAFE_POS_J2 = 861;
    private static final int SAFE_POS_J3 = 700;

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
        toPosition(J1_POWER, J2_POWER, J3_POWER, 0, 0, 0, 0);
    }

    /***************************** 90/90 ********************/
    public void toCollectionPlane()
    {
        this.inCollectionPlane = true;
        toPosition(J1_POWER, J2_POWER, J3_POWER, initialCollectionPosJ1, initialCollectionPosJ2, initialCollectionPosJ2, initialCollectionPosJ3);
    }

    public void testTrapezoid()
    {
        trapezoid.setTrapezoidTarget(motorJ3, 0.4, -1500);
    }

    public void toLanderGold()
    {
        this.inGoldDeposit = true;
        this.inCollectionPlane = false;

        runTime.reset();
        savePreviousPosition();
        toPositionWait(J1_POWER, J2_POWER, J3_POWER, J1_LANDER_GOLD, J2_LANDER_GOLD, J2_LANDER_GOLD, J3_LANDER_GOLD);
    }

    public void toLanderSilver()
    {
        this.inSilverDeposit = true;
        this.inCollectionPlane = false;

        runTime.reset();
        savePreviousPosition();
        toPositionWait(J1_POWER, J2_POWER, J3_POWER, J1_LANDER_SILVER, J2_LANDER_SILVER, J2_LANDER_SILVER, J3_LANDER_SILVER);
    }

    public void toCrater()
    {
        runTime.reset();
        this.saveLanderPosition();
        this.inSilverDeposit = false;
        this.inGoldDeposit = false;
        this.inCollectionPlane = true;
        toPositionReverse(J1_POWER, 0.3, 0.3, previousPositionJ1, previousPositionJ2, previousPositionJ2, previousPositionJ3);
    }

    public void saveLanderPosition()
    {
        if (this.inSilverDeposit)
        {
            this.saveLanderPositionSilver();
        }
        if (this.inGoldDeposit)
        {
            this.saveLanderPositionGold();
        }
    }


    private void saveLanderPositionGold()
    {
        J1_LANDER_GOLD = motorJ1.getCurrentPosition();
        J2_LANDER_GOLD = motor1J2.getCurrentPosition();
        J3_LANDER_GOLD = motorJ3.getCurrentPosition();
    }

    private void saveLanderPositionSilver()
    {
        J1_LANDER_SILVER = motorJ1.getCurrentPosition();
        J2_LANDER_SILVER = motor1J2.getCurrentPosition();
        J3_LANDER_SILVER = motorJ3.getCurrentPosition();
    }

    public void savePreviousPosition()
    {
        previousPositionJ1 = motorJ1.getCurrentPosition();
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

       /* while (motorJ3.getCurrentPosition() > SAFE_POS_J3 && runTime.time(TimeUnit.SECONDS) < 3)
        {
        }
*/

        motor1J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor1J2.setTargetPosition(j2Position1);
        motor2J2.setTargetPosition(j2Position2);
        motor1J2.setPower(j2Power);
        motor2J2.setPower(j2Power);

       /* while (motor2J2.getCurrentPosition() > SAFE_POS_J2 && runTime.time(TimeUnit.SECONDS) < 3)
    {
    }*/


        motorJ1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ1.setTargetPosition(j1Position);
        motorJ1.setPower(j1Power);

        while ((Math.abs(motorJ1.getCurrentPosition() - j1Position) > SAFE_MARGIN || Math.abs(motor1J2.getCurrentPosition() - j2Position1) > SAFE_MARGIN || Math.abs(motorJ3.getCurrentPosition() - j3Position) > SAFE_MARGIN) && !isStop && runTime.time(TimeUnit.SECONDS) < 4)
        {
        }

        System.out.println("toPosition Time: " + runTime.time(TimeUnit.SECONDS));
    }

    public void toPositionWait(double j1Power, double j2Power, double j3Power, int j1Position, int j2Position1, int j2Position2, int j3Position)
    {
        runTime.reset();

        isStop = false;

        motor1J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor1J2.setTargetPosition(j2Position1);
        motor2J2.setTargetPosition(j2Position2);
        motor1J2.setPower(j2Power);
        motor2J2.setPower(j2Power);

        motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ3.setTargetPosition(j3Position);
        motorJ3.setPower(j3Power);

        while ((motor2J2.getCurrentPosition() - 459) > 50 && motorJ3.getCurrentPosition() > SAFE_POS_J3 && runTime.time(TimeUnit.SECONDS) < 4)
        {
        }

        motorJ1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ1.setTargetPosition(j1Position);
        motorJ1.setPower(j1Power);

        robot.makeTurnWithoutWait(-90);

        while ((Math.abs(motorJ1.getCurrentPosition() - j1Position) > SAFE_MARGIN || robot.leftDrive.getCurrentPosition() < -200 || robot.rightDrive.getCurrentPosition() > 200) && runTime.time(TimeUnit.SECONDS) < 4)
        {
        }

        System.out.println("toPosition Time: " + runTime.time(TimeUnit.SECONDS));
    }

    public void toPositionReverse(double j1Power, double j2Power, double j3Power, int j1Position, int j2Position1, int j2Position2, int j3Position)
    {
        runTime.reset();
        robot.makeTurnWithoutWait(90);

        motorJ1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ1.setTargetPosition(j1Position);
        motorJ1.setPower(j1Power);
        isStop = false;

        while ((motorJ1.getCurrentPosition() < -100 || robot.leftDrive.getCurrentPosition() > 200 || robot.rightDrive.getCurrentPosition() < -200) && runTime.time(TimeUnit.SECONDS) < 4)
        {
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

        while ((Math.abs(motor1J2.getCurrentPosition() - j2Position1) > SAFE_MARGIN || Math.abs(motorJ3.getCurrentPosition() - j3Position) > SAFE_MARGIN) && !isStop && runTime.time(TimeUnit.SECONDS) < 4)
        {
        }
        System.out.println("toPositionReverse Time2: " + runTime.time(TimeUnit.SECONDS));
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

   /* public void toPositionReverse(double j1Power, double j2Power, double j3Power, int j1Position, int j2Position1, int j2Position2, int j3Position)
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
    }*/

   /* public void toPositionWithSam(double j1Power, double j2Power, double j3Power, int j1Position, int j2Position1, int j2Position2, int j3Position, boolean left)
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
    }*/
}
