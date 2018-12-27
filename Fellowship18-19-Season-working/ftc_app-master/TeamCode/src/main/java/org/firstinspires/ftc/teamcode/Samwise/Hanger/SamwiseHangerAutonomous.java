package org.firstinspires.ftc.teamcode.Samwise.Hanger;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Autonomous(name = "HangerAuto", group = "Exercises")
public class SamwiseHangerAutonomous extends LinearOpMode {


    SamwiseHanger movieboi;

    public void init(HardwareMap ahwMap) {

        movieboi = new SamwiseHanger();
        movieboi.init(hardwareMap, telemetry);
    }

    @Override
    public void runOpMode() {
        init(hardwareMap);

        waitForStart();
        //movieboi.encoderDrive(this, 0.8, -0.6,1);
        movieboi.hangerservo2.setPosition(0.5);
        movieboi.hangermotor1.setPower(-1);
        sleep(300);
        movieboi.hangermotor1.setPower(0);
            telemetry.addData("movingdown", -1);
            telemetry.update();
        movieboi.encoderDrive(this, 0.4, 20.9, 4);
        System.out.print("===^Lol");


        telemetry.addData("unhooking", "yeet");
        telemetry.update();
        movieboi.unHook();
        System.out.print("===^f to pay respects");
        sleep(2000);

        movieboi.encoderDrive(this, 0.6, 20.9, 4);

        movieboi.Hook();
        movieboi.hangerservo2.setPosition(0);
        sleep(1000);
    }

}