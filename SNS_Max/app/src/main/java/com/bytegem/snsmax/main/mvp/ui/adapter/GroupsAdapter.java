package com.bytegem.snsmax.main.mvp.ui.adapter;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.bean.group.GroupBean;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class GroupsAdapter extends BaseQuickAdapter<GroupBean, BaseViewHolder> {

    public GroupsAdapter() {
        super(R.layout.item_community_group_list);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, GroupBean bean) {
        viewHolder.setText(R.id.group_name, bean.getName());
        GlideLoaderUtil.LoadRoundImage8(mContext, Utils.checkUrl(bean.getAvatar()), viewHolder.getView(R.id.group_cover));
    }
}