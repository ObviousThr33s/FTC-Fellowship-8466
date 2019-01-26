package org.firstinspires.ftc.teamcode.Samwise.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp (name = "Test1", group="tests")
public class TheUltimateMichaelTestClass extends OpMode {
    public DcMotor MotorNo1 = null;
    public DcMotor MotorNo2 = null;
    int Motor2Pos;
    public void init() {
         MotorNo1 = hardwareMap.dcMotor.get("motor1");
         MotorNo2 = hardwareMap.dcMotor.get("motor2");
         MotorNo1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
         MotorNo2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
         MotorNo1.setPower(0);
         MotorNo2.setPower(0);
         MotorNo1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
         MotorNo2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
     }
    public void loop() {
         float Motor1Pos = gamepad1.left_stick_y *3000;
         MotorNo1.setTargetPosition((int)1120);

         Motor2Pos = MotorNo1.getCurrentPosition();
         System.out.println("===>"+Motor2Pos);
         MotorNo2.setTargetPosition(1120);
         System.out.println("===>" +Motor1Pos);
         System.out.println("===>"+Motor2Pos);
         MotorNo1.setPower(0.3);
         MotorNo2.setPower(0.3);

         MotorNo1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
         MotorNo2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

     }
}
