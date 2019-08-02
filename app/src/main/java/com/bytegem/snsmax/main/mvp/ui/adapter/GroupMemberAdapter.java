package com.bytegem.snsmax.main.mvp.ui.adapter;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.bean.group.GroupBean;
import com.bytegem.snsmax.main.app.bean.user.UserBean;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class GroupMemberAdapter extends BaseQuickAdapter<UserBean, BaseViewHolder> {

    public GroupMemberAdapter() {
        super(R.layout.item_group_members);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, UserBean bean) {
        if (bean.getAvatar() == null || bean.getAvatar().isEmpty()) {
            GlideLoaderUtil.LoadCircleImage(mContext, R.drawable.ic_deskicon, viewHolder.getView(R.id.group_member));
        } else {
            GlideLoaderUtil.LoadCircleImage(mContext, Utils.checkUrl(bean.getAvatar()), viewHolder.getView(R.id.group_member));
        }
    }
}