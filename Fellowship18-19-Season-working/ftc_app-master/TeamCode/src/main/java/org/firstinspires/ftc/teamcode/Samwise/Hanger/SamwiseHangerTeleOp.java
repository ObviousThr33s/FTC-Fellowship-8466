package org.firstinspires.ftc.teamcode.Samwise.Hanger;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name = "HangerArmTeleOp")
public class SamwiseHangerTeleOp extends OpMode {

    HardwareMap hw;
    Telemetry t;
    SamwiseHangerHardware movieboi;

    //these bad bois control the button pressing of the gamepad
    boolean apressed, xpressed;

    public SamwiseHangerTeleOp(HardwareMap hardwareMap, Telemetry telemetry) {
        hw = hardwareMap;
        t = telemetry;
    }

    public void init() {
        movieboi = new SamwiseHangerHardware(hw, t);
    }

    public void loop() {

        apressed = gamepad1.a;
        xpressed = gamepad1.x;

        //if the a button is pressed then is moves the hanger arm
        if(apressed) {
            telemetry.addLine("Moving...");
            movieboi.moveDown();
            telemetry.addLine("Stopped");
        }

        if(xpressed) {
            telemetry.addLine("Unhooking");
            movieboi.unHook();
            telemetry.addLine("Unhooked");
        }
        telemetry.update();
    }
}