package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Samwise.Autonomous.Vision.SamwiseVision;


@Autonomous(name = "SamwiseAutoDriveDepot v1", group = "Samwise")
//@Disabled
public class SamwiseAutoDriveDepot extends SamwiseAutoDrive {

    /**
     *
     * @param position
     * @return
     */
    @Override
    protected SamwiseAutoDrive samplingRoute(SamwiseVision.GoldPosition position) {

        SamwiseAutoDrive driveRoute = null;

        /**
         * todo: externalize the routes
         */
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

        return driveRoute;
    }

}
