package com.bytegem.snsmax.main.di.module;

import com.bytegem.snsmax.main.mvp.ui.adapter.GroupHotMessageAdapter;
import com.bytegem.snsmax.main.mvp.ui.adapter.OwnerFeedsAdapter;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.bytegem.snsmax.main.mvp.contract.GroupHotMessageContract;
import com.bytegem.snsmax.main.mvp.model.GroupHotMessageModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/03/2019 08:46
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class GroupHotMessageModule {

    @Binds
    abstract GroupHotMessageContract.Model bindGroupHotMessageModel(GroupHotMessageModel model);

    @FragmentScope
    @Provides
    static GroupHotMessageAdapter provideGroupHotMessageAdapter() {
        return new GroupHotMessageAdapter();
    }
}