package com.bytegem.snsmax.main.di.module;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.bytegem.snsmax.main.mvp.contract.SearchDynamicContract;
import com.bytegem.snsmax.main.mvp.model.SearchDynamicModel;
import com.bytegem.snsmax.main.mvp.ui.adapter.GroupHeadersAdapter;
import com.bytegem.snsmax.main.mvp.ui.adapter.GroupsAdapter;
import com.jess.arms.di.scope.FragmentScope;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/29/2019 16:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class SearchDynamicModule {

    @Binds
    abstract SearchDynamicContract.Model bindSearchDynamicModel(SearchDynamicModel model);

    @FragmentScope
    @Provides
    static GroupsAdapter provideScearchGroupListAdapter() {
        return new GroupsAdapter();
    }

    @FragmentScope
    @Provides
    static GroupHeadersAdapter provideScearchGroupHeaderListAdapter() {
        return new GroupHeadersAdapter();
    }
}