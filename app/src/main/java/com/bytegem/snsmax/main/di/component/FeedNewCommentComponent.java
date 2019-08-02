package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.bytegem.snsmax.main.di.module.FeedNewCommentModule;
import com.bytegem.snsmax.main.mvp.model.FeedActivityModel;
import com.bytegem.snsmax.main.mvp.model.FeedFragmentModel;
import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.mvp.contract.FeedNewCommentContract;

import com.jess.arms.di.scope.ActivityScope;
import com.bytegem.snsmax.main.mvp.ui.activity.FeedNewCommentActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/20/2019 15:39
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = FeedNewCommentModule.class, dependencies = AppComponent.class)
public interface FeedNewCommentComponent {
    void inject(FeedNewCommentActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        FeedNewCommentComponent.Builder view(FeedNewCommentContract.View view);

        FeedNewCommentComponent.Builder appComponent(AppComponent appComponent);

        FeedNewCommentComponent build();
    }
}