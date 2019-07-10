package com.bytegem.snsmax.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.bytegem.snsmax.main.app.bean.feed.FeedCommentBean;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.bytegem.snsmax.main.mvp.ui.adapter.FeedCommentsAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.bytegem.snsmax.main.di.component.DaggerFeedCommentsOfCommentComponent;
import com.bytegem.snsmax.main.mvp.contract.FeedCommentsOfCommentContract;
import com.bytegem.snsmax.main.mvp.presenter.FeedCommentsOfCommentPresenter;

import com.bytegem.snsmax.R;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/01/2019 21:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class FeedCommentsOfCommentActivity extends BaseActivity<FeedCommentsOfCommentPresenter> implements FeedCommentsOfCommentContract.View {
    @BindView(R.id.springview)
    SpringView springview;
    @BindView(R.id.recycle_view)
    RecyclerView recycle_view;
    @BindView(R.id.comment)
    EditText comment;
    @Inject
    FeedCommentsAdapter adapter;
    int feedId;
    public static final String FEED_ID = "feed-id";
    public static final String COMMENT_ID = "feedCommentBean";
    FeedCommentBean feedCommentBean;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFeedCommentsOfCommentComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @OnClick(R.id.send)
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.send:
                String content = comment.getText().toString();
                if (content.isEmpty()) {
                    comment.setError("请输入评论内容");
                    return;
                }
                mPresenter.commit(content);
                comment.setText("");
                break;
        }
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_community_post_comments_of_comment; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        feedId = getIntent().getIntExtra(FEED_ID, 0);
        feedCommentBean = (FeedCommentBean) getIntent().getSerializableExtra(COMMENT_ID);
        if (feedId == 0 || feedCommentBean == null) {
            killMyself();
            return;
        }
        setTitle(feedCommentBean.getComments_count() + "条回复");
        adapter.setLevel_2(true);
        mPresenter.setFeedCommentBean(feedCommentBean);
        recycle_view.setLayoutManager(new LinearLayoutManager(this));// 布局管理器
        recycle_view.setAdapter(adapter);
        recycle_view.setItemAnimator(new DefaultItemAnimator());
        springview.setType(SpringView.Type.FOLLOW);
        springview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                springview.setEnableFooter(false);
                mPresenter.getList(false);
            }

            @Override
            public void onLoadmore() {
                mPresenter.getList(true);
            }
        });

        springview.setEnableFooter(false);
        adapter.setOnItemChildClickListener(mPresenter);
        adapter.setOnItemClickListener(mPresenter);
        springview.setHeader(new DefaultHeader(this));   //参数为：logo图片资源，是否显示文字
        springview.setFooter(new DefaultFooter(this));
        mPresenter.setId(false, feedId, feedCommentBean.getId());

        comment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {//发送按键action
                    Utils.hideKeyboard(FeedCommentsOfCommentActivity.this);
                    String content = comment.getText().toString();
                    if (content.isEmpty()) comment.setError("请输入评论内容");
                    else {
                        mPresenter.commit(content);
                        comment.setText("");
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onFinishFreshAndLoad() {
        springview.onFinishFreshAndLoad();
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