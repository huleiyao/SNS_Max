package com.bytegem.snsmax.main.mvp.ui.adapter;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.bean.discusses.DiscussBean;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class DiscussAdapter extends BaseQuickAdapter<DiscussBean, BaseViewHolder> {

    public DiscussAdapter() {
        super(R.layout.item_discuss);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, DiscussBean bean) {
        viewHolder.setText(R.id.discuss_title, bean.getTitle())
                .setText(R.id.discuss_content, bean.getDesc())
                .setText(R.id.discuss_comment_count,
                        Utils.getNumberIfPeople(bean.getFollower_count()) + "关注 · " +
                                Utils.getNumberIfPeople(bean.getFeeds_count()) + "发言")
                .addOnClickListener(R.id.discuss_more)
        ;
        if (bean.getMedia() != null && bean.getMedia().getImageList() != null && bean.getMedia().getImageList().size() > 0)
            GlideLoaderUtil.LoadRoundImage14(mContext, Utils.checkUrl(bean.getMedia().getImageList().get(0)), viewHolder.getView(R.id.discuss_cover));
    }
}