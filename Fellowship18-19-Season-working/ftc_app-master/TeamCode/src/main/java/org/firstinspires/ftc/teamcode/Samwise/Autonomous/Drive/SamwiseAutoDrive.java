package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.Samwise.Autonomous.MarkerDeposit.SamwiseMarkerDeposit;
import org.firstinspires.ftc.teamcode.Samwise.Autonomous.Vision.SamwiseVision;
import org.firstinspires.ftc.teamcode.Samwise.Conceptual.SamwiseRobot;
import org.firstinspires.ftc.teamcode.Samwise.DriveTrain.SamwiseDriveTrain;
import org.firstinspires.ftc.teamcode.Samwise.Hanger.SamwiseHanger;

@Autonomous(name = "SamwiseAutoDrive v1", group = "Samwise")
@Disabled
public class SamwiseAutoDrive extends LinearOpMode {

    // this is set before the robot is initialized
    protected boolean isCrater = false;

    /* Declare OpMode members. */
    SamwiseRobot sr = new SamwiseRobot(hardwareMap, telemetry);
    SamwiseDriveTrain robot = new SamwiseDriveTrain();   // Use a drivetrain's hardware
    SamwiseVision vis = new SamwiseVision();
    SamwiseMarkerDeposit md = new SamwiseMarkerDeposit();
    SamwiseHanger hanger = sr.hanger();

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
        hanger.init();

        if (tf) {
            vis.init(hardwareMap);
        }
    }


    @Override
    public void runOpMode() {
        this.init(true);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        //landing
        hanger.encoderDrive(this, 0.6, -20.9, 4);

        //Unhooking
        hanger.unHook();
        sleep(500); //wait until the hook fully opens

        //Lowering the Hanger
        hanger.encoderDrive(this, 0.6, 20.9, 4);

        SamwiseVision.GoldPosition position = SamwiseVision.GoldPosition.UNKNOWN;
        //Activate object detector to get gold position, then shut it down
        if (opModeIsActive()) {
            vis.activate();

            this.adjustPosition();

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
         * drive the specific route
         */
        driveRoute.drive();

        // telemetryNow("Autonomous", "Completed");
    }

    private void adjustPosition() {

        //ideal right most mineral middle point position
        final double idealPos = 689;

        Recognition reference = vis.getReference();

        if(reference == null) {
            return;
        }

        double curPos = (reference.getBottom() + reference.getTop()) / 2;
        double ratio = idealPos / curPos;

        System.out.println("==>Current Ratio (before correction): " + ratio);

        while (ratio > 1.03 || ratio < .97) {
            if (ratio > 1) {
                // turn left
                System.out.println("==>Current Ratio: " + ratio + ". Turn Left 2 Degrees: ");
                this.robot.turnDrive(this, 2, 2);
            } else {
                // turn right
                System.out.println("==>Current Ratio: " + ratio + ". Turn Right 2 Degrees.");
                this.robot.turnDrive(this, -2, 2);
            }
            reference = vis.getReference();

            curPos = (reference.getBottom() + reference.getTop()) / 2;
            ratio = idealPos / curPos;
        }

        System.out.println("==>Current Ratio (after correction): " + ratio);
    }

    /**
     * select sampling drive route
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
                    driveRoute = SamwiseDriveRouteFactory.createCraterRight(this);
                    break;
                case LEFT: //left
                    driveRoute = SamwiseDriveRouteFactory.createCraterLeft(this);
                    break;
                case CENTER:  //center
                default:
                    driveRoute = SamwiseDriveRouteFactory.createCraterCenter(this);
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

