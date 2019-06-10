package com.bytegem.snsmax.main.mvp.ui.adapter;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class ImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ImageAdapter() {
        super(R.layout.item_image);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, String bean) {
//        viewHolder.setText(R.id.area_name, bean);
        GlideLoaderUtil.LoadRoundImage20(mContext, bean, viewHolder.getView(R.id.cover));
    }
}