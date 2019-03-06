package org.firstinspires.ftc.teamcode.Samwise.TeleOp.TestTeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Samwise.SamwiseArm.TRexSamwiseSmart;

@TeleOp(name = "LaurenTest", group = "tests")
@Disabled
public class LaurenTest extends OpMode
{
    private TRexSamwiseSmart armStuff = null;

    @Override
    public void init()
    {
        armStuff = new TRexSamwiseSmart(hardwareMap);
        //        armStuff.extendArm();
    }

    @Override
    public void loop()
    {
//        telemetry.addData("E1 encoder ticks", armStuff.getE1CurrentPosition());
        telemetry.addData("J1 encoder ticks", armStuff.getJ1CurrentPosition());
        telemetry.addData("J2 encoder ticks", armStuff.getJ2CurrentPosition());
        telemetry.addData("J3 encoder ticks", armStuff.getJ3CurrentPosition());
        telemetry.update();

        // Manual driveToCrater
        if (gamepad1.dpad_up || gamepad1.dpad_down || gamepad1.dpad_right || gamepad1.dpad_left)
        {
            armStuff.setManual(true);
        }
        //move J2 up
        if (gamepad1.dpad_right)
        {
            armStuff.stopJ3();
            armStuff.driveJ2(false);
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
            armStuff.stopJ3();
            armStuff.driveJ2(true);
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
            armStuff.stopJ2();
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
            armStuff.stopJ2();
            armStuff.driveJ3(false);
        }
        else
        {
            if (armStuff.isManual() && !gamepad1.dpad_up)
            {
                armStuff.stopJ3();
            }
        }

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


        // collection and deposit
        while (gamepad1.left_trigger > 0)
        {
            //            armStuff.savePreviousPosition();
            //            armStuff.toPreviousCollectionPosition();
            armStuff.collectMinerals();
        }

        while (gamepad1.right_trigger > 0)
        {
            //            armStuff.savePreviousPosition();
            //            armStuff.toPreviousCollectionPosition();
            armStuff.depositMinerals();
        }

        if (gamepad1.left_trigger == 0 && gamepad1.right_trigger == 0 && armStuff.isCollecting())
        {
            armStuff.stopCollecting();
            armStuff.setCollecting(false);
        }


        if (gamepad1.a)
        {

//            armStuff.savePreviousPosition();
//            armStuff.toPreviousCollectionPosition();
            armStuff.stopJ2();
            armStuff.stopJ3();

        }
        //        else
        //        {
        ////            armStuff.setHoldRequest(false);
        //        }

        //        if (gamepad1.b)
        //        {
        //            armStuff.moveJ4Down();
        //        }
        if (gamepad1.right_stick_y > 0.1)
        {
            this.armStuff.extendL1();
        }
        else
        {
            this.armStuff.stopExtendL1();
        }
        if (gamepad1.left_stick_y > 0.1)
        {
            this.armStuff.extendL2();
        }
        else
        {
            this.armStuff.stopExtendL2();
        }


    }

}
