package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by 28761 on 1/27/2018.
 */

public class RecenterCarriage extends LinearOpMode {
    public KeithCarriage kc;
//    public KeithElevator ke;


    @Override
    public void runOpMode() throws InterruptedException {
        HardwareMap hwMap = hardwareMap;
        kc = new KeithCarriage(hardwareMap, telemetry, "slideMotor", "flipMotor", "lServo", "rServo");
        waitForStart();

        while (opModeIsActive()) {
            double power = gamepad1.left_stick_y;
            if (power < 0.05) {
                kc.powerSlideMotor(0.0);
            } else {
                kc.powerSlideMotor(power);
            }
        }
    }
}
