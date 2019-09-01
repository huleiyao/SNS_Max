package com.bytegem.snsmax.main.app.bean.topic;

import com.bytegem.snsmax.common.bean.MBaseBean;

public class TopicBean extends MBaseBean {
    private int id;
    private int popularity;
    private String name;
    private String avatar;
    private String bg;
    private String created_at;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
