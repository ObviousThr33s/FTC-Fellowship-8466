package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.ArrayList;
import java.util.List;

public class SamwiseGenius extends SamwiseSmart
{
    // small enough number to consider zero
    private static final double ZERO_GATE = 0.1;
    static final double ZERO_TICKS = 10;
    private static final double SAFFE_DEGREES_DIFF = 2;

    private static final double MAX_POWER_J2 = 0.5;
    private static final double MAX_POWER_J3 = 0.8;

    /****************** Start: update as hardware or autonomous changes ***************/
     static final double L1 = 21.5; //length between J2 and J3
     static final double L2 = 22.5; //length between J3 and J4
     static final double INITIAL_DEGREES_J2 = 195; //211;
     static final double INITIAL_DEGREES_J3 = 55;  //48;
     static final double MIN_DEGREES_J2 = 120;
     static final double MIN_DEGREES_J3 = INITIAL_DEGREES_J3;
     static final double MAX_DEGREES_J2 = INITIAL_DEGREES_J2;
     static final double MAX_DEGREES_J3 = 180;

     static final double TICKS_PER_REVOLUTION_J1 = 1680;
     static final double TICKS_PER_REVOLUTION_J2 = 383.6;
     static final double TICKS_PER_REVOLUTION_J3 = 537.6;
     static final double TICKS_PER_DEGREE_J1 = (TICKS_PER_REVOLUTION_J1 / 360.0) * 5;
     static final double TICKS_PER_DEGREE_J2 = (TICKS_PER_REVOLUTION_J2 / 360.0) * 24;
     static final double TICKS_PER_DEGREE_J3 = (TICKS_PER_REVOLUTION_J3 / 360.0) * 24;

     static final double MIN_POM_HEIGHT = 9;
     static final double MINIMUM_L3_RESTRICT = 15; // maximum is calculated.

    private static final double J4_COLLECTION_HEIGHT = 4;
    /****************** End: update as hardware or autonomous changes ***************/

    // minimum horizontal distance between J2 and J4, in inches
    private double minimum_L3 = -1;
    private double maximum_L3 = -1;

    // minimum ticks at set plane of motion height
    private int minimum_ticks_J2 = -1;
    private int minimum_ticks_J3 = -1;
    // maximum ticks at set plane of motion height
    private int maximum_ticks_J2 = -1;
    private int maximum_ticks_J3 = -1;

    // vertical distance from between J2 and J4
    double pomHeight = -1;

    int loop = 0;

    boolean isExpanding = false;
    boolean isRetracting = false;

    private static final boolean RETRACT_J2_FIRST = true;

    public SamwiseGenius(HardwareMap hwm)
    {
        super(hwm);

    }

    public void lowerJ4()
    {
        runTime.reset();
        double J2Deg = INITIAL_DEGREES_J2 - getJ2CurrentPosition() / TICKS_PER_DEGREE_J2;
        double J3Deg = getJ3CurrentPosition() / TICKS_PER_DEGREE_J3 + INITIAL_DEGREES_J3;

        double height = this.calculateHeight(J2Deg, J3Deg);
        if (height <= J4_COLLECTION_HEIGHT)
        {
            return;
        }

        double k  = Math.sqrt(Math.pow(L1, 2) + Math.pow(L2, 2) - Math.cos(Math.toRadians(J3Deg)) * 2 * L1 * L2);
        double L3 = Math.sqrt(Math.pow(k, 2) - Math.pow(height, 2));

        k = Math.sqrt(Math.pow(L3, 2) + Math.pow(J4_COLLECTION_HEIGHT, 2));
        double collectingJ21 = Math.toDegrees(Math.asin(J4_COLLECTION_HEIGHT / k));
        double collectingJ22 = Math.toDegrees(Math.acos((Math.pow(L1, 2) + Math.pow(k, 2) - Math.pow(L2, 2)) / (2 * L1 * k)));
        double collectingJ2  = 90 + collectingJ22 + collectingJ21;
        double collectingJ3  = Math.toDegrees(Math.acos((Math.pow(L1, 2) + Math.pow(L2, 2) - Math.pow(k, 2)) / (2 * L1 * L2)));
        if (collectingJ2 > MAX_DEGREES_J2)
        {
            collectingJ2 = MAX_DEGREES_J2 - SAFFE_DEGREES_DIFF;
        }
        if (collectingJ2 < MIN_DEGREES_J2)
        {
            collectingJ2 = MIN_DEGREES_J2 + SAFFE_DEGREES_DIFF;
        }
        if (collectingJ3 < MIN_DEGREES_J3)
        {
            collectingJ3 = MIN_DEGREES_J3 + SAFFE_DEGREES_DIFF;
        }
        if (collectingJ3 > MAX_DEGREES_J3)
        {
            collectingJ3 = MAX_DEGREES_J3 - SAFFE_DEGREES_DIFF;
        }
        int J2_ticks = (int) ((INITIAL_DEGREES_J2 - collectingJ2) * TICKS_PER_DEGREE_J2);
        int J3_ticks = (int) ((collectingJ3 - INITIAL_DEGREES_J3) * TICKS_PER_DEGREE_J3);
        this.toPositionWithoutJ1(J2_POWER, MANUAL_POWER_J3, J2_ticks, J2_ticks, J3_ticks);
    }

