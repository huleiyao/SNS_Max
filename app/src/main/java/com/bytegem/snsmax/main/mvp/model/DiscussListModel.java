package com.bytegem.snsmax.main.mvp.model;

import android.app.Application;

import com.bytegem.snsmax.main.app.MApplication;
import com.bytegem.snsmax.main.app.bean.discusses.LISTDiscusses;
import com.bytegem.snsmax.main.app.config.GroupService;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.bytegem.snsmax.main.mvp.contract.DiscussListContract;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/12/2019 09:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class DiscussListModel extends BaseModel implements DiscussListContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public DiscussListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<LISTDiscusses> getDiscussList(int id, int afterId) {
        if (afterId == 0)
            return mRepositoryManager
                    .obtainRetrofitService(GroupService.class)
                    .getGroupDiscusses(MApplication.getTokenOrType(), id);
        else return mRepositoryManager
                .obtainRetrofitService(GroupService.class)
                .getGroupDiscusses(MApplication.getTokenOrType(), id, afterId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}