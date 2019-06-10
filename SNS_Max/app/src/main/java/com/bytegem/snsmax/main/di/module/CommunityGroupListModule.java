package com.bytegem.snsmax.main.di.module;

import android.content.Context;

import com.bytegem.snsmax.main.mvp.ui.adapter.CommunityGroupHeaderListAdapter;
import com.bytegem.snsmax.main.mvp.ui.adapter.CommunityGroupListAdapter;
import com.bytegem.snsmax.main.mvp.ui.adapter.CommunityPostListAdapter;
import com.bytegem.snsmax.main.mvp.ui.view.HomeFindTopGroupView;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.bytegem.snsmax.main.mvp.contract.CommunityGroupListContract;
import com.bytegem.snsmax.main.mvp.model.CommunityGroupListModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/05/2019 11:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class CommunityGroupListModule {

    @Binds
    abstract CommunityGroupListContract.Model bindCommunityGroupListModel(CommunityGroupListModel model);

    @FragmentScope
    @Provides
    static CommunityGroupListAdapter provideCommunityGroupListAdapter() {
        return new CommunityGroupListAdapter();
    }

    @FragmentScope
    @Provides
    static CommunityGroupHeaderListAdapter provideCommunityGroupHeaderListAdapter() {
        return new CommunityGroupHeaderListAdapter();
    }
}