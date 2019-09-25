package com.bytegem.snsmax.main.mvp.presenter;

import android.app.Application;

import com.blankj.utilcode.util.ToastUtils;
import com.bytegem.snsmax.common.utils.M;
import com.bytegem.snsmax.main.app.MApplication;
import com.bytegem.snsmax.main.app.bean.login.LoginData;
import com.bytegem.snsmax.main.app.bean.NetDefaultBean;
import com.bytegem.snsmax.main.app.utils.UserInfoUtils;
import com.bytegem.snsmax.main.mvp.ui.dialog.loadingDialog.ShapeLoadingDialog;
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

import com.bytegem.snsmax.main.mvp.contract.LoginContract;
import com.jess.arms.utils.RxLifecycleUtils;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/18/2019 09:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    ShapeLoadingDialog loadingDialog;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView) {
        super(model, rootView);
    }

    public void getCode(String phoneNumber) {
        mModel.getCode(M.getMapString(
                "phone_number"
                , phoneNumber
        ))
                .subscribeOn(Schedulers.io())
//                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .doOnError((Throwable onError) -> {
                    mRootView.getCodeFaild();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<NetDefaultBean>(mErrorHandler) {
                    @Override
                    public void onNext(NetDefaultBean data) {
                        if (data != null && data.getMessage().equals("ok"))
                            mRootView.getCodeSucceed();
                        else
                            mRootView.getCodeFaild();
                    }
                });
    }

    public void login(String phoneNumber, String code) {
        mRootView.showLoading();
        mModel.login(
                M.getMapString(
                        "phone_number"
                        , phoneNumber
                        , "code"
                        , code
                ))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<LoginData>(mErrorHandler) {
                    @Override
                    public void onNext(LoginData data) {
                        if (data != null && !data.getAccess_token().isEmpty()) {
                            MApplication.getInstance().setToken(data.getAccess_token());
                            MApplication.getInstance().setTokenType(data.getToken_type());
                            UserInfoUtils.setTokenAndType(data.getAccess_token(),data.getToken_type());
                            mRootView.toHome();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        ToastUtils.showShort("登录异常:"+t);
                    }
                });
    }

    public void Wxlogin(String code) {
        mRootView.showLoading();
        mModel.wxlogin(
                M.getMapString(
                        "code"
                        , code
                ))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<LoginData>(mErrorHandler) {
                    @Override
                    public void onNext(LoginData data) {
                        if (data != null && !data.getAccess_token().isEmpty()) {
                            MApplication.getInstance().setToken(data.getAccess_token());
                            MApplication.getInstance().setTokenType(data.getToken_type());
                            UserInfoUtils.setTokenAndType(data.getAccess_token(),data.getToken_type());
                            mRootView.toHome();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        ToastUtils.showShort("登录异常:"+t);
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
