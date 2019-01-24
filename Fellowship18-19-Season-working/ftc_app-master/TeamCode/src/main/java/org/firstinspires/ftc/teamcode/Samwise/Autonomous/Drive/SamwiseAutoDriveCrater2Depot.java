package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Samwise.Autonomous.Vision.SamwiseVision;


@Autonomous(name = "AutoDrive Crater -> Depot -> our crater", group = "Samwise")
//@Disabled
public class SamwiseAutoDriveCrater2Depot extends SamwiseAutoDrive {

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
                driveRoute = SamwiseDriveRouteFactory.createCraterRight(this);
                break;
            case LEFT: //left
                //sampleAndDeposit.sampleAndDepositCraterLeft();
                driveRoute = SamwiseDriveRouteFactory.createCraterLeft(this);
                break;
            case CENTER:  //center
            default:
                //sampleAndDeposit.sampleAndDepositCraterCenter();
                driveRoute = SamwiseDriveRouteFactory.createCraterCenter(this);
        }

        return driveRoute;
    }

}
