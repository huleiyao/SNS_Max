package com.bytegem.snsmax.main.mvp.presenter;

import android.app.Application;
import android.view.View;

import com.bytegem.snsmax.common.utils.M;
import com.bytegem.snsmax.main.app.bean.feed.FeedCommentBean;
import com.bytegem.snsmax.main.app.bean.feed.LISTFeedComments;
import com.bytegem.snsmax.main.app.bean.NetDefaultBean;
import com.bytegem.snsmax.main.mvp.contract.FeedCommentsOfCommentContract;
import com.bytegem.snsmax.main.mvp.ui.adapter.FeedCommentsAdapter;
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

import com.jess.arms.utils.RxLifecycleUtils;

import java.util.ArrayList;


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
@ActivityScope
public class FeedCommentsOfCommentPresenter extends BasePresenter<FeedCommentsOfCommentContract.Model, FeedCommentsOfCommentContract.View> implements BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener {
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
    private int limit = 15;
    boolean isDefaultOrder = true;
    FeedCommentBean feedCommentBean;
    int postId;
    int commentId;

    @Inject
    public FeedCommentsOfCommentPresenter(FeedCommentsOfCommentContract.Model model, FeedCommentsOfCommentContract.View rootView) {
        super(model, rootView);
    }

    public void setFeedCommentBean(FeedCommentBean feedCommentBean) {
        this.feedCommentBean = feedCommentBean;
    }

    public void setId(boolean isLoadMore, int postId, int commentId) {
        this.postId = postId;
        this.commentId = commentId;
        getList(isLoadMore);
    }

    public void getList(boolean isLoadMore) {
        if (!isLoadMore) {
            ArrayList<FeedCommentBean> feedCommentBeans = new ArrayList<>();
            feedCommentBeans.add(feedCommentBean);
            adapter.setNewData(feedCommentBeans);
        }
        mModel.getList(postId, commentId, limit, adapter.getItemCount() == 0 ? 0 : adapter.getItem(adapter.getItemCount() - 1).getId(), isDefaultOrder, !isLoadMore)
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
                        adapter.addData(feedCommentBeans);
                        mRootView.onFinishFreshAndLoad();
                    }
                });
    }

    public void commit(String content) {
        mModel.commit(postId, commentId, M.getMapString(
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
                        getList(false);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
