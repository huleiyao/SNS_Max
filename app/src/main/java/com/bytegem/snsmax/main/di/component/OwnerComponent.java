package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.OwnerModule;
import com.bytegem.snsmax.main.mvp.contract.OwnerContract;

import com.jess.arms.di.scope.FragmentScope;
import com.bytegem.snsmax.main.mvp.ui.fragment.OwnerFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/10/2019 16:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = OwnerModule.class, dependencies = AppComponent.class)
public interface OwnerComponent {
    void inject(OwnerFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        OwnerComponent.Builder view(OwnerContract.View view);

        OwnerComponent.Builder appComponent(AppComponent appComponent);

        OwnerComponent build();
    }
}