package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.OwnerRecordModule;
import com.bytegem.snsmax.main.mvp.contract.OwnerRecordContract;

import com.jess.arms.di.scope.FragmentScope;
import com.bytegem.snsmax.main.mvp.ui.fragment.OwnerRecordFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/30/2019 22:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = OwnerRecordModule.class, dependencies = AppComponent.class)
public interface OwnerRecordComponent {
    void inject(OwnerRecordFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        OwnerRecordComponent.Builder view(OwnerRecordContract.View view);

        OwnerRecordComponent.Builder appComponent(AppComponent appComponent);

        OwnerRecordComponent build();
    }
}