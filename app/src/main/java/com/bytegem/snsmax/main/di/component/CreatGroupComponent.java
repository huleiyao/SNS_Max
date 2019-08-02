package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.CreatGroupModule;
import com.bytegem.snsmax.main.mvp.contract.CreatGroupContract;

import com.jess.arms.di.scope.ActivityScope;
import com.bytegem.snsmax.main.mvp.ui.activity.CreatGroupActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/01/2019 11:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = CreatGroupModule.class, dependencies = AppComponent.class)
public interface CreatGroupComponent {
    void inject(CreatGroupActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CreatGroupComponent.Builder view(CreatGroupContract.View view);

        CreatGroupComponent.Builder appComponent(AppComponent appComponent);

        CreatGroupComponent build();
    }
}