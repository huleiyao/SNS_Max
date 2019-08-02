package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.WatchImageModule;
import com.bytegem.snsmax.main.mvp.contract.WatchImageContract;

import com.jess.arms.di.scope.ActivityScope;
import com.bytegem.snsmax.main.mvp.ui.activity.WatchImageActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/21/2019 17:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = WatchImageModule.class, dependencies = AppComponent.class)
public interface WatchImageComponent {
    void inject(WatchImageActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        WatchImageComponent.Builder view(WatchImageContract.View view);

        WatchImageComponent.Builder appComponent(AppComponent appComponent);

        WatchImageComponent build();
    }
}