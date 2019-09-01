package com.bytegem.snsmax.main.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.bean.user.SearchDTO;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 搜索首页评论的适配器
 */
public class SearchDiscussAdapter extends BaseQuickAdapter<SearchDTO.SearchDiscussesItem, BaseViewHolder> {

    /**
     * 评论的点击操作
     */
    public interface OnItemCircleClick {
        /**
         * 点击操作
         *
         * @param clickPos
         * @param clickItem
         */
        void itemClick(int clickPos, SearchDTO.SearchDiscussesItem clickItem);
    }

    private SearchDiscussAdapter.OnItemCircleClick onclick;

    /**
     * 创建适配器
     *
     * @param data
     * @return
     */
    public static SearchDiscussAdapter createAdapter(RecyclerView rv, List<SearchDTO.SearchDiscussesItem> data, SearchDiscussAdapter.OnItemCircleClick onclick) {
        SearchDiscussAdapter adapter = new SearchDiscussAdapter(R.layout.fragment_search_discuss_item, data);
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        adapter.setEmptyView(LayoutInflater.from(rv.getContext()).inflate(R.layout.include_empty_data, rv, false));
        rv.setAdapter(adapter);
        adapter.onclick = onclick;
        return adapter;
    }

    public SearchDiscussAdapter(int layoutResId, @Nullable List<SearchDTO.SearchDiscussesItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchDTO.SearchDiscussesItem item) {
        bindData(helper, item);
        helper.itemView.setOnClickListener((view) -> {
            onclick.itemClick(helper.getAdapterPosition(), item);
        });
    }

    private void bindData(BaseViewHolder helper, SearchDTO.SearchDiscussesItem item) {
        if (helper.getAdapterPosition() == 0) {
            helper.getView(R.id.disscuss_item_line).setVisibility(View.INVISIBLE);
        } else {
            helper.getView(R.id.disscuss_item_line).setVisibility(View.VISIBLE);
        }
        helper.setText(R.id.disscuss_item_title, item.getTitle());
        buildCountContent(item, helper.getView(R.id.disscuss_item_description));
    }

    //构建和绑定统计相关的内容
    private void buildCountContent(SearchDTO.SearchDiscussesItem item, TextView contentTextView) {
        StringBuffer sb = new StringBuffer();
        //关注
        String count = "";
        if (item.getFollower_count() < 1000) {
            count = item.getFollower_count() + "";
            sb.append(count);
        } else {
            DecimalFormat df = new DecimalFormat("#.0");
            double counD = item.getFollower_count() / 1.0;
            count = df.format(counD);
            sb.append(count + " k");
        }
        sb.append("关注·");
        //发言
        String number = "";
        if (item.getPopularity() < 1000) {
            number = item.getPopularity() + "";
            sb.append(number);
        } else {
            DecimalFormat df = new DecimalFormat("#.0");
            double counD = item.getPopularity() / 1.0;
            number = df.format(counD);
            sb.append(number + " k");
        }
        sb.append("发言");
        contentTextView.setText(Html.fromHtml(sb.toString()));
    }
}
