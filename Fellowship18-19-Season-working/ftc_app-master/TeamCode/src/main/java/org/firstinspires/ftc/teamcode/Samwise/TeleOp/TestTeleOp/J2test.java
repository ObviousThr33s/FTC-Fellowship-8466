package org.firstinspires.ftc.teamcode.Samwise.TeleOp.TestTeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp (name = "J2test")
public class J2test extends OpMode{
    private DcMotor j21 = null;
    private DcMotor j22 = null;

    public void init() {j21 = hardwareMap.dcMotor.get("1J2");
    j22 = hardwareMap.dcMotor.get("2J2");

    j21.setPower(0);
    j22.setPower(0);
    }
    public void loop() {
        if(gamepad1.left_stick_y>0.1) {
            j21.setPower(0.2);
            j22.setPower(0.2);
        } if (gamepad1.left_stick_y < -0.1) {
            j21.setPower(-0.2);
            j22.setPower(-0.2);
        }
    }
}
