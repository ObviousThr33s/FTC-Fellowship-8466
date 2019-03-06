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
@TeleOp(name = "Samwise: Teleop Tank 3", group = "Samwise")
//@Disabled
public class SamwiseTeleOp3 extends OctoArmTeleOpTest
{

    /* Declare OpMode members. */
    public SamwiseHanger swHang = new SamwiseHanger();

    //public DriveTrainTeleop swDTrain = new DriveTrainTeleop();
    //public OctoSamwiseGenius armStuff = null;

    //private boolean manual = true;
    //private boolean isHoldingJ2 = false;
    //private boolean isHoldingJ3 = false;
    //private boolean collect = false;

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
    public void init()
    {
        swHang.init(hardwareMap, telemetry);

        //swDTrain.init(hardwareMap, telemetry);
        //armStuff = new OctoSamwiseGenius(this.hardwareMap);
        super.init();

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

        swHang.move(gamepad2.right_stick_y);

        /*if (gamepad2.b) {
            System.out.println("==> Hanger unhooking ...");
            //telemetry.addData("Mode", "Unhooking");
            swHang.unHook();
            return;
        } else if (gamepad2.a) {
            System.out.println("==> Hanger hooking ...");
            //telemetry.addData("Mode", "hooking");
            swHang.Hook();
            return;
        }*/

        if (gamepad2.right_bumper)
        {
            swHang.hookUpdate();
        }

        if (gamepad2.left_bumper)
        {
            swHang.markerservo1.setPosition(.76);
        }

        //Drive Train
        if (gamepad2.b)
        {
            powerlevel = 0.5;
            System.out.println("50% power");
        }
        else if (gamepad2.a)
        {
            powerlevel = .7;
            System.out.println("70% power");
        }
        else if (gamepad2.x)
        {
            powerlevel = 1;
            System.out.println("max power");
        }

        // to avoid driving with very small powers not visible) even with no button pressed
        if (Math.abs(gamepad2.left_stick_x) > 0.1 || Math.abs(gamepad2.left_stick_y) > 0.1)
        {
            if (Math.abs(gamepad2.left_stick_x) <= Math.abs(gamepad2.left_stick_y))
            {

                float MotorPower = gamepad2.left_stick_y;

                leftdrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                rightdrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                leftdrive.setPower(MotorPower * powerlevel);
                rightdrive.setPower(MotorPower * powerlevel);
                //System.out.println("==> moving ...");
            }
            if (Math.abs(gamepad2.left_stick_x) > Math.abs(gamepad2.left_stick_y))
            {
                float TurnMotorPower = gamepad2.left_stick_x;

                leftdrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                rightdrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                leftdrive.setPower(TurnMotorPower * powerlevel);
                rightdrive.setPower(-1 * TurnMotorPower * powerlevel);
                //System.out.println("==> turning ...");
            }
        }
        else
        {
            leftdrive.setPower(0);
            rightdrive.setPower(0);
        }

        // arm control: delegate to parent OctoArmTest

        super.loop();

    }
}
