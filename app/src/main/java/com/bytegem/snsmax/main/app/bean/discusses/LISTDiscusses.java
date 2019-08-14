package com.bytegem.snsmax.main.app.bean.discusses;

import com.bytegem.snsmax.common.bean.MBaseBean;

import java.util.ArrayList;

public class LISTDiscusses extends MBaseBean {
    private ArrayList<DiscussBean> data;

    public ArrayList<DiscussBean> getData() {
        return data;
    }

    public void setData(ArrayList<DiscussBean> data) {
        this.data = data;
    }
}
