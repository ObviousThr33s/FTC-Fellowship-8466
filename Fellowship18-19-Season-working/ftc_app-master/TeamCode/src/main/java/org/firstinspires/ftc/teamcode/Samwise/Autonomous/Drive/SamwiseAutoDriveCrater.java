package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import android.widget.ImageSwitcher;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Samwise.Autonomous.Vision.SamwiseVision;


@Autonomous(name = "SamwiseAutoDriveCrater", group = "Samwise")
//@Disabled
public class SamwiseAutoDriveCrater extends SamwiseAutoDrive {

    @Override
    protected void init(boolean tf) {
        this.idealPos = 689;
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
                //sampleAndDeposit.sampleAndDepositCraterRight();
                driveRoute = SamwiseDriveRouteFactory.createCraterRight2(this);
                break;
            case LEFT: //left
                //sampleAndDeposit.sampleAndDepositCraterLeft();
                driveRoute = SamwiseDriveRouteFactory.createCraterLeft2(this);
                break;
            case CENTER:  //center
            default:
                //sampleAndDeposit.sampleAndDepositCraterCenter();
                driveRoute = SamwiseDriveRouteFactory.createCraterCenter2(this);
        }

        return driveRoute;
    }

}
