package com.bytemax.snsmax.main.mvp.ui.adapter;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytemax.snsmax.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class CommunityPostListAdapter extends BaseQuickAdapter<MBaseBean, BaseViewHolder> {

    public CommunityPostListAdapter() {
        super(R.layout.item_community_post);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, MBaseBean bean) {
//        viewHolder.setText(R.id.area_name, bean);
    }


}
