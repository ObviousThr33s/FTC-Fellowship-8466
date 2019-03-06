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
import com.qualcomm.robotcore.util.ElapsedTime;
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

    protected ElapsedTime runtime = new ElapsedTime();

    // called when init button is  pressed.
    @Override
    public void runOpMode() throws InterruptedException
    {
        imuTrain.init(hardwareMap);
        // wait for start button.

        waitForStart();

        // driveToCrater until end of period.

        //while (opModeIsActive())
        {
            testDrive();
            showDeltaAngle();
        }
    }

    protected  void showDeltaAngle(){

        System.out.println("==>> reset angles with ZYX ... ");
        imuTrain.resetAngle(AxesOrder.ZYX);
        testAngles();
        System.out.println("==>> done angles with ZYX ... ");

        sleep(2000);
        System.out.println("==>> reset angles with YZX ... ");
        imuTrain.resetAngle(AxesOrder.YZX);
        testAngles();System.out.println("==>> done angles with YZX ... ");

    }

    private void testAngles() {
        runtime.reset();

        while (opModeIsActive() && runtime.seconds() < 3){

            double zyx = imuTrain.getAngle(AxesOrder.ZYX);
            double zxy = imuTrain.getAngle(AxesOrder.ZXY);
            double zxz = imuTrain.getAngle(AxesOrder.ZXZ);
            double zyz = imuTrain.getAngle(AxesOrder.ZYZ);

            double xyz = imuTrain.getAngle(AxesOrder.XYZ);
            double xyx = imuTrain.getAngle(AxesOrder.XYX);
            double xzx = imuTrain.getAngle(AxesOrder.XZX);
            double xzy = imuTrain.getAngle(AxesOrder.XZY);

            double yxy = imuTrain.getAngle(AxesOrder.YXY);
            double yxz = imuTrain.getAngle(AxesOrder.YXZ);
            double yzy = imuTrain.getAngle(AxesOrder.YZY);
            double yzx = imuTrain.getAngle(AxesOrder.YZX);

            double defaultAx = imuTrain.getAngle(null);
            System.out.println("==>> reset ZYX: zyx="+zyx+", zxy="+zxy+", zxz="+zxz+", zyz="+zyz
                    + ", xyz="+xyz +", xyx="+xyx+", xzx="+xzx+", xzy="+xzy
                    + ", yxy="+yxy+", yxz="+yxz+", yzy="+yzy+ ", yzx="+yzx
                    + ", defaultAx="+defaultAx);

            System.out.println("==>> YZX delta = " + imuTrain.getAngleDelta(AxesOrder.YZX));

            sleep(50);
            idle();
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