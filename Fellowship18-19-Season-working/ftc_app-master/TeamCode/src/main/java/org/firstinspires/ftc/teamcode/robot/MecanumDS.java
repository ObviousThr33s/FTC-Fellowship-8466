package org.firstinspires.ftc.teamcode;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Joshua on 10/28/2017.
 */

public class MecanumDS extends DriveSystem {
    public DcMotor FrontLeft = null;
    public DcMotor FrontRight = null;
    public DcMotor BackLeft = null;
    public DcMotor BackRight = null;

    static long RAMP_UP_TIME = 1000;
    static long RAMP_DOWN_DISTANCE = 500;
    static double SPIN_SLOWDOWN_THRESHOLD = 540.0f;

    static double minPowerMove = 0.03;
    static double minPowerSpin = 0.02;

    Telemetry tl = null;
    boolean debugTelemetry = false;

    LinearOpMode lop;
    boolean useAborting;

    public void setLinearOpMode(LinearOpMode lop) {
        if (lop != null) {
            this.lop = lop;
            useAborting = true;
        } else {
            this.lop = null;
            useAborting = false;
        }
    }

    MecanumDS(HardwareMap hwMap, Telemetry telemetry, IMUSystem imuSys, String flLabel, String frLabel, String blLabel, String brLabel) {
        tl = telemetry;
        imu = imuSys;

        FrontLeft = hwMap.get(DcMotor.class, flLabel);
        FrontRight = hwMap.get(DcMotor.class, frLabel);
        BackLeft = hwMap.get(DcMotor.class, blLabel);
        BackRight = hwMap.get(DcMotor.class, brLabel);

        //Left motors on Keith robot are mounted backwards.
        FrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        BackRight.setDirection(DcMotorSimple.Direction.REVERSE);

        FrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        setMotorPower(0, 0, 0, 0);

        setEncoders(true);
    }

    public void setMotorPower(double FL, double FR, double BL, double BR) {
        //normalize range to [-1,1]
        Utilities.PowerLevels normalizedPower = Utilities.NormalizePower(new Utilities.PowerLevels(FL, FR, BL, BR), 1);
        if (debugTelemetry) {
            tl.addData("", "FL: %.3f, FR: %.3f, BL: %.3f, BR: %.3f", normalizedPower.powerBL, normalizedPower.powerFR, normalizedPower.powerBL, normalizedPower.powerBR);
        }
        FrontLeft.setPower(normalizedPower.powerFL);
        FrontRight.setPower(normalizedPower.powerFR);
        BackLeft.setPower(normalizedPower.powerBL);
        BackRight.setPower(normalizedPower.powerBR);
    }

