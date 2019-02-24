package org.firstinspires.ftc.teamcode.Samwise.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Samwise.SamwiseArm.OctoSamwiseGenius;

@TeleOp(name = "Octo Arm Test", group = "tests")
//@Disabled
public class OctoArmTeleOpTest extends OpMode
{
    private OctoSamwiseGenius armStuff;

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

    @Override
    public void init()
    {
        armStuff = new OctoSamwiseGenius(hardwareMap);
        armStuff.stopJ1();
        armStuff.stopJ2();
        armStuff.stopJ3();
    }

    @Override
    public void loop()
    {
        telemetry.addData("E2 counts", armStuff.e2Count);
        telemetry.addData("J4 counts", armStuff.J4Count);
        telemetry.addData("J1 encoder ticks", armStuff.getJ1CurrentPosition());
        telemetry.addData("J2 encoder ticks", armStuff.getJ2CurrentPosition());
        telemetry.addData("J3 encoder ticks", armStuff.getJ3CurrentPosition());
        telemetry.addData("E1 encoder ticks", armStuff.getE1CurrentPosition());
        telemetry.update();

        if (gamepad2.dpad_up)
        {
            armStuff.extendL1();
        }

        if (gamepad2.dpad_down)
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

        if (!gamepad2.dpad_right && !gamepad2.dpad_left)
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
