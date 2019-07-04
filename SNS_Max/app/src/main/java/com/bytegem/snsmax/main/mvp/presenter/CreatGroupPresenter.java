package com.bytegem.snsmax.main.mvp.presenter;

import android.app.Application;

import com.bytegem.snsmax.common.utils.M;
import com.bytegem.snsmax.main.app.bean.CommuntiyCommentData;
import com.bytegem.snsmax.main.app.bean.FileSignBean;
import com.bytegem.snsmax.main.app.utils.AssetsFileUtils;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


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

    @Inject
    public CreatGroupPresenter(CreatGroupContract.Model model, CreatGroupContract.View rootView) {
        super(model, rootView);
    }

    public void updataCover(ImageItem imageItem) {
        File tempCover = null;
        try {
            switch (imageItem.mimeType) {
                case "image/png":
                    tempCover = new File(AssetsFileUtils.copyBigDataBase(mApplication, "png.png"));
                    break;
                case "image/jpeg":
                    tempCover = new File(AssetsFileUtils.copyBigDataBase(mApplication, "jpg.jpg"));
                    break;
                case "image/gif":
                    tempCover = new File(AssetsFileUtils.copyBigDataBase(mApplication, "gif.gif"));
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (tempCover == null) {
            mRootView.showMessage("上传失败");
            return;
        }
        mModel.updataCover("image", tempCover, imageItem.size, M.getFileMD5(new File(imageItem.path)))
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<FileSignBean>(mErrorHandler) {
                    @Override
                    public void onNext(FileSignBean data) {
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
