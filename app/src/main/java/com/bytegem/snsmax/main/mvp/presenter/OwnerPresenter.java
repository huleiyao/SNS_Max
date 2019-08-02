package com.bytegem.snsmax.main.mvp.presenter;

import android.app.Application;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.common.utils.M;
import com.bytegem.snsmax.main.app.MApplication;
import com.bytegem.snsmax.main.app.bean.FileSignBean;
import com.bytegem.snsmax.main.app.bean.NetDefaultBean;
import com.bytegem.snsmax.main.app.bean.login.LoginData;
import com.bytegem.snsmax.main.app.bean.user.DATAUser;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import javax.inject.Inject;

import com.bytegem.snsmax.main.mvp.contract.OwnerContract;
import com.jess.arms.utils.RxLifecycleUtils;
import com.lzy.imagepicker.bean.ImageItem;

import java.io.EOFException;
import java.io.File;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/10/2019 16:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class OwnerPresenter extends BasePresenter<OwnerContract.Model, OwnerContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public OwnerPresenter(OwnerContract.Model model, OwnerContract.View rootView) {
        super(model, rootView);
    }

    public void getUserData() {
        mModel.getUserData()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 1))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<DATAUser>(mErrorHandler) {
                    @Override
                    public void onNext(DATAUser data) {
                        if (data != null && data.getData() != null)
                            mRootView.showUserData(data.getData());
                    }
                });
    }

    public void getSign(String type, ImageItem imageItem) {
        mRootView.showLoading();
        if (imageItem.mimeType == null) {
            mRootView.showMessage("上传失败");
            mRootView.hideLoading();
            return;
        }
        File tempCover = M.getTempFile(mApplication, imageItem.mimeType);
        if (tempCover == null) {
            mRootView.showMessage("上传失败");
            mRootView.hideLoading();
            return;
        }
        mModel.getSign(type, tempCover, imageItem.size, M.getFileMD5(new File(imageItem.path)))
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError((Throwable onError) -> {
                    mRootView.showMessage("上传失败");
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<FileSignBean>(mErrorHandler) {
                    @Override
                    public void onNext(FileSignBean data) {
                        updataCover(type, data, imageItem);
                    }
                });
    }

    public void updataCover(String type, FileSignBean fileSignBean, ImageItem imageItem) {
        mModel.updataCover(fileSignBean, imageItem)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
//                    mRootView.hideLoading();
                })
                .doOnError((Throwable onError) -> {
                    if (onError instanceof EOFException) {
                        //无数据返回  成功
                        String url = Utils.checkUrl(fileSignBean.getPath());
                        updataUser("avatar", url);
                    } else {
                        mRootView.showMessage("上传失败");
                        mRootView.hideLoading();
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<MBaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(MBaseBean data) {
                    }
                });
    }


    public void updataUser(String... map) {
        mModel.updataUser(
                M.getMapString(map))
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    getUserData();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<NetDefaultBean>(mErrorHandler) {
                    @Override
                    public void onNext(NetDefaultBean data) {
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
