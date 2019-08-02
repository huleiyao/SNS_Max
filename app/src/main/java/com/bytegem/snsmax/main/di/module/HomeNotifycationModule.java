package com.bytegem.snsmax.main.di.module;

import com.bytegem.snsmax.main.mvp.ui.adapter.ChatsAdapter;
import com.bytegem.snsmax.main.mvp.ui.adapter.NotifycationAdapter;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.bytegem.snsmax.main.mvp.contract.HomeNotifycationContract;
import com.bytegem.snsmax.main.mvp.model.HomeNotifycationModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/25/2019 00:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class HomeNotifycationModule {

    @Binds
    abstract HomeNotifycationContract.Model bindHomeNotifycationModel(HomeNotifycationModel model);

    @FragmentScope
    @Provides
    static NotifycationAdapter provideNotifycationAdapter() {
        return new NotifycationAdapter();
    }
}