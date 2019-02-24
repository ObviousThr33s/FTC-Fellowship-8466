package org.firstinspires.ftc.teamcode.Samwise.DriveTrain;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.AbstractPhysical.DriveTrain;

public class SamwiseDriveTrain extends DriveTrain {

    public DcMotor leftDrive = null;
    public DcMotor rightDrive = null;
    static final double COUNTS_PER_MOTOR_REV = 482;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 2.0;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 2.0;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = 1;
    static final double TURN_SPEED = 1;
    static final double INCHES_PER_DEGREE = 0.1640556;
    protected ElapsedTime runtime = new ElapsedTime();

    public SamwiseDriveTrain() {
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap hwm) {

        // Define and Initialize MotorsAndServos
        leftDrive = hwm.get(DcMotor.class, "left_drive");
        rightDrive = hwm.get(DcMotor.class, "right_drive");
        leftDrive.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        rightDrive.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors

        // Set all motors to zero power
        leftDrive.setPower(0);
        rightDrive.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        //leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        System.out.println("==> SamwiseDriveTrain.init : initialized");
    }

    /*
     *  Method to perform a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the opmode running.
     */
    public void encoderDrive(LinearOpMode opMode, double speed, double leftInches, double rightInches, double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opMode.opModeIsActive()) {
            // Determine new target position, and pass to motor controller
            newLeftTarget = leftDrive.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            newRightTarget = rightDrive.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
            leftDrive.setTargetPosition(newLeftTarget);
            rightDrive.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            leftDrive.setPower(Math.abs(speed));
            rightDrive.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opMode.opModeIsActive() && (runtime.seconds() < timeoutS) && (leftDrive.isBusy() && rightDrive.isBusy())) {
                opMode.idle();
            }

            // Stop all motion;
            leftDrive.setPower(0);
            rightDrive.setPower(0);

            //  sleep(250);   // optional pause after each move
        }
    }

    public void encoderDrive(LinearOpMode opMode, double inches, double timeoutS) {
        System.out.println("==> driving robot " + inches + " inches...");
        encoderDrive(opMode, DRIVE_SPEED, inches, inches, timeoutS);
    }

    public void encoderDriveSpeed(LinearOpMode opMode, double inches, double timeoutS, double speed) {
        System.out.println("==> driving robot " + inches + " inches...");
        encoderDrive(opMode, speed, inches, inches, timeoutS);
    }


    public void encoderDrive(LinearOpMode opMode, double leftInches, double rightInches, double timeoutS) {
        //System.out.println("==> driving robot " + leftInches + " inches...");
        encoderDrive(opMode, DRIVE_SPEED, leftInches, rightInches, timeoutS);
    }

    public void turn(LinearOpMode opMode, double degrees, double timeout) {
        double inches = INCHES_PER_DEGREE * degrees;
        System.out.println("==> turning robot " + degrees + " degrees...");
        encoderDrive(opMode, TURN_SPEED, inches, -inches, timeout);
    }

    @Override
    public void drive(double power) {
        leftDrive.setPower(power);
        rightDrive.setPower(power);
    }

    @Override
    public void turnleft(double power) {
        leftDrive.setPower(power);
        rightDrive.setPower(-power);
    }

    @Override
    public void turnright(double power) {
        leftDrive.setPower(-power);
        rightDrive.setPower(power);
    }

    /**
     * Drive along the wall using front side sensor until the front sensor is pressed
     *
     * @param opMode
     * @param frontside
     * @param front
     * @param timeout
     */
    public void driveToCrater(LinearOpMode opMode, DigitalChannel frontside, DigitalChannel front, boolean rightTurn, double timeout) {

        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // reset the timeout time and start motion.
        runtime.reset();
        drive(Math.abs(DRIVE_SPEED));
        System.out.println("==> driveToCrater with frontside and front touch sensors ... ");

        while (opMode.opModeIsActive() && (runtime.seconds() < timeout) && frontside.getState() && front.getState()) {
            // robot keeps driving until side or front touch sensor is pressed
            opMode.idle();
        }

        // Stop all motion;
        drive(0);

        if (!front.getState() || (runtime.seconds() >= timeout) || !opMode.opModeIsActive()) {
            System.out.println("==> front touch sensor is pressed. Stopping...");
            return; // stop
        }

        if (!frontside.getState()) {
            // side sensor is pressed. turning robot ....
            System.out.println("==> frontside sensor is pressed. turning robot ....");
            if (rightTurn)
                turn(opMode, -6.1, 2);
            else
                turn(opMode, 6.1, 2);

            driveToCrater(opMode, frontside, front, rightTurn, timeout); // recursive call till front touch sensor is hit
        }

    }

    public void driveToDepotFromSide(LinearOpMode opMode, DigitalChannel touch, boolean forward, SamwiseColor colorSensor, double timeout) {

        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // reset the timeout time and start motion.
        runtime.reset();
        if (forward) {
            drive(DRIVE_SPEED);
        } else {
            drive(-DRIVE_SPEED);
        }
        System.out.println("==> drive to depot till seeing the blue or red line ... ");

        while (opMode.opModeIsActive() && (runtime.seconds() < timeout) && touch.getState() &&
                (colorSensor.senseColor() == SamwiseColor.color.GREY)) {
            // robot keeps driving until side  is pressed
            opMode.idle();
        }

        // Stop all motion;
        drive(0);

        if (colorSensor.senseColor() != SamwiseColor.color.GREY
                || (runtime.seconds() >= timeout) || !opMode.opModeIsActive()) {
            System.out.println("==> reached depot because samwise saw the color line : " + colorSensor.senseColor());
            return; // stop
        }

        if (!touch.getState()) {
            // side sensor is pressed. turning robot ....
            System.out.println("==> side sensor is pressed. turning robot ....");
            if(forward){
                this.turn(opMode, -8, 2);
            } else {
                this.turn(opMode, 8, 2);
            }

            driveToDepotFromSide(opMode, touch, forward, colorSensor, timeout); // recursive call till front touch sensor is hit
        }

        System.out.println("==> should never reach here !!! ");

    }

    public void driveToDepotFromCenter(LinearOpMode opMode, double power, SamwiseColor colorSensor, double timeout) {

        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // reset the timeout time and start motion.
        runtime.reset();
        drive(power);

        System.out.println("==> drive from center to depot till seeing the blue or red line ... ");

        while (opMode.opModeIsActive() && (runtime.seconds() < timeout) &&
                (colorSensor.senseColor() == SamwiseColor.color.GREY)) {
            // robot keeps driving until side  is pressed
            opMode.idle();
        }

        // Stop all motion;
        drive(0);


        System.out.println("==> reached depot because samwise saw the color line : " + colorSensor.senseColor());
        return; // stop

    }

    public void driveToWall(LinearOpMode opMode, DigitalChannel backside, boolean forward, double timeout) {

        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // reset the timeout time and start motion.
        runtime.reset();
        drive(Math.abs(DRIVE_SPEED));
        if (forward) {
            drive(DRIVE_SPEED);
        } else {
            drive(-DRIVE_SPEED);
        }
        System.out.println("==> driveToCrater with back sensor ... ");

        while (opMode.opModeIsActive() && (runtime.seconds() < timeout) && backside.getState()) {
            // robot keeps driving until back touch sensor is pressed
            opMode.idle();
        }

        // Stop all motion;
        drive(0);

        System.out.println("==> back touch sensor is pressed. Stopping...");

    }

}

