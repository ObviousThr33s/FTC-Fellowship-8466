package org.firstinspires.ftc.teamcode.Samwise.TeleOp.TestTeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Samwise.SamwiseArm.SamwiseGenius;

@TeleOp(name = "Arm Test", group = "tests")
//@Disabled
public class ArmTeleOpTest extends OpMode
{
    private SamwiseGenius armStuff;

    private boolean manual = false;
    private boolean isHoldingJ2 = false;
    private boolean isHoldingJ3 = false;
    private boolean collect = false;

    @Override
    public void init()
    {
        armStuff = new SamwiseGenius(hardwareMap);
    }

    @Override
    public void loop()
    {

        telemetry.addData("J1 encoder ticks", armStuff.getJ1CurrentPosition());
        telemetry.addData("J2 encoder ticks", armStuff.getJ2CurrentPosition());
        telemetry.addData("J3 encoder ticks", armStuff.getJ3CurrentPosition());
        telemetry.update();

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


        if (gamepad1.left_stick_x > 0.1)
        {
            armStuff.driveJ1(true);
        }
        else if (gamepad1.left_stick_x < -0.1)
        {
            armStuff.driveJ1(false);
        }
        else
        {
            armStuff.stopJ1();
        }


        // to deposit position

        if (gamepad1.a || gamepad1.b || gamepad1.x || gamepad1.y)
        {
            if (gamepad1.x)
            {
                armStuff.silverDropPoint();
                isHoldingJ2 = false;
                isHoldingJ3 = false;
            }

            if (gamepad1.y)
            {
                armStuff.goldDropPoint();
                isHoldingJ2 = false;
                isHoldingJ3 = false;
            }


            if (gamepad1.a)
            {
                armStuff.toInitialPosition();
                isHoldingJ2 = false;
                isHoldingJ3 = false;
            }

            // to collection position
            if (gamepad1.b)
            {
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

        }
        // collection and deposit
        if (gamepad1.left_trigger > 0.1)
        {
            armStuff.savePreviousPosition();
            armStuff.lowerJ4();
            collect = true;
            if (collect)
            {
                armStuff.collectMinerals();
            }
        }

        if (gamepad1.right_trigger > 0.1)
        {
            armStuff.depositMinerals();
        }

        if (gamepad1.left_trigger < 0.1 && gamepad1.right_trigger < 0.1)
        {
            armStuff.stopCollecting();
            armStuff.setCollecting(false);
        }

        if (gamepad1.left_bumper)
        {
            armStuff.moveJ4Up();
        }

        if (gamepad1.right_bumper)
        {
            armStuff.moveJ4Down();
        }

        if (!gamepad1.left_bumper && !gamepad1.right_bumper)
        {
            armStuff.stopJ4();
        }

        if (manual)
        {

            if (gamepad1.right_stick_y > 0.1)
            {
                armStuff.driveJ3(false);
                isHoldingJ3 = false;
            }
            else if (gamepad1.right_stick_y < -0.1)
            {
                armStuff.driveJ3(true);
                isHoldingJ3 = false;
            }
            else if (isHoldingJ3)
            {
                armStuff.holdPositionJ3(false);
            }
            else
            {
                armStuff.holdPositionJ3(true);
                isHoldingJ3 = true;
            }

            if (gamepad1.left_stick_y > 0.1)
            {
                armStuff.driveJ2(false);
                isHoldingJ2 = false;
            }
            else if (gamepad1.left_stick_y < -0.1)
            {
                armStuff.driveJ2(true);
                isHoldingJ2 = false;
            }
            else if (isHoldingJ2)
            {
                armStuff.holdPositionJ2(false);
            }
            else
            {
                armStuff.holdPositionJ2(true);
                isHoldingJ2 = true;
            }
        }
        else
        {
            //TODO: Michael, put your plane of motion code in here when you're done with it.
        }

    }
}
