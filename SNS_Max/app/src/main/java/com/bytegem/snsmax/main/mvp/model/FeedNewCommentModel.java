package com.bytegem.snsmax.main.mvp.model;

import android.app.Application;

import com.bytegem.snsmax.main.app.MApplication;
import com.bytegem.snsmax.main.app.bean.NetDefaultBean;
import com.bytegem.snsmax.main.app.bean.feed.LISTFeedComments;
import com.bytegem.snsmax.main.app.config.CommunityService;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.bytegem.snsmax.main.mvp.contract.FeedNewCommentContract;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/20/2019 15:39
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class FeedNewCommentModel extends BaseModel implements FeedNewCommentContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public FeedNewCommentModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<LISTFeedComments> getList(int id, int limit, int commentId, boolean isDefaultOrder, boolean isFirst) {
        if (isFirst)
            return mRepositoryManager
                    .obtainRetrofitService(CommunityService.class)
                    .getCommentsListDefault(MApplication.getTokenOrType(), id, limit, isDefaultOrder ? "desc" : "asc");
        else if (isDefaultOrder) return mRepositoryManager
                .obtainRetrofitService(CommunityService.class)
                .getCommentsListBefore(MApplication.getTokenOrType(), id, limit, "desc", commentId);
        else return mRepositoryManager
                    .obtainRetrofitService(CommunityService.class)
                    .getCommentsListAfter(MApplication.getTokenOrType(), id, limit, "asc", commentId);
    }


    @Override
    public Observable<NetDefaultBean> changeCommentLikeState(int id, boolean isLike) {
        if (isLike)
            return mRepositoryManager
                    .obtainRetrofitService(CommunityService.class)
                    .changeCommentDislikeState(MApplication.getTokenOrType(), id);
        else return mRepositoryManager
                .obtainRetrofitService(CommunityService.class)
                .changeCommentLikeState(MApplication.getTokenOrType(), id);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}