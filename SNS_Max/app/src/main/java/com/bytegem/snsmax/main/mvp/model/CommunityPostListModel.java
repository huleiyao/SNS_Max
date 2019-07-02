package com.bytegem.snsmax.main.mvp.model;

import android.app.Application;

import com.bytegem.snsmax.main.app.bean.CommunityPostList;
import com.bytegem.snsmax.main.app.config.CommunityService;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.bytegem.snsmax.main.mvp.contract.CommunityPostListContract;

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
public class CommunityPostListModel extends BaseModel implements CommunityPostListContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public CommunityPostListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<CommunityPostList> getList(String latitude, String longitude, String per_page, String page) {
        return mRepositoryManager
                .obtainRetrofitService(CommunityService.class)
                .getList(latitude, longitude, per_page, page);
    }

    @Override
    public Observable<CommunityPostList> getRecommendList(String per_page, String page) {
        return mRepositoryManager
                .obtainRetrofitService(CommunityService.class)
                .getRecommendList(per_page, page);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}