    public void hoverPlaneOfMotion(double speed)
    {
        runTime.reset();

        long startTime = System.currentTimeMillis();

        // stop when joystick/speed is zero
        if (Math.abs(speed) <= ZERO_GATE)
        {
            if (this.pomHeight > 0)
            {
                this.stopPOM();
            }
            return;
        }

        System.out.println("-------------------------------- beginning of loop " + loop + "; speed: " + speed + "  ...........................");
        System.out.println("J21 position beginning of loop: " + super.motor1J2.getCurrentPosition());
        System.out.println("J22 position beginning of loop: " + super.motor2J2.getCurrentPosition());
        System.out.println("J3 position beginning of loop: " + super.motorJ3.getCurrentPosition());

        //if height/minimum/maximum was never calculated before, do it and start POM
        if (pomHeight < 0)
        {
            super.isPOM = true;
            this.startPOM(speed);

            System.out.println("The loop takes " + (System.currentTimeMillis() - startTime) + " milliseconds.");
            System.out.println("-------------------------------- end of loop " + loop++ + " ...........................");
            return;
        }

        //stop when maximum is reached
        if ((speed < 0 && Math.abs(getJ3CurrentPosition() - maximum_ticks_J3) <= ZERO_TICKS) || (speed > 0 && Math.abs(getJ2CurrentPosition() - minimum_ticks_J2) <= ZERO_TICKS))
        {
            this.stopPOM();
        }
        else //maintain POM
        {
            this.maintainPOM(speed);
        }
        System.out.println("The loop takes " + (System.currentTimeMillis() - startTime) + " milliseconds.");
        System.out.println("-------------------------------- end of loop " + loop++ + " ...........................");
    }

    private void stopPOM()
    {
        if (!RETRACT_J2_FIRST && this.isRetracting)
        {
            //fix J3 position according to J2 position in order stay on the  plane.
            super.motor1J2.setPower(0);
            super.motor2J2.setPower(0);
            double J3Degrees      = this.calculateJ3Degrees(INITIAL_DEGREES_J2 - getJ2CurrentPosition() / TICKS_PER_DEGREE_J2, this.pomHeight);
            int    targetPosition = (int) ((J3Degrees - INITIAL_DEGREES_J3) * TICKS_PER_DEGREE_J3);
            super.motorJ3.setTargetPosition(targetPosition);
            System.out.println("POM Stopped from retracting.");
        }
        if (RETRACT_J2_FIRST || this.isExpanding)
        {
            //fix J2 position according to J3 position in order stay on the  plane.
            super.motorJ3.setPower(0);
            double J2Degrees      = this.calculateJ2Degrees(getJ3CurrentPosition() / TICKS_PER_DEGREE_J3 + INITIAL_DEGREES_J3, this.pomHeight);
            int    targetPosition = (int) ((INITIAL_DEGREES_J2 - J2Degrees) * TICKS_PER_DEGREE_J2);
            super.motor1J2.setTargetPosition(targetPosition);
            super.motor2J2.setTargetPosition(targetPosition);
            System.out.println("POM Stopped from " + (RETRACT_J2_FIRST ? "retracting" : "expanding") + ".");
        }
        this.pomHeight = -1;
        this.loop = 0;
        this.isRetracting = false;
        this.isExpanding = false;
        this.isPOM = false;
    }

