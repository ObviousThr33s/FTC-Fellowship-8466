package org.firstinspires.ftc.teamcode.Samwise.TeleOp;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Michael's Servo Test2")
public class MichaelServoTestCodeTeleop extends LinearOpMode {
    MichaelServoTestCodeHardware testTeleOp = new MichaelServoTestCodeHardware();
    double testServo1Pos = testTeleOp.testServo1_HOME;
    final double testServo1_SPEED = 0.01;

    @Override

    public void runOpMode() {
        testTeleOp.init(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.a) {
                testServo1Pos += testServo1_SPEED;
            } else {
                testServo1Pos = 0;
            }
            if (gamepad1.b) {
                testServo1Pos -= testServo1_SPEED;
            } else
                testServo1Pos = 0;
            testTeleOp.testServo1.setPosition(testServo1Pos);

            telemetry.addData("test", "%.2f", testServo1Pos);

            sleep(40);
        }
    }
}
