package com.bytegem.snsmax.main.mvp.ui.view;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bytegem.snsmax.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFindTopGroupView {
    private View header;
    private Unbinder mUnbinder;
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    Context mContext;

    public HomeFindTopGroupView(Context context) {
        mContext = context;
        this.header = View.inflate(mContext, R.layout.view_home_find_header, null);
        mUnbinder = ButterKnife.bind(this, header);
    }

    public View getView() {
        return header;
    }

    public void initList(RecyclerView.Adapter adapter) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);// 布局管理器
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void unBind() {
        mUnbinder.unbind();
    }
}
