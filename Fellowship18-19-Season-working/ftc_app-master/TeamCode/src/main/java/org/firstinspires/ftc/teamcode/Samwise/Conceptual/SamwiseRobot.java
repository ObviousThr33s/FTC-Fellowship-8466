package org.firstinspires.ftc.teamcode.Samwise.Conceptual;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class SamwiseRobot {

    public HardwareMap hm;

    public void init(HardwareMap hwm){
        hm = hwm;
    }

    //Drive Train Motors
    public DcMotor leftDrive = hm.get(DcMotor.class, "left_drive");
    public DcMotor rightDrive = hm.get(DcMotor.class, "right_drive");

}
