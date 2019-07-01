package com.bytegem.snsmax.main.app.bean;

import com.bytegem.snsmax.common.bean.MBaseBean;

public class CommunityPostData extends MBaseBean{
    private CommunityPostBean data;

    public CommunityPostBean getData() {
        return data;
    }

    public void setData(CommunityPostBean data) {
        this.data = data;
    }
}
