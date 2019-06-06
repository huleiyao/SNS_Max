package com.bytemax.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytemax.snsmax.main.di.module.HomeFindModule;
import com.bytemax.snsmax.main.mvp.contract.HomeFindContract;

import com.jess.arms.di.scope.FragmentScope;
import com.bytemax.snsmax.main.mvp.ui.fragment.HomeFindFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/05/2019 16:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = HomeFindModule.class, dependencies = AppComponent.class)
public interface HomeFindComponent {
    void inject(HomeFindFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HomeFindComponent.Builder view(HomeFindContract.View view);

        HomeFindComponent.Builder appComponent(AppComponent appComponent);

        HomeFindComponent build();
    }
}