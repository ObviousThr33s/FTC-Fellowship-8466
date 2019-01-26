package org.firstinspires.ftc.teamcode.Samwise.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="MoterTest", group="tests")
public class MotorTest extends OpMode {

    DcMotor m1;
    DcMotor m2;
    DcMotor m3;
    DcMotor m4;
    DcMotor m5;
    DcMotor m6;
    DcMotor m7;

    boolean[] motorsWorkingTested = {false,false,false,false,false,false,false};
    DcMotor[] motors = {m1, m2, m3, m4, m5, m6, m7};

    //Number of motors working/not woriking
    int working;
    int notWorking;

    //the motor being tested
    int currentMotor = 0;

    public void testMotor(DcMotor motor, String name, int number){
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        int pos = motor.getCurrentPosition();

        try {
            motor = hardwareMap.dcMotor.get(name);

            motor.setPower(0.5);

            for(int a = 10; pos <= a;){
                pos = motor.getCurrentPosition();
                if(pos == a){
                    motor.setPower(0);
                    motorsWorkingTested[number] = true;
                }
            }

        }catch(Exception e) {
            motor = null;
            motorsWorkingTested[number] = false;
            telemetry.addLine(name+" Failed");
        }

        telemetry.addData( name+" SuccessFullTest=", motorsWorkingTested[number]);
    }

    @Override
    public void init() {
        testMotor(m1, "m1", 1);
        testMotor(m2, "m2", 2);
        testMotor(m3, "m3", 3);
        testMotor(m4, "m4", 4);
        testMotor(m5, "m5", 5);
        testMotor(m6, "m6", 6);
        testMotor(m7, "m7", 7);

        for(int a = 0; a < motors.length; a++){
            if(motorsWorkingTested[a] = true){
                working++;
            }else{
                notWorking++;
            }
        }

        telemetry.addData("Motors working:", working);
        telemetry.addData("Motors not working:", notWorking);
        if(working == motors.length){
            telemetry.addLine("All motors working");
        }
    }

    @Override
    public void loop() {
        telemetry.clearAll();
        telemetry.addLine("Motor:"+currentMotor);


        if(gamepad1.a){
            if(currentMotor<=motors.length) {
                currentMotor++;
            }else if(currentMotor > motors.length){
                motors[currentMotor].setPower(0);
                currentMotor = 0;
            }
        }

        if(gamepad1.right_bumper){
            motors[currentMotor].setPower(.5);
            telemetry.addLine("Current Motor Running="+currentMotor);
        }else if(!gamepad1.right_bumper){
            motors[currentMotor].setPower(0);
        }
    }
}