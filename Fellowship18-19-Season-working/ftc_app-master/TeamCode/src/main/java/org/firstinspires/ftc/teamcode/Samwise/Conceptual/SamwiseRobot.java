package org.firstinspires.ftc.teamcode.Samwise.Conceptual;

import org.firstinspires.ftc.teamcode.AbstractConceptual.Robot;
import org.firstinspires.ftc.teamcode.AbstractPhysical.DriveTrain;
import org.firstinspires.ftc.teamcode.AbstractPhysical.MotorsAndServos;
import org.firstinspires.ftc.teamcode.AbstractPhysical.Sensors;
import org.firstinspires.ftc.teamcode.AbstractPhysical.Vision;
import org.firstinspires.ftc.teamcode.Samwise.DriveTrain.SamwiseDriveTrain;

public class SamwiseRobot extends Robot {

    @Override
    public MotorsAndServos motorsAndServos() {
        return null;
    }


    @Override
    public Sensors sensors() {
        return null;
    }

    @Override
    public DriveTrain driveTrain() {
        return null;
    }

    @Override
    public Vision vision() {
        return null;
    }
    /*
    //Drive Train Motors
    public DcMotor leftDrive = null;
    public DcMotor rightDrive = null;

    public void init(HardwareMap hwm){
        hm = hwm;
        leftDrive = hm.get(DcMotor.class, "left_drive");
        rightDrive = hm.get(DcMotor.class, "right_drive");
    }
    */
}
