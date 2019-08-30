package com.bytegem.snsmax.main.mvp.model;

import android.app.Application;

import com.bytegem.snsmax.main.app.bean.user.SearchDTO;
import com.bytegem.snsmax.main.app.config.GroupService;
import com.bytegem.snsmax.main.app.utils.HttpMvcHelper;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.bytegem.snsmax.main.mvp.contract.SearchUserContract;

import io.reactivex.Observable;


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
public class SearchUserModel extends BaseModel implements SearchUserContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SearchUserModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<SearchDTO<SearchDTO.SearchUserItem>> queryUser(String keydows, String after) {
        return mRepositoryManager.obtainCacheService(GroupService.class)
                .search(
                        HttpMvcHelper.getTokenOrType(),
                        "users",
                        keydows,
                        after,
                        SearchDTO.SearchUserItem.class
                );
    }
}