package com.bytemax.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytemax.snsmax.main.di.module.CommunityListModule;
import com.bytemax.snsmax.main.mvp.contract.CommunityListContract;

import com.jess.arms.di.scope.FragmentScope;
import com.bytemax.snsmax.main.mvp.ui.fragment.CommunityListFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/04/2019 17:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = CommunityListModule.class, dependencies = AppComponent.class)
public interface CommunityListComponent {
    void inject(CommunityListFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CommunityListComponent.Builder view(CommunityListContract.View view);

        CommunityListComponent.Builder appComponent(AppComponent appComponent);

        CommunityListComponent build();
    }
}