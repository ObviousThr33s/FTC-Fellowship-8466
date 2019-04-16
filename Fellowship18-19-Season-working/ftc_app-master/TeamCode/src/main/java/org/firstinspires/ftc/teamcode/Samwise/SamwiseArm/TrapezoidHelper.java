package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.TimeUnit;

public class TrapezoidHelper
{
    static ElapsedTime runtime = new ElapsedTime();
    private static final double POWER_STEP_LENGTH_DRIVETRAIN = 0.1;
    private static final double POWER_STEP_LENGTH_J1 = 0.1;
    private static final double POWER_STEP_LENGTH_J2 = 0.1;
    private static final double POWER_STEP_LENGTH_J3 = 0.1;
    private static final double ZERO_POWER = 0.0001;

    private static final int ZERO_POSITION_OFF_SET = 100;

    public static void trapezoidStopeJ1(DcMotor motor)
    {
        trapezoidDriveJ1(motor, 0);
        motor.setPower(0);
        //        System.out.println(" to stop.");
    }

    public static void trapezoidDriveJ1(DcMotor motor, double targetPower)
    {
        trapezoidDriveMotor(motor, targetPower, POWER_STEP_LENGTH_J1);
    }

    public static void trapezoidStopJ3(DcMotor motor)
    {
        trapezoidDriveJ3(motor, 0);
        motor.setPower(0);
//        System.out.println(" to stop.");
    }

    public static void trapezoidDriveJ3(DcMotor motor, double targetPower)
    {
        trapezoidDriveMotor(motor, targetPower, POWER_STEP_LENGTH_J3);
    }

    public static void trapezoidStopJ2(DcMotor motor1, DcMotor motor2)
    {
        trapezoidDriveJ2(motor1, motor2, 0);
        motor1.setPower(0);
        motor2.setPower(0);
//        System.out.println(" to stop.");
    }

    public static void trapezoidDriveJ2(DcMotor motor1, DcMotor motor2, double targetPower)
    {
        runtime.reset();
        double currentPower = (motor1.getPower() + motor2.getPower()) / 2;
//        System.out.println("Current power: " + currentPower);
//        System.out.println("Target power: " + targetPower);
        while (Math.abs(currentPower - targetPower) > POWER_STEP_LENGTH_J2)
        {
            if (currentPower > targetPower)
            {
                motor1.setPower(currentPower - POWER_STEP_LENGTH_J2);
                motor2.setPower(currentPower - POWER_STEP_LENGTH_J2);
            }
            else
            {
                motor1.setPower(currentPower + POWER_STEP_LENGTH_J2);
                motor2.setPower(currentPower + POWER_STEP_LENGTH_J2);
            }
            currentPower = (motor1.getPower() + motor2.getPower()) / 2;
            //            System.out.println("New power: "+currentPower);
        }

        if (Math.abs(currentPower - targetPower) > ZERO_POWER)
        {
            motor1.setPower(targetPower);
            motor2.setPower(targetPower);
        }
//        System.out.print("trapezoidDriveJ2 takes " + runtime.time(TimeUnit.MILLISECONDS) + " milliseconds");
    }

    public static void trapezoidStopTrain(DcMotor leftDrive, DcMotor rightDrive)
    {
        trapezoidDriveTrain(leftDrive, 0, rightDrive, 0);
        leftDrive.setPower(0);
        rightDrive.setPower(0);
    }

