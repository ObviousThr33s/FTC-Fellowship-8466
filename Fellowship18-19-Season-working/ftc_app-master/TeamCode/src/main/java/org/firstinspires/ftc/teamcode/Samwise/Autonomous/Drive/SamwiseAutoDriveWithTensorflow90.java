package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.AbstractPhysical.Vision;
import org.firstinspires.ftc.teamcode.Samwise.Autonomous.MarkerDeposit.SamwiseMarkerDeposit;
import org.firstinspires.ftc.teamcode.Samwise.Autonomous.Vision.SamwiseVision;
import org.firstinspires.ftc.teamcode.Samwise.DriveTrain.SamwiseDriveTrain;

import java.util.List;

@Autonomous(name = "AutoDrive v2: Auto Drive with Tensorflow 90 View", group = "Samwise")
@Disabled
public class SamwiseAutoDriveWithTensorflow90 extends LinearOpMode
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
    /* Declare OpMode members. */
    SamwiseDriveTrain robot = new SamwiseDriveTrain();   // Use a drivetrain's hardware
    Vision vis = new SamwiseVision();
    SamwiseMarkerDeposit md = new SamwiseMarkerDeposit();

    private ElapsedTime runtime = new ElapsedTime();

    static final double COUNTS_PER_MOTOR_REV = 482;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 2.0;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = 0.6;
    static final double TURN_SPEED = 0.5;
    static final double INCHES_PER_DEGREE = 0.1640556;

    enum TurnDirection
    {
        LEFT, RIGHT;
    }

    /**
     * Should be override by all subclasses
     * @return
     */
    protected boolean isOpModeActive(){
        return this.opModeIsActive();
    }

    @Override
    public void runOpMode()
    {
        this.init(true);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        //Find gold mineral position and identify crater or depot
        GoldPosition position = GoldPosition.UNKNOWN;
        boolean isCrater = false;
        //Activate object detector to get gold position, then shut it down
        if (isOpModeActive() && tfod != null)
        {
            tfod.activate();
            position = GoldPositionUtil.getInstance().getGoldPosition(tfod, CameraOrientation.ROTATE_90);
            isCrater = CraterDepotUtil.getInstance().isCrater(tfod);
            telemetry.addData("Gold Position:", position);
            telemetry.addData("isCrater:", isCrater);
            telemetry.update();
            tfod.deactivate();
            tfod.shutdown();
        }

        System.out.println("This is the "+(isCrater?"Crater":"Depot"));
        System.out.println("The Gold Position: "+position);

        //Sampling
        //SamwiseAutoDriveWithTensorflow90 driveRoute = samplingRoute(position, isCrater);

        /**
         * driveToCrater the specific route
         */
        //driveRoute.driveToCrater();

        telemetryNow("Autonomous", "Completed");
    }

    /**
     * select sampling driveToCrater route
     * @param position
     * @param isCrater
     * @return
     */
    /**
    protected SamwiseAutoDriveWithTensorflow90 samplingRoute(GoldPosition position, boolean isCrater) {

        SamwiseAutoDriveWithTensorflow90 driveRoute;

        if (isCrater)
        {
            switch (position)
            {
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
        }
        else {
            switch (position)
            {
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
    **/

    /**
     * init with and without tensorflow
     * @param tf
     */
    protected void init(boolean tf) {
        /*
         * Initialize the driveToCrater system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);
        md.init(hardwareMap);

        if(!tf){
            return;
        }

        //initialize Vuforia
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
    }


    protected void turnDrive( TurnDirection direction, double degrees, double timeout)
    {
        double inches = INCHES_PER_DEGREE * degrees;
        switch (direction)
        {
            case RIGHT:
                encoderDrive(TURN_SPEED,-inches, inches, timeout);
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
    public void encoderDrive(double speed, double leftInches, double rightInches, double timeoutS)
    {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (isOpModeActive())
        {
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
            while (isOpModeActive() && (runtime.seconds() < timeoutS) && (robot.leftDrive.isBusy() && robot.rightDrive.isBusy()))
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
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters    = new TFObjectDetector.Parameters(tfodMonitorViewId);
        //TODO: Adjust confidence value
        tfodParameters.minimumConfidence = 0.8;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }

    /**
     * Common driveToCrater for all routes:
     * 1. Landing
     * 2. Orientation identification
     * 3. Gold mineral identification
     *
     * Routes (Sub classes) need to override this method for their own specific drives
     */
    protected void drive(){

        // landing

        // tensorflow for Orientation identification and Gold mineral identification

    }

    /**
     * util to print on screen now
     * @param name
     * @param obj
     */
    protected void telemetryNow (String name, Object obj) {
        telemetry.addData(name, obj);
        telemetry.update();
    }
}
