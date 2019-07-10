package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.bytegem.snsmax.main.mvp.contract.FeedCommentsOfCommentContract;
import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.FeedCommentsOfCommentModule;

import com.jess.arms.di.scope.ActivityScope;
import com.bytegem.snsmax.main.mvp.ui.activity.FeedCommentsOfCommentActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/01/2019 21:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = FeedCommentsOfCommentModule.class, dependencies = AppComponent.class)
public interface FeedCommentsOfCommentComponent {
    void inject(FeedCommentsOfCommentActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        FeedCommentsOfCommentComponent.Builder view(FeedCommentsOfCommentContract.View view);

        FeedCommentsOfCommentComponent.Builder appComponent(AppComponent appComponent);

        FeedCommentsOfCommentComponent build();
    }
}