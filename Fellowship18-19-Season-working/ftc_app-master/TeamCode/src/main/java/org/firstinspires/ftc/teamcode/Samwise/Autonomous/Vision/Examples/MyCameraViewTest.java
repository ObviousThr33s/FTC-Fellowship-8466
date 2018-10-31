package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Vision.Examples;

import android.content.Context;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

@Autonomous(name="Kai Vision Test 10", group="Samwise")
//@Disabled
public class MyCameraViewTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        //MyCameraView2 cam = new MyCameraView2(hardwareMap.appContext, 0);

        //cam.takePicture("Kai.jpg");

        telemetry.addLine("call takePhoto ...");
        telemetry.update();
        Image photo = takePhoto();

        FileOutputStream outputStream;

        try {
            System.out.println("Kai before output");

            File path = hardwareMap.appContext.getFilesDir();
            System.out.println("-------- output dir: "+path);
            File file = new File(path, "KaiTest.txt");


            FileOutputStream stream = new FileOutputStream(file);
            try {
                stream.write("text-to-write".getBytes());
            } finally {
                stream.flush();
                stream.close();
            }

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(hardwareMap.appContext.openFileOutput("KaiTest2.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write("Kai Test Data");
            outputStreamWriter.flush();
            outputStreamWriter.close();
            //outputStream = hardwareMap.appContext.openFileOutput("Kai.png", Context.MODE_WORLD_READABLE);
            //outputStream.write("my test".getBytes());
            System.out.println("Kai test after output");
            //System.out.println(photo.getPixels().array());
            //outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        telemetry.addData("image: ", photo);
        telemetry.update();

    }

    public Image takePhoto() throws InterruptedException {

        VuforiaLocalizer.Parameters params  = new VuforiaLocalizer.Parameters(0);
        params.vuforiaLicenseKey = "AavdL67/////AAABmSLyjI+FqUuCgd9WQ8dI6EwP0okYiKtYqPQFYp0ed/M5ZolBrdToax7q9PU3GBnXHTEjhViZHcIlSQrhG8INGP1EswndVpk1yYp26emXbrWaGbnoWRaUfOe/WalsOH1z+m1wNW0vj0kiiTDt8K8S4CsWi5iU/9gGZRP1p43/4geoG82J19xDxBF36pRH+7+nuVzO1IziNK2liwKx3nCwiPEbECLAWORmdSodGm+L/bWIUG6MOZsUmQgeYqqhVBPmNgEZW/SEPMnfsw6dyR3beixxdmP0Na/eWokV97TNvt8lfbGxdFwFsgxR+HI2+lRIGfvehuTdLdsTlcvdTfOsXPEdaNS9QSUeGlOqtQEYBkAo";
        params.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        VuforiaLocalizer locale = ClassFactory.createVuforiaLocalizer(params);
        Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true); //enables RGB565 format for the image
        locale.setFrameQueueCapacity(1); //tells VuforiaLocalizer to only store one frame at a time

        /*To access the image: you need to iterate through the images of the frame object:*/

        VuforiaLocalizer.CloseableFrame frame = null; //takes the frame at the head of the queue
        frame = locale.getFrameQueue().take();

        Image rgb = null;

        long numImages = frame.getNumImages();


        for (int i = 0; i < numImages; i++) {
            if (frame.getImage(i).getFormat() == PIXEL_FORMAT.RGB565) {
                rgb = frame.getImage(i);
                break;
            }//if
        }//for

        /*rgb is now the Image object that we’ve used in the video*/
        return rgb;
    }

}
