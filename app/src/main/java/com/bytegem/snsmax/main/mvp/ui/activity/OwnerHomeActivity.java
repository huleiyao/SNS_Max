package com.bytegem.snsmax.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bytegem.snsmax.common.adapter.VPFragmentAdapter;
import com.bytegem.snsmax.common.bean.FragmentBean;
import com.bytegem.snsmax.main.app.bean.user.UserBean;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.bytegem.snsmax.main.mvp.ui.fragment.OwnerFeedsFragment;
import com.bytegem.snsmax.main.mvp.ui.fragment.OwnerGroupsFragment;
import com.bytegem.snsmax.main.mvp.ui.fragment.OwnerRecordFragment;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.bytegem.snsmax.main.di.component.DaggerOwnerHomeComponent;
import com.bytegem.snsmax.main.mvp.contract.OwnerHomeContract;
import com.bytegem.snsmax.main.mvp.presenter.OwnerHomePresenter;

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
 * Created by MVPArmsTemplate on 06/23/2019 13:28
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class OwnerHomeActivity extends BaseActivity<OwnerHomePresenter> implements OwnerHomeContract.View {
    @BindView(R.id.tabs)
    SmartTabLayout tabs;
    @BindView(R.id.projectPager)
    ViewPager viewPager;
    @BindView(R.id.user_info_user_cover)
    ImageView user_cover;
    @BindView(R.id.owner_home_appbar)
    AppBarLayout appbar;
    @BindView(R.id.back_cover)
    ImageView back_cover;
    @BindView(R.id.title_cover)
    ImageView title_cover;
    @BindView(R.id.more)
    TextView more;

    @BindView(R.id.owner_home_head_layout)
    LinearLayout head_layout;
    @BindView(R.id.user_info_user_name)
    TextView user_name;
    @BindView(R.id.user_info_send_message)
    TextView send_message;
    @BindView(R.id.user_info_user_content)
    TextView user_content;
    @BindView(R.id.user_info_follow_count_and_fans_count)
    TextView follow_count_and_fans_count;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.user_info_follow_the_user)
    Button follow_the_user;
    boolean isMe = false;
    public static final String ISME = "IS-ME";
    public static final String ID = "ID";
    private int id;
    private String title = "";

    @OnClick({R.id.more})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.more:
                launchActivity(new Intent(this, UserSettingActivity.class));
                break;
        }
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOwnerHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_owner_home; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle(" ");
        isMe = getIntent().getBooleanExtra(ISME, false);
        id = getIntent().getIntExtra(ID, 0);
        ArrayList<FragmentBean> fragmentBeans = new ArrayList<>();
        fragmentBeans.add(new FragmentBean("动态", OwnerFeedsFragment.newInstance()));
        fragmentBeans.add(new FragmentBean("圈子", OwnerGroupsFragment.newInstance(isMe)));
        fragmentBeans.add(new FragmentBean("档案", OwnerRecordFragment.newInstance()));
        showFragment(fragmentBeans);

        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -head_layout.getHeight() / 2) {
                    toolbar_title.setText(title);
                    back_cover.setImageDrawable(getResources().getDrawable(R.drawable.ic_ico_title_back_151b26));
                    more.setTextColor(getResources().getColor(R.color.color_151b26));
                    toolbar_title.setTextColor(getResources().getColor(R.color.color_151b26));
                    title_cover.setVisibility(View.VISIBLE);
                } else {
                    toolbar_title.setText(" ");
                    back_cover.setImageDrawable(getResources().getDrawable(R.drawable.ic_ico_title_back_white));
                    more.setTextColor(getResources().getColor(R.color.white));
                    toolbar_title.setTextColor(getResources().getColor(R.color.white));
                    title_cover.setVisibility(View.GONE);
                }
            }
        });
        if (isMe) {
            send_message.setVisibility(View.GONE);
            follow_the_user.setVisibility(View.GONE);
        } else {
            send_message.setVisibility(View.VISIBLE);
            follow_the_user.setVisibility(View.VISIBLE);
        }
        mPresenter.getUserData(isMe, id);
    }

    public void initUserData(UserBean userBean) {
        if (userBean.getAvatar() == null || userBean.getAvatar().isEmpty()) {
            GlideLoaderUtil.LoadCircleImage(this, R.drawable.ic_deskicon, user_cover, title_cover);
        } else {
            GlideLoaderUtil.LoadCircleImage(this, Utils.checkUrl(userBean.getAvatar()), user_cover, title_cover);
        }
        title = userBean.getName();
//        GlideLoaderUtil.LoadCircleImage(this, Utils.checkUrl(userBean.getAvatar()), user_cover);
        user_name.setText(userBean.getName());
//        user_content.setText(userBean.getContent());
        follow_count_and_fans_count.setText(Utils.getNumberIfPeople(userBean.getFollowings_count()) + "  关注  |  " + Utils.getNumberIfPeople(userBean.getFollowers_count()) + " 粉丝");
    }

    public void showFragment(ArrayList<FragmentBean> fragmenList) {
        viewPager.setAdapter(new VPFragmentAdapter(getSupportFragmentManager(), fragmenList));
        viewPager.setOffscreenPageLimit(fragmenList.size() - 1);
        tabs.setViewPager(viewPager);

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
