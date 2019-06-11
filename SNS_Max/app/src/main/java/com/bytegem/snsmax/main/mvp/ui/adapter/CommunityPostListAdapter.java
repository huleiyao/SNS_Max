package com.bytegem.snsmax.main.mvp.ui.adapter;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.bean.CommunityPostBean;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.bytegem.snsmax.main.app.widget.TagTextView;
import com.bytegem.snsmax.main.mvp.ui.viewholder.CommunityPostListViewHolder;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

public class CommunityPostListAdapter extends BaseQuickAdapter<CommunityPostBean, CommunityPostListViewHolder> {

    public CommunityPostListAdapter() {
        super(R.layout.item_community_post);
    }

    @Override
    protected void convert(CommunityPostListViewHolder viewHolder, CommunityPostBean bean) {
        viewHolder.setText(R.id.group_name, bean.getCommunityGroupBean().getName());
        viewHolder.setText(R.id.user_name, bean.getUser().getName());
        viewHolder.setText(R.id.user_tag, bean.getUser().getContent());
        viewHolder.setText(R.id.content, bean.getContent());
        ((TagTextView) viewHolder.getView(R.id.content)).setContentAndTag(bean.getContent(), "健身房的风景");
        GlideLoaderUtil.LoadCircleImage(mContext, bean.getUser().getCover(), viewHolder.getView(R.id.head_image));
        GlideLoaderUtil.LoadRoundImage6(mContext, bean.getCommunityGroupBean().getCover(), viewHolder.getView(R.id.group_cover));
        if (bean.getImages() != null && bean.getImages().size() > 0) {
            viewHolder.setVisible(R.id.more_img, true);
            if (bean.getImages().size() == 1) {
                viewHolder.setVisible(R.id.f_one_img, true);
                viewHolder.setVisible(R.id.recycle_view, false);
                GlideLoaderUtil.LoadRoundImage20(mContext, bean.getImages().get(0), viewHolder.getView(R.id.one_img));
            } else {
                viewHolder.setVisible(R.id.f_one_img, false);
                viewHolder.setVisible(R.id.recycle_view, true);
                RecyclerView recyclerView = viewHolder.getView(R.id.recycle_view);
                viewHolder.setImageData(bean.getImages());
                if (bean.getImages().size() == 2) {
                    recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));// 布局管理器
                    recyclerView.setAdapter(viewHolder.getAdapter2());
                } else {
                    recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));// 布局管理器
                    recyclerView.setAdapter(viewHolder.getAdapter());
                }
                recyclerView.setItemAnimator(new DefaultItemAnimator());
            }
        } else viewHolder.setVisible(R.id.more_img, false);
    }
}