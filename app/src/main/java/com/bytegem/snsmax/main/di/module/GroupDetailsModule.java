package com.bytegem.snsmax.main.di.module;

import com.bytegem.snsmax.main.mvp.ui.adapter.GroupHotMessageAdapter;
import com.bytegem.snsmax.main.mvp.ui.adapter.GroupMemberAdapter;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.bytegem.snsmax.main.mvp.contract.GroupDetailsContract;
import com.bytegem.snsmax.main.mvp.model.GroupDetailsModel;
import com.jess.arms.di.scope.FragmentScope;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/10/2019 19:45
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class GroupDetailsModule {

    @Binds
    abstract GroupDetailsContract.Model bindGroupDetailsModel(GroupDetailsModel model);

    @ActivityScope
    @Provides
    static GroupMemberAdapter provideGroupMemberAdapter() {
        return new GroupMemberAdapter();
    }
}