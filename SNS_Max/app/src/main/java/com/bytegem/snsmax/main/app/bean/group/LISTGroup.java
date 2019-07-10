package com.bytegem.snsmax.main.app.bean.group;

import com.bytegem.snsmax.common.bean.MBaseBean;

import java.util.ArrayList;

public class LISTGroup extends MBaseBean{
    private ArrayList<GroupBean> data;

    public ArrayList<GroupBean> getData() {
        return data;
    }

    public void setData(ArrayList<GroupBean> data) {
        this.data = data;
    }
}
