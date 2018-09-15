package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.teamcode.IMUSystem;

/**
 * Created by Joshua(Just kidding, it was the magical programming fairy) on 10/28/2017.
 */

public class BoschIMU extends IMUSystem {
    BNO055IMU imu;
    double heading;

    BoschIMU(HardwareMap hwMap, String label) {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "AdafruitIMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu = hwMap.get(BNO055IMU.class, label);
        imu.initialize(parameters);
        imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);
        ResetHeading();
    }

    public void ResetHeading() {
        heading = imu.getAngularOrientation().toAxesReference(AxesReference.INTRINSIC).toAxesOrder(AxesOrder.ZYX).firstAngle;
        if (heading < 0) {
            heading = 360 + heading;
        }
    }

    public double GetHeading() {
        double currHeading = imu.getAngularOrientation().toAxesReference(AxesReference.INTRINSIC).toAxesOrder(AxesOrder.ZYX).firstAngle;
        if (currHeading < 0) {
            currHeading = 360 + currHeading;
        }

        double diff = currHeading - heading;
        if (diff > 180) {
            diff = -360 + diff;
        }
        if (diff < -180) {
            diff = 360 + diff;
        }
        return diff;
    }

    public double GetNormalizedHeading() {
        double currHeading = imu.getAngularOrientation().toAxesReference(AxesReference.INTRINSIC).toAxesOrder(AxesOrder.ZYX).firstAngle;
        if (currHeading < 0) {
            currHeading = 360 + currHeading;
        }

        double diff = currHeading - heading;
        if (diff < 0) {
            diff = 360 + diff;
        }
        return diff;
    }

    public Acceleration GetAcceleration() {
        Acceleration acceleration = imu.getAcceleration();
        return acceleration;
    }

    public Velocity GetVelocity(){
        Velocity velocity = imu.getVelocity();
        return  velocity;
    }

}