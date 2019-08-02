package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.HomeNotifycationModule;
import com.bytegem.snsmax.main.mvp.contract.HomeNotifycationContract;

import com.jess.arms.di.scope.FragmentScope;
import com.bytegem.snsmax.main.mvp.ui.fragment.HomeNotifycationFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/25/2019 00:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = HomeNotifycationModule.class, dependencies = AppComponent.class)
public interface HomeNotifycationComponent {
    void inject(HomeNotifycationFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HomeNotifycationComponent.Builder view(HomeNotifycationContract.View view);

        HomeNotifycationComponent.Builder appComponent(AppComponent appComponent);

        HomeNotifycationComponent build();
    }
}