package org.firstinspires.ftc.teamcode.Samwise.TeleOp;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp (name = "no encoder test", group="tests")
public class ArmTest4 extends OpMode{
    private DcMotor Motor1_2 = null;
    private DcMotor Motor2_2 = null;
    private DcMotor Motor3_2 = null;

    //J1 stuff
    static final double BigGearCount = 120.0;
    static final double SmallGearCount = 24.0;
    static final double EncoderCountJ1 = 1680.0;
    static final double MaxOrMinDegrees = 45.0;
    private double J1GearRatio = BigGearCount/SmallGearCount;
    private double TickPerDegreeJ1 = EncoderCountJ1/360.0;
    private double BigToSmallRatio = MaxOrMinDegrees * J1GearRatio;
    private double SmallDegreeToTicks = BigToSmallRatio * TickPerDegreeJ1;
    private double TickPerDegree = 1120.0/360.0;
    public void init(){
        Motor1_2 = hardwareMap.dcMotor.get("motor1");
        Motor2_2 = hardwareMap.dcMotor.get("motor2");
        Motor3_2 = hardwareMap.dcMotor.get("motor3");
        Motor1_2.setPower(0);
        Motor2_2.setPower(0);
        Motor3_2.setPower(0);
        Motor3_2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Motor3_2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Motor1_2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Motor1_2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void loop(){
        Motor2_2.setPower(gamepad1.left_stick_y);
        double J2CurrentPos_deg;
        double J3TargetPos_deg;
        double J3TargetPos_Ticks;
        double a, b, c, c_1, d;
        J2CurrentPos_deg = (Motor2_2.getCurrentPosition() / TickPerDegree);
        a = Math.cos(Math.toRadians(180.0 - J2CurrentPos_deg));
        b = (6 * a - 2) / 6;
        c = Math.acos(b);
        c_1 = Math.toDegrees(c);
        d = c_1 + 180.0 - J2CurrentPos_deg;
        J3TargetPos_deg = d;
        J3TargetPos_Ticks = J3TargetPos_deg * TickPerDegree;
        Motor3_2.setTargetPosition((int)J3TargetPos_Ticks);
        Motor3_2.setPower(0.5);
        Motor3_2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //J1 stuff
        double RotationJoint = gamepad1.right_stick_x * SmallDegreeToTicks; // rotation of J1
        Motor1_2.setTargetPosition((int)RotationJoint); //Joint 1 (turn table)
        Motor1_2.setPower(0.7);
        Motor1_2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if (gamepad1.b) {
            Motor1_2.setPower(0);
            Motor2_2.setPower(0);
            Motor3_2.setPower(0);
        }
    }
    public void stop(){
        Motor1_2.setPower(0);
        Motor2_2.setPower(0);
        Motor3_2.setPower(0);
    }
}
