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
        GlideLoaderUtil.LoadRoundImage20(mContext, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1560230447215&di=f140a043d0f424d61a714237031607b0&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2Fc724647f687cda249bd569ce7929ed06881672e11b4d2-I1EctH_fw658"
                , viewHolder.getView(R.id.cover));
    }
}
