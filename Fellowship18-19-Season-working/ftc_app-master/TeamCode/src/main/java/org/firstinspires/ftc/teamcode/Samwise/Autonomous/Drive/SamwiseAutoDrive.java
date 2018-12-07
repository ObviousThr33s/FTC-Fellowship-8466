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

