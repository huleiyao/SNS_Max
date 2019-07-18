package com.bytegem.snsmax.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bytegem.snsmax.main.app.bean.feed.FeedBean;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.bytegem.snsmax.main.di.component.DaggerVideoPlayerComponent;
import com.bytegem.snsmax.main.mvp.ui.adapter.FeedCommentsAdapter;
import com.bytegem.snsmax.main.mvp.ui.adapter.FeedsAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.bytegem.snsmax.main.mvp.contract.VideoPlayerContract;
import com.bytegem.snsmax.main.mvp.presenter.VideoPlayerPresenter;

import com.bytegem.snsmax.R;


import butterknife.BindView;
import butterknife.OnClick;
import cn.ittiger.player.PlayerManager;
import cn.ittiger.player.VideoPlayerView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/17/2019 07:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class VideoPlayerActivity extends BaseActivity<VideoPlayerPresenter> implements VideoPlayerContract.View {
    @BindView(R.id.video_player_view)
    VideoPlayerView video_player_view;

    @BindView(R.id.group)
    View group;

    @BindView(R.id.group_cover)
    ImageView group_cover;
    @BindView(R.id.user_cover)
    ImageView user_cover;
    @BindView(R.id.add_user)
    ImageView add_user;

    @BindView(R.id.user_name)
    TextView user_name;
    @BindView(R.id.like_count)
    TextView like_count;
    @BindView(R.id.omment_count)
    TextView omment_count;
    @BindView(R.id.shape_count)
    TextView shape_count;
    @BindView(R.id.feed_content)
    TextView feed_content;
    @BindView(R.id.group_name)
    TextView group_name;

    private FeedBean mFeedBean;

    @OnClick({R.id.add_user, R.id.video_more, R.id.like_cover, R.id.comment_cover, R.id.shape_cover})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_user:
                if (mFeedBean != null && mFeedBean.getUser() != null && mFeedBean.getUser().getId() > 0)
                    launchActivity(new Intent(this, OwnerHomeActivity.class)
                            .putExtra(OwnerHomeActivity.ISME, false)
                            .putExtra(OwnerHomeActivity.ID, mFeedBean.getUser().getId())
                    );
                break;
            case R.id.video_more:

                break;
            case R.id.like_cover:

                break;
            case R.id.comment_cover:
                if (mFeedBean != null)
                    launchActivity(new Intent(this, FeedDetailsActivity.class)
                            .putExtra("data", mFeedBean)
                    );
                break;
            case R.id.shape_cover:

                break;
        }
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerVideoPlayerComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_video_player; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        FeedBean feedBean = (FeedBean) getIntent().getSerializableExtra("feed");
        if (feedBean == null) {
            killMyself();
            return;
        }
        showFeed(feedBean);
    }

    public void showFeed(FeedBean feedBean) {
        mFeedBean = feedBean;
        GlideLoaderUtil.LoadImage(this, Utils.checkUrl(mFeedBean.getMedia().getMediaVideo().getCover()), video_player_view.getThumbImageView());
        video_player_view.bind(Utils.checkUrl(mFeedBean.getMedia().getMediaVideo().getVideo()), "");
        if (mFeedBean.getUser() != null) {
            user_name.setText(mFeedBean.getUser().getName());
            if (mFeedBean.getUser().getAvatar() == null || mFeedBean.getUser().getAvatar().isEmpty())
                GlideLoaderUtil.LoadCircleImage(this, R.drawable.ic_deskicon, user_cover);
            else
                GlideLoaderUtil.LoadCircleImage(this, Utils.checkUrl(mFeedBean.getUser().getAvatar()), user_cover);

//            GlideLoaderUtil.LoadCircleImage(this, Utils.checkUrl(mFeedBean.getUser().getAvatar()), user_cover);
//            if (mFeedBean.getUser().)@TODO 此用户是否已关注，需显示或隐藏+号图片
            add_user.setVisibility(View.VISIBLE);
        } else add_user.setVisibility(View.GONE);
        if (mFeedBean.getGroup() != null && mFeedBean.getGroup().getId() > 0) {
            group.setVisibility(View.VISIBLE);
            GlideLoaderUtil.LoadImage(this, Utils.checkUrl(mFeedBean.getGroup().getAvatar()), group_cover);
            group_name.setText(mFeedBean.getGroup().getName());
        } else group.setVisibility(View.GONE);
        feed_content.setText(mFeedBean.getContents());
        like_count.setText(Utils.getNumberIfPeople(mFeedBean.getLikes_count()));
//        if (mFeedBean.)@TODO 是否喜欢此动态，需要更改like图片
        omment_count.setText(Utils.getNumberIfPeople(mFeedBean.getComments_count()));
        shape_count.setText(Utils.getNumberIfPeople(mFeedBean.getShare_count()));
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

    @Override
    protected void onResume() {
        super.onResume();
        PlayerManager.getInstance().resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        PlayerManager.getInstance().pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PlayerManager.getInstance().release();
    }

    @Override
    protected void onStop() {
        super.onStop();
        PlayerManager.getInstance().stop();
    }
}
