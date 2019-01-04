package org.firstinspires.ftc.teamcode.Samwise.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Samwise.SamwiseArm.SamwiseArm;

@TeleOp
public class LaurenTest extends OpMode
{
    public SamwiseArm armStuff = null;

    @Override
    public void init()
    {
        armStuff = new SamwiseArm(hardwareMap);
    }

    @Override
    public void loop()
    {
        //telemetry.addData("J1 encoder ticks", armStuff.getJ1CurrentPosition());
        telemetry.addData("J2 encoder ticks", armStuff.getJ2CurrentPosition());
        telemetry.addData("J3 encoder ticks", armStuff.getJ3CurrentPosition());
        telemetry.update();

        // Manual drive
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
            if (armStuff.getIsManual() && !gamepad1.dpad_left)
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
            if (armStuff.getIsManual() && !gamepad1.dpad_right)
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
            if (armStuff.getIsManual() && !gamepad1.dpad_down)
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
            if (armStuff.getIsManual() && !gamepad1.dpad_up)
            {
                armStuff.stopJ3();
            }
        }

        // to deposit position
        if (gamepad1.x)
        {
            armStuff.silverDropPoint();
        }

        if (gamepad1.y)
        {
            armStuff.goldDropPoint();
        }

        // to collection position
        if (gamepad1.b)
        {
            if (/*Math.abs(armStuff.getJ1CurrentPosition()) < 10  && */Math.abs(armStuff.getJ2CurrentPosition()) < 10 && Math.abs(armStuff.getJ3CurrentPosition()) < 10)
            {
                armStuff.toCollectionPlane();
            }
            else
            {
                armStuff.toPreviousCollectionPosition();
            }
        }

        // to initial position
        if (gamepad1.a)
        {
            System.out.println("A pressed");
            telemetry.addData("A", " Pressed");
            armStuff.toInitialPosition();
        }

        // collection and deposit
        //        if (gamepad1.left_trigger > 0)
        //        {
        //            armStuff.collectMinerals();
        //        }
        //
        //        if (gamepad1.right_trigger > 0)
        //        {
        //            armStuff.depositMinerals();
        //        }
        //
        //        if (gamepad1.left_trigger == 0 && gamepad1.right_trigger == 0)
        //        {
        //            armStuff.stopServo();
        //        }

    }

}
