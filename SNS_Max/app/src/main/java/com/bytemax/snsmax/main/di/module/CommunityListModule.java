package com.bytemax.snsmax.main.di.module;

import android.support.v7.widget.RecyclerView;

import com.bytemax.snsmax.main.mvp.ui.adapter.CommunityPostListAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.bytemax.snsmax.main.mvp.contract.CommunityListContract;
import com.bytemax.snsmax.main.mvp.model.CommunityListModel;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/04/2019 17:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class CommunityListModule {

    @Binds
    abstract CommunityListContract.Model bindCommunityListModel(CommunityListModel model);

    @ActivityScope
    @Provides
    static CommunityPostListAdapter provideCommunityPostListAdapter() {
        return new CommunityPostListAdapter();
    }
}