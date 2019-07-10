package com.bytegem.snsmax.main.mvp.model;

import android.app.Application;

import com.bytegem.snsmax.main.app.MApplication;
import com.bytegem.snsmax.main.app.bean.feed.LISTFeedComments;
import com.bytegem.snsmax.main.app.bean.feed.DATAFeedComment;
import com.bytegem.snsmax.main.app.bean.NetDefaultBean;
import com.bytegem.snsmax.main.app.config.CommunityService;
import com.bytegem.snsmax.main.app.config.UserService;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.bytegem.snsmax.main.mvp.contract.FeedDetailsContract;

import io.reactivex.Observable;
import okhttp3.RequestBody;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/23/2019 15:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class FeedDetailsModel extends BaseModel implements FeedDetailsContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;
    public static String gets = "";

    @Inject
    public FeedDetailsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<LISTFeedComments> getList(int id, int limit, int commentId, boolean isDefaultOrder, boolean isFirst) {
        if (isFirst)
            return mRepositoryManager
                    .obtainRetrofitService(CommunityService.class)
                    .getCommentsListDefault(id, limit, isDefaultOrder ? "desc" : "asc");
        else if (isDefaultOrder) return mRepositoryManager
                .obtainRetrofitService(CommunityService.class)
                .getCommentsListBefore(id, limit, "desc", commentId);
        else return mRepositoryManager
                    .obtainRetrofitService(CommunityService.class)
                    .getCommentsListAfter(id, limit, "asc", commentId);
    }

    @Override
    public Observable<DATAFeedComment> getHotComment(int id) {
        return mRepositoryManager
                .obtainRetrofitService(CommunityService.class)
                .getHotComment(id);
    }

    @Override
    public Observable<NetDefaultBean> changeLikeState(int id, boolean isLike) {
        if (isLike)
            return mRepositoryManager
                    .obtainRetrofitService(CommunityService.class)
                    .changeDislikeState(MApplication.getTokenOrType(), id);
        else return mRepositoryManager
                .obtainRetrofitService(CommunityService.class)
                .changeLikeState(MApplication.getTokenOrType(), id);
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
    public Observable<NetDefaultBean> changeUserFollowState(int id, boolean isFollow) {
        if (isFollow)
            return mRepositoryManager
                    .obtainRetrofitService(UserService.class)
                    .changeUserUnfollowState(MApplication.getTokenOrType(), id);
        else return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .changeUserFollowState(MApplication.getTokenOrType(), id);
    }

    @Override
    public Observable<NetDefaultBean> commit(int id, String jsonData) {
        return mRepositoryManager
                .obtainRetrofitService(CommunityService.class)
                .sendFeedComment(MApplication.getTokenOrType(), id, RequestBody.create(mediaType, jsonData));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}