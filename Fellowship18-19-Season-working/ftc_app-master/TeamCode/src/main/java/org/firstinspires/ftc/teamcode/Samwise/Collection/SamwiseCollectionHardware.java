package org.firstinspires.ftc.teamcode.Samwise.Collection;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.AbstractPhysical.MotorsAndServos;
import org.firstinspires.ftc.teamcode.Samwise.SamwiseMotors.SamwiseMotors;

public class SamwiseCollectionHardware {

    public Servo servo1 = null; //shoulder hori.
    public Servo servo2 = null; //shoulder vert.
    public Servo servo3 = null; //elbow
    public Servo servo4 = null; //wrist
    public Servo servo5 = null; //hand
    public Servo servo6 = null; //spring 1
    public Servo servo7 = null; //spring 2

    public final static double servo1_HOME = 0.5;
    public final static double servo1_MIN_RANGE = 0.2;
    public final static double servo1_MAX_RANGE = 0.7;
    public final static double servo2_HOME = 0.5;
    public final static double servo2_MIN_RANGE = 0.2;
    public final static double servo2_MAX_RANGE = 0.7;
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
    public final static double servo7_HOME = 0.5;
    public final static double servo7_MIN_RANGE = 0.2;
    public final static double servo7_MAX_RANGE = 0.7;

    public void init(HardwareMap ahwMap) {
        //Define names on the phone
        servo1 = ahwMap.servo.get("servo_1");
        servo2 = ahwMap.servo.get("servo_2");
        servo3 = ahwMap.servo.get("servo_3");
        servo4 = ahwMap.servo.get("servo_4");
        servo5 = ahwMap.servo.get("servo_5");
        servo6 = ahwMap.servo.get("servo_6");
        servo7 = ahwMap.servo.get("servo_7");

        servo1.setPosition(servo1_HOME);
        servo2.setPosition(servo2_HOME);
        servo3.setPosition(servo3_HOME);
        servo4.setPosition(servo4_HOME);
        servo5.setPosition(servo5_HOME);
        servo6.setPosition(servo6_HOME);
        servo7.setPosition(servo7_HOME);
    }

}