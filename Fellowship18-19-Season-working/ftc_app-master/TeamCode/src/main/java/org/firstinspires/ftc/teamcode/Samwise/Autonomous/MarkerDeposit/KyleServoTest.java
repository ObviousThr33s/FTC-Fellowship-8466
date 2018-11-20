package org.firstinspires.ftc.teamcode.Samwise.Autonomous.MarkerDeposit;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Samwise.TeleOp.MichaelServoTestCodeHardware;

@TeleOp(name = "kyle's Servo Test")
public class KyleServoTest extends LinearOpMode {
    SamwiseMarkerDeposit md = new SamwiseMarkerDeposit();
    final double testServo1_SPEED = 0.04;

    @Override
    public void runOpMode() {

        md.init(hardwareMap);

        waitForStart();

        //while (opModeIsActive()) {
            md.move(1);
                telemetry.addData("move", "1");
                telemetry.update();
            sleep(2000);
            md.move(0);
                telemetry.addData("move", "0");
                telemetry.update();
            sleep(2000);
            md.move(0.5);
                telemetry.addData("move", "0");
                telemetry.update();
            sleep(2000);

        //}
    }
}
