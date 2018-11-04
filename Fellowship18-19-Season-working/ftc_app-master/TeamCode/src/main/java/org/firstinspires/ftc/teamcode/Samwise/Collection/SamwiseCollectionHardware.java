package org.firstinspires.ftc.teamcode.Samwise.Collection;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.AbstractPhysical.MotorsAndServos;
import org.firstinspires.ftc.teamcode.Samwise.SamwiseMotors.SamwiseMotors;

public class SamwiseCollectionHardware {

    public Servo servo3 = null;
    public Servo servo4 = null;
    public Servo servo5 = null;
    public Servo servo6 = null;

    public final static double servo3_HOME = 0.5;
    public final static double servo3_MIN_RANGE = 0.2;
    public final static double servo3_MAX_RANGE = 0.7;
    public final static double servo4_HOME = 0.5;
    public final static double servo4_MIN_RANGE = 0.2;
    public final static double servo4_MAX_RANGE = 0.7;
    public final static double servo5_HOME = 0.5;
    public final static double servo5_MIN_RANGE = 0.2;
    public final static double servo5_MAX_RANGE = 0.7;
    public final static double servo6_HOME = 0.5;
    public final static double servo6_MIN_RANGE = 0.2;
    public final static double servo6_MAX_RANGE = 0.7;

    public void init(HardwareMap ahwMap) {
        //Define names on the phone
        servo3 = ahwMap.servo.get("servo_3");
        servo4 = ahwMap.servo.get("servo_4");
        servo5 = ahwMap.servo.get("servo_5");
        servo6 = ahwMap.servo.get("servo_6");

        servo3.setPosition(servo3_HOME);
        servo4.setPosition(servo4_HOME);
        servo5.setPosition(servo5_HOME);
        servo6.setPosition(servo6_HOME);
    }

}