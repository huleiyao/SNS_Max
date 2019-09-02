package com.bytegem.snsmax.main.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.bytegem.snsmax.main.app.MApplication;
import com.bytegem.snsmax.main.app.bean.user.DATAUser;
import com.bytegem.snsmax.main.app.bean.user.UserBean;
import com.google.gson.Gson;

/**
 * 用户信息的工具类
 */
public class UserInfoUtils {

    private static DATAUser currentUser;

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
        currentUser = null;
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
        if (currentUser != null) {
            return currentUser;
        }
        //如果获取用户信息成功。那么保存到本地中
        String userinfo = getSharedPreferences()
                .getString("user_info", "");
        if (userinfo == null || "".equals(userinfo)) {
            return null;
        }
        return gson.fromJson(userinfo, DATAUser.class);
    }

    /**
     * 保存token和类型到本地
     *
     * @param token
     * @param tokenType
     */
    public static void setTokenAndType(String token, String tokenType) {
        SharedPreferences.Editor et = getSharedPreferences()
                .edit();
        if (token != null && !token.isEmpty()) {
            et.putString("token", token);
            MApplication.getInstance().setToken(token);
        }
        if (tokenType != null && !tokenType.isEmpty()) {
            et.putString("token_type", tokenType);
            MApplication.getInstance().setTokenType(tokenType);
        }
        et.apply();
    }

    /**
     * 读取本地的token和类型
     */
    public static String getTokenAndType() {
        SharedPreferences sp = getSharedPreferences();
        String token = sp.getString("token", "");
        String tokenType = sp.getString("token_type", "");
        return tokenType + " " + token;
    }

    /**
     * 读取本地的token
     */
    public static String getToken() {
        SharedPreferences sp = getSharedPreferences();
        String token = sp.getString("token", "");
        return token;
    }

    /**
     * 读取本地的token类型
     */
    public static String getTokenType() {
        SharedPreferences sp = getSharedPreferences();
        String token = sp.getString("token_type", "");
        return token;
    }

    /**
     * 检查是否已经登录了。T是，F不是有效登录
     */
    public static boolean isLogin() {
        SharedPreferences sp = getSharedPreferences();
        String token = sp.getString("token", "");
        String tokenType = sp.getString("token_type", "");
        return token != null && !"".equals(token) && tokenType != null && !"".equals(tokenType);
    }

    public static String getJsonUserData(UserBean dataUser, Gson gson) {
        String str = "";
        if (dataUser != null) {
            str = gson.toJson(dataUser);
        }
        return str;
    }

    //获取存储对象
    private static SharedPreferences getSharedPreferences() {
        return MApplication.getInstance().getSharedPreferences("user", Context.MODE_PRIVATE);
//        return PreferenceManager.getDefaultSharedPreferences(MApplication.getInstance());
    }

}
