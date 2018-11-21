package org.firstinspires.ftc.teamcode.Samwise.Conceptual;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

<<<<<<< HEAD
public class SamwiseRobot extends Robot {

    @Override
    public MotorsAndServos motorsAndServos() {
        return null;

    }
=======
public class SamwiseRobot {
>>>>>>> parent of 5bd7e30... Merge branch 'master' of https://github.com/ObviousThr33s/FTC-Fellowship-8466

    public HardwareMap hm;


    //Drive Train Motors
    public DcMotor leftDrive = null;
    public DcMotor rightDrive = null;

    public void init(HardwareMap hwm){
        hm = hwm;
        leftDrive = hm.get(DcMotor.class, "left_drive");
        rightDrive = hm.get(DcMotor.class, "right_drive");
    }

}
