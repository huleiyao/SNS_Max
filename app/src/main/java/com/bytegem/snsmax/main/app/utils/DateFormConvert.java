package com.bytegem.snsmax.main.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateFormConvert {

    /**
     * 将UTC标准时间转为本地时间
     * @param utcStr
     */
    public static Date utc2LocalData(String utcStr){
        try {
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            df2.setTimeZone(TimeZone.getTimeZone("UTC"));
            return df2.parse(utcStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
