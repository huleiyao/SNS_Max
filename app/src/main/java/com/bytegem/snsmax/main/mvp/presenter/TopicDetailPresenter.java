package com.bytegem.snsmax.main.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.bean.feed.FeedBean;
import com.bytegem.snsmax.main.app.bean.feed.LISTFeeds;
import com.bytegem.snsmax.main.app.bean.topic.DATATopic;
import com.bytegem.snsmax.main.app.bean.topic.TopicBean;
import com.bytegem.snsmax.main.app.widget.TagTextView;
import com.bytegem.snsmax.main.mvp.ui.activity.FeedDetailsActivity;
import com.bytegem.snsmax.main.mvp.ui.activity.TopicDetailActivity;
import com.bytegem.snsmax.main.mvp.ui.activity.VideoPlayerActivity;
import com.bytegem.snsmax.main.mvp.ui.adapter.FeedsAdapter;
import com.bytegem.snsmax.main.mvp.ui.adapter.ImageAdapter;
import com.bytegem.snsmax.main.mvp.ui.adapter.ImageAdapter2;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import javax.inject.Inject;

import com.bytegem.snsmax.main.mvp.contract.TopicDetailContract;
import com.jess.arms.utils.RxLifecycleUtils;
import com.lzy.imagepicker.ImagePicker;
import com.bytegem.snsmax.main.mvp.ui.activity.WatchImageActivity;

import java.util.ArrayList;


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
@ActivityScope
public class TopicDetailPresenter extends BasePresenter<TopicDetailContract.Model, TopicDetailContract.View> implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    FeedsAdapter adapter;
    private int id;
    private int limit = 15;
    boolean isDefaultOrder = true;

    @Inject
    public TopicDetailPresenter(TopicDetailContract.Model model, TopicDetailContract.View rootView) {
        super(model, rootView);
    }

    public void setId(int id, boolean isLoadMore) {
        this.id = id;
        getTopicData();
        getList(isLoadMore);
    }

    public void getTopicData() {
        mRootView.showLoading();
        mModel.getTopicData(id)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<DATATopic>(mErrorHandler) {
                    @Override
                    public void onNext(DATATopic data) {
                        if (data != null && data.getData() != null)
                            mRootView.showTopic(data.getData());
                    }
                });
    }

    public void getList(boolean isLoadMore) {
        if (!isLoadMore)
            mRootView.showLoading();
        mModel.getList(!isLoadMore, id, limit, isDefaultOrder
                , adapter.getItemCount() == 0 ? 0
                        : adapter.getItem(adapter.getItemCount() - 1).getId())
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<LISTFeeds>(mErrorHandler) {
                    @Override
                    public void onNext(LISTFeeds data) {
                        ArrayList<FeedBean> feedBeans = data.getData();
                        for (FeedBean feedBean : feedBeans)
                            if (feedBean.getMedia() != null)
                                feedBean.getMedia().initContent();
                        if (isLoadMore) adapter.addData(feedBeans);
                        else adapter.setNewData(feedBeans);
                        mRootView.onFinishFreshAndLoad();
                        mRootView.hideLoading();
                    }
                });
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.feed_item_f_one_img:
                if (adapter instanceof FeedsAdapter) {
                    FeedBean feedBean = (FeedBean) adapter.getItem(position);
                    switch (feedBean.getMedia().getType()) {
                        case "image":
                            mRootView.launchActivity(new Intent(mApplication, WatchImageActivity.class)
                                    .putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, feedBean.getMedia().getImageList())
                                    .putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0)
                                    .putExtra(ImagePicker.EXTRA_FROM_ITEMS, true)
                            );
                            break;
                        case "video":
                            mRootView.launchActivity(new Intent(mApplication, VideoPlayerActivity.class).putExtra("feed", feedBean));
                            break;
                    }
                }
                break;
            case R.id.feed_item_content:
                if (adapter instanceof FeedsAdapter) {
                    TopicBean topicBean = ((TagTextView) view).getmTopicBean();
                    if (topicBean != null)
                        mRootView.launchActivity(new Intent(mApplication, TopicDetailActivity.class).putExtra("topic", topicBean));
                    else
                        mRootView.launchActivity(new Intent(mApplication, FeedDetailsActivity.class).putExtra("data", (FeedBean) adapter.getItem(position)));
                }
                break;
//            case R.id.tv_tag:点击事件无效，不需要了
//                if (adapter instanceof FeedsAdapter) {
//                    mRootView.launchActivity(new Intent(mApplication, TopicDetailActivity.class).putExtra("topic", ((FeedsAdapter) adapter).getItem(position).getTopic()));
//                }
//                break;
            case R.id.feed_item_zan:
                FeedBean feedBean = (FeedBean) adapter.getItem(position);
                feedBean.setHas_liked(!feedBean.isHas_liked());
                if (feedBean.isHas_liked()) {
                    ((ImageView) view.findViewById(R.id.feed_item_zan_cover)).setImageResource(R.drawable.ic_ico_moment_zan_on);
                } else {
                    ((ImageView) view.findViewById(R.id.feed_item_zan_cover)).setImageResource(R.drawable.ic_ico_moment_zan);
                }
                break;
            case R.id.feed_item_comment:
                mRootView.showMessage("评论");
                break;
            case R.id.feed_item_share:
                mRootView.showMessage("分享");
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (adapter instanceof FeedsAdapter)
            mRootView.launchActivity(new Intent(mApplication, FeedDetailsActivity.class).putExtra("data", (FeedBean) adapter.getItem(position)));
        else if (adapter instanceof ImageAdapter) ;
        else if (adapter instanceof ImageAdapter2) ;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
