package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.OwnerQRCodeModule;
import com.bytegem.snsmax.main.mvp.contract.OwnerQRCodeContract;

import com.jess.arms.di.scope.ActivityScope;
import com.bytegem.snsmax.main.mvp.ui.activity.OwnerQRCodeActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/12/2019 14:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = OwnerQRCodeModule.class, dependencies = AppComponent.class)
public interface OwnerQRCodeComponent {
    void inject(OwnerQRCodeActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        OwnerQRCodeComponent.Builder view(OwnerQRCodeContract.View view);

        OwnerQRCodeComponent.Builder appComponent(AppComponent appComponent);

        OwnerQRCodeComponent build();
    }
}