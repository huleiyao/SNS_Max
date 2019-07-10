package com.bytegem.snsmax.main.app.bean.feed;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.main.app.bean.feed.FeedBean;
import com.bytegem.snsmax.main.app.bean.list_data.Links;
import com.bytegem.snsmax.main.app.bean.list_data.Meta;

import java.util.ArrayList;

public class LISTFeeds extends MBaseBean {
    private ArrayList<FeedBean> data;
    private Links links;
    private Meta meta;

    public ArrayList<FeedBean> getData() {
        return data;
    }

    public void setData(ArrayList<FeedBean> data) {
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
