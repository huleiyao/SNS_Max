package com.bytegem.snsmax.main.mvp.ui.adapter;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.bean.feed.FeedBean;
import com.bytegem.snsmax.main.app.bean.group.GroupBean;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.bytegem.snsmax.main.app.utils.MediaUtils;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.bytegem.snsmax.main.mvp.ui.viewholder.GroupsFeedsViewHolder;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.io.File;

public class GroupFeedsAdapter extends BaseQuickAdapter<FeedBean, BaseViewHolder> {

    public GroupFeedsAdapter() {
        super(R.layout.item_group_feeds);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, FeedBean bean) {
        viewHolder.setText(R.id.comment_count_or_zan_count, bean.getComments_count() + "评论  |  " + bean.getLikes_count() + "赞");
        if (bean.getMedia() != null) {
            switch (bean.getMedia().getType()) {
                case "image":
                    viewHolder.setVisible(R.id.feed_cover_view, true);
                    viewHolder.setVisible(R.id.is_video, false);
                    if (bean.getMedia().getImageList() != null && bean.getMedia().getImageList().size() > 0)
                        GlideLoaderUtil.LoadRoundImage14(mContext, Utils.checkUrl(bean.getMedia().getImageList().get(0)), viewHolder.getView(R.id.feed_cover));
                    break;
                case "video":
                    viewHolder.setVisible(R.id.feed_cover_view, true);
                    viewHolder.setVisible(R.id.is_video, true);
                    if (bean.getMedia().getMediaVideo().getCover() != null && !bean.getMedia().getMediaVideo().getCover().isEmpty())
                        GlideLoaderUtil.LoadRoundImage14(mContext, Utils.checkUrl(bean.getMedia().getMediaVideo().getCover()), viewHolder.getView(R.id.feed_cover));
                    else
                        MediaUtils.getImageForVideo(Utils.checkUrl(bean.getMedia().getMediaVideo().getVideo()), new MediaUtils.OnLoadVideoImageListener() {
                            @Override
                            public void onLoadImage(File file) {
                                GlideLoaderUtil.LoadRoundImage14(mContext, file, viewHolder.getView(R.id.feed_cover));
                            }
                        });
                    break;
                case "url":
                    viewHolder.setVisible(R.id.feed_cover_view, false);
                    break;
            }
        } else {
            viewHolder.setVisible(R.id.feed_cover_view, false);
        }
    }
}