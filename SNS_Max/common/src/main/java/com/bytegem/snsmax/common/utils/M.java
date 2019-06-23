package com.bytegem.snsmax.common.utils;

import com.google.gson.Gson;

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
}
