package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Samwise.Autonomous.MarkerDeposit.SamwiseMarkerDeposit;
import org.firstinspires.ftc.teamcode.Samwise.DriveTrain.SamwiseDriveTrain;

public class SamwiseDriveRouteFactory
{
    public static ISamwiseDriveRoute createCraterCenter(final SamwiseAutoDrive samwiseDrive)
    {
        return new ISamwiseDriveRoute() {
            String               route      = "Crater Center";
            SamwiseDriveTrain    driveTrain = samwiseDrive.robot;
            SamwiseMarkerDeposit md         = samwiseDrive.md;
            Telemetry            telemetry  = samwiseDrive.telemetry;

            @Override
            public void drive()
            {
                //common drive defined by the parent
                //telemetryNow(route, "starting parent common drive ...");
                System.out.println("==>Driving route " + route);

                driveTrain.encoderDrive(samwiseDrive, 22, 22, 5);
                //telemetry.addData(route, "finish second drive");
                //telemetry.update();

                driveTrain.encoderDrive(samwiseDrive, -8, -7, 4);
                //telemetry.addData(route, "finish third drive");
                //telemetry.update();

                driveTrain.turnDrive(samwiseDrive, -90, 5);
                telemetry.addData(route, "finish fourth turn");
                //telemetry.update();

                driveTrain.encoderDrive(samwiseDrive, -45, -45, 20);
                telemetry.addData(route, "finish fifth drive");
                //telemetry.update();

                driveTrain.turnDrive(samwiseDrive, 57, 3);
                telemetry.addData(route, "finish sixth turn");
                //telemetry.update();

                driveTrain.encoderDrive(samwiseDrive, -49, -49, 20);
                telemetry.addData(route, "finish seventh drive");
                //telemetry.update();

                md.move(1);

                driveTrain.encoderDrive(samwiseDrive, 72, 72, 20);
                telemetry.addData(route, "finish eighth drive");
                telemetry.update();
            }
        };
    }

    public static ISamwiseDriveRoute createCraterLeft(final SamwiseAutoDrive samwiseDrive)
    {
        return new ISamwiseDriveRoute() {
            String               route      = "Crater Left";
            SamwiseDriveTrain    driveTrain = samwiseDrive.robot;
            SamwiseMarkerDeposit md         = samwiseDrive.md;
            Telemetry            telemetry  = samwiseDrive.telemetry;

            @Override
            public void drive()
            {
                //common drive defined by the parent
                //telemetryNow(route, "starting parent common drive ...");
                System.out.println("==>Driving route "+route);

                driveTrain.turnDrive(samwiseDrive, 32.429725, 2);
                telemetry.addData(route, "finish first turn");
                //telemetry.update();

                driveTrain.encoderDrive(samwiseDrive, 28, 28, 3);
                telemetry.addData(route, "finish second drive");
                //telemetry.update();

                driveTrain.turnDrive(samwiseDrive, -120, 3);
                telemetry.addData(route, "finish third turn");
                //telemetry.update();

                driveTrain.encoderDrive(samwiseDrive, -31,-31,3);
                telemetry.addData(route, "finish fourth drive");
                //telemetry.update();

                driveTrain.turnDrive(samwiseDrive, 35, 3);
                telemetry.addData(route, "finish fifth turn");
                //telemetry.update();

                driveTrain.encoderDrive(samwiseDrive, -45,-45,5);
                telemetry.addData(route, "finish sixth drive");
                //telemetry.update();

                md.move(1);

                driveTrain.encoderDrive(samwiseDrive, 64, 64,15);
                telemetry.addData(route, "finish seventh drive");
                telemetry.update();
            }
        };
    }

