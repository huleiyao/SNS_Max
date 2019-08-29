package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.SearchDynamicModule;
import com.bytegem.snsmax.main.mvp.contract.SearchDynamicContract;

import com.jess.arms.di.scope.FragmentScope;
import com.bytegem.snsmax.main.mvp.ui.fragment.SearchDynamicFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/29/2019 16:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = SearchDynamicModule.class, dependencies = AppComponent.class)
public interface SearchDynamicComponent {
    void inject(SearchDynamicFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SearchDynamicComponent.Builder view(SearchDynamicContract.View view);

        SearchDynamicComponent.Builder appComponent(AppComponent appComponent);

        SearchDynamicComponent build();
    }
}