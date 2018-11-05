package org.firstinspires.ftc.teamcode.Samwise.TeleOp;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class MichaelServoTestCodeHardware {
    public Servo testServo1 = null;

    public static double testServo1_HOME = 0.5;

    public void init(HardwareMap ahwMap) {
        testServo1 = ahwMap.servo.get("testservo_1");

        testServo1.setPosition(testServo1_HOME);
    }

}
