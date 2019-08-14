package com.bytegem.snsmax.main.app.bean.discusses;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.main.app.bean.feed.MediaBean;
import com.bytegem.snsmax.main.app.bean.group.GroupBean;

public class DiscussBean extends MBaseBean {
    private int id;
    private int feeds_count;
    private int follower_count;
    private int popularity;
    private String title;
    private String desc;
    private MediaBean media;
    private GroupBean group;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFeeds_count() {
        return feeds_count;
    }

    public void setFeeds_count(int feeds_count) {
        this.feeds_count = feeds_count;
    }

    public int getFollower_count() {
        return follower_count;
    }

    public void setFollower_count(int follower_count) {
        this.follower_count = follower_count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public MediaBean getMedia() {
        return media;
    }

    public void setMedia(MediaBean media) {
        this.media = media;
    }

    public GroupBean getGroup() {
        return group;
    }

    public void setGroup(GroupBean group) {
        this.group = group;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }
}
