package org.firstinspires.ftc.teamcode.Samwise.Hanger;

import org.firstinspires.ftc.teamcode.Samwise.SamwiseMotors.SamwiseMotorsAndServos;

public class SamwiseHangerHardware extends SamwiseMotorsAndServos{

    public void moveMotor1(double pwr){
        hangermotor1.setPower(pwr);
    }

    public void moveServo1(double direction){
        hangerservo1.setPosition(direction);
    }

    enum Posistion{
        UP, DOWN
    }

    public void moveToPos(Posistion pos){
        if (pos == Posistion.UP) {
            //moveMotor1();
        }
        if (pos == Posistion.DOWN){
            //moveMotor1();
        }
    }
}
