package org.firstinspires.ftc.teamcode.Samwise.TeleOp;



import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp(name = "Michael's motor testt879568756745342513")

public class MichaelMotorTest extends OpMode {

    private DcMotor testmotor1 = null;
    private DcMotor testmotor2 = null;
    public void init() {
        testmotor1 = hardwareMap.dcMotor.get("motor_1");
        testmotor2 = hardwareMap.dcMotor.get("motor_2");
        testmotor1.setDirection(DcMotor.Direction.REVERSE);
        testmotor2.setDirection(DcMotor.Direction.REVERSE);

        testmotor1.setPower(0);
        testmotor2.setPower(0);

    }
    public void loop() {
        float motor1power = gamepad1.left_stick_y;
        float motor2power = gamepad1.left_stick_y/2;

        testmotor1.setPower(motor1power);
        testmotor2.setPower(motor2power);
    }
    public void stop() {
        testmotor1.setPower(0);
        testmotor2.setPower(0);
    }
}
