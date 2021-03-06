package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.teamcode.Samwise.Autonomous.MarkerDeposit.SamwiseMarkerDeposit;
import org.firstinspires.ftc.teamcode.Samwise.DriveTrain.SamwiseDriveTrain;
import org.firstinspires.ftc.teamcode.Samwise.DriveTrain.SamwiseDriveTrainIMU;

public class SamwiseDriveRouteFactory {
    /**
     * back 2 crater
     *
     * @param samwiseDrive
     * @return
     */
    public static ISamwiseDriveRoute createCraterCenter(final SamwiseAutoDrive samwiseDrive) {
        return new ISamwiseDriveRoute() {
            String route = "Crater Center";
            SamwiseDriveTrainIMU driveTrain = (SamwiseDriveTrainIMU) samwiseDrive.robot;
            SamwiseMarkerDeposit md = samwiseDrive.md;
            //Telemetry            telemetry  = samwiseDrive.telemetry;

            @Override
            public void drive() {
                //common driveToCrater defined by the parent
                //telemetryNow(route, "starting parent common driveToCrater ...");
                System.out.println("==>Driving route " + route);

                driveTrain.encoderDrive(samwiseDrive, 24.5, 5);
                //telemetry.addData(route, "finish second driveToCrater");
                //telemetry.update();

                if (samwiseDrive.runWithArm)
                    samwiseDrive.driveJ2up(500);

                driveTrain.encoderDrive(samwiseDrive, -10.5, 4);
                //telemetry.addData(route, "finish third driveToCrater");
                //telemetry.update();

                driveTrain.turnDrive(samwiseDrive, -86, 5);
                //telemetry.addData(route, "finish fourth turn");
                //telemetry.update();

                // turn J1
                if (samwiseDrive.runWithArm)
                    samwiseDrive.turnJ1Auto(1250);

                driveTrain.encoderDrive(samwiseDrive, -45, 5);
                //driveTrain.driveToWall(samwiseDrive, samwiseDrive.touchBack, false, 7);

                driveTrain.turnDrive(samwiseDrive, 34, 3);
                //telemetry.addData(route, "finish sixth turn");
                //telemetry.update();

                driveTrain.driveToDepotFromSide(samwiseDrive, samwiseDrive.touchBackSide, false, samwiseDrive.color, 4);

                //driveTrain.encoderDrive(samwiseDrive, -55, -55, 20);
                //telemetry.addData(route, "finish seventh driveToCrater");
                //telemetry.update();

                md.shake(SamwiseMarkerDeposit.dropPosition);

                //driveTrain.resetAngle(AxesOrder.ZYX);

                ((SamwiseDriveTrain) driveTrain).turn(samwiseDrive, 10, 2.5);

                // make this wall driveToCrater using touch sensor
                //driveTrain.encoderDrive(samwiseDrive, 66, 66,7);
                //driveTrain.driveToCrater(samwiseDrive, samwiseDrive.touchFrontSide, true, 6);
                ((SamwiseDriveTrain) driveTrain).driveToCrater(samwiseDrive, samwiseDrive.touchFrontSide, samwiseDrive.touchFront, true, 7);

                //driveTrain.encoderDrive(samwiseDrive, 72, 72, 20);
                //telemetry.addData(route, "finish eighth driveToCrater");
                //telemetry.update();

                //((SamwiseDriveTrain)driveTrain).turn(samwiseDrive, 15, 2.5);

                //driveTrain.encoderDrive(samwiseDrive, -2.5, 2);
            }
        };
    }

