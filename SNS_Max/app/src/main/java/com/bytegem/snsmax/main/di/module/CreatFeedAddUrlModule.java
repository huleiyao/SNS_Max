package com.bytegem.snsmax.main.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.bytegem.snsmax.main.mvp.contract.CreatFeedAddUrlContract;
import com.bytegem.snsmax.main.mvp.model.CreatFeedAddUrlModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/09/2019 13:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class CreatFeedAddUrlModule {

    @Binds
    abstract CreatFeedAddUrlContract.Model bindCreatFeedAddUrlModel(CreatFeedAddUrlModel model);
}