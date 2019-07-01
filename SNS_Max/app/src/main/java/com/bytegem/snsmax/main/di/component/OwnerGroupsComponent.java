package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.OwnerGroupsModule;
import com.bytegem.snsmax.main.mvp.contract.OwnerGroupsContract;

import com.jess.arms.di.scope.FragmentScope;
import com.bytegem.snsmax.main.mvp.ui.fragment.OwnerGroupsFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/30/2019 11:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = OwnerGroupsModule.class, dependencies = AppComponent.class)
public interface OwnerGroupsComponent {
    void inject(OwnerGroupsFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        OwnerGroupsComponent.Builder view(OwnerGroupsContract.View view);

        OwnerGroupsComponent.Builder appComponent(AppComponent appComponent);

        OwnerGroupsComponent build();
    }
}