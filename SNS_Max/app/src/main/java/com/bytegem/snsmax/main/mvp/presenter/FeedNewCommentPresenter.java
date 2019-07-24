package com.bytegem.snsmax.main.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.view.View;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.bean.NetDefaultBean;
import com.bytegem.snsmax.main.app.bean.feed.FeedCommentBean;
import com.bytegem.snsmax.main.app.bean.feed.LISTFeedComments;
import com.bytegem.snsmax.main.mvp.ui.activity.FeedCommentsOfCommentActivity;
import com.bytegem.snsmax.main.mvp.ui.adapter.FeedCommentsAdapter;
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

import javax.inject.Inject;

import com.bytegem.snsmax.main.mvp.contract.FeedNewCommentContract;
import com.jess.arms.utils.RxLifecycleUtils;
import com.lzy.imagepicker.ImagePicker;
import com.bytegem.snsmax.main.mvp.ui.activity.WatchImageActivity;

import java.util.ArrayList;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/20/2019 15:39
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class FeedNewCommentPresenter extends BasePresenter<FeedNewCommentContract.Model, FeedNewCommentContract.View> implements BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    FeedCommentsAdapter adapter;
    boolean isDefaultOrder = true;
    private int limit = 15;
    int feedId;

    @Inject
    public FeedNewCommentPresenter(FeedNewCommentContract.Model model, FeedNewCommentContract.View rootView) {
        super(model, rootView);
    }

    public void getList(boolean isLoadMore, int postId, int commentId) {
        feedId = postId;
        mModel.getCommentList(postId, limit, commentId, isDefaultOrder, !isLoadMore)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<LISTFeedComments>(mErrorHandler) {
                    @Override
                    public void onNext(LISTFeedComments data) {
                        ArrayList<FeedCommentBean> feedCommentBeans = data.getData();
                        if (isLoadMore) adapter.addData(feedCommentBeans);
                        else adapter.setNewData(feedCommentBeans);
                        mRootView.onFinishFreshAndLoad();
                    }
                });
    }


    public void changeCommentLikeState(int commentId, boolean isLike) {
        mModel.changeCommentLikeState(commentId, isLike)
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
            case R.id.comment_zan:
                changeCommentLikeState(((FeedCommentBean) adapter.getItem(position)).getId(), view.findViewById(R.id.comment_like_cover).isSelected());
                view.findViewById(R.id.comment_like_cover).setSelected(!view.findViewById(R.id.comment_like_cover).isSelected());
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (adapter instanceof FeedCommentsAdapter) {
            FeedCommentBean feedCommentBean = (FeedCommentBean) adapter.getItem(position);
            mRootView.launchActivity(new Intent(mApplication, FeedCommentsOfCommentActivity.class)
                    .putExtra(FeedCommentsOfCommentActivity.FEED_ID, feedId)
                    .putExtra(FeedCommentsOfCommentActivity.COMMENT_ID, feedCommentBean)
            );
        } else if (adapter instanceof ImageAdapter || adapter instanceof ImageAdapter2)
            mRootView.launchActivity(new Intent(mApplication, WatchImageActivity.class)
                    .putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<String>) adapter.getData())
                    .putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position)
                    .putExtra(ImagePicker.EXTRA_FROM_ITEMS, true)
            );
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
