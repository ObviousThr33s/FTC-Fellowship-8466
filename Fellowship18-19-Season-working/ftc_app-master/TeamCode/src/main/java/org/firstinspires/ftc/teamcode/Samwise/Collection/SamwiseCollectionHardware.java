package org.firstinspires.ftc.teamcode.Samwise.Collection;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class SamwiseCollectionHardware {

    public DcMotor motor1 = null;
    public DcMotor motor2 = null;
    public DcMotor motor3 = null;
    public DcMotor motor4 = null;

    public Servo servo1 = null;
    public Servo servo2 = null;
    public Servo servo3 = null;

    public void init(HardwareMap ahwMap) {
        //Define names on the phone
        motor1 = ahwMap.dcMotor.get("motor_1");
        motor2 = ahwMap.dcMotor.get("motor_2");
        motor3 = ahwMap.dcMotor.get("motor_3");
        motor4 = ahwMap.dcMotor.get("motor_4");

        motor1.setDirection(DcMotor.Direction.FORWARD);
        motor2.setDirection(DcMotor.Direction.REVERSE);
        motor3.setDirection(DcMotor.Direction.FORWARD);
        motor4.setDirection(DcMotor.Direction.REVERSE);


        servo1 = ahwMap.servo.get("servo_1");
        servo2 = ahwMap.servo.get("servo_2");
        servo3 = ahwMap.servo.get("servo_3");
    }

    enum armMovement{
        FORWARD,
        BACKWARD
    }

    enum graberState{
        OPEN,
        CLOSED
    }

    public void move(armMovement direction, double distance){
        if(direction == armMovement.FORWARD){
            //Set motor powers to move indicated distance
        }
        if(direction == armMovement.BACKWARD){
            //Set motor powers to move indicated distance
        }
    }

    public void grab(graberState state){
        if(state == graberState.OPEN){
            //Move motor 4 (needs verification) forward/backward
        }
        if(state == graberState.CLOSED){
            //Move motor 4 (needs verification) backward/forawad
        }
    }



}