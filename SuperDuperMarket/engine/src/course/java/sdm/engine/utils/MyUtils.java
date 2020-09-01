package course.java.sdm.engine.utils;

import java.text.DecimalFormat;

public class MyUtils {
    private static final DecimalFormat D2F = new DecimalFormat("#.##");

    public static String formatNumber(double num) {
        return D2F.format(num);
    }
    public static final String STRING_SEPARATOR = " | ";

}
