package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;

import com.disnodeteam.dogecv.DogeCV;
import com.vuforia.HINT;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vec2F;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by Joshua on 10/28/2017.
 */

public class AutoUtilities {

    static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    static VuforiaLocalizer VuforiaInitialize() {
        //do all the initialization needed
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        //set camera direction
        params.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        //set license key
        params.vuforiaLicenseKey = "AZngjFj/////AAAAGXl4r+gz20bgi78ZEfdDoSM3BRzoPWF85Z/GS524liytojbME/4mrMSgTIJrEsW1IxxgIy6Po9DKP08uYMrcCpsVG1gd800G3RIRQ0KNtQnC7onvphQ2RBZ+3JXkfdYLct13YRM1TzbJLWaS4Lz5bSMMRpSTJU8zSwzAZ1fIdqwXBevZZMkd+LKtIogK+wl1fBo/SaDcrrSW/BIePFCbk1bBG1eaAetcLjEUngrGYBtmD+PdYbefaBFwuzV+eQDU0E671GNILzDhirYTAcFfe/+F2WK9VgAVZfycin4Iv06GyebuSfTiIsE65jhoXY9FQy3ZWnwZGHcID0e/KRG/+CYdk9A+ltYPi7qfrMh/lk5/";
        //set camera feedback just for fun
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;
        //initiate vuforia
        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(params);
        //set maximum amount of objects vuforia can trace
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);

