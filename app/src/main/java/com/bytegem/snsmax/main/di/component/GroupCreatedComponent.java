package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.GroupCreatedModule;
import com.bytegem.snsmax.main.mvp.contract.GroupCreatedContract;

import com.jess.arms.di.scope.ActivityScope;
import com.bytegem.snsmax.main.mvp.ui.activity.GroupCreatedActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/19/2019 08:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = GroupCreatedModule.class, dependencies = AppComponent.class)
public interface GroupCreatedComponent {
    void inject(GroupCreatedActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        GroupCreatedComponent.Builder view(GroupCreatedContract.View view);

        GroupCreatedComponent.Builder appComponent(AppComponent appComponent);

        GroupCreatedComponent build();
    }
}