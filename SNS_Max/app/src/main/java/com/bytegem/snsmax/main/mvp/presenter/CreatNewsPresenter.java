package com.bytegem.snsmax.main.mvp.presenter;

import android.app.Application;
import android.view.View;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.common.utils.M;
import com.bytegem.snsmax.main.app.bean.CommunityMediaBean;
import com.bytegem.snsmax.main.app.bean.CommunityPostList;
import com.bytegem.snsmax.main.app.bean.LocationBean;
import com.bytegem.snsmax.main.app.bean.NetDefaultBean;
import com.bytegem.snsmax.main.mvp.ui.activity.CreatNewsActivity;
import com.bytegem.snsmax.main.mvp.ui.adapter.CreateImageAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
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

import com.bytegem.snsmax.main.mvp.contract.CreatNewsContract;
import com.jess.arms.utils.RxLifecycleUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.bytegem.snsmax.main.app.MApplication.location;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/22/2019 09:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class CreatNewsPresenter extends BasePresenter<CreatNewsContract.Model, CreatNewsContract.View> implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    CreateImageAdapter adapter;

    @Inject
    public CreatNewsPresenter(CreatNewsContract.Model model, CreatNewsContract.View rootView) {
        super(model, rootView);
    }

    public void send(String content, CommunityMediaBean mediaBean) {
        if (location == null)
            location = new LocationBean();
        String jsonData = "";
        mediaBean.setContents();
        if (mediaBean.getContents() == null || mediaBean.getType().isEmpty() || mediaBean.getContents().isEmpty())
            jsonData = M.getMapString(
                    "geo", location.getGeo()
                    , "contents", content
            );
        else jsonData = M.getMapString(
                "geo", location.getGeo()
                , "contents", content
                , "media", mediaBean
        );
        mModel.send(jsonData)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<NetDefaultBean>(mErrorHandler) {
                    @Override
                    public void onNext(NetDefaultBean data) {
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

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.delete:
                mRootView.remove(position);
                break;
            case R.id.add:
                CreatNewsActivity.FeedType feedType = ((CreateImageAdapter) adapter).getFeedType();
                if (feedType == CreatNewsActivity.FeedType.CAMERA) mRootView.openPhotos();
                else if (feedType == CreatNewsActivity.FeedType.VIDEO) {

                }
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mRootView.watchImagePicker(position);
    }
}
