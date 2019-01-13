package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Test carefully to find out the *right* "MAXIMUM_SPEED":
 * 1. Small enough to have more accurate speed and position
 * 2. Big enough so to avoid target reached before next loop
 * <p>
 * Keep in mind loop interval too.
 */
public class SamwiseArmSetSpeed extends SamwiseArm
{
    // maximum speed when joystick is 1, in inches per second
    private static final int MAXIMUM_SPEED = 20;
    // small enough number to consider zero
    private static final double ZERO_GATE = 0.01;
    // minimum horizontal distance between J2 and J4, in inches
    private static final double MINIMUM_L3 = 20;
    private static final double MAXIMUM_L3 = 50;
    // vertical distance from between J2 and J4
    private double pomHeight = -1;
    // minimum ticks at set plane of motion height
    private int minimum_ticks_J2 = -1;
    private int minimum_ticks_J3 = -1;
    private double minimum_degrees_J2 = -1;
    private double minimum_degrees_J3 = -1;
    private double max_velociy_at_min_position_J2 = -1;
    private double max_velociy_at_min_position_J3 = -1;
    // maximum ticks at set plane of motion height
    private int maximum_ticks_J2 = -1;
    private int maximum_ticks_J3 = -1;
    private double maximum_degrees_J2 = -1;
    private double maximum_degrees_J3 = -1;
    private double max_velociy_at_max_position_J2 = -1;
    private double max_velociy_at_max_position_J3 = -1;

    private int loop = 0;

    public SamwiseArmSetSpeed(HardwareMap hwm)
    {
        super(hwm);
    }

