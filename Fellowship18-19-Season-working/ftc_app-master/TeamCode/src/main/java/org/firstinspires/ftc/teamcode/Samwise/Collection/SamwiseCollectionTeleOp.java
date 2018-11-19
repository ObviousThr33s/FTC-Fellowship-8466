package org.firstinspires.ftc.teamcode.Samwise.Collection;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name = "CollectionArmTeleOp")
public class SamwiseCollectionTeleOp extends OpMode {

    public DcMotor cMotor1 = null; //left and right
    public DcMotor cMotor2 = null; //slow up and down
    public DcMotor cMotor3 = null; //fast up an down
    public DcMotor cMotor4 = null;

    public Servo servo1 = null;
    public Servo servo2 = null;
    public Servo servo3 = null;

    static final float cmotorTickCount = 1120;

    public void init() {
        cMotor1 = hardwareMap.dcMotor.get("motor1");
        cMotor2 = hardwareMap.dcMotor.get("motor2");
        cMotor3 = hardwareMap.dcMotor.get("motor3");
        cMotor4 = hardwareMap.dcMotor.get("motor4");

        servo1 = hardwareMap.servo.get("servo1");
        servo2 = hardwareMap.servo.get("servo2");
        servo3 = hardwareMap.servo.get("servo3");


        cMotor1.setPower(0);
        cMotor2.setPower(0);
        cMotor3.setPower(0);
        cMotor4.setPower(0);

        servo1.setPosition(0);
        servo2.setPosition(0);
        servo3.setPosition(0);

        cMotor1.setDirection(DcMotor.Direction.REVERSE);
        cMotor2.setDirection(DcMotor.Direction.FORWARD);
        cMotor3.setDirection(DcMotor.Direction.REVERSE);
        cMotor4.setDirection(DcMotor.Direction.REVERSE);

        cMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        cMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        cMotor3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        cMotor4.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }


    public void loop() {
        float cmotor1power = gamepad1.left_stick_y;
        double cmotor2power = gamepad1.left_stick_y/1.3;
        double cmotor3power = gamepad1.left_stick_y/1.8;

        cMotor1.setPower(cmotor1power);
        cMotor2.setPower(cmotor2power);
        cMotor3.setPower(cmotor3power);

        if (gamepad1.x) {
            cMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            cMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            cMotor3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            cMotor4.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            float turn90 = cmotorTickCount/4;

            cMotor1.setTargetPosition((int)turn90);

            cMotor1.setPower(0.8);
            cMotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        }

        }


    public void stop() {
        cMotor1.setPower(0);
        cMotor2.setPower(0);
        cMotor3.setPower(0);
        cMotor4.setPower(0);
    }
}
