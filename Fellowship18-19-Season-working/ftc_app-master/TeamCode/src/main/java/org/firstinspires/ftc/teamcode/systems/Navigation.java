package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Hardware;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

/*
 * Created by James on 11/13/2017.
 */


public class Navigation{
    FTCRobot ftcRobot = null;
    Telemetry tl = null;
    DcMotor frontLeft;
    int posStart = 0;

    Navigation (FTCRobot FTCRobot, Telemetry telemetry){
        ftcRobot = FTCRobot;
        tl = telemetry;
        //frontLeft = FrontLeft;
    }

    public void StartGetDistance (DcMotor FrontLeft){
        posStart = frontLeft.getCurrentPosition();
    }

    public int GetDistance (){
        int pos = frontLeft.getCurrentPosition();
        return  (pos - posStart)*-1;
    }

    public Velocity GetVelocity(){
        Velocity velocity = ftcRobot.GetIMUSystem().GetVelocity();
        return velocity;
    }

    public Acceleration GetAcceleration(){
        Acceleration acceleration = ftcRobot.GetIMUSystem().GetAcceleration();
        return acceleration;
    }

}
