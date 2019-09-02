package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.PersonalTraceModule;
import com.bytegem.snsmax.main.mvp.contract.PersonalTraceContract;

import com.jess.arms.di.scope.FragmentScope;
import com.bytegem.snsmax.main.mvp.ui.fragment.PersonalTraceFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/01/2019 19:50
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = PersonalTraceModule.class, dependencies = AppComponent.class)
public interface PersonalTraceComponent {
    void inject(PersonalTraceFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PersonalTraceComponent.Builder view(PersonalTraceContract.View view);

        PersonalTraceComponent.Builder appComponent(AppComponent appComponent);

        PersonalTraceComponent build();
    }
}