package com.bytegem.snsmax.main.di.module;

import com.bytegem.snsmax.main.mvp.ui.adapter.DiscussAdapter;
import com.bytegem.snsmax.main.mvp.ui.adapter.FeedCommentsOfCommentAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.bytegem.snsmax.main.mvp.contract.DiscussListContract;
import com.bytegem.snsmax.main.mvp.model.DiscussListModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/12/2019 09:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class DiscussListModule {

    @Binds
    abstract DiscussListContract.Model bindDiscussListModel(DiscussListModel model);

    @FragmentScope
    @Provides
    static DiscussAdapter provideDiscussAdapter() {
        return new DiscussAdapter();
    }
}