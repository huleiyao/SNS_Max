package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.OwnerHomeModule;
import com.bytegem.snsmax.main.mvp.contract.OwnerHomeContract;

import com.jess.arms.di.scope.ActivityScope;
import com.bytegem.snsmax.main.mvp.ui.activity.OwnerHomeActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/23/2019 13:28
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = OwnerHomeModule.class, dependencies = AppComponent.class)
public interface OwnerHomeComponent {
    void inject(OwnerHomeActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        OwnerHomeComponent.Builder view(OwnerHomeContract.View view);

        OwnerHomeComponent.Builder appComponent(AppComponent appComponent);

        OwnerHomeComponent build();
    }
}