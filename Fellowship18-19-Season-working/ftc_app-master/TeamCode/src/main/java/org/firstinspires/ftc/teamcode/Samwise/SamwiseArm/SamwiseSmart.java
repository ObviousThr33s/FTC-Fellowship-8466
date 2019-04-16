package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Samwise.DriveTrain.SamwiseDriveTrain;

import java.util.concurrent.TimeUnit;

public class SamwiseSmart extends SamwiseArm
{
    SamwiseDriveTrain robot = new SamwiseDriveTrain();

    private static final int OFFSET_TOLERANCE = 10;
    boolean isStop = false;

    int J1_LANDER_GOLD = -236;
    int J2_LANDER_GOLD = 100;
    int J3_LANDER_GOLD = 2000;

    int J1_LANDER_SILVER = 114;
    int J2_LANDER_SILVER = 100;
    int J3_LANDER_SILVER = 1700;

    int initialCollectionPosJ1 = 2150;
    int initialCollectionPosJ2 = 1320;
    int initialCollectionPosJ3 = 1330;

    int previousPositionJ1 = initialCollectionPosJ1;
    int previousPositionJ2 = initialCollectionPosJ2;
    int previousPositionJ3 = initialCollectionPosJ3;


    static final double J1_POWER = 0.4;
    static final double J2_POWER = 0.4;
    static final double J3_POWER = 0.4;

    private boolean inGoldDeposit = false;
    private boolean inSilverDeposit = false;
    private boolean inCollectionPlane = false;

    public boolean isInCollectionPlane()
    {
        return inCollectionPlane;
    }

    ElapsedTime runTime = new ElapsedTime();

    private static final int SAFE_POS_J1 = -200;
    private static final int SAFE_POS_J2 = 500;
    private static final int SAFE_POS_J3 = 1500;

