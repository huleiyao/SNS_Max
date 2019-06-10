package com.bytegem.snsmax.main.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.bytegem.snsmax.main.di.module.MessageListModule;
import com.bytegem.snsmax.main.mvp.contract.MessageListContract;

import com.jess.arms.di.scope.FragmentScope;
import com.bytegem.snsmax.main.mvp.ui.fragment.MessageListFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/05/2019 16:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = MessageListModule.class, dependencies = AppComponent.class)
public interface MessageListComponent {
    void inject(MessageListFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MessageListComponent.Builder view(MessageListContract.View view);

        MessageListComponent.Builder appComponent(AppComponent appComponent);

        MessageListComponent build();
    }
}