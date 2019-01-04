package org.firstinspires.ftc.teamcode.Samwise.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp (name = "EncodertestOftheMichael")
public class MichaelMotorEncoderTest2 extends OpMode {

    private DcMotor testMotorEncoder1 = null;
    private DcMotor testMotorEncoder2 = null;
    private DcMotor testMotorEncoder3 = null;
    private DcMotor testMotorEncoder4 = null;

    //for J1
    static final int BigGearCount = 120;
    static final int SmallGearCount = 24;
    static final int EncoderCountJ1 = 1680;
    static final int MaxOrMinDegrees = 45;
    private int J1GearRatio = BigGearCount/SmallGearCount;
    private int TickPerDegreeJ1 = EncoderCountJ1/360;
    private int BigToSmallRatio = MaxOrMinDegrees * J1GearRatio;
    private int SmallDegreeToTicks = BigToSmallRatio * TickPerDegreeJ1;
//    private Servo ArmServo1 = null;
 //   private Servo ArmServo2 = null;
  //  private Servo HandServo1 = null;
  //  private Servo HandServo2 = null;
    //for J2 and J3
    static final int EncoderCountJ2 = 1120; //number of ticks per motor round

    static final int EncoderCountJ3 = 1120; //number of ticks per motor round
    static final int HeightOfPlane = 2; //height of plane of motion
    static final int LengthJ2toJ3 = 6; //distance between J2 and J3
    static final int LengthJ3toJ4 = 6; //distance between J3 and J4

    private int H = HeightOfPlane;
    private int L1 = LengthJ2toJ3; //length between J2 and J3
    private int L2 = LengthJ3toJ4; //length between J3 and J4
    private float TickPerDegreeJ3 = EncoderCountJ3/360;
    private float TickPerDegreeJ2 = EncoderCountJ2/360;
    //private double omega = Math.asin(H + Math.cos (180 - theta) * L1) / L2;
    //private double r = L1 * Math.sin (180 - theta) + L2 * Math.cos(Math.asin( H + Math.cos(180 - theta) * L1) / L2);
    private double theta = 0;  // rotate degree of J2 if
    private double phi = 0; //Rotate J3
    //private double theta2ticks = theta * TickPerDegreeJ2;
  //  private double phi2ticks = phi * TickPerDegreeJ3;

    public void init() {

        testMotorEncoder1 = hardwareMap.dcMotor.get("testMotor1");
        testMotorEncoder2 = hardwareMap.dcMotor.get("testMotor2");
        testMotorEncoder3 = hardwareMap.dcMotor.get("testMotor3");
        //testMotorEncoder4 = hardwareMap.dcMotor.get("testMotor4");
        testMotorEncoder1.setPower(0);
        testMotorEncoder2.setPower(0);
        testMotorEncoder3.setPower(0);
        //testMotorEncoder4.setPower(0);
        testMotorEncoder1.setDirection(DcMotor.Direction.REVERSE);
        testMotorEncoder2.setDirection(DcMotor.Direction.REVERSE);
        testMotorEncoder3.setDirection(DcMotor.Direction.REVERSE);
        //testMotorEncoder4.setDirection(DcMotor.Direction.REVERSE);
        testMotorEncoder1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        testMotorEncoder2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        testMotorEncoder3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //testMotorEncoder4.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        testMotorEncoder1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        testMotorEncoder2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        testMotorEncoder3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //testMotorEncoder4.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // initialize Arm to a default potition,
        double theta_init = 90;
        double phi_init = Math.acos((L1 * Math.cos(180-theta_init)-H)/L2)+180-theta_init;

        testMotorEncoder1.setTargetPosition((int) (theta_init * TickPerDegreeJ2) );
        testMotorEncoder2.setTargetPosition((int) (phi_init * TickPerDegreeJ3) );
        testMotorEncoder2.setPower(0.5);
        testMotorEncoder1.setPower(0.5);
        testMotorEncoder1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        testMotorEncoder2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        theta = theta_init;
    }

    public void loop() {
        //print out the current motor postion for verifying initial postion settings

        int testMotor1Pos = testMotorEncoder1.getCurrentPosition();
        int testMotor2Pos = testMotorEncoder2.getCurrentPosition();
        int testMotor3Pos = testMotorEncoder3.getCurrentPosition();

        System.out.println(testMotor1Pos + testMotor2Pos + testMotor3Pos);

        float RotationJoint = gamepad1.right_stick_x * SmallDegreeToTicks; // rotation of J1

        testMotorEncoder3.setTargetPosition((int)RotationJoint); //Joint 1 (turn table)


        phi = Math.acos((L1 * Math.cos(180-theta)-H)/L2)+180-theta;
        double thetatoticks = theta * TickPerDegreeJ2;
        double phitoticks = phi * TickPerDegreeJ3;
        // set power according to theta
        if (gamepad1.left_stick_y < -0.1) {
            if( theta < 180){
                testMotorEncoder1.setTargetPosition((int) thetatoticks);
                testMotorEncoder2.setTargetPosition((int) phitoticks);
                testMotorEncoder1.setPower(0);
                testMotorEncoder2.setPower(0.8);

                theta = theta - (gamepad1.left_stick_y * 10);
            }




        }
        if (gamepad1.left_stick_y >0.1) {
            if (theta > 90){
                testMotorEncoder1.setTargetPosition((int)thetatoticks);
                testMotorEncoder2.setTargetPosition((int)phitoticks);
                testMotorEncoder1.setPower(0);
                testMotorEncoder2.setPower(0.8);

                theta = theta + (gamepad1.left_stick_y * 10);
            }

        }
        testMotorEncoder1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        testMotorEncoder2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        testMotorEncoder3.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }
    public void stop() {
        testMotorEncoder1.setPower(0);
        testMotorEncoder2.setPower(0);
        testMotorEncoder3.setPower(0);
    }

}
