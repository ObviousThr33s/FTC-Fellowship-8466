package org.firstinspires.ftc.teamcode.Samwise.Hanger;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class SamwiseHangerHardware {

    public DcMotor hangermotor1;
    public Servo hangerservo1;
    public boolean hookd;

    public SamwiseHangerHardware(HardwareMap hw){
        hangermotor1 = hw.dcMotor.get("hangermotor_1");
        hangerservo1 = hw.servo.get("hangerservo_1");
    }

    public void moveDown(){
        if (hookd == true){
            //Move until robot is down
        }
        if (hookd == false){
            //Dont move the hanger
        }
    }

    public void unHook(){
        if (hookd == true){
            //Unhook the robot
        }
        if (hookd == false){
            //Dont unhook the robot
        }
    }
}