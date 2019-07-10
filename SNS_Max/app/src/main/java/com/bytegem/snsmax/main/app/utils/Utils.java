package com.bytegem.snsmax.main.app.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.view.inputmethod.InputMethodManager;

import com.bytegem.snsmax.main.app.Api;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.HashMap;

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