    private void startPOM(double speed)
    {
        // current J2, J3 angles.
        double J2Deg = INITIAL_DEGREES_J2 - getJ2CurrentPosition() / TICKS_PER_DEGREE_J2;
        double J3Deg = getJ3CurrentPosition() / TICKS_PER_DEGREE_J3 + INITIAL_DEGREES_J3;

        //height
        this.pomHeight = this.calculateHeight(J2Deg, J3Deg);
        if (this.pomHeight < MIN_POM_HEIGHT)
        {
            this.pomHeight = MIN_POM_HEIGHT;
        }
        System.out.println("New plane of motion height: " + this.pomHeight);

        super.motor1J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        super.motor2J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        super.motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if (speed > 0)    //retracting: minimum matters.
        {
            //minimum
            double k = Math.sqrt(Math.pow(L1, 2) + Math.pow(L2, 2) - Math.cos(Math.toRadians(MIN_DEGREES_J3)) * 2 * L1 * L2);
            this.minimum_L3 = Math.sqrt(Math.pow(k, 2) - Math.pow(pomHeight, 2));
            if (new Double(minimum_L3).isNaN() || minimum_L3 < MINIMUM_L3_RESTRICT)
            {
                minimum_L3 = MINIMUM_L3_RESTRICT;
            }
            System.out.println("minimum_L3: " + minimum_L3);
            List   minList            = this.calculateJ2J3Degrees(minimum_L3, this.pomHeight);
            double minimum_degrees_J2 = (Double) minList.get(0);
            double minimum_degrees_J3 = (Double) minList.get(1);
            System.out.println("minimum_degrees_J2: " + minimum_degrees_J2);
            System.out.println("minimum_degrees_J3: " + minimum_degrees_J3);
            if (minimum_degrees_J2 > MAX_DEGREES_J2)
            {
                minimum_degrees_J2 = MAX_DEGREES_J2;
                minimum_degrees_J3 = this.calculateJ3Degrees(minimum_degrees_J2, this.pomHeight);
                minimum_L3 = Math.sqrt(Math.pow(L1, 2) + Math.pow(L2, 2) - Math.cos(Math.toRadians(minimum_degrees_J3)) * 2 * L1 * L2 - Math.pow(pomHeight, 2));
                System.out.println("minimum_L3 adjusted because <J2 is too big: " + minimum_L3);
            }
            if (minimum_degrees_J3 < MIN_DEGREES_J3)
            {
                minimum_degrees_J3 = MIN_DEGREES_J3;
                minimum_degrees_J2 = this.calculateJ2Degrees(minimum_degrees_J3, this.pomHeight);
                minimum_L3 = Math.sqrt(Math.pow(L1, 2) + Math.pow(L2, 2) - Math.cos(Math.toRadians(minimum_degrees_J3)) * 2 * L1 * L2 - Math.pow(pomHeight, 2));
                System.out.println("minimum_L3 adjusted because <J3 is too small: " + minimum_L3);
            }
            this.minimum_ticks_J2 = (int) ((INITIAL_DEGREES_J2 - minimum_degrees_J2) * TICKS_PER_DEGREE_J2);
            this.minimum_ticks_J3 = (int) ((minimum_degrees_J3 - INITIAL_DEGREES_J3) * TICKS_PER_DEGREE_J3);
            System.out.println("minimum_ticks_J2: " + minimum_ticks_J2);
            System.out.println("minimum_ticks_J3: " + minimum_ticks_J3);

            // set motors to go
            if (Math.abs(getJ2CurrentPosition() - minimum_ticks_J2) > ZERO_TICKS)
            {
                if (RETRACT_J2_FIRST)
                {
                    super.motor1J2.setTargetPosition(minimum_ticks_J2);
                    super.motor2J2.setTargetPosition(minimum_ticks_J2);
                    super.motor1J2.setPower(MAX_POWER_J2);
                    super.motor2J2.setPower(MAX_POWER_J2);
                }
                else
                {
                    super.motorJ3.setTargetPosition(minimum_ticks_J3);
                    super.motorJ3.setPower(MAX_POWER_J3);
                }
                this.isRetracting = true;
                System.out.println("Start POM -- retracting, J2 Target set to: " + super.motor2J2.getTargetPosition());
            }
        }
        else    // expanding: maximum matters
        {
            //maximum
            this.maximum_L3 = Math.sqrt(Math.pow(L1 + L2, 2) - Math.pow(this.pomHeight, 2));
            System.out.println("maximum_L3: " + maximum_L3);
            List   maxList            = this.calculateJ2J3Degrees(maximum_L3, this.pomHeight);
            double maximum_degrees_J2 = (Double) maxList.get(0);
            double maximum_degrees_J3 = (Double) maxList.get(1);
            System.out.println("maximum_degrees_J2: " + maximum_degrees_J2);
            System.out.println("maximum_degrees_J3: " + maximum_degrees_J3);
            if (maximum_degrees_J2 < MIN_DEGREES_J2)
            {
                maximum_degrees_J2 = MIN_DEGREES_J2;
                maximum_degrees_J3 = this.calculateJ3Degrees(maximum_degrees_J2, this.pomHeight);
                maximum_L3 = Math.sqrt(Math.pow(L1, 2) + Math.pow(L2, 2) - Math.cos(Math.toRadians(maximum_degrees_J3)) * 2 * L1 * L2 - Math.pow(pomHeight, 2));
                System.out.println("maximum_L3 adjusted because <J2 is too small: " + maximum_L3);
            }
            if (maximum_degrees_J3 > MAX_DEGREES_J3)
            {
                maximum_degrees_J3 = MAX_DEGREES_J3;
                maximum_degrees_J2 = this.calculateJ2Degrees(maximum_degrees_J3, this.pomHeight);
                maximum_L3 = Math.sqrt(Math.pow(L1, 2) + Math.pow(L2, 2) - Math.cos(Math.toRadians(maximum_degrees_J3)) * 2 * L1 * L2 - Math.pow(pomHeight, 2));
                System.out.println("maximum_L3 adjusted because <J3 is too big: " + maximum_L3);
            }
            maximum_ticks_J2 = (int) ((INITIAL_DEGREES_J2 - maximum_degrees_J2) * TICKS_PER_DEGREE_J2);
            maximum_ticks_J3 = (int) ((maximum_degrees_J3 - INITIAL_DEGREES_J3) * TICKS_PER_DEGREE_J3);
            System.out.println("maximum_ticks_J2: " + maximum_ticks_J2);
            System.out.println("maximum_ticks_J3: " + maximum_ticks_J3);

            // set motors to go
            if (Math.abs(getJ3CurrentPosition() - maximum_ticks_J3) > ZERO_TICKS)
            {
                super.motorJ3.setTargetPosition(maximum_ticks_J3);
                super.motorJ3.setPower(MAX_POWER_J3);
                this.isExpanding = true;
                System.out.println("Start POM -- expanding, J3 Target set to: " + super.motorJ3.getTargetPosition());
            }
        }
    }

