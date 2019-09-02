package com.bytegem.snsmax.main.mvp.ui.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.Api;
import com.bytegem.snsmax.main.app.bean.user.MyCircleDTO;
import com.bytegem.snsmax.main.app.bean.user.SearchDTO;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.bytegem.snsmax.main.mvp.ui.activity.OwnerHomeActivity;
import com.bytegem.snsmax.main.mvp.ui.view.HomeBannerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.imagepicker.GlideImageLoader;

import java.util.List;

/**
 * 搜索首页的适配器
 */
public class SearchUserAdapter extends BaseQuickAdapter<SearchDTO.SearchUserItem, BaseViewHolder> {

    /**
     * 圈子的点击操作
     */
    public interface OnItemCircleClick {
        /**
         * 点击操作
         *
         * @param clickPos
         * @param clickItem
         */
        void itemClick(int clickPos, SearchDTO.SearchUserItem clickItem);
    }

    private SearchUserAdapter.OnItemCircleClick onclick;
    //关注的监听
    private SearchUserAdapter.OnItemCircleClick onAttentionClick;

    /**
     * 创建适配器
     *
     * @param data
     * @return
     */
    public static SearchUserAdapter createAdapter(
            RecyclerView rv,
            List<SearchDTO.SearchUserItem> data,
            SearchUserAdapter.OnItemCircleClick onclick,
            SearchUserAdapter.OnItemCircleClick onAttentionClick) {
        SearchUserAdapter adapter = new SearchUserAdapter(data);
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        rv.setItemAnimator(new DefaultItemAnimator());
        adapter.setEmptyView(LayoutInflater.from(rv.getContext()).inflate(R.layout.include_empty_data, rv, false));
        rv.setAdapter(adapter);
        adapter.onclick = onclick;
        adapter.onAttentionClick = onAttentionClick;
        return adapter;
    }

    public SearchUserAdapter(@Nullable List<SearchDTO.SearchUserItem> data) {
        super(R.layout.fragment_search_user_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchDTO.SearchUserItem item) {
        bindData(helper,item);
        helper.itemView.setOnClickListener(v->{
            onclick.itemClick(helper.getAdapterPosition(),item);
        });
        helper.getView(R.id.search_user_attention).setOnClickListener(v->{
            onAttentionClick.itemClick(helper.getAdapterPosition(),item);
        });
    }

    private void bindData(BaseViewHolder helper, SearchDTO.SearchUserItem item){
        ImageView img = helper.getView(R.id.search_user_cover);
        GlideLoaderUtil.LoadCircleImage(img.getContext(), Utils.checkUrl(item.getAvatar()),img);
        helper.setText(R.id.search_user_name,item.getName())
                .setText(R.id.search_user_content,item.getBio());
        TextView text = helper.getView(R.id.search_user_attention);
        if(item.getHas_following()){
            text.setBackgroundResource(R.drawable.shape_search_user_attention_bg);
            text.setText("已关注");
            text.setTextColor(Color.parseColor("#B0B8BF"));
        }else{
            text.setBackgroundResource(R.drawable.shape_search_user_not_attention_bg);
            text.setText("+ 关注");
            text.setTextColor(Color.parseColor("#5E6CE7"));
        }
    }
}
