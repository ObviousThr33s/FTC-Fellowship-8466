package org.firstinspires.ftc.teamcode.Samwise.TeleOp.TestTeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp (name = "pLaNeOfMoTiOn")
@Disabled
public class Planeofmotion extends OpMode {
    private DcMotor J1 = null;
    private DcMotor J2 = null;
    private DcMotor J22 = null;
    private DcMotor J3 = null;

    static final double BigGearCount = 120.0;
    static final double SmallGearCount = 24.0;
    static final double EncoderCountJ1 = 1680;
    static final double MaxOrMinDegrees = 45.0;
    private double J1GearRatio = BigGearCount/SmallGearCount;
    private double TickPerDegreeJ1 = EncoderCountJ1/360.0;
    private double BigToSmallRatio = MaxOrMinDegrees * J1GearRatio;
    private double SmallDegreeToTicks = BigToSmallRatio * TickPerDegreeJ1;

    int J3targetpos;
    //J2, J3 Static
    static final double EncoderCountJ2 = 1680.0; //number of ticks per motor round
    static final double EncoderCountJ3 = 1680.0; //number of ticks per motor round
    static final double HeightOfPlane = 2.0; //height of plane of motion
    static final double LengthJ2toJ3 = 6.0; //distance between J2 and J3
    static final double LengthJ3toJ4 = 6.0; //distance between J3 and J4

    private double H = HeightOfPlane;
    private double L1 = LengthJ2toJ3; //length between J2 and J3
    private double L2 = LengthJ3toJ4; //length between J3 and J4
    private double TickPerDegreeJ3 = EncoderCountJ3/360.0;
    private double TickPerDegreeJ2 = EncoderCountJ2/360.0;
    double J2MaxPos = 180.0 * TickPerDegreeJ2;
    double J2MinPos = 90.0 * TickPerDegreeJ2;
    double mininticks = 90.0 * TickPerDegreeJ2;
    double maxinticks = 180.0 * TickPerDegreeJ2;

    public void init() {
        J1 = hardwareMap.dcMotor.get("J1");
        J2 = hardwareMap.dcMotor.get("1J2");
        J22 = hardwareMap.dcMotor.get("2J2");
        J3 = hardwareMap.dcMotor.get("J3");
        J1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        J2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        J3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        J22.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        J22.setPower(0);
        J1.setPower(0);
        J2.setPower(0);
        J3.setPower(0);
        J1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        J2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        J3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        J22.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void loop() {
        if (gamepad1.x) {
            J2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            J3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            J22.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
        if (Math.abs(gamepad1.left_stick_y) > 0.1) {
            if (gamepad1.left_stick_y>0.1) {
                J2.setTargetPosition(1000);
                J22.setTargetPosition(1000);
            } if (gamepad1.left_stick_y <-0.1) {
                J2.setTargetPosition(-1000);
                J22.setTargetPosition(-1000);
            }
            J2.setPower(Math.abs(gamepad1.left_stick_y)/2);
            J22.setPower(Math.abs(gamepad1.left_stick_y)/2);
            J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            J22.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            J3targetpos = (int)(J2.getCurrentPosition()* 1.6);
            J3.setTargetPosition(J3targetpos);
            J3.setPower(0.7);
            J3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        if ((gamepad1.left_stick_y <0.1)&&(gamepad1.left_stick_y >-0.1) ) {
            J2.setPower(0);
            J22.setPower(0);


        }

    }
}
