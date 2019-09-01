package com.bytegem.snsmax.main.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.bytegem.snsmax.main.app.MApplication;
import com.bytegem.snsmax.main.app.bean.feed.FeedBean;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class FeedsInfoUtils {
    static List<FeedBean> feedBeans = new ArrayList<>();

    /**
     * 保存浏览动态信息
     *
     * @param feed
     * @param gson
     */
    public static void saveUserInfo(FeedBean feed, Gson gson) {
        if (forFeedBean(feedBeans,feed)){
            feedBeans.add(feed);
            //如果获取用户信息成功。那么保存到本地中
            getSharedPreferences()
                    .edit()
                    .putString("feed_list_info", gson.toJson(feedBeans))
                    .apply();
        }
    }

    /**
     * 返还添加的feed JSON
     *
     * @return
     */
    public static String getFeedInfo() {
        String feedInfo = getSharedPreferences()
                .getString("feed_list_info", "");
        if (feedInfo.length() > 0) {
            return feedInfo;
        }
        return null;
    }

    /**
     * 返还添加的集合
     *
     * @return
     */
    public static ArrayList<FeedBean> getFeedInfoBeans() {
        String feedInfo = getFeedInfo();
        ArrayList<FeedBean> feedBeans = new ArrayList<>();
        if(feedInfo == null || "".equals(feedInfo)){
            return feedBeans;
        }
        try {
            FeedBean[] fbs = new Gson().fromJson(feedInfo,FeedBean[].class);
            for (FeedBean fb : fbs) {
                feedBeans.add(fb);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return feedBeans;
    }


    //获取存储对象
    private static SharedPreferences getSharedPreferences() {
        return MApplication.getInstance().getSharedPreferences("feed", Context.MODE_PRIVATE);
    }


    private static Boolean forFeedBean(List<FeedBean> list, FeedBean feed) {
        if (list.size()==30){
            list.remove(0);
        }
        if (list.size() > 0 && feed != null) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId() == feed.getId()){
                    return false;
                }
            }
        }
        return true;
    }

}
