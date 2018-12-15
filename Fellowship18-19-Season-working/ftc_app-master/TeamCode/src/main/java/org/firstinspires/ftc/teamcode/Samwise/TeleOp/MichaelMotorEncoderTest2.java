package org.firstinspires.ftc.teamcode.Samwise.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp (name = "EncoderTestOfTheMichael8888")
public class MichaelMotorEncoderTest2 extends OpMode {

    private DcMotor testMotorEncoder1 = null;
    private DcMotor testMotorEncoder2 = null;
    private DcMotor testMotorEncoder3 = null;
    private Servo ArmServo1 = null;
    private Servo ArmServo2 = null;
    private Servo HandServo1 = null;
    private Servo HandServo2 = null;

    private int H = 2; //height of plane of motion
    private int L1 = 6; //length between J2 and J3
    private int L2 = 6; //length between J3 and J4
    //private double omega = Math.asin(H + Math.cos (180 - theta) * L1) / L2;
    //private double r = L1 * Math.sin (180 - theta) + L2 * Math.cos(Math.asin( H + Math.cos(180 - theta) * L1) / L2);
    static final float TestEncoderTIckCount = 1120;

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

    public void loop() {
        /*double theta = gamepad1.left_stick_y * 1000;
        double phi = (Math.acos( (H + Math.cos (180 - theta) * L1) / L2) - theta + 180) * 100;
        testMotorEncoder3.setTargetPosition(750);
        testMotorEncoder1.setTargetPosition((int)theta);
        testMotorEncoder2.setTargetPosition((int)phi); */
            if (gamepad1.x) {

            }
        //todo: convert encoder count to degree
            int testMotor1Pos = testMotorEncoder1.getCurrentPosition();
            int testMotor2Pos = testMotorEncoder2.getCurrentPosition();
            int testMotor3Pos = testMotorEncoder3.getCurrentPosition();
            double theta = gamepad1.left_stick_y * 360;
            //double phi = Math.toDegrees(Math.acos((H + L1 * Math.cos(Math.toRadians(180 - theta))) / L2) - theta + 180);
            double phi = (Math.acos( (H + Math.cos (180 - theta) * L1) / L2) - theta + 180) *3.1111;
            float TurnTable = gamepad1.left_stick_x * 500;
            testMotorEncoder1.setTargetPosition((int) theta);    //Joint 2 (first vertical joint)
            testMotorEncoder2.setTargetPosition((int) phi);      //Joint 3 (second vertical joint)
            testMotorEncoder3.setTargetPosition((int)TurnTable); //Joint 1 (turn table)

            if (gamepad1.left_stick_y >= 0.9) {
                testMotorEncoder1.setPower(0.9);
                testMotorEncoder2.setPower(0.9);
            }

            if (gamepad1.left_stick_y < 0.9) {
                if (gamepad1.left_stick_y >= 0.3) {
                    testMotorEncoder1.setPower(0.5);
                    testMotorEncoder2.setPower(0.5);
                }
            }
            if (gamepad1.left_stick_x >= 0.6) {
                testMotorEncoder3.setPower(0);
            }
            if (gamepad1.left_stick_y > 0.3) {
                testMotorEncoder1.setPower(0.3);
                testMotorEncoder2.setPower(0.3);
            }
            if (gamepad1.left_stick_y >= 0.6) {
                testMotorEncoder1.setPower(0);
                testMotorEncoder2.setPower(0);
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
