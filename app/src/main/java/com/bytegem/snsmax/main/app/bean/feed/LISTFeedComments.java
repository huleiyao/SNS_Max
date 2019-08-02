package com.bytegem.snsmax.main.app.bean.feed;

import com.bytegem.snsmax.common.bean.MBaseBean;

import java.util.ArrayList;

public class LISTFeedComments extends MBaseBean {
    private ArrayList<FeedCommentBean> data;

    public ArrayList<FeedCommentBean> getData() {
        return data;
    }

    public void setData(ArrayList<FeedCommentBean> data) {
        this.data = data;
    }
}
