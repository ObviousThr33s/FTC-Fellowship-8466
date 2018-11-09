package org.firstinspires.ftc.teamcode.Samwise.TeleOp;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Michael's Servo Test212l")
public class MichaelServoTestCodeTeleop extends LinearOpMode {
    MichaelServoTestCodeHardware testTeleOp = new MichaelServoTestCodeHardware();
    double testServo1Pos = testTeleOp.testServo1_HOME;
    final double testServo1_SPEED = 0.06;

    @Override

    public void runOpMode() {
        testTeleOp.init(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.x) {
                testServo1Pos = 0.3;
            }else {
                testServo1Pos = 0.7;
            }



            testTeleOp.testServo1.setPosition(testServo1Pos);

            telemetry.addData("test", "%.2f", testServo1Pos);

        }
    }
}
