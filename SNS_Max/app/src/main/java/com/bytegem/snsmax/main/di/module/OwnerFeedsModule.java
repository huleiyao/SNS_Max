package com.bytegem.snsmax.main.di.module;

import com.bytegem.snsmax.main.mvp.ui.adapter.OwnerFeedsAdapter;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.bytegem.snsmax.main.mvp.contract.OwnerFeedsContract;
import com.bytegem.snsmax.main.mvp.model.OwnerFeedsModel;


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
@Module
public abstract class OwnerFeedsModule {

    @Binds
    abstract OwnerFeedsContract.Model bindOwnerFeedsModel(OwnerFeedsModel model);

    @FragmentScope
    @Provides
    static OwnerFeedsAdapter provideOwnerFeedsAdapter() {
        return new OwnerFeedsAdapter();
    }
}