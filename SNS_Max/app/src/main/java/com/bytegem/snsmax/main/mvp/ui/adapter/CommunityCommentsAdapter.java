package com.bytegem.snsmax.main.mvp.ui.adapter;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.main.app.bean.CommunityCommentBean;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class CommunityCommentsAdapter extends BaseQuickAdapter<CommunityCommentBean, BaseViewHolder> {
    public boolean isAll = false;

    public CommunityCommentsAdapter() {
        super(R.layout.item_post_comment);
    }

    public void setAll(boolean all) {
        isAll = all;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (isAll || super.getItemCount() < 3)
            return super.getItemCount();
        return 3;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, CommunityCommentBean bean) {
        viewHolder.setText(R.id.comment_user_name, bean.getUser().getName())
                .setText(R.id.send_time, bean.getCreated_at())
                .setText(R.id.comment_content, bean.getContents())
                .setText(R.id.comment_zan_count, bean.getLikes_count() + "");
        GlideLoaderUtil.LoadCircleImage(mContext, bean.getUser().getCover(), viewHolder.getView(R.id.comment_user_cover));
    }
}
