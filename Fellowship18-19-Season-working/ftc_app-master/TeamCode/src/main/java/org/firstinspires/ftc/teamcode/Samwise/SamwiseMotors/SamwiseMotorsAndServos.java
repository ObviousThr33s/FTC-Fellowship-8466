package org.firstinspires.ftc.teamcode.Samwise.SamwiseMotors;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.AbstractPhysical.MotorsAndServos;
import org.firstinspires.ftc.teamcode.Samwise.Collection.SamwiseCollectionHardware;
import org.firstinspires.ftc.teamcode.Samwise.Conceptual.SamwiseRobot;

public class SamwiseMotorsAndServos extends SamwiseRobot{
    //Hanger Motors and Servos
    public DcMotor hangermotor1 = hm.dcMotor.get("hangermotor_1");
    public Servo hangerservo1 = hm.servo.get("hangerservo_1");

    //Collection Motors and Servos
    public DcMotor motor1 = hm.dcMotor.get("motor_1");
    public DcMotor motor2 = hm.dcMotor.get("motor_2");
    public DcMotor motor3 = hm.dcMotor.get("motor_3");
    public DcMotor motor4 = hm.dcMotor.get("motor_4");

    public Servo servo1 = hm.servo.get("servo_1");
    public Servo servo2 = hm.servo.get("servo_2");
    public Servo servo3 = hm.servo.get("servo_3");
}