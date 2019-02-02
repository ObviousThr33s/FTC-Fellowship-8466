package org.firstinspires.ftc.teamcode.Samwise.TeleOp.TestTeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Samwise.SamwiseArm.SamwiseGenius;

@TeleOp(name = "Arm Test", group = "tests")
@Disabled
public class ArmTeleOpTest extends OpMode
{
    private SamwiseGenius armStuff;

    private boolean manual = false;
    private boolean hold = false;

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

        // to deposit position

        if (gamepad1.a || gamepad1.b || gamepad1.x || gamepad1.y)
        {
            hold = false;

            if (gamepad1.x)
            {
                armStuff.silverDropPoint();
            }

            if (gamepad1.y)
            {
                armStuff.goldDropPoint();
            }

            if (gamepad1.a)
            {
                armStuff.toInitialPosition();
            }

            // to collection position
            if (gamepad1.b)
            {
                if (Math.abs(armStuff.getJ1CurrentPosition()) < 10 && Math.abs(armStuff.getJ2CurrentPosition()) < 10 && Math.abs(armStuff.getJ3CurrentPosition()) < 10)
                {
                    armStuff.toCollectionPlane();
                }
                else
                {
                    armStuff.toPreviousPosition();
                }
            }

        }
        // collection and deposit
        while (gamepad1.left_trigger > 0)
        {
            armStuff.smartCollectMinerals();
        }

        while (gamepad1.right_trigger > 0)
        {
            armStuff.depositMinerals();
        }

        if (gamepad1.left_trigger == 0 && gamepad1.right_trigger == 0 && armStuff.isCollecting())
        {
            armStuff.stopCollecting();
            armStuff.setCollecting(false);
        }

        while (gamepad1.left_bumper)
        {
            armStuff.moveJ4Up();
        }

        while (gamepad1.right_bumper)
        {
            armStuff.moveJ4Down();
        }

        if (!gamepad1.left_bumper && !gamepad1.right_bumper)
        {
            armStuff.stopJ4();
        }

        if (manual)
        {
            hold = true;

            if (gamepad1.right_stick_y > 0.1)
            {
                armStuff.driveJ3(false);
                armStuff.holdJ3 = true;
            }
            else if (gamepad1.right_stick_y < -0.1)
            {
                armStuff.driveJ3(true);
                armStuff.holdJ3 = true;
            }
            else if (hold)
            {
                armStuff.holdPositionJ3();
            }

            if (gamepad1.left_stick_y > 0.1)
            {
                armStuff.driveJ2(false);
                armStuff.holdJ2 = true;
            }
            else if (gamepad1.left_stick_y < -0.1)
            {
                armStuff.driveJ2(true);
                armStuff.holdJ2 = true;
            }
            else if (hold)
            {
                armStuff.holdPositionJ2();
            }
        }
        else
        {
            //TODO: Michael, put your plane of motion code in here when you're done with it.
        }

    }
}
