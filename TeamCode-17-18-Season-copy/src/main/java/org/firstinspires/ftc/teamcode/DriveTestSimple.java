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

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

/**
 * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
 * All device access is managed through the HardwarePushbot class.
 * The code is structured as a LinearOpMode
 *
 * This particular OpMode executes a POV Game style Teleop for a PushBot
 * In this mode the left stick moves the robot FWD and back, the Right stick turns left and right.
 * It raises and lowers the claw using the Gampad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="DriveTestSimple:", group="Pushbot")
public class DriveTestSimple extends LinearOpMode {

    public DcMotor FrontLeft = null;
    public DcMotor FrontRight = null;
    public DcMotor BackLeft = null;
    public DcMotor BackRight = null;
    public DcMotor ReverseMotor = null;
    public double MaxPower = 0.4;
    public double MaxDomain = 7;


    public void setMotorPower(double FL, double FR, double BL, double BR){
        telemetry.addData("","FL: %.3f, FR: %.3f, BL: %.3f, BR: %.3f", FL, FR, BL, BR);
        FrontLeft.setPower(-FL);
        FrontRight.setPower(FR);
        BackLeft.setPower(-BL);
        BackRight.setPower(BR);
        ReverseMotor.setPower(-0.3);
    }

    @Override
    public void runOpMode() {

        HardwareMap hwMap = hardwareMap;

        FrontLeft = hwMap.get(DcMotor.class, "Front Left");
        FrontRight = hwMap.get(DcMotor.class, "Front Right");
        BackLeft = hwMap.get(DcMotor.class, "Back Left");
        BackRight = hwMap.get(DcMotor.class, "Back Right");
        ReverseMotor = hwMap.get(DcMotor.class, "ReverseMotor");

        FrontLeft.setPower(0);
        FrontRight.setPower(0);
        BackLeft.setPower(0);
        BackRight.setPower(0);

        FrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while (opModeIsActive()) {

            if(gamepad1.left_bumper) {
                MaxPower=MaxPower-0.1;
                sleep(200);
            } else if(gamepad1.right_bumper) {
                MaxPower=MaxPower+0.1;
                sleep(200);
            }
            if(MaxPower > 1) {
                MaxPower = 1;
            }
            if(MaxPower < 0) {
                MaxPower = 0;
            }

            telemetry.addData("MaxPower"," %.1f",MaxPower);

            double LeftX=gamepad1.left_stick_x;
            double LeftY=-gamepad1.left_stick_y;
            double RightX=gamepad1.right_stick_x;
            double RightY=-gamepad1.right_stick_y;

            double frontLeftPower = LeftY-LeftX;
            double frontRightPower = RightY+RightX;
            double backLeftPower = LeftY+LeftX;
            double backRightPower = RightY-RightX;

            telemetry.addData("","FLP: %.3f, FRP: %.3f, BLP: %.3f, BRP: %.3f", frontLeftPower, frontRightPower, backLeftPower, backRightPower);
            frontLeftPower = frontLeftPower * 7;
            frontRightPower = frontRightPower * 7;
            backLeftPower = backLeftPower * 7;
            backRightPower = backRightPower * 7;

            frontLeftPower = Math.signum(frontLeftPower) * (Math.pow(2, Math.abs(frontLeftPower)) - 1);
            frontRightPower = Math.signum(frontRightPower) * (Math.pow(2, Math.abs(frontRightPower)) - 1);
            backLeftPower = Math.signum(backLeftPower) * (Math.pow(2, Math.abs(backLeftPower)) - 1);
            backRightPower = Math.signum(backRightPower) * (Math.pow(2, Math.abs(backRightPower)) - 1);
            telemetry.addData("","FLP: %.2f, FRP: %.2f, BLP: %.2f, BRP: %.2f", frontLeftPower, frontRightPower, backLeftPower, backRightPower);


            double HighValue = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
            HighValue = Math.max(HighValue, Math.abs(backLeftPower));
            HighValue = Math.max(HighValue, Math.abs(backRightPower));

            if (HighValue > (Math.pow(2, MaxDomain)-1)) {
                frontLeftPower = (frontLeftPower*127)/(HighValue);
                frontRightPower = (frontRightPower*127)/(HighValue);
                backLeftPower = (backLeftPower*127)/(HighValue);
                backRightPower = (backRightPower*127)/(HighValue);
            }

            //telemetry.addData("","FLP: %.3f, FRP: %.3f, BLP: %.3f, BRP: %.3f", frontLeftPower, frontRightPower, backLeftPower, backRightPower);

            HighValue = 127;
            frontLeftPower = (frontLeftPower*MaxPower)/(HighValue);
            frontRightPower = (frontRightPower*MaxPower)/(HighValue);
            backLeftPower = (backLeftPower*MaxPower)/(HighValue);
            backRightPower = (backRightPower*MaxPower)/(HighValue);

            frontLeftPower = Range.clip(frontLeftPower, -1, 1);
            frontRightPower = Range.clip(frontRightPower, -1, 1);
            backLeftPower = Range.clip(backLeftPower, -1, 1);
            backRightPower = Range.clip(backRightPower, -1, 1);

            setMotorPower(frontLeftPower, frontRightPower, backLeftPower, backRightPower);

            telemetry.update();
        }
    }
}