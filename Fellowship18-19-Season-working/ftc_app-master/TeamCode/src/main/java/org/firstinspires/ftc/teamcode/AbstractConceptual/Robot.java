package org.firstinspires.ftc.teamcode.AbstractConceptual;

import org.firstinspires.ftc.teamcode.AbstractPhysical.DriveTrain;
import org.firstinspires.ftc.teamcode.AbstractPhysical.Motors;
import org.firstinspires.ftc.teamcode.AbstractPhysical.Sensors;
import org.firstinspires.ftc.teamcode.AbstractPhysical.Vision;

public abstract class Robot {

    public abstract Motors motors (); //Put motor instances here

    public abstract Sensors sensors(); //Put sensor instances here

    public abstract DriveTrain driveTrain(); //Put driveTrain instance here

    public abstract Vision vision(); //Put vision instances here

    //Instantiations are to be pooled in the robot class
    // so that they can be easily used in teleop and autonomous
}
