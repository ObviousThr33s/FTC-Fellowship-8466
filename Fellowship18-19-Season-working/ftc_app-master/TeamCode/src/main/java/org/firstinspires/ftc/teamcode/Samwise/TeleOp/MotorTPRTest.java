package org.firstinspires.ftc.teamcode.Samwise.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name="test arm motor CPR", group="tests")
public class MotorTPRTest extends LinearOpMode
{
    private DcMotor testMotorEncoder1 = null;
    private DcMotor testMotorEncoder2 = null;
    private DcMotor testMotorEncoder3 = null;

    @Override
    public void runOpMode() throws InterruptedException
    {
        testMotorEncoder1 = hardwareMap.dcMotor.get("J1");
        testMotorEncoder2 = hardwareMap.dcMotor.get("J2");
        testMotorEncoder3 = hardwareMap.dcMotor.get("J3");
        testMotorEncoder3.setDirection(DcMotorSimple.Direction.REVERSE);
        testMotorEncoder1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        testMotorEncoder2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        testMotorEncoder3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        telemetry.addData("moter1 read: ", testMotorEncoder1.getCurrentPosition());
        telemetry.addData("moter2 read: ", testMotorEncoder2.getCurrentPosition());
        telemetry.addData("moter3 read: ", testMotorEncoder3.getCurrentPosition());
        telemetry.update();

        waitForStart();


        for (int i=0; i<8;i++)
        {
            this.runOnce();
        }

    }

    private void runOnce()
    {
        testMotorEncoder1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        testMotorEncoder2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        testMotorEncoder3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        testMotorEncoder1.setTargetPosition(1120);
        testMotorEncoder2.setTargetPosition(560);
        testMotorEncoder3.setTargetPosition(560);

        testMotorEncoder1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        testMotorEncoder2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        testMotorEncoder3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        testMotorEncoder1.setPower(0.5);
        testMotorEncoder2.setPower(0.5);
        testMotorEncoder3.setPower(0.5);
        while(this.opModeIsActive() && (testMotorEncoder1.isBusy() ||testMotorEncoder2.isBusy() || testMotorEncoder3.isBusy()))
        {}

        telemetry.addData("moter1 read: ", testMotorEncoder1.getCurrentPosition());
        telemetry.addData("moter2 read: ", testMotorEncoder2.getCurrentPosition());
        telemetry.addData("moter3 read: ", testMotorEncoder3.getCurrentPosition());
        telemetry.update();
        this.sleep(2500);
    }

}
