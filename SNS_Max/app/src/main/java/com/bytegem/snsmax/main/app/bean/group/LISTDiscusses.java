package com.bytegem.snsmax.main.app.bean.group;

import com.bytegem.snsmax.common.bean.MBaseBean;

import java.util.ArrayList;

public class LISTDiscusses extends MBaseBean {
    private ArrayList<DiscussesBean> data;

    public ArrayList<DiscussesBean> getData() {
        return data;
    }

    public void setData(ArrayList<DiscussesBean> data) {
        this.data = data;
    }
}
