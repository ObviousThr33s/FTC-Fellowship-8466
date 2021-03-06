package org.firstinspires.ftc.teamcode.Samwise.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Samwise.SamwiseArm.OctoSamwiseGenius;
import org.firstinspires.ftc.teamcode.Samwise.SamwiseArm.TrapezoidSpeed;

import java.util.concurrent.TimeUnit;

@TeleOp(name = "Samwise: Teleop Tank 3", group = "Samwise")
@Disabled
public class OctoArmTeleOp extends SamwiseTeleOp3
{
    private OctoSamwiseGenius armStuff;
    private TrapezoidSpeed trapezoid;

    public boolean isPlaneOfMotion = false;

    ElapsedTime runTime;

    @Override
    public void init()
    {
        super.init();
        armStuff = new OctoSamwiseGenius(hardwareMap);
        runTime = new ElapsedTime();
        trapezoid = new TrapezoidSpeed();
        armStuff.stopJ1();
        armStuff.stopJ2();
        armStuff.stopJ3();
    }

    @Override
    public void loop()
    {

        super.loop();

        telemetry.addData("J1 ticks", armStuff.getJ1CurrentPosition());
        telemetry.addData("J2 ticks", armStuff.getJ2CurrentPosition() + ", " + armStuff.get2J2CurrentPosition());
        telemetry.addData("J3 ticks", armStuff.getJ3CurrentPosition());
        telemetry.addData("E1 ticks", armStuff.getE1CurrentPosition());
//        telemetry.update();
        /******************************** Gamepad 1 *******************************************************/
        //------------- a ----------------
            if (gamepad1.a)
            {
//            armStuff.lowerJ4();
                armStuff.testTrapezoid();
            }
            //------------- b ----------------
            // to collection position
            if (gamepad1.b)
            {
                armStuff.stopSam();
                if (Math.abs(armStuff.getJ1CurrentPosition()) < 100 && Math.abs(armStuff.getJ2CurrentPosition()) < 100 && Math.abs(armStuff.getJ3CurrentPosition()) < 100)
                {
                    armStuff.toCollectionPlane();
                }
                else
                {
                    runTime.reset();
                    armStuff.toCrater();
                    telemetry.addData("Time", runTime.time(TimeUnit.SECONDS));
                }
            }

            //------------- x ----------------
            // to deposit position
            if (gamepad1.x)
            {
                runTime.reset();
                armStuff.toLanderSilver();
                telemetry.addData("Time ", runTime.time(TimeUnit.SECONDS));
            }

            //------------- y ----------------
            if (gamepad1.y)
            {
                //            armStuff.stopAll();
                runTime.reset();
                armStuff.toLanderGold();
            telemetry.addData("Time ", runTime.time(TimeUnit.SECONDS));
        }

        //------------- dpad ----------------
        /*if (gamepad1.dpad_up)
        {
            armStuff.up();
        }*/
        if (gamepad1.dpad_right)
        {
            armStuff.right();
        }
        /*if (gamepad1.dpad_down)
        {
            armStuff.down();
        }/*/
        if (gamepad1.dpad_left)
        {
            armStuff.left();
        }

        //------------- triggers ----------------
        // collection and deposit
        if (gamepad1.left_trigger > 0.1)
        {
            if (armStuff.isInCollectionPlane()) armStuff.toCollectionPlane();
        }

        if (gamepad1.right_trigger > 0.1)
        {
            if (armStuff.isInCollectionPlane())
                armStuff.toPositionWithoutJ1(armStuff.pos2J2, armStuff.pos2J3);
        }

        if (gamepad1.left_bumper)
        {
            if (armStuff.isInCollectionPlane())
                armStuff.toPositionWithoutJ1(armStuff.pos3J2, armStuff.pos3J3);
        }

        if (gamepad1.right_bumper)
        {
            if (armStuff.isInCollectionPlane())
                armStuff.toPositionWithoutJ1(armStuff.pos4J2, armStuff.pos4J3);
        }

        //------------- bumpers----------------
        //Currently available
        //------------- left_stick_x----------------
        if (gamepad1.left_stick_x > 0.1 /*&& (armStuff.getJ1CurrentPosition() < J1_MAX_TICKS && (armStuff.getJ1CurrentPosition() < J1_RIGHT_PHONE || !armStuff.isPhoneJ2()))*/)
        {
            //            System.out.println("Time at beginning of \"driveJ1\""+System.currentTimeMillis());
            armStuff.driveJ1(gamepad1.left_stick_x);
        }
        else if (gamepad1.left_stick_x < -0.1 /*&& (armStuff.getJ1CurrentPosition() > J1_MIN_TICKS && (armStuff.getJ1CurrentPosition() > J1_LEFT_PHONE || !armStuff.isPhoneJ2()))*/)
        {
            //            System.out.println("Time at beginning of \"driveJ1\""+System.currentTimeMillis());
            armStuff.driveJ1(gamepad1.left_stick_x);
        }
        else
        {
            armStuff.stopJ1();
        }

        //------------- left_stick_y----------------
        if(gamepad1.dpad_up) {
            isPlaneOfMotion = true;
        } if (gamepad1.dpad_down) {
        isPlaneOfMotion = false;
    }

        if (!isPlaneOfMotion) {
            if (gamepad1.left_stick_y > 0.1) {
                armStuff.driveJ2(gamepad1.left_stick_y);
            } else if (gamepad1.left_stick_y < -0.1) {
                armStuff.driveJ2(gamepad1.left_stick_y);
            } else {
                armStuff.stopJ2();
            }

            //------------- right_stick_x----------------
            //Currently available
            //------------- right_stick_y----------------
            if (gamepad1.right_stick_y > 0.1) {
                armStuff.driveJ3(gamepad1.right_stick_y);
            } else if (gamepad1.right_stick_y < -0.1) {
                armStuff.driveJ3(gamepad1.right_stick_y);
            } else {
                armStuff.stopJ3();
            }
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
        if (gamepad2.left_trigger > 0.2)
        {
            armStuff.depositMinerals();
        }

        if (gamepad2.right_trigger > 0.2)
        {
            armStuff.collectMinerals();
        }

        if (gamepad2.left_trigger < 0.2 && gamepad2.right_trigger < 0.2)
        {
            armStuff.stopCollecting();
        }

        //------------- bumpers ----------------
        // left bumper: mapped in teleop3
        // right bumper: mapped in teleop3
        if (Math.abs(gamepad2.right_stick_x) < 0.2)
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
//        armStuff.hoverPlaneOfMotion(gamepad1.right_stick_x);
    }
}
