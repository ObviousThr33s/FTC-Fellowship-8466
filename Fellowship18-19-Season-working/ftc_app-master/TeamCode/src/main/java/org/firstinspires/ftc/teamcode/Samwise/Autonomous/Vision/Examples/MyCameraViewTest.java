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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;

@Autonomous(name="Kai Vision Test 12", group="Samwise")
//@Disabled
public class MyCameraViewTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        //MyCameraView2 cam = new MyCameraView2(hardwareMap.appContext, 0);

        //cam.takePicture("Kai.jpg");

        telemetry.addLine("call takePhoto ...");
        telemetry.update();
        Image photo = takePhoto();
/*
        try {
            System.out.println("Kai before output");

            File path = hardwareMap.appContext.getFilesDir();
            if(path.exists()){
                System.out.println("-------- output dir exists: "+path);
            }
            else {
                System.out.println("-------- output dir NOT exist: "+path);
            }
            //System.out.println("-------- output dir: "+path);
            File file = new File(path, "KaiTest.txt"); // file1.exists()=true
            File file2 = new File("/storage/emulated/0/FIRST/KaiTest2.txt");
            File imgFile = new File("/storage/emulated/0/FIRST/KaiImg.bmp");

            if(!file2.exists()) {

                System.out.println("------- file2 does not exist. Kai creating new file");
                file2.createNewFile();
            }
            else {
                System.out.println("--------  file2 exists "+path + file);
            }
            if(!imgFile.exists()) {

                System.out.println("------- imgFile does not exist. Kai creating new file");
                imgFile.createNewFile();
            }
            else {
                System.out.println("--------  imgFile exists "+path + imgFile);
            }


            FileOutputStream stream = new FileOutputStream(file);
            FileOutputStream stream2 = new FileOutputStream(file2);

            FileOutputStream imgOutStr = new FileOutputStream(imgFile);
            FileChannel imgOutChannel = imgOutStr.getChannel();

            try {
                stream.write("----- text-to-write\n".getBytes());
                stream2.write("----- file2 text-to-write\n".getBytes());
                imgOutChannel.write(photo.getPixels());

            } finally {
                stream.flush();
                stream.close();
                imgOutStr.close();
            }

            // read file
            FileInputStream fis = new FileInputStream(file);
            System.out.println("--------  read and print content of file : "+path + file);
            int content;
            while ((content = fis.read()) != -1) {
                // convert to char and display it
                System.out.print((char) content);
            }

            fis = new FileInputStream(file2);
            System.out.println("--------  read and print content of file : "+path + file2);
            //int content;
            while ((content = fis.read()) != -1) {
                // convert to char and display it
                System.out.print((char) content);
            }


            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(hardwareMap.appContext.openFileOutput("KaiTest2.txt", Context.MODE_WORLD_WRITEABLE));
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

*/
        telemetry.addData("image: ", photo);
        telemetry.update();

        System.out.println("-------- sleep  1 min ");
        sleep(60000);

    }

    public Image takePhoto() throws InterruptedException {

        VuforiaLocalizer.Parameters params  = new VuforiaLocalizer.Parameters();
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

            try {
                File imgFile = new File("/storage/emulated/0/FIRST/KaiImg"+i+".bmp");

                if (!imgFile.exists()) {

                    System.out.println("------- imgFile does not exist. Kai creating new file");
                    imgFile.createNewFile();
                } else {
                    System.out.println("--------  imgFile exists " + imgFile);
                }


                FileOutputStream imgOutStr = new FileOutputStream(imgFile);
                FileChannel imgOutChannel = imgOutStr.getChannel();

                try {
                    imgOutChannel.write(frame.getImage(i).getPixels());

                } finally {
                    imgOutStr.flush();

                    imgOutStr.close();
                }


                //if (frame.getImage(i).getFormat() == PIXEL_FORMAT.RGB565) {
                //    rgb = frame.getImage(i);
                //    break;
                }//if
            catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }//for

        /*rgb is now the Image object that we’ve used in the video*/
        return rgb;
    }

}
