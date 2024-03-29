package com.bytegem.snsmax.main.mvp.model;

import android.app.Application;

import com.bytegem.snsmax.main.app.MApplication;
import com.bytegem.snsmax.main.app.bean.messages.ChatRoomResp;
import com.bytegem.snsmax.main.app.bean.user.DATAUser;
import com.bytegem.snsmax.main.app.config.CommunityService;
import com.bytegem.snsmax.main.app.config.UserService;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.bytegem.snsmax.main.mvp.contract.OwnerHomeContract;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/23/2019 13:28
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class OwnerHomeModel extends BaseModel implements OwnerHomeContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public OwnerHomeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<DATAUser> getUserData(boolean isMe, int id) {
        if (isMe)
            return mRepositoryManager
                    .obtainRetrofitService(UserService.class)
                    .getUser(MApplication.getTokenOrType());
        else return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .getUserFromId(MApplication.getTokenOrType(), id);
    }

    @Override
    public Observable<ChatRoomResp> sendUserMessage(int userId) {
        return mRepositoryManager
                .obtainRetrofitService(CommunityService.class)
                .getChatRoom(MApplication.getTokenOrType(), userId);

    }

    @Override
    public Gson getGson() {
        return mGson;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}