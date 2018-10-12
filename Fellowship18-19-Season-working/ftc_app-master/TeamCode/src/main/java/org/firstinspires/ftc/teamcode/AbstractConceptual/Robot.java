package org.firstinspires.ftc.teamcode.AbstractConceptual;

public abstract class Robot {

    public abstract void motors (); //Put motor instances here

    public abstract void sensors(); //Put sensor instances here

    public abstract void driveTrain(); //Put driveTrain instance here

    public abstract void vision(); //Put vision instances here

    //Instantiations are to be pooled in the robot class
    // so that they can be easily used in teleop and autonomous
}
