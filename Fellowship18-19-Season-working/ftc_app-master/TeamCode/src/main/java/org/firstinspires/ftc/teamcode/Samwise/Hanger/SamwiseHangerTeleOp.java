package org.firstinspires.ftc.teamcode.Samwise.Hanger;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "HangerArmTeleOp")
public class SamwiseHangerTeleOp extends OpMode {

    public DcMotor hangermotor1 = null;

    public Servo hangerservo1 = null;

    int hook = 1;
    int slide = 1;

    @Override
    public void init() {
        hangermotor1 = hardwareMap.dcMotor.get("hangermotor_1");
        hangerservo1 = hardwareMap.servo.get("hangerservo_1");

        telemetry.addData("Mode", "(Boron-Oxygen-Oxygen-Phosphorus Beryllium-Phosphorus) init time");
        telemetry.update();

        hangerservo1.setPosition(1);

        hangermotor1.setDirection(DcMotor.Direction.REVERSE);

    }

    @Override
    public void loop() {

        if(gamepad1.a) {
            switch(hook) {
                case 1: {
                    hangerservo1.setPosition(0.0);
                    hook++;
                    telemetry.addData("Mode", "Beep boop Done");
                    telemetry.update();
                }
                case 2: {
                    hangerservo1.setPosition(1);
                    hook--;
                    telemetry.addData("Mode", "Beep boop Done");
                    telemetry.update();
                }

            }
        }
        if(gamepad1.x) {
            switch(slide) {
                case 1: {
                    hangermotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                    hangermotor1.setTargetPosition(1);

                    hangermotor1.setPower(0.00000000025);

                    hangermotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    slide++;
                    telemetry.addData("Mode", "Beep boop Done");
                    telemetry.update();
                }
                case 2: {
                    hangermotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

                    hangermotor1.setTargetPosition(0);

                    hangermotor1.setPower(-0.00000000025);

                    hangermotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    slide--;
                    telemetry.addData("Mode", "Beep boop Done");
                    telemetry.update();
                }
            }
        }
    }
}