package com.bytegem.snsmax.main.mvp.ui.adapter;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.bean.group.GroupBean;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.bytegem.snsmax.main.mvp.ui.viewholder.GroupsFeedsViewHolder;
import com.chad.library.adapter.base.BaseQuickAdapter;

public class GroupsSelectAdapter extends BaseQuickAdapter<GroupBean, GroupsFeedsViewHolder> {

    public GroupsSelectAdapter() {
        super(R.layout.item_group_select_list);
    }

    @Override
    protected void convert(GroupsFeedsViewHolder viewHolder, GroupBean bean) {
        viewHolder
                .setText(R.id.group_select_name, bean.getName())
                .setText(R.id.group_select_content, bean.getDesc())
        ;
        GlideLoaderUtil.LoadRoundImage8(mContext, Utils.checkUrl(bean.getAvatar()), viewHolder.getView(R.id.group_cover));
    }
}