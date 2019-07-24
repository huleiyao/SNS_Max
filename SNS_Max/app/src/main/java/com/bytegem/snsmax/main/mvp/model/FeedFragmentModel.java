package com.bytegem.snsmax.main.mvp.model;

import android.app.Application;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.main.app.MApplication;
import com.bytegem.snsmax.main.app.bean.FileSignBean;
import com.bytegem.snsmax.main.app.bean.NetDefaultBean;
import com.bytegem.snsmax.main.app.bean.feed.DATAFeedComment;
import com.bytegem.snsmax.main.app.bean.feed.LISTFeedComments;
import com.bytegem.snsmax.main.app.bean.feed.LISTFeeds;
import com.bytegem.snsmax.main.app.config.CommunityService;
import com.bytegem.snsmax.main.app.config.TopicService;
import com.bytegem.snsmax.main.app.config.UpdataImageService;
import com.bytegem.snsmax.main.app.config.UserService;
import com.bytegem.snsmax.main.mvp.contract.CreatNewsContract;
import com.bytegem.snsmax.main.mvp.contract.FeedCommentsOfCommentContract;
import com.bytegem.snsmax.main.mvp.contract.FeedDetailsContract;
import com.bytegem.snsmax.main.mvp.contract.FeedNewCommentContract;
import com.bytegem.snsmax.main.mvp.contract.FeedsContract;
import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.lzy.imagepicker.bean.ImageItem;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/05/2019 11:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class FeedFragmentModel extends FeedModel {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public FeedFragmentModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}