package com.bytegem.snsmax.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bytegem.snsmax.main.app.bean.topic.TopicBean;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.bytegem.snsmax.main.mvp.ui.adapter.FeedsAdapter;
import com.bytegem.snsmax.main.mvp.ui.view.HomeBannerView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.bytegem.snsmax.main.di.component.DaggerTopicDetailComponent;
import com.bytegem.snsmax.main.mvp.contract.TopicDetailContract;
import com.bytegem.snsmax.main.mvp.presenter.TopicDetailPresenter;

import com.bytegem.snsmax.R;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;


import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/13/2019 08:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class TopicDetailActivity extends BaseActivity<TopicDetailPresenter> implements TopicDetailContract.View {
    TopicBean topicBean;
    @Inject
    FeedsAdapter adapter;
    @BindView(R.id.springview)
    SpringView springView;
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    @BindView(R.id.topic_detail_head_layout)
    LinearLayout head_layout;
    @BindView(R.id.topic_detail_appbar)
    AppBarLayout appbar;

    @BindView(R.id.topic_detail_search)
    ImageView search;
    @BindView(R.id.topic_detail_share)
    ImageView share;
    @BindView(R.id.topic_detail_back)
    ImageView back;
    @BindView(R.id.topic_detail_header_bg_cover)
    ImageView topic_detail_header_bg_cover;
    @BindView(R.id.topic_detail_topic_cover)
    ImageView topic_cover;
    @BindView(R.id.topic_detail_topic_name)
    TextView topic_name;
    @BindView(R.id.topic_detail_topic_popularity)
    TextView topic_popularity;
    @BindView(R.id.topic_detail_topic_content)
    TextView topic_content;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTopicDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_topic_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        topicBean = (TopicBean) getIntent().getSerializableExtra("topic");
        if (topicBean == null) {
            killMyself();
            return;
        }
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -head_layout.getHeight() / 2) {
                    toolbar_title.setText("#" + topicBean.getName() + "#");
                    back.setImageDrawable(getResources().getDrawable(R.drawable.ic_ico_title_back_151b26));
                    search.setImageDrawable(getResources().getDrawable(R.drawable.ic_ico_title_search_151b26));
                    share.setImageDrawable(getResources().getDrawable(R.drawable.ic_ico_title_share_151b26));
                } else {
                    toolbar_title.setText(" ");
                    back.setImageDrawable(getResources().getDrawable(R.drawable.ic_ico_title_back_white));
                    search.setImageDrawable(getResources().getDrawable(R.drawable.ic_ico_title_search_white));
                    share.setImageDrawable(getResources().getDrawable(R.drawable.ic_ico_title_share));
                }
            }
        });
        showTopic(topicBean);
        if (adapter == null) adapter = new FeedsAdapter();
//        adapter.setListener(mPresenter, mPresenter,mPresenter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));// 布局管理器
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(mPresenter);
        adapter.setOnItemChildClickListener(mPresenter);
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getList(false);
            }

            @Override
            public void onLoadmore() {
                mPresenter.getList(true);
            }
        });

        mPresenter.setId(topicBean.getId(), false);
    }

    @Override
    public void showTopic(TopicBean topicBean) {
        this.topicBean = topicBean;
        if (topicBean.getBg() != null && !topicBean.getBg().isEmpty())
            GlideLoaderUtil.LoadBottomRoundImage20(this, Utils.checkUrl(topicBean.getBg()), topic_detail_header_bg_cover);
        if (topicBean.getAvatar() != null && !topicBean.getAvatar().isEmpty())
            GlideLoaderUtil.LoadRoundImage20(this, Utils.checkUrl(topicBean.getAvatar()), topic_cover);
        topic_name.setText("#" + topicBean.getName() + "#");
        topic_popularity.setText(Utils.getNumberIfPeople(topicBean.getPopularity()) + "人气");
        topic_content.setText("#" + topicBean.getCreated_at() + "#");
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

    public void onFinishFreshAndLoad() {
        springView.onFinishFreshAndLoad();
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
