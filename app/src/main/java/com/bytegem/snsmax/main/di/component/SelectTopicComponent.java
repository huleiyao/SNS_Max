package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.SelectTopicModule;
import com.bytegem.snsmax.main.mvp.contract.SelectTopicContract;

import com.jess.arms.di.scope.ActivityScope;
import com.bytegem.snsmax.main.mvp.ui.activity.SelectTopicActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/09/2019 18:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SelectTopicModule.class, dependencies = AppComponent.class)
public interface SelectTopicComponent {
    void inject(SelectTopicActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SelectTopicComponent.Builder view(SelectTopicContract.View view);

        SelectTopicComponent.Builder appComponent(AppComponent appComponent);

        SelectTopicComponent build();
    }
}