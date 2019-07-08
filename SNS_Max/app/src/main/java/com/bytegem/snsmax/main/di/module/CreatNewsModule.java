package com.bytegem.snsmax.main.di.module;

import com.bytegem.snsmax.main.mvp.ui.adapter.CreateImageAdapter;
import com.bytegem.snsmax.main.mvp.ui.adapter.GroupHotMessageAdapter;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.bytegem.snsmax.main.mvp.contract.CreatNewsContract;
import com.bytegem.snsmax.main.mvp.model.CreatNewsModel;
import com.jess.arms.di.scope.FragmentScope;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/22/2019 09:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class CreatNewsModule {

    @Binds
    abstract CreatNewsContract.Model bindCreatNewsModel(CreatNewsModel model);

    @ActivityScope
    @Provides
    static CreateImageAdapter provideCreateImageAdapter() {
        return new CreateImageAdapter();
    }
}