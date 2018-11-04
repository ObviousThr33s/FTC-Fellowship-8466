package org.firstinspires.ftc.teamcode.Samwise.Collection;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "CollectionArmTeleOp")
public class SamwiseCollectionTeleOp extends LinearOpMode {

    SamwiseCollectionHardware arm = new SamwiseCollectionHardware();

    double servo1Position = arm.servo1_HOME;
    double servo2Position = arm.servo2_HOME;
    double servo3Position = arm.servo3_HOME;
    double servo4Position = arm.servo4_HOME;
    double servo5Position = arm.servo5_HOME;
    double servo6Position = arm.servo6_HOME;
    double servo7Position = arm.servo7_HOME;

    final double servo1_SPEED = 0.01;
    final double servo2_SPEED = 0.01;
    final double servo3_SPEED = 0.01;
    final double servo4_SPEED = 0.01;
    final double servo5_SPEED = 0.01;
    final double servo6_SPEED = 0.01;
    final double servo7_SPEED = 0.01;


    @Override
    public void runOpMode() {

        arm.init(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a)
                //The math for the servos go here. The individual servo speeds must be
                // different for each servo when the whole arm moves FORWARD
                servo1Position += servo1_SPEED;// Don't worry this is just a temporary PLACEHOLDER

            else if (gamepad1.y)
                //For when the arm goes BACKWARDS
                servo4Position += servo4_SPEED; //Another temp PLACEHOLDER for testing

            servo1Position = Range.clip(servo1Position, arm.servo1_MIN_RANGE, arm.servo1_MAX_RANGE);
            servo2Position = Range.clip(servo2Position, arm.servo2_MIN_RANGE, arm.servo2_MAX_RANGE);
            servo3Position = Range.clip(servo3Position, arm.servo3_MIN_RANGE, arm.servo3_MAX_RANGE);
            servo4Position = Range.clip(servo4Position, arm.servo4_MIN_RANGE, arm.servo4_MAX_RANGE);
            servo1Position = Range.clip(servo5Position, arm.servo5_MIN_RANGE, arm.servo5_MAX_RANGE);
            servo2Position = Range.clip(servo6Position, arm.servo6_MIN_RANGE, arm.servo6_MAX_RANGE);
            servo3Position = Range.clip(servo7Position, arm.servo7_MIN_RANGE, arm.servo7_MAX_RANGE);

            arm.servo1.setPosition(servo1Position);
            arm.servo2.setPosition(servo2Position);
            arm.servo3.setPosition(servo3Position);
            arm.servo4.setPosition(servo4Position);
            arm.servo5.setPosition(servo5Position);
            arm.servo6.setPosition(servo6Position);
            arm.servo7.setPosition(servo7Position);

            telemetry.addData("left", "%.2f", servo1Position);
            //add more telemetries later as needed

            sleep(40);
        }
    }
}
