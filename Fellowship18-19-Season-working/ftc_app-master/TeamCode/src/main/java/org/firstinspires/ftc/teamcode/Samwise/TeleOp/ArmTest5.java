package org.firstinspires.ftc.teamcode.Samwise.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp (name = "Motortest1;")
public class ArmTest5 extends OpMode {

    private DcMotor testMotorEncoder1 = null;
   // private DcMotor testMotorEncoder2 = null;
    //private DcMotor testMotorEncoder3 = null;
   // private DcMotor testMotorEncoder4 = null;
   // private DcMotorEx testMotorEncoder5 = null;

    //for J1
    static final double BigGearCount = 120.0;
    static final double SmallGearCount = 24.0;
    static final double EncoderCountJ1 = 1680.0;
    static final double MaxOrMinDegrees = 45.0;
    private double J1GearRatio = BigGearCount / SmallGearCount;
    private double TickPerDegreeJ1 = EncoderCountJ1 / 360;
    private double BigToSmallRatio = MaxOrMinDegrees * J1GearRatio;
    private double SmallDegreeToTicks = BigToSmallRatio * TickPerDegreeJ1;


    //for J2 and J3
    static final double EncoderCountJ2 = 1120.0; //number of ticks per motor round

    static final double EncoderCountJ3 = 1120.0; //number of ticks per motor round
    static final double HeightOfPlane = 2.0; //height of plane of motion
    static final double LengthJ2toJ3 = 6.0; //distance between J2 and J3
    static final double LengthJ3toJ4 = 6.0; //distance between J3 and J4

    private double H = HeightOfPlane;
    private double L1 = LengthJ2toJ3; //distance between J2 and J3
    private double L2 = LengthJ3toJ4; //distance between J3 and J4
    private double TickPerDegreeJ3 = EncoderCountJ3 / 360.0;
    private double TickPerDegreeJ2 = EncoderCountJ2 / 360.0;

    private double theta = 0;  // rotate degree of J2 if
    private double phi = 0; //Rotate J3

    int motorcurrentpos =0;
    public void init() {

        testMotorEncoder1 = hardwareMap.dcMotor.get("testMotor1");
   //     testMotorEncoder2 = hardwareMap.dcMotor.get("testMotor2");
     //   testMotorEncoder3 = hardwareMap.dcMotor.get("testMotor3");
        //testMotorEncoder4 = hardwareMap.dcMotor.get("testMotor4");
        testMotorEncoder1.setPower(0);
       // testMotorEncoder2.setPower(0);
       // testMotorEncoder3.setPower(0);
        //testMotorEncoder4.setPower(0);
        testMotorEncoder1.setDirection(DcMotor.Direction.REVERSE);
      //  testMotorEncoder2.setDirection(DcMotor.Direction.REVERSE);
      //  testMotorEncoder3.setDirection(DcMotor.Direction.REVERSE);
        //testMotorEncoder4.setDirection(DcMotor.Directio+n.REVERSE);
        testMotorEncoder1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
       // testMotorEncoder2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
       // testMotorEncoder3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //testMotorEncoder4.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        testMotorEncoder1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
       // testMotorEncoder2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
     //   testMotorEncoder3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //testMotorEncoder4.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // initialize Arm to a default position, (temporary code)
       // double theta_init = 90.0;
       // double phi_init = Math.acos((L1 * Math.cos(180.0 - theta_init) - H) / L2) + 180.0 - theta_init;

       // testMotorEncoder1.setTargetPosition((int) (theta_init * TickPerDegreeJ2));
      //  testMotorEncoder2.setTargetPosition((int) (phi_init * TickPerDegreeJ3));
       // testMotorEncoder2.setPower(0.5);
       // testMotorEncoder1.setPower(0.5);
       // testMotorEncoder1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
     //   testMotorEncoder2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
       // theta = theta_init;
    }
    public void loop() {


        if (gamepad1.x) {
            motorcurrentpos = testMotorEncoder1.getCurrentPosition() +40;

            testMotorEncoder1.setTargetPosition(motorcurrentpos);
            testMotorEncoder1.setPower(0.1);
;

        }
        if (gamepad1.b) {
            motorcurrentpos = testMotorEncoder1.getCurrentPosition() -40;
            testMotorEncoder1.setTargetPosition(motorcurrentpos);
            testMotorEncoder1.setPower(0.1);


        }
        if (gamepad1.y) {
            testMotorEncoder1.setPower(0);

        }
        testMotorEncoder1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        System.out.println("==>" + motorcurrentpos);
    }
}