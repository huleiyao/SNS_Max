package com.bytemax.snsmax.main.mvp.ui.adapter;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytemax.snsmax.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class ChatListAdapter extends BaseQuickAdapter<MBaseBean, BaseViewHolder> {

    public ChatListAdapter() {
        super(R.layout.item_chat_list);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, MBaseBean bean) {
//        viewHolder.setText(R.id.area_name, bean);
    }


}
