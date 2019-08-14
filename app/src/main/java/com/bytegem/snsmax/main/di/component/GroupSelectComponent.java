package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.GroupSelectModule;
import com.bytegem.snsmax.main.mvp.contract.GroupSelectContract;

import com.jess.arms.di.scope.ActivityScope;
import com.bytegem.snsmax.main.mvp.ui.activity.GroupSelectActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/13/2019 10:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = GroupSelectModule.class, dependencies = AppComponent.class)
public interface GroupSelectComponent {
    void inject(GroupSelectActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        GroupSelectComponent.Builder view(GroupSelectContract.View view);

        GroupSelectComponent.Builder appComponent(AppComponent appComponent);

        GroupSelectComponent build();
    }
}