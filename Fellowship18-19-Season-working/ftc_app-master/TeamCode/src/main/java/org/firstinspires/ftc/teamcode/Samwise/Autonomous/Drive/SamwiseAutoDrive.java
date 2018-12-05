package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Samwise.Autonomous.MarkerDeposit.SamwiseMarkerDeposit;
import org.firstinspires.ftc.teamcode.Samwise.Autonomous.Vision.SamwiseVision;
import org.firstinspires.ftc.teamcode.Samwise.DriveTrain.SamwiseDriveTrain;

@Autonomous(name = "SamwiseAutoDrive v1", group = "Samwise")
@Disabled
public class SamwiseAutoDrive extends LinearOpMode {

    // this is set before the robot is initialized
    protected boolean isCrater = false;

    /* Declare OpMode members. */
    SamwiseDriveTrain robot = new SamwiseDriveTrain();   // Use a drivetrain's hardware
    SamwiseVision vis = new SamwiseVision();
    SamwiseMarkerDeposit md = new SamwiseMarkerDeposit();

    private ElapsedTime runtime = new ElapsedTime();

    static final double COUNTS_PER_MOTOR_REV = 482;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 2.0;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = 0.6;
    static final double TURN_SPEED = 0.5;
    static final double INCHES_PER_DEGREE = 0.1640556;

    enum TurnDirection {
        LEFT, RIGHT;
    }

    /**
     * Should be override by all subclasses
     *
     * @return
     */
    protected boolean isOpModeActive() {
        return this.opModeIsActive();
    }


    /**
     * init with and without tensorflow
     *
     * @param tf
     */
    protected void init(boolean tf) {
        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);
        md.init(hardwareMap);

        if (tf) {
            vis.init(hardwareMap);
        }
    }


    @Override
    public void runOpMode() {
        this.init(true);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        //Find gold mineral position and identify crater or depot
        SamwiseVision.GoldPosition position = SamwiseVision.GoldPosition.UNKNOWN;
        //Activate object detector to get gold position, then shut it down
        if (isOpModeActive()) {
            vis.activate();

            position = vis.getGoldPosition();

            System.out.println("==>The Gold Position: " + position);
            System.out.println("==>Is this crater: " + vis.isCrater());
            //telemetry.addData("Gold Position:", position);
            //telemetry.update();
            vis.deactivate();
            vis.shutdown();
        }

        //System.out.println("This is the " + (isCrater ? "Crater" : "Depot"));

        //Sampling
        SamwiseAutoDrive driveRoute = samplingRoute(position);

        /**
         * drive the specific route
         */
        driveRoute.drive();

        // telemetryNow("Autonomous", "Completed");
    }

    /**
     * select sampling drive route
     *
     * @param position
     * @return
     */
    protected SamwiseAutoDrive samplingRoute(SamwiseVision.GoldPosition position) {

        SamwiseAutoDrive driveRoute = null;

        /**
         * todo: externalize the routes
         */
        if (isCrater) {
            switch (position) {
                case RIGHT: //right
                    driveRoute = new SamwiseDriveRouteCraterRight(this);
                    break;
                case LEFT: //left
                    driveRoute = new SamwiseDriveRouteCraterLeft(this);
                    break;
                case CENTER:  //center
                default:
                    driveRoute = new SamwiseDriveRouteCraterCenter(this);
            }
        } else {
            switch (position) {
                case RIGHT: //right
                    driveRoute = new SamwiseDriveRouteDepotRight(this);
                    break;
                case LEFT: //left
                    driveRoute = new SamwiseDriveRouteDepotLeft(this);
                    break;
                case CENTER:  //center
                default:
                    driveRoute = new SamwiseDriveRouteDepotCenter(this);
            }
        }
        return driveRoute;
    }

    protected void turnDrive(TurnDirection direction, double degrees, double timeout) {
        double inches = INCHES_PER_DEGREE * degrees;
        switch (direction) {
            case RIGHT:
                encoderDrive(TURN_SPEED, -inches, inches, timeout);
                break;
            case LEFT:
                encoderDrive(TURN_SPEED, inches, -inches, timeout);
        }
    }

    /*
     *  Method to perfmorm a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the opmode running.
     */
    public void encoderDrive(double speed, double leftInches, double rightInches, double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (isOpModeActive()) {
//            robot.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            robot.rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            //
//            robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//            robot.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


            // Determine new target position, and pass to motor controller
            newLeftTarget = robot.leftDrive.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            newRightTarget = robot.rightDrive.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
            robot.leftDrive.setTargetPosition(newLeftTarget);
            robot.rightDrive.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            robot.leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.leftDrive.setPower(Math.abs(speed));
            robot.rightDrive.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (isOpModeActive() && (runtime.seconds() < timeoutS) && (robot.leftDrive.isBusy() && robot.rightDrive.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1", "Running to %7d :%7d", newLeftTarget, newRightTarget);
                telemetry.addData("Path2", "Running at %7d :%7d", robot.leftDrive.getCurrentPosition(), robot.rightDrive.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            robot.leftDrive.setPower(0);
            robot.rightDrive.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }

    /**
     * Common drive for all routes:
     * 1. Landing
     * 2. Orientation identification
     * 3. Gold mineral identification
     * <p>
     * Routes (Sub classes) need to override this method for their own specific drives
     */
    protected void drive() {

        // landing

        // tensorflow for Orientation identification and Gold mineral identification

    }

    /**
     * util to print on screen now
     *
     * @param name
     * @param obj
     */
    protected void telemetryNow(String name, Object obj) {
        telemetry.addData(name, obj);
        telemetry.update();
    }
}
