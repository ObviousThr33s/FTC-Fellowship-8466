package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by 28761 on 11/11/2017.
 */

@TeleOp(name="JewlKnockerTest", group="Iterative Opmode")
public class JewlKnockerTest extends LinearOpMode {

    KeithJewlKnocker jewlKnockerSystem = null;

    @Override
    public void runOpMode() {
        //initialize hardwareMap
        HardwareMap hwMap = hardwareMap;

        KeithRobot keith = new KeithRobot(hardwareMap, telemetry);;
        jewlKnockerSystem = keith.GetJewelKnockerSubsystem();
        waitForStart();

        double basePosition = jewlKnockerSystem.getBasePosition();
        double knockerPosition = jewlKnockerSystem.getKnockerPosition();

        while (true) {
            boolean pressed = false;
            if (gamepad1.y) {
                break;
            }
            if (gamepad1.dpad_up) {
                pressed = true;
                jewlKnockerSystem.knockerUp();
            }
            if (gamepad1.dpad_down) {
                pressed = true;
                jewlKnockerSystem.knockerDown();
            }
            if (gamepad1.dpad_left) {
                pressed = true;
                jewlKnockerSystem.setDownPosition();
            }
            if (gamepad1.dpad_right) {
                pressed = true;
                jewlKnockerSystem.setUpPosition();
            }
            if (gamepad1.right_bumper) {
                pressed = true;
                jewlKnockerSystem.knockRight();
            }
            if (gamepad1.left_bumper) {
                pressed = true;
                jewlKnockerSystem.knockLeft();
            }

            if (gamepad2.dpad_up) {
                pressed = true;
                knockerPosition += 0.01;
                if (knockerPosition > 1) {
                    knockerPosition = 1;
                }
                jewlKnockerSystem.setKnockerPosition(knockerPosition);
            }
            if (gamepad2.dpad_down) {
                pressed = true;
                knockerPosition -= 0.01;
                if (knockerPosition < 0) {
                    knockerPosition = 0;
                }
                jewlKnockerSystem.setKnockerPosition(knockerPosition);
            }
            if (gamepad2.dpad_right) {
                pressed = true;
                basePosition += 0.01;
                if (basePosition > 1) {
                    basePosition = 1;
                }
                jewlKnockerSystem.setBasePosition(basePosition);
            }
            if (gamepad2.dpad_left) {
                pressed = true;
                basePosition -= 0.01;
                if (basePosition < 0) {
                    basePosition = 0;
                }
                jewlKnockerSystem.setBasePosition(basePosition);
            }

            if (pressed) {
                sleep(300);
            }

            telemetry.addData("", "BasePos: %.2f, KnockerPos: %.2f", basePosition, knockerPosition);
            telemetry.update();
        }

        //jewlKnockerSystem.knockerDown();
        //sleep(5000);
        //jewlKnockerSystem.knockerUp();
        //sleep(5000);
    }
}
