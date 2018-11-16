package org.firstinspires.ftc.teamcode.Samwise.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp (name = "EncoderTestOfTheMichael")
public class MichaelMotorEncoderTest extends OpMode {

    public DcMotor testMotorEncoder1 = null;

    static final float TestEncoderTIckCount = 1120;

    public void init() {
        testMotorEncoder1 = hardwareMap.dcMotor.get("testMotor1");

        testMotorEncoder1.setPower(0);

        testMotorEncoder1.setDirection(DcMotor.Direction.REVERSE);


    }

    public void loop() {
        if (gamepad1.x) {
            testMotorEncoder1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            float testrightturn90 = TestEncoderTIckCount/4;
            testMotorEncoder1.setTargetPosition((int)testrightturn90);
            testMotorEncoder1.setPower(0.8);
            testMotorEncoder1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }

}
