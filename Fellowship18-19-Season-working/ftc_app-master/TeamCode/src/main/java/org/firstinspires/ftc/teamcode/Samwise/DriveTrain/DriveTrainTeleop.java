package org.firstinspires.ftc.teamcode.Samwise.DriveTrain;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp (name = "DriveTeleOp 1.2.12v")
public class DriveTrainTeleop extends OpMode {

    public DcMotor leftDrive = null;
    public DcMotor rightDrive = null;

    public void init() {
        leftDrive = hardwareMap.dcMotor.get("left_drive");
        rightDrive = hardwareMap.dcMotor.get("right_drive");

        leftDrive.setPower(0);
        rightDrive.setPower(0);

        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);

    }

    public void loop() {
       float leftMotorPower = gamepad1.left_stick_y;
       float rightMotorPower = gamepad1.right_stick_y;

       leftDrive.setPower(leftMotorPower);
       rightDrive.setPower(rightMotorPower);

    }
}
