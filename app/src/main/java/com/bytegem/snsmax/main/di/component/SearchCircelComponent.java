package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.SearchCircelModule;
import com.bytegem.snsmax.main.mvp.contract.SearchCircelContract;

import com.jess.arms.di.scope.FragmentScope;
import com.bytegem.snsmax.main.mvp.ui.fragment.SearchCircelFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/31/2019 11:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = SearchCircelModule.class, dependencies = AppComponent.class)
public interface SearchCircelComponent {
    void inject(SearchCircelFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SearchCircelComponent.Builder view(SearchCircelContract.View view);

        SearchCircelComponent.Builder appComponent(AppComponent appComponent);

        SearchCircelComponent build();
    }
}