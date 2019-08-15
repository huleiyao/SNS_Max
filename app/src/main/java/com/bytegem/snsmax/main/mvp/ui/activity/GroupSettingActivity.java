package com.bytegem.snsmax.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bytegem.snsmax.common.View.SwitchButton;
import com.bytegem.snsmax.main.app.bean.group.GroupBean;
import com.bytegem.snsmax.main.app.bean.user.UserBean;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.bytegem.snsmax.main.mvp.ui.adapter.GroupMemberAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.bytegem.snsmax.main.di.component.DaggerGroupSettingComponent;
import com.bytegem.snsmax.main.mvp.contract.GroupSettingContract;
import com.bytegem.snsmax.main.mvp.presenter.GroupSettingPresenter;

import com.bytegem.snsmax.R;


import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/12/2019 14:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class GroupSettingActivity extends BaseActivity<GroupSettingPresenter> implements GroupSettingContract.View {
    @BindView(R.id.group_srtting_group_member_covers)
    RecyclerView groupMemberCovers;
    @BindView(R.id.group_srtting_group_nickname)
    TextView groupNickname;
    @BindView(R.id.group_srtting_group_name)
    TextView groupName;
    @BindView(R.id.group_srtting_is_public)
    TextView ispublic;
    @BindView(R.id.exit_group)
    TextView exitGroup;
    @BindView(R.id.switch_button)
    SwitchButton switchButton;
    @Inject
    GroupMemberAdapter adapter;
    private GroupBean mGroup;

    @OnClick({R.id.group_srtting_group_member, R.id.group_srtting_group_message
            , R.id.group_srtting_group_nickname_set, R.id.group_srtting_group_name_set
            , R.id.exit_group})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.group_srtting_group_member:
                //圈子成员 列表
                launchActivity(new Intent(this, GroupMemberActivity.class).putExtra("group", mGroup));
                break;
            case R.id.group_srtting_group_message:
                //圈子公告
                break;
            case R.id.group_srtting_group_nickname_set:
                //昵称设置
                break;
            case R.id.group_srtting_group_name_set:
                //圈子名字设置
                break;
            case R.id.exit_group:
                //退出圈子
                break;
        }
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerGroupSettingComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_group_setting; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("圈子信息");
        GroupBean group = (GroupBean) getIntent().getSerializableExtra("group");
        if (group == null) {
            killMyself();
            return;
        }
        groupMemberCovers.setLayoutManager(new GridLayoutManager(this, 1));// 布局管理器
        groupMemberCovers.setAdapter(adapter);
        groupMemberCovers.setItemAnimator(new DefaultItemAnimator());
        showGroupData(group);
        mPresenter.getGroupData(group.getId());
    }

    @Override
    public void showGroupData(GroupBean group) {
        mGroup = group;
        groupName.setText(mGroup.getName());
//        groupNickname.setText(mGroup.);
        showGroupMember(group);
    }

    public void showGroupMember(GroupBean group) {
        ArrayList<UserBean> members = new ArrayList<>();
        if (group.getRequester() != null)
            members.add(group.getRequester());
        if (group.getPreview_members() != null)
            for (UserBean userBean : group.getPreview_members())
                if (members.size() < 6)
                    members.add(userBean);
        if (members.size() > 0)
            groupMemberCovers.setLayoutManager(new GridLayoutManager(this, members.size()));// 布局管理器
        adapter.setNewData(members);
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
}
