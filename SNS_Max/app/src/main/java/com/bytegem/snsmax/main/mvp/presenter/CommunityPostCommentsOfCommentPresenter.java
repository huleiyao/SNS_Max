package com.bytegem.snsmax.main.mvp.presenter;

import android.app.Application;
import android.view.View;

import com.bytegem.snsmax.common.utils.M;
import com.bytegem.snsmax.main.app.bean.CommunityCommentBean;
import com.bytegem.snsmax.main.app.bean.CommunityCommentsList;
import com.bytegem.snsmax.main.app.bean.LocationBean;
import com.bytegem.snsmax.main.app.bean.NetDefaultBean;
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

import com.bytegem.snsmax.main.mvp.contract.CommunityPostCommentsOfCommentContract;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.ArrayList;

import static com.bytegem.snsmax.main.app.MApplication.location;


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
public class CommunityPostCommentsOfCommentPresenter extends BasePresenter<CommunityPostCommentsOfCommentContract.Model, CommunityPostCommentsOfCommentContract.View> implements BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    CommunityCommentsAdapter adapter;
    private int limit = 15;
    boolean isDefaultOrder = true;
    CommunityCommentBean communityCommentBean;
    int postId;
    int commentId;

    @Inject
    public CommunityPostCommentsOfCommentPresenter(CommunityPostCommentsOfCommentContract.Model model, CommunityPostCommentsOfCommentContract.View rootView) {
        super(model, rootView);
    }

    public void setCommunityCommentBean(CommunityCommentBean communityCommentBean) {
        this.communityCommentBean = communityCommentBean;
    }

    public void setId(boolean isLoadMore, int postId, int commentId) {
        this.postId = postId;
        this.commentId = commentId;
        getList(isLoadMore);
    }

    public void getList(boolean isLoadMore) {
        if (!isLoadMore) {
            ArrayList<CommunityCommentBean> communityCommentBeans = new ArrayList<>();
            communityCommentBeans.add(communityCommentBean);
            adapter.setNewData(communityCommentBeans);
        }
        mModel.getList(postId, commentId, limit, adapter.getItemCount() == 0 ? 0 : adapter.getItem(adapter.getItemCount() - 1).getId(), isDefaultOrder, !isLoadMore)
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
                        adapter.addData(communityCommentBeans);
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
