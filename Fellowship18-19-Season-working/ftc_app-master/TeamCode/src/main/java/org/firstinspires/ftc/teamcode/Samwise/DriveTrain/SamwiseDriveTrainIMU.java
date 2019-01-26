package org.firstinspires.ftc.teamcode.Samwise.DriveTrain;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class SamwiseDriveTrainIMU extends SamwiseDriveTrain {
    BNO055IMU imu;
    Orientation lastAngles = new Orientation();
    double globalAngle, power = 1;
    double resetAngle = 0.0;
    static final int TURN_ERROR_ALLOWED = 3;
    double initAngle;
    private boolean firstTime = true;
    double craterRimAngle = 1.6; // 2 degrees

    @Override
    public void init(HardwareMap hwm) {
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

        if (!imuInitialized) {
            //RobotLog.v("IMU initialization: ", "failed to initialize.");
            System.out.println("==> IMU initialization: failed to initialize.");
        } else {
            //RobotLog.v("Mode", "calibrating...");
            System.out.println("==> IMU initialization: calibrating ...");
            // make sure the imu gyro is calibrated before continuing.
            double startTime = System.currentTimeMillis();
            while (!imu.isGyroCalibrated() && !imu.isAccelerometerCalibrated() && System.currentTimeMillis() - startTime < 5000) {
                sleep(50);
                idle();
            }
            double nowTime = System.currentTimeMillis();
            if (!imu.isGyroCalibrated() || !imu.isAccelerometerCalibrated()) {
                //RobotLog.v("IMU Calibration: ", " IMU failed to calibrate in 5 seconds.");
                System.out.println("==> IMU initialization: IMU failed to calibrate in 5 seconds.");
            }
            System.out.println("==>Time to calibrate: " + (nowTime - startTime) + " ms");
            System.out.println("==>imu calib status" + imu.getCalibrationStatus().toString());
            System.out.println("==>IMU heading: " + imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES));
            System.out.println("==>Mode" + "waiting for start");
        }
    }

    /**
     * Rotate left or right the number of degrees. Does not support turning more than 180 degrees.
     *
     * @param degrees Degrees to turn, - is left + is right
     */
    //@Override
    public void turnDrive(LinearOpMode opMode, double degrees, double timeout) {
        System.out.println("==> IMU turns degree " + degrees);
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // restart imu movement tracking.
        resetAngle(AxesOrder.ZYX);

        // set power to rotate.
        if (degrees < 0) {
            super.leftDrive.setPower(-power);
            super.rightDrive.setPower(power);

        } else if (degrees > 0) {
            super.leftDrive.setPower(power);
            super.rightDrive.setPower(-power);
        } else return;

        // rotate until turn is completed.
        double currAngle = getAngle(AxesOrder.ZYX);
        //if (degrees < 0) {

        while (opMode.opModeIsActive() && Math.abs(currAngle - degrees) > TURN_ERROR_ALLOWED) {
            System.out.println("==> IMU Turn current angle: " + currAngle);
            currAngle = getAngle(AxesOrder.ZYX);
            idle();
        }
            /*
        } else    // left turn.
            while (opMode.opModeIsActive() && Math.abs(currAngle - TURN_ERROR_ALLOWED) < degrees) {
                System.out.println("==> IMU Left Turn New Angle: " + currAngle);
                currAngle = getAngle(AxesOrder.ZYX);
                idle();
            }*/

        // turn the motors off.
        super.rightDrive.setPower(0);
        super.leftDrive.setPower(0);

        sleep(300);

        // wait for rotation to stop.
        RobotLog.v("Rotate requested degrees: ", degrees);
        double currFirstAngle = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
        double deltaAngle = currFirstAngle - resetAngle;

        if (deltaAngle < -180) deltaAngle += 360;
        else if (deltaAngle > 180) deltaAngle -= 360;

        RobotLog.v("Rotate imu feedback degrees: ", deltaAngle);
        RobotLog.v("IMU started: ", resetAngle);
        RobotLog.v("IMU heading: ", currFirstAngle);
        System.out.println("==> Rotate requested: " + degrees + "; imu feedback: " + deltaAngle);
        System.out.println("==> IMU started: " + resetAngle + "; IMU heading: " + currFirstAngle);

    }

    private void sleep(long milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private final void idle() {
        // Otherwise, yield back our thread scheduling quantum and give other threads at
        // our priority level a chance to run
        Thread.yield();
    }

    /**
     * Resets the cumulative angle tracking to zero.
     */
    public void resetAngle(AxesOrder axesOrder) {

        System.out.println("==>> reset Angle ...");

        sleep(300); // wait till the robot is still

        if (axesOrder != null)
            lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, axesOrder, AngleUnit.DEGREES);
        else
            lastAngles = imu.getAngularOrientation();
        resetAngle = lastAngles.firstAngle;
        globalAngle = 0;

        firstTime = true;
    }

    /**
     * Get current cumulative angle rotation from last reset.
     *
     * @return Angle in degrees. + = left, - = right.
     */
    public double getAngle(AxesOrder axesOrder) {
        // We experimentally determined the Z axis is the axis we want to use for heading angle.
        // We have to process the angle because the imu works in euler angles so the Z axis is
        // returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
        // 180 degrees. We detect this transition and track the total cumulative angle of rotation.

        Orientation angles;
        if (axesOrder != null) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, axesOrder, AngleUnit.DEGREES);
        } else {
            angles = imu.getAngularOrientation(); // default is the angle flat with the rev hub
        }


        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180) deltaAngle += 360;
        else if (deltaAngle > 180) deltaAngle -= 360;

        globalAngle += deltaAngle;

        lastAngles = angles;

        return globalAngle;

        //System.out.println("==> IMU Gyro angles: " + angles);
        //return angles.firstAngle;
    }

    public double getAngleDelta(AxesOrder axesOrder) {
        // We experimentally determined the Z axis is the axis we want to use for heading angle.
        // We have to process the angle because the imu works in euler angles so the Z axis is
        // returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
        // 180 degrees. We detect this transition and track the total cumulative angle of rotation.


        Orientation angles;
        if (axesOrder != null) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, axesOrder, AngleUnit.DEGREES);
        } else {
            angles = imu.getAngularOrientation(); // default is the angle flat with the rev hub
        }

        if (this.firstTime) {
            initAngle = angles.firstAngle;
            firstTime = false;
        }

        double deltaAngle = angles.firstAngle - this.initAngle;

        if (deltaAngle < -180) deltaAngle += 360;
        else if (deltaAngle > 180) deltaAngle -= 360;

        System.out.println("==>> Angle change along axle " + axesOrder.name() + " = " + deltaAngle);

        return deltaAngle;
    }

    public void driveToCrater(LinearOpMode opMode, DigitalChannel frontside, boolean rightTurn, double timeout) {

        //resetAngle(AxesOrder.ZYX);

        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // reset the timeout time and start motion.
        runtime.reset();
        int newLeftTarget = leftDrive.getCurrentPosition() + (int) (200 * COUNTS_PER_INCH);
        int newRightTarget = rightDrive.getCurrentPosition() + (int) (200 * COUNTS_PER_INCH);
        leftDrive.setTargetPosition(newLeftTarget);
        rightDrive.setTargetPosition(newRightTarget);

        // Turn On RUN_TO_POSITION
        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        drive(DRIVE_SPEED);
        System.out.println("==> IMU driveToCrater along the wall with frontside sensor ... ");

        double deltaAngle = Math.abs(getAngleDelta(AxesOrder.YZX));

        while (opMode.opModeIsActive() && (runtime.seconds() < timeout) && frontside.getState()
                && (leftDrive.isBusy() && rightDrive.isBusy())
                && deltaAngle < craterRimAngle) {
            // robot keeps driving until side or front touch sensor is pressed
            deltaAngle = Math.abs(getAngleDelta(AxesOrder.YZX));
            opMode.idle();
        }

        // Stop all motion;
        drive(0);

        if (deltaAngle >= craterRimAngle || (runtime.seconds() >= timeout) || !opMode.opModeIsActive()) {
            System.out.println("==> robot is tilted on the RIM. Stopping...");
            return; // stop
        }

        if (!frontside.getState()) {
            // side sensor is pressed. turning robot ....
            System.out.println("==> frontside sensor is pressed. turning robot ....");
            if (rightTurn)
                turnDrive(opMode, -6.1, 2);
            else
                turnDrive(opMode, 6.1, 2);

            driveToCrater(opMode, frontside, rightTurn, timeout); // recursive call till front touch sensor is hit
        }

    }

    public void updateAngleReadings(Telemetry telemetry) {
        telemetry.addData("0 imu angular orientation: ", imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle);
        telemetry.addData("1 imu heading (lastAngle): ", lastAngles.firstAngle);
        telemetry.addData("2 global heading (globalAngle): ", globalAngle);
        telemetry.update();
    }
}