    @Override
    public void hoverPlaneOfMotion(double speed)
    {
        long startTime = System.currentTimeMillis();
        setManual(false);

        // stop when joystick/speed is zero
        if (Math.abs(speed) <= ZERO_GATE)
        {
            //fix J3 position according to J2 position in order stay on the  plane.
            super.motorJ2.setPower(0);
            double J3Degrees = this.calculateJ3Degrees(getJ2CurrentPosition() / TICKS_PER_DEGREE_J2 + INITIAL_DEGREES_J2);
            super.motorJ3.setTargetPosition((int) ((J3Degrees - INITIAL_DEGREES_J3) * TICKS_PER_DEGREE_J3));
            this.pomHeight = -1;
            this.loop = 0;
            return;
        }

        System.out.println("-------------------------------- beginning of loop " + loop + " ...........................");
        System.out.println("J2 position beginning of loop: " + super.motorJ2.getCurrentPosition());
        System.out.println("J3 position beginning of loop: " + super.motorJ3.getCurrentPosition());

        // current J2, J3 angles.
        double J2Deg = getJ2CurrentPosition() / TICKS_PER_DEGREE_J2 + INITIAL_DEGREES_J2;
        double J3Deg = getJ3CurrentPosition() / TICKS_PER_DEGREE_J3 + INITIAL_DEGREES_J3;

        //if height/minimum/maximum was never calculated before, do it:
        if (pomHeight < 0)
        {
            //height
            double J4Deg        = 180 - J3Deg - J2Deg + 90;
            double height_J3_J4 = ARM_L2 * Math.sin(Math.toRadians(J4Deg));
            double height_J3_J2 = ARM_L1 * Math.sin(Math.toRadians(J2Deg - 90));
            pomHeight = height_J3_J2 - height_J3_J4;
            System.out.println("New plane of motion height: " + this.pomHeight);
            //minimum
            List minList = this.calculateJ2J3Degrees(MINIMUM_L3);
            this.minimum_degrees_J2 = (Double) minList.get(0);
            this.minimum_degrees_J3 = (Double) minList.get(1);
            this.minimum_ticks_J2 = (int) ((this.minimum_degrees_J2 - INITIAL_DEGREES_J2) * TICKS_PER_DEGREE_J2);
            this.minimum_ticks_J3 = (int) ((this.minimum_degrees_J3 - INITIAL_DEGREES_J3) * TICKS_PER_DEGREE_J3);
            // maximum ticks speed (when value is 1) at this location
            double max_speed_L3 =  MAXIMUM_L3 + MAXIMUM_SPEED;
            minList = this.calculateJ2J3Degrees(max_speed_L3);
            this.max_velociy_at_min_position_J2 = Math.abs((Double) minList.get(0) - J2Deg) * TICKS_PER_DEGREE_J2;
            this.max_velociy_at_min_position_J3 = Math.abs((Double) minList.get(1) - J3Deg) * TICKS_PER_DEGREE_J3;

            //maximum
            List maxList = this.calculateJ2J3Degrees(MAXIMUM_L3);
            this.maximum_degrees_J2 = (Double) maxList.get(0);
            this.maximum_degrees_J3 = (Double) maxList.get(1);
            this.maximum_ticks_J2 = (int) ((this.maximum_degrees_J2 - INITIAL_DEGREES_J2) * TICKS_PER_DEGREE_J2);
            this.maximum_ticks_J3 = (int) ((this.maximum_degrees_J3 - INITIAL_DEGREES_J3) * TICKS_PER_DEGREE_J3);
            //maximum ticks speed (when value is 1) at this location
            max_speed_L3 = MAXIMUM_L3 - MAXIMUM_SPEED;
            maxList = this.calculateJ2J3Degrees(max_speed_L3);
            this.max_velociy_at_max_position_J2 = Math.abs((Double) maxList.get(0) - J2Deg) * TICKS_PER_DEGREE_J2;
            this.max_velociy_at_max_position_J3 = Math.abs((Double) maxList.get(1) - J3Deg) * TICKS_PER_DEGREE_J3;

        }

        //Dist from J2 to J4
        double k_sqrd = (ARM_L1 * ARM_L1) + (ARM_L2 * ARM_L2) - 2 * ARM_L1 * ARM_L2 * Math.cos(Math.toRadians(J3Deg));
        double L3     = Math.sqrt(k_sqrd - (this.pomHeight * pomHeight));

        // assume we apply distance speed for 1 second, find out angle speeds
        double L3_next     = L3 + MAXIMUM_SPEED * speed;
        double targetJ2Deg = -1;
        double targetJ3Deg = -1;
        if (L3_next < MINIMUM_L3)
        {
            targetJ2Deg = this.minimum_degrees_J2;
            targetJ3Deg = this.minimum_degrees_J3;
        }
        else if (L3_next > MAXIMUM_L3)
        {
            targetJ2Deg = this.maximum_degrees_J2;
            targetJ3Deg = this.maximum_degrees_J3;
        }
        else
        {
            List listDegNext = this.calculateJ2J3Degrees(L3_next);
            targetJ2Deg = (Double) listDegNext.get(0);
            targetJ3Deg = (Double) listDegNext.get(1);
        }

        double targetTicksJ2 = (targetJ2Deg - INITIAL_DEGREES_J2) * TICKS_PER_DEGREE_J2;
        double targetTicksJ3 = (targetJ3Deg - INITIAL_DEGREES_J3) * TICKS_PER_DEGREE_J3;

        // apply angle speed to motors
        if (speed > 0)    //expanding
        {
            this.motorJ2.setTargetPosition(maximum_ticks_J2 < targetTicksJ2 ? (int) targetTicksJ2 : maximum_ticks_J3);
            this.motorJ3.setTargetPosition(maximum_ticks_J3 < targetTicksJ3 ? (int) targetTicksJ3 : maximum_ticks_J3);
        }
        else            //retracting
        {
            this.motorJ2.setTargetPosition(minimum_ticks_J2 < targetTicksJ2 ? (int) targetTicksJ2 : minimum_ticks_J2);
            this.motorJ3.setTargetPosition(minimum_ticks_J3 < targetTicksJ3 ? (int) targetTicksJ3 : minimum_ticks_J3);
        }

        System.out.println("J2 position before set velocity: " + super.motorJ2.getCurrentPosition());
        System.out.println("J3 position before set velocity: " + super.motorJ3.getCurrentPosition());

        DcMotorEx motorExJ2 = (DcMotorEx) motorJ2;
        DcMotorEx motorExJ3 = (DcMotorEx)motorJ3;
        if (L3_next < MINIMUM_L3)
        {
            motorExJ2.setVelocity(this.max_velociy_at_min_position_J2 * speed);
            motorExJ3.setVelocity(this.max_velociy_at_min_position_J3 * speed);
        }
        else if (L3_next > MAXIMUM_L3)
        {
            motorExJ2.setVelocity(this.max_velociy_at_max_position_J2 * speed);
            motorExJ3.setVelocity(this.max_velociy_at_max_position_J3 * speed);
        }
        else
        {
            double diffJ2Ticks = Math.abs(targetJ2Deg - J2Deg) * TICKS_PER_DEGREE_J2;
            double diffJ3Ticks = Math.abs(targetJ3Deg - J3Deg) * TICKS_PER_DEGREE_J3;
            motorExJ2.setVelocity(diffJ2Ticks);
            motorExJ3.setVelocity(diffJ3Ticks);
        }

        System.out.println("J2 Target set to: " + super.motorJ2.getTargetPosition());
        System.out.println("J3 Target set to: " + super.motorJ3.getTargetPosition());

        System.out.println("J2 started angle: " + J2Deg);
        System.out.println("J3 started angle: " + J3Deg);
        System.out.println("J2 next/target angle: " + targetJ2Deg);
        System.out.println("J3 next/target angle: " + targetJ3Deg);
        System.out.println("J2 speed: " + ((DcMotorEx) motorJ2).getVelocity() + " ticks/s");
        System.out.println("J3 speed: " + ((DcMotorEx) motorJ3).getVelocity() + " ticks/s");

        System.out.println("The loop takes " + (System.currentTimeMillis() - startTime) + " milliseconds.");
        System.out.println("-------------------------------- end of loop " + loop++ + " ...........................");
    }

    private List<Double> calculateJ2J3Degrees(double L3)
    {
        List<Double> result = new ArrayList<>(2);

        double k_sqrd     = L3 * L3 + pomHeight * pomHeight;
        double angle_J3   = Math.toDegrees(Math.acos((ARM_L1 * ARM_L1 + ARM_L2 * ARM_L2 - k_sqrd) / (2 * ARM_L1 * ARM_L2)));
        double angle_J2_2 = Math.toDegrees(Math.asin(pomHeight / L3));
        double angle_J2_1 = Math.toDegrees(Math.acos((ARM_L1 * ARM_L1 + k_sqrd - ARM_L2 * ARM_L2) / (2 * ARM_L1 * Math.sqrt(k_sqrd))));
        double angle_J2   = angle_J2_1 + angle_J2_2 + 90;
        result.add(angle_J2);
        result.add(angle_J3);

        return result;
    }

    private double calculateJ3Degrees(double J2Degrees)
    {
        double height_J2_J3 = Math.sin(Math.toRadians(J2Degrees - 90)) * ARM_L1;
        double height_J3_J4 = height_J2_J3 - this.pomHeight;
        double J4Degrees    = Math.toDegrees(Math.asin(height_J3_J4 / ARM_L2));

        return 270 - J2Degrees - J4Degrees;
    }
}
