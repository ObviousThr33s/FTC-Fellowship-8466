package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Samwise.DriveTrain.SamwiseDriveTrain;

import java.util.concurrent.TimeUnit;

public class OctoSamwiseSmart extends OctoSamwiseArm
{
    SamwiseDriveTrain robot = new SamwiseDriveTrain();

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


    /********************** 90/90 turn deposit extednded initial ***********************/

    int J1_LANDER_GOLD_extendedArm = -600;
    int J2_LANDER_GOLD_extendedArm = 459;
    int J3_LANDER_GOLD_extendedArm = 2541;

    int J1_LANDER_SILVER_extendedArm = -900;
    int J2_LANDER_SILVER_extendedArm = 459;
    int J3_LANDER_SILVER_extendedArm = 2541;

    int initialCollectionPosJ1_extendedArm = 1450;
    int initialCollectionPosJ2_extendedArm = 1462;
    int initialCollectionPosJ3_extendedArm = 1387;

    int previousPositionJ1_extendedArm = initialCollectionPosJ1_extendedArm;
    int previousPositionJ2_extendedArm = initialCollectionPosJ2_extendedArm;
    int previousPositionJ3_extendedArm = initialCollectionPosJ3_extendedArm;

    /********************** Overhead deposit start ***********************/

    int J1_LANDER_GOLD_OVERHEAD = -600;
    int J2_LANDER_GOLD_OVERHEAD = 792;
    int J3_LANDER_GOLD_OVERHEAD = 6985;

    int J1_LANDER_SILVER_OVERHEAD = -900;
    int J2_LANDER_SILVER_OVERHEAD = 792;
    int J3_LANDER_SILVER_OVERHEAD = 6985;

    int initialCollectionPosJ1_overhead = -600;
    int initialCollectionPosJ2_overhead = 1462;
    int initialCollectionPosJ3_overhead = 1387;

    int previousCollectionPositionJ1_overhead = initialCollectionPosJ1_overhead;
    int previousCollectionPositionJ2_overhead = initialCollectionPosJ2_overhead;
    int previousCollectionPositionJ3_overhead = initialCollectionPosJ3_overhead;

    /********************** sShared ***********************/
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

    public void toLanderGold()
    {
        if (this.inGoldDeposit) return;


        runTime.reset();
        this.inGoldDeposit = true;
        this.inCollectionPlane = false;
        if (this.inSilverDeposit)
        {
            this.saveLanderPositionSilver();
            toPosition(J1_POWER, J2_POWER, J3_POWER, J1_LANDER_GOLD, J2_LANDER_GOLD, J2_LANDER_GOLD, J3_LANDER_GOLD);
        }
        else if (this.inCollectionPlane)
        {
            savePreviousPosition();
            toPositionWait(J1_POWER, J2_POWER, J3_POWER, J1_LANDER_GOLD, J2_LANDER_GOLD, J2_LANDER_GOLD, J3_LANDER_GOLD);
        }
        else
            System.out.println("Accidentally pressed gold deposit");
    }

    public void toLanderSilver()
    {
        if (this.inSilverDeposit) return;

        runTime.reset();
        this.inSilverDeposit = true;
        this.inCollectionPlane = false;
        if (this.inGoldDeposit)
        {
            this.saveLanderPositionGold();
            toPosition(J1_POWER, J2_POWER, J3_POWER, J1_LANDER_SILVER, J2_LANDER_SILVER, J2_LANDER_SILVER, J3_LANDER_SILVER);
        }
        else if (this.inCollectionPlane)
        {
            savePreviousPosition();
            toPositionWait(J1_POWER, J2_POWER, J3_POWER, J1_LANDER_SILVER, J2_LANDER_SILVER, J2_LANDER_SILVER, J3_LANDER_SILVER);
        }
        else
            System.out.println("Accidentally pressed silver deposit");
    }

