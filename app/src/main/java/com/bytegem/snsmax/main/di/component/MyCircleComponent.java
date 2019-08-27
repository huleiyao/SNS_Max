package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.MyCircleModule;
import com.bytegem.snsmax.main.mvp.contract.MyCircleContract;

import com.jess.arms.di.scope.ActivityScope;
import com.bytegem.snsmax.main.mvp.ui.activity.MyCircleActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/26/2019 14:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MyCircleModule.class, dependencies = AppComponent.class)
public interface MyCircleComponent {
    void inject(MyCircleActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyCircleComponent.Builder view(MyCircleContract.View view);

        MyCircleComponent.Builder appComponent(AppComponent appComponent);

        MyCircleComponent build();
    }
}