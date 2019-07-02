package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.CommunityPostCommentsOfCommentModule;
import com.bytegem.snsmax.main.mvp.contract.CommunityPostCommentsOfCommentContract;

import com.jess.arms.di.scope.ActivityScope;
import com.bytegem.snsmax.main.mvp.ui.activity.CommunityPostCommentsOfCommentActivity;


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
@Component(modules = CommunityPostCommentsOfCommentModule.class, dependencies = AppComponent.class)
public interface CommunityPostCommentsOfCommentComponent {
    void inject(CommunityPostCommentsOfCommentActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        CommunityPostCommentsOfCommentComponent.Builder view(CommunityPostCommentsOfCommentContract.View view);

        CommunityPostCommentsOfCommentComponent.Builder appComponent(AppComponent appComponent);

        CommunityPostCommentsOfCommentComponent build();
    }
}