package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by Joshua on 1/30/2018.
 */

@Autonomous(name = "NewRed1: Next to relic recovery", group = "Auto")
public class NewRed1 extends LinearOpMode {

    //vuforia component
    OpenGLMatrix lastLocation = null;
    VuforiaLocalizer vuforia;

    @Override
    public void runOpMode() throws InterruptedException {
        KeithRobot keith = new KeithRobot(hardwareMap, telemetry);
        MecanumDS ds = (MecanumDS) (keith.GetDriveSystem());
        ds.setLinearOpMode(this);
        KeithElevator ele = keith.GetKeithElevator();
        KeithJewlKnocker jks = keith.GetJewelKnockerSubsystem();
        jks.sensor.enableLed(true);
        jks.setLinearOpMode(this);
        NewUtilities utility = new NewUtilities(this);
        ele.kickerSetPosition(KeithElevator.upPos);
        jks.setKnockerPosition(0.99);
        jks.setBasePosition(0.18);
        ele.kickerSetPosition(0.7);
        //Note that this color sensor stuff may not be working.
        //However, the rest of the program(s) should be all good.

        //vuforia initialization
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AZngjFj/////AAAAGXl4r+gz20bgi78ZEfdDoSM3BRzoPWF85Z/GS524liytojbME/4mrMSgTIJrEsW1IxxgIy6Po9DKP08uYMrcCpsVG1gd800G3RIRQ0KNtQnC7onvphQ2RBZ+3JXkfdYLct13YRM1TzbJLWaS4Lz5bSMMRpSTJU8zSwzAZ1fIdqwXBevZZMkd+LKtIogK+wl1fBo/SaDcrrSW/BIePFCbk1bBG1eaAetcLjEUngrGYBtmD+PdYbefaBFwuzV+eQDU0E671GNILzDhirYTAcFfe/+F2WK9VgAVZfycin4Iv06GyebuSfTiIsE65jhoXY9FQy3ZWnwZGHcID0e/KRG/+CYdk9A+ltYPi7qfrMh/lk5/";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary
        telemetry.addData(">", "Press Play to start");

        //prepare to read cypher
        relicTrackables.activate();
        RelicRecoveryVuMark vuMark = null;

        waitForStart();
        long startTime = System.currentTimeMillis();
        //loop 100 times for vuforia to get a stable reading
        while (System.currentTimeMillis() - startTime < 3000) {

            vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                telemetry.addData("VuMark", "%s visible", vuMark);
                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) relicTemplate.getListener()).getPose();
                telemetry.addData("Pose", format(pose));
                if (pose != null) {
                    VectorF trans = pose.getTranslation();
                    Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                    // Extract the X, Y, and Z components of the offset of the target relative to the robot
                    double tX = trans.get(0);
                    double tY = trans.get(1);
                    double tZ = trans.get(2);

                    // Extract the rotational components of the target relative to the robot
                    double rX = rot.firstAngle;
                    double rY = rot.secondAngle;
                    double rZ = rot.thirdAngle;
                }
                //successfully read the cypher
                break;
            } else {
                telemetry.addData("VuMark", "not visible");
            }

            telemetry.update();
        }

        telemetry.addLine("reading session finished");
        telemetry.addLine("result:" + vuMark);
        telemetry.update();

        double movePower = 0.1;
        double spinPower = 0.1;


        //SCORING POSITION EITHER LEFT (-1), CENTER (0), OR RIGHT (1)
        //by default, 0 at the center
        int cryptoPosition = 0;

        if (vuMark.toString().equals("LEFT")) {
            cryptoPosition = 1;
        } else if (vuMark.toString().equals("RIGHT")) {
            cryptoPosition = -1;
        } else if (vuMark.toString().equals("CENTER")) {
            cryptoPosition = 0;
        }


        telemetry.addLine(String.format("Detected code: %d", cryptoPosition));
        telemetry.update();
        //sleep(3000);

        if (!opModeIsActive()) {
            //abort due to turning off OpMode
            return;
        }

        utility.KnockJewel(jks, true);


//        if (color_sensor.red() > 200) {
//            NewUtilities.KnockJewel(jks, true); // <--- Use either vuforia or color sensor code to find which side to knock.
//        }else if (color_sensor.blue() > 200) {
//            NewUtilities.KnockJewel(jks, false); // <--- Use either vuforia or color sensor code to find which side to knock.
//        }else {
//            telemetry.addLine("Could not find a jewel color. Do nothing.");
//        }

        if (!opModeIsActive()) {
            //abort due to turning off OpMode
            return;
        }

        utility.ExecuteMovesRed(ds, ele, movePower, spinPower, true, telemetry, cryptoPosition);

//        stop();

//        while (opModeIsActive()) {
//
//        }

    }

    String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }

}