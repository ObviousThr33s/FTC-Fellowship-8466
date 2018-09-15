package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by 28761 on 1/27/2018.
 */

public class CharliesElevator {
    DcMotor eleMotor = null;
    Servo eleServo = null;
    Telemetry tl = null;

    CharliesElevator(HardwareMap hwmap, Telemetry tele, String eleMotorLabel, String eleServoLabel) {
        eleMotor = hwmap.get(DcMotor.class, eleMotorLabel);
        eleServo = hwmap.get(Servo.class, eleServoLabel);
        tl = tele;
    }

    public void powerMotor(double power) {
        eleMotor.setPower(power);
    }

    public void stopMotor() {
        eleMotor.setPower(0.0);
    }

    public void kick() {
        eleServo.setPosition(0.4);
        sleep(500);
        eleServo.setPosition(0.0);
    }

    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
