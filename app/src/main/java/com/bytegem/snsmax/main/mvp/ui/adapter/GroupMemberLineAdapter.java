package com.bytegem.snsmax.main.mvp.ui.adapter;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.bean.user.UserBean;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class GroupMemberLineAdapter extends BaseQuickAdapter<UserBean, BaseViewHolder> {

    public GroupMemberLineAdapter() {
        super(R.layout.item_group_member_line);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, UserBean bean) {
        if (bean.getGroup_member() != null) {
            viewHolder.setText(R.id.group_member_user_name, bean.getGroup_member().getNickname() == null ? bean.getName() : bean.getGroup_member().getNickname());
            switch (bean.getGroup_member().getRole()) {
                case "creator":
                    viewHolder.setVisible(R.id.group_duty, true).setText(R.id.group_duty, "圈主");
                    break;
                case "manager":
                    viewHolder.setVisible(R.id.group_duty, true).setText(R.id.group_duty, "管理员");
                    break;
                default:
                    viewHolder.setVisible(R.id.group_duty, false);
            }
        } else {
            viewHolder.setText(R.id.group_member_user_name, bean.getName())
                    .setVisible(R.id.group_duty, false);
        }
        if (bean.getAvatar() == null || bean.getAvatar().isEmpty()) {
            GlideLoaderUtil.LoadCircleImage(mContext, R.drawable.ic_deskicon, viewHolder.getView(R.id.group_member_cover));
        } else {
            GlideLoaderUtil.LoadCircleImage(mContext, Utils.checkUrl(bean.getAvatar()), viewHolder.getView(R.id.group_member_cover));
        }
    }
}