    public static ISamwiseDriveRoute createCraterCenter2(final SamwiseAutoDrive samwiseDrive) {
        return new ISamwiseDriveRoute() {
            String route = "Crater Center";
            SamwiseDriveTrain driveTrain = samwiseDrive.robot;
            SamwiseMarkerDeposit md = samwiseDrive.md;
            Telemetry telemetry = samwiseDrive.telemetry;

            @Override
            public void drive() {
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

    public static ISamwiseDriveRoute createCraterLeft(final SamwiseAutoDrive samwiseDrive) {
        return new ISamwiseDriveRoute() {
            String route = "Crater Left";
            //SamwiseDriveTrain    driveTrain = samwiseDrive.robot;
            SamwiseDriveTrainIMU driveTrain = (SamwiseDriveTrainIMU) samwiseDrive.robot;
            SamwiseMarkerDeposit md = samwiseDrive.md;
            Telemetry telemetry = samwiseDrive.telemetry;

            @Override
            public void drive() {
                //common driveToCrater defined by the parent
                //telemetryNow(route, "starting parent common driveToCrater ...");
                System.out.println("==>Driving route " + route);

                driveTrain.encoderDriveSpeed(samwiseDrive, 2.5, 4, .5);

                driveTrain.turnDrive(samwiseDrive, 36, 2);
                //telemetry.addData(route, "finish first turn");
                //telemetry.update();

                driveTrain.encoderDrive(samwiseDrive, 25, 3);
                //telemetry.addData(route, "finish second driveToCrater");
                //telemetry.update();

                driveTrain.encoderDrive(samwiseDrive, -4, 2);

                samwiseDrive.driveJ2up(500);

                driveTrain.turnDrive(samwiseDrive, -116, 3);
                //telemetry.addData(route, "finish third turn");
                //telemetry.update();

                // turn J1
                if (samwiseDrive.runWithArm)
                    samwiseDrive.turnJ1Auto(1250);

                driveTrain.encoderDrive(samwiseDrive, -35, 3);
                //driveTrain.driveToWall(samwiseDrive, samwiseDrive.touchBack, false, 3.5);

                driveTrain.turnDrive(samwiseDrive, 21, 3);
                //telemetry.addData(route, "finish fifth turn");
                //telemetry.update();

                driveTrain.driveToDepotFromSide(samwiseDrive, samwiseDrive.touchBackSide, false, samwiseDrive.color, 4);

                md.shake(SamwiseMarkerDeposit.dropPosition);


                //driveTrain.resetAngle(AxesOrder.ZYX);

                //driveTrain.turnDrive(samwiseDrive, 12, 3);
                ((SamwiseDriveTrain) driveTrain).turn(samwiseDrive, 10, 2.5);


                // make this wall driveToCrater using touch sensor
                //driveTrain.encoderDrive(samwiseDrive, 66, 66,7);
                //driveTrain.driveToCrater(samwiseDrive, samwiseDrive.touchFrontSide, true, 6);
                ((SamwiseDriveTrain) driveTrain).driveToCrater(samwiseDrive, samwiseDrive.touchFrontSide, samwiseDrive.touchFront, true, 7);

                //((SamwiseDriveTrain)driveTrain).turn(samwiseDrive, 15, 2.5);

                //driveTrain.encoderDrive(samwiseDrive, -2.5, 2);
            }
        };
    }

    public static ISamwiseDriveRoute createCraterLeft2(final SamwiseAutoDrive samwiseDrive) {
        return new ISamwiseDriveRoute() {
            String route = "Crater Left";
            //SamwiseDriveTrain    driveTrain = samwiseDrive.robot;
            SamwiseDriveTrainIMU driveTrain = (SamwiseDriveTrainIMU) samwiseDrive.robot;
            SamwiseMarkerDeposit md = samwiseDrive.md;
            Telemetry telemetry = samwiseDrive.telemetry;

            @Override
            public void drive() {
                //common driveToCrater defined by the parent
                //telemetryNow(route, "starting parent common driveToCrater ...");
                System.out.println("==>Driving route " + route);

                driveTrain.encoderDrive(samwiseDrive, 2.5, 5);

                driveTrain.turnDrive(samwiseDrive, 27, 2);
                telemetry.addData(route, "finish first turn");
                //telemetry.update();

                driveTrain.encoderDrive(samwiseDrive, 27, 3);
                telemetry.addData(route, "finish second driveToCrater");
                //telemetry.update();

                driveTrain.turnDrive(samwiseDrive, -46, 3);
                telemetry.addData(route, "finish third turn");
                //telemetry.update();

                driveTrain.encoderDrive(samwiseDrive, 5.5, 3);
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

    public static ISamwiseDriveRoute createCraterRight(final SamwiseAutoDrive samwiseDrive) {
        return new ISamwiseDriveRoute() {
            String route = "Crater Right";
            //SamwiseDriveTrain    driveTrain = samwiseDrive.robot;
            SamwiseDriveTrainIMU driveTrain = (SamwiseDriveTrainIMU) samwiseDrive.robot;
            SamwiseMarkerDeposit md = samwiseDrive.md;
            Telemetry telemetry = samwiseDrive.telemetry;

            @Override
            public void drive() {
                //common driveToCrater defined by the parent
                //telemetryNow(route, "starting parent common driveToCrater ...");
                System.out.println("==>Driving route " + route);

                driveTrain.encoderDriveSpeed(samwiseDrive, 2.5, 4, .5);
                //telemetry.addData(route, "finish ")

                driveTrain.turnDrive(samwiseDrive, -35, 2);
                //telemetry.addData(route, "finish first turn");
                //telemetry.update();

                if (samwiseDrive.runWithArm)
                    samwiseDrive.driveJ2up(500);

                driveTrain.encoderDrive(samwiseDrive, 25.5, 4);
                //telemetry.addData(route, "finish second driveToCrater");
                //telemetry.update();

                // turn J1
                if (samwiseDrive.runWithArm)
                    samwiseDrive.turnJ1Auto(1250);

                driveTrain.encoderDrive(samwiseDrive, -9.5, 3);
                //telemetry.addData(route, "finish third driveToCrater");
                //telemetry.update();

                driveTrain.turnDrive(samwiseDrive, -46.5, 5.5);
                //telemetry.addData(route, "finish fourth turn");
                //telemetry.update();

                driveTrain.encoderDrive(samwiseDrive, -54, 6);

                driveTrain.turnDrive(samwiseDrive, 28, 5);

                driveTrain.driveToDepotFromSide(samwiseDrive, samwiseDrive.touchBackSide, false, samwiseDrive.color, 4);


                //driveTrain.driveToDepotFromSide(samwiseDrive, samwiseDrive.touchFrontSide, true, samwiseDrive.color, 6);

                //driveTrain.encoderDrive(samwiseDrive, 75,6);

                //driveTrain.encoderDrive(samwiseDrive, 7, 4);

                md.shake(SamwiseMarkerDeposit.dropPosition);

                //driveTrain.resetAngle(AxesOrder.ZYX);

                //driveTrain.turnDrive(samwiseDrive, 15, 3);
                ((SamwiseDriveTrain) driveTrain).turn(samwiseDrive, 14.5, 2.5);


                // make this wall driveToCrater using touch sensor
                //driveTrain.encoderDrive(samwiseDrive, 66, 66,7);
                ((SamwiseDriveTrain) driveTrain).driveToCrater(samwiseDrive, samwiseDrive.touchFrontSide, samwiseDrive.touchFront, true, 7);

                //((SamwiseDriveTrain)driveTrain).turn(samwiseDrive, 15, 2.5);

                //driveTrain.encoderDrive(samwiseDrive, -2.5, 2);
            }
        };
    }

    public static ISamwiseDriveRoute createCraterRight4Alliance(final SamwiseAutoDrive samwiseDrive) {
        return new ISamwiseDriveRoute() {
            String route = "Crater Right";
            //SamwiseDriveTrain    driveTrain = samwiseDrive.robot;
            SamwiseDriveTrainIMU driveTrain = (SamwiseDriveTrainIMU) samwiseDrive.robot;
            SamwiseMarkerDeposit md = samwiseDrive.md;
            Telemetry telemetry = samwiseDrive.telemetry;

            @Override
            public void drive() {
                //common driveToCrater defined by the parent
                //telemetryNow(route, "starting parent common driveToCrater ...");
                System.out.println("==>Driving route " + route);

                driveTrain.encoderDriveSpeed(samwiseDrive, 2.5, 4, .5);
                //telemetry.addData(route, "finish ")

                driveTrain.turnDrive(samwiseDrive, -35, 2);
                //telemetry.addData(route, "finish first turn");
                //telemetry.update();

                driveTrain.encoderDrive(samwiseDrive, 25.5, 4);
                //telemetry.addData(route, "finish second driveToCrater");
                //telemetry.update();

                driveTrain.encoderDrive(samwiseDrive, -9.5, 3);
                //telemetry.addData(route, "finish third driveToCrater");
                //telemetry.update();

                driveTrain.turnDrive(samwiseDrive, -42, 5.5);
                //telemetry.addData(route, "finish fourth turn");
                //telemetry.update();

                driveTrain.encoderDrive(samwiseDrive, -43, 6);

                driveTrain.turnDrive(samwiseDrive, 26, 5);

                samwiseDrive.driveJ2up(500);

                driveTrain.driveToDepotFromSide(samwiseDrive, samwiseDrive.touchBackSide, false, samwiseDrive.color, 4);

                //driveTrain.driveToDepotFromSide(samwiseDrive, samwiseDrive.touchFrontSide, true, samwiseDrive.color, 6);

                //driveTrain.encoderDrive(samwiseDrive, 75,6);

                //driveTrain.encoderDrive(samwiseDrive, 7, 4);

                md.shake(SamwiseMarkerDeposit.dropPosition);

                // turn J1
                if (samwiseDrive.runWithArm)
                    samwiseDrive.turnJ1Auto(1250);

                //driveTrain.resetAngle(AxesOrder.ZYX);

                //driveTrain.turnDrive(samwiseDrive, 15, 3);
                ((SamwiseDriveTrain) driveTrain).turn(samwiseDrive, 18, 2.5);


                // make this wall driveToCrater using touch sensor
                //driveTrain.encoderDrive(samwiseDrive, 66, 66,7);
                ((SamwiseDriveTrain) driveTrain).driveToCrater(samwiseDrive, samwiseDrive.touchFrontSide, samwiseDrive.touchFront, true, 7);

                //((SamwiseDriveTrain)driveTrain).turn(samwiseDrive, 15, 2.5);

                //driveTrain.encoderDrive(samwiseDrive, -2.5, 2);
            }
        };
    }

    public static ISamwiseDriveRoute createCraterRight2(final SamwiseAutoDrive samwiseDrive) {
        return new ISamwiseDriveRoute() {
            String route = "Crater Right";
            //SamwiseDriveTrain    driveTrain = samwiseDrive.robot;
            SamwiseDriveTrainIMU driveTrain = (SamwiseDriveTrainIMU) samwiseDrive.robot;
            SamwiseMarkerDeposit md = samwiseDrive.md;
            Telemetry telemetry = samwiseDrive.telemetry;

            @Override
            public void drive() {
                //common driveToCrater defined by the parent
                //telemetryNow(route, "starting parent common driveToCrater ...");
                System.out.println("==>Driving route " + route);

                driveTrain.encoderDrive(samwiseDrive, 2.5, 5);
                //telemetry.addData(route, "finish ")

                driveTrain.turnDrive(samwiseDrive, -27, 2);
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

    public static ISamwiseDriveRoute createDepotCenter(final SamwiseAutoDrive samwiseDrive) {
        return new ISamwiseDriveRoute() {
            String route = "Depot Center";
            //SamwiseDriveTrain    driveTrain = samwiseDrive.robot;
            SamwiseDriveTrainIMU driveTrain = (SamwiseDriveTrainIMU) samwiseDrive.robot;
            SamwiseMarkerDeposit md = samwiseDrive.md;
            Telemetry telemetry = samwiseDrive.telemetry;

            @Override
            public void drive() {
                //common driveToCrater defined by the parent
                //telemetryNow(route, "starting parent common driveToCrater ...");
                System.out.println("==>Driving route " + route);

                driveTrain.encoderDrive(samwiseDrive, 36, 5);

                //driveTrain.turnDrive(samwiseDrive,6,2);

                samwiseDrive.driveJ2up(500);

                driveTrain.driveToDepotFromCenter(samwiseDrive, .8, samwiseDrive.color, 4);

                //driveTrain.encoderDrive(samwiseDrive,1,1); //get more into depot

                driveTrain.turnDrive(samwiseDrive, 123, 6);

                driveTrain.encoderDrive(samwiseDrive, 8, 2);

                md.shake(SamwiseMarkerDeposit.dropPosition);

                // turn J1
                if (samwiseDrive.runWithArm)
                    samwiseDrive.turnJ1Auto(1250);

                //driveTrain.turnDrive(samwiseDrive,-9.5,1);

                //driveTrain.resetAngle(AxesOrder.ZYX);

                //driveTrain.encoderDrive(samwiseDrive, 80,5);
                //driveTrain.driveToCrater(samwiseDrive, samwiseDrive.touchfront2, false, 6.5);
                ((SamwiseDriveTrain) driveTrain).driveToCrater(samwiseDrive, samwiseDrive.touchfront2, samwiseDrive.touchfront3, false, 6.5);

            }
        };
    }

    public static ISamwiseDriveRoute createDepotCenter2(final SamwiseAutoDrive samwiseDrive) {
        return new ISamwiseDriveRoute() {
            String route = "Depot Center";
            //SamwiseDriveTrain    driveTrain = samwiseDrive.robot;
            SamwiseDriveTrainIMU driveTrain = (SamwiseDriveTrainIMU) samwiseDrive.robot;
            SamwiseMarkerDeposit md = samwiseDrive.md;
            Telemetry telemetry = samwiseDrive.telemetry;

            @Override
            public void drive() {
                //common driveToCrater defined by the parent
                //telemetryNow(route, "starting parent common driveToCrater ...");
                System.out.println("==>Driving route " + route);

                driveTrain.encoderDrive(samwiseDrive, 30, 5);

                samwiseDrive.driveJ2up(500);

                driveTrain.driveToDepotFromCenter(samwiseDrive, .6, samwiseDrive.color, 4);

                driveTrain.turnDrive(samwiseDrive, -122.5, 6);

                md.shake(SamwiseMarkerDeposit.dropPosition);

                // turn J1
                if (samwiseDrive.runWithArm)
                    samwiseDrive.turnJ1Auto(1250);

                //driveTrain.encoderDrive(samwiseDrive, 80,5);
                //driveTrain.driveToCrater(samwiseDrive, samwiseDrive.touchFrontSide, true, 7);
                ((SamwiseDriveTrain) driveTrain).driveToCrater(samwiseDrive, samwiseDrive.touchFrontSide, samwiseDrive.touchFront, true, 7);

            }
        };
    }

    public static ISamwiseDriveRoute createDepotLeft(final SamwiseAutoDrive samwiseDrive) {
        return new ISamwiseDriveRoute() {
            String route = "Depot Left";
            //SamwiseDriveTrain    driveTrain = samwiseDrive.robot;
            SamwiseDriveTrainIMU driveTrain = (SamwiseDriveTrainIMU) samwiseDrive.robot;
            SamwiseMarkerDeposit md = samwiseDrive.md;
            Telemetry telemetry = samwiseDrive.telemetry;

            @Override
            public void drive() {
                //common driveToCrater defined by the parent
                //telemetryNow(route, "starting parent common driveToCrater ...");
                System.out.println("==>Driving route " + route);

                //driveTrain.encoderDrive(samwiseDrive,3, 3);
                driveTrain.encoderDriveSpeed(samwiseDrive, 3, 4, .5);

                driveTrain.turnDrive(samwiseDrive, 39, 3);

                driveTrain.encoderDrive(samwiseDrive, 33, 4);

                driveTrain.turnDrive(samwiseDrive, -71, 4);

                samwiseDrive.driveJ2up(500);

                //driveTrain.encoderDrive(samwiseDrive, 38,38,4);
                driveTrain.driveToDepotFromSide(samwiseDrive, samwiseDrive.touchFrontSide, true, samwiseDrive.color, 6);

                //driveTrain.encoderDrive(samwiseDrive,-2.5, 3);

                driveTrain.turnDrive(samwiseDrive, -106, 5);

                md.shake(SamwiseMarkerDeposit.dropPosition);

                // turn J1
                if (samwiseDrive.runWithArm)
                    samwiseDrive.turnJ1Auto(1250);

                //driveTrain.resetAngle(AxesOrder.ZYX);

                driveTrain.turnDrive(samwiseDrive, -78, 5);

                //driveTrain.encoderDrive(samwiseDrive,73,73, 5);
                //driveTrain.driveToCrater(samwiseDrive, samwiseDrive.touchfront2, false, 6);
                ((SamwiseDriveTrain) driveTrain).driveToCrater(samwiseDrive, samwiseDrive.touchfront2, samwiseDrive.touchfront3, false, 6.5);

            }
        };
    }

    public static ISamwiseDriveRoute createDepotLeft2(final SamwiseAutoDrive samwiseDrive) {
        return new ISamwiseDriveRoute() {
            String route = "Depot Left to our crater";
            //SamwiseDriveTrain    driveTrain = samwiseDrive.robot;
            SamwiseDriveTrainIMU driveTrain = (SamwiseDriveTrainIMU) samwiseDrive.robot;
            SamwiseMarkerDeposit md = samwiseDrive.md;
            Telemetry telemetry = samwiseDrive.telemetry;

            @Override
            public void drive() {
                //common driveToCrater defined by the parent
                //telemetryNow(route, "starting parent common driveToCrater ...");
                System.out.println("==>Driving route " + route);

                //driveTrain.encoderDrive(samwiseDrive,2.5, 5);
                driveTrain.encoderDriveSpeed(samwiseDrive, 2.5, 4, .5);

                driveTrain.turnDrive(samwiseDrive, 36, 3);

                driveTrain.encoderDrive(samwiseDrive, 30, 4);

                driveTrain.turnDrive(samwiseDrive, -67, 4);

                samwiseDrive.driveJ2up(500);

                //driveTrain.encoderDrive(samwiseDrive, 38,38,4);
                driveTrain.driveToDepotFromSide(samwiseDrive, samwiseDrive.touchFrontSide, true, samwiseDrive.color, 6);

                driveTrain.encoderDrive(samwiseDrive, -2.5, 5);

                driveTrain.turnDrive(samwiseDrive, -86, 5);

                md.shake(SamwiseMarkerDeposit.dropPosition);

                // turn J1
                if (samwiseDrive.runWithArm)
                    samwiseDrive.turnJ1Auto(1250);

                //driveTrain.encoderDrive(samwiseDrive,73,73, 5);
                //driveTrain.driveToCrater(samwiseDrive, samwiseDrive.touchFrontSide, true, 6);
                ((SamwiseDriveTrain) driveTrain).driveToCrater(samwiseDrive, samwiseDrive.touchFrontSide, samwiseDrive.touchFront, true, 7);

            }
        };
    }


    public static ISamwiseDriveRoute createDepotRight(final SamwiseAutoDrive samwiseDrive) {
        return new ISamwiseDriveRoute() {
            String route = "Depot Right";
            //SamwiseDriveTrain    driveTrain = samwiseDrive.robot;
            SamwiseDriveTrainIMU driveTrain = (SamwiseDriveTrainIMU) samwiseDrive.robot;
            SamwiseMarkerDeposit md = samwiseDrive.md;
            //DigitalChannel dc = samwiseDrive.digitalTouch;
            Telemetry telemetry = samwiseDrive.telemetry;

            @Override
            public void drive() {
                //common driveToCrater defined by the parent
                //telemetryNow(route, "starting parent common driveToCrater ...");
                System.out.println("==>Driving route " + route);

                driveTrain.encoderDriveSpeed(samwiseDrive, 3, 4, .5);

                driveTrain.turnDrive(samwiseDrive, -26.3, 3);

                driveTrain.encoderDrive(samwiseDrive, 44, 4);
                driveTrain.encoderDrive(samwiseDrive, -3, 4);
                //driveTrain.encoderDrive(samwiseDrive,-2, 4);

                driveTrain.turnDrive(samwiseDrive, 70, 5);
                //driveTrain.turnDrive(samwiseDrive, -101, 5);
                //driveTrain.turn(samwiseDrive, samwiseDrive.touchBackSide, true, 5);
                //driveTrain.encoderDrive(samwiseDrive, 16,2 );

                samwiseDrive.driveJ2up(500);

                driveTrain.driveToDepotFromSide(samwiseDrive, samwiseDrive.touchBackSide, true, samwiseDrive.color, 4.5);

                //driveTrain.encoderDrive(samwiseDrive, -16, 4);

                //driveTrain.turnDrive(samwiseDrive, 15, 3);
                driveTrain.turnDrive(samwiseDrive, 83, 3);

                md.shake(SamwiseMarkerDeposit.dropPosition);

                // turn J1
                if (samwiseDrive.runWithArm)
                    samwiseDrive.turnJ1Auto(1250);

                //driveTrain.resetAngle(AxesOrder.ZYX);

                // make this wall driveToCrater using touch sensor
                //driveTrain.encoderDrive(samwiseDrive, 66, 66,7);
                //driveTrain.driveToCrater(samwiseDrive, samwiseDrive.touchfront2, false, 7);
                ((SamwiseDriveTrain) driveTrain).driveToCrater(samwiseDrive, samwiseDrive.touchfront2, samwiseDrive.touchfront3, false, 6.5);

            }
        };
    }

    public static ISamwiseDriveRoute createDepotRight2(final SamwiseAutoDrive samwiseDrive) {
        return new ISamwiseDriveRoute() {
            String route = "Depot Right";
            //SamwiseDriveTrain    driveTrain = samwiseDrive.robot;
            SamwiseDriveTrainIMU driveTrain = (SamwiseDriveTrainIMU) samwiseDrive.robot;
            SamwiseMarkerDeposit md = samwiseDrive.md;
            //DigitalChannel dc = samwiseDrive.digitalTouch;
            Telemetry telemetry = samwiseDrive.telemetry;

            @Override
            public void drive() {
                //common driveToCrater defined by the parent
                //telemetryNow(route, "starting parent common driveToCrater ...");
                System.out.println("==>Driving route " + route);

                //driveTrain.encoderDrive(samwiseDrive, 2.5, 4);
                driveTrain.encoderDriveSpeed(samwiseDrive, 3, 4, .5);

                driveTrain.turnDrive(samwiseDrive, -35, 3);

                driveTrain.encoderDrive(samwiseDrive, 41, 4);

                samwiseDrive.driveJ2up(500);

                driveTrain.turnDrive(samwiseDrive, -101, 5);
                //driveTrain.turn(samwiseDrive, samwiseDrive.touchBackSide, true, 5);

                // turn J1
                if (samwiseDrive.runWithArm)
                    samwiseDrive.turnJ1Auto(1250);


                driveTrain.driveToDepotFromSide(samwiseDrive, samwiseDrive.touchBackSide, false, samwiseDrive.color, 6);

                //driveTrain.encoderDrive(samwiseDrive, -16, 4);

                //driveTrain.turnDrive(samwiseDrive, 15, 3);

                md.shake(SamwiseMarkerDeposit.dropPosition);



                driveTrain.turnDrive(samwiseDrive, 12, 3);

                // make this wall driveToCrater using touch sensor
                //driveTrain.encoderDrive(samwiseDrive, 66, 66,7);
                //driveTrain.driveToCrater(samwiseDrive, samwiseDrive.touchFrontSide, true, 7);
                ((SamwiseDriveTrain) driveTrain).driveToCrater(samwiseDrive, samwiseDrive.touchFrontSide, samwiseDrive.touchFront, true, 7);

            }
        };
    }


    /**
     * A general route for all depot positions
     *
     * @param samwiseDrive
     * @return
     */
    public static ISamwiseDriveRoute createDepot(final SamwiseAutoDrive samwiseDrive) {
        return new ISamwiseDriveRoute() {
            String route = "Depot Left";
            //SamwiseDriveTrain    driveTrain = samwiseDrive.robot;
            SamwiseDriveTrainIMU driveTrain = (SamwiseDriveTrainIMU) samwiseDrive.robot;
            SamwiseMarkerDeposit md = samwiseDrive.md;
            Telemetry telemetry = samwiseDrive.telemetry;

            @Override
            public void drive() {
                //common driveToCrater defined by the parent
                //telemetryNow(route, "starting parent common driveToCrater ...");
                System.out.println("==>Driving route " + route);

                driveTrain.encoderDrive(samwiseDrive, 3, 4);

                driveTrain.turnDrive(samwiseDrive, -35, 3);

                driveTrain.encoderDrive(samwiseDrive, 43, 4);

                driveTrain.turnDrive(samwiseDrive, -100, 3);

                driveTrain.encoderDrive(samwiseDrive, -27, 4);

                driveTrain.turnDrive(samwiseDrive, 15, 3);

                md.shake(SamwiseMarkerDeposit.dropPosition);

                // turn J1
                if (samwiseDrive.runWithArm)
                    samwiseDrive.turnJ1Auto(550);

                // make this wall driveToCrater using touch sensor
                //driveTrain.encoderDrive(samwiseDrive, 66, 66,7);
                driveTrain.driveToCrater(samwiseDrive, samwiseDrive.touchFrontSide, samwiseDrive.touchFront, false, 6);
            }
        };
    }

    public static ISamwiseDriveRoute createCraterRimTest(final SamwiseAutoDrive samwiseDrive) {

        return new ISamwiseDriveRoute() {
            String route = "Crater RIM Test";
            SamwiseDriveTrainIMU driveTrain = (SamwiseDriveTrainIMU) samwiseDrive.robot;
            SamwiseMarkerDeposit md = samwiseDrive.md;
            //Telemetry            telemetry  = samwiseDrive.telemetry;

            @Override
            public void drive() {
                //common driveToCrater defined by the parent
                //telemetryNow(route, "starting parent common driveToCrater ...");
                System.out.println("==>Driving route " + route);

                driveTrain.resetAngle(AxesOrder.ZYX);

                driveTrain.driveToCrater(samwiseDrive, samwiseDrive.touchFrontSide, true, 6);

                //driveTrain.encoderDrive(samwiseDrive, 72, 72, 20);
                //telemetry.addData(route, "finish eighth driveToCrater");
                //telemetry.update();
            }
        };
    }
}
