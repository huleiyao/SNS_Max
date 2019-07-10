package com.bytegem.snsmax.main.mvp.ui.adapter;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.bean.feed.FeedCommentBean;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class FeedCommentsAdapter extends BaseQuickAdapter<FeedCommentBean, BaseViewHolder> {
    public boolean isAll = true;
    private boolean isLevel_2 = false;

    public FeedCommentsAdapter() {
        super(R.layout.item_post_comment);
    }

    public void setAll(boolean all) {
        isAll = all;
        notifyDataSetChanged();
    }

    public boolean isLevel_2() {
        return isLevel_2;
    }

    public void setLevel_2(boolean level_2) {
        isLevel_2 = level_2;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (isAll || super.getItemCount() < 3)
            return super.getItemCount();
        return 3;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, FeedCommentBean bean) {
        viewHolder.setText(R.id.comment_user_name, bean.getUserBean().getName())
                .setText(R.id.comment_send_time, bean.getCreated_at())
                .setText(R.id.comment_content, bean.getContents())
                .setText(R.id.comment_zan_count, bean.getLikes_count() + "")
                .addOnClickListener(R.id.comment_zan)
        ;
        GlideLoaderUtil.LoadCircleImage(mContext, Utils.checkUrl(bean.getUserBean().getAvatar()), viewHolder.getView(R.id.comment_user_cover));
        if (isLevel_2) {
            if (viewHolder.getPosition() == 0) {
                viewHolder.setBackgroundColor(R.id.bg, mContext.getResources().getColor(R.color.white));
            } else
                viewHolder.setBackgroundColor(R.id.bg, mContext.getResources().getColor(R.color.default_background));
        }
    }
}
