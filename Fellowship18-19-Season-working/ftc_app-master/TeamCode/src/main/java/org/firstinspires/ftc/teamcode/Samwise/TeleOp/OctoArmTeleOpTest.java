package org.firstinspires.ftc.teamcode.Samwise.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Samwise.DriveTrain.SamwiseDriveTrain;
import org.firstinspires.ftc.teamcode.Samwise.SamwiseArm.OctoSamwiseGenius;

@TeleOp(name = "Octo Arm Test", group = "tests")
//@Disabled
public class OctoArmTeleOpTest extends OpMode
{
    private OctoSamwiseGenius armStuff;
    private SamwiseDriveTrain driveTrain;

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
/******************************** Gamepad 1 *******************************************************/
        //------------- a ----------------
        if (gamepad1.a)
        {
            armStuff.lowerJ4();
        }
        //------------- b ----------------
        // to collection position
        if (gamepad1.b)
        {
            armStuff.stopSam();
            if (Math.abs(armStuff.getJ1CurrentPosition()) < 100 && Math.abs(armStuff.getJ2CurrentPosition()) < 100 && Math.abs(armStuff.getJ3CurrentPosition()) < 100)
            {
                armStuff.toCollectionPlane();
                isHoldingJ2 = false;
                isHoldingJ3 = false;
            }
            else
            {
                //                armStuff.saveLanderPosition();
                armStuff.backFromLander();
                isHoldingJ2 = false;
                isHoldingJ3 = false;
            }
        }

        //------------- x ----------------
        // to deposit position
        if (gamepad1.x)
        {
            armStuff.stopSam();
            armStuff.toLander();
        }

        //------------- y ----------------
        if (gamepad1.y)
        {
            armStuff.extendL1Auto();
        }

        //------------- dpad ----------------
        if (gamepad1.dpad_up){
            armStuff.toCollectionPlane();
        }
        if (gamepad1.dpad_left)
        {
            armStuff.toPositionWithoutJ1(armStuff.pos2J2, armStuff.pos2J2, armStuff.pos2J3);
        }
        if (gamepad1.dpad_right)
        {
            armStuff.toPositionWithoutJ1(armStuff.pos3J2, armStuff.pos3J2, armStuff.pos3J3);
        }
        if (gamepad1.dpad_down)
        {
            armStuff.toPositionWithoutJ1(armStuff.pos4J2, armStuff.pos4J2, armStuff.pos4J3);
        }

        //------------- triggers ----------------
        // collection and deposit
        if (gamepad1.left_trigger > 0.1)
        {
            armStuff.collectMinerals();
        }

        if (gamepad1.right_trigger > 0.1)
        {
            armStuff.depositMinerals();
        }

        if (gamepad1.left_trigger < 0.1 && gamepad1.right_trigger < 0.1)
        {
            armStuff.stopCollecting();
        }

        //------------- bumpers----------------
        if (gamepad1.left_bumper)
        {
            armStuff.moveJ4Down();
        }
        else if (gamepad1.right_bumper)
        {
            armStuff.moveJ4Up();
        }
        else
        {
            armStuff.stopJ4();
        }

        //------------- left_stick_x----------------
            if (gamepad1.left_stick_x > 0.2 /*&& (armStuff.getJ1CurrentPosition() < J1_MAX_TICKS && (armStuff.getJ1CurrentPosition() < J1_RIGHT_PHONE || !armStuff.isPhoneJ2()))*/)
            {
                armStuff.driveJ1(gamepad1.left_stick_x);
            }
            else if (gamepad1.left_stick_x < -0.2 /*&& (armStuff.getJ1CurrentPosition() > J1_MIN_TICKS && (armStuff.getJ1CurrentPosition() > J1_LEFT_PHONE || !armStuff.isPhoneJ2()))*/)
            {
                armStuff.driveJ1(gamepad1.left_stick_x);
            }
            else
            {
                armStuff.stopJ1();
            }

        //------------- left_stick_y----------------
            if (gamepad1.left_stick_y > 0.2)
            {
                armStuff.driveJ2(gamepad1.left_stick_y);
            }
            else if (gamepad1.left_stick_y < -0.2)
            {
                armStuff.driveJ2(gamepad1.left_stick_y);
            }
            else
            {
                armStuff.stopJ2();
            }

        //------------- right_stick_x----------------
        // UNMAPPED FOR NOW

        //------------- right_stick_y----------------
        if (gamepad1.right_stick_y > 0.2)
        {
            armStuff.driveJ3(gamepad1.right_stick_y);
        }
        else if (gamepad1.right_stick_y < -0.2)
        {
            armStuff.driveJ3(gamepad1.right_stick_y);
        }
        else
        {
            armStuff.stopJ3();
        }


        /************************ gamepad 2 ************************/
        //------------- a ----------------
        // UNMAPPED

        //------------- b ----------------
        // UNMAPPED

        //------------- x ----------------
        // UNMAPPED

        //------------- y ----------------
        // UNMAPPED


        //------------- dpad ----------------
        if (gamepad2.dpad_up)
        {
            armStuff.stopSam();
            armStuff.toInitialPosition();
        }
        if (gamepad2.dpad_left)
        {
            armStuff.stop();
            armStuff.stopJ1();
            armStuff.stopJ2();
            armStuff.stopJ3();
            armStuff.stopJ4();
            armStuff.stopExtendL1();
            armStuff.stopExtendL2();
        }


        //------------- triggers ----------------
        if (gamepad2.left_trigger>0.2)
        {
            armStuff.extendL1();
        }

        if (gamepad2.right_trigger>0.2)
        {
            armStuff.extendL2();
        }

        //------------- bumpers ----------------
        if (gamepad2.left_bumper)
        {
            armStuff.retractL1();
        }
        if (gamepad2.right_bumper)
        {
            armStuff.retractL2();
        }

        if (gamepad2.right_trigger<0.2 && !gamepad2.right_bumper)
        {
            armStuff.stopExtendL2();
        }

        if (!gamepad2.left_bumper && gamepad2.left_trigger<0.2)
        {
            armStuff.stopExtendL1();
        }

//        armStuff.hoverPlaneOfMotion(gamepad2.right_stick_y);
    }
}
