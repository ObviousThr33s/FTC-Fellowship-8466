package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

/**
 * Created by 28761 on 10/7/2017.
 */

@Autonomous(name = "Blue 2: Opposite to relic recovery", group = "Auto")
public class AutoBlue2 extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
            KeithRobot keith = new KeithRobot(hardwareMap, telemetry);
            MecanumDS ds = (MecanumDS) (keith.GetDriveSystem());
            KeithElevator ele = keith.GetKeithElevator();
            KeithCarriage car = keith.GetKeithCarriage();
            JewlDetect jwld = keith.GetKeithJewlDetect();
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.UNKNOWN;
            KeithJewlKnocker jks = keith.GetJewelKnockerSubsystem();
            jks.setKnockerPosition(0.65);
            jks.setBasePosition(0.0);

            ele.kickerSetPosition(0.7);

            double movePower = 0.1;
            double spinPower = 0.1;

            boolean isBlue;

            jwld.JewlDetectForInit(telemetry, hardwareMap);
            telemetry.update();

            // All is initialized, wait for the start
            waitForStart();

            while (opModeIsActive()) {

                AutoUtilities.CarriageGrip(car, opModeIsActive());

                // At this point we should have a pictogram and a color for the
                // jewel that is close to the pictogram, i.e. on the left side of
                // the jewel knocker. We will now make a decision about which jewel
                // to knock off using the jewel knocker.
                // since we are in the Blue zone, we'll try to remove the Red jewel

                isBlue = jwld.JewlColor();
                telemetry.addData("", jwld.JewlColor());
                telemetry.update();

                if (isBlue == true) {
                    telemetry.addData("", "Left side jewel is BLUE");
                    AutoUtilities.KnockJewel(jks, false);
                } else if (isBlue == false) {
                    telemetry.addData("", "Left side jewel is RED");
                    AutoUtilities.KnockJewel(jks, true);
                } else {
                    telemetry.addData("", "Could not find jewel color.");
                }

                // irrespective of whether we knocked the jewel or not, we are now
                // going to navigate towards the glyph box. We are in the Blue zone
                // and we are opposite to the relic recovery area.
                AutoUtilities.ExecuteMovesBlue(ds, movePower, spinPower, Color.BLUE, false, telemetry, opModeIsActive());

                // At this point we should be in front of the glyph box. Let's unload
                // the block we have based on the cypher that we read
                if (vuMark == RelicRecoveryVuMark.UNKNOWN) {
                    // we were not able to read the cypher, don't do anything
                    telemetry.addData("", "Could not read the cypher, nothing to do.");
                } else {
                    // We now know the column into which we are supposed to score our glyph
                    // The glyph is at the top of the elevator at this point. Depending on
                    // the column from vuMark, we need to move the carriage into the position
                    // to receive the glyph, kick the glyph, then move the carriage into the
                    // position so that the glyph is in front of the right column, and then
                    // finally unload the glyph.
                    telemetry.addData("", "Cypher column: %s", vuMark);
                    // TODO - implement this
                }

                AutoUtilities.CarriageFlip(car);

                ds.Move(0.1, 0, 0, 300, 1000);

                // make all the telemetry messages appear
                telemetry.update();

                // we are done. Wait here for the time to run out, it'll give a chance to
                // the operator to read the telemetry results.
                stop();
            }
        }
    }
