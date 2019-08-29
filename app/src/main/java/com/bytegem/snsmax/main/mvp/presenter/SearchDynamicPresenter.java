package com.bytegem.snsmax.main.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.view.View;

import com.bytegem.snsmax.main.app.bean.feed.FeedBean;
import com.bytegem.snsmax.main.app.bean.group.GroupBean;
import com.bytegem.snsmax.main.app.bean.group.LISTGroup;
import com.bytegem.snsmax.main.mvp.ui.activity.GroupDetailsActivity;
import com.bytegem.snsmax.main.mvp.ui.adapter.GroupsAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.bytegem.snsmax.main.mvp.contract.SearchDynamicContract;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.ArrayList;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/29/2019 16:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class SearchDynamicPresenter extends BasePresenter<SearchDynamicContract.Model, SearchDynamicContract.View>
        implements BaseQuickAdapter.OnItemClickListener {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    GroupsAdapter adapter;

    @Inject
    public SearchDynamicPresenter(SearchDynamicContract.Model model, SearchDynamicContract.View rootView) {
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

    public void getList() {
        mRootView.showLoading();
        mModel.getGroupList()
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<LISTGroup>(mErrorHandler) {
                    @Override
                    public void onNext(LISTGroup data) {
                        ArrayList<GroupBean> groupBeans = data.getData();
                        for (GroupBean groupBean : groupBeans)
                            if (groupBean.getFeeds() != null && groupBean.getFeeds().size() > 0)
                                for (FeedBean feedBean : groupBean.getFeeds())
                                    if (feedBean.getMedia() != null)
                                        feedBean.getMedia().initContent();
                        adapter.setNewData(groupBeans);
                        mRootView.onFinishFreshAndLoad();
                    }
                });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (adapter instanceof GroupsAdapter) {
            mRootView.launchActivity(new Intent(mApplication, GroupDetailsActivity.class).putExtra("group", ((GroupsAdapter) adapter).getItem(position)));
        }
    }
}
