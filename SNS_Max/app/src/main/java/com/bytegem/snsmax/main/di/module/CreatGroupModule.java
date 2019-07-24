package com.bytegem.snsmax.main.di.module;

import com.bytegem.snsmax.main.mvp.model.CreatGroupModel;
import com.bytegem.snsmax.main.mvp.model.FeedFragmentModel;

import dagger.Binds;
import dagger.Module;

import com.bytegem.snsmax.main.mvp.contract.CreatGroupContract;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/01/2019 11:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class CreatGroupModule {

    @Binds
    abstract CreatGroupContract.Model bindCreatGroupModel(CreatGroupModel model);
}