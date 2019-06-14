package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.AddressSelectModule;
import com.bytegem.snsmax.main.mvp.contract.AddressSelectContract;

import com.jess.arms.di.scope.ActivityScope;
import com.bytegem.snsmax.main.mvp.ui.activity.AddressSelectActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/12/2019 17:48
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AddressSelectModule.class, dependencies = AppComponent.class)
public interface AddressSelectComponent {
    void inject(AddressSelectActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AddressSelectComponent.Builder view(AddressSelectContract.View view);

        AddressSelectComponent.Builder appComponent(AppComponent appComponent);

        AddressSelectComponent build();
    }
}