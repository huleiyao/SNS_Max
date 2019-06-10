package com.bytegem.snsmax.main.mvp.ui.adapter;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class CommunityGroupHeaderListAdapter extends BaseQuickAdapter<MBaseBean, BaseViewHolder> {

    public CommunityGroupHeaderListAdapter() {
        super(R.layout.item_community_group_header);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, MBaseBean bean) {
//        viewHolder.setText(R.id.area_name, bean);
        GlideLoaderUtil.LoadRoundImage20(mContext, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560157380928&di=a5fcba2094b5d96612a2a77b4873115e&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F9b671d17b52639d35e7c76c23f79fbabebe769d43140-xjB5Tw_fw658"
                , viewHolder.getView(R.id.cover));
    }
}
