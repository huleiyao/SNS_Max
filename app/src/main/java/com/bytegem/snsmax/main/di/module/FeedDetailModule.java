package com.bytegem.snsmax.main.di.module;

import com.bytegem.snsmax.main.mvp.model.FeedFragmentModel;
import com.bytegem.snsmax.main.mvp.ui.adapter.FeedCommentsAdapter;
import com.bytegem.snsmax.main.mvp.ui.adapter.FeedCommentsOfCommentAdapter;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.bytegem.snsmax.main.mvp.contract.FeedDetailContract;
import com.jess.arms.di.scope.FragmentScope;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/06/2019 12:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class FeedDetailModule {

    @Binds
    abstract FeedDetailContract.Model bindFeedModel(FeedFragmentModel model);

    @FragmentScope
    @Provides
    static FeedCommentsAdapter provideCommunityCommentsAdapter() {
        return new FeedCommentsAdapter();
    }

    @FragmentScope
    @Provides
    static FeedCommentsOfCommentAdapter provideCommuntiyCommentsOfCommentAdapter() {
        return new FeedCommentsOfCommentAdapter();
    }
}