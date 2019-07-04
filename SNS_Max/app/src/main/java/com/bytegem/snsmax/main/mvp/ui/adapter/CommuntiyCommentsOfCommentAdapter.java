package com.bytegem.snsmax.main.mvp.ui.adapter;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.main.app.bean.CommunityCommentBean;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class CommuntiyCommentsOfCommentAdapter extends BaseQuickAdapter<CommunityCommentBean, BaseViewHolder> {

    public CommuntiyCommentsOfCommentAdapter() {
        super(R.layout.item_community_comments_of_comment);
    }

    @Override
    public int getItemCount() {
        int count = super.getItemCount();
        if (count > 2)
            return 3;
        else
            return count;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, CommunityCommentBean bean) {
        if (viewHolder.getPosition() == 2)
            viewHolder.setText(R.id.user_name, "共" + super.getItemCount() + "条回复>>");
        else {
            viewHolder.setText(R.id.user_name, bean.getUser().getName()+": ")
                    .setText(R.id.content, bean.getContents());
        }
    }


}
