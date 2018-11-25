package org.firstinspires.ftc.teamcode.Samwise.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp (name = "EncoderTestOfTheMichael7879")
public class MichaelMotorEncoderTest extends OpMode {

    private DcMotor testMotorEncoder1 = null;
    private DcMotor testMotorEncoder2 = null;
    private int H = 2; //height of plane of motion
    private int L1 = 6; //length between J2 and J3
    private int L2 = 6; //length between J3 and J4
    private float theta = gamepad1.left_stick_y;
    private double phi = Math.acos( (H + Math.cos (180 - theta) * L1) / L2) - theta + 180;
    private double omega = Math.asin(H + Math.cos (180 - theta) * L1) / L2;
    private double r = L1 * Math.sin (180 - theta) + L2 * Math.cos(Math.asin( H + Math.cos(180 - theta) * L1) / L2);
    static final float TestEncoderTIckCount = 1120;

    public void init() {
        testMotorEncoder1 = hardwareMap.dcMotor.get("testMotor1");
        testMotorEncoder2 = hardwareMap.dcMotor.get("testMotor2");

        testMotorEncoder1.setPower(0);
        testMotorEncoder2.setPower(0);
        testMotorEncoder1.setDirection(DcMotor.Direction.REVERSE);
        testMotorEncoder2.setDirection(DcMotor.Direction.REVERSE);

        testMotorEncoder1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        testMotorEncoder2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        testMotorEncoder1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        testMotorEncoder2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void loop() {


        testMotorEncoder1.setTargetPosition((int)theta);
        testMotorEncoder2.setTargetPosition((int)phi);
    }
    public void stop() {
        testMotorEncoder1.setPower(0);
    }

}
