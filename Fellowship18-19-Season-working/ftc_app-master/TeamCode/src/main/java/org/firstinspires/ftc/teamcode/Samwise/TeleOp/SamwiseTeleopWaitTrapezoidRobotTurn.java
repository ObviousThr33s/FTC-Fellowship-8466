package org.firstinspires.ftc.teamcode.Samwise.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Samwise.SamwiseArm.SamwiseGenius;

import java.util.concurrent.TimeUnit;

@TeleOp(name = "Samwise Teleop Wait Trapezoid Turn", group = "Samwise")
//@Disabled

public class SamwiseTeleopWaitTrapezoidRobotTurn extends SamwiseTeleOp3
{
    private SamwiseGenius armStuff;

    private ElapsedTime runTime;

    static final double TRIGGER_SENSITIVITY = 0.2;
    static final double BUMPER_SENSITIVITY = 0.2;

    @Override
    public void init()
    {
        super.init();
        armStuff = new SamwiseGenius(hardwareMap);
        runTime = new ElapsedTime();
    }

    @Override
    public void loop()
    {
        super.loop();

        telemetry.addData("J1 ticks", armStuff.getJ1CurrentPosition());
        telemetry.addData("J2 ticks", armStuff.getJ2CurrentPosition() + ", " + armStuff.get2J2CurrentPosition());
        telemetry.addData("J3 ticks", armStuff.getJ3CurrentPosition());
        telemetry.addData("E1 ticks", armStuff.getE1CurrentPosition());
        telemetry.addData("Robot left ticks", armStuff.getRobotLeftTicks());
        telemetry.addData("Robot right ticks", armStuff.getRobotRightTicks());
        //        telemetry.update();
        /******************************** Gamepad 1 *******************************************************/
        //------------- a ----------------
        if (gamepad1.a && !gamepad1.start)
        {
            armStuff.lowerJ4();
        }
        //------------- b ----------------
        // to collection position
        if (gamepad1.b && !gamepad1.start)
        {
            runTime.reset();
            armStuff.toCollectionPlane(true, true);
            telemetry.addData("Time", runTime.time(TimeUnit.SECONDS));
        }

        //------------- x ----------------
        // to deposit position
        if (gamepad1.x)
        {
            runTime.reset();
            armStuff.toLanderSilver(true, true);
            telemetry.addData("Time ", runTime.time(TimeUnit.SECONDS));
        }

        //------------- y ----------------
        if (gamepad1.y)
        {
            runTime.reset();
            armStuff.toLanderGold(true, true);
            telemetry.addData("Time ", runTime.time(TimeUnit.SECONDS));
        }

        //------------- dpad ----------------
        // --------------- pom ---------------------

        if (gamepad1.dpad_up)
        {
            armStuff.up();
        }
        if (gamepad1.dpad_right)
        {
            armStuff.right();
        }
        if (gamepad1.dpad_down)
        {
            armStuff.down();
        }
        if (gamepad1.dpad_left)
        {
            armStuff.left();
        }

        //------------- triggers ----------------
        // plane of motion
        if (gamepad1.left_trigger > 0.1)
        {
            armStuff.hoverPlaneOfMotion(gamepad1.left_trigger);
        }
        if (gamepad1.right_trigger > 0.1)
        {
            armStuff.hoverPlaneOfMotion(-gamepad1.right_trigger);
        }
        if (gamepad1.left_trigger < 0.1 && gamepad1.right_trigger < 0.1)
        {
            armStuff.hoverPlaneOfMotion(0);
        }


        //------------- bumpers----------------
        // collection and deposit
  /*     if (gamepad1.left_bumper)
        {
            armStuff.depositMinerals();
        }
        else if (gamepad1.right_bumper)
        {
            armStuff.collectMinerals();
        }
        else
        {
            armStuff.stopCollecting();
        }*/

        //------------- left_stick_x----------------
        if (Math.abs(gamepad1.left_stick_x) > JOYSTICK_SENSITIVITY)
        {
            armStuff.manualDriveJ1(gamepad1.left_stick_x);
        }
        else
        {
            armStuff.manualStopJ1();
        }

        //------------- left_stick_y----------------
        if (Math.abs(gamepad1.left_stick_y) > JOYSTICK_SENSITIVITY)
        {
            armStuff.manualDriveJ2(gamepad1.left_stick_y);
        }
        else
        {
            armStuff.manualStopJ2();
        }

        //------------- right_stick_x----------------
        // UNMAPPED FOR NOW

        //------------- right_stick_y----------------
        if (Math.abs(gamepad1.right_stick_y) > JOYSTICK_SENSITIVITY)
        {
            armStuff.manualDriveJ3(gamepad1.right_stick_y);
        }
        else
        {
            armStuff.manualStopJ3();
        }


        /************************ gamepad 2 ************************/
        //------------- a ----------------
        // mapped in teleop3

        //------------- b ----------------
        // unmapped

        //------------- x ----------------
        // unmapped

        //------------- y ----------------
        if (gamepad2.y)
        {
            armStuff.stopSam();
            armStuff.toInitialPosition();
        }


        //------------- dpad ----------------
        if (gamepad2.dpad_up && gamepad2.x)
        {
            armStuff.extendL1();
        }
        if (gamepad2.dpad_down && gamepad2.x)
        {
            armStuff.retractL1();
        }

        if (!gamepad2.dpad_up && !gamepad2.dpad_down)
        {
            armStuff.stopExtendL1();
        }

        if (gamepad2.dpad_right && gamepad2.x)
        {
            armStuff.extendL2();
        }
        if (gamepad2.dpad_left && gamepad2.x)
        {
            armStuff.retractL2();
        }

        if (!gamepad2.dpad_left && !gamepad2.dpad_right)
        {
            armStuff.stopExtendL2();
        }


        //------------- triggers ----------------
        if (gamepad2.left_trigger > TRIGGER_SENSITIVITY)
        {
            armStuff.depositMinerals();
        }

        if (gamepad2.right_trigger > TRIGGER_SENSITIVITY)
        {
            armStuff.collectMinerals();
        }

        if (gamepad2.left_trigger < TRIGGER_SENSITIVITY && gamepad2.right_trigger < TRIGGER_SENSITIVITY)
        {
            armStuff.stopCollecting();
        }

        //------------- bumpers ----------------
        // left bumper: mapped in teleop3
        // right bumper: mapped in teleop3
        if (Math.abs(gamepad2.right_stick_x) < BUMPER_SENSITIVITY)
        {
            armStuff.stopJ4();
        }
        else if (gamepad2.right_stick_x > 0)
        {
            armStuff.moveJ4Up();
        }
        else
        {
            armStuff.moveJ4Down();
        }
    }
}
