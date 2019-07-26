package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.GroupMemberModule;
import com.bytegem.snsmax.main.mvp.contract.GroupMemberContract;

import com.jess.arms.di.scope.ActivityScope;
import com.bytegem.snsmax.main.mvp.ui.activity.GroupMemberActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/26/2019 15:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = GroupMemberModule.class, dependencies = AppComponent.class)
public interface GroupMemberComponent {
    void inject(GroupMemberActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        GroupMemberComponent.Builder view(GroupMemberContract.View view);

        GroupMemberComponent.Builder appComponent(AppComponent appComponent);

        GroupMemberComponent build();
    }
}