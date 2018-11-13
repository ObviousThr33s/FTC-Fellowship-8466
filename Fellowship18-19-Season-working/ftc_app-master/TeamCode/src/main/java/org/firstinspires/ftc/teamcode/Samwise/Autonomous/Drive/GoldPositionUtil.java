package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.ArrayList;
import java.util.List;

class GoldPositionUtil
{
    private static final GoldPositionUtil ourInstance = new GoldPositionUtil();

    static GoldPositionUtil getInstance()
    {
        return ourInstance;
    }

    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    private static final long TIMEOUT = 4000;
    private static final String VUFORIA_KEY = "Ae3YbWD/////AAABmUVFK6zEn0lFpmdsClff/lgOdFCD2VjMx7JZ6kx9s2KEMbcPTgOP3z1oUQl8LdCe75uZNfPs11Z3whBz260+kjUB+zLhpxGI/q5kbCvbqkhkh9VeK8PDZm/MP6z3xJPFWt0j7QCBsvakMFaBeymZLhpXD10IUcDD3XGM8TlKtJytMgnqB2HmLCqR1/eNjCncoRH/iGlLeB9SvsCOYKwRYemcH3/F75Hrg7jJcl9euneQ5DM7lV3upEZnw12UdlmRZr8FUJo/9bdN0ndl33HCxZT63fvMqaBVtuntufQFL8c4fImcckfcUCiRTcoIqaQq3Htl2LWgQC5OPHeuJ+HrxxR/FvekqUbltzuZM2Nxm0lM";


    private GoldPositionUtil()
    {
    }


    public String getVuforiaKey()
    {
        return VUFORIA_KEY;
    }

    /**
     * Take 50 samples or 1 second timeout to determine the gold location. Considering only the closest three objects if more thant 3 are detected.
     *
     * @return
     */
    GoldPosition getGoldPosition(TFObjectDetector tfod, CameraOrientation camRotate)
    {
        GoldPosition result = GoldPosition.UNKNOWN;

        // Can not continue if tfod is null
        if (tfod == null)
        {
            return result;
        }

        // getUpdatedRecognitions() will return null if no new information is available since
        // the last time that call was made.
        List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
        //loop to find position
        //TODO: Decide if we can move the camera during autonomous sampling
        float   tempLeft      = -Float.MAX_VALUE;
        int     goldNumber    = 0;
        boolean outOfBoundary = false;
        long startTime = System.currentTimeMillis();
        List<Recognition> nearestMinerals = null;
        while ((updatedRecognitions == null || updatedRecognitions.size() < 3 || goldNumber != 1 || outOfBoundary)) //&& (System.currentTimeMillis()-startTime) < TIMEOUT )
        {
            updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null)
            {
                nearestMinerals = getNearestMinerals(updatedRecognitions, camRotate);
                if (nearestMinerals.size() == 3)
                {
                    goldNumber = 0;
                    outOfBoundary = false;
                    for (Recognition recognition : nearestMinerals)
                    {
                        //TODO: Remove if too strict. Removing will lower the confidence level.
                        //consider valid only when 1 gold is detected
                        if (recognition.getLabel().equals(LABEL_GOLD_MINERAL))
                        {
                            goldNumber++;
                        }

                        //TODO: Remove if too strict. Removing will lower the confidence level.
                        //consider valid only when recognition X is bigger than 0
                        switch (camRotate)
                        {
                            case ROTATE_0:
                                tempLeft = recognition.getLeft();
                                break;
                            case ROTATE_90:
                                tempLeft = recognition.getTop();
                                break;
                            default:
                        }
                        if (tempLeft < 0)
                        {
                            outOfBoundary = true;
                        }
                    }
                }
            }
        }

        if (nearestMinerals == null)
        {
            return result;
        }
        //recognition boundary
        int i = 1;
        for (Recognition recognition : nearestMinerals)
        {
            System.out.println("recognition " + i + ":" + recognition.toString());
        }

