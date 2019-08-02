package com.bytegem.snsmax.main.mvp.model;

import android.app.Application;

import com.bytegem.snsmax.main.app.MApplication;
import com.bytegem.snsmax.main.app.bean.feed.LISTFeedComments;
import com.bytegem.snsmax.main.app.bean.feed.LISTFeeds;
import com.bytegem.snsmax.main.app.bean.topic.DATATopic;
import com.bytegem.snsmax.main.app.bean.user.DATAUser;
import com.bytegem.snsmax.main.app.config.CommunityService;
import com.bytegem.snsmax.main.app.config.TopicService;
import com.bytegem.snsmax.main.app.config.UserService;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.bytegem.snsmax.main.mvp.contract.TopicDetailContract;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/13/2019 08:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class TopicDetailModel extends BaseModel implements TopicDetailContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public TopicDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<DATATopic> getTopicData(int id) {
        return mRepositoryManager
                .obtainRetrofitService(TopicService.class)
                .getTopicDetail(MApplication.getTokenOrType(), id);
    }

    @Override
    public Observable<LISTFeeds> getList(boolean isFirst, int id, int limit, boolean isDefaultOrder, int feedId) {
        if (isFirst)
            return mRepositoryManager
                    .obtainRetrofitService(TopicService.class)
                    .getTopicFeedsListDefault(MApplication.getTokenOrType(), id, limit, isDefaultOrder ? "desc" : "asc");
        else if (isDefaultOrder) return mRepositoryManager
                .obtainRetrofitService(TopicService.class)
                .getTopicFeedsistBefore(MApplication.getTokenOrType(), id, limit, "desc", feedId);
        else return mRepositoryManager
                    .obtainRetrofitService(TopicService.class)
                    .getTopicFeedsListAfter(MApplication.getTokenOrType(), id, limit, "asc", feedId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}