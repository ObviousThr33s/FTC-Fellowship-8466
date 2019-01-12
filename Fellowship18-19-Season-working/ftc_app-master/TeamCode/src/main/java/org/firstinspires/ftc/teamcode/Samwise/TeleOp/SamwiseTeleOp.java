/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.Samwise.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Samwise.DriveTrain.DriveTrainTeleop;
import org.firstinspires.ftc.teamcode.Samwise.Hanger.SamwiseHanger;
import org.firstinspires.ftc.teamcode.Samwise.Hanger.SamwiseHangerTeleOp;
import org.firstinspires.ftc.teamcode.Samwise.SamwiseArm.SamwiseArm;

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
@TeleOp(name = "Samwise: Teleop Tank", group = "Samwise")
//@Disabled
public class SamwiseTeleOp extends OpMode {

    /* Declare OpMode members. */
    public SamwiseHanger swHang = new SamwiseHanger();

    //public DriveTrainTeleop swDTrain = new DriveTrainTeleop();
    //public SamwiseArm swArm;

    public DcMotor leftDrive = null;
    public DcMotor rightDrive = null;
    private double powerlevel = 1.0;

    @Override
    public void init() {
        swHang.init(hardwareMap, telemetry);

        //swDTrain.init(hardwareMap, telemetry);
        //swArm = new SamwiseArm(this.hardwareMap);
        leftDrive = hardwareMap.dcMotor.get("left_drive");
        rightDrive = hardwareMap.dcMotor.get("right_drive");

        leftDrive.setPower(0);
        rightDrive.setPower(0);

        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
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

        //Hanger system
        //if the a button is pressed then is moves the hanger arm
        if (gamepad1.dpad_up) {
            System.out.println("==> Hanger moving up ...");
            //telemetry.addData("Mode", "Moving up...");
            swHang.move(1);
            //telemetry.addData("Mode", "Stopped");
            //telemetry.update();
            return;
        } else if (gamepad1.dpad_down) {
            System.out.println("==> Hanger moving down ...");
            //telemetry.addData("Mode", "moving down ");
            swHang.move(-1);
            //telemetry.addLine("stopped");
            //telemetry.update();
            return;
        } else {
            swHang.move(0);
        }

        if (gamepad1.left_bumper) {
            System.out.println("==> Hanger unhooking ...");
            //telemetry.addData("Mode", "Unhooking");
            swHang.unHook();
            //telemetry.addLine("Unhooked");
            //telemetry.update();
            return;
        } else if (gamepad1.right_bumper) {
            System.out.println("==> Hanger hooking ...");
            //telemetry.addData("Mode", "hooking");
            swHang.Hook();
            //telemetry.addLine("hooked");
            //telemetry.update();
            return;
        }

        //Drive Train
        if(gamepad1.a) {
            powerlevel = 0.5;
        }
        if(gamepad1.b) {
            powerlevel = .25;
        }
        else if(gamepad1.x) {
            powerlevel = 1;
        }

        float leftMotorPower = gamepad1.left_stick_y;
        float rightMotorPower = gamepad1.right_stick_y;

        leftDrive.setPower(leftMotorPower*powerlevel);
        rightDrive.setPower(rightMotorPower*powerlevel);


//        /************************************** Gamepad #2 Mappings *************************************
//         *                               Arm(J1, J2, J3) Position Transitions                           *
//         *                       (Please add related function mappings below)                           *
//         ************************************************************************************************/
//        // Manual driveToCrater
//        if (gamepad1.dpad_up || gamepad1.dpad_down || gamepad1.dpad_right || gamepad1.dpad_left)
//        {
//            swArm.setManual(true);
//        }
//        //move J2 up
//        if (gamepad1.dpad_right)
//        {
//            swArm.driveJ2(true);
//        }
//        else
//        {
//            if (swArm.getIsManual() && !gamepad1.dpad_left)
//            {
//                swArm.stopJ2();
//            }
//        }
//
//        //move J2 down
//        if (gamepad1.dpad_left)
//        {
//            swArm.driveJ2(false);
//        }
//        else
//        {
//            if (swArm.getIsManual() && !gamepad1.dpad_right)
//            {
//                swArm.stopJ2();
//            }
//        }
//
//        //move J3 up
//        if (gamepad1.dpad_up)
//        {
//            swArm.driveJ3(true);
//        }
//        else
//        {
//            if (swArm.getIsManual() && !gamepad1.dpad_down)
//            {
//                swArm.stopJ3();
//            }
//        }
//
//        //move J3 down
//        if (gamepad1.dpad_down)
//        {
//            swArm.driveJ3(false);
//        }
//        else
//        {
//            if (swArm.getIsManual() && !gamepad1.dpad_up)
//            {
//                swArm.stopJ3();
//            }
//        }
//
//        // to deposit position
//        if (gamepad1.x)
//        {
//            swArm.silverDropPoint();
//        }
//
//        if (gamepad1.y)
//        {
//            swArm.goldDropPoint();
//        }
//
//        // to collection position
//        if (gamepad1.b)
//        {
//            if (/*Math.abs(armStuff.getJ1CurrentPosition()) < 10  && */Math.abs(swArm.getJ2CurrentPosition()) < 10 && Math.abs(swArm.getJ3CurrentPosition()) < 10)
//            {
//                swArm.toCollectionPlane();
//            }
//            else
//            {
//                swArm.toPreviousCollectionPosition();
//            }
//        }
//
//        // to initial position
//        if (gamepad1.a)
//        {
//            swArm.toInitialPosition();
//        }
//
//        /************************************** Gamepad #2 Mappings *************************************
//         *                               Arm (J2, J3) Plane of Motion                                   *
//         *                       (Please add related function mappings below)                           *
//         ************************************************************************************************/
//
//
//        /************************************** Gamepad #2 Mappings *************************************
//         *                  Claws (J2, J3, J4, J5, J6) Collection & Deposit                             *
//         *                       (Please add related function mappings below)                           *
//         ************************************************************************************************/
//
//        //collection and deposit
//        if (gamepad1.left_trigger > 0)
//        {
//            swArm.collectMinerals();
//        }
//
//        if (gamepad1.right_trigger > 0)
//        {
//            swArm.depositMinerals();
//        }
//
//        if (gamepad1.left_trigger == 0 && gamepad1.right_trigger == 0)
//        {
//            swArm.stopServo();
//        }
    }

    @Override
    public void stop() {
    }
}
