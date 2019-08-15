package com.bytegem.snsmax.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.view.ViewPager;
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

import com.afollestad.materialdialogs.MaterialDialog;
import com.bytegem.snsmax.common.adapter.VPFragmentAdapter;
import com.bytegem.snsmax.common.bean.FragmentBean;
import com.bytegem.snsmax.main.app.bean.feed.FeedBean;
import com.bytegem.snsmax.main.app.bean.feed.MediaLinkContent;
import com.bytegem.snsmax.main.app.bean.user.UserBean;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.bytegem.snsmax.main.mvp.contract.FeedDetailsContract;
import com.bytegem.snsmax.main.mvp.presenter.FeedDetailsPresenter;
import com.bytegem.snsmax.main.mvp.ui.adapter.ImageAdapter;
import com.bytegem.snsmax.main.mvp.ui.adapter.ImageAdapter2;
import com.bytegem.snsmax.main.mvp.ui.dialog.CommitFeedCommentBottomSheetDialog;
import com.bytegem.snsmax.main.mvp.ui.fragment.FeedDetailFragment;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.bytegem.snsmax.main.di.component.DaggerFeedDetailsComponent;

import com.bytegem.snsmax.R;
import com.lzy.imagepicker.ImagePicker;


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
    ImageAdapter imageAdapter;
    @Inject
    ImageAdapter2 imageAdapter2;

    @BindView(R.id.feed_detail_appbar)
    AppBarLayout appbar;
    @BindView(R.id.feed_detail_head_layout)
    LinearLayout head_layout;
    @BindView(R.id.toolbar_title_center)
    TextView toolbar_title_center;
    @BindView(R.id.post_detail_url_text)
    TextView url_text;
    @BindView(R.id.post_detail_one_img)
    ImageView one_img;
    @BindView(R.id.post_detail_is_video)
    ImageView is_video;
    @BindView(R.id.post_detail_url_cover)
    ImageView url_cover;
    @BindView(R.id.feed_detail_zan_the_post_img)
    ImageView feed_detail_zan_the_post_img;

    @BindView(R.id.post_detail_f_one_img)
    FrameLayout f_one_img;
    @BindView(R.id.post_detail_more_img)
    FrameLayout more_img;

    @BindView(R.id.post_detail_url)
    LinearLayout url;


    @BindView(R.id.post_detail_follow_the_user)
    TextView follow_the_user;
    @BindView(R.id.post_detail_title_follow_the_user)
    TextView title_follow_the_user;
    @BindView(R.id.post_detail_tv_address)
    TextView tv_address;
    @BindView(R.id.post_detail_content)
    TextView content;
    @BindView(R.id.post_detail_user_content)
    TextView user_content;
    @BindView(R.id.post_detail_user_name)
    TextView user_name;


    @BindView(R.id.feed_detail_zan_the_post_count)
    TextView zan_the_post_count;
    @BindView(R.id.feed_detail_comment_the_post_count)
    TextView comment_the_post_count;
    @BindView(R.id.feed_detail_share_the_post_count)
    TextView share_the_post_count;

    @BindView(R.id.post_detail_user_cover)
    ImageView user_cover;

    EditText commit_content;//发送评论的edittext
    @BindView(R.id.post_detail_address)
    LinearLayout address;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.more)
    TextView more;
    @BindView(R.id.title_cover)
    ImageView title_cover;
    @BindView(R.id.projectPager)
    ViewPager viewPager;
    @BindView(R.id.recycle_view)
    RecyclerView recycle_view;//动态图片列表
    BottomSheetDialog bottomSheetDialog;
    CommitFeedCommentBottomSheetDialog commitBottomSheetDialog;

    @OnClick({R.id.post_detail_follow_the_user, R.id.post_detail_user_cover, R.id.post_detail_one_img, R.id.post_detail_share_to_wechat
            , R.id.post_detail_share_to_moments, R.id.post_detail_share_to_qq, R.id.feed_detail_zan_the_post, R.id.feed_detail_comment_the_post
            , R.id.feed_detail_share_the_post, R.id.post_detail_tv_address, R.id.more, R.id.post_detail_title_follow_the_user})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.post_detail_title_follow_the_user://关注动态发起人
            case R.id.post_detail_follow_the_user://关注动态发起人
                mPresenter.changeUserFollowState(feedBean.getUser().getId());
                break;
            case R.id.post_detail_user_cover://动态发起人
                launchActivity(new Intent(this, OwnerHomeActivity.class)
                        .putExtra(OwnerHomeActivity.ISME, false)
                        .putExtra(OwnerHomeActivity.ID, feedBean.getUser().getId())
                );
                break;
            case R.id.post_detail_one_img://只有一张图的时候   从这里打开全图,或去看视频
                switch (feedBean.getMedia().getType()) {
                    case "image":
                        launchActivity(new Intent(this, WatchImageActivity.class)
                                .putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, feedBean.getMedia().getImageList())
                                .putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0)
                                .putExtra(ImagePicker.EXTRA_FROM_ITEMS, true)
                        );
                        break;
                    case "video":
                        launchActivity(new Intent(this, VideoPlayerActivity.class).putExtra("feed", feedBean));
                        break;
                }
                break;
            case R.id.post_detail_share_to_wechat://分享到微信
                showMessage("分享到微信");
                break;
            case R.id.post_detail_share_to_moments://朋友圈
                showMessage("分享到朋友圈");
                break;
            case R.id.post_detail_share_to_qq://qq
                showMessage("分享到qq");
                break;
            case R.id.feed_detail_zan_the_post://底部赞
                mPresenter.changeLikeState(feedBean.getId(), feedBean.isHas_liked());
