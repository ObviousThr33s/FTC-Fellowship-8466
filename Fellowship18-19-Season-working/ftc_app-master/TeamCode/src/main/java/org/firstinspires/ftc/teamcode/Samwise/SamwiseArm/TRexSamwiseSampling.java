package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class TRexSamwiseSampling extends TRexSamwiseSmart
{
    private static final int SAMPLING_RIGHT_J1 = -20;
    private static final int SAMPLING_CENTER_J1 = 0;
    private static final int SAMPLING_LEFT_J1 = 20;
    private static final int SAMPLING_CENTER_J2 = 90;
    private static final int SAMPLING_CENTER_J3 = 100;
    private static final int SAMPLING_LEFT_RIGHT_J2 = 100;
    private static final int SAMPLING_LEFT_RIGHT_J3 = 110;
    private static final int SAMPLING_J1_CRATER = 40;
    private static final int SAMPLING_J2_CRATER = 100;
    private static final int SAMPLING_J3_CRATER = 200;
    private static final int SAMPLING_J1_DEPOT = 40;
    private static final int SAMPLING_J2_DEPOT = 100;
    private static final int SAMPLING_J3_DEPOT = 300;

    public TRexSamwiseSampling(HardwareMap hwm)
    {
        super(hwm);
    }

    public void toSamplePositionRight()
    {
        System.out.println("toSamplePositionRight");
//        toPosition(SAMPLING_RIGHT_J1, SAMPLING_LEFT_RIGHT_J2, SAMPLING_LEFT_RIGHT_J3);
    }

    public void toSamplePositionLeft()
    {
        System.out.println("toSamplePositionLeft");
//        toPosition(SAMPLING_LEFT_J1, SAMPLING_CENTER_J2, SAMPLING_CENTER_J3);
    }

    public void toSamplePositionCenter()
    {
        System.out.println("toSamplePositionCenter");
//        toPosition(SAMPLING_CENTER_J1, SAMPLING_LEFT_RIGHT_J2, SAMPLING_LEFT_RIGHT_J3);
    }

    public void goldSampleDropDepot()
    {
//        toPosition(SAMPLING_J1_DEPOT, SAMPLING_J2_DEPOT, SAMPLING_J3_DEPOT);
    }

    public void goldSampleDropCrater()
    {
//        toPosition(SAMPLING_J1_CRATER, SAMPLING_J2_CRATER, SAMPLING_J3_CRATER);
    }
}
