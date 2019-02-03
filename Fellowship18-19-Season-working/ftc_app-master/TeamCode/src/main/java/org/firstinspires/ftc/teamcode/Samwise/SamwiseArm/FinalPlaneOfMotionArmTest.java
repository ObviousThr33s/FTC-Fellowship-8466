package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp (name = "Michaels Arm testtttt1")


// COPY OF ArmTest3___________________________________________________________




public class FinalPlaneOfMotionArmTest extends OpMode{

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

     /*   double J3MinPos, a, b, c, c_1, d;
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
        }*/
        int motorcurrentpos;
        int motor3currentpos;
        if (gamepad1.x) {
            motorcurrentpos =  ArmMotor2.getCurrentPosition() +40;
            ArmMotor2.setTargetPosition(motorcurrentpos);
            ArmMotor2.setPower(0.1);
        }
        if (gamepad1.b) {
            motorcurrentpos = ArmMotor2.getCurrentPosition() -40;
            ArmMotor2.setTargetPosition(motorcurrentpos);
            ArmMotor2.setPower(0.1);
        }
        ArmMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if (gamepad1.y) {
            ArmMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            ArmMotor3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            ArmMotor3.setPower(0);
            ArmMotor2.setPower(0);
        }
        if(gamepad1.dpad_left) {
            motor3currentpos = ArmMotor3.getCurrentPosition() +40;
            ArmMotor3.setTargetPosition(motor3currentpos);
            ArmMotor3.setPower(0.1);
        }
        if (gamepad1.dpad_right) {
            motor3currentpos = ArmMotor3.getCurrentPosition() - 40;
            ArmMotor3.setTargetPosition(motor3currentpos);
            ArmMotor3.setPower(0.1);
        }
        ArmMotor3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void loop() {
        if (Math.abs(gamepad1.left_stick_y)>0.1) {
//        init_loop();
            if (gamepad1.left_stick_y <= -0.01) { //push forward
                ArmMotor2.setTargetPosition((int) (J2MaxPos * J2FirsttoLast));
            }
            if (gamepad1.left_stick_y >= 0.01) { //backwards
                ArmMotor2.setTargetPosition((int) (J2MinPos * J2FirsttoLast));
            }
            ArmMotor2.setPower(gamepad1.left_stick_y / 1.7);
        /*double J2CurrentPos_Ticks = ArmMotor2.getCurrentPosition();
        if (J2CurrentPos_Ticks <= mininticks) {
            if (gamepad1.left_stick_y >= 0.02) {
                ArmMotor2.setPower(0);
            }
        }
        if (J2CurrentPos_Ticks >= 180) {
            if (gamepad1.left_stick_y <= -0.02) {
                ArmMotor2.setPower(0);
            }
        }*/
            ArmMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            if (gamepad1.left_stick_y == 0) {
                ArmMotor2.setPower(0);
            }
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

            ArmMotor3.setTargetPosition((int) (J3TargetPos_Ticks * 3 * J3FirsttoLast));
            ArmMotor3.setPower(0.1); //test Power LAtEr
            ArmMotor3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //J1
            double RotationJoint = gamepad1.right_stick_x * SmallDegreeToTicks; // rotation of J1
            ArmMotor1.setTargetPosition((int) RotationJoint); //Joint 1 (turn table)
            ArmMotor1.setPower(0.1);
            ArmMotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            if (gamepad1.b) {
                ArmMotor1.setPower(0);
                ArmMotor2.setPower(0);
                ArmMotor3.setPower(0);
            }
            HoldPosONOFF = 1;
        } else {
            if (HoldPosONOFF == 1) {
                ArmMotor2.setTargetPosition(ArmMotor2.getCurrentPosition());
                ArmMotor3.setTargetPosition(ArmMotor3.getCurrentPosition());
                ArmMotor2.setPower(1);
                ArmMotor3.setPower(1);
                ArmMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                ArmMotor3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                HoldPosONOFF = 2;
            }

        }
    }
    public void stop() {
        ArmMotor2.setPower(0);
        ArmMotor3.setPower(0);
        ArmMotor1.setPower(0);
    }
}
