package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Jack's Rec on 12/16/2017.
 */

public abstract class JewlKnocker {

    public abstract void knockerDown();

    public abstract void knockerUp();

    public abstract double getBasePosition();

    public abstract double getKnockerPosition();

    public abstract void setDownPosition();

    public abstract void setUpPosition();

    public abstract void knockLeft();

    public abstract void knockRight();
}


