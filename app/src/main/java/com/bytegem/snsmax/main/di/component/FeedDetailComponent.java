package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.FeedDetailModule;
import com.bytegem.snsmax.main.mvp.contract.FeedDetailContract;

import com.jess.arms.di.scope.FragmentScope;
import com.bytegem.snsmax.main.mvp.ui.fragment.FeedDetailFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/06/2019 12:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = FeedDetailModule.class, dependencies = AppComponent.class)
public interface FeedDetailComponent {
    void inject(FeedDetailFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        FeedDetailComponent.Builder view(FeedDetailContract.View view);

        FeedDetailComponent.Builder appComponent(AppComponent appComponent);

        FeedDetailComponent build();
    }
}