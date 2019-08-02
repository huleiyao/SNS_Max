package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.DiscussListModule;
import com.bytegem.snsmax.main.mvp.contract.DiscussListContract;

import com.jess.arms.di.scope.FragmentScope;
import com.bytegem.snsmax.main.mvp.ui.fragment.DiscussListFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/12/2019 09:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = DiscussListModule.class, dependencies = AppComponent.class)
public interface DiscussListComponent {
    void inject(DiscussListFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        DiscussListComponent.Builder view(DiscussListContract.View view);

        DiscussListComponent.Builder appComponent(AppComponent appComponent);

        DiscussListComponent build();
    }
}