    public static ISamwiseDriveRoute createCraterRight(final SamwiseAutoDrive samwiseDrive)
    {
        return new ISamwiseDriveRoute() {
            String               route      = "Crater Right";
            SamwiseDriveTrain    driveTrain = samwiseDrive.robot;
            SamwiseMarkerDeposit md         = samwiseDrive.md;
            Telemetry            telemetry  = samwiseDrive.telemetry;

            @Override
            public void drive()
            {
                //common drive defined by the parent
                //telemetryNow(route, "starting parent common drive ...");
                System.out.println("==>Driving route "+route);

                driveTrain.turnDrive( samwiseDrive,-30.429725, 2);
                telemetry.addData(route, "finish first turn");
                //telemetry.update();

                driveTrain.encoderDrive(samwiseDrive, 28, 28, 5);
                telemetry.addData(route, "finish second drive");
                //telemetry.update();

                driveTrain.encoderDrive(samwiseDrive, -8, -8, 4);
                telemetry.addData(route, "finish third drive");
                //telemetry.update();

                driveTrain.turnDrive(samwiseDrive, -65, 5);
                telemetry.addData(route, "finish fourth turn");
                //telemetry.update();

                driveTrain.encoderDrive(samwiseDrive, -58,-58,20);
                telemetry.addData(route, "finish fifth drive");
                //telemetry.update();

                driveTrain.turnDrive(samwiseDrive, 48, 3);
                telemetry.addData(route, "finish sixth turn");
                //telemetry.update();

                driveTrain.encoderDrive(samwiseDrive, -48,-48, 20);
                telemetry.addData(route, "finish seventh drive");
                //telemetry.update();

                md.move(1);

                driveTrain.encoderDrive(samwiseDrive, 69, 69,20);
                telemetry.addData(route, "finish eighth drive");
                telemetry.update();
            }
        };
    }

    public static ISamwiseDriveRoute createDepotCenter(final SamwiseAutoDrive samwiseDrive)
    {
        return new ISamwiseDriveRoute() {
            String               route      = "Depot Center";
            SamwiseDriveTrain    driveTrain = samwiseDrive.robot;
            SamwiseMarkerDeposit md         = samwiseDrive.md;
            Telemetry            telemetry  = samwiseDrive.telemetry;

            @Override
            public void drive()
            {
                //common drive defined by the parent
                //telemetryNow(route, "starting parent common drive ...");
                System.out.println("==>Driving route "+route);

                driveTrain.encoderDrive(samwiseDrive, 60,60,5);
                driveTrain.turnDrive(samwiseDrive,-125,3);

                md.move(1);

                driveTrain.encoderDrive(samwiseDrive, 70,70,5);
            }
        };
    }

    public static ISamwiseDriveRoute createDepotLeft(final SamwiseAutoDrive samwiseDrive)
    {
        return new ISamwiseDriveRoute() {
            String               route      = "Depot Left";
            SamwiseDriveTrain    driveTrain = samwiseDrive.robot;
            SamwiseMarkerDeposit md         = samwiseDrive.md;
            Telemetry            telemetry  = samwiseDrive.telemetry;

            @Override
            public void drive()
            {
                //common drive defined by the parent
                //telemetryNow(route, "starting parent common drive ...");
                System.out.println("==>Driving route "+route);

                driveTrain.turnDrive(samwiseDrive, 28, 3);

                driveTrain.encoderDrive(samwiseDrive,30, 30, 4);

                driveTrain.turnDrive(samwiseDrive,-50,3);

                driveTrain.encoderDrive(samwiseDrive, 38,38,4);

                driveTrain.turnDrive(samwiseDrive, -107, 3);

                md.move(1);

                driveTrain.encoderDrive(samwiseDrive,73,73, 5);
            }
        };
    }

    public static ISamwiseDriveRoute createDepotRight(final SamwiseAutoDrive samwiseDrive)
    {
        return new ISamwiseDriveRoute() {
            String               route      = "Depot Right";
            SamwiseDriveTrain    driveTrain = samwiseDrive.robot;
            SamwiseMarkerDeposit md         = samwiseDrive.md;
            Telemetry            telemetry  = samwiseDrive.telemetry;

            @Override
            public void drive()
            {
                //common drive defined by the parent
                //telemetryNow(route, "starting parent common drive ...");
                System.out.println("==>Driving route "+route);

                driveTrain.turnDrive(samwiseDrive, -29, 3);

                driveTrain.encoderDrive(samwiseDrive, 30,30, 4);

                driveTrain.turnDrive(samwiseDrive, 42.6684716, 3);

                driveTrain.encoderDrive(samwiseDrive, 30, 30, 4);

                driveTrain.turnDrive(samwiseDrive, -147.75, 3);

                md.move(1);

                driveTrain.encoderDrive(samwiseDrive, 66, 66,5);
            }
        };
    }
}
