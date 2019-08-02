package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.bytegem.snsmax.main.mvp.contract.GroupsContract;
import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.GroupsModule;

import com.jess.arms.di.scope.FragmentScope;
import com.bytegem.snsmax.main.mvp.ui.fragment.GroupsFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/05/2019 11:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = GroupsModule.class, dependencies = AppComponent.class)
public interface GroupsComponent {
    void inject(GroupsFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        GroupsComponent.Builder view(GroupsContract.View view);

        GroupsComponent.Builder appComponent(AppComponent appComponent);

        GroupsComponent build();
    }
}