package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

public abstract class SamwiseDriveRouteTest extends SamwiseAutoDrive
{
    @Override
    public void runOpMode()
    {
        /**
         * call parent to start:
         * 1. init
         * 2. waitforStart
         * 3. start drive
         */
        init(false);

        waitForStart();

        this.drive();

        //super.runOpMode();
    }

    protected abstract  void drive();
}
