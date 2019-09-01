package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.SearchDiscussModule;
import com.bytegem.snsmax.main.mvp.contract.SearchDiscussContract;

import com.jess.arms.di.scope.FragmentScope;
import com.bytegem.snsmax.main.mvp.ui.fragment.SearchDiscussFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/31/2019 10:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = SearchDiscussModule.class, dependencies = AppComponent.class)
public interface SearchDiscussComponent {
    void inject(SearchDiscussFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SearchDiscussComponent.Builder view(SearchDiscussContract.View view);

        SearchDiscussComponent.Builder appComponent(AppComponent appComponent);

        SearchDiscussComponent build();
    }
}