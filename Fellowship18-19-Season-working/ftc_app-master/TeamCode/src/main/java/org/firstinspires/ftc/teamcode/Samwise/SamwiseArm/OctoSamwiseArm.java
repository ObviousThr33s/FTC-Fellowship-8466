package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AbstractPhysical.MotorsAndServos;

public class OctoSamwiseArm extends OctoSamwiseCollection
{
    DcMotor motorJ1;
    DcMotor motor1J2;
    DcMotor motor2J2;
    DcMotor motorJ3;

    int E2_MAX_COUNTS = Integer.MAX_VALUE;
    int J4_MAX_COUNTS = Integer.MAX_VALUE;

    static final double BigGearCount = 120.0;
    static final double SmallGearCount = 24.0;
    static final double EncoderCountJ1 = 1680.0;
    static final double MaxOrMinDegrees = 45.0;
    private double J1GearRatio = BigGearCount / SmallGearCount;
    private double TickPerDegreeJ1 = EncoderCountJ1 / 360.0;
    private double BigToSmallRatio = MaxOrMinDegrees * J1GearRatio;
    private double SmallDegreeToTicks = BigToSmallRatio * TickPerDegreeJ1;

    //J2, J3 Static
    static final double EncoderCountJ2 = 1680.0; //number of ticks per motor round
    static final double EncoderCountJ3 = 1680.0; //number of ticks per motor round
    static final double HeightOfPlane = 6.0; //height of plane of motion
    static final double LengthJ2toJ3 = 15.0; //distance between J2 and J3
    static final double LengthJ3toJ4 = 22.0; //distance between J3 and J4

    private double H = HeightOfPlane;
    private double L1 = LengthJ2toJ3; //length between J2 and J3
    private double L2 = LengthJ3toJ4; //length between J3 and J4
    private double TickPerDegreeJ3 = EncoderCountJ3 / 360.0;
    private double TickPerDegreeJ2 = EncoderCountJ2 / 360.0;
    double J2MaxPos = 90.0 * TickPerDegreeJ2;
    double J2MinPos = 0.0 * TickPerDegreeJ2;
    double mininticks = 0.0 * TickPerDegreeJ2;
    double maxinticks = 90.0 * TickPerDegreeJ2;

    double J2Gear1count = 1.0;
    double J2Gear2count = 1.0;
    double J2Gear3count = 1.0;

    double J3Gear1count = 1.0;
    double J3Gear2count = 1.0;

    double J2FirsttoLast = 4;
    double J3FirsttoLast = 2;

    int HoldPosONOFF = 1;

    public int e2Count = 0;

    static final int E1_MAX_COUNT = 4661;

    //    public static final int J1_LEFT_PHONE = -1001;
    //    public static final int J1_RIGHT_PHONE = -2716;
    //    public static final int J2_MIN_PHONE_TICKS = /*916*/ 770;

    // for Octo Arm
    DcMotor motorE1;
    CRServo servoE2;

    ElapsedTime runTime = new ElapsedTime();
    static final int TIMEOUT = 100;

    public static final double MANUAL_POWER_J1 = 0.8;
    public static final double MANUAL_POWER_J2 = 0.4;
    public static final double UP_POWER_J3 = 0.4;
    public static final double E1_POWER = 0.8;
    public static final double E2_POWER = 0.8;


