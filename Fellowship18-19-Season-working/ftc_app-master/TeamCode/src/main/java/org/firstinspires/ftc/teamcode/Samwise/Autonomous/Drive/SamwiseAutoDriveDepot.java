package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Samwise.Autonomous.Vision.SamwiseVision;


@Autonomous(name = "SamwiseAutoDriveDepot v1", group = "Samwise")
//@Disabled
public class SamwiseAutoDriveDepot extends SamwiseAutoDrive {

    @Override
    protected void init(boolean tf) {
        this.idealPos = 699;
        super.init(tf);
    }


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
                driveRoute = SamwiseDriveRouteFactory.createDepotRight(this);
                break;
            case LEFT: //left
                driveRoute = SamwiseDriveRouteFactory.createDepotLeft(this);
                break;
            case CENTER:  //center
            default:
                driveRoute = SamwiseDriveRouteFactory.createDepotCenter(this);
        }

        return driveRoute;
    }

}
