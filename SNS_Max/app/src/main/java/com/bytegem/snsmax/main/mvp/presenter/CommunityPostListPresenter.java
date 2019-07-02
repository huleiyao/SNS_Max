package com.bytegem.snsmax.main.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.view.View;

import com.bytegem.snsmax.common.utils.M;
import com.bytegem.snsmax.main.app.MApplication;
import com.bytegem.snsmax.main.app.bean.CommunityPostBean;
import com.bytegem.snsmax.main.app.bean.CommunityPostList;
import com.bytegem.snsmax.main.app.bean.LocationBean;
import com.bytegem.snsmax.main.app.bean.LoginData;
import com.bytegem.snsmax.main.mvp.ui.activity.CommunityPostDetailsActivity;
import com.bytegem.snsmax.main.mvp.ui.adapter.CommunityPostListAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
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

import com.bytegem.snsmax.main.mvp.contract.CommunityPostListContract;
import com.jess.arms.utils.RxLifecycleUtils;

import java.net.URLEncoder;
import java.util.ArrayList;

import static com.bytegem.snsmax.main.app.MApplication.location;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/05/2019 11:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class CommunityPostListPresenter extends BasePresenter<CommunityPostListContract.Model, CommunityPostListContract.View> implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    CommunityPostListAdapter adapter;
    private int per_page = 15;
    private int page = 1;
    int type;

    @Inject
    public CommunityPostListPresenter(CommunityPostListContract.Model model, CommunityPostListContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void setType(int type, boolean isLoadMore) {
        this.type = type;
        getList(isLoadMore);
    }

    public void getList(boolean isLoadMore) {
        if (isLoadMore) page++;
        else {
            page = 1;
        }
        if (location == null)
            location = new LocationBean();
        (type == 0 ? mModel.getRecommendList(per_page + "", page + "") :
                mModel.getList(location.getLatitude() + "", location.getLongitude() + "", per_page + "", page + ""))
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CommunityPostList>(mErrorHandler) {
                    @Override
                    public void onNext(CommunityPostList data) {
                        ArrayList<CommunityPostBean> communityPostBeans = data.getData();
                        if (isLoadMore) adapter.addData(communityPostBeans);
                        else adapter.setNewData(communityPostBeans);
                        mRootView.onFinishFreshAndLoad();
                    }
                });
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mRootView.launchActivity(new Intent(mApplication, CommunityPostDetailsActivity.class).putExtra("data", (CommunityPostBean) adapter.getItem(position)));
    }
}