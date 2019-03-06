package org.firstinspires.ftc.teamcode.Samwise.Hanger;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp(name = "ARM v2")
@Disabled
    public class SamwiseArmBaseTest extends OpMode {
        public DcMotor leftdrive = null;
        public DcMotor rightdrive = null;

        private double powerlevel = 1.0;

        public void init() {
            leftdrive = hardwareMap.dcMotor.get("left");
            rightdrive = hardwareMap.dcMotor.get("right");

            leftdrive.setPower(0);
            rightdrive.setPower(0);

            leftdrive.setDirection(DcMotor.Direction.FORWARD);
            rightdrive.setDirection(DcMotor.Direction.FORWARD);
        }

        public void loop() {
            if(gamepad2.b) {
                powerlevel = 0.3;
                System.out.println("50% power");
            }
            else if(gamepad2.a) {
                powerlevel = .5;
                System.out.println("70% power");
            }
            else if(gamepad2.x) {
                powerlevel = 1;
                System.out.println("max power");
            }
                float MotorPower = gamepad2.left_stick_y;

                leftdrive.setPower(MotorPower * powerlevel);
                rightdrive.setPower(MotorPower * powerlevel);
        }
    }