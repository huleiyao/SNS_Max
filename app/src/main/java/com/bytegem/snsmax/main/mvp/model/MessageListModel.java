package com.bytegem.snsmax.main.mvp.model;

import android.app.Application;

import com.bytegem.snsmax.main.app.MApplication;
import com.bytegem.snsmax.main.app.bean.chat.ChatList;
import com.bytegem.snsmax.main.app.bean.user.DATAUser;
import com.bytegem.snsmax.main.app.config.CommunityService;
import com.bytegem.snsmax.main.app.config.UserService;
import com.bytegem.snsmax.main.app.utils.HttpMvcHelper;
import com.bytegem.snsmax.main.app.utils.UserInfoUtils;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.bytegem.snsmax.main.mvp.contract.MessageListContract;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/05/2019 16:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class MessageListModel extends BaseModel implements MessageListContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MessageListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<ChatList> getUserChatList(int page) {
        return mRepositoryManager.obtainRetrofitService(CommunityService.class)
                .getUserChatList(MApplication.getTokenOrType(), page);
    }

    @Override
    public Observable<DATAUser> getUserData() {
        DATAUser userinfo = UserInfoUtils.getUserInfo(mGson);
        if (userinfo == null && userinfo.getData() != null) {
            return Observable.just(userinfo);
        } else {
            return mRepositoryManager.obtainRetrofitService(UserService.class)
                    .getUser(HttpMvcHelper.getTokenOrType())
                    .doOnNext(item -> UserInfoUtils.saveUserInfo(item,mGson));
        }
    }
}