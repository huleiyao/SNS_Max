package com.bytegem.snsmax.main.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.bytegem.snsmax.main.app.bean.NetDefaultBean;
import com.bytegem.snsmax.main.app.bean.user.SearchDTO;
import com.bytegem.snsmax.main.mvp.ui.activity.OwnerHomeActivity;
import com.bytegem.snsmax.main.mvp.ui.adapter.SearchUserAdapter;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.bytegem.snsmax.main.mvp.contract.SearchUserContract;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/30/2019 14:15
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class SearchUserPresenter extends BasePresenter<SearchUserContract.Model, SearchUserContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    SearchUserAdapter adapter;
    List<SearchDTO.SearchUserItem> userDatas = new ArrayList();

    @Inject
    public SearchUserPresenter(SearchUserContract.Model model, SearchUserContract.View rootView) {
        super(model, rootView);
    }

    public void queryUser(String key) {
        mModel.queryUser(key, "" + userDatas.size())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<SearchDTO<SearchDTO.SearchUserItem>>(mErrorHandler) {
                    @Override
                    public void onNext(SearchDTO<SearchDTO.SearchUserItem> searchUserItemSearchDTO) {
                        mRootView.bindListUserData(searchUserItemSearchDTO);
                        adapter.setNewData(searchUserItemSearchDTO.data);
                        mRootView.hideLoading();
                    }
                });
    }

    public void createAdapter(RecyclerView rv) {
        //关注按钮的点击
        adapter = SearchUserAdapter.createAdapter(rv, userDatas, (pos, item) -> {
            mRootView.launchActivity(new Intent(rv.getContext(), OwnerHomeActivity.class)
                    .putExtra(OwnerHomeActivity.ISME, false)
                    .putExtra(OwnerHomeActivity.ID, item.getId())
            );
        }, this::changeUserFollowState);
    }

    public void changeUserFollowState(int pos, SearchDTO.SearchUserItem item) {
        mRootView.showLoading();
        mModel.changeUserFollowState(item.getId(), item.getHas_following())
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<NetDefaultBean>(mErrorHandler) {
                    @Override
                    public void onNext(NetDefaultBean data) {
                        item.setHas_following(!item.getHas_following());
                        adapter.notifyItemChanged(pos);
                        mRootView.hideLoading();
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
