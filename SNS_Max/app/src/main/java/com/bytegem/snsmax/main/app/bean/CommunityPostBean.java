package com.bytegem.snsmax.main.app.bean;

import com.bytegem.snsmax.common.bean.MBaseBean;

import java.util.ArrayList;

public class CommunityPostBean extends MBaseBean {
    private int id;
    private String contents;
    private String created_at;
    private Geo geo;
    private CommunityMediaBean media;

    private User user;
    private CommunityGroupBean communityGroupBean;
    private ArrayList<String> images;

    public CommunityPostBean() {
        setUser(new User());
        setCommunityGroupBean(new CommunityGroupBean());
        setContents("我不喜欢主人公克服弱点，守护家人并拯救世界这样的情节，更想描述没有英雄，只有平凡人生活的，有点肮脏的世界忽然变得美好的瞬间。");
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

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    public CommunityMediaBean getMedia() {
        return media;
    }

    public void setMedia(CommunityMediaBean media) {
        this.media = media;
    }
}
