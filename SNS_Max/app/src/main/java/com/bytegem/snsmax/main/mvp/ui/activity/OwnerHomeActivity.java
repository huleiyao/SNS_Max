package com.bytegem.snsmax.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
    @BindView(R.id.user_cover)
    ImageView user_cover;

    @BindView(R.id.user_name)
    TextView user_name;
    @BindView(R.id.send_message)
    TextView send_message;
    @BindView(R.id.user_content)
    TextView user_content;
    @BindView(R.id.follow_count_and_fans_count)
    TextView follow_count_and_fans_count;

    @BindView(R.id.follow_the_user)
    Button follow_the_user;
    boolean isMe = false;
    public static final String ISME = "IS-ME";
    public static final String ID = "ID";
    private int id;

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
        setTitle("");
        isMe = getIntent().getBooleanExtra(ISME, false);
        id = getIntent().getIntExtra(ID, 0);
        ArrayList<FragmentBean> fragmentBeans = new ArrayList<>();
        fragmentBeans.add(new FragmentBean("动态", OwnerFeedsFragment.newInstance()));
        fragmentBeans.add(new FragmentBean("圈子", OwnerGroupsFragment.newInstance(isMe)));
        fragmentBeans.add(new FragmentBean("档案", OwnerRecordFragment.newInstance()));
        showFragment(fragmentBeans);
        mPresenter.getUserData(isMe, id);
    }

    public void initUserData(UserBean userBean) {
        if (userBean.getAvatar() == null ||userBean.getAvatar().isEmpty())
            GlideLoaderUtil.LoadCircleImage(this, R.drawable.ic_deskicon, user_cover);
        else
            GlideLoaderUtil.LoadCircleImage(this, Utils.checkUrl(userBean.getAvatar()), user_cover);

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
