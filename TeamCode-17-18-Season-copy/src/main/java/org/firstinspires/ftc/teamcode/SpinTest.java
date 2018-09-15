package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by 28761 on 11/11/2017.
 */

@TeleOp(name="SpinTest", group="Iterative Opmode")
public class SpinTest extends LinearOpMode {

    KeithRobot keith;
    public MecanumDS ds = null;

    @Override
    public void runOpMode() {
        //initialize hardwareMap
        HardwareMap hwMap = hardwareMap;
        telemetry.addLine("DcMotors Set...");

        keith = new KeithRobot(hwMap, telemetry);
        //initialize Mecanum Driving System
        ds = (MecanumDS)(keith.GetDriveSystem());
        waitForStart();

        double movePower = 0.1;
        double moveDistance = 1000;
        double spinPower = 0.1;
        double spinValue = 90;

        double selectionTime = 0;

        while (true) {
            if (gamepad1.y) {
                break;
            }
            if (gamepad1.dpad_up) {
                if (gamepad1.dpad_right) {
                    ds.Move(movePower, -45, 0, moveDistance, 0);
                } else if (gamepad1.dpad_left) {
                    ds.Move(movePower, 45, 0, moveDistance, 0);
                } else {
                    ds.Move(movePower, 0, 0, moveDistance, 0);
                }
            }
            if (gamepad1.dpad_down) {
                if (gamepad1.dpad_right) {
                    ds.Move(movePower, -135, 0, moveDistance, 0);
                } else if (gamepad1.dpad_left) {
                    ds.Move(movePower, 135, 0, moveDistance, 0);
                } else {
                    ds.Move(movePower, 180, 0, moveDistance, 0);
                }
            }
            if (gamepad1.dpad_left) {
                if (gamepad1.dpad_up) {
                    ds.Move(movePower, 45, 0, moveDistance, 0);
                } else if (gamepad1.dpad_down) {
                    ds.Move(movePower, 135, 0, moveDistance, 0);
                } else {
                    ds.Move(movePower, 90, 0, moveDistance, 0);
                }
            }
            if (gamepad1.dpad_right) {
                if (gamepad1.dpad_up) {
                    ds.Move(movePower, -45, 0, moveDistance, 0);
                } else if (gamepad1.dpad_down) {
                    ds.Move(movePower, -135, 0, moveDistance, 0);
                } else {
                    ds.Move(movePower, -90, 0, moveDistance, 0);
                }
            }
            if (gamepad1.left_bumper) {
                ds.Move(spinPower, 0, spinValue, 0, 0);
            }
            if (gamepad1.right_bumper) {
                ds.Move(spinPower, 0, -spinValue, 0, 0);
            }

            if ((System.currentTimeMillis() - selectionTime) >= 300) {
                boolean selection = false;

                // deal with the claw opening/closing
                if (gamepad2.right_bumper) {
                    selection = true;
                    spinPower += 0.01;
                    movePower += 0.01;
                    telemetry.addData("", "Motor power: %.2f, Spin power: %.2f", movePower, spinPower);
                    telemetry.update();
                }
                if (gamepad2.left_bumper) {
                    selection = true;
                    spinPower -= 0.01;
                    movePower -= 0.01;
                    telemetry.addData("", "Motor power: %.2f, Spin power: %.2f", movePower, spinPower);
                    telemetry.update();
                }

                if (gamepad2.x) {
                    selection = true;
                    moveDistance -= 10;
                    telemetry.addData("", "Move distance: %.2f", moveDistance);
                    telemetry.update();
                }
                if (gamepad2.b) {
                    selection = true;
                    moveDistance += 10;
                    telemetry.addData("", "Move distance: %.2f", moveDistance);
                    telemetry.update();
                }

                if (selection) {
                    selectionTime = System.currentTimeMillis();
                }
            }
        }

        /*
        while (true) {
            boolean exit = false;
            while (!gamepad1.x) {
                if (gamepad1.y) {
                    sleep(200);
                    exit = true;
                    break;
                }
            }
            if (exit) {
                break;
            }
            ds.Move(spinPower, 0, -spinValue, 0, 0);
        }
        */

        /*
        while (!gamepad1.x) {}

        ds.Move(spinPower, 0, -spinValue, 0, 0);

        while (!gamepad1.x) {}

        ds.Move(spinPower, 0, -spinValue, 0, 0);

        while (!gamepad1.x) {}

        ds.Move(spinPower, 0, -spinValue, 0, 0);

        while (!gamepad1.x) {}

        ds.Move(spinPower, 0, -spinValue, 0, 0);

        while (!gamepad1.x) {}

        ds.Move(spinPower, 0, -spinValue, 0, 0);

        while (!gamepad1.x) {}

        ds.Move(spinPower, 0, -spinValue, 0, 0);

        while (!gamepad1.x) {}

        ds.Move(spinPower, 0, spinValue, 0, 0);

        while (!gamepad1.x) {}

        ds.Move(spinPower, 0, spinValue, 0, 0);

        while (!gamepad1.x) {}

        ds.Move(spinPower, 0, spinValue, 0, 0);

        while (!gamepad1.x) {}

        ds.Move(spinPower, 0, spinValue, 0, 0);

        while (!gamepad1.x) {}

        ds.Move(spinPower, 0, spinValue, 0, 0);

        while (!gamepad1.x) {}

        ds.Move(spinPower, 0, spinValue, 0, 0);

        while (!gamepad1.x) {}
        */
    }
}
