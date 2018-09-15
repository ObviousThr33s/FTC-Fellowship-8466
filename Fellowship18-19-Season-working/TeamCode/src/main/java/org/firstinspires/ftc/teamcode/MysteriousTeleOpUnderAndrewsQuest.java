package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by 28761 on 1/8/2018.
 */
@TeleOp(name = "MysteriousTeleOpUnderAndrewsQuest", group = "Iterative Opmode")
public class MysteriousTeleOpUnderAndrewsQuest extends LinearOpMode {
    //KeithRobot keith;
    public FishingRodSystem frs = null;

    @Override
    public void runOpMode() throws InterruptedException {
        HardwareMap hwMap = hardwareMap;
        //keith = new KeithRobot(hwMap, telemetry);
        //initialize Mecanum Driving System
        //ras = keith.GetRelicArmSubsystem();
        frs = new FishingRodSystem(hardwareMap, telemetry, "lowerReel", "upperReel", "claw", "rodMotor");
        waitForStart();

        while (opModeIsActive()) {

            double ly = gamepad1.left_stick_y;
            double ry = gamepad1.right_stick_y;
            telemetry.addLine(String.format("Left joystick: %f, Right joystick: %f", ly, ry));
            telemetry.update();
            frs.setLowerReelPower(ly);
            frs.setUpperReelPower(ry);
            if (gamepad1.x) {
                frs.setRodMotorPower(0.5);
            } else {
                frs.setRodMotorPower(0.0);
            }
            if (gamepad1.y) {
                frs.setRodMotorPower(-0.5);
            } else {
                frs.setRodMotorPower(0.0);
            }

//            double ly = -gamepad1.left_stick_y / 2;
//            telemetry.addLine(String.format("Left joystick: %.3f", ly));
//            frs.setRodMotorPower(ly);
//            telemetry.update();
        }
    }
}
