package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by 28761 on 1/29/2018.
 */
@TeleOp(name = "JwlTesSSSSSSSSSSSSSSSSSSSSt", group = "Iterative Opmode")
public class TestJwlKnocker extends LinearOpMode {

    KeithJewlKnocker kjk;

    @Override
    public void runOpMode() throws InterruptedException {
        kjk = new KeithJewlKnocker(hardwareMap, "JewlBase", "JewlKnocker", "colorSensor", telemetry);
        kjk.setBasePosition(0.18);
        kjk.setKnockerPosition(0.99);
        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.x) {
                kjk.setDownPosition();
                sleep(500);
                kjk.knockLeft();
                sleep(500);
                kjk.setUpPosition();
            }
            if (gamepad1.y){
                kjk.setDownPosition();
                sleep(500);
                kjk.knockRight();
                sleep(500);
                kjk.setUpPosition();
            }
        }
    }
}
