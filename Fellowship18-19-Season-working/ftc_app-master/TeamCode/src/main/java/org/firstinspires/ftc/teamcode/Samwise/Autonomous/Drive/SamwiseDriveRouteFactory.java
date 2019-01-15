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
                //common driveToCrater defined by the parent
                //telemetryNow(route, "starting parent common driveToCrater ...");
                System.out.println("==>Driving route " + route);

                driveTrain.encoderDrive(samwiseDrive, 22, 5);
                //telemetry.addData(route, "finish second driveToCrater");
                //telemetry.update();

                driveTrain.encoderDrive(samwiseDrive, -8, 4);
                //telemetry.addData(route, "finish third driveToCrater");
                //telemetry.update();

                driveTrain.turnDrive(samwiseDrive, -90, 5);
                //telemetry.addData(route, "finish fourth turn");
                //telemetry.update();

                driveTrain.encoderDrive(samwiseDrive, -44,  20);
                //telemetry.addData(route, "finish fifth driveToCrater");
                //telemetry.update();

                driveTrain.turnDrive(samwiseDrive, 45, 3);
                //telemetry.addData(route, "finish sixth turn");
                //telemetry.update();

                driveTrain.driveToDepotFromSide(samwiseDrive, samwiseDrive.touchBackSide, false, samwiseDrive.color, 6);

                //driveTrain.encoderDrive(samwiseDrive, -55, -55, 20);
                //telemetry.addData(route, "finish seventh driveToCrater");
                //telemetry.update();

                md.shake(SamwiseMarkerDeposit.dropPosition);

                driveTrain.turnDrive(samwiseDrive, 15, 3);

                // make this wall driveToCrater using touch sensor
                //driveTrain.encoderDrive(samwiseDrive, 66, 66,7);
                driveTrain.driveToCrater(samwiseDrive, samwiseDrive.touchFrontSide, samwiseDrive.touchFront, 7);

                //driveTrain.encoderDrive(samwiseDrive, 72, 72, 20);
                //telemetry.addData(route, "finish eighth driveToCrater");
                //telemetry.update();
            }
        };
    }

    public static ISamwiseDriveRoute createCraterCenter2(final SamwiseAutoDrive samwiseDrive)
    {
        return new ISamwiseDriveRoute() {
            String               route      = "Crater Center";
            SamwiseDriveTrain    driveTrain = samwiseDrive.robot;
            SamwiseMarkerDeposit md         = samwiseDrive.md;
            Telemetry            telemetry  = samwiseDrive.telemetry;

            @Override
            public void drive()
            {
                //common driveToCrater defined by the parent
                //telemetryNow(route, "starting parent common driveToCrater ...");
                System.out.println("==>Driving route " + route);

                driveTrain.encoderDrive(samwiseDrive, 28, 5);
                //telemetry.addData(route, "finish second driveToCrater");
                //telemetry.update();

                /*driveTrain.encoderDrive(samwiseDrive, -8, -7, 4);
                //telemetry.addData(route, "finish third driveToCrater");
                //telemetry.update();

                driveTrain.turnDrive(samwiseDrive, -90, 5);
                telemetry.addData(route, "finish fourth turn");
                //telemetry.update();

                driveTrain.encoderDrive(samwiseDrive, -40, -40, 20);
                telemetry.addData(route, "finish fifth driveToCrater");
                //telemetry.update();

                driveTrain.turnDrive(samwiseDrive, 57, 3);
                telemetry.addData(route, "finish sixth turn");
                //telemetry.update();

                driveTrain.encoderDrive(samwiseDrive, -55, -55, 20);
                telemetry.addData(route, "finish seventh driveToCrater");
                //telemetry.update();

                md.shake(SamwiseMarkerDeposit.dropPosition);

                driveTrain.encoderDrive(samwiseDrive, 72, 72, 20);
                telemetry.addData(route, "finish eighth driveToCrater");
                telemetry.update();*/
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
                //common driveToCrater defined by the parent
                //telemetryNow(route, "starting parent common driveToCrater ...");
                System.out.println("==>Driving route "+route);

                driveTrain.encoderDrive(samwiseDrive, 2.5, 5);

                driveTrain.turnDrive(samwiseDrive, 35, 2);
                //telemetry.addData(route, "finish first turn");
                //telemetry.update();

                driveTrain.encoderDrive(samwiseDrive, 27,3);
                //telemetry.addData(route, "finish second driveToCrater");
                //telemetry.update();

                driveTrain.turnDrive(samwiseDrive, -110, 3);
                //telemetry.addData(route, "finish third turn");
                //telemetry.update();

                driveTrain.encoderDrive(samwiseDrive, -26,3);
                //telemetry.addData(route, "finish fourth driveToCrater");
                //telemetry.update();

                driveTrain.turnDrive(samwiseDrive, 30, 3);
                //telemetry.addData(route, "finish fifth turn");
                //telemetry.update();

                driveTrain.driveToDepotFromSide(samwiseDrive, samwiseDrive.touchBackSide, false, samwiseDrive.color, 6);

                md.shake(SamwiseMarkerDeposit.dropPosition);

                driveTrain.turnDrive(samwiseDrive, 12, 3);

                // make this wall driveToCrater using touch sensor
                //driveTrain.encoderDrive(samwiseDrive, 66, 66,7);
                driveTrain.driveToCrater(samwiseDrive, samwiseDrive.touchFrontSide, samwiseDrive.touchFront, 7);

            }
        };
    }

    public static ISamwiseDriveRoute createCraterLeft2(final SamwiseAutoDrive samwiseDrive)
    {
        return new ISamwiseDriveRoute() {
            String               route      = "Crater Left";
            SamwiseDriveTrain    driveTrain = samwiseDrive.robot;
            SamwiseMarkerDeposit md         = samwiseDrive.md;
            Telemetry            telemetry  = samwiseDrive.telemetry;

            @Override
            public void drive()
            {
                //common driveToCrater defined by the parent
                //telemetryNow(route, "starting parent common driveToCrater ...");
                System.out.println("==>Driving route "+route);

                driveTrain.encoderDrive(samwiseDrive, 2.5, 5);

                driveTrain.turnDrive(samwiseDrive, 27, 2);
                telemetry.addData(route, "finish first turn");
                //telemetry.update();

                driveTrain.encoderDrive(samwiseDrive, 27,  3);
                telemetry.addData(route, "finish second driveToCrater");
                //telemetry.update();

                driveTrain.turnDrive(samwiseDrive, -46, 3);
                telemetry.addData(route, "finish third turn");
                //telemetry.update();

                driveTrain.encoderDrive(samwiseDrive, 5.5,3);
                telemetry.addData(route, "finish fourth driveToCrater");
                //telemetry.update();

                /*driveTrain.turnDrive(samwiseDrive, 36, 3);
                telemetry.addData(route, "finish fifth turn");
                //telemetry.update();

                driveTrain.encoderDrive(samwiseDrive, -46,-46,5);
                telemetry.addData(route, "finish sixth driveToCrater");
                //telemetry.update();

                md.shake(SamwiseMarkerDeposit.dropPosition);

                driveTrain.encoderDrive(samwiseDrive, 65, 65,15);
                telemetry.addData(route, "finish seventh driveToCrater");
                telemetry.update();*/
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
                //common driveToCrater defined by the parent
                //telemetryNow(route, "starting parent common driveToCrater ...");
                System.out.println("==>Driving route "+route);

                driveTrain.encoderDrive(samwiseDrive, 2.5, 5);
                //telemetry.addData(route, "finish ")

                driveTrain.turnDrive( samwiseDrive,-35, 2);
                //telemetry.addData(route, "finish first turn");
                //telemetry.update();

                driveTrain.encoderDrive(samwiseDrive, 25.5, 5);
                //telemetry.addData(route, "finish second driveToCrater");
                //telemetry.update();

                driveTrain.encoderDrive(samwiseDrive, -9, 4);
                //telemetry.addData(route, "finish third driveToCrater");
                //telemetry.update();

                driveTrain.turnDrive(samwiseDrive, -52, 5);
                //telemetry.addData(route, "finish fourth turn");
                //telemetry.update();

                driveTrain.encoderDrive(samwiseDrive, -51,20);
                //telemetry.addData(route, "finish fifth driveToCrater");
                //telemetry.update();

                driveTrain.turnDrive(samwiseDrive, 40, 3);
                //telemetry.addData(route, "finish sixth turn");
                //telemetry.update();

                driveTrain.driveToDepotFromSide(samwiseDrive, samwiseDrive.touchBackSide, false, samwiseDrive.color, 6);

                md.shake(SamwiseMarkerDeposit.dropPosition);

                driveTrain.turnDrive(samwiseDrive, 15, 3);

                // make this wall driveToCrater using touch sensor
                //driveTrain.encoderDrive(samwiseDrive, 66, 66,7);
                driveTrain.driveToCrater(samwiseDrive, samwiseDrive.touchFrontSide, samwiseDrive.touchFront, 7);

            }
        };
    }

    public static ISamwiseDriveRoute createCraterRight2(final SamwiseAutoDrive samwiseDrive)
    {
        return new ISamwiseDriveRoute() {
            String               route      = "Crater Right";
            SamwiseDriveTrain    driveTrain = samwiseDrive.robot;
            SamwiseMarkerDeposit md         = samwiseDrive.md;
            Telemetry            telemetry  = samwiseDrive.telemetry;

            @Override
            public void drive()
            {
                //common driveToCrater defined by the parent
                //telemetryNow(route, "starting parent common driveToCrater ...");
                System.out.println("==>Driving route "+route);

                driveTrain.encoderDrive(samwiseDrive, 2.5, 5);
                //telemetry.addData(route, "finish ")

                driveTrain.turnDrive( samwiseDrive,-27, 2);
                //telemetry.addData(route, "finish first turn");
                //telemetry.update();

                driveTrain.encoderDrive(samwiseDrive, 27, 5);
                //telemetry.addData(route, "finish second driveToCrater");
                //telemetry.update();

                driveTrain.turnDrive(samwiseDrive, 46, 5);

                driveTrain.encoderDrive(samwiseDrive, 5.5, 4);

                /*driveTrain.encoderDrive(samwiseDrive, -9, -9, 4);
                telemetry.addData(route, "finish third driveToCrater");
                //telemetry.update();

                driveTrain.turnDrive(samwiseDrive, -56, 5);
                telemetry.addData(route, "finish fourth turn");
                //telemetry.update();

                driveTrain.encoderDrive(samwiseDrive, -50,-50,20);
                telemetry.addData(route, "finish fifth driveToCrater");
                //telemetry.update();

                driveTrain.turnDrive(samwiseDrive, 46, 3);
                telemetry.addData(route, "finish sixth turn");
                //telemetry.update();

                driveTrain.encoderDrive(samwiseDrive, -48,-48, 20);
                telemetry.addData(route, "finish seventh driveToCrater");
                //telemetry.update();

                md.shake(SamwiseMarkerDeposit.dropPosition);

                driveTrain.encoderDrive(samwiseDrive, 69, 69,20);
                telemetry.addData(route, "finish eighth driveToCrater");
                telemetry.update();*/
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
                //common driveToCrater defined by the parent
                //telemetryNow(route, "starting parent common driveToCrater ...");
                System.out.println("==>Driving route "+route);

                driveTrain.encoderDrive(samwiseDrive, 30,5);

                driveTrain.driveToDepotFromCenter(samwiseDrive, .7, samwiseDrive.color, 4);

                driveTrain.turnDrive(samwiseDrive,-124,3);

                md.shake(SamwiseMarkerDeposit.dropPosition);

                //driveTrain.encoderDrive(samwiseDrive, 80,5);
                driveTrain.driveToCrater(samwiseDrive, samwiseDrive.touchFrontSide, samwiseDrive.touchFront, 6);
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
                //common driveToCrater defined by the parent
                //telemetryNow(route, "starting parent common driveToCrater ...");
                System.out.println("==>Driving route "+route);

                driveTrain.encoderDrive(samwiseDrive,2.5, 5);

                driveTrain.turnDrive(samwiseDrive, 33, 3);

                driveTrain.encoderDrive(samwiseDrive,29, 29, 4);

                driveTrain.turnDrive(samwiseDrive,-67,3);

                //driveTrain.encoderDrive(samwiseDrive, 38,38,4);
                driveTrain.driveToDepotFromSide(samwiseDrive, samwiseDrive.touchFrontSide, true, samwiseDrive.color, 6);

                driveTrain.encoderDrive(samwiseDrive,-2.5, 5);

                driveTrain.turnDrive(samwiseDrive, -92, 3);

                md.shake(SamwiseMarkerDeposit.dropPosition);

                //driveTrain.encoderDrive(samwiseDrive,73,73, 5);
                driveTrain.driveToCrater(samwiseDrive, samwiseDrive.touchFrontSide, samwiseDrive.touchFront, 6);
            }
        };
    }


    public static ISamwiseDriveRoute createDepotRight(final SamwiseAutoDrive samwiseDrive)
    {
        return new ISamwiseDriveRoute() {
            String               route      = "Depot Right";
            SamwiseDriveTrain    driveTrain = samwiseDrive.robot;
            SamwiseMarkerDeposit md         = samwiseDrive.md;
            //DigitalChannel dc = samwiseDrive.digitalTouch;
            Telemetry            telemetry  = samwiseDrive.telemetry;

            @Override
            public void drive()
            {
                //common driveToCrater defined by the parent
                //telemetryNow(route, "starting parent common driveToCrater ...");
                System.out.println("==>Driving route "+route);

                driveTrain.encoderDrive(samwiseDrive, 2.5, 4);

                driveTrain.turnDrive(samwiseDrive, -35, 3);

                driveTrain.encoderDrive(samwiseDrive,41, 4);

                driveTrain.turnDrive(samwiseDrive, -101, 3);
                //driveTrain.turn(samwiseDrive, samwiseDrive.touchBackSide, true, 5);

                driveTrain.driveToDepotFromSide(samwiseDrive, samwiseDrive.touchBackSide, false, samwiseDrive.color, 6);

                //driveTrain.encoderDrive(samwiseDrive, -16, 4);

                //driveTrain.turnDrive(samwiseDrive, 15, 3);

                md.shake(SamwiseMarkerDeposit.dropPosition);

                driveTrain.turnDrive(samwiseDrive, 12, 3);

                // make this wall driveToCrater using touch sensor
                //driveTrain.encoderDrive(samwiseDrive, 66, 66,7);
                driveTrain.driveToCrater(samwiseDrive, samwiseDrive.touchFrontSide, samwiseDrive.touchFront, 7);
            }
        };
    }


    /**
     * A general route for all depot positions
     * @param samwiseDrive
     * @return
     */
    public static ISamwiseDriveRoute createDepot(final SamwiseAutoDrive samwiseDrive)
    {
        return new ISamwiseDriveRoute() {
            String               route      = "Depot Left";
            SamwiseDriveTrain    driveTrain = samwiseDrive.robot;
            SamwiseMarkerDeposit md         = samwiseDrive.md;
            Telemetry            telemetry  = samwiseDrive.telemetry;

            @Override
            public void drive()
            {
                //common driveToCrater defined by the parent
                //telemetryNow(route, "starting parent common driveToCrater ...");
                System.out.println("==>Driving route "+route);

                driveTrain.encoderDrive(samwiseDrive, 3, 4);

                driveTrain.turnDrive(samwiseDrive, -35, 3);

                driveTrain.encoderDrive(samwiseDrive,43, 4);

                driveTrain.turnDrive(samwiseDrive, -100, 3);

                driveTrain.encoderDrive(samwiseDrive, -27, 4);

                driveTrain.turnDrive(samwiseDrive, 15, 3);

                md.shake(SamwiseMarkerDeposit.dropPosition);

                // make this wall driveToCrater using touch sensor
                //driveTrain.encoderDrive(samwiseDrive, 66, 66,7);
                driveTrain.driveToCrater(samwiseDrive, samwiseDrive.touchFrontSide, samwiseDrive.touchFront, 6);
            }
        };
    }
}
