package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OctoSamwiseGenius extends OctoSamwiseSmart
{
    // small enough number to consider zero
    private static final double ZERO_GATE = 0.1;
    private static final double ZERO_TICKS = 10;
    static final double MIN_POM_HEIGHT = 9;
    static final double L1 = 21.5; //length between J2 and J3
    static final double L2 = 22.75; //length between J3 and J4
    static final double INITIAL_DEGREES_J2 = 211;
    static final double INITIAL_DEGREES_J3 = 48;
    static final double MAX_DEGREES_J2 = INITIAL_DEGREES_J2;
    static final double MAX_DEGREES_J3 = 180;
    static final double MIN_DEGREES_J2 = 135;
    static final double MIN_DEGREES_J3 = INITIAL_DEGREES_J3;
    static final double J4_COLLECTION_HEIGHT = 4;
    static final double MAX_POWER_J2 = 0.3;
    static final double MAX_POWER_J3 = 0.3;

    static final double TICKS_PER_REVOLUTION_J2 = 383.6;
    static final double TICKS_PER_REVOLUTION_J3 = 537.6;
    static final double TICKS_PER_DEGREE_J2 = (TICKS_PER_REVOLUTION_J2 / 360.0) * 24;
    static final double TICKS_PER_DEGREE_J3 = (TICKS_PER_REVOLUTION_J3 / 360.0) * 24;

    static final double MINIMUM_l3_RESTRICT = 15;

    // minimum horizontal distance between J2 and J4, in inches
    private double minimum_L3 = -1;
    private double maximum_L3 = -1;
    // vertical distance from between J2 and J4
    private double pomHeight = -1;

    // minimum ticks at set plane of motion height
    private int minimum_ticks_J2 = -1;
    private int minimum_ticks_J3 = -1;
    // maximum ticks at set plane of motion height
    private int maximum_ticks_J2 = -1;
    private int maximum_ticks_J3 = -1;

    private int loop = 0;

    public OctoSamwiseGenius(HardwareMap hwm)
    {
        super(hwm);

    }

    public void hoverPlaneOfMotion(double speed)
    {
        long startTime = System.currentTimeMillis();
        setManual(false);

        // stop when joystick/speed is zero
        if (Math.abs(speed) <= ZERO_GATE)
        {
            if (this.pomHeight > 0) this.stopPOM();
            return;
        }

        System.out.println("-------------------------------- beginning of loop " + loop + "; speed: " + speed + "  ...........................");
        System.out.println("J21 position beginning of loop: " + super.motor1J2.getCurrentPosition());
        System.out.println("J22 position beginning of loop: " + super.motor2J2.getCurrentPosition());
        System.out.println("J3 position beginning of loop: " + super.motorJ3.getCurrentPosition());

        //if height/minimum/maximum was never calculated before, do it and start POM
        if (pomHeight < 0)
        {
            this.startPOM(speed);

            try
            {
                Thread.sleep(20);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            System.out.println("The loop takes " + (System.currentTimeMillis() - startTime) + " milliseconds.");
            System.out.println("-------------------------------- end of loop " + loop++ + " ...........................");
            return;
        }
/*

        //stop when maximum is reached
        if ((speed < 0 && Math.abs(getJ3CurrentPosition() - maximum_ticks_J3) <= ZERO_TICKS) || (speed > 0 && Math.abs(getJ2CurrentPosition() - minimum_ticks_J2) <= ZERO_TICKS))
        {
            this.stopPOM();
        }
        else //maintain POM
        {
            this.maintainPOM(speed);
        }
*/
        System.out.println("The loop takes " + (System.currentTimeMillis() - startTime) + " milliseconds.");
        System.out.println("-------------------------------- end of loop " + loop++ + " ...........................");
    }

    private void stopPOM()
    {
        //fix J3 position according to J2 position in order stay on the  plane.
        super.motor1J2.setPower(0);
        super.motor2J2.setPower(0);
        double J3Degrees = this.calculateJ3Degrees(INITIAL_DEGREES_J2 - getJ2CurrentPosition() / TICKS_PER_DEGREE_J2);
        super.motorJ3.setTargetPosition((int) ((J3Degrees - INITIAL_DEGREES_J3) * TICKS_PER_DEGREE_J3));
        this.pomHeight = -1;
        this.loop = 0;
        System.out.println("POM Stopped.");
    }

    private void startPOM(double speed)
    {
        // current J2, J3 angles.
        double J2Deg = INITIAL_DEGREES_J2 - getJ2CurrentPosition() / TICKS_PER_DEGREE_J2;
        double J3Deg = getJ3CurrentPosition() / TICKS_PER_DEGREE_J3 + INITIAL_DEGREES_J3;

        //height
        double J4Deg        = 180 - J3Deg - J2Deg + 90;
        double height_J3_J4 = L2 * Math.sin(Math.toRadians(J4Deg));
        double height_J3_J2 = L1 * Math.sin(Math.toRadians(J2Deg - 90));
        pomHeight = height_J3_J2 - height_J3_J4;
        if (pomHeight < MIN_POM_HEIGHT)
        {
            pomHeight = MIN_POM_HEIGHT;
        }
        System.out.println("New plane of motion height: " + this.pomHeight);

        //minimum
        double k = Math.sqrt(Math.pow(L1, 2) + Math.pow(L2, 2) - Math.cos(Math.toRadians(MIN_DEGREES_J3)) * 2 * L1 * L2);
        this.minimum_L3 = Math.sqrt(Math.pow(k, 2) - Math.pow(pomHeight, 2));
        if (new Double(minimum_L3).isNaN() || minimum_L3 < MINIMUM_l3_RESTRICT)
        {
            minimum_L3 = MINIMUM_l3_RESTRICT;
        }
        System.out.println("minimum_L3: " + minimum_L3);
        List   minList            = this.calculateJ2J3Degrees(minimum_L3);
        double minimum_degrees_J2 = (Double) minList.get(0);
        double minimum_degrees_J3 = (Double) minList.get(1);
        System.out.println("minimum_degrees_J2: " + minimum_degrees_J2);
        System.out.println("minimum_degrees_J3: " + minimum_degrees_J3);
        if (minimum_degrees_J2 > MAX_DEGREES_J2)
        {
            minimum_degrees_J2 = MAX_DEGREES_J2;
            minimum_degrees_J3 = this.calculateJ3Degrees(minimum_degrees_J2);
            minimum_L3 = Math.sqrt(Math.pow(L1, 2) + Math.pow(L2, 2) - Math.cos(Math.toRadians(minimum_degrees_J3)) * 2 * L1 * L2 - Math.pow(pomHeight, 2));
            System.out.println("minimum_L3 adjusted because <J2 is too big: " + minimum_L3);
        }
        if (minimum_degrees_J3 < MIN_DEGREES_J3)
        {
            minimum_degrees_J3 = MIN_DEGREES_J3;
            minimum_degrees_J2 = this.calculateJ2Degrees(minimum_degrees_J3);
            minimum_L3 = Math.sqrt(Math.pow(L1, 2) + Math.pow(L2, 2) - Math.cos(Math.toRadians(minimum_degrees_J3)) * 2 * L1 * L2 - Math.pow(pomHeight, 2));
            System.out.println("minimum_L3 adjusted because <J3 is too small: " + minimum_L3);
        }
        this.minimum_ticks_J2 = (int) ((INITIAL_DEGREES_J2 - minimum_degrees_J2) * TICKS_PER_DEGREE_J2);
        this.minimum_ticks_J3 = (int) ((minimum_degrees_J3 - INITIAL_DEGREES_J3) * TICKS_PER_DEGREE_J3);
        System.out.println("minimum_ticks_J2: " + minimum_ticks_J2);
        System.out.println("minimum_ticks_J3: " + minimum_ticks_J3);

        //maximum
        this.maximum_L3 = Math.sqrt(Math.pow(L1 + L2, 2) - Math.pow(this.pomHeight, 2));
        System.out.println("maximum_L3: " + maximum_L3);
        List   maxList            = this.calculateJ2J3Degrees(maximum_L3);
        double maximum_degrees_J2 = (Double) maxList.get(0);
        double maximum_degrees_J3 = (Double) maxList.get(1);
        System.out.println("maximum_degrees_J2: " + maximum_degrees_J2);
        System.out.println("maximum_degrees_J3: " + maximum_degrees_J3);
        if (maximum_degrees_J2 < MIN_DEGREES_J2)
        {
            maximum_degrees_J2 = MIN_DEGREES_J2;
            maximum_degrees_J3 = this.calculateJ3Degrees(maximum_degrees_J2);
            maximum_L3 = Math.sqrt(Math.pow(L1, 2) + Math.pow(L2, 2) - Math.cos(Math.toRadians(maximum_degrees_J3)) * 2 * L1 * L2 - Math.pow(pomHeight, 2));
            System.out.println("maximum_L3 adjusted because <J2 is too small: " + maximum_L3);
        }
        if (maximum_degrees_J3 > MAX_DEGREES_J3)
        {
            maximum_degrees_J3 = MAX_DEGREES_J3;
            maximum_degrees_J2 = this.calculateJ2Degrees(maximum_degrees_J3);
            maximum_L3 = Math.sqrt(Math.pow(L1, 2) + Math.pow(L2, 2) - Math.cos(Math.toRadians(maximum_degrees_J3)) * 2 * L1 * L2 - Math.pow(pomHeight, 2));
            System.out.println("maximum_L3 adjusted because <J3 is too big: " + maximum_L3);
        }
        maximum_ticks_J2 = (int) ((INITIAL_DEGREES_J2 - maximum_degrees_J2) * TICKS_PER_DEGREE_J2);
        maximum_ticks_J3 = (int) ((maximum_degrees_J3 - INITIAL_DEGREES_J3) * TICKS_PER_DEGREE_J3);
        System.out.println("maximum_ticks_J2: " + maximum_ticks_J2);
        System.out.println("maximum_ticks_J3: " + maximum_ticks_J3);

        // apply angle speed to motors
        this.motor1J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.motor2J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if (speed < 0)    //(joystick up) expanding : move J3 first
        {
            if (Math.abs(getJ3CurrentPosition() - maximum_ticks_J3) > ZERO_TICKS)
            {
                this.motorJ3.setTargetPosition(maximum_ticks_J3);
                this.motorJ3.setPower(MAX_POWER_J3 * speed);
                System.out.println("Start POM -- expanding, J3 Target set to: " + super.motorJ3.getTargetPosition());
            }
        }
        else            //(joystick down)retracting: move J2 first
        {
            if (Math.abs(getJ2CurrentPosition() - minimum_ticks_J2) > ZERO_TICKS)
            {
                this.motor1J2.setTargetPosition(minimum_ticks_J2);
                this.motor2J2.setTargetPosition(minimum_ticks_J2);
                this.motor1J2.setPower(MAX_POWER_J2 * speed);
                this.motor2J2.setPower(MAX_POWER_J2 * speed);
                System.out.println("Start POM -- retracting, J2 Target set to: " + super.motor2J2.getTargetPosition());
            }
        }
    }

    private void maintainPOM(double speed)
    {
        // apply angle speed to motors
        if (speed < 0)    //expanding
        {
            double J3ticks = getJ3CurrentPosition();
            double J3Deg   = J3ticks / TICKS_PER_DEGREE_J3 + INITIAL_DEGREES_J3;

            //Dist from J2 to J4
            double J2Deg = this.calculateJ2Degrees(J3Deg);
            this.motor1J2.setTargetPosition((int) ((INITIAL_DEGREES_J2 - J2Deg) * TICKS_PER_DEGREE_J2));
            this.motor2J2.setTargetPosition((int) ((INITIAL_DEGREES_J2 - J2Deg) * TICKS_PER_DEGREE_J2));
            this.motor1J2.setPower(MAX_POWER_J2 * speed);
            this.motor2J2.setPower(MAX_POWER_J2 * speed);
            System.out.println("Maintain POM -- expanding: J3 is at: " + J3ticks);
            System.out.println("Maintain POM -- expanding: J2 Target set to: " + super.motor2J2.getTargetPosition());
        }
        else            //retracting
        {
            double J2ticks = getJ2CurrentPosition();
            double J2Deg   = INITIAL_DEGREES_J2 - J2ticks / TICKS_PER_DEGREE_J2;
            double J3Deg   = this.calculateJ3Degrees(J2Deg);
            this.motorJ3.setTargetPosition((int) ((J3Deg - INITIAL_DEGREES_J3) * TICKS_PER_DEGREE_J3));
            this.motorJ3.setPower(UP_POWER_J3 * speed);
            System.out.println("Maintain POM -- retracting: J2 is at: " + J2ticks);
            System.out.println("Maintain POM -- retracting: J3 Target set to: " + super.motorJ3.getTargetPosition());
        }
    }

    private List<Double> calculateJ2J3Degrees(double L3)
    {
        List<Double> result = new ArrayList<>(2);

        double k_sqrd     = L3 * L3 + pomHeight * pomHeight;
        double k          = Math.sqrt(k_sqrd);
        double angle_J3   = Math.toDegrees(Math.acos((L1 * L1 + L2 * L2 - k_sqrd) / (2 * L1 * L2)));
        double angle_J2_2 = Math.toDegrees(Math.asin(pomHeight / k));
        double angle_J2_1 = Math.toDegrees(Math.acos((L1 * L1 + k_sqrd - L2 * L2) / (2 * L1 * k)));
        double angle_J2   = angle_J2_1 + angle_J2_2 + 90;
        result.add(angle_J2);
        result.add(angle_J3);

        return result;
    }

    private double calculateJ2Degrees(double j3Deg)
    {
        double k_sqrd = Math.sqrt(Math.pow(L2, 2) + Math.pow(L1, 2) - Math.cos(Math.toRadians(j3Deg)) * 2 * L2 * L1);
        double k      = Math.sqrt(k_sqrd);
        double J2Deg1 = Math.toDegrees(Math.asin(pomHeight / k));
        double J2Deg2 = Math.toDegrees(Math.acos((L1 * L1 + k_sqrd - L2 * L2) / (2 * L1 * k)));
        ;

        return 90 + J2Deg1 + J2Deg2;
    }

    private double calculateJ3Degrees(double J2Degrees)
    {
        double height_J2_J3 = Math.sin(Math.toRadians(J2Degrees - 90)) * L1;
        double height_J3_J4 = height_J2_J3 - this.pomHeight;
        double J4Degrees    = Math.toDegrees(Math.asin(height_J3_J4 / L2));

        return 270 - J2Degrees - J4Degrees;
    }


    private double calculateHeight(double J2Deg, double J3Deg)
    {
        if (pomHeight < 0)
        {
            //height
            double J4Deg        = 180 - J3Deg - J2Deg + 90;
            double height_J3_J4 = L2 * Math.sin(Math.toRadians(J4Deg));
            double height_J3_J2 = L1 * Math.sin(Math.toRadians(J2Deg - 90));
            pomHeight = height_J3_J2 - height_J3_J4;
        }
        return pomHeight;
    }

    public void lowerJ4()
    {
        System.out.println("------------------------------------ lowerJ4 start ---------------------------------");
        System.out.println("J2 ticks: " + getJ2CurrentPosition());
        System.out.println("J3 ticks: " + getJ3CurrentPosition());
        double J2Deg = INITIAL_DEGREES_J2 - getJ2CurrentPosition() / TICKS_PER_DEGREE_J2;
        double J3Deg = getJ3CurrentPosition() / TICKS_PER_DEGREE_J3 + INITIAL_DEGREES_J3;
        System.out.println("J2Deg: " + J2Deg);
        System.out.println("J3Deg: " + J3Deg);

        double height = this.calculateHeight(J2Deg, J3Deg);
        System.out.println("height: " + height);

        double k  = Math.sqrt(Math.pow(L1, 2) + Math.pow(L2, 2) - Math.cos(Math.toRadians(J3Deg)) * 2 * L1 * L2);
        double L3 = Math.sqrt(Math.pow(k, 2) - Math.pow(height, 2));
        System.out.println("k 1st: " + k);
        System.out.println("L3: " + L3);

        k = Math.sqrt(Math.pow(L3, 2) + Math.pow(J4_COLLECTION_HEIGHT, 2));
        System.out.println("k 2nd: " + k);
        double collectingJ21 = Math.toDegrees(Math.asin(J4_COLLECTION_HEIGHT / k));
        System.out.println("collectingJ21: " + collectingJ21);
        double collectingJ22 = Math.toDegrees(Math.acos((Math.pow(L1, 2) + Math.pow(k, 2) - Math.pow(L2, 2)) / (2 * L1 * k)));
        System.out.println("collectingJ22: " + collectingJ22);
        double collectingJ2 = 90 + collectingJ22 + collectingJ21;
        System.out.println("collectingJ2: " + collectingJ2);
        double collectingJ3 = Math.toDegrees(Math.acos((Math.pow(L1, 2) + Math.pow(L2, 2) - Math.pow(k, 2)) / (2 * L1 * L2)));
        System.out.println("collectingJ3: " + collectingJ3);
        if (collectingJ2 > MAX_DEGREES_J2)
        {
            collectingJ2 = MAX_DEGREES_J2 - 5;
            System.out.println("collectingJ2 adjusted because J2 is too big: " + collectingJ2);
        }
        if (collectingJ3 < INITIAL_DEGREES_J3)
        {
            collectingJ3 = INITIAL_DEGREES_J3 + 5;
            System.out.println("collectingJ3 adjusted because J3 is too small: " + collectingJ3);
        }
        int J2_ticks = (int) ((INITIAL_DEGREES_J2 - collectingJ2) * TICKS_PER_DEGREE_J2);
        int J3_ticks = (int) ((collectingJ3 - INITIAL_DEGREES_J3) * TICKS_PER_DEGREE_J3);
        System.out.println("J2_ticks: " + J2_ticks);
        System.out.println("J3_ticks: " + J3_ticks);
        this.toPositionWithoutJ1(J2_POWER, UP_POWER_J3, J2_ticks, J2_ticks, J3_ticks);
    }
    public void toPositionWithoutJ1( int j2Position1, int j2Position2, int j3Position)
    {
        this.toPositionWithoutJ1(J2_POWER, UP_POWER_J3, j2Position2, j2Position2, j3Position);
    }

    public void toPositionWithoutJ1(double j2Power, double j3Power, int j2Position1, int j2Position2, int j3Position)
    {
        runTime.reset();
        isStop = false;

        motorJ3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorJ3.setTargetPosition(j3Position);
        motorJ3.setPower(j3Power);

        motor1J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2J2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor1J2.setTargetPosition(j2Position1);
        motor2J2.setTargetPosition(j2Position2);
        motor1J2.setPower(j2Power);
        motor2J2.setPower(j2Power);

        while ((Math.abs(getJ2CurrentPosition() - motor2J2.getTargetPosition()) > ZERO_TICKS || Math.abs(getJ3CurrentPosition() - motorJ3.getTargetPosition()) > ZERO_TICKS) && !isStop && runTime.time(TimeUnit.SECONDS) < 4)
        {
            try
            {
                Thread.sleep(10);
            }
            catch (Exception e) {}
        }

        System.out.println("toPositionWithoutJ1 Time: " + runTime.time(TimeUnit.SECONDS));
    }
}
