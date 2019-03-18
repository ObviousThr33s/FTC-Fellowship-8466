package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class OctoSamwiseArmPoM extends OctoSamwiseSmart
{
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

    static final int E1_MAX_COUNT = 4661;

    ElapsedTime runTime = new ElapsedTime();
    static final int TIMEOUT = 100;

    public static final double MANUAL_POWER_J1 = 0.8;
    public static final double MANUAL_POWER_J2 = 0.4;
    public static final double UP_POWER_J3 = 0.4;
    public static final double E1_POWER = 0.8;
    public static final double E2_POWER = 0.8;


    public OctoSamwiseArmPoM(HardwareMap hwm)
    {
        super(hwm);
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
                J2MaxPos = motor1J2.getCurrentPosition() + EncoderCountJ2 / 360.0;
                motor1J2.setTargetPosition((int) (J2MaxPos * J2FirsttoLast));
                motor2J2.setTargetPosition((int) (J2MaxPos * J2FirsttoLast));

                motor1J2.setPower(0.5);
                motor2J2.setPower(0.5);
                motor1J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motor2J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                J3TargetPos_Ticks = motorJ3.getCurrentPosition() + (-1.5 * (EncoderCountJ2 / 360.0));
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