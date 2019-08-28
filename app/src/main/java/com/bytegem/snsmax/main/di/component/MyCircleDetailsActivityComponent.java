package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.MyCircleDetailsActivityModule;
import com.bytegem.snsmax.main.mvp.contract.MyCircleDetailsActivityContract;

import com.jess.arms.di.scope.ActivityScope;
import com.bytegem.snsmax.main.mvp.ui.activity.MyCircleDetailsActivityActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/28/2019 11:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MyCircleDetailsActivityModule.class, dependencies = AppComponent.class)
public interface MyCircleDetailsActivityComponent {
    void inject(MyCircleDetailsActivityActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyCircleDetailsActivityComponent.Builder view(MyCircleDetailsActivityContract.View view);

        MyCircleDetailsActivityComponent.Builder appComponent(AppComponent appComponent);

        MyCircleDetailsActivityComponent build();
    }
}