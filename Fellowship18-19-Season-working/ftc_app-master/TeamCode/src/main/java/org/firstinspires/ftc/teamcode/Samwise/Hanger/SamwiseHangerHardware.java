package org.firstinspires.ftc.teamcode.Samwise.Hanger;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class SamwiseHangerHardware {

    public Telemetry telemetry;

    public DcMotor hangermotor1 = null;
    public Servo hangerservo1 = null;

    int hook = 1;
    int slide = 1;

    public SamwiseHangerHardware(HardwareMap hw, Telemetry t) {
        hangermotor1 = hw.dcMotor.get("hangermotor_1");
        hangerservo1 = hw.servo.get("hangerservo_1");

        telemetry = t;

        telemetry.addData("Mode", "Boron-Oxygen-Oxygen-Phosphorus Beryllium-Phosphorus init time");
        telemetry.update();

        hangerservo1.setPosition(1);

        hangermotor1.setDirection(DcMotor.Direction.REVERSE);
    }

    public void unHook() {
        switch(hook) {
            default: {
                hangerservo1.setPosition(0.0);
                hook = 2;
                telemetry.addLine("Stopped  1");
            }
            case 2: {
                hangerservo1.setPosition(1);
                hook = 1;
                telemetry.addLine("Stopped   2");
            }
        }
    }
    public void moveDown() {
        switch (slide) {
            default: {
                hangermotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                hangermotor1.setTargetPosition(1);

                hangermotor1.setPower(0.25);

                hangermotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                slide = 2;
                telemetry.addLine("Stopped   1");
            }
            case 2: {
                hangermotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                hangermotor1.setTargetPosition(0);

                hangermotor1.setPower(-0.25);

                hangermotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                slide = 1;
                telemetry.addLine("Stopped   2");
            }
        }
    }
}