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

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

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

@TeleOp(name = "Keith - TeleOp", group = "TeleOp")

public class KeithTeleOp extends OpMode{
	//Declare OpMode members
	//TeleOP Varriables



    private static boolean isStopped = false;
    private ElapsedTime runtime = new ElapsedTime();

    boolean debugTelemetry = false;

    KeithRobot keithRobot = null;
    KeithJewlKnocker jwl = null;

    // Variables that control the driving system
    MecanumDS ds = null;
    boolean newDriveActive = false;
    boolean linearMode = false;
    boolean allowEncoders = true;
    double maxDrivingPower = 0.3;
    boolean engageMotors = true;
    double dsSelectionTime = 0;
    PowerLvl pwrLvl = PowerLvl.MED;

    // Variables that control the relic arm system
    FishingRodSystem frs = null;
    boolean relicArmMoving = false;
    boolean relicArmAnalog = false;
    boolean gripperMoving = false;
    boolean gripperAnalog = false;
    boolean tiltActive = false;
    double frsSelectionTime = 0;

    // Variables that control the harvester system
    KeithElevator ele = null;
    KeithCarriage car = null;
    double lTrigger2 = 1.0;
    double rTrigger2 = 1.0;
    //Reverse should always be between -1.0 and 0.0, positive should always be between 0.0 and 1.0
    double pwrForward = 1.0;
    double pwrReverse = -pwrForward;

    //Variables that control the Jewl Detection system
    JewlDetect jds;
    boolean jewlDetectDebug = false;

    // Variables that control switching from
	// the harvester system to the relic arm
    boolean harvesterActive = true;
    double switchSelectionTime = 0;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        keithRobot = new KeithRobot(hardwareMap, telemetry);
        ds = (MecanumDS) (keithRobot.GetDriveSystem());
        frs = (FishingRodSystem) (keithRobot.GetRelicArmSubsystem());
        ele = (KeithElevator) (keithRobot.GetKeithElevator());
        car = (KeithCarriage) (keithRobot.GetKeithCarriage());
        jwl = (KeithJewlKnocker) (keithRobot.GetJewelKnockerSubsystem());
        jds = (JewlDetect) (keithRobot.GetKeithJewlDetect());

        ele.kickerSetPosition(ele.downPos);

        if (jewlDetectDebug) {
            jds.JewlDetectForInit(telemetry, hardwareMap);
        }

//        jwl.setBasePosition(0.2);
//        jwl.setKnockerPosition(0.75);
        jwl.setKnockerPosition(0.99);
        jwl.setBasePosition(0.18);

        telemetry.addLine("init...");
        telemetry.update();
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {

    }

    double TransformInterval(double Value, double SourceStart, double SourceEnd, double TargetStart, double TargetEnd) {
        double sourceRange = SourceEnd - SourceStart;
        double targetRange = TargetEnd - TargetStart;
        return TargetStart + (Value - SourceStart) * targetRange / sourceRange;
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        dsSelectionTime = runtime.milliseconds();
        frsSelectionTime = runtime.milliseconds();

        telemetry.addLine("start...");
        telemetry.update();
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        if (jewlDetectDebug) {
            jds.JewlDetectForStop();
        }
        isStopped = true;
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

        //Debug info
        telemetry.addData("Gamepad2 L trigger float:", gamepad2.left_trigger);
        telemetry.addData("Gamepad2 R trigger float:", gamepad2.right_trigger);
        if (jewlDetectDebug) {
            jds.JewlDetectForLoop();
        }

	    if (gamepad1.left_bumper || gamepad1.right_bumper) {
        	if (gamepad1.left_bumper && !gamepad1.right_bumper && pwrLvl != PowerLvl.LOW){
        		pwrLvl = PowerLvl.values()[pwrLvl.ordinal() - 1];
	        }
	        if (gamepad1.right_bumper && !gamepad1.left_bumper && pwrLvl != PowerLvl.HIG){
        		pwrLvl = PowerLvl.values()[pwrLvl.ordinal() + 1];
	        }
	    }

        if (gamepad1.start) {
            if (gamepad1.x) {
                //start+x switch to old drive mode
                newDriveActive = false;
            }
            if (gamepad1.y) {
                //start+y switch to new drive mode
                newDriveActive = true;
            }
        }

        if (gamepad2.start) {
            if (gamepad2.x) {
                //start+x switch to harvester mode
                harvesterActive = true;
                switchSelectionTime = System.currentTimeMillis();
            }
            if (gamepad2.y) {
                //start+y switch to relic Arm mode
                harvesterActive = false;
                switchSelectionTime = System.currentTimeMillis();
            }
        }

        if (newDriveActive) {
            CallTestDriveSystem(pwrLvl);
        } else {
            CallDriveSystem();
        }
        if (System.currentTimeMillis() - switchSelectionTime > 300) {
            if (harvesterActive) {
                CallHarvesterSystem();
            } else {
                CallRelicArmSystem();
            }
        }
        telemetry.update();
    }

