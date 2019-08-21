package com.bytegem.snsmax.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bytegem.snsmax.main.app.bean.group.GroupBean;
import com.bytegem.snsmax.main.mvp.ui.adapter.FeedCommentsAdapter;
import com.bytegem.snsmax.main.mvp.ui.adapter.GroupMemberLineAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.bytegem.snsmax.main.di.component.DaggerGroupMemberComponent;
import com.bytegem.snsmax.main.mvp.contract.GroupMemberContract;
import com.bytegem.snsmax.main.mvp.presenter.GroupMemberPresenter;

import com.bytegem.snsmax.R;
import com.liaoinstan.springview.widget.SpringView;


import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/26/2019 15:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class GroupMemberActivity extends BaseActivity<GroupMemberPresenter> implements GroupMemberContract.View {
    @BindView(R.id.springview)
    SpringView springview;
    @BindView(R.id.recycle_view)
    RecyclerView recycle_view;
    @Inject
    GroupMemberLineAdapter adapter;
    MemberType memberType;

    public enum MemberType {
        Find, Manager, RemoveMember, MakeOverMaster
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerGroupMemberComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_group_member; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    private void getMemberType(int type) {
        switch (type) {
            case 1:
                memberType = MemberType.Manager;
                break;
            case 2:
                memberType = MemberType.RemoveMember;
                break;
            case 3:
                memberType = MemberType.MakeOverMaster;
                break;
            default:
                memberType = MemberType.Find;
        }
        mPresenter.setMemberType(memberType);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("圈子成员");
        GroupBean groupBean = (GroupBean) getIntent().getSerializableExtra("group");
        getMemberType(getIntent().getIntExtra("memberType", 0));
        if (groupBean == null) {
            killMyself();
            return;
        }
        recycle_view.setLayoutManager(new LinearLayoutManager(this));// 布局管理器
        recycle_view.setAdapter(adapter);
        recycle_view.setItemAnimator(new DefaultItemAnimator());
        springview.setType(SpringView.Type.FOLLOW);
        springview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getGroupMemberList(false);
            }

            @Override
            public void onLoadmore() {
                mPresenter.getGroupMemberList(true);
            }
        });

        adapter.setOnItemChildClickListener(mPresenter);
        adapter.setOnItemClickListener(mPresenter);
        adapter.setOnItemLongClickListener(mPresenter);
        mPresenter.setGroupId(groupBean.getId());
    }

    MaterialDialog materialDialog;

    @Override
    public void showLoading() {
        hideLoading();
        materialDialog = getMaterialDialog("", "").show();
    }

    @Override
    public void hideLoading() {
        if (materialDialog != null && materialDialog.isShowing()) materialDialog.dismiss();
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
    public void onFinishFreshAndLoad() {
        springview.onFinishFreshAndLoad();
    }
}
