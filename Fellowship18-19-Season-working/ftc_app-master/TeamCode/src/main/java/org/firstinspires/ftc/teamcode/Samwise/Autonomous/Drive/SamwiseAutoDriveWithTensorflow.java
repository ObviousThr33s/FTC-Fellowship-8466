package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.Samwise.Autonomous.MarkerDeposit.SamwiseMarkerDeposit;
import org.firstinspires.ftc.teamcode.Samwise.DriveTrain.SamwiseDriveTrain;

import java.util.List;

@Autonomous(name = "Samwise: Auto Drive with Tensorflow", group = "Samwise")
//@Disabled
public class SamwiseAutoDriveWithTensorflow extends LinearOpMode
{
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the Tensor Flow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;
    /* Declare OpMode members. */ SamwiseDriveTrain robot = new SamwiseDriveTrain();   // Use a drivetrain's hardware
    SamwiseMarkerDeposit md = new SamwiseMarkerDeposit();

    private ElapsedTime runtime = new ElapsedTime();

    //TODO: this number may not work on different field. Should use IMU to help
    static final double COUNTS_PER_MOTOR_REV = 482;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 2.0;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = 0.6;
    static final double TURN_SPEED = 0.5;
    static final double INCHES_PER_DEGREE = 0.1640556;


    BNO055IMU imu;

    enum TurnDirection
    {
        LEFT, RIGHT;
    }


    @Override
    public void runOpMode()
    {
        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);
        md.init(hardwareMap);
        this.initIMU();

        //initialize Vuforia
        //TODO
        initVuforia();

        //initialize tensorflow object detector
        if (ClassFactory.getInstance().canCreateTFObjectDetector())
        {
            initTfod();
        }
        else
        {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }
        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        robot.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0", "Starting at %7d :%7d", robot.leftDrive.getCurrentPosition(), robot.rightDrive.getCurrentPosition());
        telemetry.update();

        //tf
        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();


        // Wait for the game to start (driver presses PLAY)
        waitForStart();


        //Find gold mineral position
        GoldPosition position = GoldPosition.UNKNOWN;
        boolean isCrater = false;
        //Activate object detector to get gold position, then shut it down
        if (opModeIsActive() && tfod != null)
        {
            tfod.activate();
            position = GoldPositionUtil.getInstance().getGoldPosition(tfod, CameraOrientation.ROTATE_90);
            isCrater = CraterDepotUtil.getInstance().isCrater(tfod);
            telemetry.addData("Gold Position:", position);
            telemetry.addData("isCrater:", isCrater);
            RobotLog.a("Gold Position:" + position);
            RobotLog.a("isCrater:" + isCrater);
            telemetry.update();
            tfod.deactivate();
            tfod.shutdown();
        }

        System.out.println("This is the " + (isCrater ? "Crater" : "Depot"));

        //TODO: REMOVE testMoveIMU()
        this.testMoveIMU();

