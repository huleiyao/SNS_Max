package com.bytegem.snsmax.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bytegem.snsmax.main.app.bean.feed.FeedCommentBean;
import com.bytegem.snsmax.main.app.bean.feed.FeedBean;
import com.bytegem.snsmax.main.app.bean.feed.MediaLinkContent;
import com.bytegem.snsmax.main.app.bean.user.UserBean;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.bytegem.snsmax.main.app.utils.MediaUtils;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.bytegem.snsmax.main.mvp.contract.FeedDetailsContract;
import com.bytegem.snsmax.main.mvp.presenter.FeedDetailsPresenter;
import com.bytegem.snsmax.main.mvp.ui.adapter.FeedCommentsAdapter;
import com.bytegem.snsmax.main.mvp.ui.adapter.FeedCommentsOfCommentAdapter;
import com.bytegem.snsmax.main.mvp.ui.adapter.ImageAdapter;
import com.bytegem.snsmax.main.mvp.ui.adapter.ImageAdapter2;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.bytegem.snsmax.main.di.component.DaggerFeedDetailsComponent;

import com.bytegem.snsmax.R;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;


import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/23/2019 15:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class FeedDetailsActivity extends BaseActivity<FeedDetailsPresenter> implements FeedDetailsContract.View, View.OnClickListener {
    FeedBean feedBean;
    @Inject
    FeedCommentsAdapter adapter;//最新评论
    @Inject
    FeedCommentsOfCommentAdapter feedCommentsOfCommentAdapter;//热门评论的二级评论
    @Inject
    ImageAdapter imageAdapter;
    @Inject
    ImageAdapter2 imageAdapter2;

    @BindView(R.id.url_text)
    TextView url_text;
    @BindView(R.id.one_img)
    ImageView one_img;
    @BindView(R.id.is_video)
    ImageView is_video;
    @BindView(R.id.url_cover)
    ImageView url_cover;
    @BindView(R.id.f_one_img)
    FrameLayout f_one_img;
    @BindView(R.id.url)
    LinearLayout url;
    @BindView(R.id.follow_the_user)
    TextView follow_the_user;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.user_content)
    TextView user_content;
    @BindView(R.id.user_name)
    TextView user_name;
    @BindView(R.id.group_name)
    TextView group_name;
    @BindView(R.id.group_cotent)
    TextView group_cotent;
    @BindView(R.id.join_us)
    TextView join_us;
    @BindView(R.id.comment_send_time)
    TextView comment_send_time;
    @BindView(R.id.comment_content)
    TextView comment_content;
    @BindView(R.id.comment_zan_count)
    TextView comment_zan_count;
    @BindView(R.id.comment_user_name)
    TextView comment_user_name;
    @BindView(R.id.zan_the_post_count)
    TextView zan_the_post_count;
    @BindView(R.id.comment_the_post_count)
    TextView comment_the_post_count;
    @BindView(R.id.share_the_post_count)
    TextView share_the_post_count;

    @BindView(R.id.user_cover)
    ImageView user_cover;
    @BindView(R.id.group_cover)
    ImageView group_cover;
    @BindView(R.id.comment_user_cover)
    ImageView comment_user_cover;

    @BindView(R.id.springview)
    SpringView springview;
    @BindView(R.id.comment_recycleview)
    RecyclerView comment_recycleview;//最新评论列表
    @BindView(R.id.comment_image_recycleview)
    RecyclerView comment_image_recycleview;//最热评论的图片列表
    @BindView(R.id.comment_comment_recycleview)
    RecyclerView comment_comment_recycleview;//最热评论的评论列表
    @BindView(R.id.recycle_view)
    RecyclerView recycle_view;//动态图片列表
    @BindView(R.id.more_comment)
    LinearLayout more_comment;
    @BindView(R.id.address)
    LinearLayout address;
    @BindView(R.id.comment_zan)
    LinearLayout comment_zan;
    @BindView(R.id.hot_comment)
    LinearLayout hot_comment;//热门评论
    @BindView(R.id.group)
    LinearLayout group;//圈子相关，如果没有关联圈子  这部分需要隐藏
    EditText commit_content;//发送评论的edittext
    @BindView(R.id.more_img)
    FrameLayout more_img;
    FeedCommentBean hotFeedCommentBean;
    BottomSheetDialog bottomSheetDialog;
    BottomSheetDialog commitBottomSheetDialog;

    @OnClick({R.id.follow_the_user, R.id.join_us, R.id.user_cover, R.id.one_img, R.id.share_to_wechat
            , R.id.share_to_moments, R.id.share_to_qq, R.id.zan_the_post, R.id.comment_the_post
            , R.id.share_the_post, R.id.tv_address, R.id.bg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.follow_the_user://关注动态发起人
                mPresenter.changeUserFollowState(feedBean.getUser().getId());
                break;
            case R.id.user_cover://动态发起人
                showMessage("进入用户信息界面");
                break;
            case R.id.one_img://只有一张图的时候   从这里打开全图

                break;
            case R.id.join_us://只有一张图的时候   从这里打开全图
                showMessage("加入圈子");
                break;
            case R.id.share_to_wechat://分享到微信
                showMessage("分享到微信");
                break;
            case R.id.share_to_moments://朋友圈
                showMessage("分享到朋友圈");
                break;
            case R.id.share_to_qq://qq
                showMessage("分享到qq");
                break;
            case R.id.zan_the_post://底部赞
                mPresenter.changeLikeState(feedBean.getId());
                break;
            case R.id.comment_the_post://发评论
                bottomSheetDialog.show();
                break;
            case R.id.share_the_post://分享
                showMessage("去分享");
                break;
            case R.id.tv_address://地址
                showMessage("地址，定位");
                break;
            case R.id.to_commit:
                bottomSheetDialog.dismiss();
                commitBottomSheetDialog.show();
                break;
            case R.id.to_report:
                showMessage("举报");
                bottomSheetDialog.dismiss();
                break;
            case R.id.cancel:
                bottomSheetDialog.dismiss();
                break;
            case R.id.send:
                if (commit_content != null) {
                    String content = commit_content.getText().toString();
                    if (content.isEmpty()) {
//                        showMessage("请输入评论内容");
                        commit_content.setError("请输入评论内容");
                        return;
                    }
                    mPresenter.commit(feedBean.getId(), content);
                    commit_content.setText("");
                    commitBottomSheetDialog.dismiss();
                }
                break;
            case R.id.bg:
                //热门评论
                if (hotFeedCommentBean != null)
                    launchActivity(new Intent(this, FeedCommentsOfCommentActivity.class)
                            .putExtra(FeedCommentsOfCommentActivity.FEED_ID, feedBean.getId())
                            .putExtra(FeedCommentsOfCommentActivity.COMMENT_ID, hotFeedCommentBean));
                break;
        }
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFeedDetailsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_community_post_details; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("贴子详情");
        feedBean = (FeedBean) getIntent().getSerializableExtra("data");
        if (feedBean == null) {
            killMyself();
            return;
        }
        showFeed(feedBean);
        initCommitBottomSheetDialog();
        initBottomSheetDialog();
        comment_recycleview.setLayoutManager(new LinearLayoutManager(this));// 布局管理器
        comment_recycleview.setAdapter(adapter);
        comment_recycleview.setItemAnimator(new DefaultItemAnimator());

        comment_comment_recycleview.setLayoutManager(new LinearLayoutManager(this));// 布局管理器
        comment_comment_recycleview.setAdapter(feedCommentsOfCommentAdapter);
        comment_comment_recycleview.setItemAnimator(new DefaultItemAnimator());

        springview.setType(SpringView.Type.FOLLOW);
        springview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                springview.setEnableFooter(false);
                mPresenter.getList(false, feedBean.getId(), 0);
                mPresenter.getHotComment(feedBean.getId());
            }

            @Override
            public void onLoadmore() {
                mPresenter.getList(true, feedBean.getId(), 0);
            }
        });

        springview.setEnableFooter(false);
        adapter.setOnItemChildClickListener(mPresenter);
        adapter.setOnItemClickListener(mPresenter);
        springview.setHeader(new DefaultHeader(this));   //参数为：logo图片资源，是否显示文字
        springview.setFooter(new DefaultFooter(this));
        mPresenter.getList(false, feedBean.getId(), 0);
        mPresenter.getHotComment(feedBean.getId());
    }

    private void showFeed(FeedBean feedBean) {
        url.setVisibility(View.GONE);
        more_img.setVisibility(View.GONE);
        f_one_img.setVisibility(View.GONE);
        is_video.setVisibility(View.GONE);
        more_img.setVisibility(View.GONE);
        imageAdapter2.setOnItemChildClickListener(mPresenter);
        imageAdapter2.setOnItemClickListener(mPresenter);
        imageAdapter.setOnItemChildClickListener(mPresenter);
        imageAdapter.setOnItemClickListener(mPresenter);
        this.feedBean = feedBean;
        UserBean user = feedBean.getUser();
        if (user != null) {
            GlideLoaderUtil.LoadCircleImage(this, feedBean.getUser().getAvatar(), user_cover);
            user_name.setText(feedBean.getUser().getName());
//            user_content.setText(feedBean.getUser().getName());
        }
        content.setText(feedBean.getContents());
        zan_the_post_count.setText(feedBean.getLikes_count() + "");
        comment_the_post_count.setText(feedBean.getComments_count() + "");
        share_the_post_count.setText(feedBean.getShare_count() + "");
        if (feedBean.getMedia() != null)
            switch (feedBean.getMedia().getType()) {
                case "image":
                    if (feedBean.getMedia().getImageList() != null && feedBean.getMedia().getImageList().size() > 0) {
                        more_img.setVisibility(View.VISIBLE);
                        if (feedBean.getMedia().getImageList().size() == 1) {
                            f_one_img.setVisibility(View.VISIBLE);
                            GlideLoaderUtil.LoadRoundImage20(this, Utils.checkUrl(feedBean.getMedia().getImageList().get(0)), one_img);
                        } else {
                            recycle_view.setVisibility(View.VISIBLE);
                            if (feedBean.getMedia().getImageList().size() == 2) {
                                recycle_view.setLayoutManager(new GridLayoutManager(this, 2));// 布局管理器
                                recycle_view.setAdapter(imageAdapter2);
                                imageAdapter2.setNewData(feedBean.getMedia().getImageList());
                            } else {
                                recycle_view.setLayoutManager(new GridLayoutManager(this, 3));// 布局管理器
                                recycle_view.setAdapter(imageAdapter);
                                imageAdapter.setNewData(feedBean.getMedia().getImageList());
                            }
                            recycle_view.setItemAnimator(new DefaultItemAnimator());
                        }
                    }
                    break;
                case "video":
                    f_one_img.setVisibility(View.VISIBLE);
                    is_video.setVisibility(View.VISIBLE);
                    more_img.setVisibility(View.VISIBLE);
                    MediaUtils.getImageForVideo(Utils.checkUrl(feedBean.getMedia().getMediaVideo().getVideo()), new MediaUtils.OnLoadVideoImageListener() {
                        @Override
                        public void onLoadImage(File file) {
                            GlideLoaderUtil.LoadRoundImage20(FeedDetailsActivity.this, file, one_img);
                        }
                    });
                    break;
                case "url":
                    MediaLinkContent mediaLinkContent = feedBean.getMedia().getMediaLink();
                    if (mediaLinkContent == null) break;
                    url.setVisibility(View.VISIBLE);
                    more_img.setVisibility(View.VISIBLE);
                    url_text.setText(mediaLinkContent.getTitle() == null ? "" : mediaLinkContent.getTitle());
                    if (mediaLinkContent.getImage() != null && !mediaLinkContent.getImage().isEmpty())
                        GlideLoaderUtil.LoadRoundImage(this, Utils.checkUrl(mediaLinkContent.getUrl()), url_cover);
                    else
                        url_cover.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_img_link));
                    break;
            }
    }

    private void initCommitBottomSheetDialog() {
        commitBottomSheetDialog = new BottomSheetDialog(this);
        commitBottomSheetDialog.setContentView(R.layout.view_commit);
        commitBottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet)
                .setBackgroundColor(getResources().getColor(R.color.albumTransparent));
        commitBottomSheetDialog.findViewById(R.id.send).setOnClickListener(this);
        commit_content = commitBottomSheetDialog.findViewById(R.id.commit_content);
        commit_content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {//发送按键action
                    if (commit_content != null) {
                        String content = commit_content.getText().toString();
                        if (content.isEmpty()) {
//                        showMessage("请输入评论内容");
                            commit_content.setError("请输入评论内容");
                            return true;
                        }
                        mPresenter.commit(feedBean.getId(), content);
                        commit_content.setText("");
                        commitBottomSheetDialog.dismiss();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void initBottomSheetDialog() {
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.dialog_community_commit);
        bottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet)
                .setBackgroundColor(getResources().getColor(R.color.albumTransparent));
        bottomSheetDialog.findViewById(R.id.to_commit).setOnClickListener(this);
        bottomSheetDialog.findViewById(R.id.to_report).setOnClickListener(this);
        bottomSheetDialog.findViewById(R.id.cancel).setOnClickListener(this);
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
    public void onFinishFreshAndLoad() {
        springview.onFinishFreshAndLoad();
    }

    @Override
    public void showHotComment(FeedCommentBean feedCommentBean) {
        if (feedCommentBean == null) hot_comment.setVisibility(View.GONE);
        else {
            hot_comment.setVisibility(View.VISIBLE);
            hotFeedCommentBean = feedCommentBean;
            GlideLoaderUtil.LoadCircleImage(this, Utils.checkUrl(hotFeedCommentBean.getUserBean().getAvatar()), comment_user_cover);
            comment_user_name.setText(hotFeedCommentBean.getUserBean().getName());
            comment_send_time.setText(hotFeedCommentBean.getCreated_at());
            comment_zan_count.setText(hotFeedCommentBean.getLikes_count() + "");
            comment_content.setText(hotFeedCommentBean.getContents());
            ArrayList<FeedCommentBean> feedCommentBeans = hotFeedCommentBean.getComments();
            if (feedCommentBeans != null && feedCommentBeans.size() > 0) {
                comment_comment_recycleview.setVisibility(View.VISIBLE);
                feedCommentsOfCommentAdapter.setNewData(feedCommentBeans);
            } else comment_comment_recycleview.setVisibility(View.GONE);
        }
    }
}
