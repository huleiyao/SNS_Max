package com.bytegem.snsmax.main.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.bytegem.snsmax.main.app.MApplication;
import com.bytegem.snsmax.main.app.bean.user.DATAUser;
import com.google.gson.Gson;

/**
 * 用户信息的工具类
 */
public class UserInfoUtils {

    /**
     * 保存用户信息
     *
     * @param user
     * @param gson
     */
    public static void saveUserInfo(DATAUser user, Gson gson) {
        if (user.getData() == null) {
            return;
        }
        //如果获取用户信息成功。那么保存到本地中
        getSharedPreferences()
                .edit()
                .putString("user_info", gson.toJson(user))
                .apply();
    }

    /**
     * 读取当前保存的用户信息
     *
     * @param gson
     */
    public static DATAUser getUserInfo(Gson gson) {
        //如果获取用户信息成功。那么保存到本地中
        String userinfo = getSharedPreferences()
                .getString("user_info", "");
        if (userinfo == null || "".equals(userinfo)) {
            return null;
        }
        return gson.fromJson(userinfo, DATAUser.class);
    }

    //获取存储对象
    private static SharedPreferences getSharedPreferences() {
        return MApplication.getInstance().getSharedPreferences("user", Context.MODE_PRIVATE);
//        return PreferenceManager.getDefaultSharedPreferences(MApplication.getInstance());
    }

}
