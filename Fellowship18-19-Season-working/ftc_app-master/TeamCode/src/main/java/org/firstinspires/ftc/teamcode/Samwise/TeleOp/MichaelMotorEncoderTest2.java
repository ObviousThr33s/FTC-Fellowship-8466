package org.firstinspires.ftc.teamcode.Samwise.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp (name = "FinalMichaelArmTest")
public class MichaelMotorEncoderTest2 extends OpMode {

    private DcMotor testMotorEncoder1 = null;
    private DcMotor testMotorEncoder2 = null;
    private DcMotor testMotorEncoder3 = null;
    private DcMotor testMotorEncoder4 = null;
    private DcMotorEx testMotorEncoder5 = null;

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
        // initialize Arm to a default position, (temporary code)
        double theta_init = 90.0;
        double phi_init = Math.acos((L1 * Math.cos(180.0 - theta_init) - H) / L2) + 180.0 - theta_init;

        testMotorEncoder1.setTargetPosition((int) (theta_init * TickPerDegreeJ2));
        testMotorEncoder2.setTargetPosition((int) (phi_init * TickPerDegreeJ3));
        testMotorEncoder2.setPower(0.5);
        testMotorEncoder1.setPower(0.5);
        testMotorEncoder1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        testMotorEncoder2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        theta = theta_init;
    }
/*
    // Test Loop1 control J2. J3 by one key
    public void loop() {
        //print out the current motor postion for verifying initial postion settings

        int testMotor1Pos = testMotorEncoder1.getCurrentPosition();
        int testMotor2Pos = testMotorEncoder2.getCurrentPosition();
        int testMotor3Pos = testMotorEncoder3.getCurrentPosition();

        System.out.println(testMotor1Pos + testMotor2Pos + testMotor3Pos);

        float RotationJoint = gamepad1.right_stick_x * SmallDegreeToTicks; // rotation of J1

        testMotorEncoder3.setTargetPosition((int)RotationJoint); //Joint 1 (turn table)
        testMotorEncoder3.setPower(0.8);

        double a = Math.cos(Math.toRadians(180.0-theta));
        double b = (L1 * a - H)/L2;
        double c = Math.acos(b);
        double c_1 = Math.toDegrees(c);
        double d = c + 180.0 - theta;
        //phi = Math.acos((L1 * Math.cos(180.0-theta)-H)/L2)+180.0-theta;
        phi = d;

        double thetatoticks = theta * TickPerDegreeJ2;
        double phitoticks = phi * TickPerDegreeJ3;

        if (gamepad1.left_stick_y < -0.1) {
            if (theta > 90){
            //if ((theta >= 90)&&(theta <180)){
                testMotorEncoder1.setTargetPosition((int) thetatoticks);
                testMotorEncoder2.setTargetPosition((int) phitoticks);
                if ((!testMotorEncoder1.isBusy())&& (!testMotorEncoder2.isBusy())) {
                    testMotorEncoder1.setPower(0.8);
                    testMotorEncoder2.setPower(0.8);
                    theta = theta - (gamepad1.left_stick_y * 25);
                };

            }
        }
        if (gamepad1.left_stick_y >0.1) {
            //if ((theta <= 180)&&(theta > 90)){

            if (theta < 180){
                testMotorEncoder1.setTargetPosition((int)thetatoticks);
                testMotorEncoder2.setTargetPosition((int)phitoticks);
                if ((!testMotorEncoder1.isBusy())&& (!testMotorEncoder2.isBusy())) {
                    testMotorEncoder1.setPower(0.8);
                    testMotorEncoder2.setPower(0.8);
                    theta = theta - (gamepad1.left_stick_y * 25);

                }
            }

        }
        if (gamepad1.left_stick_y == 0) {
            testMotorEncoder1.setPower(0);
            testMotorEncoder2.setPower(0);
        }


        System.out.println("===>" + phitoticks + "phi to ticks");
        System.out.println("===>" + thetatoticks +"theta to ticks");



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
*/
//Test Loop 2; control J2 and J3 separately

    public void loop() {
        //print out the current motor postion for verifying initial postion settings

        int testMotor1Pos = testMotorEncoder1.getCurrentPosition();
        int testMotor2Pos = testMotorEncoder2.getCurrentPosition();
        int testMotor3Pos = testMotorEncoder3.getCurrentPosition();

        System.out.println(testMotor1Pos + testMotor2Pos + testMotor3Pos);

        double RotationJoint = gamepad1.right_stick_x * SmallDegreeToTicks; // rotation of J1

        testMotorEncoder3.setTargetPosition((int) RotationJoint); //Joint 1 (turn table)
        testMotorEncoder3.setPower(0.8);

        double a = Math.cos(Math.toRadians(180.0 - theta));
        double b = (L1 * a - H) / L2;
        double c = Math.acos(b);
        double c_1 = Math.toDegrees(c);
        double d = c + 180.0 - theta;
        phi = d;
        //phi = Math.acos((L1 * Math.cos(180.0-theta)-H)/L2)+180.0-theta;
        double thetatoticks = theta * TickPerDegreeJ2;
        double phitoticks = phi * TickPerDegreeJ3;
        // set power according to theta
        if (gamepad1.left_stick_y < -0.1) {
            if (theta > 90) {
                //if ((theta >= 90)&&(theta <180)){
                testMotorEncoder1.setTargetPosition((int) thetatoticks);

                if (!testMotorEncoder1.isBusy()) {
                    testMotorEncoder1.setPower(0.8);
                    testMotorEncoder1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    theta = theta - (gamepad1.left_stick_y * 25);
                }
                ;

            }
        }
        if (gamepad1.left_stick_y > 0.1) {
            //if ((theta <= 180)&&(theta > 90)){
            if (theta < 180) {
                testMotorEncoder1.setTargetPosition((int) thetatoticks);

                if (!testMotorEncoder1.isBusy()) {
                    testMotorEncoder1.setPower(0.8);
                    testMotorEncoder1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    theta = theta - (gamepad1.left_stick_y * 25);
                }
            }

        }

        if ((gamepad1.left_stick_y == 0) && (gamepad1.x)) {
            testMotorEncoder2.setTargetPosition((int) phitoticks);
            while (!testMotorEncoder2.isBusy()) {
                testMotorEncoder2.setPower(0.8);
                testMotorEncoder2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
        }


        System.out.println("===>" + phitoticks + "phi to ticks");
        System.out.println("===>" + thetatoticks + "theta to ticks");


        testMotorEncoder3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if(gamepad1.b) {
            testMotorEncoder1.setPower(0);
            testMotorEncoder2.setPower(0);
            testMotorEncoder3.setPower(0);
            testMotorEncoder4.setPower(0);
            testMotorEncoder5.setPower(0);
        }
    }

    public void stop() {
        testMotorEncoder1.setPower(0);
        testMotorEncoder2.setPower(0);
        testMotorEncoder3.setPower(0);
    }

}
