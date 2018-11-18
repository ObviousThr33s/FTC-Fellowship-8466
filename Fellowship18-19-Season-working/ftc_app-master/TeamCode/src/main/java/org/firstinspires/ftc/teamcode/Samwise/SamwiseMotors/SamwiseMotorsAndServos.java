package org.firstinspires.ftc.teamcode.Samwise.SamwiseMotors;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.AbstractPhysical.MotorsAndServos;
import org.firstinspires.ftc.teamcode.Samwise.Collection.SamwiseCollectionHardware;

public class SamwiseMotorsAndServos extends MotorsAndServos{

    HardwareMap hwm = null;

    public SamwiseMotorsAndServos(HardwareMap hm){
        hwm = hm;
    }
    public SamwiseCollectionHardware SamwiseCollectionHardware(){
        return SamwiseCollectionHardware();
    }

}