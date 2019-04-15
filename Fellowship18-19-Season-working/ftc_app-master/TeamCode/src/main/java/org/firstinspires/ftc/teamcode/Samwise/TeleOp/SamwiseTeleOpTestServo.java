package org.firstinspires.ftc.teamcode.Samwise.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Samwise.SamwiseArm.SamwiseGenius;

import java.util.concurrent.TimeUnit;

@TeleOp(name = "Samwise Teleop - Servo Test", group = "Samwise")
//@Disabled

public class SamwiseTeleOpTestServo extends SamwiseTeleOp3 {
    private SamwiseGenius armStuff;

    private ElapsedTime runTime;

    private boolean j4Toggle = true;

    @Override
    public void init() {
        super.init();
        armStuff = new SamwiseGenius(hardwareMap);
        runTime = new ElapsedTime();
    }

    @Override
    public void loop() {
        super.loop();

        if (gamepad2.x) {
            if (j4Toggle) {
                armStuff.moveJ4Up();
                j4Toggle = false;
            } else {
                armStuff.moveJ4Down();
                j4Toggle = true;
            }
        }
        if (gamepad2.y) {
            armStuff.stopJ4();
        }
    }
}
