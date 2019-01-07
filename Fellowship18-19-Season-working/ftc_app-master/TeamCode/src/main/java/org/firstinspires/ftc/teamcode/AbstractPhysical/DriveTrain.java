package org.firstinspires.ftc.teamcode.AbstractPhysical;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public abstract class DriveTrain {
    public void drive(float x, float y){}

    public abstract void drive(double power);

    public abstract void turnleft(double power);

    public abstract void turnright(double power);
}
