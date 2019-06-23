package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.CommunityPostDetailsModule;
import com.bytegem.snsmax.main.mvp.contract.CommunityPostDetailsContract;

import com.jess.arms.di.scope.ActivityScope;
import com.bytegem.snsmax.main.mvp.ui.activity.CommunityPostDetailsActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/23/2019 15:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = CommunityPostDetailsModule.class, dependencies = AppComponent.class)
public interface CommunityPostDetailsComponent {
    void inject(CommunityPostDetailsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CommunityPostDetailsComponent.Builder view(CommunityPostDetailsContract.View view);

        CommunityPostDetailsComponent.Builder appComponent(AppComponent appComponent);

        CommunityPostDetailsComponent build();
    }
}