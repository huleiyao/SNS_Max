package com.bytegem.snsmax.main.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.Api;
import com.bytegem.snsmax.main.app.bean.user.MyCircleDTO;
import com.bytegem.snsmax.main.app.bean.user.SearchDTO;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 搜索首页圈子的适配器
 */
public class SearchCircelAdapter extends BaseQuickAdapter<SearchDTO.SearchCircelItem, BaseViewHolder> {

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
        void itemClick(int clickPos, SearchDTO.SearchCircelItem clickItem);
    }

    private SearchCircelAdapter.OnItemCircleClick onclick;

    /**
     * 创建适配器
     *
     * @param data
     * @return
     */
    public static SearchCircelAdapter createAdapter(RecyclerView rv, List<SearchDTO.SearchCircelItem> data, SearchCircelAdapter.OnItemCircleClick onclick) {
        SearchCircelAdapter adapter = new SearchCircelAdapter(R.layout.my_circle_item, data);
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        adapter.setEmptyView(LayoutInflater.from(rv.getContext()).inflate(R.layout.include_empty_data, rv, false));
        rv.setAdapter(adapter);
        adapter.onclick = onclick;
        return adapter;
    }

    public SearchCircelAdapter(int layoutResId, @Nullable List<SearchDTO.SearchCircelItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchDTO.SearchCircelItem item) {
        bindData(helper, item);
        helper.itemView.setOnClickListener((view) -> {
            onclick.itemClick(helper.getAdapterPosition(), item);
        });
    }

    private void bindData(BaseViewHolder helper, SearchDTO.SearchCircelItem item) {
        if (helper.getAdapterPosition() == 0) {
            helper.getView(R.id.mcircle_item_line).setVisibility(View.INVISIBLE);
        } else {
            helper.getView(R.id.mcircle_item_line).setVisibility(View.VISIBLE);
        }
        GlideLoaderUtil.LoadRoundImage(
                helper.itemView.getContext(), Utils.checkUrl(item.avatar),
                helper.getView(R.id.mcircle_item_icon),
                15
        );
        helper.setText(R.id.mcircle_item_title, item.name == null ? "" : item.name);
        helper.getView(R.id.mcircle_item_title_flg).setVisibility(View.GONE);
//        helper.setText(R.id.mcircle_item_title_flg, item.id == item.creator.id ? "博主" : "管理员");
        helper.setText(R.id.mcircle_item_description, item.desc == null ? "" : item.desc);
        buildCountContent(item, helper.getView(R.id.mcircle_item_count));
    }

    //构建和绑定统计相关的内容
    private void buildCountContent(SearchDTO.SearchCircelItem item, TextView contentTextView) {
        StringBuffer sb = new StringBuffer("<font color=\"#5E6CE7\">");
        //人气
        String count = "";
        if (item.feeds_count < 1000) {
            count = item.feeds_count + "";
            sb.append(count);
        } else {
            DecimalFormat df = new DecimalFormat("#.0");
            double counD = item.feeds_count / 1.0;
            count = df.format(counD);
            sb.append(count + " k");
        }
        sb.append("</font>");
        sb.append("\u3000人气\u3000|\u3000");
        //成员
        sb.append("<font color=\"#5E6CE7\">");
        String number = "";
        if (item.members_count < 1000) {
            number = item.members_count + "";
            sb.append(number);
        } else {
            DecimalFormat df = new DecimalFormat("#.0");
            double counD = item.members_count / 1.0;
            number = df.format(counD);
            sb.append(number + " k");
        }
        sb.append("</font>");
        sb.append("\u3000成员");
        contentTextView.setText(Html.fromHtml(sb.toString()));
    }
}
