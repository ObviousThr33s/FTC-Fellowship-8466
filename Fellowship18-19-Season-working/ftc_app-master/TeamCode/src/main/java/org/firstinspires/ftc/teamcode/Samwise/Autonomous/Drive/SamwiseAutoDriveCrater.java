package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import android.widget.ImageSwitcher;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Samwise.Autonomous.Vision.SamwiseVision;


@Autonomous(name = "SamwiseAutoDriveCrater v1", group = "Samwise")
//@Disabled
public class SamwiseAutoDriveCrater extends SamwiseAutoDrive {

    /**
     *
     * @param position
     * @return
     */
    @Override
    protected ISamwiseDriveRoute samplingRoute(SamwiseVision.GoldPosition position) {

        ISamwiseDriveRoute driveRoute = null;

        /**
         * todo: externalize the routes
         */
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

        return driveRoute;
    }

}
