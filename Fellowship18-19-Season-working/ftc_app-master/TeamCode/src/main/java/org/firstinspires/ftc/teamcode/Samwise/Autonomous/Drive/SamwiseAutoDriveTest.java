package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.Samwise.Autonomous.MarkerDeposit.SamwiseMarkerDeposit;
import org.firstinspires.ftc.teamcode.Samwise.Autonomous.Vision.SamwiseVision;
import org.firstinspires.ftc.teamcode.Samwise.DriveTrain.SamwiseDriveTrain;
import org.firstinspires.ftc.teamcode.Samwise.Hanger.SamwiseHanger;

@Autonomous(name = "SamwiseAutoDriveTest v1", group = "Samwise")
//@Disabled
public class SamwiseAutoDriveTest extends LinearOpMode {

    // this is set before the robot is initialized
    protected boolean isCrater = false;

    //ideal right most mineral middle point position
    protected double idealPos = 689;

    /* Declare OpMode members. */
    //SamwiseRobot sr = new SamwiseRobot(hardwareMap, telemetry);
    SamwiseDriveTrain robot = new SamwiseDriveTrain();   // Use a drivetrain's hardware
    SamwiseVision vis = new SamwiseVision();
    SamwiseMarkerDeposit md = new SamwiseMarkerDeposit();
    SamwiseHanger hanger = new SamwiseHanger();//sr.hanger();
   // SampleAndDeposit sampleAndDeposit = null;

    DigitalChannel touchFrontSide;  // side touch sensor
    DigitalChannel touchBackSide;  // side touch sensor
    DigitalChannel touchFront;  // front touch sensor


    /**
     * init with and without tensorflow
     *
     * @param tf
     */
    protected void init(boolean tf) {
        /*
         * Initialize the driveToCrater system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);
        md.init(hardwareMap);
        hanger.init(hardwareMap, telemetry);

        //sampleAndDeposit = new SampleAndDeposit(hardwareMap);

        // get a reference to our digitalTouch object.
        touchFrontSide = hardwareMap.get(DigitalChannel.class, "touch_front_side");
        // set the digital channel to input.
        touchFrontSide.setMode(DigitalChannel.Mode.INPUT);

        touchBackSide = hardwareMap.get(DigitalChannel.class, "touch_back_side");
        // set the digital channel to input.
        touchBackSide.setMode(DigitalChannel.Mode.INPUT);

        touchFront = hardwareMap.get(DigitalChannel.class, "touch_front");
        // set the digital channel to input.
        touchFront.setMode(DigitalChannel.Mode.INPUT);


        if (tf) {
            vis.init(hardwareMap);
        }

    }


    @Override
    public void runOpMode() {
        this.init(false);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        robot.leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //this.robot.turnleft(.8);

        robot.leftDrive.setPower(-.6);
        robot.rightDrive.setPower(.9);

        ElapsedTime runtime = new ElapsedTime();

        System.out.println("==> turning the robot till the side touch sensor is pressed ... ");

        while (opModeIsActive() && (runtime.seconds() < 2) && this.touchBackSide.getState()) {
            // robot keeps driving until side  is pressed
            idle();
        }

        //sleep(2000);

        //System.out.println("This is the " + (isCrater ? "Crater" : "Depot"));

        //Sampling
        //ISamwiseDriveRoute driveRoute = samplingRoute(position);

        /**
         * driveToCrater the specific route
         */
        //driveRoute.driveToCrater();

        // telemetryNow("Autonomous", "Completed");
    }

    private void adjustPosition() {

        double ratio;

         do {
             Recognition reference = vis.getReference();

             if(reference == null) {
                 return;
             }

             double curPos = (reference.getBottom() + reference.getTop()) / 2;
             ratio = idealPos / curPos;

             System.out.println("==>Current Ratio (before correction): " + ratio);

            if (ratio > 1) {
                // turn left
                System.out.println("==>Current Ratio: " + ratio + ". Turn Left 2 Degrees: ");
                this.robot.turnDrive(this, 2, 2);
            } else {
                // turn right
                System.out.println("==>Current Ratio: " + ratio + ". Turn Right 2 Degrees.");
                this.robot.turnDrive(this, -2, 2);
            }
        }
        while (ratio > 1.03 || ratio < .97);

        System.out.println("==>Current Ratio (after correction): " + ratio);
    }


}

