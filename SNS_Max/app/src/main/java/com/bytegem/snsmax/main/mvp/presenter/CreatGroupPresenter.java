package com.bytegem.snsmax.main.mvp.presenter;

import android.app.Application;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.common.utils.M;
import com.bytegem.snsmax.main.app.Api;
import com.bytegem.snsmax.main.app.bean.FileSignBean;
import com.bytegem.snsmax.main.app.bean.NetDefaultBean;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.bytegem.snsmax.main.mvp.contract.CreatGroupContract;
import com.jess.arms.utils.RxLifecycleUtils;
import com.lzy.imagepicker.bean.ImageItem;

import java.io.EOFException;
import java.io.File;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/01/2019 11:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class CreatGroupPresenter extends BasePresenter<CreatGroupContract.Model, CreatGroupContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    String avatar;

    @Inject
    public CreatGroupPresenter(CreatGroupContract.Model model, CreatGroupContract.View rootView) {
        super(model, rootView);
    }

    public void getSign(ImageItem imageItem) {
        mRootView.showLoading();
        File tempCover = M.getTempFile(mApplication, imageItem.mimeType);
        if (tempCover == null) {
            mRootView.showMessage("上传失败");
            mRootView.hideLoading();
            return;
        }
        mModel.getSign("image", tempCover, imageItem.size, M.getFileMD5(new File(imageItem.path)))
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
                        updataCover(data, imageItem);
                    }
                });
    }

    public void updataCover(FileSignBean fileSignBean, ImageItem imageItem) {
        mModel.updataCover(fileSignBean, imageItem)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .doOnError((Throwable onError) -> {
                    if (onError instanceof EOFException) {
                        //无数据返回  成功
                        mRootView.showMessage("上传成功");
                        avatar = fileSignBean.getPath();
                        mRootView.showGroupCover(Api.FILE_LOOK_DOMAIN + fileSignBean.getPath());
                    } else {
                        mRootView.showMessage("上传失败");
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<MBaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(MBaseBean data) {
                    }
                });
    }

    /*
     * 创建圈子
     * is_private,0 - 公开/1 - 私密
     *
     * */
    public void createGroup(String name, String desc, int is_private) {
        if (avatar == null) {
            mRootView.showMessage("请先上传圈子头像！");
            return;
        }
        mModel.createGroup(name, desc, avatar, is_private)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError((Throwable onError) -> {
                    mRootView.showMessage("创建失败");
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<NetDefaultBean>(mErrorHandler) {
                    @Override
                    public void onNext(NetDefaultBean data) {
                        mRootView.showMessage("创建成功");
                        mRootView.killMyself();
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
