package com.bytegem.snsmax.main.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bytegem.snsmax.common.adapter.VPFragmentAdapter;
import com.bytegem.snsmax.common.bean.FragmentBean;
import com.bytegem.snsmax.main.app.bean.group.GroupBean;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.bytegem.snsmax.main.mvp.ui.fragment.DiscussListFragment;
import com.bytegem.snsmax.main.mvp.ui.fragment.OwnerFeedsFragment;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.bytegem.snsmax.main.di.component.DaggerGroupDetailsComponent;
import com.bytegem.snsmax.main.mvp.contract.GroupDetailsContract;
import com.bytegem.snsmax.main.mvp.presenter.GroupDetailsPresenter;

import com.bytegem.snsmax.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/10/2019 19:45
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class GroupDetailsActivity extends BaseActivity<GroupDetailsPresenter> implements GroupDetailsContract.View, View.OnClickListener {
    @BindView(R.id.tabs)
    SmartTabLayout tabs;
    @BindView(R.id.group_detail_appbar)
    AppBarLayout appbar;
    @BindView(R.id.projectPager)
    ViewPager viewPager;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.group_detail_member_covers)
    RecyclerView member_covers;
    @BindView(R.id.group_detail_head_layout)
    LinearLayout head_layout;
    @BindView(R.id.group_detail_group_cover)
    ImageView group_cover;
    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.back_cover)
    ImageView back_cover;

    @BindView(R.id.more)
    TextView more;
    @BindView(R.id.group_detail_group_name)
    TextView group_name;
    @BindView(R.id.group_detail_group_active_count)
    TextView group_active_count;
    @BindView(R.id.group_detail_group_detail)
    TextView group_detail;
    @BindView(R.id.group_detail_group_member_count)
    TextView group_member_count;
    @BindView(R.id.group_detail_group_message)
    TextView group_message;

    @BindView(R.id.group_detail_join_us)
    CheckBox join_us;

    @BindView(R.id.group_detail_group_member)
    LinearLayout group_member;
    TextView notice_content;
    private GroupBean mGroup;
    BottomSheetDialog notice;

    @OnClick({R.id.more, R.id.group_detail_group_message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.more:
                launchActivity(new Intent(this, GroupSettingActivity.class));
                break;
            case R.id.group_detail_group_message:
                notice.show();
                break;
            case R.id.exit_notice:
                notice.dismiss();
                break;
        }
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerGroupDetailsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_group_details; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        GroupBean group = (GroupBean) getIntent().getSerializableExtra("group");
        if (group == null) {
            killMyself();
            return;
        }
        toolbar_title.setTextColor(getResources().getColor(R.color.color_151b26));
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -head_layout.getHeight() / 2) {
                    more.setTextColor(getResources().getColor(R.color.color_151b26));
                    toolbar_title.setText(group.getName());
                    back_cover.setImageDrawable(getResources().getDrawable(R.drawable.ic_ico_title_back_151b26));
                    search.setImageDrawable(getResources().getDrawable(R.drawable.ic_ico_title_search_151b26));
                } else {
                    toolbar_title.setText(" ");
                    more.setTextColor(getResources().getColor(R.color.white));
                    back_cover.setImageDrawable(getResources().getDrawable(R.drawable.ic_ico_title_back_white));
                    search.setImageDrawable(getResources().getDrawable(R.drawable.ic_ico_title_search_white));
                }
            }
        });
        search.setVisibility(View.VISIBLE);
        showGroupData(group);
        initFragment();
        initNotice();
        mPresenter.getGroupData(group.getId());
    }

    private void initNotice() {
        notice = new BottomSheetDialog(this);
        notice.setContentView(R.layout.view_group_detail_notice);
        notice.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet)
                .setBackgroundColor(getResources().getColor(R.color.albumTransparent));
        notice.findViewById(R.id.exit_notice).setOnClickListener(this);
        notice_content = notice.findViewById(R.id.notice_content);
    }

    public void showGroupData(GroupBean group) {
        mGroup = group;
        GlideLoaderUtil.LoadRoundImage20(this, Utils.checkUrl(mGroup.getAvatar()), group_cover);
        group_name.setText(mGroup.getName());
        group_active_count.setText(Utils.getNumberIfPeople(mGroup.getMembers_count()) + " 人气");
        if (mGroup.isHas_joined()) {
            join_us.setText("已加入");
        } else {
            join_us.setText("+ 加入");
        }
        group_detail.setText(mGroup.getDesc());
        group_member_count.setText(Utils.getNumberIfPeople(mGroup.getMembers_count()) + " 成员");
    }

    private void initFragment() {
        ArrayList<FragmentBean> fragmentBeans = new ArrayList<>();
        fragmentBeans.add(new FragmentBean("动态", OwnerFeedsFragment.newInstance()));
        fragmentBeans.add(new FragmentBean("讨论", DiscussListFragment.newInstance(mGroup)));
        viewPager.setAdapter(new VPFragmentAdapter(getSupportFragmentManager(), fragmentBeans));
        viewPager.setOffscreenPageLimit(fragmentBeans.size() - 1);
        tabs.setViewPager(viewPager);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
