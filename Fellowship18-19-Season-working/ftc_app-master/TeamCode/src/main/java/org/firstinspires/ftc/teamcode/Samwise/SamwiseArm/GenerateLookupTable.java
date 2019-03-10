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
    private static final List listHeight = new ArrayList(Arrays.asList( 15.600269452067298));
    private static final double L1 = 21.5; //length between motorJoint1 and motorJoint2
    private static final double L2 = 21.75; //length between motorJoint2 and servoWrist
    private static final double MINIMUM_L3 = 15;
    private static final double INTERVAL = 0.5;

    public static void main(String[] args)
    {

        // calculate height
        double J2_ticks     = 459;
        double J3_ticks     = 2541;
        double J2Deg        = 211 - J2_ticks / 25.57;
        double J3Deg        = 48 + J3_ticks / 35.84;
        System.out.println("J2Deg: "+J2Deg);
        System.out.println("J3Deg: "+J3Deg);
        double J4Deg        = 180 - J3Deg - J2Deg + 90;
        double height_J3_J4 = L2 * Math.sin(Math.toRadians(J4Deg));
        double height_J3_J2 = L1 * Math.sin(Math.toRadians(J2Deg - 90));
        System.out.println("height_J3_J4: "+height_J3_J4);
        System.out.println("height_J3_J2: "+height_J3_J2);
        double pomHeight = height_J3_J2 - height_J3_J4;
        System.out.println("pomHeight: "+pomHeight);

        // calculate minimum L3
        double k_sqrd = Math.pow(L1, 2) + Math.pow(L2, 2) - Math.cos(Math.toRadians(J3Deg)) * 2 * L1 * L2;
        System.out.println("k_sqrd: " + k_sqrd);
        System.out.println("height sqrd: " + Math.pow(pomHeight, 2));
        double current_L3 = Math.sqrt(k_sqrd - Math.pow(pomHeight, 2));
        System.out.println("current_L3: " + current_L3);


/*

        //        try
        //        {
        //            File fout = new File("LaurenLookupTable.txt");
        //            fout.createNewFile();
        //            FileOutputStream fos = new FileOutputStream(fout);
        //
        //            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        //            bw.write("Height(inch)      Distance(inch)      	  J2 Angle(degree)     	 	  J3 Angle(degree)");
        //            bw.newLine();
        System.out.println("Height(inch)      Distance(inch)      	  J2 Angle(degree)     	 	  J3 Angle(degree)");
        double maximum_L3=0;
        for (int i = 0; i < listHeight.size(); i++)
        {
            double height = (double) listHeight.get(i);
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
                System.out.print("   " + height + " 	     	    ");
                System.out.print("{");
                System.out.print(L3 + "	,	");
                System.out.print(angle_J2 + ",		");
                System.out.println(angle_J3 + "},");
                //                    bw.write("   "+height + " 	     	    ");
                //                    bw.write("{");
                //                    bw.write(L3 + "	,	");
                //                    bw.write(angle_J2 + ",		");
                //                    bw.write(angle_J3 + "},");
                //                    bw.newLine();
            }
        }

        //            bw.close();
        //        }
        //        catch (IOException e)
        //        {
        //            e.printStackTrace();
        //        }
*/
    }
}


