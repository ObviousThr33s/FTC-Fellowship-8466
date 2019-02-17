package org.firstinspires.ftc.teamcode.Samwise.TeleOp.TestTeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Samwise.SamwiseArm.HoldPositionTestArm;

@TeleOp(name = "Hold Position Test", group = "tests")
//@Disabled
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

        //if(gamepad1.dpad_left){

        //    arm.mdriveJ1(true);
        //}else if (!gamepad1.dpad_right){
        //    arm.mstopJ1();
        //}

        //if(gamepad1.dpad_right){
        //    arm.mdriveJ1(false);
        //}else if (!gamepad1.dpad_left){
        //    arm.mstopJ1();
        //}
        if (gamepad1.dpad_left) {
            arm.J1MovementLeft();
        }
        if (gamepad1.dpad_right) {
            arm.J1MovementRight();
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
            arm.stopCollecting();
        }

        super.telemetry.addData("J1 ticks: ", this.arm.getJ1CurrentPosition());
        super.telemetry.addData("J2 ticks: ", this.arm.getJ2CurrentPosition());
        super.telemetry.addData("J3 ticks: ", this.arm.getJ3CurrentPosition());
    }
}
