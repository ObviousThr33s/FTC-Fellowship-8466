package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class SamwiseSampling extends SamwiseSmart
{
    static final int SAMPLING_RIGHT_J1 = -20;
    static final int SAMPLING_CENTER_J1 = 0;
    static final int SAMPLING_LEFT_J1 = 20;
    static final int SAMPLING_CENTER_J2 = 90;
    static final int SAMPLING_CENTER_J3 = 100;
    static final int SAMPLING_LEFT_RIGHT_J2 = 100;
    static final int SAMPLING_LEFT_RIGHT_J3 = 110;
    static final int SAMPLING_J1_CRATER = 40;
    static final int SAMPLING_J2_CRATER = 100;
    static final int SAMPLING_J3_CRATER = 200;
    static final int SAMPLING_J1_DEPOT = 40;
    static final int SAMPLING_J2_DEPOT = 100;
    static final int SAMPLING_J3_DEPOT = 300;

    public SamwiseSampling(HardwareMap hwm)
    {
        super(hwm);
    }

    public void toSamplePositionRight()
    {
        System.out.println("toSamplePositionRight");
        toPositionWithJ1(SAMPLING_RIGHT_J1, SAMPLING_LEFT_RIGHT_J2, SAMPLING_LEFT_RIGHT_J3);
    }

    public void toSamplePositionLeft()
    {
        System.out.println("toSamplePositionLeft");
        toPositionWithJ1(SAMPLING_LEFT_J1, SAMPLING_CENTER_J2, SAMPLING_CENTER_J3);
    }

    public void toSamplePositionCenter()
    {
        System.out.println("toSamplePositionCenter");
        toPositionWithJ1(SAMPLING_CENTER_J1, SAMPLING_LEFT_RIGHT_J2, SAMPLING_LEFT_RIGHT_J3);
    }

    public void goldSampleDropDepot()
    {
        toPositionWithJ1(SAMPLING_J1_DEPOT, SAMPLING_J2_DEPOT, SAMPLING_J3_DEPOT);
    }

    public void goldSampleDropCrater()
    {
        toPositionWithJ1(SAMPLING_J1_CRATER, SAMPLING_J2_CRATER, SAMPLING_J3_CRATER);
    }
}
