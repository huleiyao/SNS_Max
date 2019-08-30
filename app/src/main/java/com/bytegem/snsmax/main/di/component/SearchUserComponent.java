package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.SearchUserModule;
import com.bytegem.snsmax.main.mvp.contract.SearchUserContract;

import com.jess.arms.di.scope.FragmentScope;
import com.bytegem.snsmax.main.mvp.ui.fragment.SearchUserFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/30/2019 14:15
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = SearchUserModule.class, dependencies = AppComponent.class)
public interface SearchUserComponent {
    void inject(SearchUserFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SearchUserComponent.Builder view(SearchUserContract.View view);

        SearchUserComponent.Builder appComponent(AppComponent appComponent);

        SearchUserComponent build();
    }
}