        //drive
        this.drive(isCrater, position);
    }

    private void initIMU()
    {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode                = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled      = false;

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = hardwareMap.get(BNO055IMU.class, "imu");

        imu.initialize(parameters);

        // make sure the imu gyro is calibrated before continuing.
        while (!isStopRequested() && !imu.isGyroCalibrated())
        {
            sleep(50);
            idle();
        }
    }
    private void testMoveIMU()
    {

    }

    /**
     * Autonomous drive
     *
     * @param isCrater
     * @param position
     */
    private void drive(boolean isCrater, GoldPosition position)
    {
        //Sampling
        if (isCrater)  //crater
        {
            switch (position)
            {
                case RIGHT: //right
                    turnDrive(30.429725, 2);
                    encoderDrive(DRIVE_SPEED, 28, 28, 5);
                    encoderDrive(DRIVE_SPEED, -8, -8, 4);
                    turnDrive(65, 5);
                    encoderDrive(DRIVE_SPEED, -59, -59, 20);
                    turnDrive(-48, 3);
                    encoderDrive(DRIVE_SPEED, -48, -48, 20);

                    md.move(1);

                    encoderDrive(DRIVE_SPEED, 69, 69, 20);
                    break;
                case LEFT: //left
                    turnDrive(-32.429725, 2);
                    encoderDrive(DRIVE_SPEED, 28, 28, 3);
                    turnDrive(-120, 3);
                    encoderDrive(DRIVE_SPEED, -29, -29, 3);
                    turnDrive(-31, 3);
                    encoderDrive(DRIVE_SPEED, -45, -45, 5);

                    md.move(1);

                    encoderDrive(DRIVE_SPEED, 64, 64, 15);
                    break;
                case CENTER:  //depot
                default:
                    encoderDrive(DRIVE_SPEED, 26, 26, 5);
                    encoderDrive(DRIVE_SPEED, -8, -8, 4);
                    turnDrive(65, 5);
                    encoderDrive(DRIVE_SPEED, -39, -39, 20);
                    turnDrive(-48, 3);
                    encoderDrive(DRIVE_SPEED, -48, -48, 20);

                    md.move(1);

                    encoderDrive(DRIVE_SPEED, 69, 69, 20);
            }
        }
        else        //depot
        {
            switch (position)
            {
                case RIGHT: //right
                    turnDrive(31, 3);
                    encoderDrive(DRIVE_SPEED, 30, 30, 4);
                    turnDrive(-44, 3);
                    encoderDrive(DRIVE_SPEED, 24, 28, 4);
                    turnDrive(147, 3);
                    encoderDrive(DRIVE_SPEED, -6, -6, 2);

                    md.move(1);

                    encoderDrive(DRIVE_SPEED, 38, 38, 2);
                    turnDrive(55, 3);
                    encoderDrive(DRIVE_SPEED, 66, 66, 5);
                    turnDrive(-20, 3);
                    encoderDrive(DRIVE_SPEED, 27, 27, 2);
                    break;
                case LEFT: //left
                    turnDrive(-31, 3);
                    encoderDrive(DRIVE_SPEED, 30, 30, 4);
                    turnDrive(48, 3);
                    encoderDrive(DRIVE_SPEED, 34, 34, 4);
                    turnDrive(110, 3);

                    md.move(1);

                    encoderDrive(DRIVE_SPEED, 33, 33, 2);
                    turnDrive(55, 3);
                    encoderDrive(DRIVE_SPEED, 73, 73, 6);
                    turnDrive(-30, 3);
                    encoderDrive(DRIVE_SPEED, 27, 27, 3);
                    break;
                case CENTER:  //center
                default:
                    encoderDrive(DRIVE_SPEED, 57, 57, 5);
                    turnDrive(124, 3);

                    md.move(1);

                    encoderDrive(DRIVE_SPEED, 45, 45, 3);
                    turnDrive(55, 2);
                    encoderDrive(DRIVE_SPEED, 60, 25, 2);
                    turnDrive(-20, 3);
                    encoderDrive(DRIVE_SPEED, 27, 27, 2);
            }
        }
    }

    /**
     * @param degrees Positive for turning RIGHT, negative for turning LEFT.
     * @param timeout
     */
    protected void turnDrive(double degrees, double timeout)
    {
        double inches = INCHES_PER_DEGREE * degrees;
        encoderDrive(TURN_SPEED, -inches, inches, timeout);
    }

    /*
     *  Method to perfmorm a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the opmode running.
     */
    public void encoderDrive(double speed, double leftInches, double rightInches, double timeoutS)
    {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive())
        {
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
            while (opModeIsActive() && (runtime.seconds() < timeoutS) && (robot.leftDrive.isBusy() && robot.rightDrive.isBusy()))
            {

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
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia()
    {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = GoldPositionUtil.getInstance().getVuforiaKey();
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod()
    {
        int                         tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters    = new TFObjectDetector.Parameters(tfodMonitorViewId);
        //TODO: Adjust confidence value
        //        tfodParameters.minimumConfidence = 0.75;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }
}
