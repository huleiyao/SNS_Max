package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.bytegem.snsmax.main.mvp.contract.FeedDetailsContract;
import com.bytegem.snsmax.main.mvp.ui.activity.FeedDetailsActivity;
import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.FeedDetailsModule;

import com.jess.arms.di.scope.ActivityScope;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/23/2019 15:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = FeedDetailsModule.class, dependencies = AppComponent.class)
public interface FeedDetailsComponent {
    void inject(FeedDetailsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        FeedDetailsComponent.Builder view(FeedDetailsContract.View view);

        FeedDetailsComponent.Builder appComponent(AppComponent appComponent);

        FeedDetailsComponent build();
    }
}