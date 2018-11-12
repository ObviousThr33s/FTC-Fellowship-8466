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


    public void init() {
        cMotor1 = hardwareMap.dcMotor.get("motor1");
        cMotor2 = hardwareMap.dcMotor.get("motor2");
        cMotor3 = hardwareMap.dcMotor.get("motor3");
        cMotor4 = hardwareMap.dcMotor.get("motor4");

        cMotor1.setPower(0);
        cMotor2.setPower(0);
        cMotor3.setPower(0);
        cMotor4.setPower(0);

        cMotor1.setDirection(DcMotor.Direction.REVERSE);
        cMotor2.setDirection(DcMotor.Direction.REVERSE);
        cMotor3.setDirection(DcMotor.Direction.REVERSE);
        cMotor4.setDirection(DcMotor.Direction.REVERSE);

    }


    public void loop() {
        float motor1power = gamepad1.left_stick_x;
        float motor2power = gamepad1.left_stick_y/2;
        double motor3power = gamepad1.left_stick_y/1.2;


    }


    public void stop() {
        cMotor1.setPower(0);
        cMotor2.setPower(0);
        cMotor3.setPower(0);
        cMotor4.setPower(0);
    }
}