        if (nearestMinerals.size() == 3)
        {
            float goldMineralX    = -Float.MAX_VALUE;
            float silverMineral1X = -Float.MAX_VALUE;
            float silverMineral2X = -Float.MAX_VALUE;
            int   silverNumber    = 0;
            for (Recognition recognition : nearestMinerals)
            {
                //get each left
                switch (camRotate)
                {
                    case ROTATE_0:
                        tempLeft = recognition.getLeft();
                        break;
                    case ROTATE_90:
                        tempLeft = recognition.getTop();
                        break;
                    default:
                }

                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL))
                {
                    goldMineralX = tempLeft;
                }
                else
                {
                    if (silverNumber == 0)
                    {
                        silverNumber++;
                        silverMineral1X = tempLeft;
                    }
                    else
                    {
                        silverMineral2X = tempLeft;
                    }
                }
            }
            System.out.println("goldMineralX: " + goldMineralX + "; silverMineral1X: " + silverMineral1X + "; silverMineral2X: " + silverMineral2X);
            if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X)
            {
                        result = GoldPosition.LEFT;
                        System.out.println("Gold Mineral Position: Left");
            }
            else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X)
            {
                        result = GoldPosition.RIGHT;
                        System.out.println("Gold Mineral Position: Right");
            }
            else
            {
                result = GoldPosition.CENTER;
                System.out.println("Gold Mineral Position: Center");
            }
        }
        return result;
    }

    /**
     * Get the nearest three minerals from the list of recognized objects passed in
     * @param recognitions
     * @return
     */
    private List<Recognition> getNearestMinerals(List<Recognition> recognitions, CameraOrientation camRotate)
    {
        List<Recognition> result = new ArrayList<Recognition>();
        if (recognitions == null || recognitions.size() == 0)
            return result;
        // Identify the one nearest mineral
        // save the nearest position (bottom)
        // save the diameter of this nearest mineral
        float nearestMineralDiameter = 0;
        float nearestMineralBottom = 0;
        switch (camRotate)
        {
            case ROTATE_0:
                nearestMineralBottom = recognitions.get(0).getBottom();
                nearestMineralDiameter = recognitions.get(0).getBottom()-recognitions.get(0).getTop();
                break;
            case ROTATE_90:
                nearestMineralBottom = recognitions.get(0).getRight();
                nearestMineralDiameter = recognitions.get(0).getRight()-recognitions.get(0).getLeft();
                break;
        }
        float tempBottom = 0;
        for (Recognition recognition: recognitions)
        {
            if (recognition.getLabel().equals(LABEL_GOLD_MINERAL) || recognition.getLabel().equals(LABEL_SILVER_MINERAL))
            {
                switch (camRotate)
                {
                    case ROTATE_0:
                        tempBottom = recognition.getBottom();
                        break;
                    case ROTATE_90:
                        tempBottom = recognition.getRight();
                        break;
                }
                switch (camRotate)
                {
                    case ROTATE_0:
                        if (nearestMineralBottom < tempBottom)
                        {
                            nearestMineralBottom = tempBottom;
                            nearestMineralDiameter = recognition.getBottom()-recognition.getTop();
                        }
                        break;
                    case ROTATE_90:
                        if (nearestMineralBottom > tempBottom)
                        {
                            nearestMineralBottom = tempBottom;
                            nearestMineralDiameter = recognition.getRight()-recognition.getLeft();
                        }
                }

            }
        }

        System.out.println("Nearest Mineral Bottom: "+nearestMineralBottom);
        System.out.println("Nearest Mineral Diameter: "+nearestMineralDiameter);

        //find all (should be just 3) the minerals that's about the same height as the nearest one.
        //Giving room of twice the diameter of the nearest mineral
        for (Recognition recognition: recognitions)
        {
            switch (camRotate)
            {
                case ROTATE_0:
                    tempBottom = recognition.getBottom();
                    break;
                case ROTATE_90:
                    tempBottom = recognition.getRight();
                    break;
            }

           if ( Math.abs(nearestMineralBottom-tempBottom) < (2*nearestMineralDiameter))
            {
                result.add(recognition);
            }
        }

        int i = 0;
        for (Recognition recognition: result)
        {
            System.out.println("Nearest recognition "+i++ + recognition);
        }

        return result;
    }
}
