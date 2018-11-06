package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

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
    GoldPosition getGoldPosition(TFObjectDetector tfod, CameraOrientation camRoate)
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
        // TODO: Need to add timeout to handle the case of never find.
        //TODO: Decide if we can wiggle the camera during autonomous sampling
        //TODO: Handle cases where detected objects are more than 3.
        float   tempLeft      = -Float.MAX_VALUE;
        int     goldNumber    = 0;
        boolean outOfBoundary = false;
        while (updatedRecognitions == null || updatedRecognitions.size() < 3 || goldNumber != 1 || outOfBoundary)
        {
            updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null)
            {
                if (updatedRecognitions.size() == 3)
                {
                    goldNumber = 0;
                    outOfBoundary = false;
                    for (Recognition recognition : updatedRecognitions)
                    {
                        //TODO: Remove if too strict. Removing will lower the confidence level.
                        //consider valid only when 1 gold is detected
                        if (recognition.getLabel().equals(LABEL_GOLD_MINERAL))
                        {
                            goldNumber++;
                        }

                        //TODO: Remove if too strict. Removing will lower the confidence level.
                        //consider valid only when recognition X is bigger than 0
                        switch (camRoate)
                        {
                            case ROTATE_0:
                                tempLeft = recognition.getLeft();
                                break;
                            case ROTATE_90:
                                tempLeft = recognition.getTop();
                                break;
                            case ROTATE_270:
                                tempLeft = recognition.getBottom();
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


        //recognition boundary
        int i = 0;
        for (Recognition recognition : updatedRecognitions)
        {
            System.out.println("recognition " + i + ":" + recognition.toString());
        }

        if (updatedRecognitions.size() == 3)
        {
            float goldMineralX    = -Float.MAX_VALUE;
            float silverMineral1X = -Float.MAX_VALUE;
            float silverMineral2X = -Float.MAX_VALUE;
            int   silverNumber    = 0;
            for (Recognition recognition : updatedRecognitions)
            {
                //get each left
                switch (camRoate)
                {
                    case ROTATE_0:
                        tempLeft = recognition.getLeft();
                        break;
                    case ROTATE_90:
                        tempLeft = recognition.getTop();
                        break;
                    case ROTATE_270:
                        tempLeft = recognition.getBottom();
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
                switch (camRoate)
                {
                    case ROTATE_270:
                        result = GoldPosition.RIGHT;
                        System.out.println("Gold Mineral Position: Right");
                        break;
                    case ROTATE_0:
                    case ROTATE_90:
                    default:
                        result = GoldPosition.LEFT;
                        System.out.println("Gold Mineral Position: Left");
                }
            }
            else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X)
            {
                switch (camRoate)
                {
                    case ROTATE_270:
                        result = GoldPosition.LEFT;
                        System.out.println("Gold Mineral Position: Left");
                        break;
                    case ROTATE_0:
                    case ROTATE_90:
                    default:
                        result = GoldPosition.RIGHT;
                        System.out.println("Gold Mineral Position: Right");
                }
            }
            else
            {
                result = GoldPosition.CENTER;
                System.out.println("Gold Mineral Position: Center");
            }
        }
        return result;
    }

}
