package com.bytegem.snsmax.main.mvp.ui.adapter;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.main.app.MApplication;
import com.bytegem.snsmax.main.app.bean.location.TencentMapLocationBean;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public class AddressAdapter extends BaseQuickAdapter<TencentMapLocationBean, BaseViewHolder> {

    public AddressAdapter() {
        super(R.layout.item_address);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, TencentMapLocationBean bean) {
        viewHolder.setText(R.id.address_title, bean.getTitle())
                .setText(R.id.address, bean.getAddress());
        if (MApplication.selectLocation != null && MApplication.selectLocation.getId().equals(bean.getId()))
            viewHolder.setVisible(R.id.is_select, true);
        else viewHolder.setVisible(R.id.is_select, false);
    }
}
