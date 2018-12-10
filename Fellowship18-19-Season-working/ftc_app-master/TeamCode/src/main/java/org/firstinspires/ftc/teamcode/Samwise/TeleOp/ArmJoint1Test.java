/*package org.firstinspires.ftc.teamcode.Samwise.TeleOp;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp (name = "ArmJoint1Test")
public class ArmJoint1Test extends OpMode {
    public DcMotor Joint1Motor = null;

    public void init() {
        Joint1Motor = hardwareMap.dcMotor.get("Table");
        Joint1Motor.setPower(0);
        Joint1Motor.setDirection(DcMotor.Direction.REVERSE);
        Joint1Motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Joint1Motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }


}
*/