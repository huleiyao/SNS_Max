package com.bytegem.snsmax.main.mvp.ui.adapter;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.bean.feed.FeedBean;
import com.bytegem.snsmax.main.app.bean.feed.MediaLinkContent;
import com.bytegem.snsmax.main.app.bean.topic.TopicBean;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.bytegem.snsmax.main.app.utils.MediaUtils;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.bytegem.snsmax.main.app.widget.TagTextView;
import com.bytegem.snsmax.main.mvp.ui.viewholder.CommunityPostListViewHolder;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.File;

public class TopicsAdapter extends BaseQuickAdapter<TopicBean, CommunityPostListViewHolder> {

    public TopicsAdapter() {
        super(R.layout.item_topic);
    }

    @Override
    protected void convert(CommunityPostListViewHolder viewHolder, TopicBean bean) {
        viewHolder.setText(R.id.topic_title, bean.getName());
    }
}