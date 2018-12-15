package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Autonomous(name = "IMU velocity right 90", group = "Exercises")
@Disabled
public class DriveIMUVelocityTurns extends LinearOpMode
{
    public DcMotor leftMotor = null;
    public DcMotor rightMotor = null;

    BNO055IMU imu;
    Orientation lastAngles = new Orientation();
    double globalAngle, power = 0.35;
    double resetAngle = 0.0;
    static final int TURN_ERROR_ALLOWED = 5;

    // called when init button is  pressed.
    @Override
    public void runOpMode() throws InterruptedException
    { // Define and Initialize MotorsAndServos
        leftMotor = super.hardwareMap.get(DcMotor.class, "left_drive");
        rightMotor = super.hardwareMap.get(DcMotor.class, "right_drive");
        leftMotor.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        rightMotor.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors

        // Set all motors to zero power
        leftMotor.setPower(0);
        rightMotor.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        //leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // wait for start button.

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        //parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";


        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = super.hardwareMap.get(BNO055IMU.class, "imu");

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
        waitForStart();

        // drive until end of period.

        while (opModeIsActive())
        {
            testDrive();
        }
    }

    protected void testDrive()
    {

        // turn 90 degrees right.
        this.rotate(-90, power);
    }

    /**
     * Rotate left or right the number of degrees. Does not support turning more than 180 degrees.
     *
     * @param degrees Degrees to turn, - is left + is right
     */ protected void rotate(int degrees, double power)
    {
        if (!(leftMotor instanceof DcMotorEx))
        {
            telemetry.addData("Drive train motors do not support expanded functions", leftMotor.getClass());
            telemetry.update();
            return;
        }

        // restart imu movement tracking.
        resetAngle();

        int speed = 10;

        // set power to rotate.
        if (degrees < 0)
        {
            ((DcMotorEx)leftMotor).setVelocity(-speed, AngleUnit.DEGREES);
            ((DcMotorEx)rightMotor).setVelocity(speed, AngleUnit.DEGREES);

        }
        else if (degrees > 0)
        {
            ((DcMotorEx)leftMotor).setVelocity(speed, AngleUnit.DEGREES);
            ((DcMotorEx)rightMotor).setVelocity(-speed, AngleUnit.DEGREES);
        }
        else return;

        // rotate until turn is completed.
        double currAngle = getAngle();
        if (degrees < 0)
        {

            while (opModeIsActive() && getAngle() - TURN_ERROR_ALLOWED > degrees)
            {
                //                System.out.println("IMU Right Turn New Angle: " + currAngle);
                //                currAngle = getAngle();
            }
        }
        else    // left turn.
            while (opModeIsActive() && getAngle() + TURN_ERROR_ALLOWED < degrees)
            {
                //                System.out.println("IMU Left Turn New Angle: " + currAngle);
                //                currAngle = getAngle();
            }

        // turn the motors off.
        rightMotor.setPower(0);
        leftMotor.setPower(0);

        sleep(1000);

        // wait for rotation to stop.
        telemetry.addData("Rotate requested degrees: ", degrees);
        double currFirstAngle = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
        double deltaAngle     = currFirstAngle - resetAngle;

        if (deltaAngle < -180) deltaAngle += 360;
        else if (deltaAngle > 180) deltaAngle -= 360;

        telemetry.addData("Rotate imu feedback degrees: ", deltaAngle);
        telemetry.addData("IMU started: ", resetAngle);
        telemetry.addData("IMU heading: ", currFirstAngle);
        telemetry.update();
        System.out.println("Rotate requested: " + degrees + "; imu feedback: " + deltaAngle);
        System.out.println("IMU started: " + resetAngle + "; IMU heading: " + currFirstAngle);

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
}
