package com.bytegem.snsmax.main.mvp.model;

import android.app.Application;

import com.bytegem.snsmax.main.app.MApplication;
import com.bytegem.snsmax.main.app.bean.location.LISTTencentMapLocation;
import com.bytegem.snsmax.main.app.config.GroupService;
import com.bytegem.snsmax.main.app.config.LocationService;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.bytegem.snsmax.main.mvp.contract.AddressSelectContract;

import io.reactivex.Observable;


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
public class AddressSelectModel extends BaseModel implements AddressSelectContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;
    String key = "3NEBZ-W7UCP-FIBD4-VM2FU-DKBPF-7GBHD";

    @Inject
    public AddressSelectModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<LISTTencentMapLocation> search(String keyword, String nearby, int count, int page) {
        if (keyword == null || keyword.isEmpty())
            return mRepositoryManager
                    .obtainRetrofitService(LocationService.class)
                    .searchLocation(nearby, count, page, key);
        else return mRepositoryManager
                .obtainRetrofitService(LocationService.class)
                .searchLocation(nearby, count, page, key, keyword);
    }
}