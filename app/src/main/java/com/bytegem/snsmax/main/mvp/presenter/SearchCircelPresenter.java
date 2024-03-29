package com.bytegem.snsmax.main.mvp.presenter;

import android.app.Application;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bytegem.snsmax.main.app.bean.user.SearchDTO;
import com.bytegem.snsmax.main.mvp.ui.adapter.SearchCircelAdapter;
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

import com.bytegem.snsmax.main.mvp.contract.SearchCircelContract;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/31/2019 11:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class SearchCircelPresenter extends BasePresenter<SearchCircelContract.Model, SearchCircelContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    SearchCircelAdapter adapter;
    List<SearchDTO.SearchCircelItem> userDatas = new ArrayList();

    @Inject
    public SearchCircelPresenter(SearchCircelContract.Model model, SearchCircelContract.View rootView) {
        super(model, rootView);
    }
    public void queryCircel(String key) {
        mModel.queryCircel(key, "" + userDatas.size())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(()->{
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<SearchDTO<SearchDTO.SearchCircelItem>>(mErrorHandler) {
                    @Override
                    public void onNext(SearchDTO<SearchDTO.SearchCircelItem> searchUserItemSearchDTO) {
                        mRootView.bindListData(searchUserItemSearchDTO);
                        adapter.setNewData(searchUserItemSearchDTO.data);
                        mRootView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        Log.e("this","查询圈子出错:"+t.toString());
                    }
                });
    }

    public void createAdapter(RecyclerView rv) {
        adapter = SearchCircelAdapter.createAdapter(rv,userDatas,((clickPos, clickItem) -> {
            mRootView.circelClick(clickPos,clickItem);
        }));
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);
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
