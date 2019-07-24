package com.bytegem.snsmax.main.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.common.utils.M;
import com.bytegem.snsmax.main.app.bean.NetDefaultBean;
import com.bytegem.snsmax.main.app.bean.feed.FeedBean;
import com.bytegem.snsmax.main.app.bean.feed.LISTFeeds;
import com.bytegem.snsmax.main.app.bean.location.LocationBean;
import com.bytegem.snsmax.main.app.bean.topic.TopicBean;
import com.bytegem.snsmax.main.app.widget.TagTextView;
import com.bytegem.snsmax.main.mvp.ui.activity.FeedDetailsActivity;
import com.bytegem.snsmax.main.mvp.ui.activity.TopicDetailActivity;
import com.bytegem.snsmax.main.mvp.ui.activity.VideoPlayerActivity;
import com.bytegem.snsmax.main.mvp.ui.adapter.FeedsAdapter;
import com.bytegem.snsmax.main.mvp.ui.adapter.ImageAdapter;
import com.bytegem.snsmax.main.mvp.ui.adapter.ImageAdapter2;
import com.bytegem.snsmax.main.mvp.ui.listener.ImageAdapterGetFeed;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.bytegem.snsmax.main.mvp.contract.FeedsContract;
import com.jess.arms.utils.RxLifecycleUtils;
import com.lzy.imagepicker.ImagePicker;
import com.bytegem.snsmax.main.mvp.ui.activity.WatchImageActivity;

import java.util.ArrayList;

import static com.bytegem.snsmax.main.app.MApplication.location;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/05/2019 11:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class FeedsPresenter extends BasePresenter<FeedsContract.Model, FeedsContract.View> implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener, TagTextView.TopicListener {
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
    private int per_page = 15;
    private int page = 1;
    int type;

    @Inject
    public FeedsPresenter(FeedsContract.Model model, FeedsContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void setType(int type, boolean isLoadMore) {
        this.type = type;
        getList(isLoadMore);
    }

    public void getList(boolean isLoadMore) {
        if (isLoadMore) page++;
        else {
            page = 1;
        }
        if (location == null)
            location = new LocationBean();
        (type == 0 ? mModel.getRecommendList(per_page + "", page + "") :
                mModel.getFeedList(location.getLatitude() + "", location.getLongitude() + "", per_page + "", page + ""))
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
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
                    }
                });
    }

    public void commit(int feedId, String content) {
        mModel.commitFeedComment(feedId, M.getMapString(
                "contents", content
        ))
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<NetDefaultBean>(mErrorHandler) {
                    @Override
                    public void onNext(NetDefaultBean data) {
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
                                    .putExtra("feed", feedBean)
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
                mRootView.toComment((FeedBean) adapter.getItem(position));
                break;
            case R.id.feed_item_more:
                mRootView.showMore((FeedBean) adapter.getItem(position));
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
        else if (adapter instanceof ImageAdapter || adapter instanceof ImageAdapter2)
            mRootView.launchActivity(new Intent(mApplication, WatchImageActivity.class)
                    .putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<String>) adapter.getData())
                    .putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position)
                    .putExtra(ImagePicker.EXTRA_FROM_ITEMS, true)
                    .putExtra("feed", ((ImageAdapterGetFeed) adapter).getFeed())
            );
    }

    @Override
    public void topicListener(TopicBean topicBean) {
        mRootView.launchActivity(new Intent(mApplication, TopicDetailActivity.class).putExtra("topic", topicBean));
    }
}
