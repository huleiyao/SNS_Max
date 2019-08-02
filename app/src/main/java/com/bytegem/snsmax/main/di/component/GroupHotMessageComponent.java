package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.GroupHotMessageModule;
import com.bytegem.snsmax.main.mvp.contract.GroupHotMessageContract;

import com.jess.arms.di.scope.FragmentScope;
import com.bytegem.snsmax.main.mvp.ui.fragment.GroupHotMessageFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/03/2019 08:46
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = GroupHotMessageModule.class, dependencies = AppComponent.class)
public interface GroupHotMessageComponent {
    void inject(GroupHotMessageFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        GroupHotMessageComponent.Builder view(GroupHotMessageContract.View view);

        GroupHotMessageComponent.Builder appComponent(AppComponent appComponent);

        GroupHotMessageComponent build();
    }
}