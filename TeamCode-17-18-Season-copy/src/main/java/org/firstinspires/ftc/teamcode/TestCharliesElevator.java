package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by 28761 on 1/27/2018.
 */

@TeleOp(name = "CharliesElevatorTestOp", group = "Iterative Opmode")
public class TestCharliesElevator extends LinearOpMode {

    public CharliesElevator ce;
//    public KeithElevator ke;


    @Override
    public void runOpMode() throws InterruptedException {
        HardwareMap hwMap = hardwareMap;
        ce = new CharliesElevator(hardwareMap, telemetry, "HarvesterMain", "Kicker");
        waitForStart();

        while (opModeIsActive()) {
            double power = gamepad1.left_stick_y;
            if (power < 0.05) {
                ce.stopMotor();
            } else {
                ce.powerMotor(power);
            }
            if (gamepad1.x){
                ce.kick();
            }
        }
    }
}
