package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

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

        //Find gold mineral position
        SamwiseVision.GoldPosition position = SamwiseVision.GoldPosition.UNKNOWN;
        //Activate object detector to get gold position, then shut it down
        if (opModeIsActive()) {
            vis.activate();

            position = vis.getGoldPosition();

            System.out.println("==>The Gold Position: " + position);
            // System.out.println("==>Is this crater: " + vis.isCrater());
            //telemetry.addData("Gold Position:", position);
            //telemetry.update();
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
                    driveRoute =SamwiseDriveRouteFactory.createDepotLeft(this);
                    break;
                case CENTER:  //center
                default:
                    driveRoute = SamwiseDriveRouteFactory.createDepotCenter(this);
            }
        }
        return driveRoute;
    }

}

