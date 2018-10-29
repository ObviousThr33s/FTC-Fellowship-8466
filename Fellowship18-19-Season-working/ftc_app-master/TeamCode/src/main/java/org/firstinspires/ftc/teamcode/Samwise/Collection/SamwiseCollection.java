package org.firstinspires.ftc.teamcode.Samwise.Collection;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.AbstractPhysical.MotorsAndServos;
import org.firstinspires.ftc.teamcode.Samwise.SamwiseMotors.SamwiseMotors;

public class SamwiseCollection extends SamwiseMotors {

    public Servo servo3 = null;
    public Servo servo4 = null;
    public Servo servo5 = null;
    public Servo servo6 = null;
    public final static double servo3_HOME = 0.2;
    public final static double servo4_HOME = 0.2;
    public final static double servo5_HOME = 0.2;
    public final static double servo6_HOME = 0.2;

    public void init(HardwareMap ahwMap) {
        servo3 = ahwMap.servo.get("servo3");
        servo4 = ahwMap.servo.get("servo4");
        servo5 = ahwMap.servo.get("servo5");
        servo6 = ahwMap.servo.get("servo6");
    }

}