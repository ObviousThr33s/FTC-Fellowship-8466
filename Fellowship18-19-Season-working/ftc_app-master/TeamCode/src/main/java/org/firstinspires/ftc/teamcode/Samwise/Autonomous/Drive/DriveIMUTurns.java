// Simple autonomous program that drives bot forward until end of period
// or touch sensor is hit. If touched, backs up a bit and turns 90 degrees
// right and keeps going. Demonstrates obstacle avoidance and use of the
// REV Hub's built in IMU in place of a gyro. Also uses gamepad1 buttons to
// simulate touch sensor press and supports left as well as right turn.
//
// Also uses IMU to driveToCrater in a straight line when not avoiding an obstacle.

package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.NavUtil;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.Samwise.DriveTrain.SamwiseDriveTrainIMU;

@Autonomous(name = "IMU turns", group = "Exercises")
@Disabled
public class DriveIMUTurns extends LinearOpMode
{
    SamwiseDriveTrainIMU imuTrain = new SamwiseDriveTrainIMU();

    // called when init button is  pressed.
    @Override
    public void runOpMode() throws InterruptedException
    {
        imuTrain.init(hardwareMap);
        // wait for start button.

        waitForStart();

        // driveToCrater until end of period.

        while (opModeIsActive())
        {
            testDrive();
        }
    }

    protected void testDrive()
    {
        imuTrain.turnDrive(this, 90, 5);
        this.sleep(1000);
        imuTrain.updateAngleReadings(telemetry);
        this.sleep(1000);
        imuTrain.turnDrive(this, -90, 5);
        this.sleep(1000);
        imuTrain.updateAngleReadings(telemetry);
        this.sleep(1000);
    }
}