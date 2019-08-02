package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;
import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.VideoPlayerModule;
import com.bytegem.snsmax.main.mvp.contract.VideoPlayerContract;

import com.jess.arms.di.scope.ActivityScope;
import com.bytegem.snsmax.main.mvp.ui.activity.VideoPlayerActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/17/2019 07:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = VideoPlayerModule.class, dependencies = AppComponent.class)
public interface VideoPlayerComponent {
    void inject(VideoPlayerActivity activity);
    @Component.Builder
    interface Builder {
        @BindsInstance
        VideoPlayerComponent.Builder view(VideoPlayerContract.View view);
        VideoPlayerComponent.Builder appComponent(AppComponent appComponent);
        VideoPlayerComponent build();
    }
}