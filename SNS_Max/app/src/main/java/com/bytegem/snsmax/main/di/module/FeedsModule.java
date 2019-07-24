package com.bytegem.snsmax.main.di.module;

import com.bytegem.snsmax.main.mvp.model.FeedFragmentModel;
import com.bytegem.snsmax.main.mvp.ui.adapter.FeedsAdapter;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.bytegem.snsmax.main.mvp.contract.FeedsContract;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/05/2019 11:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class FeedsModule {

    @Binds
    abstract FeedsContract.Model bindFeedModel(FeedFragmentModel model);

    @FragmentScope
    @Provides
    static FeedsAdapter provideCommunityPostListAdapter() {
        return new FeedsAdapter();
    }
}