package com.bytegem.snsmax.main.di.module;

import com.bytegem.snsmax.main.mvp.model.FeedActivityModel;
import com.bytegem.snsmax.main.mvp.ui.adapter.FeedCommentsAdapter;
import com.bytegem.snsmax.main.mvp.ui.adapter.FeedCommentsOfCommentAdapter;
import com.bytegem.snsmax.main.mvp.ui.adapter.ImageAdapter;
import com.bytegem.snsmax.main.mvp.ui.adapter.ImageAdapter2;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.bytegem.snsmax.main.mvp.contract.FeedDetailsContract;


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
@Module
public abstract class FeedDetailsModule {

    @Binds
    abstract FeedDetailsContract.Model bindFeedModel(FeedActivityModel model);

    @ActivityScope
    @Provides
    static FeedCommentsAdapter provideCommunityCommentsAdapter() {
        return new FeedCommentsAdapter();
    }

    @ActivityScope
    @Provides
    static FeedCommentsOfCommentAdapter provideCommuntiyCommentsOfCommentAdapter() {
        return new FeedCommentsOfCommentAdapter();
    }

    @ActivityScope
    @Provides
    static ImageAdapter provideImageAdapter() {
        return new ImageAdapter();
    }

    @ActivityScope
    @Provides
    static ImageAdapter2 provideImageAdapter2() {
        return new ImageAdapter2();
    }
}