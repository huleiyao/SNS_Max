package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.OwnerFeedsModule;
import com.bytegem.snsmax.main.mvp.contract.OwnerFeedsContract;

import com.jess.arms.di.scope.FragmentScope;
import com.bytegem.snsmax.main.mvp.ui.fragment.OwnerFeedsFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/28/2019 10:44
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = OwnerFeedsModule.class, dependencies = AppComponent.class)
public interface OwnerFeedsComponent {
    void inject(OwnerFeedsFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        OwnerFeedsComponent.Builder view(OwnerFeedsContract.View view);

        OwnerFeedsComponent.Builder appComponent(AppComponent appComponent);

        OwnerFeedsComponent build();
    }
}