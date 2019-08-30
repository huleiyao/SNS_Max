package com.bytegem.snsmax.main.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.bean.user.SearchDTO;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 搜索首页的适配器
 */
public class SearchUserAdapter extends BaseQuickAdapter<SearchDTO.SearchUserItem, BaseViewHolder> {

    public SearchUserAdapter(@Nullable List<SearchDTO.SearchUserItem> data) {
        super(R.layout.fragment_search_user_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchDTO.SearchUserItem item) {

    }
}
