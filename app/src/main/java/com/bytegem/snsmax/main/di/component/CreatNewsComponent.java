package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.bytegem.snsmax.main.di.module.CreatNewsModule;
import com.bytegem.snsmax.main.mvp.model.FeedActivityModel;
import com.bytegem.snsmax.main.mvp.model.FeedFragmentModel;
import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.mvp.contract.CreatNewsContract;

import com.jess.arms.di.scope.ActivityScope;
import com.bytegem.snsmax.main.mvp.ui.activity.CreatNewsActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/22/2019 09:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = CreatNewsModule.class, dependencies = AppComponent.class)
public interface CreatNewsComponent {
    void inject(CreatNewsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CreatNewsComponent.Builder view(CreatNewsContract.View view);

        CreatNewsComponent.Builder appComponent(AppComponent appComponent);

        CreatNewsComponent build();
    }
}