    public void setEncoders(boolean allow) {
        if (allow) {
            FrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            BackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            BackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        } else {
            FrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            FrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            BackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            BackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    public double GetAverageEncodersValue() {
        double flAbs = Math.abs(FrontLeft.getCurrentPosition());
        double frAbs = Math.abs(FrontRight.getCurrentPosition());
        double blAbs = Math.abs(BackLeft.getCurrentPosition());
        double brAbs = Math.abs(BackRight.getCurrentPosition());

        return (flAbs + frAbs + blAbs + brAbs) / 4;
    }

    public void ResetEncoders() {
        DcMotor.RunMode flMode = FrontLeft.getMode();
        DcMotor.RunMode frMode = FrontRight.getMode();
        DcMotor.RunMode blMode = BackLeft.getMode();
        DcMotor.RunMode brMode = BackRight.getMode();
        FrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontLeft.setMode(flMode);
        FrontRight.setMode(frMode);
        BackLeft.setMode(blMode);
        BackRight.setMode(brMode);
    }

    boolean ExecuteSpin(double headingGoal, double maxPower, double minPower, double tolerance) {
        double currentHeading = imu.GetHeading();
        long originalTime = System.currentTimeMillis();
        double targetPower = maxPower;
        if (targetPower > maxPower) {
            targetPower = maxPower;
        }
        double slowDownDegrees = SPIN_SLOWDOWN_THRESHOLD * (targetPower - minPower);
        while (currentHeading > (headingGoal + tolerance) || currentHeading < (headingGoal - tolerance)) {
            if (useAborting && !lop.opModeIsActive()) {
                //abort due to turning off the OpMode
                break;
            }
            double currentPower = targetPower;
            long diffTime = System.currentTimeMillis() - originalTime;
            if (diffTime <= RAMP_UP_TIME) {
                //apply the "s" curve on the power based on time for acceleration
                currentPower = Utilities.Calculate_S_Curve(0, currentPower, 0, RAMP_UP_TIME, diffTime);
            }

            currentHeading = imu.GetHeading();
            if (Math.abs(headingGoal - currentHeading) <= slowDownDegrees) {
                //apply the "s" curve on the power based on the remaining number of degrees for deceleration
                currentPower = Utilities.Calculate_S_Curve(currentPower, minPower, slowDownDegrees, 0, Math.abs(headingGoal - currentHeading));
            }

            if (debugTelemetry) {
                tl.addData("", "TargetPower: %.3f, SlowDegrees: %.3f", targetPower, slowDownDegrees);
                tl.addData("", "CP: %.3f, CH: %.3f", currentPower, currentHeading);
            }

            if (currentHeading > headingGoal) {
                setMotorPower(currentPower, -currentPower, currentPower, -currentPower);
            }
            if (currentHeading < headingGoal) {
                setMotorPower(-currentPower, currentPower, -currentPower, currentPower);
            }

            // if we got to the end, let's wait for another 200 ms to settle down
            if (currentHeading > (headingGoal - tolerance) && currentHeading < (headingGoal + tolerance)) {
                setMotorPower(0, 0, 0, 0);
                long startTime = System.currentTimeMillis();
                while (System.currentTimeMillis() - startTime <= 100) ;
            }
        }
        setMotorPower(0, 0, 0, 0);
        if (debugTelemetry) {
            tl.addData("", "Final angle: %.3f", imu.GetHeading());
        }
        return true;
    }

    boolean ExecuteMove(double direction, double distance, double maxPower, double minPower, double tolerance) {
        //coordinate conversion
        double directionR = Math.toRadians(direction);
        double x = maxPower * Math.sin(-directionR);
        double y = maxPower * Math.cos(directionR);

        //power configuration
        double frontLeftPower = y + x;
        double frontRightPower = y - x;
        double backLeftPower = y - x;
        double backRightPower = y + x;

        //normalize range to [-1,1]
        double highValue = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
        highValue = Math.max(highValue, Math.abs(backLeftPower));
        highValue = Math.max(highValue, Math.abs(backRightPower));
        frontLeftPower = frontLeftPower / highValue * maxPower;
        frontRightPower = frontRightPower / highValue * maxPower;
        backLeftPower = backLeftPower / highValue * maxPower;
        backRightPower = backRightPower / highValue * maxPower;

        // in order to ramp up or down the power this function will use a modifier
        // which is a value between 0 and 1 that will be multiplied with the expected
        // power for each motor, effectively obtaining the same power pattern but
        // with lower values.
        // In order to do this properly we need to find out the minimum value for
        // the modifier, otherwise we are running the risk of some motors getting
        // power level below the minimum power that would make them move. But we
        // will only do this for the motors for which the absolute power is greater
        // than the minimum power, otherwise
        double minModifier = 1;
        double tempModifier = 1;
        tempModifier = minPower / Math.abs(frontLeftPower);
        if (tempModifier < minModifier) {
            minModifier = tempModifier;
        }
        tempModifier = minPower / Math.abs(frontRightPower);
        if (tempModifier < minModifier) {
            minModifier = tempModifier;
        }
        tempModifier = minPower / Math.abs(backLeftPower);
        if (tempModifier < minModifier) {
            minModifier = tempModifier;
        }
        tempModifier = minPower / Math.abs(backRightPower);
        if (tempModifier < minModifier) {
            minModifier = tempModifier;
        }

        //record starting time
        long originalTime = System.currentTimeMillis();

        ResetEncoders();
        double originalEncoderValue = GetAverageEncodersValue();

        while (true) {
            if (useAborting && !lop.opModeIsActive()) {
                //abort due to turning off the OpMode
                break;
            }
            double currentEncoderValue = GetAverageEncodersValue();
            double diffEncoderValue = currentEncoderValue - originalEncoderValue;
            if (diffEncoderValue >= (distance - tolerance)) {
                break;
            }
            long diffTime = System.currentTimeMillis() - originalTime;
            double modifier = 1;
            if (diffTime <= RAMP_UP_TIME) {
                //apply the "s" curve on the power based on time for acceleration
                modifier = Utilities.Calculate_S_Curve(0, 1, 0, RAMP_UP_TIME, diffTime);
            }

            if ((distance - diffEncoderValue) <= RAMP_DOWN_DISTANCE) {
                //apply the "s" curve on the power based on the remaining distance for deceleration
                double localModifier = Utilities.Calculate_S_Curve(1, minModifier, RAMP_DOWN_DISTANCE, 0, distance - diffEncoderValue);
                if (localModifier < modifier) {
                    modifier = localModifier;
                }
            }
            double localFLPower = frontLeftPower * modifier;
            double localFRPower = frontRightPower * modifier;
            double localBLPower = backLeftPower * modifier;
            double localBRPower = backRightPower * modifier;

            //TODO - apply any correction we might need due to drift

            if (debugTelemetry) {
                tl.addData("", "FLE: %d, FRE: %d, BLE: %d, BRE: %d", FrontLeft.getCurrentPosition(), FrontRight.getCurrentPosition(), BackLeft.getCurrentPosition(), BackRight.getCurrentPosition());
                tl.addData("", "OrgEnc: %.3f, CurrEnc: %.3f", originalEncoderValue, currentEncoderValue);
                tl.addData("", "Dist: %.3f, DiffD: %.3f", distance, diffEncoderValue);
                tl.addData("", "Modifier: %.3f", modifier);
                tl.addData("", "FL: %.3f, FR: %.3f, BL: %.3f, BR: %.3f", localFLPower, localFRPower, localBLPower, localBRPower);
            }

            setMotorPower(localFLPower, localFRPower, localBLPower, localBRPower);
        }
        setMotorPower(0, 0, 0, 0);
        return true;
    }

    public boolean Move(double power, double direction, double spin, double distance, int timeout) {
        //power range check
        assert 0 <= power && power <= 1;
        //spin check
        assert -180 <= spin && spin <= 180;
        //distance check
        assert distance >= 0;

        //make sure encoders are on, our minimal speeds
        //won't work with encoders off
        setEncoders(true);

        //reset heading to zero
        imu.ResetHeading();

        if (distance > 0) {
            if (useAborting && !lop.opModeIsActive()) {
                //abort due to turning off the OpMode
                return false;
            }
            ExecuteMove(direction, distance, power, minPowerMove, 50);
        }
        if (spin != 0) {
            if (useAborting && !lop.opModeIsActive()) {
                //abort due to turning off the OpMode
                return false;
            }
            ExecuteSpin(spin, power, minPowerSpin, 0.1);
        }

        return true;
    }

    final static String fileName = "data.txt";
    final static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/instinctcoder/readwrite/";

    public static boolean saveToFile(String data) {
        try {
            new File(path).mkdir();
            File file = new File(path + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            fileOutputStream.write((data + System.getProperty("line.separator")).getBytes());
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
