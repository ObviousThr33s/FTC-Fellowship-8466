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

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.KeithJewlKnocker;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 * <p>
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all iterative OpModes contain.
 * <p>
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name = "TeleOpModeTest", group = "Iterative Opmode")

public class TeleOpModeTest{ //extends OpMode {
    /*
    // Declare OpMode members.

    private ElapsedTime runtime = new ElapsedTime();

    MecanumDS ds = null;
    boolean normalMode = true;
    boolean allowEncoders = true;
    public double MaxPower = 1.0;
    public double MaxDomain = 7;
    public boolean linearMode = true;


    /*
     * Code to run ONCE when the driver hits INIT
     */
    /*
    @Override
    public void init() {
        ds = 0new MecanumDS(hardwareMap, telemetry, null, "Front Left", "Front Right", "Back Left", "Back Right");
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    /*
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    /*
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    /*
    @Override
    public void loop() {
        //KeithRobot.nav.posStart;
        if (getRuntime() == 200) {
            if (gamepad1.left_bumper) {
                resetStartTime();
                MaxPower = MaxPower - 0.1;
            } else if (gamepad1.right_bumper) {
                resetStartTime();
                MaxPower = MaxPower + 0.1;
            }
            resetStartTime();
        }
        if (MaxPower > 1) {
            MaxPower = 1;
        }
        if (MaxPower < 0) {
            MaxPower = 0;
        }
        if (gamepad1.a) {
            linearMode = true;
        }
        if (gamepad1.b) {
            linearMode = false;
        }
        if (gamepad1.y) {
            normalMode = !normalMode;
        }
        if (gamepad1.x) {
            allowEncoders = !allowEncoders;
        }
        String Mode = "Lin";
        if (!linearMode) {
            Mode = "Exp";
        }
        String normalModeStr = "Y";
        if (!normalMode) {
            normalModeStr = "N";
        }
        String allowEncodersStr = "Y";
        if (!allowEncoders) {
            allowEncodersStr = "N";
        }
        telemetry.addData("", "Power: %.1f, Input: %s, Normal: %s, Encoders: %s", MaxPower, Mode, normalModeStr, allowEncodersStr);

        ds.setEncoders(allowEncoders);

        double LeftX = gamepad1.left_stick_x;
        double LeftY = -gamepad1.left_stick_y;
        double RightX = gamepad1.right_stick_x;
        double RightY = -gamepad1.right_stick_y;
        telemetry.addData("", "LX: %.3f, LY: %.3f, RX: %.3f, RY: %.3f", LeftX, LeftY, RightX, RightY);

        double frontLeftPower = 0;
        double frontRightPower = 0;
        double backLeftPower = 0;
        double backRightPower = 0;

        if (normalMode) {
            frontLeftPower = LeftY - LeftX;
            frontRightPower = RightY + RightX;
            backLeftPower = LeftY + LeftX;
            backRightPower = RightY - RightX;
        } else {
            frontLeftPower = RightY + RightX;
            frontRightPower = -LeftY + LeftX;
            backLeftPower = -RightY + RightX;
            backRightPower = LeftY + LeftX;
        }

        telemetry.addData("", "FLP: %.3f, FRP: %.3f, BLP: %.3f, BRP: %.3f", frontLeftPower, frontRightPower, backLeftPower, backRightPower);
        if (!linearMode) {
            frontLeftPower = frontLeftPower * 7;
            frontRightPower = frontRightPower * 7;
            backLeftPower = backLeftPower * 7;
            backRightPower = backRightPower * 7;

            frontLeftPower = Math.signum(frontLeftPower) * (Math.pow(2, Math.abs(frontLeftPower)) - 1);
            frontRightPower = Math.signum(frontRightPower) * (Math.pow(2, Math.abs(frontRightPower)) - 1);
            backLeftPower = Math.signum(backLeftPower) * (Math.pow(2, Math.abs(backLeftPower)) - 1);
            backRightPower = Math.signum(backRightPower) * (Math.pow(2, Math.abs(backRightPower)) - 1);
            telemetry.addData("", "FLP: %.2f, FRP: %.2f, BLP: %.2f, BRP: %.2f", frontLeftPower, frontRightPower, backLeftPower, backRightPower);


            double HighValue = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
            HighValue = Math.max(HighValue, Math.abs(backLeftPower));
            HighValue = Math.max(HighValue, Math.abs(backRightPower));

            if (HighValue > (Math.pow(2, MaxDomain) - 1)) {
                frontLeftPower = (frontLeftPower * 127) / (HighValue);
                frontRightPower = (frontRightPower * 127) / (HighValue);
                backLeftPower = (backLeftPower * 127) / (HighValue);
                backRightPower = (backRightPower * 127) / (HighValue);
            }

            //telemetry.addData("","FLP: %.3f, FRP: %.3f, BLP: %.3f, BRP: %.3f", frontLeftPower, frontRightPower, backLeftPower, backRightPower);

            HighValue = 127;
            frontLeftPower = (frontLeftPower * MaxPower) / (HighValue);
            frontRightPower = (frontRightPower * MaxPower) / (HighValue);
            backLeftPower = (backLeftPower * MaxPower) / (HighValue);
            backRightPower = (backRightPower * MaxPower) / (HighValue);

            frontLeftPower = Range.clip(frontLeftPower, -1, 1);
            frontRightPower = Range.clip(frontRightPower, -1, 1);
            backLeftPower = Range.clip(backLeftPower, -1, 1);
            backRightPower = Range.clip(backRightPower, -1, 1);
        } else {
            double HighValue = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
            HighValue = Math.max(HighValue, Math.abs(backLeftPower));
            HighValue = Math.max(HighValue, Math.abs(backRightPower));

            frontLeftPower = Range.clip(frontLeftPower, -1, 1);
            frontRightPower = Range.clip(frontRightPower, -1, 1);
            backLeftPower = Range.clip(backLeftPower, -1, 1);
            backRightPower = Range.clip(backRightPower, -1, 1);
        }
        ds.setMotorPower(frontLeftPower, frontRightPower, backLeftPower, backRightPower);

        telemetry.update();
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    /*
    @Override
    public void stop() {
    }
    */

}
