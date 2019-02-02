package org.firstinspires.ftc.teamcode.Samwise.TeleOp.TestTeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Samwise.SamwiseArm.SamwiseArm;

@TeleOp(name = "ArmVelocityTest", group="tests")
@Disabled
public class ArmVelocityTest extends OpMode {

    SamwiseArm arm;

    @Override
    public void init() {
        arm = new SamwiseArm(hardwareMap);
    }

    @Override
    public void loop() {
        telemetry.addData("J2 Pos ",arm.getJ2CurrentPosition());
        telemetry.addData("J3 Pos",arm.getJ3CurrentPosition());
    }
}
