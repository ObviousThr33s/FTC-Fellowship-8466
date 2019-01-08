package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import org.firstinspires.ftc.teamcode.Samwise.SamwiseArm.LookupRecord;

public class LaurenLookupTable
{
    double[][] lookupTable;

    public LookupRecord search(int height, double j2Angle)
    {
        double distance = 0;
        double j3Angle  = 0;
        int    j        = 2;
        int    minIndex = 0;
        int    maxIndex = lookupTable.length - 1;

        LookupRecord lookupRecord = this.recursiveSearch(height, j2Angle, minIndex, maxIndex);

        return lookupRecord;
    }

    private LookupRecord recursiveSearch(int height, double j2Angle, int minIndex, int maxIndex)
    {
        int    midIndex = Math.round((maxIndex + minIndex) / 2);
        double value    = lookupTable[midIndex][2];
        if (Math.abs(maxIndex - minIndex) == 1)
        {
            j2Angle = lookupTable[minIndex][2];

            //TODO

            return null;
        }
        else
        {
            if (j2Angle == value)
            {
                double       j3Angle      = lookupTable[midIndex][3];
                double       distance     = lookupTable[midIndex][1];

                LookupRecord lookupRecord = new LookupRecord(height, distance, j2Angle, j3Angle);
                return lookupRecord;
            }
            else
            {
                if (j2Angle > value)
                {
                    maxIndex = midIndex - 1;
                }
                else
                {
                    minIndex = midIndex + 1;
                }

                LookupRecord lookupRecord = this.recursiveSearch(height, j2Angle, minIndex, maxIndex);
                return lookupRecord;
            }
        }
    }
}