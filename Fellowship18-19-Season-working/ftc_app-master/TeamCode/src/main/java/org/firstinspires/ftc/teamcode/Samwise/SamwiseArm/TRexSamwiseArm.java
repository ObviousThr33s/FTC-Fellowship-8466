package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class TRexSamwiseArm extends TRexSamwiseCollection
{
    DcMotor motorJ1;
    DcMotor motorJ2;
    DcMotor motorJ3;

    static final double BigGearCount = 120.0;
    static final double SmallGearCount = 24.0;
    static final double EncoderCountJ1 = 1680.0;
    static final double MaxOrMinDegrees = 45.0;
    private double J1GearRatio = BigGearCount/SmallGearCount;
    private double TickPerDegreeJ1 = EncoderCountJ1/360.0;
    private double BigToSmallRatio = MaxOrMinDegrees * J1GearRatio;
    private double SmallDegreeToTicks = BigToSmallRatio * TickPerDegreeJ1;

    //J2, J3 Static
    static final double EncoderCountJ2 = 1680.0; //number of ticks per motor round
    static final double EncoderCountJ3 = 1680.0; //number of ticks per motor round
    static final double HeightOfPlane = 6.0; //height of plane of motion
    static final double LengthJ2toJ3 = 24.09; //distance between J2 and J3
    static final double LengthJ3toJ4 = 27.75; //distance between J3 and J4

    private double H = HeightOfPlane;
    private double L1 = LengthJ2toJ3; //length between J2 and J3
    private double L2 = LengthJ3toJ4; //length between J3 and J4
    private double TickPerDegreeJ3 = EncoderCountJ3/360.0;
    private double TickPerDegreeJ2 = EncoderCountJ2/360.0;
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

    public static final int J1_LEFT_PHONE = -1001;
    public static final int J1_RIGHT_PHONE = -2716;
    public static final int J2_MIN_PHONE_TICKS = /*916*/ 770;

    // for Octo Arm
    DcMotor motorE1;
    CRServo servoE2;

    ElapsedTime runTime = new ElapsedTime();
    static final int TIMEOUT = 100;

    static final double MANUAL_POWER_J1 = 0.3;
    static final double MANUAL_POWER_J2 = 0.1;
    static final double UP_POWER_J3 = 0.15;
    static final double DOWN_POWER_J3 = 0.05;

    public TRexSamwiseArm(HardwareMap hwm)
    {
        super(hwm);

        //Instantiate hardware
        motorJ1 = hwm.dcMotor.get("J1");
        motorJ2 = hwm.dcMotor.get("J2");
        motorJ3 = hwm.dcMotor.get("J3");
        servoE2 = hwm.crservo.get("E2");

        motorJ1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorJ1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorJ2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorJ2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorJ3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorJ3.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void driveJ1(boolean isLeft)
    {
        motorJ1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        if (isLeft)
        {
            motorJ1.setPower(MANUAL_POWER_J1);
        }
        else
        {
            motorJ1.setPower(-MANUAL_POWER_J1);
        }
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
        motorJ2.setPower(0);
    }

    /**
     * When mapped to DPad
     *
     * @param isUp
     */
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

    /**
     * When mapped to DPad
     *
     * @param isUp
     */
    public void driveJ3(boolean isUp)
    {
        motorJ3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        if (isUp)
        {
            motorJ3.setPower(-UP_POWER_J3);
        }
        else
        {
            motorJ3.setPower(DOWN_POWER_J3);
        }
    }


    public int getJ1CurrentPosition()
    {
        return motorJ1.getCurrentPosition();
    }

    public int getJ2CurrentPosition()
    {
        return motorJ2.getCurrentPosition();
    }

    public int getJ3CurrentPosition()
    {
        return motorJ3.getCurrentPosition();
    }

    public boolean isPhoneJ1()
    {
        boolean isJ1PhoneArea = getJ1CurrentPosition() < J1_LEFT_PHONE && getJ1CurrentPosition() > J1_RIGHT_PHONE;
        return isJ1PhoneArea;
    }

    public boolean isPhoneJ2()
    {
        boolean isJ2PhoneArea = getJ2CurrentPosition() >= J2_MIN_PHONE_TICKS;
        return isJ2PhoneArea;
    }

    /************************************************************************************************
     *               For OCTO Arm Only: Arm Extension/Retraction                                    *
     ************************************************************************************************/
    public void extendArms()
    {
        //TODO: may need to add back when OCTO is in use
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
        //TODO: may need to add back when OCTO is in use
        /*motorE1.setPower(0);
        servoE2.setPower(0);*/
    }

    public void extendL1()
    {
        //TODO: may need to add back when OCTO is in use
        /*motorE1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorE1.setDirection(DcMotorSimple.Direction.REVERSE);
        motorE1.setPower(0.15);*/
    }

    public void extendL2()
    {
        //TODO: may need to add back when OCTO is in use
        servoE2.setDirection(DcMotorSimple.Direction.FORWARD);
        servoE2.setPower(0.8);
    }

    public void stopExtendL1()
    {
        //TODO: may need to add back when OCTO is in use
        /*motorE1.setPower(0);*/
    }

    public void stopExtendL2()
    {
        //TODO: may need to add back when OCTO is in use
        servoE2.setPower(0);
    }

    public void retractArm()
    {
        //TODO: may need to add back when OCTO is in use
        /*motorE1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorE1.setDirection(DcMotorSimple.Direction.REVERSE);*/
        servoE2.setDirection(DcMotorSimple.Direction.REVERSE);
        //        motorE1.setPower(0.2);
        servoE2.setPower(0.8);
    }

    public int getE1CurrentPosition()
    {
        //TODO: may need to add back when OCTO is in use
        /*return motorE1.getCurrentPosition();*/
        return 0;
    }

    public void PlaneOfMotion(float Joysticks) {
        if (Math.abs(Joysticks)>0.1) {
//        init_loop();
            if (Joysticks <= -0.01) { //push forward
                motorJ2.setTargetPosition((int) (J2MaxPos * J2FirsttoLast));
            }
            if (Joysticks >= 0.01) { //backwards
                motorJ2.setTargetPosition((int) (J2MinPos * J2FirsttoLast));
            }
            motorJ2.setPower(Joysticks / 1.7);
        /*double J2CurrentPos_Ticks = motor1J2.getCurrentPosition();
        if (J2CurrentPos_Ticks <= mininticks) {
            if (Joysticks.left_stick_y >= 0.02) {
                motor1J2.setPower(0);
            }
        }
        if (J2CurrentPos_Ticks >= 180) {
            if (Joysticks.left_stick_y <= -0.02) {
                motor1J2.setPower(0);
            }
        }*/
            motorJ2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            if (Joysticks == 0) {
                motorJ2.setPower(0);
            }
            double J2CurrentPos_deg;

            double J3TargetPos_deg;
            double J3TargetPos_Ticks;
            double a, b, c, c_1, d;
            J2CurrentPos_deg = (motorJ2.getCurrentPosition() / TickPerDegreeJ2);
            a = Math.cos(Math.toRadians(180.0 - J2CurrentPos_deg));
            b = (L1 * a - H) / L2;
            c = Math.acos(b);
            c_1 = Math.toDegrees(c);
            d = c_1 + 180.0 - J2CurrentPos_deg;
            J3TargetPos_deg = d;
            J3TargetPos_Ticks = J3TargetPos_deg * TickPerDegreeJ3;

            motorJ3.setTargetPosition((int) (J3TargetPos_Ticks * 3 * J3FirsttoLast));
            motorJ3.setPower(0.1); //test Power LAtEr
            motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            HoldPosONOFF = 1;
        } else {
            if (HoldPosONOFF == 1) {
                motorJ2.setTargetPosition(motorJ2.getCurrentPosition());
                motorJ3.setTargetPosition(motorJ3.getCurrentPosition());
                motorJ2.setPower(1);
                motorJ3.setPower(1);
                motorJ2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                HoldPosONOFF = 2;
            }

        }
    }
}

