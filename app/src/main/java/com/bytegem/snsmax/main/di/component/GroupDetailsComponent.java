package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.GroupDetailsModule;
import com.bytegem.snsmax.main.mvp.contract.GroupDetailsContract;

import com.jess.arms.di.scope.ActivityScope;
import com.bytegem.snsmax.main.mvp.ui.activity.GroupDetailsActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/10/2019 19:45
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = GroupDetailsModule.class, dependencies = AppComponent.class)
public interface GroupDetailsComponent {
    void inject(GroupDetailsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        GroupDetailsComponent.Builder view(GroupDetailsContract.View view);

        GroupDetailsComponent.Builder appComponent(AppComponent appComponent);

        GroupDetailsComponent build();
    }
}