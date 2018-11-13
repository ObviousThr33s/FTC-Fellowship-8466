package org.firstinspires.ftc.teamcode.Samwise.Autonomous.Drive;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

class CraterDepotUtil
{
    private static final CraterDepotUtil ourInstance = new CraterDepotUtil();

    static CraterDepotUtil getInstance()
    {
        return ourInstance;
    }

    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    private static final long TIMEOUT = 4000;

    private static final String VUFORIA_KEY = "Ae3YbWD/////AAABmUVFK6zEn0lFpmdsClff/lgOdFCD2VjMx7JZ6kx9s2KEMbcPTgOP3z1oUQl8LdCe75uZNfPs11Z3whBz260+kjUB+zLhpxGI/q5kbCvbqkhkh9VeK8PDZm/MP6z3xJPFWt0j7QCBsvakMFaBeymZLhpXD10IUcDD3XGM8TlKtJytMgnqB2HmLCqR1/eNjCncoRH/iGlLeB9SvsCOYKwRYemcH3/F75Hrg7jJcl9euneQ5DM7lV3upEZnw12UdlmRZr8FUJo/9bdN0ndl33HCxZT63fvMqaBVtuntufQFL8c4fImcckfcUCiRTcoIqaQq3Htl2LWgQC5OPHeuJ+HrxxR/FvekqUbltzuZM2Nxm0lM";


    private CraterDepotUtil()
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
    boolean isCrater (TFObjectDetector tfod)
    {
        boolean isCrater = false;

        // Can not continue if tfod is null
        if (tfod == null)
        {
            return isCrater;
        }

        // getUpdatedRecognitions() will return null if no new information is available since
        // the last time that call was made.
        List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
        //loop to find position
        int mineralNumber = 0;
        long startTime = System.currentTimeMillis();
        boolean isDepot = false;
        int depotCounter = 0;
        while ((updatedRecognitions == null || updatedRecognitions.size() <3) /*&& (System.currentTimeMillis()-startTime) < TIMEOUT*/ && !isDepot)
        {
            updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null)
            {
                mineralNumber = 0;
                    for (Recognition recognition : updatedRecognitions)
                    {
                        //consider valid only when 1 gold is detected
                        if (recognition.getLabel().equals(LABEL_GOLD_MINERAL) || recognition.getLabel().equals(LABEL_SILVER_MINERAL))
                        {
                            mineralNumber++;
                        }
                    }
                    if (mineralNumber == 3)
                    {
                        depotCounter++;
                        if (depotCounter >= 4)
                            isDepot = true;
                    }
            }
        }

        //Decide if crater or depot
        if (updatedRecognitions == null)
            return isCrater;
        if (mineralNumber > 3)
            isCrater = true;
        return isCrater;
    }

}
