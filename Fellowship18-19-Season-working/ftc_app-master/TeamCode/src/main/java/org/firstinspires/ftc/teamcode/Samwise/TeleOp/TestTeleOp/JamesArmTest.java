package org.firstinspires.ftc.teamcode.Samwise.TeleOp.TestTeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Samwise.SamwiseArm.TRexSamwiseGenius;

@TeleOp (name = "JamesArmTest", group="tests")
//@Disabled
public class JamesArmTest extends OpMode{

    TRexSamwiseGenius sa;

    DcMotor j1;
    DcMotor j2;
    DcMotor j3;

    @Override
    public void init() {
        sa = new TRexSamwiseGenius(hardwareMap);
        //DcMotor j1 = sa.motorJ1;
        //DcMotor j2 = sa.motorJ2;
        //DcMotor j3 = sa.motorJ3;
        sa.hoverPlaneOfMotion(1.0);
    }

    @Override
    public void loop() {
        if(gamepad1.dpad_up){
            //sa.hoverPlaneOfMotion();
        }
    }
}
