package org.firstinspires.ftc.teamcode.Samwise.Hanger;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Samwise.Conceptual.SamwiseRobot;

@TeleOp(name = "HangerArmTeleOp v2")
public class SamwiseHangerTeleOp extends OpMode {

    //HardwareMap hw;
    //Telemetry t;
    SamwiseHanger movieboi;

    //these bad bois control the button pressing of the gamepad
    boolean uopressed, downpressed, Lpressed, Rpressed, lofgpressed, rightpressed;

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
        movieboi = new SamwiseHanger();
        movieboi.init(hardwareMap, telemetry);
    }

    public void loop() {

        downpressed = gamepad1.dpad_down;
        uopressed = gamepad1.dpad_up;
        lofgpressed = gamepad1.dpad_left;
        rightpressed = gamepad1.dpad_right;
        Lpressed = gamepad1.left_bumper; //switched to bumper controls
        Rpressed = gamepad1.right_bumper;

        //if the a button is pressed then is moves the hanger arm
        if(uopressed) {
            System.out.println("==> Hanger moving up ...");
            telemetry.addData("Mode", "Moving up...");
            movieboi.move(1);
            telemetry.addData("Mode", "Stopped");
            telemetry.update();
            return;
        }
        else if(downpressed) {
            System.out.println("==> Hanger moving down ...");
            telemetry.addData("Mode", "moving down ");
            movieboi.move(-1);
            telemetry.addLine("stopped");
            telemetry.update();
            return;
        }
        else {
            movieboi.move(0);
        }

        if(Lpressed) {
            System.out.println("==> Hanger unhooking ...");
            telemetry.addData("Mode", "Unhooking");
            movieboi.unHook();
            telemetry.addLine("Unhooked");
            telemetry.update();
            return;
        }
        else if(Rpressed) {
            System.out.println("==> Hanger hooking ...");
            telemetry.addData("Mode", "hooking");
            movieboi.Hook();
            telemetry.addLine("hooked");
            telemetry.update();
            return;
        }
        /*if(lofgpressed) {
            movieboi.hangerservo2.setPosition(0.5);
        }
        else if(rightpressed) {
            movieboi.hangerservo2.setPosition(0);
        }*/
    }
}