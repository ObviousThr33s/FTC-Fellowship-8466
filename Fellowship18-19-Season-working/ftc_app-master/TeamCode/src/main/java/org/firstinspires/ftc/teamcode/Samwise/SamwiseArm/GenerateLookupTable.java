package org.firstinspires.ftc.teamcode.Samwise.SamwiseArm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenerateLookupTable
{
    private static final List listHeight = new ArrayList(Arrays.asList(5, 6, 7));
    private static final double L1 = 24.09; //length between motorJoint1 and motorJoint2
    private static final double L2 = 27.75; //length between motorJoint2 and servoWrist
    private static final double MINIMUM_L3 = 20;
    private static final double INTERVAL = 0.25;

    public static void main(String[] args)
    {
        double maximum_L3 = 0;
        try
        {
            File fout = new File("LaurenLookupTable.txt");
            fout.createNewFile();
            FileOutputStream fos = new FileOutputStream(fout);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write("Height(inch)      Distance(inch)      	  J2 Angle(degree)     	 	  J3 Angle(degree)");
            bw.newLine();
            for (int i = 0; i < listHeight.size(); i++)
            {
                int height = (int) listHeight.get(i);
                //calculate maximum
                maximum_L3 = Math.sqrt((L1 + L2) * (L1 + L2) - height * height);
                int    number_of_lines = (int) ((maximum_L3 - MINIMUM_L3) / INTERVAL);
                double k_sqrd          = 0;
                double angle_J2, angle_J2_1, angle_J2_2;
                double angle_J3;
                double L3;
                for (int j = 0; j < number_of_lines; j++)
                {
                    L3 = MINIMUM_L3 + INTERVAL * j;
                    k_sqrd = L3 * L3 + height * height;
                    angle_J3 = Math.toDegrees(Math.acos((L1 * L1 + L2 * L2 - k_sqrd) / (2 * L1 * L2)));
                    angle_J2_2 = Math.toDegrees(Math.asin(height / L3));
                    angle_J2_1 = Math.toDegrees(Math.acos((L1 * L1 + k_sqrd - L2 * L2) / (2 * L1 * Math.sqrt(k_sqrd))));
                    angle_J2 = angle_J2_1 + angle_J2_2 + 90;
                    bw.write("   "+height + " 	     	    ");
                    bw.write(L3 + "		");
                    bw.write(angle_J2 + "		");
                    bw.write(angle_J3 + "");
                    bw.newLine();
                }
            }

            bw.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}


