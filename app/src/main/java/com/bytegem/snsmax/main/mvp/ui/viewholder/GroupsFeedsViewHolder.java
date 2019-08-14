package com.bytegem.snsmax.main.mvp.ui.viewholder;

import android.view.View;

import com.bytegem.snsmax.main.app.bean.feed.FeedBean;
import com.bytegem.snsmax.main.mvp.ui.adapter.GroupFeedsAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

public class GroupsFeedsViewHolder extends BaseViewHolder {
    GroupFeedsAdapter adapter;
    private BaseQuickAdapter.OnItemClickListener mOnItemClickListener;

    public GroupsFeedsViewHolder(View view) {
        super(view);
    }

    public GroupFeedsAdapter getAdapter() {
        if (adapter == null)
            adapter = new GroupFeedsAdapter();
        return adapter;
    }

    public void setListener( BaseQuickAdapter.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
        if (adapter != null) {
            if (mOnItemClickListener != null)
                adapter.setOnItemClickListener(mOnItemClickListener);
        }
    }

    public void setFeeds(ArrayList<FeedBean> list) {
        if (list == null) return;
        if (adapter == null) {
            adapter = new GroupFeedsAdapter();
            adapter.setNewData(list);
        } else adapter.replaceData(list);
    }
}
