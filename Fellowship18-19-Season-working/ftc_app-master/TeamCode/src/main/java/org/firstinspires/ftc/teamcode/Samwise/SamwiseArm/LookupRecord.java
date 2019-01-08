package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

public class LookupRecord
{
    private int height;
    private double distance;
    private double j2Angle;
    private double j3Angle;

    public LookupRecord(int height, double distance, double j2Angle, double j3Angle)
    {
        this.height = height;
        this.distance = distance;
        this.j2Angle = j2Angle;
        this.j3Angle = j3Angle;
    }

    public int getHeight()
    {
        return height;
    }

    public double getDistance()
    {
        return distance;
    }

    public double getJ2Angle()
    {
        return j2Angle;
    }

    public double getJ3Angle()
    {
        return j3Angle;
    }
}
