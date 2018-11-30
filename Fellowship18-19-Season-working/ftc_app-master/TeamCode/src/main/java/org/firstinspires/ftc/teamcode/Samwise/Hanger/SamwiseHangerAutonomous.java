package org.firstinspires.ftc.teamcode.Samwise.Hanger;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="SamwiseHangerHardware", group="Exercises")
public class SamwiseHangerAutonomous extends LinearOpMode {

    public DcMotor hangermotor1 = null;

    public Servo hangerservo1 = null;

    public void init(HardwareMap ahwMap) {
        hangermotor1 = ahwMap.dcMotor.get("hangermotor_1");
        hangerservo1 = ahwMap.servo.get("hangerservo_1");
    }

    public void runOpMode() {

        hangermotor1.setDirection(DcMotor.Direction.REVERSE);

        hangerservo1.setPosition(0.0);

        hangermotor1.setPower(0.0);

        telemetry.addData("Mode", "*windows startup noise* Ready to Start");
        telemetry.update();

        waitForStart(); {

            telemetry.addData("Mode", "Boop bop Starting");
            telemetry.update();

            hangermotor1.setPower(0.00000000025);

            sleep(1000000000);

            hangermotor1.setPower(0.0);

            hangerservo1.setPosition(1);

            telemetry.addData("Mode", "Beep beep Done");
            telemetry.update();
        }
    }
}