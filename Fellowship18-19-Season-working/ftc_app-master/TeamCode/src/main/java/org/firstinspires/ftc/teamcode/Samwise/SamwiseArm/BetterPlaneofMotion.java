package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Samwise.TeleOp.OctoArmTeleOp;

//@TeleOp (name = "Planeofmotion2")
@TeleOp(name = "Samwise: Teleop Tank 4")
public class BetterPlaneofMotion extends OctoArmTeleOp {
    private DcMotor J1 = null;
    private DcMotor J2 = null;
    private DcMotor J22 = null;
    private DcMotor J3 = null;

    boolean goingforward = false;

    int J3targetpos;
    int J2targetpos;
    //J2, J3 Static
    static final double EncoderCountJ2 = 1680.0; //number of ticks per motor round
    static final double EncoderCountJ3 = 1680.0; //number of ticks per motor round

    private double TickPerDegreeJ3 = EncoderCountJ3/360.0;
    private double TickPerDegreeJ2 = EncoderCountJ2/360.0;

    public void init() {
        //super.init();

        J1 = hardwareMap.dcMotor.get("J1");
        J2 = hardwareMap.dcMotor.get("1J2");
        J22 = hardwareMap.dcMotor.get("2J2");
        J3 = hardwareMap.dcMotor.get("J3");
        J1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        J2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        J3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        J22.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        J22.setPower(0);
        J1.setPower(0);
        J2.setPower(0);
        J3.setPower(0);
        J1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        J2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        J3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        J22.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void loop() {
        //super.loop();

        //if(!isPlaneOfMotion){
            //return;
       // }
        if (Math.abs(gamepad1.left_stick_y) > 0.1) {
            if (gamepad1.left_stick_y>0.1) {
                J3.setTargetPosition(500);
                goingforward = true;
            } if (gamepad1.left_stick_y <-0.1) {
                J2.setTargetPosition(-500);
                J22.setTargetPosition(-500);
                goingforward = false;
            }
        }
        J2targetpos = (int)(J3.getCurrentPosition()/ 1.6);
        J3targetpos = (int)(J2.getCurrentPosition()* 1.6);
        if (goingforward == true){
            J3.setPower(Math.abs(gamepad1.left_stick_y)/2);
            J3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            J2.setTargetPosition(J2targetpos);
            J22.setTargetPosition(J2targetpos);
            J2.setPower(0.3);
            J22.setPower(0.3);
            J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            J22.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            if ((gamepad1.left_stick_y<0.1)&&(gamepad1.left_stick_y>-0.1)) {
                J3.setPower(0);
            }
        } if (goingforward == false) {
            J2.setPower(Math.abs(gamepad1.left_stick_y)/2);
            J22.setPower(Math.abs(gamepad1.left_stick_y)/2);
            J22.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            J3.setTargetPosition(J3targetpos);
            J3.setPower(0.3);
            J3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            if ((gamepad1.left_stick_y<0.1)&&(gamepad1.left_stick_y>-0.1)) {
                J2.setPower(0);
                J22.setPower(0);
            }
        }

    }
}
