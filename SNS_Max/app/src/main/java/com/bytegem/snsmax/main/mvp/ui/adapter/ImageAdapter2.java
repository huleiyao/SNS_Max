package com.bytegem.snsmax.main.mvp.ui.adapter;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.Api;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class ImageAdapter2 extends BaseQuickAdapter<String, BaseViewHolder> {

    public ImageAdapter2() {
        super(R.layout.item_image2);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, String bean) {
        GlideLoaderUtil.LoadRoundImage20(mContext, Utils.checkUrl(bean), viewHolder.getView(R.id.cover));
    }
}