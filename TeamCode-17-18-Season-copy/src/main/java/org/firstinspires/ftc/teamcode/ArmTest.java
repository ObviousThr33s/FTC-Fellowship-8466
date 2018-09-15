package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by 28761 on 1/6/2018.
 */
@TeleOp(name="ArmTest", group="Iterative Opmode")
public class ArmTest extends LinearOpMode {

    //KeithRobot keith;
    public RelicArmSubsystem ras = null;

    @Override
    public void runOpMode() throws InterruptedException {
        HardwareMap hwMap = hardwareMap;
        //keith = new KeithRobot(hwMap, telemetry);
        //initialize Mecanum Driving System
        //ras = keith.GetRelicArmSubsystem();
        ras = new FishingRodSystem(hardwareMap, telemetry, "lowerReel", "upperReel", "claw", "rodMotor");
        waitForStart();
        /*
        int count = 0;
        while (count < 8) {
            if (gamepad1.x) {
                telemetry.addLine("test CR servo " + count);
                telemetry.update();
                ras.testCRServo();
                count++;

            }
            if (gamepad1.a) {
                telemetry.addLine("test motor " + count);
                telemetry.update();
                ras.testMotor();
                count++;
            }
            if (gamepad1.b) {
                telemetry.addLine("test DB servo " + count);
                telemetry.update();
                ras.testDBServo();
                count++;
            }
        }
        */
    }
}
