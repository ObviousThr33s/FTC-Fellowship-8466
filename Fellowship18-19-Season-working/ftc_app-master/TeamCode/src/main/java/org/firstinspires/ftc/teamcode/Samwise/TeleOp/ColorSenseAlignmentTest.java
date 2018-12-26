package org.firstinspires.ftc.teamcode.Samwise.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.Samwise.DriveTrain.SamwiseFlushToLine;

@TeleOp(name="ColorSenseAlignmentTest", group="test")
public class ColorSenseAlignmentTest extends OpMode {

    SamwiseFlushToLine ftl;

    @Override
    public void init() {
        ftl = new SamwiseFlushToLine(telemetry, hardwareMap, "c1", "c2");
    }

    @Override
    public void loop() {
        ftl.colorDebug();
    }
}