package org.firstinspires.ftc.teamcode.Samwise.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Samwise.DriveTrain.SamwiseColor;

@TeleOp(name="ColorSenseAlignmentTest", group="test")
public class ColorSenseAlignmentTest extends OpMode {

    SamwiseColor ftl;

    @Override
    public void init() {
        ftl.init(hardwareMap, telemetry);
    }

    @Override
    public void loop() {
        ftl.colorDebug();
    }

    //This comment is a big mood
}