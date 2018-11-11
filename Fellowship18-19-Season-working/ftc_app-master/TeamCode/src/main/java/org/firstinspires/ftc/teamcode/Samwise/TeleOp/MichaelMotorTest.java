package org.firstinspires.ftc.teamcode.Samwise.TeleOp;



import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name = "Michael's motor test")
public class MichaelMotorTest extends OpMode {

    public DcMotor motor1 = null;
    float motor1power = gamepad1.left_stick_y;

    public void init() {
        motor1 = hardwareMap.dcMotor.get("motor_1");
        motor1.setDirection(DcMotor.Direction.REVERSE);

        motor1.setPower(0);


    }
    public void loop() {
        motor1.setPower(motor1power);
    }
}
