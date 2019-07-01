package com.bytegem.snsmax.main.di.module;

import com.bytegem.snsmax.main.mvp.ui.adapter.OwnerFeedsAdapter;
import com.bytegem.snsmax.main.mvp.ui.adapter.OwnerGroupsAdapter;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.bytegem.snsmax.main.mvp.contract.OwnerGroupsContract;
import com.bytegem.snsmax.main.mvp.model.OwnerGroupsModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/30/2019 11:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class OwnerGroupsModule {

    @Binds
    abstract OwnerGroupsContract.Model bindOwnerGroupsModel(OwnerGroupsModel model);


    @FragmentScope
    @Provides
    static OwnerGroupsAdapter provideOwnerGroupsAdapter() {
        return new OwnerGroupsAdapter();
    }
}