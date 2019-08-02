package com.bytegem.snsmax.main.mvp.ui.adapter;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.Api;
import com.bytegem.snsmax.main.app.bean.feed.FeedBean;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.bytegem.snsmax.main.mvp.ui.listener.ImageAdapterGetFeed;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class ImageAdapter2 extends BaseQuickAdapter<String, BaseViewHolder> implements ImageAdapterGetFeed {
    private FeedBean feedBean;

    public ImageAdapter2() {
        super(R.layout.item_image2);
    }

    public FeedBean getFeed() {
        return feedBean;
    }

    public void setFeedBean(FeedBean feedBean) {
        this.feedBean = feedBean;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, String bean) {
        GlideLoaderUtil.LoadRoundImage20(mContext, Utils.checkUrl(bean), viewHolder.getView(R.id.cover));
    }
}