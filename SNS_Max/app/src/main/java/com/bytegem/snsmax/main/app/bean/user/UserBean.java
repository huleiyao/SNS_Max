package com.bytegem.snsmax.main.app.bean.user;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.main.app.bean.group.GroupMemberBean;

public class UserBean extends MBaseBean {
    private String avatar;
    private int id;
    private int exp;
    private int likes_count;
    private int followers_count;//粉丝
    private int followings_count;//关注的人
    private String name;
    private String sex;//man/women/unknown
    private String created_at;

    private String phone_number;

    private GroupMemberBean group_member;//圈子内用户身份信息

    public UserBean() {
//        setContent("毒鸡汤掌门人");
//        setName("一碗鲜美的鸡汤");
//        setCover("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560157380928&di=a5fcba2094b5d96612a2a77b4873115e&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F9b671d17b52639d35e7c76c23f79fbabebe769d43140-xjB5Tw_fw658");
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(int likes_count) {
        this.likes_count = likes_count;
    }

    public int getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(int followers_count) {
        this.followers_count = followers_count;
    }

    public int getFollowings_count() {
        return followings_count;
    }

    public void setFollowings_count(int followings_count) {
        this.followings_count = followings_count;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public GroupMemberBean getGroup_member() {
        return group_member;
    }

    public void setGroup_member(GroupMemberBean group_member) {
        this.group_member = group_member;
    }
}
