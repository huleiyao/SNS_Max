package com.bytegem.snsmax.main.app.bean;

import com.bytegem.snsmax.common.bean.MBaseBean;

import java.util.ArrayList;

public class CommunityPostList extends MBaseBean {
    private ArrayList<CommunityPostBean> data;
    private Links links;
    private Meta meta;

    public ArrayList<CommunityPostBean> getData() {
        return data;
    }

    public void setData(ArrayList<CommunityPostBean> data) {
        this.data = data;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
