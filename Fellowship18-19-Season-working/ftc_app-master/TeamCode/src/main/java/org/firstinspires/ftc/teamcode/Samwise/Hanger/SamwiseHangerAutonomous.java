package org.firstinspires.ftc.teamcode.Samwise.Hanger;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "HangerAuto", group = "Exercises")
public class SamwiseHangerAutonomous extends LinearOpMode {

    public DcMotor hangermotor1 = null;

    public Servo hangerservo1 = null;

    SamwiseHangerHardware movieboi;

    public void init(HardwareMap ahwMap) {
        movieboi = new SamwiseHangerHardware(hardwareMap, telemetry);
    }

    @Override
    public void runOpMode() {
        init();


        waitForStart();

            telemetry.addData("movingdown", -1);
            telemetry.update();
        movieboi.move(-1);

            telemetry.addData("unhooking", "yeet");
            telemetry.update();
        movieboi.unHook();

    }
}