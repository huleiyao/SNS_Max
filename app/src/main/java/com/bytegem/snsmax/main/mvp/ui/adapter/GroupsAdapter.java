package com.bytegem.snsmax.main.mvp.ui.adapter;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.bean.feed.FeedBean;
import com.bytegem.snsmax.main.app.bean.group.GroupBean;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.bytegem.snsmax.main.mvp.ui.viewholder.GroupsFeedsViewHolder;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

public class GroupsAdapter extends BaseQuickAdapter<GroupBean, GroupsFeedsViewHolder> {
    private BaseQuickAdapter.OnItemClickListener mOnItemClickListener;
    private BaseQuickAdapter.OnItemChildClickListener mOnItemChildClickListener;

    public GroupsAdapter() {
        super(R.layout.item_community_group_list);
    }

    public void setListener(BaseQuickAdapter.OnItemClickListener onItemClickListener, BaseQuickAdapter.OnItemChildClickListener onItemChildClickListener) {
        mOnItemClickListener = onItemClickListener;
        mOnItemChildClickListener = onItemChildClickListener;
    }

    @Override
    protected void convert(GroupsFeedsViewHolder viewHolder, GroupBean bean) {
        viewHolder.setText(R.id.group_name, bean.getName())
                .addOnClickListener(R.id.group_join_us);
        GlideLoaderUtil.LoadRoundImage8(mContext, Utils.checkUrl(bean.getAvatar()), viewHolder.getView(R.id.group_cover));
        RecyclerView recyclerView = viewHolder.getView(R.id.recycle_view);
        if (bean.getFeeds() == null || bean.getFeeds().size() == 0)
            viewHolder.setVisible(R.id.recycle_view, false);
        else {
            viewHolder.setVisible(R.id.recycle_view, true);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));// 布局管理器
            viewHolder.setListener(mOnItemChildClickListener, mOnItemClickListener);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(viewHolder.getAdapter());
            viewHolder.setFeeds(bean.getFeeds());
        }
    }
}