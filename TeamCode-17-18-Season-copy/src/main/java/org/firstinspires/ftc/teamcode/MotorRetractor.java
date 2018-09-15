package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by 28761 on 1/13/2018.
 */

public class MotorRetractor extends LinearOpMode {

    public FishingRodSystem frs = null;

    @Override
    public void runOpMode() throws InterruptedException {
        HardwareMap hwMap = hardwareMap;

        frs = new FishingRodSystem(hardwareMap, telemetry, "lowerReel", "upperReel", "claw", "rodMotor");
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.x) {
                telemetry.addLine("retract");
                frs.setRodMotorPower(-1.0);
            }else {
                telemetry.addLine("stop");
                frs.setRodMotorPower(0.0);
            }
            telemetry.update();
        }
    }
}