    public SamwiseSmart(HardwareMap hwm)
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
        toInitialPosition(J1_POWER, J2_POWER, J3_POWER);
        this.inGoldDeposit = false;
        this.inSilverDeposit = false;
        this.inCollectionPlane = false;
    }

    public void toCollectionPlane()
    {
        if (!this.inCollectionPlane)
        {
            if (this.inGoldDeposit || this.inSilverDeposit)
            {
                this.backFromLander();
            }
            else
            {
                toInitialCollectionPosition(J1_POWER, J2_POWER, J3_POWER, initialCollectionPosJ1, initialCollectionPosJ2, initialCollectionPosJ2, initialCollectionPosJ3);
                this.inCollectionPlane = true;
                this.inSilverDeposit = false;
                this.inGoldDeposit = false;
            }
        }
    }

    public void toLanderGold()
    {
        runTime.reset();
        if (!this.inGoldDeposit)
        {
            if (this.inSilverDeposit)
            {
                toPositionWait(false, J1_POWER, J2_POWER, J3_POWER, J1_LANDER_GOLD, J2_LANDER_GOLD, J2_LANDER_GOLD, J3_LANDER_GOLD);
            }
            else
            {
                savePreviousPosition();
                toPositionWait(true, J1_POWER, J2_POWER, J3_POWER, J1_LANDER_GOLD, J2_LANDER_GOLD, J2_LANDER_GOLD, J3_LANDER_GOLD);
            }
            this.inGoldDeposit = true;
            this.inCollectionPlane = false;
            this.inSilverDeposit = false;
        }
    }

    public void toLanderSilver()
    {
        if (!this.inSilverDeposit)
        {
            runTime.reset();
            if (this.inGoldDeposit)
            {
                toPositionWait(false, J1_POWER, J2_POWER, J3_POWER, J1_LANDER_SILVER, J2_LANDER_SILVER, J2_LANDER_SILVER, J3_LANDER_SILVER);
            }
            else
            {
                savePreviousPosition();
                toPositionWait(true, J1_POWER, J2_POWER, J3_POWER, J1_LANDER_SILVER, J2_LANDER_SILVER, J2_LANDER_SILVER, J3_LANDER_SILVER);
            }
            this.inSilverDeposit = true;
            this.inCollectionPlane = false;
            this.inGoldDeposit = false;
        }
    }

    public void backFromLander()
    {
        if (!this.inCollectionPlane)
        {
            runTime.reset();
            this.saveLanderPosition();
            toPositionReverse(J1_POWER, J2_POWER, 0.3, previousPositionJ1, previousPositionJ2, previousPositionJ2, previousPositionJ3);
            this.inSilverDeposit = false;
            this.inGoldDeposit = false;
            this.inCollectionPlane = true;
        }
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

    public void toInitialPosition(double j1Power, double j2Power, double j3Power)
    {
        runTime.reset();

        isStop = false;

        motorJ1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ1.setTargetPosition(0);
        //        motorJ1.setPower(j1Power);
        TrapezoidHelper.trapezoidDriveJ1(motorJ1, j1Power);

        motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ3.setTargetPosition(0);
        //        motorJ3.setPower(j3Power);
        TrapezoidHelper.trapezoidDriveJ3(motorJ3, j3Power);

        motor1J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor1J2.setTargetPosition(0);
        motor2J2.setTargetPosition(0);
        //        motor1J2.setPower(j2Power);
        //        motor2J2.setPower(j2Power);
        TrapezoidHelper.trapezoidDriveJ2(motor1J2, motor2J2, j2Power);
        System.out.println("toPosition Time: " + runTime.time(TimeUnit.SECONDS));
    }

    public void toInitialCollectionPosition(double j1Power, double j2Power, double j3Power, int j1Position, int j2Position1, int j2Position2, int j3Position)
    {
        runTime.reset();

        isStop = false;

        // J3
        motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ3.setTargetPosition(j3Position);
        //        motorJ3.setPower(j3Power);
        TrapezoidHelper.trapezoidDriveJ3(motorJ3, j3Power);

        //J2
        motor1J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor1J2.setTargetPosition(j2Position1);
        motor2J2.setTargetPosition(j2Position2);
        //        motor1J2.setPower(j2Power);
        //        motor2J2.setPower(j2Power);
        TrapezoidHelper.trapezoidDriveJ2(motor1J2, motor2J2, j2Power);


        //J1 trun first
        motorJ1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ1.setTargetPosition(j1Position);
        //        motorJ1.setPower(j1Power);
        TrapezoidHelper.trapezoidDriveJ1(motorJ1, j1Power);
        System.out.println("toPosition Time: " + runTime.time(TimeUnit.SECONDS));
    }

    public void toPositionWait(boolean turnRobot, double j1Power, double j2Power, double j3Power, int j1Position, int j2Position1, int j2Position2, int j3Position)
    {
        runTime.reset();

        isStop = false;

        motor1J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor1J2.setTargetPosition(j2Position1);
        motor2J2.setTargetPosition(j2Position2);
        //        motor1J2.setPower(j2Power);
        //        motor2J2.setPower(j2Power);
        TrapezoidHelper.trapezoidDriveJ2(motor1J2, motor2J2, j2Power);

        motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ3.setTargetPosition(j3Position);
        //        motorJ3.setPower(j3Power);
        TrapezoidHelper.trapezoidDriveJ3(motorJ3, j3Power);

        while (motor2J2.getCurrentPosition() > SAFE_POS_J2 && motorJ3.getCurrentPosition() < SAFE_POS_J3 && runTime.time(TimeUnit.SECONDS) < 4)
        {
        }

        motorJ1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ1.setTargetPosition(j1Position);
        //        motorJ1.setPower(j1Power);
        TrapezoidHelper.trapezoidDriveJ1(motorJ1, j1Power);

        if (turnRobot)
        {
            robot.makeTurnWithoutWait(-90);
        }


        System.out.println("toPosition Time: " + runTime.time(TimeUnit.SECONDS));
    }

    public void toPositionReverse(double j1Power, double j2Power, double j3Power, int j1Position, int j2Position1, int j2Position2, int j3Position)
    {
        runTime.reset();
        robot.makeTurnWithoutWait(90);
        motorJ1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ1.setTargetPosition(j1Position);
        //        motorJ1.setPower(j1Power);
        TrapezoidHelper.trapezoidDriveJ1(motorJ1, j1Power);
        isStop = false;

        while ((robot.leftDrive.getCurrentPosition() < -450 || robot.rightDrive.getCurrentPosition() > 450) && runTime.time(TimeUnit.SECONDS) < 4)
        {
        }

        System.out.println("toPositionReverse Time1: " + runTime.time(TimeUnit.SECONDS));
        motor1J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor1J2.setTargetPosition(j2Position1);
        motor2J2.setTargetPosition(j2Position2);
        //        motor1J2.setPower(j2Power);
        //        motor2J2.setPower(j2Power);
        TrapezoidHelper.trapezoidDriveJ2(motor1J2, motor2J2, j2Power);

        motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ3.setTargetPosition(j3Position);
        TrapezoidHelper.trapezoidDriveJ3(motorJ3, j3Power);
       /* motorJ3.setPower(j3Power - 0.1);

        if (j3Position - 400 < motorJ3.getCurrentPosition())
        {
            motorJ3.setPower(j3Power);
        }

        if (j3Position - 1000 < motorJ3.getCurrentPosition())
        {
            motorJ3.setPower(j3Power - 0.1);
        }*/

        System.out.println("toPositionReverse Time2: " + runTime.time(TimeUnit.SECONDS));
    }

    public int getRobotTicks()
    {
        return robot.rightDrive.getCurrentPosition();
    }

    public void stopSam()
    {
        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);
        this.stopJ4();
        this.stopCollecting();
    }
}
