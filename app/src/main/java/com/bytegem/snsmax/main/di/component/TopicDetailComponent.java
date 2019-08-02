package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.TopicDetailModule;
import com.bytegem.snsmax.main.mvp.contract.TopicDetailContract;

import com.jess.arms.di.scope.ActivityScope;
import com.bytegem.snsmax.main.mvp.ui.activity.TopicDetailActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/13/2019 08:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = TopicDetailModule.class, dependencies = AppComponent.class)
public interface TopicDetailComponent {
    void inject(TopicDetailActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TopicDetailComponent.Builder view(TopicDetailContract.View view);

        TopicDetailComponent.Builder appComponent(AppComponent appComponent);

        TopicDetailComponent build();
    }
}