    private void maintainPOM(double speed)
    {
        // apply angle speed to motors
        if (!RETRACT_J2_FIRST || speed < 0)    //expanding
        {
            double J3ticks = getJ3CurrentPosition();
            double J3Deg   = J3ticks / TICKS_PER_DEGREE_J3 + INITIAL_DEGREES_J3;
            System.out.println("J3 degrees: " + J3Deg);

            //Dist from J2 to J4
            double J2Deg = this.calculateJ2Degrees(J3Deg, this.pomHeight);
            System.out.println("J2 degrees: " + J2Deg);
            int targetPositionJ2 = (int) ((INITIAL_DEGREES_J2 - J2Deg) * TICKS_PER_DEGREE_J2);
            super.motor1J2.setTargetPosition(targetPositionJ2);
            super.motor2J2.setTargetPosition(targetPositionJ2);
            super.motor1J2.setPower(MAX_POWER_J2);
            super.motor2J2.setPower(MAX_POWER_J2);
            System.out.println("Maintain POM -- " + (!RETRACT_J2_FIRST ? "retracting" : "expanding") + ": J3 is at: " + J3ticks);
            System.out.println("Maintain POM -- " + (!RETRACT_J2_FIRST ? "retracting" : "expanding") + ": J2 Target set to: " + targetPositionJ2);
        }
        else            //retracting
        {
            double J2ticks          = getJ2CurrentPosition();
            double J2Deg            = INITIAL_DEGREES_J2 - J2ticks / TICKS_PER_DEGREE_J2;
            double J3Deg            = this.calculateJ3Degrees(J2Deg, this.pomHeight);
            int    targetPositionJ3 = (int) ((J3Deg - INITIAL_DEGREES_J3) * TICKS_PER_DEGREE_J3);
            super.motorJ3.setTargetPosition(targetPositionJ3);
            super.motorJ3.setPower(1);
            System.out.println("Maintain POM -- retracting: J2 is at: " + J2ticks);
            System.out.println("Maintain POM -- retracting: J3 Target set to: " + super.motorJ3.getTargetPosition());
        }
    }

