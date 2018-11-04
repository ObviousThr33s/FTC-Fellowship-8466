package org.firstinspires.ftc.teamcode.Samwise.Collection;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "CollectionArmTeleOp")
public class SamwiseCollectionTeleOp extends LinearOpMode {

    SamwiseCollectionHardware arm = new SamwiseCollectionHardware();
    double servo3Position = arm.servo3_HOME;
    double servo4Position = arm.servo4_HOME;
    double servo5Position = arm.servo5_HOME;
    double servo6Position = arm.servo6_HOME;
    final double servo3_SPEED = 0.01;       // I just did it for servo 3 for most of the stuff so i will do the others later

    @Override
    public void runOpMode() {

        arm.init(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a)
                //The math for the servos go here. The individual servo speeds must be
                // different for each servo when the whole arm moves FORWARD
                servo3Position = 0.6;// Don't worry this is just a temporary PLACEHOLDER

            else if (gamepad1.y)
                //For when the arm goes BACKWARDS
                servo4Position = 0.6; //Another temp PLACEHOLDER for testing

            servo3Position = Range.clip(servo3Position, arm.servo3_MIN_RANGE, arm.servo3_MAX_RANGE);
            servo4Position = Range.clip(servo4Position, arm.servo4_MIN_RANGE, arm.servo4_MAX_RANGE);
            servo5Position = Range.clip(servo5Position, arm.servo5_MIN_RANGE, arm.servo5_MAX_RANGE);
            servo6Position = Range.clip(servo6Position, arm.servo6_MIN_RANGE, arm.servo6_MAX_RANGE);

            arm.servo3.setPosition(servo3Position);

            telemetry.addData("left", "%.2f", servo3Position);
            //add more telemetries later as needed

            sleep(40;)
        }
    }
}
