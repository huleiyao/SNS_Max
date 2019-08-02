package com.bytegem.snsmax.main.app.bean.feed;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.main.app.bean.user.UserBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FeedCommentBean extends MBaseBean {
    private int id;
    private String contents;
    private MediaBean media;
    @SerializedName("user")
    private UserBean userBean;
    private boolean is_child;
    private int likes_count;
    private int comments_count;
    private String created_at;//创建时间

    private ArrayList<FeedCommentBean> comments;

    public ArrayList<FeedCommentBean> getComments() {
        return comments;
    }

    public void setComments(ArrayList<FeedCommentBean> comments) {
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public MediaBean getMedia() {
        return media;
    }

    public void setMedia(MediaBean media) {
        this.media = media;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public boolean isIs_child() {
        return is_child;
    }

    public void setIs_child(boolean is_child) {
        this.is_child = is_child;
    }

    public int getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(int likes_count) {
        this.likes_count = likes_count;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
