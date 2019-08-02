package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.bytegem.snsmax.main.di.module.FeedsModule;
import com.bytegem.snsmax.main.mvp.model.FeedFragmentModel;
import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.mvp.contract.FeedsContract;

import com.jess.arms.di.scope.FragmentScope;
import com.bytegem.snsmax.main.mvp.ui.fragment.FeedsFragment;


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
@Component(modules = FeedsModule.class, dependencies = AppComponent.class)
public interface FeedsComponent {
    void inject(FeedsFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        FeedsComponent.Builder view(FeedsContract.View view);

        FeedsComponent.Builder appComponent(AppComponent appComponent);

        FeedsComponent build();
    }
}