    void CallHarvesterSystem() {
        telemetry.addLine("================ Harvester ===================");

        //Carriage code
        //Controls for carriage

        if (gamepad2.dpad_left) {
            telemetry.addLine("command: slide left");
            telemetry.update();
//            car.slideTo(KeithCarriage.LEFT);
            car.slideStart(KeithCarriage.LEFT);
        }
        if (gamepad2.dpad_up) {
            telemetry.addLine("command: slide center");
            telemetry.update();
//            car.slideTo(KeithCarriage.CENTER);
            car.slideStart(KeithCarriage.CENTER);
        }
        if (gamepad2.dpad_right) {
            telemetry.addLine("command: slide right");
            telemetry.update();
//            car.slideTo(KeithCarriage.RIGHT);
            car.slideStart(KeithCarriage.RIGHT);
        }
        //stop any finished sliding movement
        car.slideVerify();

        if (gamepad2.x) {
            telemetry.addLine("Flipper toggle");
            car.flipperToggle();
        }
        if (gamepad2.left_bumper) {
            telemetry.addLine("left servo toggle");
            car.holderToggle(KeithCarriage.LEFTS);
        }
        if (gamepad2.right_bumper) {
            telemetry.addLine("right servo toggle");
            car.holderToggle(KeithCarriage.RIGHTS);
        }

        //Kicker and Elevator Code
        //Kicker and Elevator controls

        if (gamepad2.a && !gamepad2.b) {
            telemetry.addLine("command: kick");
            ele.kickerKick();
        }

        if (gamepad2.b && !gamepad2.a) {
            telemetry.addLine("command: kicker reset");
            ele.kickerReset();
        }


        if (gamepad2.left_trigger <= lTrigger2 && gamepad2.left_trigger != 0.0 && gamepad2.right_trigger == 0.0) {
            telemetry.addLine("command: elevator reverse");
            ele.elevatorPower(pwrReverse);
        }
        if (gamepad2.right_trigger <= 1.0 && gamepad2.right_trigger != 0.0 && gamepad2.left_trigger == 0.0) {
            telemetry.addLine("command: elevator forward");
            ele.elevatorPower(pwrForward);
        }
        if (gamepad2.left_trigger == 0 && gamepad2.right_trigger == 0) {
            ele.elevatorPower(0.0);
        }

        telemetry.update();
    }

    void CallRelicArmSystem() {
        telemetry.addLine("================ Relic Arm ===================");

        // deal with arm extending/retracting
        boolean relicArmCommand = false;
        if (gamepad2.dpad_right) {
            relicArmCommand = true;
            if ((!relicArmMoving) && (!gripperMoving) && (!tiltActive)) {
                relicArmMoving = true;
                frs.extendOn(1);
            }
        }
        if (gamepad2.dpad_left) {
            relicArmCommand = true;
            if ((!relicArmMoving) && (!gripperMoving) && (!tiltActive)) {
                relicArmMoving = true;
                relicArmCommand = true;
                frs.extendOn(-1);
            }
        }
        if ((gamepad2.left_trigger > 0.1) || (gamepad2.right_trigger > 0.1)) {
            relicArmCommand = true;
            if ((!gripperMoving) && (!tiltActive) && (!relicArmMoving || relicArmAnalog)) {
                relicArmMoving = true;
                relicArmAnalog = true;
                double moveValue = gamepad2.right_trigger - gamepad2.left_trigger;
                frs.setRodMotorPower(moveValue);
            }
        }
        if ((!relicArmCommand) && relicArmMoving) {
            relicArmAnalog = false;
            relicArmMoving = false;
            frs.extendOff();
        }

        // deal with the gripper lowering/retracting
        boolean gripperCommand = false;
        if (gamepad2.dpad_up) {
            gripperCommand = true;
            if ((!relicArmMoving) && (!gripperMoving) && (!tiltActive)) {
                gripperMoving = true;
                frs.gripperMoveOn(1);
            }
        }
        if (gamepad2.dpad_down) {
            gripperCommand = true;
            if ((!relicArmMoving) && (!gripperMoving) && (!tiltActive)) {
                gripperMoving = true;
                frs.gripperMoveOn(-1);
            }
        }
        if (gamepad2.b) {
            gripperCommand = true;
            if ((!relicArmMoving) && (!gripperMoving) && (!tiltActive)) {
                gripperMoving = true;
                frs.setUpperReelPower(1);
            }
        }
        if (gamepad2.x) {
            gripperCommand = true;
            if ((!relicArmMoving) && (!gripperMoving) && (!tiltActive)) {
                gripperMoving = true;
                frs.setLowerReelPower(1);
            }
        }
        if ((Math.abs(gamepad2.left_stick_y) > 0.1) || (Math.abs(gamepad2.right_stick_y) > 0.1)) {
            gripperCommand = true;
            if ((!relicArmMoving) && (!tiltActive) && ((!gripperMoving) || gripperAnalog)) {
                gripperMoving = true;
                gripperAnalog = true;
                if (Math.abs(gamepad2.left_stick_y) > 0.1) {
                    frs.setLowerReelPower(-gamepad2.left_stick_y);
                }
                if (Math.abs(gamepad2.right_stick_y) > 0.1) {
                    frs.setUpperReelPower(-gamepad2.right_stick_y);
                }
            }
        }
        if ((!gripperCommand) && gripperMoving) {
            gripperMoving = false;
            gripperAnalog = false;
            frs.gripperMoveOff();
        }

        // non-continuous optaions
        if ((runtime.milliseconds() - frsSelectionTime) >= 300) {
            boolean frsSelection = false;

            // deal with the claw opening/closing
            if (gamepad2.a) {
                frsSelection = true;
                frs.gripperToggle();
            }

            // modify the conversion ration between motor and servos
            if (gamepad2.right_bumper) {
                frsSelection = true;
                frs.incRatio(0.001);
            }
            if (gamepad2.left_bumper) {
                frsSelection = true;
                frs.incRatio(-0.001);
            }

            // tilt the claw
            if (gamepad2.y) {
                if ((!relicArmMoving) && (!gripperMoving) && (!tiltActive)) {
                    frsSelection = true;
                    tiltActive = true;
                    frs.tiltToggleStart();
                }
            }

            if (frsSelection) {
                frsSelectionTime = runtime.milliseconds();
            }
        }
        if (tiltActive) {
            if (frs.tiltToggleVerify()) {
                tiltActive = false;
            }
        }
        telemetry.addData("", "Ratio(Bumpers): %.3f, Tilting: %s", frs.getRatio(), tiltActive ? "Yes" : "No");
    }

