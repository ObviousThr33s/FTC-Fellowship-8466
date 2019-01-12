package org.firstinspires.ftc.teamcode.Samwise.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp (name = "EncoderTestOfTheMichael")
public class ArmTest1 extends OpMode {

    private DcMotor testMotorEncoder1 = null;
    private DcMotor testMotorEncoder2 = null;
    private DcMotor testMotorEncoder3 = null;

    //for J1 Static
    static final double BigGearCount = 120.0;
    static final double SmallGearCount = 24.0;
    static final double EncoderCountJ1 = 1680.0;
    static final double MaxOrMinDegrees = 45.0;
    private double J1GearRatio = BigGearCount/SmallGearCount;
    private double TickPerDegreeJ1 = EncoderCountJ1/360;
    private double BigToSmallRatio = MaxOrMinDegrees * J1GearRatio;
    private double SmallDegreeToTicks = BigToSmallRatio * TickPerDegreeJ1;

    //for J2 and J3
    static final double EncoderCountJ2 = 1120.0; //number of ticks per motor round

    static final double EncoderCountJ3 = 1120.0; //number of ticks per motor round
    static final double HeightOfPlane = 2.0; //height of plane of motion
    static final double LengthJ2toJ3 = 6.0; //distance between J2 and J3
    static final double LengthJ3toJ4 = 6.0; //distance between J3 and J4

    private double H = HeightOfPlane;
    private double L1 = LengthJ2toJ3; //length between J2 and J3
    private double L2 = LengthJ3toJ4; //length between J3 and J4
    private double TickPerDegreeJ3 = EncoderCountJ3/360.0;
    private double TickPerDegreeJ2 = EncoderCountJ2/360.0;
    double J2MaxPos = 180.0;
    double J2Pos_deg = J2MaxPos;

    public void init() {

        testMotorEncoder1 = hardwareMap.dcMotor.get("testMotor1");
        testMotorEncoder2 = hardwareMap.dcMotor.get("testMotor2");
        testMotorEncoder3 = hardwareMap.dcMotor.get("testMotor3");

        testMotorEncoder1.setPower(0);
        testMotorEncoder2.setPower(0);
        testMotorEncoder3.setPower(0);

        testMotorEncoder1.setDirection(DcMotor.Direction.REVERSE);
        testMotorEncoder2.setDirection(DcMotor.Direction.REVERSE);
        testMotorEncoder3.setDirection(DcMotor.Direction.REVERSE);

        testMotorEncoder1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        testMotorEncoder2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        testMotorEncoder3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        testMotorEncoder1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        testMotorEncoder2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        testMotorEncoder3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

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
            testMotorEncoder2.setTargetPosition((int)J2MaxPos);
            testMotorEncoder3.setTargetPosition((int)J3MinPos);
            testMotorEncoder2.setPower(0.8);
            testMotorEncoder3.setPower(0.8);
            testMotorEncoder2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            testMotorEncoder3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }

    public void loop() {
        //print out the current motor postion for verifying initial postion settings

        init_loop();


        double J2Pos_ticks = J2Pos_deg * TickPerDegreeJ2;
        double J3Pos_deg, J3Pos_ticks;
        double a, b, c, c_1, d;
        int testMotor1Pos = testMotorEncoder1.getCurrentPosition();
        int testMotor2Pos = testMotorEncoder2.getCurrentPosition();
        int testMotor3Pos = testMotorEncoder3.getCurrentPosition();

        System.out.println(testMotor1Pos + testMotor2Pos + testMotor3Pos);

        double RotationJoint = gamepad1.right_stick_x * SmallDegreeToTicks; // rotation of J1


        testMotorEncoder1.setTargetPosition((int)RotationJoint); //Joint 1 (turn table)

        // set power according to theta
        J2Pos_deg = gamepad1.left_stick_y * 100.0;
        J2Pos_ticks = J2Pos_deg * TickPerDegreeJ2;
        a = Math.cos(Math.toRadians(180.0 - J2Pos_deg));
        b = (L1 * a - H) / L2;
        c = Math.acos(b);
        c_1 = Math.toDegrees(c);
        d = c_1 + 180.0 - J2Pos_deg;
        J3Pos_deg = d;
        J3Pos_ticks = J3Pos_deg * TickPerDegreeJ3;
        testMotorEncoder2.setTargetPosition((int) J2Pos_ticks);
        testMotorEncoder3.setTargetPosition((int) J3Pos_ticks);
        testMotorEncoder2.setPower(0.8);
        testMotorEncoder3.setPower(0.8);



        testMotorEncoder1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        testMotorEncoder2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        testMotorEncoder3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if (gamepad1.b) {
            testMotorEncoder1.setPower(0);
            testMotorEncoder2.setPower(0);
            testMotorEncoder3.setPower(0);

        }
    }
    public void stop() {
        testMotorEncoder1.setPower(0);
        testMotorEncoder2.setPower(0);
        testMotorEncoder3.setPower(0);
    }

}
