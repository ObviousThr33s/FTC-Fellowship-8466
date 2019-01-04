package org.firstinspires.ftc.teamcode.Samwise.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp (name = "Michaels Armmmm testtttt")
public class ArmTest3 extends OpMode{

    private DcMotor ArmMotor1 = null;
    private DcMotor ArmMotor2 = null;
    private DcMotor ArmMotor3 = null;
    static final int BigGearCount = 120;
    static final int SmallGearCount = 24;
    static final int EncoderCountJ1 = 1680;
    static final int MaxOrMinDegrees = 45;
    private int J1GearRatio = BigGearCount/SmallGearCount;
    private int TickPerDegreeJ1 = EncoderCountJ1/360;
    private int BigToSmallRatio = MaxOrMinDegrees * J1GearRatio;
    private int SmallDegreeToTicks = BigToSmallRatio * TickPerDegreeJ1;
    static final double EncoderCountJ2 = 1120.0; //number of ticks per motor round
    static final double EncoderCountJ3 = 1120.0; //number of ticks per motor round
    static final int HeightOfPlane = 2; //height of plane of motion
    static final int LengthJ2toJ3 = 6; //distance between J2 and J3
    static final int LengthJ3toJ4 = 6; //distance between J3 and J4
    private int H = HeightOfPlane;
    private int L1 = LengthJ2toJ3; //length between J2 and J3
    private int L2 = LengthJ3toJ4; //length between J3 and J4
    private double TickPerDegreeJ3 = EncoderCountJ3/360.0;
    private double TickPerDegreeJ2 = EncoderCountJ2/360.0;

    public void init() {
        ArmMotor1 = hardwareMap.dcMotor.get("ArmMotor1");
        ArmMotor2 = hardwareMap.dcMotor.get("ArmMotor2");
        ArmMotor3 = hardwareMap.dcMotor.get("ArmMotor3");
        ArmMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ArmMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ArmMotor3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ArmMotor1.setPower(0);
        ArmMotor2.setPower(0);
        ArmMotor3.setPower(0);
        ArmMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ArmMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ArmMotor3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void loop() {
        double J2TargetPos = (gamepad1.left_stick_y * 45 + 135) * TickPerDegreeJ2;




        //J1
        float RotationJoint = gamepad1.right_stick_x * SmallDegreeToTicks; // rotation of J1
        ArmMotor1.setTargetPosition((int)RotationJoint); //Joint 1 (turn table)
        ArmMotor1.setPower(0.8);
        ArmMotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }
    public void stop() {

    }
}
