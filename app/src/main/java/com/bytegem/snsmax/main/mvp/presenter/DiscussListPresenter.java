package com.bytegem.snsmax.main.mvp.presenter;

import android.app.Application;

import com.bytegem.snsmax.main.app.bean.discusses.DiscussBean;
import com.bytegem.snsmax.main.app.bean.discusses.LISTDiscusses;
import com.bytegem.snsmax.main.mvp.ui.adapter.DiscussAdapter;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.bytegem.snsmax.main.mvp.contract.DiscussListContract;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.ArrayList;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/12/2019 09:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class DiscussListPresenter extends BasePresenter<DiscussListContract.Model, DiscussListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    DiscussAdapter adapter;
    int mId;
    int afterId;

    @Inject
    public DiscussListPresenter(DiscussListContract.Model model, DiscussListContract.View rootView) {
        super(model, rootView);
    }

    public void setId(int id) {
        mId = id;
        getList(true);
    }

    public void getList(boolean isRefresh) {
        if (isRefresh) mRootView.showLoading();
        mModel.getDiscussList(mId, afterId)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<LISTDiscusses>(mErrorHandler) {
                    @Override
                    public void onNext(LISTDiscusses data) {
                        ArrayList<DiscussBean> feedBeans = data.getData();
                        if (isRefresh)
                            adapter.setNewData(feedBeans);
                        else
                            adapter.addData(feedBeans);
                        mRootView.onFinishFreshAndLoad();
                        if (feedBeans.size() > 0)
                            afterId = feedBeans.get(feedBeans.size() - 1).getId();
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
}
