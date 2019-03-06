package org.firstinspires.ftc.teamcode.Samwise.TeleOp.TestTeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp (name = "Michaels ArM testtttt", group="tests")
@Disabled
public class ArmTest3 extends OpMode{

    private DcMotor ArmMotor1 = null;
    private DcMotor ArmMotor2 = null;
    private DcMotor ArmMotor3 = null;
    //J1 static
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

    public void init_loop() {

        double J3MinPos, a, b, c, c_1, d;
        a = Math.cos(Math.toRadians(180.0 - J2MaxPos));
        b = (L1 * a - H) / L2;
        c = Math.acos(b);
        c_1 = Math.toDegrees(c);
        d = c_1 + 180.0 - J2MaxPos;
        J3MinPos = d * TickPerDegreeJ3;

        if (gamepad1.x){
            ArmMotor2.setTargetPosition((int)J2MaxPos);
            ArmMotor3.setTargetPosition((int)J3MinPos);
            ArmMotor2.setPower(0.8);
            ArmMotor3.setPower(0.8);
            ArmMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            ArmMotor3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }
    public void loop() {

        init_loop();
        if (gamepad1.left_stick_y <= -0.01) { //push forward
                ArmMotor2.setTargetPosition((int)J2MaxPos);
        }
        if (gamepad1.left_stick_y >= 0.01) { //backwards
            ArmMotor2.setTargetPosition((int)J2MinPos);
        }
        ArmMotor2.setPower(gamepad1.left_stick_y);
        /* double J2CurrentPos_Ticks = ArmMotor2.getCurrentPosition();
        if (J2CurrentPos_Ticks <= mininticks) {
            if (gamepad1.left_stick_y >= 0.02) {
                ArmMotor2.setPower(0);
            }
        }
        if (J2CurrentPos_Ticks >= 180) {
            if (gamepad1.left_stick_y <= -0.02) {
                ArmMotor2.setPower(0);
            }
        } */
        ArmMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        double J2CurrentPos_deg;

        double J3TargetPos_deg;
        double J3TargetPos_Ticks;
        double a, b, c, c_1, d;
        J2CurrentPos_deg = (ArmMotor2.getCurrentPosition() / TickPerDegreeJ2);
        a = Math.cos(Math.toRadians(180.0 - J2CurrentPos_deg));
        b = (L1 * a - H) / L2;
        c = Math.acos(b);
        c_1 = Math.toDegrees(c);
        d = c_1 + 180.0 - J2CurrentPos_deg;
        J3TargetPos_deg = d;
        J3TargetPos_Ticks = J3TargetPos_deg * TickPerDegreeJ3;

        ArmMotor3.setTargetPosition((int)J3TargetPos_Ticks);
        ArmMotor3.setPower(0.1); //test Power LAtEr
        ArmMotor3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //J1
        double RotationJoint = gamepad1.right_stick_x * SmallDegreeToTicks; // rotation of J1
        ArmMotor1.setTargetPosition((int)RotationJoint); //Joint 1 (turn table)
        ArmMotor1.setPower(0.1);
        ArmMotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if (gamepad1.b) {
            ArmMotor1.setPower(0);
            ArmMotor2.setPower(0);
            ArmMotor3.setPower(0);
        }
    }
    public void stop() {
        ArmMotor2.setPower(0);
        ArmMotor3.setPower(0);
        ArmMotor1.setPower(0);
    }
}
