package com.bytegem.snsmax.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.bean.user.MyCircleDTO;
import com.bytegem.snsmax.main.mvp.ui.adapter.MyCircleListAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.bytegem.snsmax.main.di.component.DaggerMyCircleComponent;
import com.bytegem.snsmax.main.mvp.contract.MyCircleContract;
import com.bytegem.snsmax.main.mvp.presenter.MyCirclePresenter;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * 我的圈子。首页->个人中心->我的圈子
 * <p>
 * Created by MVPArmsTemplate on 08/26/2019 14:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MyCircleActivity extends BaseActivity<MyCirclePresenter> implements MyCircleContract.View {

    @BindView(R.id.title_title)
    TextView titleText;
    @BindView(R.id.my_circle_list)
    RecyclerView rvList;
    @BindView(R.id.my_circle_refresh)
    SwipeRefreshLayout refreshLayout;

    MyCircleListAdapter adapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyCircleComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_circle; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        refreshLayout.setOnRefreshListener(() -> {
            refreshLayout.setRefreshing(false);
            mPresenter.getMyCircle();
        });
        titleText.setText("我的圈子");
        mPresenter.getMyCircle();
    }

    @Override
    public void showLoading() {
        refreshLayout.setEnabled(true);
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        refreshLayout.setRefreshing(false);
        refreshLayout.setEnabled(false);
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void updateMyCircle(MyCircleDTO data) {
        if (adapter == null) {
            adapter = MyCircleListAdapter.createAdapter(rvList,data.data);
        }
    }

    @OnClick({R.id.title_back})
    void click(View v) {
        switch (v.getId()) {
            case R.id.title_back: //返回
                killMyself();
                break;
        }
    }
}
