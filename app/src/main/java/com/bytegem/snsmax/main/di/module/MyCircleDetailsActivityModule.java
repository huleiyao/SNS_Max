package com.bytegem.snsmax.main.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.bytegem.snsmax.main.mvp.contract.MyCircleDetailsActivityContract;
import com.bytegem.snsmax.main.mvp.model.MyCircleDetailsActivityModel;


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
@Module
public abstract class MyCircleDetailsActivityModule {

    @Binds
    abstract MyCircleDetailsActivityContract.Model bindMyCircleDetailsActivityModel(MyCircleDetailsActivityModel model);
}