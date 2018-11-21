package org.firstinspires.ftc.teamcode.Samwise.Hanger;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class SamwiseHanger {

    public DcMotor hangermotor1;
    public Servo hangerservo1;

    public SamwiseHanger(HardwareMap hw){
        hangermotor1 = hw.dcMotor.get("hangermotor_1");
        hangerservo1 = hw.servo.get("hangerservo_1");
    }

    public void down(){
        //Move down with motor and wait
        //Unlatch with servo
    }

}
