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

import org.firstinspires.ftc.teamcode.Samwise.Hanger.SamwiseHanger;
import org.firstinspires.ftc.teamcode.Samwise.SamwiseArm.OctoSamwiseGenius;

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
 *                  8. Aim, hang up, and say                                                       *
 ****************************************************************************************************/
@TeleOp(name = "Samwise: Teleop Tank 2", group = "Samwise")
//@Disabled
public class SamwiseTeleOp2 extends OpMode {

    /* Declare OpMode members. */
    public SamwiseHanger swHang = new SamwiseHanger();

    //public DriveTrainTeleop swDTrain = new DriveTrainTeleop();
    public OctoSamwiseGenius armStuff = null;

    private boolean manual = true;
    private boolean isHoldingJ2 = false;
    private boolean isHoldingJ3 = false;
    private boolean collect = false;

    private static final int J1_MAX_TICKS = 0;
    private static final int J1_MIN_TICKS = -2887;
    private static final int J1_LEFT_PHONE = -1001;
    private static final int J1_RIGHT_PHONE = -2716;
    private static final int J2_MIN_TICKS = Integer.MIN_VALUE;
    private static final int J2_MAX_TICKS = Integer.MAX_VALUE;
    private static final int J2_MIN_PHONE_TICKS = /*916*/ 800;
    private static final int J3_MAX_TICKS = Integer.MAX_VALUE;
    private static final int J3_MIN_TICKS = -1938;

    public DcMotor leftdrive = null;
    public DcMotor rightdrive = null;
    private double powerlevel = 1.0;


