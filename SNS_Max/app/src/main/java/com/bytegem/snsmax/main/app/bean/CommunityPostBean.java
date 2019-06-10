package com.bytegem.snsmax.main.app.bean;

import com.bytegem.snsmax.common.bean.MBaseBean;

import java.util.ArrayList;

public class CommunityPostBean extends MBaseBean {
    private User user;
    private CommunityGroupBean communityGroupBean;
    private String content;
    private ArrayList<String> images;

    public CommunityPostBean() {
        setUser(new User());
        setCommunityGroupBean(new CommunityGroupBean());
        setContent("我不喜欢主人公克服弱点，守护家人并拯救世界这样的情节，更想描述没有英雄，只有平凡人生活的，有点肮脏的世界忽然变得美好的瞬间。");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CommunityGroupBean getCommunityGroupBean() {
        return communityGroupBean;
    }

    public void setCommunityGroupBean(CommunityGroupBean communityGroupBean) {
        this.communityGroupBean = communityGroupBean;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }
}