    void CallTestDriveSystem(PowerLvl pwrLvl) {

        double leftX = -gamepad1.left_stick_y;
        double leftY = gamepad1.left_stick_x;

        double r = Math.hypot(leftX, leftY);
        double robotAngle = Math.atan2(leftX, leftY) - Math.PI / 4;
        double rightX = gamepad1.right_stick_x;
        final double v1 = r * Math.cos(robotAngle) + rightX*pwrLvl.constant;
        final double v2 = r * Math.sin(robotAngle) - rightX*pwrLvl.constant;
        final double v3 = r * Math.sin(robotAngle) + rightX*pwrLvl.constant;
        final double v4 = r * Math.cos(robotAngle) - rightX*pwrLvl.constant;

        ds.FrontLeft.setPower(v3);
        ds.FrontRight.setPower(v4);
        ds.BackLeft.setPower(v1);
        ds.BackRight.setPower(v2);
        telemetry.addData("V1", v1);
        telemetry.addData("V2", v2);
        telemetry.addData("V3", v3);
        telemetry.addData("V4", v4);
        telemetry.addData("Power Level", pwrLvl);
    }

    void CallDriveSystem() {
        // Get the input for the driving system options
        if ((runtime.milliseconds() - dsSelectionTime) >= 300) {
            boolean dsSelection = false;
            if (gamepad1.left_bumper) {
                dsSelection = true;
                maxDrivingPower = maxDrivingPower - 0.1;
                if (maxDrivingPower > 1) {
                    maxDrivingPower = 1;
                }
                if (maxDrivingPower < 0.1) {
                    maxDrivingPower = 0.1;
                }
            } else if (gamepad1.right_bumper) {
                dsSelection = true;
                maxDrivingPower = maxDrivingPower + 0.1;
                if (maxDrivingPower > 1) {
                    maxDrivingPower = 1;
                }
                if (maxDrivingPower < 0) {
                    maxDrivingPower = 0;
                }
            }
            if (gamepad1.a) {
                dsSelection = true;
                linearMode = !linearMode;
            }
            if (gamepad1.b) {
                dsSelection = true;
                allowEncoders = !allowEncoders;
                ds.setEncoders(allowEncoders);
            }
            if (gamepad1.x) {
                dsSelection = true;
                engageMotors = !engageMotors;
            }
            if (dsSelection) {
                dsSelectionTime = runtime.milliseconds();
            }
        }
        String Mode = "Lin";
        if (!linearMode) {
            Mode = "Exp";
        }
        String allowEncodersStr = "Y";
        if (!allowEncoders) {
            allowEncodersStr = "N";
        }
        telemetry.addData("", "Power(Bumpers): %.1f, Input(A): %s, Encoders(B): %s", maxDrivingPower, Mode, allowEncodersStr);

        // Get the input from the joysticks
        double LeftX = gamepad1.left_stick_x;
        double LeftY = -gamepad1.left_stick_y;
        double RightX = gamepad1.left_stick_x;
        double RightY = -gamepad1.left_stick_y;
        double spinFactor = gamepad1.right_stick_x;
        if (debugTelemetry) {
            telemetry.addData("", "LX: %.3f, LY: %.3f, RX: %.3f, RY: %.3f", LeftX, LeftY, RightX, RightY);
        }

        double minExpX = -5;
        double maxExpX = 2;

        if (!linearMode) {
            if ((LeftX != 0) || (LeftY != 0)) {
                double angleLeft = (LeftX == 0) ? Math.toRadians(90) : Math.atan(Math.abs(LeftY) / Math.abs(LeftX));
                if (LeftX > 0) {
                    if (LeftY > 0) {
                        // no change
                    } else {
                        angleLeft = Math.toRadians(360) - angleLeft;
                    }
                } else {
                    if (LeftY > 0) {
                        angleLeft = Math.toRadians(180) - angleLeft;
                    } else {
                        angleLeft = Math.toRadians(180) + angleLeft;
                    }
                }
                if (debugTelemetry) {
                    telemetry.addData("", "AngleL: %.1f", Math.toDegrees(angleLeft));
                }
                double diagLeft = Range.clip(Math.sqrt(Math.pow(LeftX, 2) + Math.pow(LeftY, 2)), 0, 1);
                diagLeft = TransformInterval(diagLeft, 0, 1, minExpX, maxExpX);
                diagLeft = Math.exp(diagLeft);
                diagLeft = TransformInterval(diagLeft, Math.exp(minExpX), Math.exp(maxExpX), 0, 1);
                LeftX = Math.cos(angleLeft) * diagLeft;
                LeftY = Math.sin(angleLeft) * diagLeft;
            }

            if ((RightX != 0) || (RightY != 0)) {
                double angleRight = (RightX == 0) ? Math.toRadians(90) : Math.atan(Math.abs(RightY) / Math.abs(RightX));
                if (RightX > 0) {
                    if (RightY > 0) {
                        // no change
                    } else {
                        angleRight = Math.toRadians(360) - angleRight;
                    }
                } else {
                    if (RightY > 0) {
                        angleRight = Math.toRadians(180) - angleRight;
                    } else {
                        angleRight = Math.toRadians(180) + angleRight;
                    }
                }
                if (debugTelemetry) {
                    telemetry.addData("", "AngleR: %.1f", Math.toDegrees(angleRight));
                }
                double diagRight = Range.clip(Math.sqrt(Math.pow(RightX, 2) + Math.pow(RightY, 2)), 0, 1);
                diagRight = TransformInterval(diagRight, 0, 1, minExpX, maxExpX);
                diagRight = Math.exp(diagRight);
                diagRight = TransformInterval(diagRight, Math.exp(minExpX), Math.exp(maxExpX), 0, 1);
                RightX = Math.cos(angleRight) * diagRight;
                RightY = Math.sin(angleRight) * diagRight;
            }
        }

        LeftX = LeftX * maxDrivingPower;
        LeftY = LeftY * maxDrivingPower;
        RightX = RightX * maxDrivingPower;
        RightY = RightY * maxDrivingPower;
        double frontLeft = LeftY + LeftX;
        double frontRight = RightY - RightX;
        double backLeft = LeftY - LeftX;
        double backRight = RightY + RightX;
        frontLeft = frontLeft + spinFactor * maxDrivingPower;
        frontRight = frontRight - spinFactor * maxDrivingPower;
        backLeft = backLeft + spinFactor * maxDrivingPower;
        backRight = backRight - spinFactor * maxDrivingPower;

        Utilities.PowerLevels powerLevels = new Utilities.PowerLevels(
                frontLeft,
                frontRight,
                backLeft,
                backRight);
        if (debugTelemetry) {
            telemetry.addData("", "FL(IP): %.2f, FR(IP): %.2f, BL(IP): %.2f, BR(IP): %.2f", powerLevels.powerFL, powerLevels.powerFR, powerLevels.powerBL, powerLevels.powerBR);
        }

        powerLevels = Utilities.NormalizePower(powerLevels, maxDrivingPower);
        if (debugTelemetry) {
            telemetry.addData("", "FL(PL): %.2f, FR(PL): %.2f, BL(PL): %.2f, BR(PL): %.2f", powerLevels.powerFL, powerLevels.powerFR, powerLevels.powerBL, powerLevels.powerBR);
        }
        if (engageMotors) {
            ds.setMotorPower(powerLevels.powerFL, powerLevels.powerFR, powerLevels.powerBL, powerLevels.powerBR);
        }
    }

    public static boolean isIsStopped() {
        return isStopped;
    }

}

