package com.bytegem.snsmax.main.app.bean.user;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.main.app.bean.list_data.Links;
import com.bytegem.snsmax.main.app.bean.list_data.Meta;

import java.util.ArrayList;

public class LISTUser extends MBaseBean {
    private ArrayList<UserBean> data;
    private Links links;
    private Meta meta;

    public ArrayList<UserBean> getData() {
        return data;
    }

    public void setData(ArrayList<UserBean> data) {
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
