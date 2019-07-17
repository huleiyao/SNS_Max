package com.bytegem.snsmax.main.mvp.ui.adapter;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.bytegem.snsmax.main.app.utils.MediaUtils;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.bytegem.snsmax.main.mvp.ui.activity.CreatNewsActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.imagepicker.bean.ImageItem;

import java.io.File;
import java.util.ArrayList;

import static com.bytegem.snsmax.main.mvp.ui.activity.CreatNewsActivity.FeedType.DEFAULT;

public class CreateImageAdapter extends BaseQuickAdapter<ImageItem, BaseViewHolder> {
    CreatNewsActivity.FeedType feedType = DEFAULT;

    public CreateImageAdapter() {
        super(R.layout.item_create_image);
    }

    public void setFeedType(CreatNewsActivity.FeedType feedType) {
        this.feedType = feedType;
        notifyDataSetChanged();
    }

    public void setFeedType(CreatNewsActivity.FeedType feedType, ArrayList<ImageItem> imageItems) {
        this.feedType = feedType;
        setNewData(imageItems);
    }

    public CreatNewsActivity.FeedType getFeedType() {
        return feedType;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, ImageItem bean) {
        viewHolder
                .addOnClickListener(R.id.delete)
                .addOnClickListener(R.id.add)
                .setVisible(R.id.is_video, false)//默认隐藏
                .setVisible(R.id.delete, false)//默认隐藏
                .setVisible(R.id.video_add, false)//默认隐藏
                .setVisible(R.id.add, false);//默认隐藏

        if (feedType != null)
            switch (feedType) {
                case CAMERA:
                    if (bean.path.equals("add")) {
                        //最后一条 //添加
                        viewHolder.setVisible(R.id.img, false)
                                .setVisible(R.id.add, true);
                    } else {
                        viewHolder
                                .setVisible(R.id.delete, true)
                                .setVisible(R.id.img, true)
                                .setVisible(R.id.add, false);
                        GlideLoaderUtil.LoadRoundImage20(mContext, bean.path, viewHolder.getView(R.id.cover));
                    }
                    break;
                case VIDEO:
                    if (super.getItemCount() == 1 && viewHolder.getPosition() == 0) {
                        viewHolder.setVisible(R.id.is_video, true)
                                .setVisible(R.id.delete, true)
                                .setVisible(R.id.img, true)
                                .setVisible(R.id.add, false);
                        MediaUtils.getImageForVideo(bean.path, new MediaUtils.OnLoadVideoImageListener() {
                            @Override
                            public void onLoadImage(File file) {
                                GlideLoaderUtil.LoadRoundImage20(mContext, file, viewHolder.getView(R.id.one_img));
                            }
                        });
                        GlideLoaderUtil.LoadRoundImage20(mContext, bean.path, viewHolder.getView(R.id.cover));
                    } else if (bean.path.equals("add")) {
                        //添加
                        viewHolder.setVisible(R.id.img, false)
                                .setVisible(R.id.add, true)
                                .setVisible(R.id.video_add, true);
                    }
                    break;
            }
        else {
            viewHolder.setVisible(R.id.delete, true);
            GlideLoaderUtil.LoadRoundImage20(mContext, bean.path, viewHolder.getView(R.id.cover));
        }
    }
}