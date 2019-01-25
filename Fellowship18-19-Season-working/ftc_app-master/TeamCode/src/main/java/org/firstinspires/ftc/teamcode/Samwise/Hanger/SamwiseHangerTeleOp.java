package org.firstinspires.ftc.teamcode.Samwise.Hanger;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Samwise.Conceptual.SamwiseRobot;

@TeleOp(name = "HangerArmTeleOp v2")
public class SamwiseHangerTeleOp extends OpMode {

    //HardwareMap hw;
    //Telemetry t;
    SamwiseHanger swHang;

    //these bad bois control the button pressing of the gamepad
    //boolean uopressed, downpressed, Lpressed, Rpressed, lofgpressed, rightpressed;

    //a constructor that takes in hardwaremap and telemetry for later uses
    /*public SamwiseHangerTeleOp(HardwareMap hardwareMap, Telemetry telemetry) {
        hw = hardwareMap;
        t = telemetry;
    }*/

    //allows for use of the class SamwiseHanger
    public void init() {
        //hw = hardwareMap;
        System.out.println("==> Hanger init ...");
        /*SamwiseRobot sr = new SamwiseRobot(hardwareMap, telemetry);
        movieboi = sr.hanger();*/
        swHang = new SamwiseHanger();
        swHang.init(hardwareMap, telemetry);
    }

    public void loop() {

        swHang.move(gamepad1.right_stick_y);

        if (gamepad1.left_bumper) {
            System.out.println("==> Hanger unhooking ...");
            //telemetry.addData("Mode", "Unhooking");
            swHang.unHook();
            //telemetry.addLine("Unhooked");
            //telemetry.update();
            return;
        }
        else if (gamepad1.right_bumper) {
            System.out.println("==> Hanger hooking ...");
            //telemetry.addData("Mode", "hooking");
            swHang.Hook();
            //telemetry.addLine("hooked");
            //telemetry.update();
            return;
        }
    }
}