package com.bytegem.snsmax.main.mvp.model;

import android.app.Application;

import com.bytegem.snsmax.main.app.MApplication;
import com.bytegem.snsmax.main.app.bean.feed.LISTFeedComments;
import com.bytegem.snsmax.main.app.bean.NetDefaultBean;
import com.bytegem.snsmax.main.app.config.CommunityService;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.bytegem.snsmax.main.mvp.contract.FeedCommentsOfCommentContract;

import io.reactivex.Observable;
import okhttp3.RequestBody;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/01/2019 21:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class FeedCommentsOfCommentModel extends BaseModel implements FeedCommentsOfCommentContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public FeedCommentsOfCommentModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<LISTFeedComments> getList(int feedId, int id, int limit, int commentId, boolean isDefaultOrder, boolean isFirst) {
        if (isFirst)
            return mRepositoryManager
                    .obtainRetrofitService(CommunityService.class)
                    .getCommentsCommentsListDefault(id, limit, isDefaultOrder ? "desc" : "asc");
        else if (isDefaultOrder) return mRepositoryManager
                .obtainRetrofitService(CommunityService.class)
                .getCommentsCommentsListBefore(id, limit, "desc", commentId);
        else return mRepositoryManager
                    .obtainRetrofitService(CommunityService.class)
                    .getCommentsCommentsListAfter(id, limit, "asc", commentId);
    }

    @Override
    public Observable<NetDefaultBean> commit(int feedId, int id, String jsonData) {
        return mRepositoryManager
                .obtainRetrofitService(CommunityService.class)
                .sendFeedCommentsComment(MApplication.getTokenOrType(), id, RequestBody.create(mediaType, jsonData));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}