    public void backFromLander()
    {
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

    /*********************** overhead ***************/

    public void toCollectionPlaneOverhead()
    {
        toPosition(J1_POWER, J2_POWER, J3_POWER, initialCollectionPosJ1_overhead, initialCollectionPosJ2_overhead, initialCollectionPosJ2_overhead, initialCollectionPosJ3_overhead);
    }

    public void toLanderGoldOverhead()
    {
        this.inSilverDeposit = true;

        runTime.reset();
        savePreviousPositionOverhead();
        toPosition(J1_POWER, J2_POWER, J3_POWER, J1_LANDER_GOLD_OVERHEAD, J2_LANDER_GOLD_OVERHEAD, J2_LANDER_GOLD_OVERHEAD, J3_LANDER_GOLD_OVERHEAD);
        robot.encoderDrive(null, 15, 3);
    }

    public void toLanderSilverOverhead()
    {
        this.inSilverDeposit = true;
        runTime.reset();
        savePreviousPositionOverhead();
        toPosition(J1_POWER, J2_POWER, J3_POWER, J1_LANDER_SILVER_OVERHEAD, J2_LANDER_SILVER_OVERHEAD, J2_LANDER_SILVER_OVERHEAD, J3_LANDER_SILVER_OVERHEAD);
        robot.encoderDrive(null, 15, 3);
    }

    public void backFromLanderOverhead()
    {
        this.saveLanderPositionOverhead();
        this.inSilverDeposit = false;
        this.inGoldDeposit = false;
        stopSam();

        toPosition(J1_POWER, J2_POWER, J3_POWER, previousCollectionPositionJ1_overhead, previousCollectionPositionJ2_overhead, previousCollectionPositionJ2_overhead, previousCollectionPositionJ3_overhead);
        robot.encoderDrive(null, -15, 3);
    }


    public void saveLanderPositionOverhead()
    {
        if (this.inSilverDeposit)
        {
            this.saveLanderPositionOverheadSilver();
        }
        if (this.inGoldDeposit)
        {
            this.saveLanderPositionOverheadGold();
        }
    }


    private void saveLanderPositionOverheadGold()
    {
        J1_LANDER_GOLD_OVERHEAD = motorJ1.getCurrentPosition();
        J2_LANDER_GOLD_OVERHEAD = motor1J2.getCurrentPosition();
        J3_LANDER_GOLD_OVERHEAD = motorJ3.getCurrentPosition();
    }

    private void saveLanderPositionOverheadSilver()
    {
        J1_LANDER_SILVER_OVERHEAD = motorJ1.getCurrentPosition();
        J2_LANDER_SILVER_OVERHEAD = motor1J2.getCurrentPosition();
        J3_LANDER_SILVER_OVERHEAD = motorJ3.getCurrentPosition();
    }


    public void savePreviousPositionOverhead()
    {
        previousCollectionPositionJ1_overhead = motorJ1.getCurrentPosition();
        previousCollectionPositionJ2_overhead = motor2J2.getCurrentPosition();
        previousCollectionPositionJ3_overhead = motorJ3.getCurrentPosition();
    }

    /********************* start from extended arm ****************************/

    public void toCollectionPlaneExtendedArm()
    {
        toPosition(J1_POWER, J2_POWER, J3_POWER, initialCollectionPosJ1_extendedArm, initialCollectionPosJ2_extendedArm, initialCollectionPosJ2_extendedArm, initialCollectionPosJ3_extendedArm);
    }

    public void toLanderGoldExtendedArm()
    {
        this.inGoldDeposit = true;

        runTime.reset();
        savePreviousPositionExtendedArm();
        robot.makeTurnWithoutWait(-90);
        toPosition(J1_POWER, J2_POWER, J3_POWER, J1_LANDER_GOLD_extendedArm, J2_LANDER_GOLD_extendedArm, J2_LANDER_GOLD_extendedArm, J3_LANDER_GOLD_extendedArm);
        stopSam();
    }

    public void toLanderSilverExtendedArm()
    {
        this.inSilverDeposit = true;

        runTime.reset();
        savePreviousPositionExtendedArm();
        robot.makeTurnWithoutWait(-90);
        toPosition(J1_POWER, J2_POWER, J3_POWER, J1_LANDER_SILVER_extendedArm, J2_LANDER_SILVER_extendedArm, J2_LANDER_SILVER_extendedArm, J3_LANDER_SILVER_extendedArm);
        stopSam();
    }

    public void backFromLanderExtendedArm()
    {
        this.saveLanderPositionExtendedArm();
        this.inSilverDeposit = false;
        this.inGoldDeposit = false;
        stopSam();
        robot.makeTurnWithoutWait(90);
        toPosition(J1_POWER, J2_POWER, J3_POWER, previousPositionJ1_extendedArm, previousPositionJ2_extendedArm, previousPositionJ2_extendedArm, previousPositionJ3_extendedArm);
    }

    public void saveLanderPositionExtendedArm()
    {
        if (this.inSilverDeposit)
        {
            this.saveLanderPositionSilverExtendedArm();
        }
        if (this.inGoldDeposit)
        {
            this.saveLanderPositionGoldExtendedArm();
        }
    }

    private void saveLanderPositionGoldExtendedArm()
    {
        J1_LANDER_GOLD_extendedArm = motorJ1.getCurrentPosition();
        J2_LANDER_GOLD_extendedArm = motor1J2.getCurrentPosition();
        J3_LANDER_GOLD_extendedArm = motorJ3.getCurrentPosition();
    }

    private void saveLanderPositionSilverExtendedArm()
    {
        J1_LANDER_SILVER_extendedArm = motorJ1.getCurrentPosition();
        J2_LANDER_SILVER_extendedArm = motor1J2.getCurrentPosition();
        J3_LANDER_SILVER_extendedArm = motorJ3.getCurrentPosition();
    }

    public void savePreviousPositionExtendedArm()
    {
        previousPositionJ1_extendedArm = motorJ1.getCurrentPosition();
        previousPositionJ2_extendedArm = motor2J2.getCurrentPosition();
        previousPositionJ3_extendedArm = motorJ3.getCurrentPosition();
    }

    /*************************** end of extended arm *********************/

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
            // do nothing but wait
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

        robot.makeTurnWithoutWait(-90);

        motorJ1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ1.setTargetPosition(j1Position);
        motorJ1.setPower(j1Power);

        while (Math.abs(motorJ1.getCurrentPosition() - j1Position) > SAFE_MARGIN && runTime.time(TimeUnit.SECONDS) < 4)
        {
        }

        System.out.println("toPosition Time: " + runTime.time(TimeUnit.SECONDS));
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

    public void toPositionReverse(double j1Power, double j2Power, double j3Power, int j1Position, int j2Position1, int j2Position2, int j3Position)
    {
        runTime.reset();
        robot.makeTurnWithoutWait(90);
        motorJ1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ1.setTargetPosition(j1Position);
        motorJ1.setPower(j1Power);
        isStop = false;

        while (motorJ1.getCurrentPosition() < -100 && runTime.time(TimeUnit.SECONDS) < 3)
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
