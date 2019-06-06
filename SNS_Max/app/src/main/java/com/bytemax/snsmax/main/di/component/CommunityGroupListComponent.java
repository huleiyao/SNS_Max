package com.bytemax.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytemax.snsmax.main.di.module.CommunityGroupListModule;
import com.bytemax.snsmax.main.mvp.contract.CommunityGroupListContract;

import com.jess.arms.di.scope.FragmentScope;
import com.bytemax.snsmax.main.mvp.ui.fragment.CommunityGroupListFragment;


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
@Component(modules = CommunityGroupListModule.class, dependencies = AppComponent.class)
public interface CommunityGroupListComponent {
    void inject(CommunityGroupListFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CommunityGroupListComponent.Builder view(CommunityGroupListContract.View view);

        CommunityGroupListComponent.Builder appComponent(AppComponent appComponent);

        CommunityGroupListComponent build();
    }
}