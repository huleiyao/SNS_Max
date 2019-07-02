package com.bytegem.snsmax.main.di.module;

import com.bytegem.snsmax.main.mvp.ui.adapter.CommunityCommentsAdapter;
import com.bytegem.snsmax.main.mvp.ui.adapter.CommunityGroupHeaderListAdapter;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.bytegem.snsmax.main.mvp.contract.CommunityPostDetailsContract;
import com.bytegem.snsmax.main.mvp.model.CommunityPostDetailsModel;
import com.jess.arms.di.scope.FragmentScope;


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
public abstract class CommunityPostDetailsModule {

    @Binds
    abstract CommunityPostDetailsContract.Model bindCommunityPostDetailsModel(CommunityPostDetailsModel model);

    @ActivityScope
    @Provides
    static CommunityCommentsAdapter provideCommunityCommentsAdapter() {
        return new CommunityCommentsAdapter();
    }
}