package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.CommunityPostListModule;
import com.bytegem.snsmax.main.mvp.contract.CommunityPostListContract;

import com.jess.arms.di.scope.FragmentScope;
import com.bytegem.snsmax.main.mvp.ui.fragment.CommunityPostListFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/05/2019 11:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = CommunityPostListModule.class, dependencies = AppComponent.class)
public interface CommunityPostListComponent {
    void inject(CommunityPostListFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CommunityPostListComponent.Builder view(CommunityPostListContract.View view);

        CommunityPostListComponent.Builder appComponent(AppComponent appComponent);

        CommunityPostListComponent build();
    }
}