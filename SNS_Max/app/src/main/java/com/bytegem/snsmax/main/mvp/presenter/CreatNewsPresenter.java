package com.bytegem.snsmax.main.mvp.presenter;

import android.app.Application;
import android.view.View;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.common.utils.M;
import com.bytegem.snsmax.main.app.bean.feed.MediaBean;
import com.bytegem.snsmax.main.app.bean.FileSignBean;
import com.bytegem.snsmax.main.app.bean.location.LocationBean;
import com.bytegem.snsmax.main.app.bean.NetDefaultBean;
import com.bytegem.snsmax.main.app.bean.topic.TopicBean;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.bytegem.snsmax.main.app.widget.TagTextView;
import com.bytegem.snsmax.main.mvp.ui.activity.CreatNewsActivity;
import com.bytegem.snsmax.main.mvp.ui.adapter.CreateImageAdapter;
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

import com.bytegem.snsmax.main.mvp.contract.CreatNewsContract;
import com.jess.arms.utils.RxLifecycleUtils;
import com.lzy.imagepicker.bean.ImageItem;

import java.io.EOFException;
import java.io.File;
import java.util.List;

import static com.bytegem.snsmax.main.app.MApplication.location;


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
@ActivityScope
public class CreatNewsPresenter extends BasePresenter<CreatNewsContract.Model, CreatNewsContract.View> implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener, TagTextView.TopicListener {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    CreateImageAdapter adapter;
    MediaBean mMediaBean;
    String mContent;
    TopicBean mTopicBean;
    boolean isStartSend = false;

    @Inject
    public CreatNewsPresenter(CreatNewsContract.Model model, CreatNewsContract.View rootView) {
        super(model, rootView);
    }

    public void checkSend(String content, MediaBean mediaBean, CreatNewsActivity.FeedType feedType, TopicBean topicBean) {
        mRootView.showLoading();
        mContent = content;
        mTopicBean = topicBean;
        mMediaBean = mediaBean;
        switch (feedType) {
            case CAMERA:
                List<ImageItem> imageItems = mMediaBean.getImageItems();
                if (imageItems != null && imageItems.size() > 0) {
                    if (imageItems.get(imageItems.size() - 1).path.equals("add"))
                        imageItems.remove(imageItems.size() - 1);
                    mMediaBean.setImages(new String[imageItems.size()]);
                    for (int i = 0; i < imageItems.size(); i++) {
                        getSign("image", imageItems.get(i), i);
                    }
                } else send();
                break;
            case VIDEO:
//                getSign("video", imageItems.get(i), i);
                break;
            case LINK:
                send();
                break;
            default:
                send();
                break;
        }
    }

    public void getSign(String type, ImageItem imageItem, int position) {
        mRootView.showLoading();
        File tempCover = M.getTempFile(mApplication, imageItem.mimeType);
        if (tempCover == null) {
            mRootView.showMessage("上传失败");
            mRootView.hideLoading();
            return;
        }
        mModel.getSign(type, tempCover, imageItem.size, M.getFileMD5(new File(imageItem.path)))
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError((Throwable onError) -> {
                    mRootView.showMessage("上传失败");
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<FileSignBean>(mErrorHandler) {
                    @Override
                    public void onNext(FileSignBean data) {
                        updataCover(data, imageItem, position);
                    }
                });
    }

    public void updataCover(FileSignBean fileSignBean, ImageItem imageItem, int position) {
        mModel.updataCover(fileSignBean, imageItem)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
//                    mRootView.hideLoading();
                })
                .doOnError((Throwable onError) -> {
                    if (onError instanceof EOFException) {
                        //无数据返回  成功
                        mMediaBean.setImages(position, Utils.checkUrl(fileSignBean.getPath()));
                        if (!isStartSend && mMediaBean.checkImage())
                            send();
                    } else {
                        mRootView.showMessage("上传失败");
                        mRootView.hideLoading();
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<MBaseBean>(mErrorHandler) {
                    @Override
                    public void onNext(MBaseBean data) {
                    }
                });
    }

    public void send() {
        if (location == null)
            location = new LocationBean();
        isStartSend = true;
        String jsonData = "";
        MBaseBean bean = mMediaBean.getMedia();
        if (bean == null || mMediaBean.getType().isEmpty())
            jsonData = M.getMapString(
                    "geo", location.getGeo()
                    , "contents", mContent
            );
        else jsonData = M.getMapString(
                "geo", location.getGeo()
                , "contents", mContent
                , "media", bean
        );
        (mTopicBean == null ?
                mModel.send(jsonData)
                : mModel.topicSend(mTopicBean.getId(), jsonData))
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    isStartSend = false;
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<NetDefaultBean>(mErrorHandler) {
                    @Override
                    public void onNext(NetDefaultBean data) {
                        mRootView.killMyself();
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
        switch (view.getId()) {
            case R.id.creat_image_item_delete:
                mRootView.remove(position);
                break;
            case R.id.creat_image_item_add:
                CreatNewsActivity.FeedType feedType = ((CreateImageAdapter) adapter).getFeedType();
                if (feedType == CreatNewsActivity.FeedType.CAMERA) mRootView.openPhotos();
                else if (feedType == CreatNewsActivity.FeedType.VIDEO) {

                }
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mRootView.watchImagePicker(position);
    }

    @Override
    public void topicListener(TopicBean topicBean) {
        //点击话题后的操作
    }
}
