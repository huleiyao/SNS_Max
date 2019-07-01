package com.bytegem.snsmax.common.utils;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.util.HashMap;

public class M {
    public static String getMapString(Object... parameter) {
        HashMap<Object, Object> map = new HashMap<>();
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
        return bytesToHexString(digest.digest());
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}
