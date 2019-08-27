package com.bytegem.snsmax.main.mvp.presenter;

import android.app.Application;
import android.content.Context;

import com.bytegem.snsmax.main.app.MApplication;
import com.bytegem.snsmax.main.app.bean.user.DATAUser;
import com.bytegem.snsmax.main.app.utils.UserInfoUtils;
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

import com.bytegem.snsmax.main.mvp.contract.OwnerHomeContract;
import com.jess.arms.utils.RxLifecycleUtils;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/23/2019 13:28
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class OwnerHomePresenter extends BasePresenter<OwnerHomeContract.Model, OwnerHomeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public OwnerHomePresenter(OwnerHomeContract.Model model, OwnerHomeContract.View rootView) {
        super(model, rootView);
    }

    public void getUserData(boolean isMe, int id) {
        mRootView.showLoading();
        mModel.getUserData(isMe, id)
                .doOnNext((it) -> {
                    //如果获取用户信息成功。那么保存到本地中
                    UserInfoUtils.saveUserInfo(it,mModel.getGson());
                })
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<DATAUser>(mErrorHandler) {
                    @Override
                    public void onNext(DATAUser data) {
                        if (data != null && data.getData() != null)
                            mRootView.initUserData(data.getData());
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