    public OctoSamwiseArm(HardwareMap hwm)
    {
        super(hwm);

        //Instantiate hardware
        motorJ1 = hwm.dcMotor.get("J1");
        motor1J2 = hwm.dcMotor.get("1J2");
        motor2J2 = hwm.dcMotor.get("2J2");
        motorJ3 = hwm.dcMotor.get("J3");
        motorE1 = hwm.dcMotor.get("E1");
        servoE2 = hwm.crservo.get("E2");

        motorJ1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorJ1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor1J2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor1J2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor2J2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor2J2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorJ3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorJ3.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorE1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void driveJ1(double power)
    {
        motorJ1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motorJ1.setPower(MANUAL_POWER_J1 * power);

    }

    public void stopJ1()
    {
        motorJ1.setPower(0);
    }

    public void stopJ3()
    {
        motorJ3.setPower(0);
    }

    public void stopJ2()
    {
        motor1J2.setPower(0);
        motor2J2.setPower(0);
    }

    /**
     * When mapped to DPad
     *
     * @param
     */
    public void driveJ2(double power)
    {
        motor1J2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor2J2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        motor1J2.setPower(-MANUAL_POWER_J2 * power);
        motor2J2.setPower(-MANUAL_POWER_J2 * power);
    }


    /**
     * When mapped to DPad
     */
    public void driveJ3(double power)
    {
        motorJ3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motorJ3.setPower(UP_POWER_J3 * power);
    }


    public int getJ1CurrentPosition()
    {
        return motorJ1.getCurrentPosition();
    }

    public int getJ2CurrentPosition()
    {
        return motor1J2.getCurrentPosition();
    }

    public int getJ3CurrentPosition()
    {
        return motorJ3.getCurrentPosition();
    }

    //    public boolean isPhoneJ1()
    //    {
    //        boolean isJ1PhoneArea = getJ1CurrentPosition() < J1_LEFT_PHONE && getJ1CurrentPosition() > J1_RIGHT_PHONE;
    //        return isJ1PhoneArea;
    //    }
    //
    //    public boolean isPhoneJ2()
    //    {
    //        boolean isJ2PhoneArea = getJ2CurrentPosition() >= J2_MIN_PHONE_TICKS;
    //        return isJ2PhoneArea;
    //    }

    /************************************************************************************************
     *               For OCTO Arm Only: Arm Extension/Retraction                                    *
     ************************************************************************************************/
    public void extendArms()
    {
        /*runTime.reset();
        while (runTime.milliseconds() < TIMEOUT)
        {
            motorE1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorE1.setDirection(DcMotorSimple.Direction.FORWARD);
            servoE2.setDirection(DcMotorSimple.Direction.FORWARD);
            motorE1.setPower(0.2);
            servoE2.setPower(0.8);
        }
        stopExtendServos();*/
    }

    public void stopExtendServos()
    {
        //        motorE1.setPower(0);
        //        motorE2.setPower(0);
    }

    public void retractL1Auto()
    {
        motorE1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorE1.setDirection(DcMotorSimple.Direction.FORWARD);
        motorE1.setPower(0.2);
        motorE1.setTargetPosition(0);
    }

    public void extendL2Auto()
    {
        servoE2.setDirection(DcMotorSimple.Direction.FORWARD);
        servoE2.setPower(E2_POWER);
        try
        {
            Thread.sleep(25000);
        }
        catch (Exception e) {}
    }

    public void stopExtendL1()
    {
        motorE1.setPower(0);
    }

    public void stopExtendL2()
    {
        servoE2.setPower(0);
    }

    public void extendL1Auto()
    {
        motorE1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorE1.setDirection(DcMotorSimple.Direction.REVERSE);
        motorE1.setPower(0.8);
        motorE1.setTargetPosition(E1_MAX_COUNT);
    }

    public void retractL2Auto()
    {
        servoE2.setDirection(DcMotorSimple.Direction.REVERSE);
        servoE2.setPower(E2_POWER);
        try
        {
            Thread.sleep(25000);
        }
        catch (Exception e) {}
    }

    public void extendL1()
    {
        motorE1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorE1.setDirection(DcMotorSimple.Direction.REVERSE);
        motorE1.setPower(0.8);
    }

    public void retractL2()
    {
        servoE2.setDirection(DcMotorSimple.Direction.REVERSE);
        servoE2.setPower(E2_POWER);
        e2Count--;
    }

    public void retractL1()
    {
        motorE1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorE1.setDirection(DcMotorSimple.Direction.FORWARD);
        motorE1.setPower(0.2);
    }

    public void extendL2()
    {
        servoE2.setDirection(DcMotorSimple.Direction.FORWARD);
        servoE2.setPower(E2_POWER);
        e2Count++;
    }

    public int getE1CurrentPosition()
    {
        return motorE1.getCurrentPosition();
    }

    public void PlaneOfMotion(float Joysticks)
    {
        motor1J2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor2J2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorJ3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        double J2initialposition = motor1J2.getCurrentPosition();
        double J3initialposition = motorJ3.getCurrentPosition();

        double J3TargetPos_Ticks;
        HoldPosONOFF = 1;

        while (motor2J2.isBusy() || motor1J2.isBusy() || motorJ3.isBusy())
        {

            // ;

        }
        ;
        J2initialposition = motor1J2.getCurrentPosition();
        J3initialposition = motorJ3.getCurrentPosition();
        if (Math.abs(Joysticks) > 0.1)
        {

            if (Joysticks <= -0.05)
            { //push forward
                J2MaxPos = J2initialposition + EncoderCountJ2 / 50.0;
                motor1J2.setTargetPosition((int) (J2MaxPos * J2FirsttoLast));
                motor2J2.setTargetPosition((int) (J2MaxPos * J2FirsttoLast));

                motor1J2.setPower(0.5);
                motor2J2.setPower(0.5);
                motor1J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motor2J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                J3TargetPos_Ticks = J3initialposition + (-1.47 * (EncoderCountJ2 / 50.0));
                motorJ3.setTargetPosition((int) (J3TargetPos_Ticks * J3FirsttoLast));
                motorJ3.setPower(0.5); //test Power LAtEr yeet
                motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            }
            else if (Joysticks >= 0.05)
            { //backwards
                J2MaxPos = J2initialposition - EncoderCountJ2 / 50.0;
                motor1J2.setTargetPosition((int) (J2MaxPos * J2FirsttoLast));
                motor2J2.setTargetPosition((int) (J2MaxPos * J2FirsttoLast));
                motor1J2.setPower(0.5);
                motor2J2.setPower(0.5);
                motor1J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motor2J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                J3TargetPos_Ticks = J3initialposition - (-1.47 * (EncoderCountJ2 / 50.0));
                motorJ3.setTargetPosition((int) (J3TargetPos_Ticks * J3FirsttoLast));
                motorJ3.setPower(0.5); //test Power LAtEr yeet
                motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
        }
        else
        {

            stopJ2();
            stopJ3();

               /* if (HoldPosONOFF == 1) {
                    ArmMotor2.setTargetPosition(ArmMotor2.getCurrentPosition());
                    ArmMotor2_2.setTargetPosition(ArmMotor2.getCurrentPosition());
                    ArmMotor3.setTargetPosition(ArmMotor3.getCurrentPosition());
                    ArmMotor2.setPower(1);
                    ArmMotor2_2.setPower(1);
                    ArmMotor3.setPower(1);
                    ArmMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    ArmMotor2_2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    ArmMotor3.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                }*/

        }

    }


}