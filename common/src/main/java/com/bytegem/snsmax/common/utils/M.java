package com.bytegem.snsmax.common.utils;

import android.content.Context;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class M {
    /* 音频文件扩展名称 */
    public static final String RECORD_EXT_NAME = ".acc";
    /* 音频上传时候的格式 */
    public static final String RECORD_WEB_SEND_FROM = "audio/" + RECORD_EXT_NAME;

    public static String getMapString(Object... parameter) {
        HashMap<Object, Object> map = new LinkedHashMap<>();
        for (int i = 0; i < parameter.length / 2; i++)
            map.put(parameter[i * 2], parameter[i * 2 + 1]);
//        if (PreferenceUtil.onlyLogibnKey != null && !PreferenceUtil.onlyLogibnKey.isEmpty()) {
//            map.put(PreferenceUtil.ONLY_LOGIN_KEY, PreferenceUtil.onlyLogibnKey);
//        }
        return new Gson().toJson(map);
    }

    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }

    public static File getTempFile(Context context, String mimeType) {
        File tempCover = null;
        try {
            switch (mimeType) {
                case "image/png":
                    tempCover = new File(AssetsFileUtils.copyBigDataBase(context, "png.png"));
                    break;
                case "image/jpeg":
                    tempCover = new File(AssetsFileUtils.copyBigDataBase(context, "jpg.jpg"));
                    break;
                case "image/jpg":
                    tempCover = new File(AssetsFileUtils.copyBigDataBase(context, "jpg.jpg"));
                    break;
                case RECORD_WEB_SEND_FROM:
                    tempCover = new File(AssetsFileUtils.copyBigDataBase(context, "mpeg.mpeg"));
                    break;
                case "image/gif":
                    tempCover = new File(AssetsFileUtils.copyBigDataBase(context, "gif.gif"));
                    break;
                case "video/mp4":
                    tempCover = new File(AssetsFileUtils.copyBigDataBase(context, "mp4.mp4"));
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempCover;
    }
}