        return vuforia;
    }


    static public void CarriageGrip(KeithCarriage carriage, boolean opModeIsActive) {
        if (opModeIsActive) {
            carriage.slideTo(KeithCarriage.CENTER);
            sleep(500);
        }
        if (opModeIsActive) {
            carriage.holderToggle(KeithCarriage.RIGHTS);
        }
    }


    static public void KnockJewel(KeithJewlKnocker jewlKnocker, boolean leftSide) {
        jewlKnocker.setUpPosition();
        jewlKnocker.knockerDown();
        if (leftSide) {
            jewlKnocker.knockRight();
        } else {
            jewlKnocker.knockLeft();
        }
        jewlKnocker.knockerUp();
        jewlKnocker.setDownPosition();

    }


    static public void CarriageFlip(KeithCarriage carriage) {
            carriage.slideTo(KeithCarriage.RIGHT);
            sleep(2000);
            carriage.flipperToggle();
            sleep(2000);
            carriage.holderToggle(KeithCarriage.RIGHTS);
            sleep(2000);
            carriage.flipperToggle();
            sleep(2000);
            carriage.slideTo(KeithCarriage.CENTER);
            sleep(1000);
            carriage.slideTo(KeithCarriage.CENTER);

    }

    public static boolean ExecuteMovesBlue(MecanumDS ds, double movePower, double spinPower, int colorPosition, boolean nextToRelicRecovery, Telemetry telemetry, boolean opModeIsActive) {
        if (colorPosition == Color.BLUE && opModeIsActive) {
            if (nextToRelicRecovery) {
                telemetry.addData("", "Moves: BLUE, next to RelicRecovery");
                // For our start position, we are facing away from the glyph box area,
                // so we need to move back, then turn left 90 degrees, then move
                // back again a little bit.

                // Move back
                if (opModeIsActive) {
                    if (!ds.Move(movePower, 180, 0, 1300, 5000)) {
                        // we seem to have timed out. Let's not continue this anymore
                        // it's not safe to try to go further as we don't really know
                        // our current position anymore
                        telemetry.addData("", "Backward move timed out, abandoning.");
                        return false;
                    }
                }

                // Turn left 90 degrees
                if (opModeIsActive) {
                    if (!ds.Move(spinPower, 0, 90, 0, 5000) && opModeIsActive) {
                        // we seem to have timed out. Let's not continue this anymore
                        // it's not safe to try to go further as we don't really know
                        // our current position anymore
                        telemetry.addData("", "Left turn timed out, abandoning.");
                        return false;
                    }
                }

                // Move back
                if (opModeIsActive) {
                    if (!ds.Move(movePower, 180, 0, 310, 5000) && opModeIsActive) {
                        // we seem to have timed out. Let's not continue this anymore
                        // it's not safe to try to go further as we don't really know
                        // our current position anymore
                        telemetry.addData("", "Backward move timed out, abandoning.");
                        return false;
                    }
                }
            } else {
                telemetry.addData("", "Moves: BLUE, away from RelicRecovery");
                // For our start position, we are facing away from the glyph box area so we
                // need to move back, then turn left 90 degrees, then move forward a bit more,
                // turn right 90 degrees and finally move back a little bit.

                // Move back
                if (opModeIsActive) {
                    if (!ds.Move(movePower, 180, 0, 1000, 5000) && opModeIsActive) {
                        // we seem to have timed out. Let's not continue this anymore
                        // it's not safe to try to go further as we don't really know
                        // our current position anymore
                        telemetry.addData("", "Backward move timed out, abandoning.");
                        return false;
                    }
                }

                // Turn left 90 degrees
                if (opModeIsActive) {
                    if (!ds.Move(spinPower, 0, 90, 0, 5000) && opModeIsActive) {
                        // we seem to have timed out. Let's not continue this anymore
                        // it's not safe to try to go further as we don't really know
                        // our current position anymore
                        telemetry.addData("", "Left turn timed out, abandoning.");
                        return false;
                    }
                }

                // Move forward
                if (opModeIsActive) {
                    if (!ds.Move(movePower, 0, 0, 440, 5000) && opModeIsActive) {
                        // we seem to have timed out. Let's not continue this anymore
                        // it's not safe to try to go further as we don't really know
                        // our current position anymore
                        telemetry.addData("", "Forward move timed out, abandoning.");
                        return false;
                    }
                }

                // Turn right 90 degrees
                if (opModeIsActive) {
                    if (!ds.Move(spinPower, 0, -90, 0, 5000) && opModeIsActive) {
                        // we seem to have timed out. Let's not continue this anymore
                        // it's not safe to try to go further as we don't really know
                        // our current position anymore
                        telemetry.addData("", "Right turn timed out, abandoning.");
                        return false;
                    }
                }

                // Move back
                if (opModeIsActive) {
                    if (!ds.Move(movePower, 180, 0, 120, 5000) && opModeIsActive) {
                        // we seem to have timed out. Let's not continue this anymore
                        // it's not safe to try to go further as we don't really know
                        // our current position anymore
                        telemetry.addData("", "Backward move timed out, abandoning.");
                        return false;
                    }
                }
            }
        } else {
            telemetry.addData("", "Moves: unknown position");
            return false;
        }
        return true;
    }

    public static boolean ExecuteMovesRed(MecanumDS ds, double movePower, double spinPower, int colorPosition, boolean nextToRelicRecovery, Telemetry telemetry, boolean opModeIsActive) {
        if (colorPosition == Color.RED && opModeIsActive) {
            if (nextToRelicRecovery) {
                telemetry.addData("", "Moves: RED, next to RelicRecovery");
                // For our start position, we are facing the glyph box area, so we
                // need to move forward, then turn left 90 degrees, then move
                // back a little bit.

                // Move forward
                if (opModeIsActive) {
                    if (!ds.Move(movePower, 0, 0, 1300, 5000)) {
                        // we seem to have timed out. Let's not continue this anymore
                        // it's not safe to try to go further as we don't really know
                        // our current position anymore
                        telemetry.addData("", "Forward move timed out, abandoning.");
                        return false;
                    }
                }

                // Turn left 90 degrees
                if (opModeIsActive) {
                    if (!ds.Move(spinPower, 0, 90, 0, 5000)) {
                        // we seem to have timed out. Let's not continue this anymore
                        // it's not safe to try to go further as we don't really know
                        // our current position anymore
                        telemetry.addData("", "Left turn timed out, abandoning.");
                        return false;
                    }
                }

                // Move back
                if (opModeIsActive) {
                    if (!ds.Move(movePower, 180, 0, 340, 5000)) {
                        // we seem to have timed out. Let's not continue this anymore
                        // it's not safe to try to go further as we don't really know
                        // our current position anymore
                        telemetry.addData("", "Backward move timed out, abandoning.");
                        return false;
                    }
                }
            } else {
                telemetry.addData("", "Moves: RED, away from RelicRecovery");
                // For our start position, we are facing the glyph box area, so we
                // need to move forward, then turn left 90 degrees, then move forward
                // a bit more, turn left 90 degrees again and finally move back a little bit.

                // Move forward
                if (opModeIsActive) {
                    if (!ds.Move(movePower, 0, 0, 1000, 5000)) {
                        // we seem to have timed out. Let's not continue this anymore
                        // it's not safe to try to go further as we don't really know
                        // our current position anymore
                        telemetry.addData("", "Forward move timed out, abandoning.");
                        return false;
                    }
                }

                // Turn left 90 degrees
                if (opModeIsActive) {
                    if (!ds.Move(spinPower, 0, 90, 0, 5000) && opModeIsActive) {
                        // we seem to have timed out. Let's not continue this anymore
                        // it's not safe to try to go further as we don't really know
                        // our current position anymore
                        telemetry.addData("", "Left turn timed out, abandoning.");
                        return false;
                    }
                }

                // Move forward
                if (opModeIsActive) {
                    if (!ds.Move(movePower, 0, 0, 450, 5000) && opModeIsActive) {
                        // we seem to have timed out. Let's not continue this anymore
                        // it's not safe to try to go further as we don't really know
                        // our current position anymore
                        telemetry.addData("", "Forward move timed out, abandoning.");
                        return false;
                    }
                }

                // Turn left 90 degrees
                if (opModeIsActive) {
                    if (!ds.Move(spinPower, 0, 90, 0, 5000) && opModeIsActive) {
                        // we seem to have timed out. Let's not continue this anymore
                        // it's not safe to try to go further as we don't really know
                        // our current position anymore
                        telemetry.addData("", "Left turn timed out, abandoning.");
                        return false;
                    }
                }

                // Move back
                if (opModeIsActive) {
                    if (!ds.Move(movePower, 180, 0, 180, 5000) && opModeIsActive) {
                        // we seem to have timed out. Let's not continue this anymore
                        // it's not safe to try to go further as we don't really know
                        // our current position
                        telemetry.addData("", "Backward move timed out, abandoning.");
                        return false;
                    }
                }
            }
        } else {
            telemetry.addData("", "Moves: unknown position");
            return false;
        }
        return true;
    }
}
