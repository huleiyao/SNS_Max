package com.bytegem.snsmax.main.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.common.utils.M;
import com.bytegem.snsmax.main.app.bean.CommunityCommentBean;
import com.bytegem.snsmax.main.app.bean.CommunityCommentsList;
import com.bytegem.snsmax.main.app.bean.CommunityPostBean;
import com.bytegem.snsmax.main.app.bean.CommunityPostList;
import com.bytegem.snsmax.main.app.bean.LocationBean;
import com.bytegem.snsmax.main.app.bean.NetDefaultBean;
import com.bytegem.snsmax.main.mvp.ui.activity.CommunityPostCommentsOfCommentActivity;
import com.bytegem.snsmax.main.mvp.ui.adapter.CommunityCommentsAdapter;
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

import com.bytegem.snsmax.main.mvp.contract.CommunityPostDetailsContract;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.ArrayList;

import static com.bytegem.snsmax.main.app.MApplication.location;


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
@ActivityScope
public class CommunityPostDetailsPresenter extends BasePresenter<CommunityPostDetailsContract.Model, CommunityPostDetailsContract.View> implements BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    private int limit = 15;
    boolean isDefaultOrder = true;
    @Inject
    CommunityCommentsAdapter adapter;
    boolean isLike = false;
    boolean isFollow = false;
    int feedId;

    @Inject
    public CommunityPostDetailsPresenter(CommunityPostDetailsContract.Model model, CommunityPostDetailsContract.View rootView) {
        super(model, rootView);
    }

    public void getList(boolean isLoadMore, int postId, int commentId) {
        feedId = postId;
        mModel.getList(postId, limit, commentId, isDefaultOrder, !isLoadMore)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CommunityCommentsList>(mErrorHandler) {
                    @Override
                    public void onNext(CommunityCommentsList data) {
                        ArrayList<CommunityCommentBean> communityCommentBeans = data.getData();
                        if (isLoadMore) adapter.addData(communityCommentBeans);
                        else adapter.setNewData(communityCommentBeans);
                        mRootView.onFinishFreshAndLoad();
                    }
                });
    }

    public void commit(int feedId, String content) {
        mModel.commit(feedId, M.getMapString(
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
                        getList(false, feedId, 0);
                    }
                });
    }

    public void changeLikeState(int feedId) {
        mModel.changeLikeState(feedId, isLike)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<NetDefaultBean>(mErrorHandler) {
                    @Override
                    public void onNext(NetDefaultBean data) {
                        isLike = !isLike;
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

    public void changeUserFollowState(int userId) {
        mModel.changeUserFollowState(userId, isFollow)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<NetDefaultBean>(mErrorHandler) {
                    @Override
                    public void onNext(NetDefaultBean data) {
                        isFollow = !isFollow;
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
        switch (view.getId()) {
            case R.id.comment_zan:
                changeCommentLikeState(((CommunityCommentBean) adapter.getItem(position)).getId(), view.findViewById(R.id.like_cover).isSelected());
                view.findViewById(R.id.like_cover).setSelected(!view.findViewById(R.id.like_cover).isSelected());
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        CommunityCommentBean communityCommentBean = (CommunityCommentBean) adapter.getItem(position);
        mRootView.launchActivity(new Intent(mApplication, CommunityPostCommentsOfCommentActivity.class)
                .putExtra(CommunityPostCommentsOfCommentActivity.FEED_ID, feedId)
                .putExtra(CommunityPostCommentsOfCommentActivity.COMMENT_ID, communityCommentBean)
        );
    }
}
