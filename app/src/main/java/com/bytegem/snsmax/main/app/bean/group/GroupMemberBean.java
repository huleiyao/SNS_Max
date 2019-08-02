package com.bytegem.snsmax.main.app.bean.group;

import com.bytegem.snsmax.common.bean.MBaseBean;

public class GroupMemberBean extends MBaseBean{
    private String nickname;
    private String role;
    private String joined_at;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getJoined_at() {
        return joined_at;
    }

    public void setJoined_at(String joined_at) {
        this.joined_at = joined_at;
    }
}
