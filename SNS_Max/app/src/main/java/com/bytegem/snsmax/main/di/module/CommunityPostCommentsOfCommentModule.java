package com.bytegem.snsmax.main.di.module;

import com.bytegem.snsmax.main.mvp.ui.adapter.CommunityCommentsAdapter;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.bytegem.snsmax.main.mvp.contract.CommunityPostCommentsOfCommentContract;
import com.bytegem.snsmax.main.mvp.model.CommunityPostCommentsOfCommentModel;


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
@Module
public abstract class CommunityPostCommentsOfCommentModule {

    @Binds
    abstract CommunityPostCommentsOfCommentContract.Model bindCommunityPostCommentsOfCommentModel(CommunityPostCommentsOfCommentModel model);

    @ActivityScope
    @Provides
    static CommunityCommentsAdapter provideCommunityCommentsAdapter() {
        return new CommunityCommentsAdapter();
    }
}