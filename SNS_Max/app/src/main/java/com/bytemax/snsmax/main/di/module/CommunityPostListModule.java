package com.bytemax.snsmax.main.di.module;

import com.bytemax.snsmax.main.mvp.ui.adapter.CommunityPostListAdapter;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.bytemax.snsmax.main.mvp.contract.CommunityPostListContract;
import com.bytemax.snsmax.main.mvp.model.CommunityPostListModel;


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
@Module
public abstract class CommunityPostListModule {

    @Binds
    abstract CommunityPostListContract.Model bindCommunityPostListModel(CommunityPostListModel model);

    @FragmentScope
    @Provides
    static CommunityPostListAdapter provideCommunityPostListAdapter() {
        return new CommunityPostListAdapter();
    }
}