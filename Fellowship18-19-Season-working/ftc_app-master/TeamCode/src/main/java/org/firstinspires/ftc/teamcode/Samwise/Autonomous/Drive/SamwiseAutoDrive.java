package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.Samwise.Autonomous.MarkerDeposit.SamwiseMarkerDeposit;
import org.firstinspires.ftc.teamcode.Samwise.Autonomous.Vision.SamwiseVision;
import org.firstinspires.ftc.teamcode.Samwise.DriveTrain.SamwiseColor;
import org.firstinspires.ftc.teamcode.Samwise.DriveTrain.SamwiseDriveTrain;
import org.firstinspires.ftc.teamcode.Samwise.DriveTrain.SamwiseDriveTrainIMU;
import org.firstinspires.ftc.teamcode.Samwise.Hanger.SamwiseHanger;

@Autonomous(name = "SamwiseAutoDrive v1", group = "Samwise")
@Disabled
public class SamwiseAutoDrive extends LinearOpMode {

    // this is set before the robot is initialized
    protected boolean isCrater = false;

    //ideal right most mineral middle point position
    protected double idealPos = 689;

    /* Declare OpMode members. */
    //SamwiseRobot sr = new SamwiseRobot(hardwareMap, telemetry);
    SamwiseDriveTrainIMU robot = new SamwiseDriveTrainIMU();   // Use a drivetrain's hardware
    SamwiseVision vis = new SamwiseVision();
    SamwiseMarkerDeposit md = new SamwiseMarkerDeposit();
    SamwiseHanger hanger = new SamwiseHanger();//sr.hanger();
    SamwiseColor color = new SamwiseColor();
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

        color.init(hardwareMap, telemetry);

        if (tf) {
            vis.init(hardwareMap);
        }

    }


    @Override
    public void runOpMode() {
        this.init(true);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

            //Unhinging Robot
        //hanger.encoderDrive(this,1,0.9,1);
        hanger.hangermotor1.setPower(1);
        sleep(5);

        //landing the Robot
        hanger.encoderDrive(this, 0.6, -24.5, 4);

        //Unhooking
        hanger.unHook();
        sleep(1000); //wait until the hook fully opens

        //Lowering the Slide
        hanger.encoderDrive(this, 0.6, 21, 4);

        SamwiseVision.GoldPosition position = SamwiseVision.GoldPosition.UNKNOWN;

        //Activate object detector to get gold position, then shut it down
        if (opModeIsActive()) {
            vis.activate();

            //this.adjustPosition();

            position = vis.getGoldPosition();  //Find gold mineral position

            System.out.println("==>The Gold Position: " + position);

            if (position == SamwiseVision.GoldPosition.UNKNOWN) {
                // it is more likely to be UNKNOWN when two silver minerals in the view.
                position = SamwiseVision.GoldPosition.LEFT;
            }
            // System.out.println("==>Is this crater: " + vis.isCrater());
            vis.deactivate();
            vis.shutdown();
        }

        //System.out.println("This is the " + (isCrater ? "Crater" : "Depot"));

        //Sampling
        ISamwiseDriveRoute driveRoute = samplingRoute(position);

        /**
         * driveToCrater the specific route
         */
        driveRoute.drive();

        this.md.move(SamwiseMarkerDeposit.initPosition); //back to init position
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
        while (ratio > 1.05 || ratio < .97);

        System.out.println("==>Current Ratio (after correction): " + ratio);
    }

    /**
     * select sampling driveToCrater route
     *
     * @param position
     * @return
     */
    protected ISamwiseDriveRoute samplingRoute(SamwiseVision.GoldPosition position) {

        ISamwiseDriveRoute driveRoute = null;

        /**
         * todo: externalize the routes
         */
        if (isCrater) {
            switch (position) {
                case RIGHT: //right
                    driveRoute = SamwiseDriveRouteFactory.createCraterRight2(this);
                    break;
                case LEFT: //left
                    driveRoute = SamwiseDriveRouteFactory.createCraterLeft2(this);
                    break;
                case CENTER:  //center
                default:
                    driveRoute = SamwiseDriveRouteFactory.createCraterCenter2(this);
            }
        } else {
            switch (position) {
                case RIGHT: //right
                    driveRoute = SamwiseDriveRouteFactory.createDepotRight(this);
                    break;
                case LEFT: //left
                    driveRoute = SamwiseDriveRouteFactory.createDepotLeft(this);
                    break;
                case CENTER:  //center
                default:
                    driveRoute = SamwiseDriveRouteFactory.createDepotCenter(this);
            }
        }
        return driveRoute;
    }

}

