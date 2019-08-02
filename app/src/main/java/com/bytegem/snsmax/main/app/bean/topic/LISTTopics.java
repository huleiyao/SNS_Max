package com.bytegem.snsmax.main.app.bean.topic;

import com.bytegem.snsmax.common.bean.MBaseBean;

import java.util.ArrayList;

public class LISTTopics extends MBaseBean {
    private ArrayList<TopicBean> data;

    public ArrayList<TopicBean> getData() {
        return data;
    }

    public void setData(ArrayList<TopicBean> data) {
        this.data = data;
    }
}
