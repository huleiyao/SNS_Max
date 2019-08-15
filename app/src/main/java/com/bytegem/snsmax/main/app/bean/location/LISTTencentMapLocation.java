package com.bytegem.snsmax.main.app.bean.location;

import com.bytegem.snsmax.common.bean.MBaseBean;

import java.util.ArrayList;

public class LISTTencentMapLocation extends MBaseBean {
    private ArrayList<TencentMapLocationBean> data;

    public ArrayList<TencentMapLocationBean> getData() {
        return data;
    }

    public void setData(ArrayList<TencentMapLocationBean> data) {
        this.data = data;
    }
}
