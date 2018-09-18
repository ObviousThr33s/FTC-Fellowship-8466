package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.HINT;
import com.vuforia.Image;
import com.vuforia.Matrix34F;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Tool;
import com.vuforia.Vec2F;
import com.vuforia.Vec3F;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;

/**
 * Created by 28761 on 10/7/2017.
 */

@Autonomous(name = "Vuforia: Jewel Detection", group = "Concept")
public class VuforiaTest extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        //set camera direction
        params.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        //set license key
        params.vuforiaLicenseKey = "AZngjFj/////AAAAGXl4r+gz20bgi78ZEfdDoSM3BRzoPWF85Z/GS524liytojbME/4mrMSgTIJrEsW1IxxgIy6Po9DKP08uYMrcCpsVG1gd800G3RIRQ0KNtQnC7onvphQ2RBZ+3JXkfdYLct13YRM1TzbJLWaS4Lz5bSMMRpSTJU8zSwzAZ1fIdqwXBevZZMkd+LKtIogK+wl1fBo/SaDcrrSW/BIePFCbk1bBG1eaAetcLjEUngrGYBtmD+PdYbefaBFwuzV+eQDU0E671GNILzDhirYTAcFfe/+F2WK9VgAVZfycin4Iv06GyebuSfTiIsE65jhoXY9FQy3ZWnwZGHcID0e/KRG/+CYdk9A+ltYPi7qfrMh/lk5/";
        //set camera feedback just for fun
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;
        //initiate vuforia
        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(params);
        //set maximum amount of objects vuforia can trace
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);
        //get image form database, vumark type
        VuforiaTrackables pictograms = vuforia.loadTrackablesFromAsset("RelicVuMark");
        //set name for image/object
        pictograms.get(0).setName("pictogram");
        //
        Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true); //enables RGB888 format for the image
        //only 1 frame at a time
        vuforia.setFrameQueueCapacity(1);

        waitForStart();
        //vuforia starts running
        pictograms.activate();
        int p = 0;

        while (opModeIsActive()) {
            p++;
            VuforiaLocalizer.CloseableFrame frame = vuforia.getFrameQueue().take(); //takes the frame at the head of the queue
            Image rgb = null;
            long numImages = frame.getNumImages();

            for (int i = 0; i < numImages; i++) {
                if (frame.getImage(i).getFormat() == PIXEL_FORMAT.RGB565) {
                    rgb = frame.getImage(i);
                    break;
                }
            }
            Bitmap bm = null;

            if (rgb != null) {
                bm = Bitmap.createBitmap(rgb.getWidth(), rgb.getHeight(), Bitmap.Config.RGB_565);
                bm.copyPixelsFromBuffer(rgb.getPixels());
                telemetry.addData("RGB", "isn't null");

            } else {
                telemetry.addData("RGB", "is null");
            }
            for (VuforiaTrackable image : pictograms) {
                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) image.getListener()).getPose();
                OpenGLMatrix raw = ((VuforiaTrackableDefaultListener) image.getListener()).getRawPose();
                if (pose != null) {
                    VectorF tanslation = pose.getTranslation();
                    Matrix34F rawPose = new Matrix34F();
                    float[] poseData = Arrays.copyOfRange(pose.transposed().getData(), 0, 12);
                    rawPose.setData(poseData);
                    //project a 3d vector on to out 2d plane
                    Vec2F location = new Vec2F(100, 150);
                    if (bm == null) {
                        telemetry.addData("Bitmap", "is null");
                    } else {
                        int ox = (int) location.getData()[0];
                        int oy = (int) location.getData()[1];
                        int RSum = 0;
                        int GSum = 0;
                        int BSum = 0;
                        int count = 0;
                        String rColor = null;
                        for (int i = ox; i < ox + 3; i++) {
                            for (int j = oy; j > oy - 3; j--) {
                                if (0 < i && i < bm.getWidth() && 0 < j && j < bm.getHeight()) {
                                    count++;
                                    int color = bm.getPixel(i, j);
                                    RSum += getR(color);
                                    GSum += getG(color);
                                    BSum += getB(color);

                                }
                            }
                        }
                        if (count != 0) {
                            RSum /= count;
                            GSum /= count;
                            BSum /= count;
                        }
                        if (p == 100) {
                            try {
                                String filename = "image.png";
                                File sd = Environment.getExternalStorageDirectory();
                                File dest = new File(sd, filename);
                                if (inRange(ox, oy, bm)) {
                                    mark(ox, oy, bm);
                                } else {
                                    telemetry.addData("pixle ", "out of range");
                                }

                                bm.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(dest));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }

                        if ((RSum > 200 && GSum < 50 && BSum < 50) | (RSum > GSum + 100 && RSum > BSum + 100)) {
                            rColor = "Red";
                        } else if ((RSum < 70 && GSum > 70 && BSum > 170) | (BSum > RSum + 100 && BSum > GSum + 50)) {
                            rColor = "Blue";
                        } else {
                            rColor = "Other";
                        }

                        telemetry.addData("Color:", rColor);

//                        if (0 < x && x < bm.getWidth() && 0 < y && y < bm.getHeight()) {
//                            int color = bm.getPixel(x, y);
//                            telemetry.addData("Color ", format(color));
//                        } else {
//                            telemetry.addData("Color ", "not in range");
//                        }

                    }

                }
            }
            telemetry.update();
        }
    }

    static int determinColor(int color) {
        int R = getR(color);
        int G = getG(color);
        int B = getB(color);
        if ((R > 200 && G < 50 && B < 50) | (R > G + 100 && R > B + 100)) {
            return 0;
        } else if ((R < 70 && G > 70 && B > 170) | (B > R + 100 && B > G + 50)) {
            return 1;
        } else {
            return -1;
        }
    }

    static boolean inRange(int x, int y, Bitmap bm) {
        return 0 < x && x < bm.getWidth() && 0 < y && y < bm.getHeight();
    }

    static void mark(int x, int y, Bitmap bm) {
        for (int i = x - 50; i < x + 50; i++) {
            if (0 < i && i < bm.getWidth()) {
                bm.setPixel(i, y, Color.CYAN);
            }
        }
        for (int j = y - 50; j < y + 50; j++) {
            if (0 < j && j < bm.getHeight()) {
                bm.setPixel(x, j, Color.CYAN);
            }
        }
    }

    static String format(int color) {
        return "Red: " + getR(color) + " Green: " + getG(color) + " Blue: " + getB(color);
    }

    static int getR(int rgb) {
        return (rgb & 0xf800) >> 11;
    }

    static int getG(int rgb) {
        return (rgb & 0x7e0) >> 5;
    }

    static int getB(int rgb) {
        return (rgb & 0x1f);
    }
}
