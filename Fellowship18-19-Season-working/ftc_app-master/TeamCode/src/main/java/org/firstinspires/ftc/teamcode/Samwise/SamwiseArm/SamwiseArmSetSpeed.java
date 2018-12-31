package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import java.math.*;

public class SamwiseArmSetSpeed extends SamwiseArm{

    double J2Deg;
    double J3Deg;

    //Lengths in inches
    static double L1 = 24;
    static double L2 = 27;
    static double HEIGHT_FROM_GROUND = 6;

    //end vars
    double k_sqrd;
    double L3;
    //J2 and J3 velocity
    double J2Vel;
    double J3Vel;

    @Override
    public void hoverPlaneOfMotion(double speed){
        setManual(false);

        J2Deg = getJ2CurrentPosition()/TICKS_PER_DEGREE_J1;
        J3Deg = getJ3CurrentPosition()/TICKS_PER_DEGREE_J3;


        //Dist from J2 to joint between the claw and arm
        k_sqrd = (L1*L1)+(L2*L2)-2*(L1*L2)*Math.cos(J3Deg);
        L3 = Math.sqrt(k_sqrd-(HEIGHT_FROM_GROUND*HEIGHT_FROM_GROUND));

        J2Vel = speed/L3;
        J3Vel = speed/L3;

        if(motorJ2 instanceof DcMotorEx && motorJ3 instanceof DcMotorEx){
            ((DcMotorEx) motorJ2).setVelocity(J2Vel);
            ((DcMotorEx) motorJ3).setVelocity(J3Vel);
        }
    }

}
