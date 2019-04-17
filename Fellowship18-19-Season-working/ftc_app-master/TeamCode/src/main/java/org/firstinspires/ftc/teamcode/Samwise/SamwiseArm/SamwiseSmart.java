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

    static final int ROBOT_90_TURN_TICKS = 2265;

    /****************  todo: fine tune numbers start ********************/
    // press b to go from initial to collection numbers: wait for J1 turn before J2, J3
    static final double INITIAL_TO_COLLECTION_WAIT_PERCENTAGE_J1 = 0.7;
    // press x/y to go lander numbers: wait for J2, J3 lift up certain percentage before robot, J1 90|90 turn.
    static final double TO_LANDER_WAIT_PERCENTAGE_J2 = 0.4;
    static final double TO_LANDER_WAIT_PERCENTAGE_J3 = 0.5;
    // press b to go from gold|silver to collection numbers: wait for J2, J3 cobra before robot, J1 90|90 turn
    static final double LANDER_TO_COLLECTION_WAIT_PERCENTAGE_ROBOT_TURN = 0.6;
    static final double GOLD_LANDER_TO_COLLECTION_WAIT_PERCENTAGE_J1 = 0.8;
    static final double SILVER_LANDER_TO_COLLECTION_WAIT_PERCENTAGE_J1 = 0.8;
    /**************** todo: fine tune numbers end ********************/

    int collectionPosJ1 = 2150;
    int collectionPosJ2 = 1320;
    int collectionPosJ3 = 1330;

    int J1_LANDER_GOLD = -100;
    int J2_LANDER_GOLD = 100;
    int J3_LANDER_GOLD = 1800;

    int J1_LANDER_SILVER = 114;
    int J2_LANDER_SILVER = 100;
    int J3_LANDER_SILVER = 1700;

    static final double J1_POWER = 0.4;
    static final double J2_POWER = 0.4;
    static final double J3_POWER = 0.4;

    private boolean inGoldDeposit = false;
    private boolean inSilverDeposit = false;
    private boolean inCollectionPlane = false;

    private static final int SAFE_POS_J1 = -200;
    private static final int SAFE_POS_J2 = 500;
    private static final int SAFE_POS_J3 = 1500;

    private ElapsedTime runTime = new ElapsedTime();

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
        toPosition(0, 0, 0);
        this.inGoldDeposit = false;
        this.inSilverDeposit = false;
        this.inCollectionPlane = false;
    }

    public void toCollectionPlane(boolean wait, boolean trapezoidRobotTurn)
    {
        if (this.inGoldDeposit || this.inSilverDeposit)
        {
            this.backFromLander(wait, trapezoidRobotTurn);
        }
        else
        {
            if (!this.inCollectionPlane)
            {
                if (wait)
                {
                    System.out.println("------------------- to collection wait ------------");
                    this.toCollectionWaitSequence();
                }
                else
                {
                    System.out.println("------------------- to collection ------------");
                    this.toCollection();
                }
                this.inCollectionPlane = true;
                this.inSilverDeposit = false;
                this.inGoldDeposit = false;
            }
        }
    }

    public void toLanderGold(boolean wait, boolean trapezoidRobotTurn)
    {
        if (!this.inGoldDeposit)
        {
            if (this.inSilverDeposit)
            {
                System.out.println("------------------- from silver to gold both ------------");
                this.toPosition(J1_LANDER_GOLD, J2_LANDER_GOLD, J3_LANDER_GOLD);
                this.inGoldDeposit = true;
                this.inCollectionPlane = false;
                this.inSilverDeposit = false;
            }
            else
            {
                this.saveCollectionPosition();
                if (wait)
                {
                    System.out.println("------------------- from non-silver to gold wait------------");
                    this.toGoldOrSilverWaitSequence(trapezoidRobotTurn, J1_LANDER_GOLD, J2_LANDER_GOLD, J3_LANDER_GOLD);
                }
                else
                {
                    System.out.println("------------------- from non-silver to gold ------------");
                    this.toLander(trapezoidRobotTurn, J1_LANDER_GOLD, J2_LANDER_GOLD, J3_LANDER_GOLD);
                }
                this.inGoldDeposit = true;
                this.inCollectionPlane = false;
                this.inSilverDeposit = false;
            }
        }
    }

    public void toLanderSilver(boolean wait, boolean trapezoidRobotTurn)
    {
        if (!this.inSilverDeposit)
        {
            if (this.inGoldDeposit)
            {
                System.out.println("------------------- from gold to silver both ------------");
                this.toPosition(J1_LANDER_SILVER, J2_LANDER_SILVER, J3_LANDER_SILVER);
                this.inSilverDeposit = true;
                this.inCollectionPlane = false;
                this.inGoldDeposit = false;
            }
            else
            {
                this.saveCollectionPosition();
                if (wait)
                {
                    System.out.println("------------------- from non-gold to silver wait ------------");
                    this.toGoldOrSilverWaitSequence(trapezoidRobotTurn, J1_LANDER_SILVER, J2_LANDER_SILVER, J3_LANDER_SILVER);
                }
                else
                {
                    System.out.println("------------------- from non-gold to silver ------------");
                    this.toLander(trapezoidRobotTurn, J1_LANDER_SILVER, J2_LANDER_SILVER, J3_LANDER_SILVER);
                }
                this.inSilverDeposit = true;
                this.inCollectionPlane = false;
                this.inGoldDeposit = false;
            }
        }
    }

    private void backFromLander(boolean wait, boolean trapezoidRobotTurn)
    {
        this.saveLanderPosition();
        if (wait)
        {
            System.out.println("------------------- lander to collection wait------------");
            this.landerToCollectionWaitSequence(trapezoidRobotTurn);
        }
        else
        {
            System.out.println("------------------- lander to collection ------------");
            this.landerToCollection(trapezoidRobotTurn);
        }
        this.inSilverDeposit = false;
        this.inGoldDeposit = false;
        this.inCollectionPlane = true;
    }

    private void saveLanderPosition()
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

    private void saveCollectionPosition()
    {
        collectionPosJ1 = motorJ1.getCurrentPosition();
        collectionPosJ2 = motor2J2.getCurrentPosition();
        collectionPosJ3 = motorJ3.getCurrentPosition();
    }

    public int getRobotLeftTicks()
    {
        return robot.leftDrive.getCurrentPosition();
    }

    public int getRobotRightTicks()
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

    /**
     * From anywhere to position (0, 0, 0)
     * and switch between GOLD and SILVER drop
     */
    private void toPosition(int j1Position, int j2Position, int j3Position)
    {
        isStop = false;

        motorJ1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ1.setTargetPosition(j1Position);
        TrapezoidHelper.trapezoidDriveJ1(motorJ1, J1_POWER);

        motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ3.setTargetPosition(j3Position);
        TrapezoidHelper.trapezoidDriveJ3(motorJ3, J2_POWER);

        motor1J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor1J2.setTargetPosition(j2Position);
        motor2J2.setTargetPosition(j2Position);
        TrapezoidHelper.trapezoidDriveJ2(motor1J2, motor2J2, J3_POWER);
    }

    /***************************** 90/90 Original **************************************/

    /**
     * From position (0, 0, 0) --> (2150, 1320, 1330) collection position
     */
    private void toCollection()
    {
        isStop = false;

        // J3
        motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ3.setTargetPosition(collectionPosJ3);
        TrapezoidHelper.trapezoidDriveJ3(motorJ3, J3_POWER);

        //J2
        motor1J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor1J2.setTargetPosition(collectionPosJ2);
        motor2J2.setTargetPosition(collectionPosJ2);
        TrapezoidHelper.trapezoidDriveJ2(motor1J2, motor2J2, J2_POWER);

        //J1
        motorJ1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ1.setTargetPosition(collectionPosJ1);
        TrapezoidHelper.trapezoidDriveJ1(motorJ1, J1_POWER);
    }

    /**
     * 1. From collection position      (2150, 1320, 1330) --> (-236, 100, 2000) gold position
     * 2. From collection position      (2150, 1320, 1330) --> (114, 100, 1700) silver position
     */
    private void toLander(boolean trapezoidRobotTurn, int j1Position, int j2Position, int j3Position)
    {
        runTime.reset();

        isStop = false;

        motor1J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor1J2.setTargetPosition(j2Position);
        motor2J2.setTargetPosition(j2Position);
        TrapezoidHelper.trapezoidDriveJ2(motor1J2, motor2J2, J2_POWER);

        motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ3.setTargetPosition(j3Position);
        TrapezoidHelper.trapezoidDriveJ3(motorJ3, J3_POWER);

        while (motor2J2.getCurrentPosition() > SAFE_POS_J2 || motorJ3.getCurrentPosition() < SAFE_POS_J3 && runTime.time(TimeUnit.SECONDS) < 4)
        {
        }

        motorJ1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ1.setTargetPosition(j1Position);
        TrapezoidHelper.trapezoidDriveJ1(motorJ1, J1_POWER);

        robot.makeTurnWithoutWait(trapezoidRobotTurn, -90);
    }

    /**
     * From gold position       (-236, 100, 1800)
     * or silver position       (114, 100, 1700)
     * to collection position   (2150, 1320, 1330)
     */
    private void landerToCollection(boolean trapezoidRobotTurn)
    {
        runTime.reset();
        robot.makeTurnWithoutWait(trapezoidRobotTurn, 90);
        motorJ1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ1.setTargetPosition(collectionPosJ1);
        TrapezoidHelper.trapezoidDriveJ1(motorJ1, J1_POWER);
        isStop = false;

        while ((Math.abs(robot.leftDrive.getCurrentPosition()) < 1800 || (collectionPosJ1 - motorJ1.getCurrentPosition()) > 300) && runTime.time(TimeUnit.SECONDS) < 4)
        {
        }

        motor1J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor1J2.setTargetPosition(collectionPosJ2);
        motor2J2.setTargetPosition(collectionPosJ2);
        TrapezoidHelper.trapezoidDriveJ2(motor1J2, motor2J2, J2_POWER);

        motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ3.setTargetPosition(collectionPosJ3);
        TrapezoidHelper.trapezoidDriveJ3(motorJ3, J3_POWER);
    }

    /***************************** 90/90 Sequence **************************************/

    /**
     * From position (0, 0, 0) --> (2150, 1320, 1330) collection position
     */
    private void toCollectionWaitSequence()
    {
        isStop = false;

        runTime.reset();
        //J1 turn first
        motorJ1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ1.setTargetPosition(collectionPosJ1);
        TrapezoidHelper.trapezoidDriveJ1(motorJ1, J1_POWER);
        int diff_wait_ticks = (int) (collectionPosJ1 * (1.0 - INITIAL_TO_COLLECTION_WAIT_PERCENTAGE_J1));
        while (Math.abs(motorJ1.getCurrentPosition() - collectionPosJ1) > diff_wait_ticks && runTime.time(TimeUnit.SECONDS) < 4)
        {
            //wait for J1
        }
        System.out.println("To collection - wait for J1 turn time: " + runTime.time(TimeUnit.SECONDS) + " seconds");

        // J3
        motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ3.setTargetPosition(collectionPosJ3);
        TrapezoidHelper.trapezoidDriveJ3(motorJ3, J3_POWER);

        //J2
        motor1J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor1J2.setTargetPosition(collectionPosJ2);
        motor2J2.setTargetPosition(collectionPosJ2);
        TrapezoidHelper.trapezoidDriveJ2(motor1J2, motor2J2, J2_POWER);


    }

    /**
     * 1. From collection position      (2150, 1320, 1330) --> (-236, 100, 1800) gold position
     * 2. From collection position      (2150, 1320, 1330) --> (114, 100, 1700) silver position
     */
    private void toGoldOrSilverWaitSequence(boolean trapezoidRobotTurn, int j1Position, int j2Position, int j3Position)
    {
        runTime.reset();

        isStop = false;

        // Step 1: J2, J3 lift up
        motor1J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor1J2.setTargetPosition(j2Position);
        motor2J2.setTargetPosition(j2Position);
        TrapezoidHelper.trapezoidDriveJ2(motor1J2, motor2J2, J2_POWER);

        motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ3.setTargetPosition(j3Position);
        TrapezoidHelper.trapezoidDriveJ3(motorJ3, J3_POWER);

        int diff_wait_ticks_J2 = (int) ((j2Position - collectionPosJ2) * (1.0 - TO_LANDER_WAIT_PERCENTAGE_J2));
        int diff_wait_ticks_J3 = (int) ((j3Position - collectionPosJ3) * (1.0 - TO_LANDER_WAIT_PERCENTAGE_J3));

        while ((Math.abs(j2Position - motor2J2.getCurrentPosition()) > Math.abs(diff_wait_ticks_J2) || Math.abs(j3Position - motorJ3.getCurrentPosition()) > Math.abs(diff_wait_ticks_J3)) && runTime.time(TimeUnit.SECONDS) < 4)
        {
            // wait
        }
        System.out.println("To gold|silver - wait for J2, J3 lift time: " + runTime.time(TimeUnit.SECONDS) + " seconds");

        // Step 2: J1, robot turn 90/90
        motorJ1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ1.setTargetPosition(j1Position);
        TrapezoidHelper.trapezoidDriveJ1(motorJ1, J1_POWER);
        //        motorJ1.setPower(J1_POWER);

        robot.makeTurnWithoutWait(trapezoidRobotTurn, -90);
    }

    /**
     * From gold position       (-236, 100, 1800)
     * or silver position       (114, 100, 1700)
     * to collection position   (2150, 1320, 1330)
     */
    private void landerToCollectionWaitSequence(boolean trapezoidRobotTurn)
    {
        isStop = false;

        runTime.reset();

        // cobra
        motor1J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor1J2.setTargetPosition(0);
        motor2J2.setTargetPosition(0);
        TrapezoidHelper.trapezoidDriveJ2(motor1J2, motor2J2, J2_POWER);

        // 90/90 turn
        motorJ1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ1.setTargetPosition(collectionPosJ1);
        TrapezoidHelper.trapezoidDriveJ1(motorJ1, J1_POWER);
        robot.makeTurnWithoutWait(trapezoidRobotTurn, 90);

        // wait for 90|90 turn
        int wait_ticks_robot = (int) (ROBOT_90_TURN_TICKS * LANDER_TO_COLLECTION_WAIT_PERCENTAGE_ROBOT_TURN);
        int wait_ticks_J1    = 0;
        if (this.inGoldDeposit)
        {
            wait_ticks_J1 = (int) ((J1_LANDER_GOLD - collectionPosJ1) * (1.0 - GOLD_LANDER_TO_COLLECTION_WAIT_PERCENTAGE_J1));
        }
        else
        {
            wait_ticks_J1 = (int) ((J1_LANDER_SILVER - collectionPosJ1) * (1.0 - SILVER_LANDER_TO_COLLECTION_WAIT_PERCENTAGE_J1));
        }
        boolean print = false;
        while ((Math.abs(robot.leftDrive.getCurrentPosition()) < wait_ticks_robot || Math.abs(collectionPosJ1 - motorJ1.getCurrentPosition()) > Math.abs(wait_ticks_J1)) && runTime.time(TimeUnit.SECONDS) < 4)
        {
            // wait for turn
            if (Math.abs(robot.leftDrive.getCurrentPosition()) < wait_ticks_robot && Math.abs(robot.rightDrive.getCurrentPosition()) < wait_ticks_robot)
            {
                if (!print)
                {
                    System.out.println("Lander to collection, Robot turn finished faster. Time: " + runTime.time(TimeUnit.SECONDS) + " seconds");
                    print = true;
                }
            }

            if (Math.abs(collectionPosJ1 - motorJ1.getCurrentPosition()) <= wait_ticks_J1)
            {
                if (!print)
                {
                    System.out.println("Lander to collection, J1 turn finished faster. Time: " + runTime.time(TimeUnit.SECONDS) + " seconds");
                    print = true;
                }
            }
        }
        System.out.println("Lander to collection, J1 turn and robot turn finished time: " + runTime.time(TimeUnit.SECONDS) + " seconds");

        motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ3.setTargetPosition(collectionPosJ3);
        TrapezoidHelper.trapezoidDriveJ3(motorJ3, J3_POWER);

        motor1J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor1J2.setTargetPosition(collectionPosJ2);
        motor2J2.setTargetPosition(collectionPosJ2);
        TrapezoidHelper.trapezoidDriveJ2(motor1J2, motor2J2, J2_POWER);
    }
}
