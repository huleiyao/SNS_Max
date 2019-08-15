package com.bytegem.snsmax.main.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.view.View;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.bean.feed.FeedCommentBean;
import com.bytegem.snsmax.main.app.bean.feed.LISTFeedComments;
import com.bytegem.snsmax.main.app.bean.user.LISTUser;
import com.bytegem.snsmax.main.app.bean.user.UserBean;
import com.bytegem.snsmax.main.mvp.ui.activity.OwnerHomeActivity;
import com.bytegem.snsmax.main.mvp.ui.adapter.GroupMemberLineAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.bytegem.snsmax.main.mvp.contract.GroupMemberContract;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.ArrayList;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/26/2019 15:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class GroupMemberPresenter extends BasePresenter<GroupMemberContract.Model, GroupMemberContract.View> implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemLongClickListener {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    GroupMemberLineAdapter adapter;
    int per_page = 20;
    int page = 1;
    int groupId;

    @Inject
    public GroupMemberPresenter(GroupMemberContract.Model model, GroupMemberContract.View rootView) {
        super(model, rootView);
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
        getGroupMemberList(false);
    }

    public void getGroupMemberList(boolean isLoadMore) {
        if (!isLoadMore) {
            page = 1;
            mRootView.showLoading();
        } else page++;
        mModel.getGroupMemberList(groupId, per_page, page)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<LISTUser>(mErrorHandler) {
                    @Override
                    public void onNext(LISTUser data) {
                        ArrayList<UserBean> feedCommentBeans = data.getData();
                        if (isLoadMore)
                            adapter.addData(feedCommentBeans);
                        else
                            adapter.setNewData(feedCommentBeans);
                        mRootView.onFinishFreshAndLoad();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Object item = adapter.getItem(position);
        if (item != null && item instanceof UserBean)
            mRootView.launchActivity(new Intent(mApplication, OwnerHomeActivity.class)
                    .putExtra(OwnerHomeActivity.ID, ((UserBean) item).getId())
                    .putExtra(OwnerHomeActivity.ISME, false)
            );
    }

    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        Object item = adapter.getItem(position);
        if (item != null && item instanceof UserBean) {
            UserBean bean = (UserBean) item;
            if (bean.getGroup_member() != null)
                switch (bean.getGroup_member().getRole()) {
                    case "creator"://圈主
                        break;
                    case "manager"://管理员
                        break;
                    default://普通人
                }
        }
        return false;
    }
}
