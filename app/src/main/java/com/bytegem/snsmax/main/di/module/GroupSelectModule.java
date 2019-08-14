package com.bytegem.snsmax.main.di.module;

import com.bytegem.snsmax.main.mvp.ui.adapter.GroupMemberAdapter;
import com.bytegem.snsmax.main.mvp.ui.adapter.GroupsSelectAdapter;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.bytegem.snsmax.main.mvp.contract.GroupSelectContract;
import com.bytegem.snsmax.main.mvp.model.GroupSelectModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/13/2019 10:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class GroupSelectModule {

    @Binds
    abstract GroupSelectContract.Model bindGroupSelectModel(GroupSelectModel model);

    @ActivityScope
    @Provides
    static GroupsSelectAdapter provideGroupsSelectAdapter() {
        return new GroupsSelectAdapter();
    }
}