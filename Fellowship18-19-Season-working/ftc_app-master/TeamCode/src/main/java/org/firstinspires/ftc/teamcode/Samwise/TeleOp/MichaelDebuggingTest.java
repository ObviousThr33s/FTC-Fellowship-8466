package org.firstinspires.ftc.teamcode.Samwise.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp (name = "Michael's debugger test", group="tests")
public class MichaelDebuggingTest extends OpMode {

    public void init() {

    }

    public void loop() {
        float TestNumber2 = gamepad1.left_stick_y;
        System.out.println(TestNumber2);
    }
}
