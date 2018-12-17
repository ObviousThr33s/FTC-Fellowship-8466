

package org.firstinspires.ftc.teamcode.Samwise.TeleOp;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp (name = "ArmJoint1Test")
public class ArmJoint1Test extends OpMode {
    private DcMotor Joint1Motor = null;
    private int SmallGearCount = 24;
    private int J1GearRatio = 120/SmallGearCount;
    private int EncoderCount = 1680;
    private int MaxOrMinDegrees = 45;
    private int EncoderToDegrees = EncoderCount/360;
    private int BigToSmallRatio = MaxOrMinDegrees * J1GearRatio;
    private int SmallDegreeToTicks = BigToSmallRatio * EncoderToDegrees;
    public void init() {
        Joint1Motor = hardwareMap.dcMotor.get("joint1motor");
        Joint1Motor.setPower(0);
        Joint1Motor.setDirection(DcMotor.Direction.REVERSE);
        Joint1Motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Joint1Motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void loop() {

        float ArmJoint1 = gamepad1.left_stick_x * SmallDegreeToTicks;
        Joint1Motor.setTargetPosition((int)ArmJoint1);

        Joint1Motor.setPower(0.8);
        Joint1Motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }


}
