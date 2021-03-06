package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Vision;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.AbstractPhysical.Vision;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.firstinspires.ftc.robotcore.external.tfod.TfodRoverRuckus;

public class SamwiseVision extends Vision {
    /**
     * on screen distance btwn samples and camera. Must be calibrated!!!
     */
    private final float sampleDistanceNear = 0;
    private final float sampleDistanceFar = 160;

    private final double ratio = .35;
    private final double confidence = 0.65;

    // private GoldPosition goldPos = GoldPosition.UNKNOWN;
    private boolean crater = false;

    private static final long TIMEOUT = 3000;
    private static final String VUFORIA_KEY = "Ae3YbWD/////AAABmUVFK6zEn0lFpmdsClff/lgOdFCD2VjMx7JZ6kx9s2KEMbcPTgOP3z1oUQl8LdCe75uZNfPs11Z3whBz260+kjUB+zLhpxGI/q5kbCvbqkhkh9VeK8PDZm/MP6z3xJPFWt0j7QCBsvakMFaBeymZLhpXD10IUcDD3XGM8TlKtJytMgnqB2HmLCqR1/eNjCncoRH/iGlLeB9SvsCOYKwRYemcH3/F75Hrg7jJcl9euneQ5DM7lV3upEZnw12UdlmRZr8FUJo/9bdN0ndl33HCxZT63fvMqaBVtuntufQFL8c4fImcckfcUCiRTcoIqaQq3Htl2LWgQC5OPHeuJ+HrxxR/FvekqUbltzuZM2Nxm0lM";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    public enum GoldPosition {

        LEFT, CENTER, RIGHT, UNKNOWN;
    }

    enum CameraOrientation {
        ROTATE_0, ROTATE_90;
    }
    //CameraOrientation camRotate = CameraOrientation.ROTATE_90;

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    public void init(HardwareMap hardwareMap) {

        //initialize tensorflow object detector
        if (!ClassFactory.getInstance().canCreateTFObjectDetector()) {
            System.out.println("Sorry! This device is not compatible with TFOD");
        }

        //initialize Vuforia
        initVuforia();

        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        //TODO: Adjust confidence value
        tfodParameters.minimumConfidence = confidence;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TfodRoverRuckus.TFOD_MODEL_ASSET, TfodRoverRuckus.LABEL_GOLD_MINERAL, TfodRoverRuckus.LABEL_SILVER_MINERAL);

        //turn on camera LED
        com.vuforia.CameraDevice.getInstance().setFlashTorchMode(true);
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    public void activate() {
        tfod.activate();
    }

    public void deactivate() {
        com.vuforia.CameraDevice.getInstance().setFlashTorchMode(false);
        tfod.deactivate();
    }

    public void shutdown() {
        tfod.shutdown();
    }

    /**
     * returns left, center, right, unknown
     * Assumption: ONLY the middle and right side minerals shown in the camera view
     */
    public GoldPosition getGoldPosition() {

        // Can not continue if tfod is null
        if (tfod == null) {
            return GoldPosition.UNKNOWN;
        }

        // getUpdatedRecognitions() will return null if no new information is available since
        // the last time that call was made.
        List<Recognition> updatedRecognitions = null;

        long startTime = System.currentTimeMillis();
        List<Recognition> samples = null;

        //find the reference object :


        while ((samples == null || samples.size() != 2) && (System.currentTimeMillis() - startTime) < TIMEOUT) {
            updatedRecognitions = tfod.getUpdatedRecognitions();

            System.out.println("==> minerals found :" + updatedRecognitions);

            // ONLY the middle and right side minerals shown in the camera view
            if (updatedRecognitions != null && updatedRecognitions.size() >= 2) {
                samples = getSamples(updatedRecognitions);
            }
            //idle();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (samples == null || samples.size() != 2) {
            return GoldPosition.UNKNOWN;
        }

        /* this detection is not reliable
         if (updatedRecognitions.size()>2) {
            // This is very very likely the crater side
            this.crater = true;
         }*/

        //Sort samples from right to left
        Collections.sort(samples, new SortByBottom());

        // ONLY should have two minerals
        System.out.println("==>Sample position right :" + samples.get(0));
        System.out.println("==>Sample position middle :" + samples.get(1));

        if (samples.get(0).getLabel().equals(TfodRoverRuckus.LABEL_GOLD_MINERAL)) {
            return GoldPosition.RIGHT;
        }

        if (samples.get(1).getLabel().equals(TfodRoverRuckus.LABEL_GOLD_MINERAL)) {
            return GoldPosition.CENTER;
        }

        return GoldPosition.LEFT;

    }

    public boolean isCrater() {
        return crater;
    }

    public void setCrater(boolean isCrater) {
        this.crater = isCrater;
    }

    /**
     * Sort by getBottom()
     */
    class SortByBottom implements Comparator<Recognition> {

        // Used for sorting in ascending order of
        // roll number
        public int compare(Recognition a, Recognition b) {
            float diff = a.getBottom() - b.getBottom();
            if (diff < 0) {
                return 1;
            } else if (diff > 0) {
                return -1;
            }
            return 0;
        }
    }

    /**
     * Sort by getLeft()
     */
    class SortByLeft implements Comparator<Recognition> {

        // Used for sorting in ascending order of
        // roll number
        public int compare(Recognition a, Recognition b) {
            float diff = a.getLeft() - b.getLeft();
            if (diff < 0) {
                return -1;
            } else if (diff > 0) {
                return 1;
            }
            return 0;
        }
    }

