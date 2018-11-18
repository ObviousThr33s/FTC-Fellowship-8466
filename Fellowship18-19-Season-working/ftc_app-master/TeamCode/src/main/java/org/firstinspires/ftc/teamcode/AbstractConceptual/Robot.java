package org.firstinspires.ftc.teamcode.AbstractConceptual;

import org.firstinspires.ftc.teamcode.AbstractPhysical.DriveTrain;
import org.firstinspires.ftc.teamcode.AbstractPhysical.MotorsAndServos;
import org.firstinspires.ftc.teamcode.AbstractPhysical.Sensors;
import org.firstinspires.ftc.teamcode.AbstractPhysical.Vision;

public abstract class Robot {

    public abstract MotorsAndServos getMotorsAndServos (); //Put motor instances here

    public abstract Sensors getSensors(); //Put sensor instances here

    public abstract DriveTrain getDriveTrain(); //Put driveTrain instance here

    public abstract Vision getVision(); //Put vision instances here

    //Instantiations are to be pooled in the robot class
    // so that they can be easily used in teleop and autonomous
}
