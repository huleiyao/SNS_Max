package com.bytegem.snsmax.main.app.bean.feed;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.main.app.bean.feed.FeedBean;

public class DataFeed extends MBaseBean{
    private FeedBean data;

    public FeedBean getData() {
        return data;
    }

    public void setData(FeedBean data) {
        this.data = data;
    }
}
