package org.firstinspires.ftc.teamcode.Samwise.DriveTrain;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class SamwiseDriveTrainIMU extends SamwiseDriveTrain
{
    BNO055IMU imu;
    Orientation lastAngles = new Orientation();
    double globalAngle, power = 0.35;
    double resetAngle = 0.0;
    static final int TURN_ERROR_ALLOWED = 5;

    @Override
    public void init(HardwareMap hwm)
    {
        super.init(hwm);
        super.leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        super.rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        //parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";


        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = hwm.get(BNO055IMU.class, "imu");

        boolean imuInitialized = imu.initialize(parameters);

        if (!imuInitialized)
        {
            RobotLog.v("IMU initialization: ", "failed to initialize.");
        }
        else
        {
            RobotLog.v("Mode", "calibrating...");
            // make sure the imu gyro is calibrated before continuing.
            double startTime = System.currentTimeMillis();
            while (!imu.isGyroCalibrated() && !imu.isAccelerometerCalibrated() && System.currentTimeMillis() - startTime < 5000)
            {
                sleep(50);
                idle();
            }
            double nowTime = System.currentTimeMillis();
            if (!imu.isGyroCalibrated() || !imu.isAccelerometerCalibrated())
            {
                RobotLog.v("IMU Calibration: ", " IMU failed to calibrate in 5 seconds.");
            }
            RobotLog.v("Time to calibrate: ", nowTime - startTime + " ms");
            RobotLog.v("imu calib status", imu.getCalibrationStatus().toString());
            RobotLog.v("IMU heading: ", imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES));
            RobotLog.v("Mode", "waiting for start");
        }
    }

    /**
     * Rotate left or right the number of degrees. Does not support turning more than 180 degrees.
     *
     * @param degrees Degrees to turn, - is left + is right
     */
    @Override
    public void turnDrive(LinearOpMode opMode, double degrees, double timeout)
    {

        // restart imu movement tracking.
        resetAngle();

        // set power to rotate.
        if (degrees < 0)
        {
            super.leftDrive.setPower(-power);
            super.rightDrive.setPower(power);

        }
        else if (degrees > 0)
        {
            super.leftDrive.setPower(power);
            super.rightDrive.setPower(-power);
        }
        else return;

        // rotate until turn is completed.
        double currAngle = getAngle();
        if (degrees < 0)
        {

            while (opMode.opModeIsActive() && getAngle() - TURN_ERROR_ALLOWED > degrees)
            {
                //                System.out.println("IMU Right Turn New Angle: " + currAngle);
                //                currAngle = getAngle();
            }
        }
        else    // left turn.
            while (opMode.opModeIsActive() && getAngle() + TURN_ERROR_ALLOWED < degrees)
            {
                //                System.out.println("IMU Left Turn New Angle: " + currAngle);
                //                currAngle = getAngle();
            }

        // turn the motors off.
        super.rightDrive.setPower(0);
        super.leftDrive.setPower(0);

        sleep(1000);

        // wait for rotation to stop.
        RobotLog.v("Rotate requested degrees: ", degrees);
        double currFirstAngle = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
        double deltaAngle     = currFirstAngle - resetAngle;

        if (deltaAngle < -180) deltaAngle += 360;
        else if (deltaAngle > 180) deltaAngle -= 360;

        RobotLog.v("Rotate imu feedback degrees: ", deltaAngle);
        RobotLog.v("IMU started: ", resetAngle);
        RobotLog.v("IMU heading: ", currFirstAngle);
        System.out.println("Rotate requested: " + degrees + "; imu feedback: " + deltaAngle);
        System.out.println("IMU started: " + resetAngle + "; IMU heading: " + currFirstAngle);

    }

    private void sleep(long milliSeconds)
    {
        try
        {
            Thread.sleep(milliSeconds);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
    }

    private final void idle()
    {
        // Otherwise, yield back our thread scheduling quantum and give other threads at
        // our priority level a chance to run
        Thread.yield();
    }

    /**
     * Resets the cumulative angle tracking to zero.
     */
    private void resetAngle()
    {
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        resetAngle = lastAngles.firstAngle;
        globalAngle = 0;
    }

    /**
     * Get current cumulative angle rotation from last reset.
     *
     * @return Angle in degrees. + = left, - = right.
     */
    protected double getAngle()
    {
        // We experimentally determined the Z axis is the axis we want to use for heading angle.
        // We have to process the angle because the imu works in euler angles so the Z axis is
        // returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
        // 180 degrees. We detect this transition and track the total cumulative angle of rotation.

        Orientation angles     = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        double      deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180) deltaAngle += 360;
        else if (deltaAngle > 180) deltaAngle -= 360;

        globalAngle += deltaAngle;

        lastAngles = angles;

        return globalAngle;
    }

    public void updateAngleReadings(Telemetry telemetry)
    {
        telemetry.addData("0 imu angular orientation: ", imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle);
        telemetry.addData("1 imu heading (lastAngle): ", lastAngles.firstAngle);
        telemetry.addData("2 global heading (globalAngle): ", globalAngle);
        telemetry.update();
    }
}
