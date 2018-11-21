package org.firstinspires.ftc.teamcode.Samwise.Collection;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.MotorControlAlgorithm;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.AbstractPhysical.MotorsAndServos;
import org.firstinspires.ftc.teamcode.Samwise.SamwiseMotors.SamwiseMotorsAndServos;


public class SamwiseCollectionHardware extends SamwiseMotorsAndServos {

    public DcMotor motor1 = null;
    public DcMotor motor2 = null;
    public DcMotor motor3 = null;
    public DcMotor motor4 = null;

    public Servo servo1 = null;
    public Servo servo2 = null;
    public Servo servo3 = null;

    public SamwiseCollectionHardware(HardwareMap ahwMap){
        //Define names on the phone

        motor1.setDirection(DcMotor.Direction.FORWARD);
        motor2.setDirection(DcMotor.Direction.REVERSE);
        motor3.setDirection(DcMotor.Direction.FORWARD);
        motor4.setDirection(DcMotor.Direction.REVERSE);
    }

}
