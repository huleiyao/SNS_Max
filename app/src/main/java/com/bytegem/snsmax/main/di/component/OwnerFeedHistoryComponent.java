package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.OwnerFeedHistoryModule;
import com.bytegem.snsmax.main.mvp.contract.OwnerFeedHistoryContract;

import com.jess.arms.di.scope.ActivityScope;
import com.bytegem.snsmax.main.mvp.ui.activity.OwnerFeedHistoryActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/06/2019 16:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = OwnerFeedHistoryModule.class, dependencies = AppComponent.class)
public interface OwnerFeedHistoryComponent {
    void inject(OwnerFeedHistoryActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        OwnerFeedHistoryComponent.Builder view(OwnerFeedHistoryContract.View view);

        OwnerFeedHistoryComponent.Builder appComponent(AppComponent appComponent);

        OwnerFeedHistoryComponent build();
    }
}