    public static void trapezoidDriveTrain(DcMotor leftDrive, double leftTargetPower, DcMotor rightDrive, double rightTargetPower)
    {
        runtime.reset();
        double currentLeftPower  = leftDrive.getPower();
        double currentRightPower = rightDrive.getPower();
        while (Math.abs(currentLeftPower - leftTargetPower) > POWER_STEP_LENGTH_DRIVETRAIN || Math.abs(currentRightPower - rightTargetPower) > POWER_STEP_LENGTH_DRIVETRAIN)
        {
            if (currentLeftPower > leftTargetPower)
            {
                leftDrive.setPower(currentLeftPower - POWER_STEP_LENGTH_DRIVETRAIN);
            }
            else
            {
                leftDrive.setPower(currentLeftPower + POWER_STEP_LENGTH_DRIVETRAIN);
            }
            if (currentRightPower > rightTargetPower)
            {
                rightDrive.setPower(currentRightPower - POWER_STEP_LENGTH_DRIVETRAIN);
            }
            else
            {
                rightDrive.setPower(currentRightPower + POWER_STEP_LENGTH_DRIVETRAIN);
            }
            currentLeftPower = leftDrive.getPower();
            currentRightPower = rightDrive.getPower();
        }

        if (Math.abs(currentRightPower - rightTargetPower) > ZERO_POWER)
        {
            rightDrive.setPower(rightTargetPower);
        }
        if (Math.abs(currentLeftPower - leftTargetPower) > ZERO_POWER)
        {
            leftDrive.setPower(leftTargetPower);
        }
        //        System.out.print("It takes " + runtime.time(TimeUnit.MILLISECONDS) + " milliseconds");
    }

    private static void trapezoidDriveMotor(DcMotor motor, double targetPower, double powerStepLength)
    {
        runtime.reset();
        double currentPower = motor.getPower();
        powerStepLength = Math.abs(powerStepLength);
//        System.out.println("Current power: " + currentPower);
//        System.out.println("Target power: " + targetPower);


        while (Math.abs(currentPower - targetPower) > powerStepLength)
        {
            if (currentPower > targetPower) motor.setPower(currentPower - powerStepLength);
            else motor.setPower(currentPower + powerStepLength);
            currentPower = motor.getPower();
            //            System.out.println("New power: "+currentPower);
        }

        motor.setPower(targetPower);
//        System.out.print("trapezoidDriveMotor takes " + runtime.time(TimeUnit.MILLISECONDS) + " milliseconds");
    }

    private static void trapezoidSetPowerWith(DcMotor motor, double targetPower, double powerStepLength)
    {
        runtime.reset();
        double currentPower = motor.getPower();
        powerStepLength = Math.abs(powerStepLength);
        while (Math.abs(currentPower - targetPower) > powerStepLength)
        {
            if (currentPower > targetPower) motor.setPower(currentPower - powerStepLength);
            else motor.setPower(currentPower + powerStepLength);
            currentPower = motor.getPower();
        }

        motor.setPower(targetPower);
        //        System.out.print("It takes " + runtime.time(TimeUnit.MILLISECONDS) + " milliseconds");
    }

    public static void trapezoidStopJ2WithTarget(DcMotor motor1, DcMotor motor2)
    {
        double currentPosition = (motor1.getCurrentPosition() + motor2.getCurrentPosition()) / 2;
        double targetPosition  = (motor1.getTargetPosition() + motor2.getTargetPosition()) / 2;
        double positionOffSet  = Math.abs(targetPosition - currentPosition);
        double power           = (motor1.getPower() + motor2.getPower()) / 2;
        double diffPosition    = Math.abs(motor1.getTargetPosition() - motor1.getCurrentPosition());
        while (Math.abs(diffPosition) <= ZERO_POSITION_OFF_SET)
        {
            motor1.setPower((diffPosition / positionOffSet) * power);
            motor2.setPower((diffPosition / positionOffSet) * power);
            diffPosition = Math.abs(motor1.getTargetPosition() - motor1.getCurrentPosition());
        }
        trapezoidStopJ2(motor1, motor2);
    }

    public static void trapezoidStopJ3WithTarget(DcMotor motor)
    {
        double currentPosition = motor.getCurrentPosition();
        double targetPosition  = motor.getTargetPosition();
        double positionOffSet  = Math.abs(targetPosition - currentPosition);
        double power           = motor.getPower();
        double diffPosition    = Math.abs(motor.getTargetPosition() - motor.getCurrentPosition());
        while (Math.abs(positionOffSet) <= ZERO_POSITION_OFF_SET)
        {
            motor.setPower((diffPosition / positionOffSet) * power);
            diffPosition = Math.abs(motor.getTargetPosition() - motor.getCurrentPosition());
        }
        trapezoidStopJ3(motor);
    }
}
