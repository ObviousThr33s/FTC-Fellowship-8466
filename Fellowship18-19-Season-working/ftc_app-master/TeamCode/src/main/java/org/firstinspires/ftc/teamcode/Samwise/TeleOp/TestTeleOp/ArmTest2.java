package org.firstinspires.ftc.teamcode.Samwise.TeleOp.TestTeleOp;



import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp(name = "Michael's motor test", group="tests")
@Disabled

public class ArmTest2 extends OpMode {
    public DcMotor ArmJ1 = null;

    public void init(){
        ArmJ1 = hardwareMap.dcMotor.get("Joint1");
        ArmJ1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ArmJ1.setDirection(DcMotor.Direction.REVERSE);
        ArmJ1.setPower(0);
        ArmJ1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void loop(){
        float ArmSpeed = gamepad1.right_stick_y;
        if (gamepad1.left_stick_y > 0.2) {
            ArmJ1.setTargetPosition(9999999);
            ArmJ1.setPower(gamepad1.left_stick_y);

        } else {
            ArmJ1.setPower(0);
        }
        ArmJ1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if(gamepad1.b) {
            ArmJ1.setPower(0);
        }
    }


}
