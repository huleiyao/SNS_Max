package com.bytegem.snsmax.main.mvp.ui.viewholder;

import android.view.View;

import com.bytegem.snsmax.main.mvp.ui.adapter.ImageAdapter;
import com.bytegem.snsmax.main.mvp.ui.adapter.ImageAdapter2;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

public class CommunityPostListViewHolder extends BaseViewHolder {
    ImageAdapter adapter;
    ImageAdapter2 adapter2;
    private BaseQuickAdapter.OnItemChildClickListener mOnItemChildClickListener;
    private BaseQuickAdapter.OnItemClickListener mOnItemClickListener;

    public CommunityPostListViewHolder(View view) {
        super(view);
//        adapter = new ImageAdapter();
//        adapter2 = new ImageAdapter2();
    }

    public ImageAdapter getAdapter() {
        return adapter;
    }

    public ImageAdapter2 getAdapter2() {
        return adapter2;
    }

    public void setListener(BaseQuickAdapter.OnItemChildClickListener onItemChildClickListener, BaseQuickAdapter.OnItemClickListener onItemClickListener) {
        mOnItemChildClickListener = onItemChildClickListener;
        mOnItemClickListener = onItemClickListener;
        if (adapter != null) {
            if (mOnItemChildClickListener != null)
                adapter.setOnItemChildClickListener(mOnItemChildClickListener);
            if (mOnItemClickListener != null)
                adapter.setOnItemClickListener(mOnItemClickListener);
        } else if (adapter2 != null) {
            if (mOnItemChildClickListener != null)
                adapter2.setOnItemChildClickListener(mOnItemChildClickListener);
            if (mOnItemClickListener != null)
                adapter2.setOnItemClickListener(mOnItemClickListener);
        }
    }

    public void setImageData(ArrayList<String> list) {
        if (list == null) return;
        if (list.size() == 2) {
            if (adapter2 == null) {
                adapter2 = new ImageAdapter2();
                adapter2.setNewData(list);
            } else adapter2.replaceData(list);

        } else if (adapter == null) {
            adapter = new ImageAdapter();
            adapter.setNewData(list);
        } else adapter.replaceData(list);
    }
}
