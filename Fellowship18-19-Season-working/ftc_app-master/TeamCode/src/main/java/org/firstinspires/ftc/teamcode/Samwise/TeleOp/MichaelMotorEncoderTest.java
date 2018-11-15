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

        


    }

    public void loop() {

    }

}
