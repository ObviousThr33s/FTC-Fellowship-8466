package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class OctoSamwiseGenius extends OctoSamwiseSmart
{
    int hold_position_J2 = Integer.MAX_VALUE;
    int hold_position_J3 = Integer.MAX_VALUE;

    static final double BigGearCount = 120.0;
    static final double SmallGearCount = 24.0;
    static final double EncoderCountJ1 = 1680.0;
    static final double MaxOrMinDegrees = 45.0;
    private double J1GearRatio = BigGearCount/SmallGearCount;
    private double TickPerDegreeJ1 = EncoderCountJ1/360.0;
    private double BigToSmallRatio = MaxOrMinDegrees * J1GearRatio;
    private double SmallDegreeToTicks = BigToSmallRatio * TickPerDegreeJ1;

    //J2, J3 Static
    static final double EncoderCountJ2 = 1993.0; //number of ticks per motor round
    static final double EncoderCountJ3 = 1993.0; //number of ticks per motor round
    static final double HeightOfPlane = 6.0; //height of plane of motion
    static final double LengthJ2toJ3 = 13.0; //distance between J2 and J3
    static final double LengthJ3toJ4 = 15.0; //distance between J3 and J4

    private double H = HeightOfPlane;
    private double L1 = LengthJ2toJ3; //length between J2 and J3
    private double L2 = LengthJ3toJ4; //length between J3 and J4
    private double TickPerDegreeJ3 = EncoderCountJ3/360.0;
    private double TickPerDegreeJ2 = EncoderCountJ2/360.0;




    double J2FirsttoLast = 1;
    double J3FirsttoLast = 1;

    int HoldPosONOFF = 1;


    public OctoSamwiseGenius(HardwareMap hwm)
    {
        super(hwm);

    }

    public void hoverPlaneOfMotion(double speed)
    {

    }
}
