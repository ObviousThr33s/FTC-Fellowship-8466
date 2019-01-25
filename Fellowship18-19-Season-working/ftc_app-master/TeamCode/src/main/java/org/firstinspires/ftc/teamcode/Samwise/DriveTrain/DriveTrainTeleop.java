package org.firstinspires.ftc.teamcode.Samwise.DriveTrain;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp (name = "DriveTeleOp 2.2.12v")
public class DriveTrainTeleop extends OpMode {

    public DcMotor leftdrive = null;
    public DcMotor rightdrive = null;

    private double powerlevel = 1.0;

    public void init() {
        leftdrive = hardwareMap.dcMotor.get("left_drive");
        rightdrive = hardwareMap.dcMotor.get("right_drive");

        leftdrive.setPower(0);
        rightdrive.setPower(0);

        leftdrive.setDirection(DcMotor.Direction.REVERSE);
        rightdrive.setDirection(DcMotor.Direction.FORWARD);
    }

    public void loop() {
        if(gamepad1.b) {
            powerlevel = 0.5;
            System.out.println("50% power");
        }
        else if(gamepad1.a) {
            powerlevel = .7;
            System.out.println("70% power");
        }
        else if(gamepad1.x) {
            powerlevel = 1;
            System.out.println("max power");
        }

        if(Math.abs(gamepad1.left_stick_x) <= Math.abs(gamepad1.left_stick_y)) {
            float MotorPower = gamepad1.left_stick_y;

            leftdrive.setPower(MotorPower * powerlevel);
            rightdrive.setPower(MotorPower * powerlevel);
            System.out.println("==> moving ...");
        }
        else if(Math.abs(gamepad1.left_stick_x) > Math.abs(gamepad1.left_stick_y)) {
            float TurnMotorPower = gamepad1.left_stick_x;

            leftdrive.setPower(TurnMotorPower * powerlevel);
            rightdrive.setPower(-1 * TurnMotorPower * powerlevel);
            System.out.println("==> turning ...");
        }
    }
}