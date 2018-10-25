package org.firstinspires.ftc.teamcode.Samwise;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.AbstractConceptual.Robot;
import org.firstinspires.ftc.teamcode.AbstractPhysical.DriveTrain;
import org.firstinspires.ftc.teamcode.AbstractPhysical.MotorsAndServos;
import org.firstinspires.ftc.teamcode.AbstractPhysical.Sensors;
import org.firstinspires.ftc.teamcode.AbstractPhysical.Vision;
import org.firstinspires.ftc.teamcode.Samwise.SamwiseDriveTrain;
import org.firstinspires.ftc.teamcode.Samwise.SamwiseCollection;
import org.firstinspires.ftc.teamcode.Samwise.SamwiseDriveTrain;
import org.firstinspires.ftc.teamcode.Samwise.SamwiseMotorsAndServos;
import org.firstinspires.ftc.teamcode.Samwise.SamwiseSensors;
import org.firstinspires.ftc.teamcode.Samwise.SamwiseVision;

public class SamwiseRobot extends Robot {

    SamwiseMotorsAndServos mas;
    SamwiseDriveTrain      drt;
    SamwiseSensors         sen;
    SamwiseVision          vis;

    SamwiseRobot (HardwareMap hw, Telemetry tel){
        mas = new SamwiseMotorsAndServos();
        drt = new SamwiseDriveTrain();
        sen = new SamwiseSensors();
        vis = new SamwiseVision();
    }

    public MotorsAndServos motorsAndServos() {
        return mas;
    }

       public DriveTrain driveTrain() {
        return drt;
    }

    public Sensors sensors() {
        return sen;
    }

    public Vision vision() {
        return vis;
    }
}
