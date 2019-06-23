package com.bytegem.snsmax.main.app.bean;

import com.bytegem.snsmax.common.bean.MBaseBean;

public class User extends MBaseBean {
    private String content;
    private String cover;

    private int id;
    private int exp;
    private int likes_count;
    private int followers_count;
    private int followings_count;
    private String phone_number;
    private String name;
    private String sex;//man/women/unknown
    private String created_at;

    public User(){
        setContent("毒鸡汤掌门人");
//        setName("一碗鲜美的鸡汤");
        setCover("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560157380928&di=a5fcba2094b5d96612a2a77b4873115e&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F9b671d17b52639d35e7c76c23f79fbabebe769d43140-xjB5Tw_fw658");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
