package com.bytegem.snsmax.main.mvp.model;

import android.app.Application;

import com.bytegem.snsmax.main.app.MApplication;
import com.bytegem.snsmax.main.app.bean.group.DATAGroup;
import com.bytegem.snsmax.main.app.bean.user.DATAUser;
import com.bytegem.snsmax.main.app.config.GroupService;
import com.bytegem.snsmax.main.app.config.UserService;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.bytegem.snsmax.main.mvp.contract.GroupDetailsContract;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/10/2019 19:45
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class GroupDetailsModel extends BaseModel implements GroupDetailsContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public GroupDetailsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<DATAGroup> getGroupData(int id) {
        return mRepositoryManager
                .obtainRetrofitService(GroupService.class)
                .getGroupDetails(MApplication.getTokenOrType(),id);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}