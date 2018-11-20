package org.firstinspires.ftc.teamcode.Samwise.Conceptual;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class SamwiseRobot {

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
