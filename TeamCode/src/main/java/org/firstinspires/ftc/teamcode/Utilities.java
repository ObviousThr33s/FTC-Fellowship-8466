package org.firstinspires.ftc.teamcode;

/**
 * Created by Joshua on 10/28/2017.
 */

public abstract class Utilities {

    static public class PowerLevels {
        public double powerFL = 0;
        public double powerFR = 0;
        public double powerBL = 0;
        public double powerBR = 0;
        PowerLevels (double FL, double FR, double BL, double BR) {
            powerFL = FL;
            powerFR = FR;
            powerBL = BL;
            powerBR = BR;
        }
    }

    static public PowerLevels NormalizePower(PowerLevels CurrentLevels, double MaxPower) {
        //normalize range to [-MaxPower,MaxPower]
        double localFL = CurrentLevels.powerFL;
        double localFR = CurrentLevels.powerFR;
        double localBL = CurrentLevels.powerBL;
        double localBR = CurrentLevels.powerBR;
        double highValue = Math.max(Math.abs(localFL), Math.max(Math.abs(localFR), Math.max(Math.abs(localBL), Math.abs(localBR))));
        if (highValue > MaxPower) {
            localFL = localFL / highValue * MaxPower;
            localFR = localFR / highValue * MaxPower;
            localBL = localBL / highValue * MaxPower;
            localBR = localBR / highValue * MaxPower;
        }
        return new PowerLevels(localFL, localFR, localBL, localBR);
    }

    static public double Calculate_S_Curve (double startReturnValue, double endReturnValue, double startValue, double endValue, double currentValue) {
        double valueRatio = Math.abs(endValue - startValue) / 12; // domain is between -6 and +6
        double powerRatio = Math.abs(endReturnValue - startReturnValue) / 1; // range is between 0 and 1
        double x = Math.abs(currentValue - startValue) / valueRatio - 6;
        double y = Math.pow(Math.E, x) / (Math.pow(Math.E, x) + 1);
        double result = 0;
        if (startReturnValue > endReturnValue) {
            result = startReturnValue - y * powerRatio;
        } else {
            result = startReturnValue + y * powerRatio;
        }
        return result;
    }
}
