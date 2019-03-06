package org.firstinspires.ftc.teamcode.Samwise.Hanger;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Samwise.Conceptual.SamwiseRobot;

@Autonomous(name = "HangerAuto", group = "Exercises")
@Disabled
public class SamwiseHangerAutonomous extends LinearOpMode {


    SamwiseHanger movieboi;

    public void init(HardwareMap ahwMap) {
        movieboi = new SamwiseHanger();
        movieboi.init(ahwMap, telemetry);
    }

    @Override
    public void runOpMode() {
        init(hardwareMap);

        waitForStart();
        //movieboi.encoderDrive(this, 0.8, -0.6,1);
        //movieboi.hangerservo2.setPosition(0.5);
        movieboi.hangermotor1.setPower(-1);
        sleep(300);
        movieboi.hangermotor1.setPower(0);

        movieboi.encoderDrive(this, 0.4, 20.9, 4);
        System.out.print("unhooking");


        movieboi.unHook();
        System.out.print("rehookinng");
        sleep(2000);

        movieboi.encoderDrive(this, 0.6, 20.9, 4);

        movieboi.Hook();
        //movieboi.hangerservo2.setPosition(0);
        sleep(1000);
    }
}