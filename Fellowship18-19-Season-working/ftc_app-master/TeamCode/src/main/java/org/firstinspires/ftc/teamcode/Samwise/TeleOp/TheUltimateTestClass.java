package org.firstinspires.ftc.teamcode.Samwise.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp (name = "Test1")
public class TheUltimateTestClass extends OpMode {
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
         MotorNo1.setTargetPosition((int)Motor1Pos);

         Motor2Pos = MotorNo1.getCurrentPosition();
         MotorNo2.setTargetPosition(Motor2Pos);
         System.out.println("===>" +Motor1Pos);
         System.out.println("===>"+Motor2Pos);
         MotorNo1.setPower(0.8);
         MotorNo2.setPower(0.8);

         MotorNo1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
         MotorNo2.setMode(DcMotor.RunMode.RUN_TO_POSITION);


     }
}
