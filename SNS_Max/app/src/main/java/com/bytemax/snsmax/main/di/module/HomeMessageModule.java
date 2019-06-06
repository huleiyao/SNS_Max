package com.bytemax.snsmax.main.di.module;

import com.bytemax.snsmax.main.mvp.ui.adapter.ChatListAdapter;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.bytemax.snsmax.main.mvp.contract.HomeMessageContract;
import com.bytemax.snsmax.main.mvp.model.HomeMessageModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/05/2019 16:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class HomeMessageModule {

    @Binds
    abstract HomeMessageContract.Model bindHomeMessageModel(HomeMessageModel model);

}