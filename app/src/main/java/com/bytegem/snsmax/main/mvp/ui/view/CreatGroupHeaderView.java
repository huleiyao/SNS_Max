package com.bytegem.snsmax.main.mvp.ui.view;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bytegem.snsmax.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CreatGroupHeaderView {
    private View header;
    private Unbinder mUnbinder;
    Context mContext;
    CreatGroupListener mCreatGroupListener;

    @OnClick(R.id.creat_group)
    void onClick() {
        if (mCreatGroupListener != null) mCreatGroupListener.creatGroup();
    }

    public CreatGroupHeaderView(Context context, CreatGroupListener creatGroupListener) {
        mContext = context;
        mCreatGroupListener = creatGroupListener;
        this.header = View.inflate(mContext, R.layout.view_create_group, null);
        mUnbinder = ButterKnife.bind(this, header);
    }

    public View getView() {
        return header;
    }

    public void unBind() {
        mUnbinder.unbind();
    }

    public interface CreatGroupListener {
        void creatGroup();
    }
}
