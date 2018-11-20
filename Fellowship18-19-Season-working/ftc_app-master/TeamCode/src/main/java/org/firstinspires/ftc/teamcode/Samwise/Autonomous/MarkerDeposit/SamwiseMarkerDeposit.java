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

    public static double servo9_HOME = 0.0;

    final double servo9_SPEED = 0.04;

    public void init(HardwareMap ahwmap) {
        servo9 = ahwmap.servo.get("marker");

        servo9.setPosition(servo9_HOME);

    }

    public void move(int i) {
        servo9.setPosition(i);
    }


}