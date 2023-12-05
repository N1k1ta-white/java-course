package bg.sofia.uni.fmi.mjt.trading;

import java.text.DecimalFormat;

public class Rounding {
    private static int power = 2;
    /**
     * Rounds numbers
     * @param num number
     * @return rounded number
     */
    public static double rounding(double num) {
        double scale = Math.pow(10, power);
        int temp = (int)(num * scale);
        double res = num * scale - temp;
        if (res >= 0.5)
            temp++;
        return (double)temp / scale;
    }
}
