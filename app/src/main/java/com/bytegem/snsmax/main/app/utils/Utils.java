package com.bytegem.snsmax.main.app.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.view.inputmethod.InputMethodManager;

import com.bytegem.snsmax.main.app.Api;
import com.bytegem.snsmax.main.app.config.LocationCache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

import timber.log.Timber;

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

    public static String checkUrl(String path) {
        if (path == null || path.isEmpty()) return "";
        if (path.contains("http"))
            return path;
        else return Api.FILE_LOOK_DOMAIN + path;
    }

    public static Date parseServerTime(String serverTime, String format) {
        serverTime = serverTime.replace("T", " ");
        serverTime = serverTime.replace("Z", "");
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINESE);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        Date date = null;
        try {
            date = sdf.parse(serverTime);
        } catch (Exception e) {
            Timber.e(e, "");
        }
        return date;
    }

    public static String getUpdataTime(String time) {
        long freshTime = parseServerTime(time, null).getTime();
        int m = (int) ((System.currentTimeMillis() - freshTime) / 1000 / 60);
        if (m >= 1 && m < 60) {
            return m + "分钟前";
        } else if (m >= 60) {
            int h = m / 60;
            return h + "小时前";
        } else if (m > 7 * 60 * 24) {
            String[] times = time.split("T");
            if (times.length > 1)
                return times[0];
            else {
                int d = m / (60 * 24);
                return d + "天前";
            }
        } else if (m > 60 * 24) {
            int d = m / (60 * 24);
            return d + "天前";
        } else if (m == 0) {
            return "刚刚";
        } else return "";
    }

    public static Bitmap getNetVideoBitmap(String videoUrl) {
        Bitmap bitmap = null;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据url获取缩略图
            retriever.setDataSource(videoUrl, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }

    /**
     * 隐藏键盘的方法
     *
     * @param context
     */
    public static void hideKeyboard(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        imm.hideSoftInputFromWindow(context.getWindow().getDecorView().getWindowToken(), 0);
    }
}
