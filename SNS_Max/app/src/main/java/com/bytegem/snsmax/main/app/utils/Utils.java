package com.bytegem.snsmax.main.app.utils;

import java.math.RoundingMode;
import java.text.NumberFormat;

public class Utils {
    public static String getNumberIfPeople(int number) {
        String unit = "";
        double num;
        if (number > 10000) {
            unit = "w";
            num = number / 10000d;
        } else if (number > 1000) {
            unit = "k";
            num = number / 1000d;
        } else return number + "";
        NumberFormat nf = NumberFormat.getNumberInstance();
        // 保留两位小数
        nf.setMaximumFractionDigits(1);
        // 如果不需要四舍五入，可以使用RoundingMode.DOWN
        nf.setRoundingMode(RoundingMode.UP);
        return nf.format(num) + unit;
    }
}
