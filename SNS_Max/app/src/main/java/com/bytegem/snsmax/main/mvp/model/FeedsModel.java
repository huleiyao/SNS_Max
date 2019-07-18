package com.bytegem.snsmax.main.mvp.model;

import android.app.Application;

import com.bytegem.snsmax.main.app.MApplication;
import com.bytegem.snsmax.main.app.bean.feed.LISTFeeds;
import com.bytegem.snsmax.main.app.config.CommunityService;
import com.bytegem.snsmax.main.mvp.contract.FeedsContract;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import io.reactivex.Observable;


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
public class FeedsModel extends BaseModel implements FeedsContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public FeedsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<LISTFeeds> getList(String latitude, String longitude, String per_page, String page) {
        return mRepositoryManager
                .obtainRetrofitService(CommunityService.class)
                .getList(MApplication.getTokenOrType(), latitude, longitude, per_page, page);
    }

    @Override
    public Observable<LISTFeeds> getRecommendList(String per_page, String page) {
        return mRepositoryManager
                .obtainRetrofitService(CommunityService.class)
                .getRecommendList(MApplication.getTokenOrType(), per_page, page);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}