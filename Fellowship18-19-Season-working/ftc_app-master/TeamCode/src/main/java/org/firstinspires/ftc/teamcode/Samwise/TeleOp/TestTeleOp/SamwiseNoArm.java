package org.firstinspires.ftc.teamcode.Samwise.TeleOp.TestTeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Samwise.Hanger.SamwiseHanger;

/****************************************************************************************************
 *                  Teleop Outline:                                                                 *
 *                  0. Drive to collection spot                                                     *
 *                  1. Arm initial to initial collection plane                                      *
 *                  2. Move along collection plane                                                  *
 *                  3. Collect (arm position and claw spins)                                        *
 *                  4. Deposit (arm position and claw spins)                                        *
 *                  5. Back to previous collection plane                                            *
 *                  6. Repeat 2-5                                                                   *
 *                  Start of last 30 seconds:                                                       *
 *                  7. Drive to lander                                                              *
 *                  8. Aim, hang up, and stay                                                       *
 ****************************************************************************************************/
@TeleOp(name = "Samwise no arm", group = "Samwise")
@Disabled
public class SamwiseNoArm extends OpMode {

    /* Declare OpMode members. */
    public SamwiseHanger swHang = new SamwiseHanger();

    public DcMotor leftdrive = null;
    public DcMotor rightdrive = null;
    private double powerlevel = 1.0;

    @Override
    public void init() {
        swHang.init(hardwareMap, telemetry);

        leftdrive = hardwareMap.dcMotor.get("left_drive");
        rightdrive = hardwareMap.dcMotor.get("right_drive");

        leftdrive.setPower(0);
        rightdrive.setPower(0);

        leftdrive.setDirection(DcMotor.Direction.REVERSE);
        rightdrive.setDirection(DcMotor.Direction.FORWARD);
    }


    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
    }

    /**
     * This is where our main teleop gamepad input and functions mapping go.
     */
    @Override
    public void loop() {
        /************************************** Gamepad #1 Mappings *************************************
         *                               Samwise Drive Train and Hanging                                *
         *                       (Please add related function mappings below)                           *
         ************************************************************************************************/

        //power settings to control the motors maxuimium power
        if(gamepad2.b) {
            powerlevel = 0.5;
            System.out.println("50% power");
        }
        else if(gamepad2.a) {
            powerlevel = .7;
            System.out.println("70% power");
        }
        else if(gamepad2.x) {
            powerlevel = 1;
            System.out.println("max power");
        }

        //Hanger system
        //if the a button is pressed then is moves the hanger arm
        swHang.move(gamepad2.right_stick_y);

        if (gamepad2.left_bumper) {
            System.out.println("==> Hanger unhooking ...");
            //telemetry.addData("Mode", "Unhooking");
            swHang.unHook();
            return;
        }
        else if (gamepad2.right_bumper) {
            System.out.println("==> Hanger hooking ...");
            //telemetry.addData("Mode", "hooking");
            swHang.Hook();
            return;
        }

        if(gamepad2.dpad_down) {
            swHang.markerservo1.setPosition(.76);
        }
        if(gamepad2.y) {
            swHang.markerservo1.setPosition(.76);
        }

        //Drive Train
        if(Math.abs(gamepad2.left_stick_x) <= Math.abs(gamepad2.left_stick_y)) {
            float MotorPower = gamepad2.left_stick_y;

            leftdrive.setPower(MotorPower * powerlevel);
            rightdrive.setPower(MotorPower * powerlevel);
            //System.out.println("==> moving ...");
        }
        else if(Math.abs(gamepad2.left_stick_x) > Math.abs(gamepad2.left_stick_y)) {
            float TurnMotorPower = gamepad2.left_stick_x;

            leftdrive.setPower(TurnMotorPower * powerlevel);
            rightdrive.setPower(-1 * TurnMotorPower * powerlevel);
            //System.out.println("==> turning ...");
        }
    }

    @Override
    public void stop() {
    }
}