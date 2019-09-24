package com.bytegem.snsmax.main.mvp.presenter;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.bytegem.snsmax.main.app.bean.chat.ChatList;
import com.bytegem.snsmax.main.app.bean.user.DATAUser;
import com.bytegem.snsmax.main.app.mvc.chat.ChatActivity;
import com.bytegem.snsmax.main.app.utils.HttpMvcHelper;
import com.bytegem.snsmax.main.app.utils.UserInfoUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import javax.inject.Inject;

import com.bytegem.snsmax.main.mvp.contract.MessageListContract;
import com.jess.arms.utils.RxLifecycleUtils;


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
public class MessageListPresenter extends BasePresenter<MessageListContract.Model, MessageListContract.View> implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    int page = 1;

    @Inject
    public MessageListPresenter(MessageListContract.Model model, MessageListContract.View rootView) {
        super(model, rootView);
    }

    @SuppressLint("CheckResult")
    public void getUserChatList(boolean isLoad) {
        if (isLoad) {
            page = 1;
        }
        mModel.getUserData()
                .flatMap(userDto -> mModel.getUserChatList(page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .doFinally(() -> {
                    mRootView.onFinishFreshAndLoad();
                })
                .subscribe(new ErrorHandleSubscriber<ChatList>(mErrorHandler) {
                    @Override
                    public void onNext(ChatList chatList) {
                        if (chatList.data != null && chatList.data.size() > 0) {
                            mRootView.update(isLoad, chatList.data);
                            page += 1;
                        }
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
//        mRootView.launchActivity(new Intent(mApplication, ChatActivity.class));
        ChatList.ChatListItem item = mRootView.getChatListItem(position);
        if (item == null) {
            ToastUtils.showShort("未找到相关记录");
            return;
        }
        Intent it = new Intent(mApplication, ChatActivity.class);
        it.putExtra(ChatActivity.USREINFO_KEY, HttpMvcHelper.getGson().toJson(item.members));
        it.putExtra(ChatActivity.ROOM_ID, item.id);
        ((Fragment) mRootView).getActivity().startActivity(it);
    }
}
