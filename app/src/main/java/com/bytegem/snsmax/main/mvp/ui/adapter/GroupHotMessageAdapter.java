package com.bytegem.snsmax.main.mvp.ui.adapter;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.main.app.bean.discusses.DiscussBean;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class GroupHotMessageAdapter extends BaseQuickAdapter<DiscussBean, BaseViewHolder> {

    public GroupHotMessageAdapter() {
        super(R.layout.item_group_hot_message);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, DiscussBean bean) {
        viewHolder
                .setText(R.id.group_hot_title, bean.getTitle())
                .setText(R.id.group_hot_content, bean.getDesc())
                .setText(R.id.group_hot_group_name, bean.getGroup().getName())
                .setText(R.id.group_hot_num, Utils.getNumberIfPeople(bean.getPopularity()) + "热度")
        ;
        GlideLoaderUtil.LoadRoundImage14(mContext, Utils.checkUrl(bean.getGroup().getAvatar()), viewHolder.getView(R.id.group_hot_cover));
        GlideLoaderUtil.LoadRoundImage8(mContext, Utils.checkUrl(bean.getGroup().getAvatar()), viewHolder.getView(R.id.group_hot_group_cover));
    }
}