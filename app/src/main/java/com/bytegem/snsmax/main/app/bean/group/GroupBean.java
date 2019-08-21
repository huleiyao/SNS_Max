package com.bytegem.snsmax.main.app.bean.group;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.main.app.bean.feed.FeedBean;
import com.bytegem.snsmax.main.app.bean.user.UserBean;

import java.util.ArrayList;

public class GroupBean extends MBaseBean {
    private int id;
    private int popularity;
    private int members_count;

    private boolean is_private;
    private boolean has_joined;//是否已经加入圈子

    private String avatar;
    private String name;
    private String desc;
    private String speak;
    private String created_at;
    private String updated_at;
    private UserBean group_member;//发现页返回的用户信息数据
    private UserBean creator;
    private GroupMemberBean requester;//当前用户在圈子下面的信息，未加入则不存在！
    private ArrayList<UserBean> preview_members;//用户信息列表，第一个是圈主无需返回。第二个是已经加入的当前用户，补足五条
    private ArrayList<FeedBean> feeds;

    public GroupBean() {
    }

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

    public int getMembers_count() {
        return members_count;
    }

    public void setMembers_count(int members_count) {
        this.members_count = members_count;
    }

    public boolean isIs_private() {
        return is_private;
    }

    public void setIs_private(boolean is_private) {
        this.is_private = is_private;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSpeak() {
        return speak;
    }

    public void setSpeak(String speak) {
        this.speak = speak;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public ArrayList<FeedBean> getFeeds() {
        return feeds;
    }

    public void setFeeds(ArrayList<FeedBean> feeds) {
        this.feeds = feeds;
    }

    public boolean isHas_joined() {
        return has_joined;
    }

    public void setHas_joined(boolean has_joined) {
        this.has_joined = has_joined;
    }

    public UserBean getCreator() {
        return creator;
    }

    public void setCreator(UserBean creator) {
        this.creator = creator;
    }

    public GroupMemberBean getRequester() {
        return requester;
    }

    public void setRequester(GroupMemberBean requester) {
        this.requester = requester;
    }

    public ArrayList<UserBean> getPreview_members() {
        return preview_members;
    }

    public void setPreview_members(ArrayList<UserBean> preview_members) {
        this.preview_members = preview_members;
    }
}
