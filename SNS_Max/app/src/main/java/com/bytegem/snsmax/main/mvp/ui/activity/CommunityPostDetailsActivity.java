package com.bytegem.snsmax.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.main.app.bean.CommunityPostBean;
import com.bytegem.snsmax.main.mvp.ui.adapter.ChatListAdapter;
import com.bytegem.snsmax.main.mvp.ui.adapter.CommunityCommentsAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.bytegem.snsmax.main.di.component.DaggerCommunityPostDetailsComponent;
import com.bytegem.snsmax.main.mvp.contract.CommunityPostDetailsContract;
import com.bytegem.snsmax.main.mvp.presenter.CommunityPostDetailsPresenter;

import com.bytegem.snsmax.R;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;


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
public class CommunityPostDetailsActivity extends BaseActivity<CommunityPostDetailsPresenter> implements CommunityPostDetailsContract.View, View.OnClickListener {
    CommunityPostBean communityPostBean;
    @Inject
    CommunityCommentsAdapter adapter;//最新评论

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
    @BindView(R.id.send_time)
    TextView send_time;
    @BindView(R.id.comment_content)
    TextView comment_content;
    @BindView(R.id.comment_zan_count)
    TextView comment_zan_count;

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
    @BindView(R.id.group)
    LinearLayout group;//圈子相关，如果没有关联圈子  这部分需要隐藏
    EditText commit_content;//发送评论的edittext
    @BindView(R.id.more_img)
    FrameLayout more_img;

    BottomSheetDialog bottomSheetDialog;
    BottomSheetDialog commitBottomSheetDialog;

    @OnClick({R.id.follow_the_user, R.id.join_us, R.id.user_cover, R.id.one_img, R.id.share_to_wechat
            , R.id.share_to_moments, R.id.share_to_qq, R.id.zan_the_post, R.id.comment_the_post, R.id.share_the_post, R.id.tv_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.follow_the_user://关注动态发起人
                mPresenter.changeUserFollowState(communityPostBean.getUser().getId());
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
                mPresenter.changeLikeState(communityPostBean.getId());
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
                    mPresenter.commit(communityPostBean.getId(), content);
                    commit_content.setText("");
                    commitBottomSheetDialog.dismiss();
                }
                break;
        }
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCommunityPostDetailsComponent //如找不到该类,请编译一下项目
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
        communityPostBean = (CommunityPostBean) getIntent().getSerializableExtra("data");
        if (communityPostBean == null) {
            killMyself();
            return;
        }
        initCommitBottomSheetDialog();
        initBottomSheetDialog();
        comment_recycleview.setLayoutManager(new LinearLayoutManager(this));// 布局管理器
        comment_recycleview.setAdapter(adapter);
        comment_recycleview.setItemAnimator(new DefaultItemAnimator());
        springview.setType(SpringView.Type.FOLLOW);
        springview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                springview.setEnableFooter(false);
                mPresenter.getList(false, communityPostBean.getId(), 0);
            }

            @Override
            public void onLoadmore() {
                mPresenter.getList(true, communityPostBean.getId(), 0);
            }
        });

        springview.setEnableFooter(false);
        adapter.setOnItemChildClickListener(mPresenter);
        adapter.setOnItemClickListener(mPresenter);
        springview.setHeader(new DefaultHeader(this));   //参数为：logo图片资源，是否显示文字
        springview.setFooter(new DefaultFooter(this));
        mPresenter.getList(false, communityPostBean.getId(), 0);

    }

    private void initCommitBottomSheetDialog() {
        commitBottomSheetDialog = new BottomSheetDialog(this);
        commitBottomSheetDialog.setContentView(R.layout.view_commit);
        commitBottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet)
                .setBackgroundColor(getResources().getColor(R.color.albumTransparent));
        commitBottomSheetDialog.findViewById(R.id.send).setOnClickListener(this);
        commit_content = commitBottomSheetDialog.findViewById(R.id.commit_content);
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
}
