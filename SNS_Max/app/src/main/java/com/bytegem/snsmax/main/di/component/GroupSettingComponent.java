package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.GroupSettingModule;
import com.bytegem.snsmax.main.mvp.contract.GroupSettingContract;

import com.jess.arms.di.scope.ActivityScope;
import com.bytegem.snsmax.main.mvp.ui.activity.GroupSettingActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/12/2019 14:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = GroupSettingModule.class, dependencies = AppComponent.class)
public interface GroupSettingComponent {
    void inject(GroupSettingActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        GroupSettingComponent.Builder view(GroupSettingContract.View view);

        GroupSettingComponent.Builder appComponent(AppComponent appComponent);

        GroupSettingComponent build();
    }
}