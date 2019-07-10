package com.bytegem.snsmax.main.app.bean.feed;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.main.app.bean.feed.FeedCommentBean;

public class DATAFeedComment extends MBaseBean {
    FeedCommentBean data;

    public FeedCommentBean getData() {
        return data;
    }

    public void setData(FeedCommentBean data) {
        this.data = data;
    }
}
