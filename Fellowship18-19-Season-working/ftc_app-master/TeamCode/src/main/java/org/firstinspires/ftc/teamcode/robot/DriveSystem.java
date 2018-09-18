package org.firstinspires.ftc.teamcode;

/**
 * Created by Joshua on 10/28/2017.
 */

public abstract class DriveSystem {

    IMUSystem imu = null;

    public abstract boolean Move(double power, double direction, double spin, double distance, int timeout);

}
