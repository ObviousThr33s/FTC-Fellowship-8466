package org.firstinspires.ftc.teamcode.Samwise.Hanger;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class SamwiseHangerHardware {

    public Telemetry telemetry;

    public DcMotor hangermotor1 = null;
    public Servo hangerservo1 = null;

    public SamwiseHangerHardware(HardwareMap hw, Telemetry t) {
        hangermotor1 = hw.dcMotor.get("hangermotor_1");
        hangerservo1 = hw.servo.get("hangerservo_1");

        telemetry = t;

        telemetry.addData("Mode", "Boron-Oxygen-Oxygen-Phosphorus Beryllium-Phosphorus init time");
        telemetry.update();

        //hangerservo1.setPosition(10);

        hangermotor1.setDirection(DcMotor.Direction.REVERSE);

        hangermotor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        hangermotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //hangermotor1.setTargetPosition(0);

        //hangermotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //hangermotor1.setPower(1);
    }

    public void unHook() {

        telemetry.addData("Mode", "hook 2");
        hangerservo1.setPosition(10);
    }
    public void Hook() {
        telemetry.addData("Mode", "hook 2");
        hangerservo1.setPosition(0);
    }

    public void move(double power) {
        this.hangermotor1.setPower(power);
    }

    public void movedown() {
        telemetry.addData("Mode", "slide 1");

        //hangermotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //hangermotor1.setTargetPosition(0);

        //hangermotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        hangermotor1.setPower(1);
    }
    public void moveup() {

        telemetry.addData("Mode", "slide 1");

        //hangermotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //hangermotor1.setTargetPosition(10);

        //hangermotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        hangermotor1.setPower(-1);
    }
}