    /**
     * Sort by getRight()
     */
    class SortByRight implements Comparator<Recognition> {

        // Used for sorting in ascending order of
        // roll number
        public int compare(Recognition a, Recognition b) {
            float diff = a.getRight() - b.getRight();
            if (diff < 0) {
                return 1;
            } else if (diff > 0) {
                return -1;
            }
            return 0;
        }
    }

    /**
     * return nearest two minerals.
     * Assumption: ONLY 2 sample minerals shows in the view
     *
     * @param recognitions
     * @return
     */
    private List<Recognition> getSamples(List<Recognition> recognitions) {

        System.out.println("==> --- before sorting --- size: " + recognitions.size());
        for (Recognition recog : recognitions) {
            System.out.println("==>" + recog);
        }

        Collections.sort(recognitions, new SortByLeft());

        System.out.println("==> --- after sorting --- size: " + recognitions.size());
        for (Recognition recog : recognitions) {
            System.out.println("==>" + recog);
        }
        /**
         * Consider ONLY first two minerals in the list
         */
        float firstBoxSize = recognitions.get(0).getRight() - recognitions.get(0).getLeft();
        float secondBoxSize = recognitions.get(1).getRight() - recognitions.get(1).getLeft();

        /**
         * ONLY add to samples when the two minerals:
         * 1. are similar in size
         * 2. are not too close together (eliminate double images)
         * 3. are within the accepted distance (excluded the crater ones)
         */
        double sizeRatio = secondBoxSize / firstBoxSize;
        if (sizeRatio > 1 + ratio || sizeRatio < 1 - ratio) {
            System.out.println("==> the two objects are too different in size. Their ratio = " + sizeRatio);
            return null;
        }

        double overlapRatio = recognitions.get(0).getTop() / recognitions.get(1).getTop();
        if (overlapRatio < 1.5 && overlapRatio > 0.7) {
            System.out.println("==> the two objects are too close to each other. Their overlap ratio = " + overlapRatio);
            return null;
        }

        /*
        if (recognitions.get(0).getLeft() > sampleDistanceFar ||
                recognitions.get(0).getLeft() < sampleDistanceNear ||
                recognitions.get(1).getLeft() > sampleDistanceFar ||
                recognitions.get(1).getLeft() < sampleDistanceNear) {
            System.out.println("==> the two objects are too close or too far from the sample distance.");
            return null;
        }*/


        List<Recognition> samples = new ArrayList<Recognition>();
        samples.add(recognitions.get(0));
        samples.add(recognitions.get(1));
        //System.out.println("==>==> This is added to samples : " + recog);

        return samples; //recognitions.subList(0,2);

    }

    public Recognition getReference() {


        // Can not continue if tfod is null
        if (tfod == null) {
            return null;
        }

        // getUpdatedRecognitions() will return null if no new information is available since
        // the last time that call was made.
        List<Recognition> recognitions = null;

        long startTime = System.currentTimeMillis();

        //find the reference object :
        while ((System.currentTimeMillis() - startTime) < TIMEOUT) {

            recognitions = tfod.getUpdatedRecognitions();

            //System.out.println("==> minerals found :" + recognitions);

            if (recognitions == null || recognitions.size() == 0) {
                continue;
            }

            if (recognitions.size() == 1) {
                if (recognitions.get(0).getLeft() > sampleDistanceFar ||
                        recognitions.get(0).getLeft() < sampleDistanceNear) {
                    System.out.println("==> the two objects are too close or too far from the sample distance.");
                    //recognitions = null; //restart
                    continue;
                }
                System.out.println("==> only one mineral found");
                return recognitions.get(0);
            }


            Collections.sort(recognitions, new SortByLeft());

            System.out.println("==> --- reference after sorting --- size: " + recognitions.size());
            for (Recognition recog : recognitions) {
                System.out.println("==>" + recog);
            }

            /**
             * Consider ONLY first two minerals in the list
             */
            float firstBoxSize = recognitions.get(0).getRight() - recognitions.get(0).getLeft();
            float secondBoxSize = recognitions.get(1).getRight() - recognitions.get(1).getLeft();

            /**
             * ONLY add to samples when the two minerals:
             * 1. are similar in size
             * 2. are not too close together (eliminate double images)
             * 3. are within the accepted distance (excluded the crater ones)
             */
            double sizeRatio = secondBoxSize / firstBoxSize;
            if (sizeRatio > 1 + ratio || sizeRatio < 1 - ratio) {
                System.out.println("==> the two objects are too different in size. Their ratio = " + sizeRatio);
                continue;
            }

            double overlapRatio = recognitions.get(0).getTop() / recognitions.get(1).getTop();
            if (overlapRatio < 1.5 && overlapRatio > 0.7) {
                System.out.println("==> the two objects are too close to each other. Their overlap ratio = " + overlapRatio);
                continue;
            }

            if (recognitions.get(0).getLeft() > sampleDistanceFar ||
                    recognitions.get(0).getLeft() < sampleDistanceNear ||
                    recognitions.get(1).getLeft() > sampleDistanceFar ||
                    recognitions.get(1).getLeft() < sampleDistanceNear) {
                System.out.println("==> the two objects are too close or too far from the sample distance.");
                continue;
            }


            if (recognitions.get(0).getTop() < recognitions.get(1).getTop()) {
                return recognitions.get(1);
            } else {
                return recognitions.get(0);
            }
        }

        return null;
    }

}