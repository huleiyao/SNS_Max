package com.bytegem.snsmax.main.mvp.model;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.main.app.bean.help.HelperBean;

import java.util.List;

public class HelperModel extends MBaseBean {

    private List<HelperBean> data;

    public List<HelperBean> getData() {
        return data;
    }

    public void setData(List<HelperBean> data) {
        this.data = data;
    }
}
