package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by 28761 on 11/11/2017.
 */

@TeleOp(name="ServoTest", group="Iterative Opmode")
public class ServoTest extends LinearOpMode {

    ServoSubsystemTest servoSystem = null;

    @Override
    public void runOpMode() {
        //initialize hardwareMap
        HardwareMap hwMap = hardwareMap;

        servoSystem = new ServoSubsystemTest(hwMap, telemetry);
        String servoPartialStr = "Partial";
        String servoContinuousStr = "Continuous";
        boolean servoPartialSelected = true;
        Servo currentServo = servoSystem.GetServoPartial();
        double servoPosition = 0;

        currentServo.scaleRange(0, 0.5);

        waitForStart();
        telemetry.addData("", "Servo(A): %s", servoPartialSelected ? servoPartialStr : servoContinuousStr);
        telemetry.addLine("==============================================");
        telemetry.update();
        while (true) {
            boolean pressed = false;
            if (gamepad1.y) {
                break;
            }
            if (gamepad1.a) {
                pressed = true;
                if (servoPartialSelected) {
                    currentServo.setPosition(0.0);
                } else {
                    currentServo.setPosition(0.5);
                }
                servoPartialSelected = !servoPartialSelected;
                currentServo = servoPartialSelected ? servoSystem.GetServoPartial() : servoSystem.GetServoContinuous();
                if (servoPartialSelected) {
                    servoPosition = 0.0;
                } else {
                    servoPosition = 0.5;
                }
            }
            if (gamepad1.dpad_up) {
                pressed = true;
                servoPosition += 0.1;
                if (servoPosition > 1) {
                    servoPosition = 1;
                }
            }
            if (gamepad1.dpad_down) {
                pressed = true;
                servoPosition -= 0.1;
                if (servoPosition < 0) {
                    servoPosition = 0;
                }
            }
            if (pressed) {
                telemetry.addData("", "Servo(A): %s", servoPartialSelected ? servoPartialStr : servoContinuousStr);
                telemetry.addLine("==============================================");
                telemetry.addData("", "Servo value : %.3f", servoPosition);
                currentServo.setPosition(servoPosition);
                telemetry.addData("", "Actual value: %.3f", currentServo.getPosition());
                telemetry.update();
            }
            if (pressed) {
                sleep(200);
            }
        }
    }
}
