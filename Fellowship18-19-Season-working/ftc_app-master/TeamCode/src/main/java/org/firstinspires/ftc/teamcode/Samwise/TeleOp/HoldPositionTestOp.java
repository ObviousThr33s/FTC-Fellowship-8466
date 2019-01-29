package org.firstinspires.ftc.teamcode.Samwise.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Samwise.SamwiseArm.HoldPositionTestArm;

@TeleOp(name = "Hold Position Test", group = "tests")
public class HoldPositionTestOp extends OpMode
{
    HoldPositionTestArm arm = null;

    @Override
    public void init()
    {
        this.arm = new HoldPositionTestArm(super.hardwareMap);
    }

    @Override
    public void loop()
    {
        if (super.gamepad1.dpad_left)
        {
            this.arm.mdriveJ1(true);
        }
        else
        {
            this.arm.mstopJ1();
        }
        if (super.gamepad1.dpad_right)
        {
            this.arm.mdriveJ1(false);
        }
        else
        {
            this.arm.mstopJ1();
        }


        this.arm.mdriveJ2(super.gamepad1.left_stick_y);
        this.arm.mdriveJ3(super.gamepad1.right_stick_y);

        if (super.gamepad1.dpad_up)
        {
            this.arm.moveJ4Up();
        }
        if (super.gamepad1.dpad_down)
        {
            this.arm.moveJ4Down();
        }
        if (!super.gamepad1.dpad_up && !super.gamepad1.dpad_down)
        {
            this.arm.stopJ4();
        }

        // collection and deposit
        while (gamepad1.left_trigger > 0)
        {
            arm.collectMinerals();
        }

        while (gamepad1.right_trigger > 0)
        {
            arm.depositMinerals();
        }

        if (gamepad1.left_trigger < 0.1 && gamepad1.right_trigger <0.1)
        {
            arm.stopServo();
        }
    }
}
