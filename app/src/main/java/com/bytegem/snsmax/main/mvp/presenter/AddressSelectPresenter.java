package com.bytegem.snsmax.main.mvp.presenter;

import android.app.Application;
import android.view.View;

import com.bytegem.snsmax.main.app.MApplication;
import com.bytegem.snsmax.main.app.bean.group.GroupBean;
import com.bytegem.snsmax.main.app.bean.group.LISTGroup;
import com.bytegem.snsmax.main.app.bean.location.LISTTencentMapLocation;
import com.bytegem.snsmax.main.app.bean.location.TencentMapLocationBean;
import com.bytegem.snsmax.main.mvp.ui.adapter.AddressAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.bytegem.snsmax.main.mvp.contract.AddressSelectContract;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.ArrayList;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/12/2019 17:48
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class AddressSelectPresenter extends BasePresenter<AddressSelectContract.Model, AddressSelectContract.View> implements BaseQuickAdapter.OnItemClickListener {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    AddressAdapter adapter;
    int count = 49;
    int page = 0;
    String oldKeyword;
    boolean isLoadMore = true;

    @Inject
    public AddressSelectPresenter(AddressSelectContract.Model model, AddressSelectContract.View rootView) {
        super(model, rootView);
    }

    public void search(String keyword) {
        if (keyword == null || keyword.isEmpty()) keyword = "";
        if (keyword.trim().equals(oldKeyword))
            isLoadMore = true;
        else {
            page = 0;
            isLoadMore = false;
            oldKeyword = keyword.trim();
        }
        mRootView.showLoading();
        search(isLoadMore);
    }

    public void search(boolean isLoadMore) {
        page++;
        mModel.search(oldKeyword, getBoundary(), count, page)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                    mRootView.onFinishFreshAndLoad();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<LISTTencentMapLocation>(mErrorHandler) {
                    @Override
                    public void onNext(LISTTencentMapLocation data) {
                        ArrayList<TencentMapLocationBean> feedBeans = data.getData();
                        if (isLoadMore)
                            adapter.addData(feedBeans);
                        else
                            adapter.setNewData(feedBeans);
                    }
                });
    }

    private String getBoundary() {
        if (MApplication.location == null)
            return "nearby(0,0,1000)";
        else
            return "nearby(" + MApplication.location.getLatitude() + "," + MApplication.location.getLongitude() + ",1000)";
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
        Object item = adapter.getItem(position);
        if (item != null && item instanceof TencentMapLocationBean)
            MApplication.selectLocation = (TencentMapLocationBean) item;
        adapter.notifyDataSetChanged();
    }
}
