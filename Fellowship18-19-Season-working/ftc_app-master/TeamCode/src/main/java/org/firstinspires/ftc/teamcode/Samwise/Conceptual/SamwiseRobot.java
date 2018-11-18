package org.firstinspires.ftc.teamcode.Samwise.Conceptual;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.AbstractConceptual.Robot;
import org.firstinspires.ftc.teamcode.AbstractPhysical.DriveTrain;
import org.firstinspires.ftc.teamcode.AbstractPhysical.MotorsAndServos;
import org.firstinspires.ftc.teamcode.AbstractPhysical.Sensors;
import org.firstinspires.ftc.teamcode.AbstractPhysical.Vision;
import org.firstinspires.ftc.teamcode.Samwise.SamwiseMotors.SamwiseMotorsAndServos;

public class SamwiseRobot extends Robot {

    MotorsAndServos swMotors = null;

    public SamwiseRobot(HardwareMap hwm){
        swMotors = new SamwiseMotorsAndServos(hwm);
    }

    @Override
    public Sensors getSensors() { return null; }

    public MotorsAndServos getMotorsAndServos() { return swMotors; }

    public DriveTrain getDriveTrain() {
        return null;
    }

    public Vision getVision() {
        return null;
    }
}
