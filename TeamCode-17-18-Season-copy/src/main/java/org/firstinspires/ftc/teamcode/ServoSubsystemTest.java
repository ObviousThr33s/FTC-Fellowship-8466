package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Joshua on 10/28/2017.
 */

public class ServoSubsystemTest {

    Telemetry tl = null;
    Servo servoPartial = null;
    Servo servoContinuous = null;

    ServoSubsystemTest(HardwareMap hwMap, Telemetry telemetry) {
        tl = telemetry;

        telemetry.addData("", "Attempting to acquire servos");
        telemetry.update();
        servoPartial = hwMap.servo.get("ServoPartial");
        servoPartial.setPosition(0.0);
        telemetry.addData("", "Got one");
        telemetry.update();
        servoContinuous = hwMap.servo.get("ServoContinuous");
        servoContinuous.setPosition(0.5);
        telemetry.addData("", "Servos acquired");
        telemetry.update();
    }

    public Servo GetServoPartial() {
        return servoPartial;
    }

    public Servo GetServoContinuous() {
        return servoContinuous;
    }

}
