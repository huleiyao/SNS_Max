package com.bytegem.snsmax.main.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bytegem.snsmax.main.app.bean.feed.FeedBean;
import com.bytegem.snsmax.main.app.bean.feed.FeedCommentBean;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.bytegem.snsmax.main.mvp.ui.activity.FeedCommentsOfCommentActivity;
import com.bytegem.snsmax.main.mvp.ui.activity.FeedNewCommentActivity;
import com.bytegem.snsmax.main.mvp.ui.activity.OwnerHomeActivity;
import com.bytegem.snsmax.main.mvp.ui.activity.VideoPlayerActivity;
import com.bytegem.snsmax.main.mvp.ui.activity.WatchImageActivity;
import com.bytegem.snsmax.main.mvp.ui.adapter.FeedCommentsAdapter;
import com.bytegem.snsmax.main.mvp.ui.adapter.FeedCommentsOfCommentAdapter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.bytegem.snsmax.main.di.component.DaggerFeedDetailComponent;
import com.bytegem.snsmax.main.mvp.contract.FeedDetailContract;
import com.bytegem.snsmax.main.mvp.presenter.FeedDetailPresenter;

import com.bytegem.snsmax.R;
import com.liaoinstan.springview.widget.SpringView;
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
 * Created by MVPArmsTemplate on 08/06/2019 12:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class FeedDetailFragment extends BaseFragment<FeedDetailPresenter> implements FeedDetailContract.View {
    @Inject
    FeedCommentsAdapter adapter;//最新评论
    @Inject
    FeedCommentsOfCommentAdapter feedCommentsOfCommentAdapter;//热门评论的二级评论
    @BindView(R.id.feed_detail_group_cover)
    ImageView group_cover;
    @BindView(R.id.comment_user_cover)
    ImageView comment_user_cover;

    @BindView(R.id.comment_send_time)
    TextView comment_send_time;
    @BindView(R.id.comment_content)
    TextView comment_content;
    @BindView(R.id.comment_zan_count)
    TextView comment_zan_count;
    @BindView(R.id.comment_user_name)
    TextView comment_user_name;
    @BindView(R.id.feed_detail_group_name)
    TextView group_name;
    @BindView(R.id.feed_detail_group_cotent)
    TextView group_cotent;
    @BindView(R.id.feed_detail_group_join_us)
    TextView join_us;
    @BindView(R.id.more_comment)
    LinearLayout more_comment;

    @BindView(R.id.springview)
    SpringView springview;
    @BindView(R.id.comment_recycleview)
    RecyclerView comment_recycleview;//最新评论列表
    @BindView(R.id.comment_image_recycleview)
    RecyclerView comment_image_recycleview;//最热评论的图片列表
    @BindView(R.id.comment_comment_recycleview)
    RecyclerView comment_comment_recycleview;//最热评论的评论列表

    @BindView(R.id.comment_zan)
    LinearLayout comment_zan;
    @BindView(R.id.hot_comment)
    LinearLayout hot_comment;//热门评论
    @BindView(R.id.group)
    LinearLayout group;//圈子相关，如果没有关联圈子  这部分需要隐藏
    FeedBean feedBean;
    FeedCommentBean hotFeedCommentBean;

    @OnClick({R.id.feed_detail_group_join_us, R.id.bg, R.id.more_comment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.feed_detail_group_join_us:
                showMessage("加入圈子");
                break;
            case R.id.bg:
                //热门评论
                if (hotFeedCommentBean != null)
                    launchActivity(new Intent(getContext(), FeedCommentsOfCommentActivity.class)
                            .putExtra(FeedCommentsOfCommentActivity.FEED_ID, feedBean.getId())
                            .putExtra(FeedCommentsOfCommentActivity.COMMENT_ID, hotFeedCommentBean));
                break;
            case R.id.more_comment:
                //进入最新评论全部列表界面
                launchActivity(new Intent(getContext(), FeedNewCommentActivity.class)
                        .putExtra("feedId", feedBean.getId()));
                break;
        }
    }


    public static FeedDetailFragment newInstance(FeedBean feedBean) {
        FeedDetailFragment fragment = new FeedDetailFragment();
        fragment.setFeedBean(feedBean);
        return fragment;
    }

    public void setFeedBean(FeedBean feedBean) {
        this.feedBean = feedBean;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerFeedDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed_detail, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (feedBean == null) {
            return;
        }
        adapter.setAll(false);
        comment_recycleview.setNestedScrollingEnabled(false);
        comment_recycleview.setLayoutManager(new LinearLayoutManager(getContext()));// 布局管理器
        comment_recycleview.setAdapter(adapter);
        comment_recycleview.setItemAnimator(new DefaultItemAnimator());

        comment_comment_recycleview.setLayoutManager(new LinearLayoutManager(getContext()));// 布局管理器
        comment_comment_recycleview.setAdapter(feedCommentsOfCommentAdapter);
        comment_comment_recycleview.setItemAnimator(new DefaultItemAnimator());

        springview.setType(SpringView.Type.FOLLOW);
        springview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getList(false, feedBean.getId(), 0);
                mPresenter.getHotComment(feedBean.getId());
            }

            @Override
            public void onLoadmore() {
                mPresenter.getList(true, feedBean.getId(), adapter.getItem(adapter.getItemCount() - 1).getId());
            }
        });

        adapter.setOnItemChildClickListener(mPresenter);
        adapter.setOnItemClickListener(mPresenter);

        mPresenter.getList(false, feedBean.getId(), 0);
        mPresenter.getHotComment(feedBean.getId());
    }

    @Override
    public void showHotComment(FeedCommentBean feedCommentBean) {
        if (feedCommentBean == null) hot_comment.setVisibility(View.GONE);
        else {
            hot_comment.setVisibility(View.VISIBLE);
            hotFeedCommentBean = feedCommentBean;
            if (hotFeedCommentBean.getUserBean().getAvatar() == null || hotFeedCommentBean.getUserBean().getAvatar().isEmpty())
                GlideLoaderUtil.LoadCircleImage(getContext(), R.drawable.ic_deskicon, comment_user_cover);
            else
                GlideLoaderUtil.LoadCircleImage(getContext(), Utils.checkUrl(hotFeedCommentBean.getUserBean().getAvatar()), comment_user_cover);

//            GlideLoaderUtil.LoadCircleImage(this, Utils.checkUrl(hotFeedCommentBean.getUserBean().getAvatar()), comment_user_cover);
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

    @Override
    public void onFinishFreshAndLoad() {
        springview.onFinishFreshAndLoad();
    }

    public void getList() {
        mPresenter.getList(false, feedBean.getId(), 0);
    }

    /**
     * 通过此方法可以使 Fragment 能够与外界做一些交互和通信, 比如说外部的 Activity 想让自己持有的某个 Fragment 对象执行一些方法,
     * 建议在有多个需要与外界交互的方法时, 统一传 {@link Message}, 通过 what 字段来区分不同的方法, 在 {@link #setData(Object)}
     * 方法中就可以 {@code switch} 做不同的操作, 这样就可以用统一的入口方法做多个不同的操作, 可以起到分发的作用
     * <p>
     * 调用此方法时请注意调用时 Fragment 的生命周期, 如果调用 {@link #setData(Object)} 方法时 {@link Fragment#onCreate(Bundle)} 还没执行
     * 但在 {@link #setData(Object)} 里却调用了 Presenter 的方法, 是会报空的, 因为 Dagger 注入是在 {@link Fragment#onCreate(Bundle)} 方法中执行的
     * 然后才创建的 Presenter, 如果要做一些初始化操作,可以不必让外部调用 {@link #setData(Object)}, 在 {@link #initData(Bundle)} 中初始化就可以了
     * <p>
     * Example usage:
     * <pre>
     * public void setData(@Nullable Object data) {
     *     if (data != null && data instanceof Message) {
     *         switch (((Message) data).what) {
     *             case 0:
     *                 loadData(((Message) data).arg1);
     *                 break;
     *             case 1:
     *                 refreshUI();
     *                 break;
     *             default:
     *                 //do something
     *                 break;
     *         }
     *     }
     * }
     *
     * // call setData(Object):
     * Message data = new Message();
     * data.what = 0;
     * data.arg1 = 1;
     * fragment.setData(data);
     * </pre>
     *
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
    @Override
    public void setData(@Nullable Object data) {

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

    }
}
