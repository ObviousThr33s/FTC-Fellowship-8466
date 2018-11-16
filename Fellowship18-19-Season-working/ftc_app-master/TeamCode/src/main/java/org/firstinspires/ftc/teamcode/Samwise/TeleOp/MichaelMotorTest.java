package org.firstinspires.ftc.teamcode.Samwise.TeleOp;



import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp(name = "Michael's motor test8795")

public class MichaelMotorTest extends OpMode {

    private DcMotor testmotor1 = null;
    private DcMotor testmotor2 = null;
    private DcMotor testmotor3 = null;
    private DcMotor testmotor4 = null;

    public void init() {
        testmotor1 = hardwareMap.dcMotor.get("motor_1");
        testmotor2 = hardwareMap.dcMotor.get("motor_2");
        testmotor3 = hardwareMap.dcMotor.get("motor_3");
        testmotor4 = hardwareMap.dcMotor.get("motor_4");

        testmotor1.setDirection(DcMotor.Direction.REVERSE);
        testmotor2.setDirection(DcMotor.Direction.REVERSE);
        testmotor3.setDirection(DcMotor.Direction.REVERSE);
        testmotor4.setDirection(DcMotor.Direction.REVERSE);

        testmotor1.setPower(0);
        testmotor2.setPower(0);
        testmotor3.setPower(0);
        testmotor4.setPower(0);

    }
    public void loop() {
        double motor1power = gamepad1.right_stick_y/1.2;
        float motor2power = gamepad1.right_stick_y/2;
        float motor3power = gamepad1.left_stick_y/2;
        float motor4power = gamepad1.left_stick_y/2;


        testmotor1.setPower(motor1power);
        testmotor2.setPower(motor2power);
        testmotor3.setPower(motor3power);
        testmotor4.setPower(motor4power);
    }
    public void stop() {
        testmotor1.setPower(0);
        testmotor2.setPower(0);
        testmotor3.setPower(0);
        testmotor4.setPower(0);
    }
}
