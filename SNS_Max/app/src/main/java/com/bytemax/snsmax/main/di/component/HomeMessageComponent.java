package com.bytemax.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytemax.snsmax.main.di.module.HomeMessageModule;
import com.bytemax.snsmax.main.mvp.contract.HomeMessageContract;

import com.jess.arms.di.scope.FragmentScope;
import com.bytemax.snsmax.main.mvp.ui.fragment.HomeMessageFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/05/2019 16:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = HomeMessageModule.class, dependencies = AppComponent.class)
public interface HomeMessageComponent {
    void inject(HomeMessageFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HomeMessageComponent.Builder view(HomeMessageContract.View view);

        HomeMessageComponent.Builder appComponent(AppComponent appComponent);

        HomeMessageComponent build();
    }
}