    @Override
    public void init() {
        swHang.init(hardwareMap, telemetry);

        //swDTrain.init(hardwareMap, telemetry);
        armStuff = new OctoSamwiseGenius(this.hardwareMap);

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

        //Hanger system
        //if the a button is pressed then is moves the hanger arm

        swHang.move(gamepad2.right_stick_y);

        if (gamepad2.left_bumper) {
            System.out.println("==> Hanger unhooking ...");
            //telemetry.addData("Mode", "Unhooking");
            swHang.unHook();
            return;
        } else if (gamepad2.right_bumper) {
            System.out.println("==> Hanger hooking ...");
            //telemetry.addData("Mode", "hooking");
            swHang.Hook();
            return;
        }

        if (gamepad2.dpad_down) {
            swHang.markerservo1.setPosition(.76);
        }
        if (gamepad2.y) {
            swHang.markerservo1.setPosition(.76);
        }

        //Drive Train
        if (gamepad2.b) {
            powerlevel = 0.5;
            System.out.println("50% power");
        } else if (gamepad2.a) {
            powerlevel = .7;
            System.out.println("70% power");
        } else if (gamepad2.x) {
            powerlevel = 1;
            System.out.println("max power");
        }

        if (Math.abs(gamepad2.left_stick_x) <= Math.abs(gamepad2.left_stick_y)) {
            float MotorPower = gamepad2.left_stick_y;

            leftdrive.setPower(MotorPower * powerlevel);
            rightdrive.setPower(MotorPower * powerlevel);
            //System.out.println("==> moving ...");
        }
        if (Math.abs(gamepad2.left_stick_x) > Math.abs(gamepad2.left_stick_y)) {
            float TurnMotorPower = gamepad2.left_stick_x;

            leftdrive.setPower(TurnMotorPower * powerlevel);
            rightdrive.setPower(-1 * TurnMotorPower * powerlevel);
            //System.out.println("==> turning ...");
        }


        /*******************;
         * new arm
         *
         *******************/
        telemetry.addData("E2 counts", armStuff.e2Count);
        telemetry.addData("J4 counts", armStuff.J4Count);
        telemetry.addData("J1 encoder ticks", armStuff.getJ1CurrentPosition());
        telemetry.addData("J2 encoder ticks", armStuff.getJ2CurrentPosition());
        telemetry.addData("J3 encoder ticks", armStuff.getJ3CurrentPosition());
        telemetry.addData("E1 encoder ticks", armStuff.getE1CurrentPosition());
        telemetry.update();

        if (gamepad2.left_trigger > 0.1)
        {
            armStuff.extendL1();
        }

        if (gamepad2.right_trigger > 0.1)
        {
            armStuff.retractL1();
        }

        if (!gamepad2.dpad_up && !gamepad2.dpad_down)
        {
            armStuff.stopExtendL1();
        }

        if (gamepad1.dpad_up)
        {
            manual = true;
        }

        if (gamepad1.dpad_down)
        {
            manual = false;
        }

        if (gamepad1.dpad_left)
        {
            armStuff.stop();

            if (!isHoldingJ2)
            {
                armStuff.holdPositionJ2(true);
                isHoldingJ2 = true;
            }
            else
            {
                armStuff.holdPositionJ2(false);
            }

            if (!isHoldingJ3)
            {
                armStuff.holdPositionJ3(true);
                isHoldingJ3 = true;
            }
            else
            {
                armStuff.holdPositionJ3(false);
            }
        }


        if (gamepad1.left_stick_x > 0.6 /*&& (armStuff.getJ1CurrentPosition() < J1_MAX_TICKS && (armStuff.getJ1CurrentPosition() < J1_RIGHT_PHONE || !armStuff.isPhoneJ2()))*/)
        {
            armStuff.driveJ1(true);
        }
        else if (gamepad1.left_stick_x < -0.6 /*&& (armStuff.getJ1CurrentPosition() > J1_MIN_TICKS && (armStuff.getJ1CurrentPosition() > J1_LEFT_PHONE || !armStuff.isPhoneJ2()))*/)
        {
            armStuff.driveJ1(false);
        }
        else
        {
            armStuff.stopJ1();
        }

        // to deposit position
        if (gamepad1.x)
        {
            armStuff.silverDropPoint();
            //                    isHoldingJ2 = false;
            //                    isHoldingJ3 = false;
            //                    armStuff.silverDropPointManual();
        }

        if (gamepad1.y)
        {
            armStuff.goldDropPoint();
            //                    isHoldingJ2 = false;
            //                    isHoldingJ3 = false;

            //                    armStuff.goldDropPointManual();
        }
        //
        //        if (!gamepad1.y && !gamepad1.dpad_right)
        //        {
        //            armStuff.stopExtendL1();
        //        }

        //        if (gamepad1.x)
        //        {
        //            armStuff.toLander();
        //            isHoldingJ2 = false;
        //            isHoldingJ3 = false;
        //        }

        if (gamepad1.a)
        {
            armStuff.toInitialPosition();
            //            isHoldingJ2 = false;
            //            isHoldingJ3 = false;
            //            armStuff.toInitialPositionManual();
        }

        // to collection position
        if (gamepad1.b)
        {
            //            armStuff.toPreviousPositionManual();
            if (Math.abs(armStuff.getJ1CurrentPosition()) < 10 && Math.abs(armStuff.getJ2CurrentPosition()) < 10 && Math.abs(armStuff.getJ3CurrentPosition()) < 10)
            {
                armStuff.toCollectionPlane();
                isHoldingJ2 = false;
                isHoldingJ3 = false;
            }
            else
            {
                armStuff.toPreviousPosition();
                isHoldingJ2 = false;
                isHoldingJ3 = false;
            }
        }

        if (gamepad2.dpad_right)
        {
            armStuff.extendL2();
        }

        if (gamepad2.dpad_left)
        {
            armStuff.retractL2();
        }

        if (!gamepad2.a && !gamepad2.b)
        {
            armStuff.stopExtendL2();
        }

        // collection and deposit
        if (gamepad1.left_trigger > 0.1)
        {
            armStuff.depositMinerals();
        }

        if (gamepad1.right_trigger > 0.1)
        {
            armStuff.collectMinerals();
        }

        if (gamepad1.left_trigger < 0.1 && gamepad1.right_trigger < 0.1)
        {
            armStuff.stopCollecting();
        }

        if (manual)
        {
            if (gamepad1.right_stick_y > 0.4)
            {
                armStuff.driveJ3(false);
            }
            else if (gamepad1.right_stick_y < -0.4)
            {
                armStuff.driveJ3(true);
            }
            else
            {
                armStuff.stopJ3();
            }

            if (gamepad1.left_stick_y > 0.4)
            {
                armStuff.driveJ2(false);
            }
            else if (gamepad1.left_stick_y < -0.4)
            {
                armStuff.driveJ2(true);
            }
            else
            {
                armStuff.stopJ2();
            }

            if (gamepad1.left_bumper)
            {
                armStuff.moveJ4Down();
            }

            if (gamepad1.right_bumper)
            {
                armStuff.moveJ4Up();
            }

            if (!gamepad1.left_bumper && !gamepad1.right_bumper)
            {
                armStuff.stopJ4();
            }
        }
        else
        {
            armStuff.setcurrent(gamepad1.left_bumper);
            armStuff.PlaneOfMotion(gamepad1.left_stick_y);
        }


    }
}
