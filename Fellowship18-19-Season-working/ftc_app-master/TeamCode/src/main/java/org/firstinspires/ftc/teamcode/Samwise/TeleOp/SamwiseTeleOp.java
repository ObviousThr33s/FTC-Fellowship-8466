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
import org.firstinspires.ftc.teamcode.Samwise.SamwiseArm.SamwiseSmart;

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
public class SamwiseTeleOp extends OpMode
{

    /* Declare OpMode members. */
    public SamwiseHanger swHang = new SamwiseHanger();

    //public DriveTrainTeleop swDTrain = new DriveTrainTeleop();
    public SamwiseSmart armStuff = null;

    public DcMotor leftdrive = null;
    public DcMotor rightdrive = null;
    private double powerlevel = 1.0;

    @Override
    public void init()
    {
        swHang.init(hardwareMap, telemetry);

        //swDTrain.init(hardwareMap, telemetry);
        armStuff = new SamwiseSmart(this.hardwareMap);

        leftdrive = hardwareMap.dcMotor.get("left_drive");
        rightdrive = hardwareMap.dcMotor.get("right_drive");

        leftdrive.setPower(0);
        rightdrive.setPower(0);

        leftdrive.setDirection(DcMotor.Direction.REVERSE);
        rightdrive.setDirection(DcMotor.Direction.FORWARD);
    }


    @Override
    public void init_loop()
    {
    }

    @Override
    public void start()
    {
    }

    /**
     * This is where our main teleop gamepad input and functions mapping go.
     */
    @Override
    public void loop()
    {
        /************************************** Gamepad #1 Mappings *************************************
         *                               Samwise Drive Train and Hanging                                *
         *                       (Please add related function mappings below)                           *
         ************************************************************************************************/

        //Hanger system
        //if the a button is pressed then is moves the hanger arm

        swHang.move(gamepad1.right_stick_y);

        if (gamepad1.left_bumper)
        {
            System.out.println("==> Hanger unhooking ...");
            //telemetry.addData("Mode", "Unhooking");
            swHang.unHook();
            return;
        }
        else if (gamepad1.right_bumper)
        {
            System.out.println("==> Hanger hooking ...");
            //telemetry.addData("Mode", "hooking");
            swHang.Hook();
            return;
        }

        if (gamepad1.dpad_down)
        {
            swHang.markerservo1.setPosition(.76);
        }
        if (gamepad1.y)
        {
            swHang.markerservo1.setPosition(.76);
        }

        //Drive Train
        if (gamepad1.b)
        {
            powerlevel = 0.5;
            System.out.println("50% power");
        }
        else if (gamepad1.a)
        {
            powerlevel = .7;
            System.out.println("70% power");
        }
        else if (gamepad1.x)
        {
            powerlevel = 1;
            System.out.println("max power");
        }

        if (Math.abs(gamepad1.left_stick_x) <= Math.abs(gamepad1.left_stick_y))
        {
            float MotorPower = gamepad1.left_stick_y;

            leftdrive.setPower(MotorPower * powerlevel);
            rightdrive.setPower(MotorPower * powerlevel);
            //System.out.println("==> moving ...");
        }
        if (Math.abs(gamepad1.left_stick_x) > Math.abs(gamepad1.left_stick_y))
        {
            float TurnMotorPower = gamepad1.left_stick_x;

            leftdrive.setPower(-1 * TurnMotorPower * powerlevel);
            rightdrive.setPower(TurnMotorPower * powerlevel);
            //System.out.println("==> turning ...");
        }


        /************************************** Gamepad #2 Mappings *************************************
         *                               Arm(J1, J2, J3) Position Transitions                           *
         *                       (Please add related function mappings below)                           *
         ************************************************************************************************/

        // to deposit position
        /* if (gamepad1.x)
        {
            armStuff.silverDropPoint();
        }

        if (gamepad1.y)
        {
            armStuff.goldDropPoint();
        }*/

        // to collection position
        //        if (gamepad1.b)
        //        {
        //            if (Math.abs(armStuff.getJ1CurrentPosition()) < 10 && Math.abs(armStuff.getJ2CurrentPosition()) < 10 && Math.abs(armStuff.getJ3CurrentPosition()) < 10)
        //            {
        //                armStuff.toCollectionPlane();
        //            }
        //            else
        //            {
        //                armStuff.toPreviousCollectionPosition();
        //            }
        //        }


        if (gamepad1.a)
        {
            //            armStuff.moveJ4Up();
            armStuff.savePreviousPosition();
//            armStuff.top();
        }

        if (gamepad1.b)
        {
            armStuff.moveJ4Down();
        }
        if (gamepad1.right_stick_y > 0.02)
        {
            this.armStuff.extendL1();
        }
        else
        {
            this.armStuff.stopExtendL1();
        }
        if (gamepad1.left_stick_y > 0.02)
        {
            this.armStuff.extendL2();
        }
        else
        {
            this.armStuff.stopExtendL2();
        }
//        if (gamepad1.right_stick_x > 0.05)
//        {
//            this.armStuff.driveJ1(0.3);
//        }
//        else
//        {
//            this.armStuff.driveJ1(0);
//        }


        /************************************** Gamepad #2 Mappings *************************************
         *                               Arm (J2, J3) Plane of Motion                                   *
         *                       (Please add related function mappings below)                           *
         ************************************************************************************************/


        /************************************** Gamepad #2 Mappings *************************************
         *                  Claws (J2, J3, J4, J5, J6) Collection & Deposit                             *
         *                       (Please add related function mappings below)                           *
         ************************************************************************************************/

        // Manual driveToCrater
        if (gamepad1.dpad_up || gamepad1.dpad_down || gamepad1.dpad_right || gamepad1.dpad_left)
        {
            armStuff.setManual(true);
        }
        //move J2 up
        if (gamepad1.dpad_right)
        {
            armStuff.driveJ2(true);
        }
        else
        {
            if (armStuff.isManual() && !gamepad1.dpad_left)
            {
                armStuff.stopJ2();
            }
        }

        //move J2 down
        if (gamepad1.dpad_left)
        {
            armStuff.driveJ2(false);
        }
        else
        {
            if (armStuff.isManual() && !gamepad1.dpad_right)
            {
                armStuff.stopJ2();
            }
        }

        //move J3 up
        if (gamepad1.dpad_up)
        {
            armStuff.driveJ3(true);
        }
        else
        {
            if (armStuff.isManual() && !gamepad1.dpad_down)
            {
                armStuff.stopJ3();
            }
        }

        //move J3 down
        if (gamepad1.dpad_down)
        {
            armStuff.driveJ3(false);
        }
        else
        {
            if (armStuff.isManual() && !gamepad1.dpad_up)
            {
                armStuff.stopJ3();
            }
        }

        // collection and deposit
        if (gamepad1.left_trigger > 0)
        {
            armStuff.collectMinerals();
        }

        if (gamepad1.right_trigger > 0)
        {
            armStuff.depositMinerals();
        }


        if (gamepad1.left_trigger == 0 && gamepad1.right_trigger == 0 && armStuff.isCollecting())
        {
            armStuff.stopCollecting();
            armStuff.setCollecting(false);
        }
    }
}