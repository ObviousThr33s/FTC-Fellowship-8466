package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by 28761 on 1/7/2018.
 */
@TeleOp(name="RelicOp", group="Iterative Opmode")
public class RelicOp extends LinearOpMode {

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
            if (gamepad1.dpad_right) {
                telemetry.addLine("arm extend");
                frs.extendOn(1.0);
            } else {
                frs.extendOff();
            }
            if (gamepad1.dpad_left) {
                telemetry.addLine("arm retract");
                frs.extendOn(-1.0);
            } else {
                frs.extendOff();
            }
            if (gamepad1.dpad_up) {
                telemetry.addLine("gripper down");
                frs.gripperMoveOn(1.0);
            } else {
                frs.gripperMoveOff();
            }
            if (gamepad1.dpad_down) {
                telemetry.addLine("gripper up");
                frs.gripperMoveOn(-1.0);
            } else {
                frs.gripperMoveOff();
            }
            if (gamepad1.a) {
                telemetry.addLine("grip/ungrip");
                frs.gripperToggle();
            }
            if (gamepad1.b) {
                telemetry.addLine("tilt/untilt");
                frs.tiltToggle();
            }
            if (gamepad1.left_bumper) {
                frs.incRatio(-0.05);
                telemetry.addLine(String.format("ratio: %f",FishingRodSystem.ratio));
            }
            if (gamepad1.right_bumper) {
                frs.incRatio(0.05);
                telemetry.addLine(String.format("ratio: %f",FishingRodSystem.ratio));
            }
            telemetry.update();
        }

    }

}
