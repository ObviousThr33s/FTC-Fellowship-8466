package org.firstinspires.ftc.teamcode.Samwise.TeleOp.TestTeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "ArmTest1 Gamepad Input", group="tests")
@Disabled
public class GamepadInputTest extends LinearOpMode
{

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException
    {
        waitForStart();
        while (opModeIsActive())
        {
            this.inputTest();
        }
    }

    private void inputTest()
    {
        if (gamepad1.a)
        {
            System.out.println("gamepad1.a");
        }
        if (gamepad1.b)
        {
            System.out.println("gamepad1.b");
        }
        if (gamepad1.x)
        {
            System.out.println("gamepad1.x");
        }
        if (gamepad1.y)
        {
            System.out.println("gamepad1.y");
        }
        if (gamepad1.dpad_down)
        {
            System.out.println("gamepad1.dpad_down");
        }
        if (gamepad1.dpad_up)
        {
            System.out.println("gamepad1.dpad_up");
        }
        if (gamepad1.dpad_right)
        {
            System.out.println("gamepad1.dpad_right");
        }
        if (gamepad1.dpad_left)
        {
            System.out.println("gamepad1.dpad_left");
        }
        if (gamepad1.left_bumper)
        {
            System.out.println("gamepad1.left_bumper");
        }
        if (gamepad1.right_bumper)
        {
            System.out.println("gamepad1.right_bumper");
        }
        if (gamepad1.left_stick_button)
        {
            System.out.println("gamepad1.left_stick_button");
        }
        if (gamepad1.right_stick_button)
        {
            System.out.println("gamepad1.right_stick_button");
        }
        if (gamepad1.start)
        {
            System.out.println("gamepad1.start");
        }
        if (gamepad1.guide)
        {
            System.out.println("gamepad1.guide");
        }

        runtime.reset();
        if (gamepad1.back)
        {
            System.out.println("gamepad1.back");
        }
//        System.out.println("Runtime: "+runtime.toString());

        if (gamepad1.left_trigger>0 || gamepad1.left_trigger<0)
        {
            System.out.println("gamepad1.left_trigger: "+gamepad1.left_trigger);
        }

        if (gamepad1.right_trigger>0 || gamepad1.right_trigger<0)
        {
            System.out.println("gamepad1.right_trigger: "+gamepad1.right_trigger);
        }
        if (gamepad1.left_stick_x>0 || gamepad1.left_stick_x<0)
        {
            System.out.println("gamepad1.left_stick_x: "+gamepad1.left_stick_x);
        }

        if (gamepad1.left_stick_y>0 || gamepad1.left_stick_y<0)
        {
            System.out.println("gamepad1.left_stick_y: "+gamepad1.left_stick_y);
        }
        if (gamepad1.right_stick_x>0 || gamepad1.right_stick_x<0)
        {
            System.out.println("gamepad1.right_stick_x: "+gamepad1.right_stick_x);
        }

        if (gamepad1.right_stick_y>0 || gamepad1.right_stick_y<0)
        {
            System.out.println("gamepad1.right_stick_y: "+gamepad1.right_stick_y);
        }
    }
}