    List <Double> calculateJ2J3Degrees(double L3, double height)
    {
        List <Double> result = new ArrayList <>(2);

        double k_sqrd     = L3 * L3 + height * height;
        double k          = Math.sqrt(k_sqrd);
        double angle_J3   = Math.toDegrees(Math.acos((L1 * L1 + L2 * L2 - k_sqrd) / (2 * L1 * L2)));
        double angle_J2_2 = Math.toDegrees(Math.asin(height / k));
        double angle_J2_1 = Math.toDegrees(Math.acos((L1 * L1 + k_sqrd - L2 * L2) / (2 * L1 * k)));
        double angle_J2   = angle_J2_1 + angle_J2_2 + 90;
        result.add(angle_J2);
        result.add(angle_J3);

        return result;
    }

    double calculateJ2Degrees(double j3Deg, double height)
    {
        double k_sqrd = Math.pow(L2, 2) + Math.pow(L1, 2) - Math.cos(Math.toRadians(j3Deg)) * 2 * L2 * L1;
        double k      = Math.sqrt(k_sqrd);
        double J2Deg1 = Math.toDegrees(Math.asin(height / k));
        double J2Deg2 = Math.toDegrees(Math.acos((L1 * L1 + k_sqrd - L2 * L2) / (2 * L1 * k)));

        return 90 + J2Deg1 + J2Deg2;
    }

    double calculateJ3Degrees(double J2Degrees, double height)
    {
        double height_J2_J3 = Math.sin(Math.toRadians(J2Degrees - 90)) * L1;
        double height_J3_J4 = height_J2_J3 - height;
        double J4Degrees    = Math.toDegrees(Math.asin(height_J3_J4 / L2));

        return 270 - J2Degrees - J4Degrees;
    }


    double calculateHeight(double J2Deg, double J3Deg)
    {
        double J4Deg        = 180 - J3Deg - J2Deg + 90;
        double height_J3_J4 = L2 * Math.sin(Math.toRadians(J4Deg));
        double height_J3_J2 = L1 * Math.sin(Math.toRadians(J2Deg - 90));
        return height_J3_J2 - height_J3_J4;
    }

    public void toPositionWithoutJ1(double j2Power, double j3Power, int j2Position1, int j2Position2, int j3Position)
    {
        motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ3.setTargetPosition(j3Position);
        motorJ3.setPower(j3Power);

        motor1J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor1J2.setTargetPosition(j2Position1);
        motor2J2.setTargetPosition(j2Position2);
        motor1J2.setPower(j2Power);
        motor2J2.setPower(j2Power);
    }
}
