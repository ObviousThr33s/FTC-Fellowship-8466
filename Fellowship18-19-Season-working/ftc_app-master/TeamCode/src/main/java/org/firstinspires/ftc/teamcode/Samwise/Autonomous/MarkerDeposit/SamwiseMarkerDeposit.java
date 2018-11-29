package org.firstinspires.ftc.teamcode.Samwise.Autonomous.MarkerDeposit;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.AbstractPhysical.MotorsAndServos;

//Version by Kyle
public class SamwiseMarkerDeposit {

    public Servo servo9 = null;  /*Probably rename servo to a useful number when
                                we figure out the hardware map diagram of it*/

    //public static double servo9_HOME = 0.0;

    public static final  Servo.Direction direction = Servo.Direction.FORWARD;

    final double servo9_SPEED = 0.04;

    public void init(HardwareMap ahwmap) {

        servo9 = ahwmap.servo.get("marker");

        System.out.println("SamwiseMarkerDeposit.init : initialized");

        servo9.setDirection(direction);

    }

    public void move(double d) {
        servo9.setPosition(d);
    }


}