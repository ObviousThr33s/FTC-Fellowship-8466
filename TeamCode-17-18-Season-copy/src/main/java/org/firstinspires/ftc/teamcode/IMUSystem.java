package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

/**
 * Created by Joshua on 10/28/2017.
 */

public abstract class IMUSystem {
    public abstract void ResetHeading ();

    public abstract double GetHeading ();

    public abstract double GetNormalizedHeading ();

    public abstract Acceleration GetAcceleration ();

    public  abstract Velocity GetVelocity ();
}