//                feedBean.setHas_liked(!feedBean.isHas_liked());
//                int likeCount = feedBean.getLikes_count();
//                if (feedBean.isHas_liked()) feedBean.setLikes_count(likeCount++);
//                else if (likeCount > 0) feedBean.setLikes_count(likeCount--);
//                else ;
//                changeFeedLike();
                break;
            case R.id.more://顶部右边更多按钮
                bottomSheetDialog.show();
                break;
            case R.id.feed_detail_comment_the_post://发评论
                commitBottomSheetDialog.show();
//                bottomSheetDialog.show();
                break;
            case R.id.feed_detail_share_the_post://分享
                showMessage("去分享");
                break;
            case R.id.post_detail_tv_address://地址
//                showMessage("地址，定位");
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
            case R.id.dialog_send_comment://没有使用
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
        feedBean = (FeedBean) getIntent().getSerializableExtra("data");
        if (feedBean == null) {
            killMyself();
            return;
        }
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -head_layout.getHeight() / 2) {
                    toolbar_title_center.setText(" ");
                    toolbar_title.setText(feedBean.getUser().getName());
                    toolbar_title.setTextColor(getResources().getColor(R.color.color_151b26));
                    toolbar_title_center.setVisibility(View.GONE);
                    more.setVisibility(View.GONE);
                    toolbar_title.setVisibility(View.VISIBLE);
                    title_cover.setVisibility(View.VISIBLE);
                    title_follow_the_user.setVisibility(View.VISIBLE);
                } else {
                    toolbar_title_center.setText("帖子详情");
                    toolbar_title.setText(" ");
                    toolbar_title.setTextColor(getResources().getColor(R.color.white));
                    toolbar_title_center.setVisibility(View.VISIBLE);
                    more.setVisibility(View.VISIBLE);
                    toolbar_title.setVisibility(View.GONE);
                    title_cover.setVisibility(View.GONE);
                    title_follow_the_user.setVisibility(View.GONE);
                }
            }
        });
        ArrayList<FragmentBean> fragmentList = new ArrayList<>();
        fragmentList.add(new FragmentBean("详情", FeedDetailFragment.newInstance(feedBean)));
        viewPager.setAdapter(new VPFragmentAdapter(getSupportFragmentManager(), fragmentList));

        showFeed(feedBean);
        initCommitBottomSheetDialog();
        initBottomSheetDialog();
        mPresenter.getFeedInfo(feedBean.getId());
    }

    private void changeFeedLike() {
        if (feedBean.isHas_liked()) {
            feed_detail_zan_the_post_img.setImageResource(R.drawable.ic_ico_moment_zan_on);
            zan_the_post_count.setTextColor(getResources().getColor(R.color.color_EC414B));
        } else {
            feed_detail_zan_the_post_img.setImageResource(R.drawable.ic_ico_moment_zan);
            zan_the_post_count.setTextColor(getResources().getColor(R.color.color_9CA6AF));
        }
        zan_the_post_count.setText(feedBean.getLikes_count() + "");
    }

    @Override
    public void showFeed(FeedBean feedBean) {
        url.setVisibility(View.GONE);
        more_img.setVisibility(View.GONE);
        f_one_img.setVisibility(View.GONE);
        is_video.setVisibility(View.GONE);
        more_img.setVisibility(View.GONE);
        imageAdapter2.setOnItemClickListener(mPresenter);
        imageAdapter.setOnItemClickListener(mPresenter);
        this.feedBean = feedBean;
        UserBean user = feedBean.getUser();
        if (user != null) {
            if (feedBean.getUser().getAvatar() == null || feedBean.getUser().getAvatar().isEmpty())
                GlideLoaderUtil.LoadCircleImage(this, R.drawable.ic_deskicon, user_cover, title_cover);
            else
                GlideLoaderUtil.LoadCircleImage(this, Utils.checkUrl(feedBean.getUser().getAvatar()), user_cover, title_cover);
            user_name.setText(feedBean.getUser().getName());
//            user_content.setText(feedBean.getUser().getName());
        }
        if (feedBean.getGeo() != null && !feedBean.getGeo().getAddress().isEmpty()) {
            address.setVisibility(View.VISIBLE);
            tv_address.setText(feedBean.getGeo().getAddress());
        } else address.setVisibility(View.GONE);
        changeFeedLike();
        content.setText(feedBean.getContents());
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
                    GlideLoaderUtil.LoadRoundImage20(FeedDetailsActivity.this, Utils.checkUrl(feedBean.getMedia().getMediaVideo().getCover()), one_img);
//                    MediaUtils.getImageForVideo(Utils.checkUrl(feedBean.getMedia().getMediaVideo().getVideo()), new MediaUtils.OnLoadVideoImageListener() {
//                        @Override
//                        public void onLoadImage(File file) {
//                            GlideLoaderUtil.LoadRoundImage20(FeedDetailsActivity.this, file, one_img);
//                        }
//                    });
                    break;
                case "url":
                    MediaLinkContent mediaLinkContent = feedBean.getMedia().getMediaLink();
                    if (mediaLinkContent == null) break;
                    url.setVisibility(View.VISIBLE);
                    more_img.setVisibility(View.VISIBLE);
                    url_text.setText(mediaLinkContent.getTitle() == null ? "" : mediaLinkContent.getTitle());
                    if (mediaLinkContent.getImage() != null && !mediaLinkContent.getImage().isEmpty())
                        GlideLoaderUtil.LoadRoundImage(this, Utils.checkUrl(mediaLinkContent.getImage()), url_cover);
                    else
                        url_cover.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_img_link));
                    break;
            }
    }

    private void initCommitBottomSheetDialog() {
        commitBottomSheetDialog = new CommitFeedCommentBottomSheetDialog(this
                , this
                , new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {//发送按键action
                    if (textView != null) {
                        String content = textView.getText().toString();
                        if (content.isEmpty()) {
                            textView.setError("请输入评论内容");
                            return true;
                        }
                        mPresenter.commit(feedBean.getId(), content);
                        textView.setText("");
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
    public void getList() {

    }
}
