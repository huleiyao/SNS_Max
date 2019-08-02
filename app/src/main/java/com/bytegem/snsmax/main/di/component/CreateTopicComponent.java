package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.CreateTopicModule;
import com.bytegem.snsmax.main.mvp.contract.CreateTopicContract;

import com.jess.arms.di.scope.ActivityScope;
import com.bytegem.snsmax.main.mvp.ui.activity.CreateTopicActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/10/2019 19:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = CreateTopicModule.class, dependencies = AppComponent.class)
public interface CreateTopicComponent {
    void inject(CreateTopicActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CreateTopicComponent.Builder view(CreateTopicContract.View view);

        CreateTopicComponent.Builder appComponent(AppComponent appComponent);

        CreateTopicComponent build();
    }
}