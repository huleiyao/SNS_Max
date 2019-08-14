package com.bytegem.snsmax.main.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.view.View;

import com.bytegem.snsmax.main.app.bean.feed.FeedBean;
import com.bytegem.snsmax.main.app.bean.feed.LISTFeeds;
import com.bytegem.snsmax.main.app.bean.location.LocationBean;
import com.bytegem.snsmax.main.mvp.ui.activity.FeedDetailsActivity;
import com.bytegem.snsmax.main.mvp.ui.activity.WatchImageActivity;
import com.bytegem.snsmax.main.mvp.ui.adapter.FeedsAdapter;
import com.bytegem.snsmax.main.mvp.ui.adapter.ImageAdapter;
import com.bytegem.snsmax.main.mvp.ui.adapter.ImageAdapter2;
import com.bytegem.snsmax.main.mvp.ui.adapter.OwnerFeedsAdapter;
import com.bytegem.snsmax.main.mvp.ui.listener.ImageAdapterGetFeed;
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

import com.bytegem.snsmax.main.mvp.contract.OwnerFeedsContract;
import com.jess.arms.utils.RxLifecycleUtils;
import com.lzy.imagepicker.ImagePicker;

import java.util.ArrayList;

import static com.bytegem.snsmax.main.app.MApplication.location;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/28/2019 10:44
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class OwnerFeedsPresenter extends BasePresenter<OwnerFeedsContract.Model, OwnerFeedsContract.View> implements BaseQuickAdapter.OnItemClickListener {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    OwnerFeedsAdapter adapter;

    @Inject
    public OwnerFeedsPresenter(OwnerFeedsContract.Model model, OwnerFeedsContract.View rootView) {
        super(model, rootView);
    }

    public void getList(boolean isLoadMore) {
//        (type == 0 ? mModel.getRecommendList(per_page + "", page + "") :
//                mModel.getFeedList(location.getLatitude() + "", location.getLongitude() + "", per_page + "", page + ""))
//                .subscribeOn(Schedulers.io())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doFinally(() -> {
//                })
//                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
//                .subscribe(new ErrorHandleSubscriber<LISTFeeds>(mErrorHandler) {
//                    @Override
//                    public void onNext(LISTFeeds data) {
//                        ArrayList<FeedBean> feedBeans = data.getData();
//                        for (FeedBean feedBean : feedBeans)
//                            if (feedBean.getMedia() != null)
//                                feedBean.getMedia().initContent();
//                        if (isLoadMore) adapter.addData(feedBeans);
//                        else adapter.setNewData(feedBeans);
//                        mRootView.onFinishFreshAndLoad();
//                    }
//                });
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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mRootView.launchActivity(new Intent(mApplication, FeedDetailsActivity.class).putExtra("data", (FeedBean) adapter.getItem(position)));
    }
}
