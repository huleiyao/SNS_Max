package com.bytegem.snsmax.main.mvp.presenter;

import android.app.Application;
import android.content.Intent;

import com.bytegem.snsmax.main.mvp.ui.activity.CreatGroupActivity;
import com.bytegem.snsmax.main.mvp.ui.adapter.OwnerGroupsAdapter;
import com.bytegem.snsmax.main.mvp.ui.view.CreatGroupHeaderView;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.bytegem.snsmax.main.mvp.contract.OwnerGroupsContract;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/30/2019 11:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class OwnerGroupsPresenter extends BasePresenter<OwnerGroupsContract.Model, OwnerGroupsContract.View> implements CreatGroupHeaderView.CreatGroupListener {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    OwnerGroupsAdapter adapter;

    @Inject
    public OwnerGroupsPresenter(OwnerGroupsContract.Model model, OwnerGroupsContract.View rootView) {
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

    @Override
    public void creatGroup() {
        //点击创建圈子按钮后的操作
//        mRootView.showMessage("创建圈子");
        mRootView.launchActivity(new Intent(mApplication, CreatGroupActivity.class));
    }
}
