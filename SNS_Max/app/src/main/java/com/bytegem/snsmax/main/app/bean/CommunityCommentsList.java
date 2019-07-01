package com.bytegem.snsmax.main.app.bean;

import com.bytegem.snsmax.common.bean.MBaseBean;

import java.util.ArrayList;

public class CommunityCommentsList extends MBaseBean {
    private ArrayList<CommunityCommentBean> data;

    public ArrayList<CommunityCommentBean> getData() {
        return data;
    }

    public void setData(ArrayList<CommunityCommentBean> data) {
        this.data = data;
    }
}
