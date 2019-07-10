package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.CreatFeedAddUrlModule;
import com.bytegem.snsmax.main.mvp.contract.CreatFeedAddUrlContract;

import com.jess.arms.di.scope.ActivityScope;
import com.bytegem.snsmax.main.mvp.ui.activity.CreatFeedAddUrlActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/09/2019 13:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = CreatFeedAddUrlModule.class, dependencies = AppComponent.class)
public interface CreatFeedAddUrlComponent {
    void inject(CreatFeedAddUrlActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CreatFeedAddUrlComponent.Builder view(CreatFeedAddUrlContract.View view);

        CreatFeedAddUrlComponent.Builder appComponent(AppComponent appComponent);

        CreatFeedAddUrlComponent build();
    }
}