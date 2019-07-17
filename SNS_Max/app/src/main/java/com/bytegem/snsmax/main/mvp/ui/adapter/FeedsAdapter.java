package com.bytegem.snsmax.main.mvp.ui.adapter;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.bean.feed.FeedBean;
import com.bytegem.snsmax.main.app.bean.feed.MediaLinkContent;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.bytegem.snsmax.main.app.utils.MediaUtils;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.bytegem.snsmax.main.app.widget.TagTextView;
import com.bytegem.snsmax.main.mvp.ui.activity.FeedDetailsActivity;
import com.bytegem.snsmax.main.mvp.ui.viewholder.CommunityPostListViewHolder;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.File;

public class FeedsAdapter extends BaseQuickAdapter<FeedBean, CommunityPostListViewHolder> {
    private OnItemChildClickListener mOnItemChildClickListener;
    private OnItemClickListener mOnItemClickListener;
    private TagTextView.TopicListener mTopicListener;

    public FeedsAdapter() {
        super(R.layout.item_community_post);
    }

    public void setListener(OnItemChildClickListener onItemChildClickListener, OnItemClickListener onItemClickListener, TagTextView.TopicListener topicListener) {
        mOnItemChildClickListener = onItemChildClickListener;
        mOnItemClickListener = onItemClickListener;
        mTopicListener = topicListener;
    }

    @Override
    protected void convert(CommunityPostListViewHolder viewHolder, FeedBean bean) {
        viewHolder.setText(R.id.group_name, bean.getGroup().getName())
                .setText(R.id.user_name, bean.getUser().getName())
//                .setText(R.id.user_tag, bean.getUserBean().getContent())
                .setText(R.id.content, bean.getContents())
                .setText(R.id.likes_count, Utils.getNumberIfPeople(bean.getLikes_count()))
                .setText(R.id.comments_count, Utils.getNumberIfPeople(bean.getComments_count()))
                .setText(R.id.share_count, Utils.getNumberIfPeople(bean.getShare_count()))
                .setVisible(R.id.is_video, false)
                .setVisible(R.id.more_img, false)
                .setVisible(R.id.url, false)
                .setVisible(R.id.recycle_view, false)
                .setVisible(R.id.f_one_img, false)
                .setVisible(R.id.group, false)
                .addOnClickListener(R.id.f_one_img)
                .addOnClickListener(R.id.content)
                .addOnClickListener(R.id.tv_tag)
        ;
        if (bean.getGeo() == null
                || (bean.getGeo().getLatitude().isEmpty() && bean.getGeo().getLongitude().isEmpty())
                || (bean.getGeo().getLatitude().equals("0") && bean.getGeo().getLongitude().equals("0")))
            ;
        else {
            viewHolder.setVisible(R.id.tv_address, true)
                    .setText(R.id.tv_address, "");
        }
        if (bean.getTopic() != null && bean.getTopic().getName() != null)
            ((TagTextView) viewHolder.getView(R.id.content)).setContentAndTag(bean.getContents(), bean.getTopic());
        ((TagTextView) viewHolder.getView(R.id.content)).setListener(mTopicListener);
        GlideLoaderUtil.LoadCircleImage(mContext, Utils.checkUrl(bean.getUser().getAvatar()), viewHolder.getView(R.id.head_image));
        GlideLoaderUtil.LoadRoundImage6(mContext, Utils.checkUrl(bean.getGroup().getAvatar()), viewHolder.getView(R.id.group_cover));
        if (bean.getMedia() != null)
            switch (bean.getMedia().getType()) {
                case "image":
                    if (bean.getMedia().getImageList() != null && bean.getMedia().getImageList().size() > 0) {
                        viewHolder.setVisible(R.id.more_img, true);
                        if (bean.getMedia().getImageList().size() == 1) {
                            viewHolder.setVisible(R.id.f_one_img, true);
                            GlideLoaderUtil.LoadRoundImage20(mContext, Utils.checkUrl(bean.getMedia().getImageList().get(0)), viewHolder.getView(R.id.one_img));
                        } else {
                            viewHolder.setVisible(R.id.recycle_view, true);
                            RecyclerView recyclerView = viewHolder.getView(R.id.recycle_view);
                            viewHolder.setImageData(bean.getMedia().getImageList());
                            if (bean.getMedia().getImageList().size() == 2) {
                                recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));// 布局管理器
                                recyclerView.setAdapter(viewHolder.getAdapter2());
                                viewHolder.setListener(mOnItemChildClickListener, mOnItemClickListener);
                            } else {
                                recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));// 布局管理器
                                recyclerView.setAdapter(viewHolder.getAdapter());
                                viewHolder.setListener(mOnItemChildClickListener, mOnItemClickListener);
                            }
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                        }
                    }
                    break;
                case "video":
                    viewHolder.setVisible(R.id.f_one_img, true)
                            .setVisible(R.id.is_video, true)
                            .setVisible(R.id.more_img, true)
                    ;
                    GlideLoaderUtil.LoadRoundImage20(mContext, Utils.checkUrl(bean.getMedia().getMediaVideo().getCover()), viewHolder.getView(R.id.one_img));
//                    MediaUtils.getImageForVideo(Utils.checkUrl(bean.getMedia().getMediaVideo().getVideo()), new MediaUtils.OnLoadVideoImageListener() {
//                        @Override
//                        public void onLoadImage(File file) {
//                            GlideLoaderUtil.LoadRoundImage20(mContext, file, viewHolder.getView(R.id.one_img));
//                        }
//                    });
                    break;
                case "url":
                    MediaLinkContent mediaLinkContent = bean.getMedia().getMediaLink();
                    if (mediaLinkContent == null) break;
                    viewHolder.setVisible(R.id.url, true)
                            .setVisible(R.id.more_img, true)
                            .setText(R.id.url_text, mediaLinkContent.getTitle() == null ? "" : mediaLinkContent.getTitle())
                    ;
                    if (mediaLinkContent.getImage() != null && !mediaLinkContent.getImage().isEmpty())
                        GlideLoaderUtil.LoadRoundImage(mContext, Utils.checkUrl(mediaLinkContent.getUrl()), viewHolder.getView(R.id.url_cover));
                    else
                        ((ImageView) viewHolder.getView(R.id.url_cover)).setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_img_link));
                    break;
            }
    }
}