package com.bytegem.snsmax.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bytegem.snsmax.main.app.bean.feed.FeedBean;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.bytegem.snsmax.main.di.component.DaggerWatchImageComponent;
import com.bytegem.snsmax.main.mvp.contract.WatchImageContract;
import com.bytegem.snsmax.main.mvp.presenter.WatchImagePresenter;

import com.bytegem.snsmax.R;
import com.lzy.imagepicker.DataHolder;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.adapter.ImagePageAdapterForImagePath;
import com.lzy.imagepicker.ui.ImageBaseActivity;
import com.lzy.imagepicker.view.ViewPagerFixed;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/21/2019 17:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class WatchImageActivity extends BaseActivity<WatchImagePresenter> implements WatchImageContract.View {
    protected ImagePicker imagePicker;
    protected ArrayList<String> mImageItems;      //跳转进ImagePreviewFragment的图片文件夹
    protected int mCurrentPosition = 0;              //跳转进ImagePreviewFragment时的序号，第几个图片
    @BindView(R.id.watch_image_content)
    RelativeLayout content;
    @BindView(R.id.viewpager)
    ViewPagerFixed mViewPager;
    protected ImagePageAdapterForImagePath mAdapter;
    protected boolean isFromItems = false;

    @BindView(R.id.video_player_group)
    LinearLayout group;

    @BindView(R.id.video_player_group_cover)
    ImageView group_cover;
    @BindView(R.id.video_player_user_cover)
    ImageView user_cover;
    @BindView(R.id.video_player_add_user)
    ImageView add_user;
    @BindView(R.id.video_player_like_cover)
    ImageView video_player_like_cover;

    @BindView(R.id.video_player_user_name)
    TextView user_name;
    @BindView(R.id.video_player_like_count)
    TextView like_count;
    @BindView(R.id.video_player_omment_count)
    TextView omment_count;
    @BindView(R.id.video_player_shape_count)
    TextView shape_count;
    @BindView(R.id.video_player_feed_content)
    TextView feed_content;
    @BindView(R.id.video_player_group_name)
    TextView group_name;


    @BindView(R.id.image_feed_info)
    LinearLayout image_feed_info;

    private FeedBean mFeedBean;

    @OnClick({R.id.video_player_add_user, R.id.video_player_video_more, R.id.video_player_like
            , R.id.video_player_comment, R.id.video_player_shape, R.id.video_player_group})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.video_player_add_user:
                if (mFeedBean != null && mFeedBean.getUser() != null && mFeedBean.getUser().getId() > 0)
                    launchActivity(new Intent(this, OwnerHomeActivity.class)
                            .putExtra(OwnerHomeActivity.ISME, false)
                            .putExtra(OwnerHomeActivity.ID, mFeedBean.getUser().getId())
                    );
                break;
            case R.id.video_player_video_more:

                break;
            case R.id.video_player_like:

                break;
            case R.id.video_player_comment:
                if (mFeedBean != null)
                    launchActivity(new Intent(this, FeedDetailsActivity.class)
                            .putExtra("data", mFeedBean)
                    );
                break;
            case R.id.video_player_shape:

                break;
            case R.id.video_player_group://进入圈子

                break;
        }
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWatchImageComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    MaterialDialog materialDialog;

    @Override
    public void showLoading() {
        hideLoading();
        materialDialog = getMaterialDialog("", "").show();
    }

    @Override
    public void hideLoading() {
        if (materialDialog != null && materialDialog.isShowing()) materialDialog.dismiss();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        ImagePicker.getInstance().restoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        ImagePicker.getInstance().saveInstanceState(outState);
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


    /**
     * 单击时，隐藏头和尾
     */
    public void onImageSingleTap() {

    }

    ;

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ImagePicker.getInstance().restoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ImagePicker.getInstance().saveInstanceState(outState);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_watch_image;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mCurrentPosition = getIntent().getIntExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0);
        isFromItems = getIntent().getBooleanExtra(ImagePicker.EXTRA_FROM_ITEMS, false);

        if (isFromItems) {
            // 据说这样会导致大量图片崩溃
            mImageItems = (ArrayList<String>) getIntent().getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
        } else {
            // 下面采用弱引用会导致预览崩溃
            mImageItems = (ArrayList<String>) DataHolder.getInstance().retrieve(DataHolder.DH_CURRENT_IMAGE_FOLDER_ITEMS);
        }

        imagePicker = ImagePicker.getInstance();
        mAdapter = new ImagePageAdapterForImagePath(this, mImageItems);
        mAdapter.setPhotoViewClickListener(new ImagePageAdapterForImagePath.PhotoViewClickListener() {
            @Override
            public void OnPhotoTapListener(View view, float v, float v1) {
                onImageSingleTap();
            }
        });
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(mCurrentPosition, false);

        FeedBean feedBean = (FeedBean) getIntent().getSerializableExtra("feed");
        if (feedBean == null) {
            image_feed_info.setVisibility(View.GONE);
        } else
            showFeed(feedBean);
    }

    public void showFeed(FeedBean feedBean) {
        mFeedBean = feedBean;
        if (mFeedBean.getUser() != null) {
            user_name.setText(mFeedBean.getUser().getName());
            if (mFeedBean.getUser().getAvatar() == null || mFeedBean.getUser().getAvatar().isEmpty())
                GlideLoaderUtil.LoadCircleImage(this, R.drawable.ic_deskicon, user_cover);
            else
                GlideLoaderUtil.LoadCircleImage(this, Utils.checkUrl(mFeedBean.getUser().getAvatar()), user_cover);
//            if (mFeedBean.getUser().)@TODO 此用户是否已关注，需显示或隐藏+号图片
            add_user.setVisibility(View.VISIBLE);
        } else add_user.setVisibility(View.GONE);
        if (mFeedBean.getGroup() != null && mFeedBean.getGroup().getId() > 0) {
            group.setVisibility(View.VISIBLE);
            GlideLoaderUtil.LoadImage(this, Utils.checkUrl(mFeedBean.getGroup().getAvatar()), group_cover);
            group_name.setText(mFeedBean.getGroup().getName());
        } else group.setVisibility(View.GONE);
        feed_content.setText(mFeedBean.getContents());
        like_count.setText(Utils.getNumberIfPeople(mFeedBean.getLikes_count()));
        if (mFeedBean.isHas_liked())
            video_player_like_cover.setImageResource(R.drawable.ic_ico_video_zan_on);
        else
            video_player_like_cover.setImageResource(R.drawable.ic_ico_video_zan);
        omment_count.setText(Utils.getNumberIfPeople(mFeedBean.getComments_count()));
        shape_count.setText(Utils.getNumberIfPeople(mFeedBean.getShare_count()));
    }
}
