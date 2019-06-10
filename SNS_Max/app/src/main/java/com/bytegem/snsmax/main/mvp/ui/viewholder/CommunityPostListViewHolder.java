package com.bytegem.snsmax.main.mvp.ui.viewholder;

import android.view.View;

import com.bytegem.snsmax.main.mvp.ui.adapter.ImageAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

public class CommunityPostListViewHolder extends BaseViewHolder {
    ImageAdapter adapter;

    public CommunityPostListViewHolder(View view) {
        super(view);
        adapter = new ImageAdapter();
    }

    public ImageAdapter getAdapter() {
        return adapter;
    }

    public void setImageData(ArrayList<String> list) {
        if (list == null) return;
        if (adapter == null) {
            adapter = new ImageAdapter();
            adapter.setNewData(list);
        } else adapter.replaceData(list);
    }
}
