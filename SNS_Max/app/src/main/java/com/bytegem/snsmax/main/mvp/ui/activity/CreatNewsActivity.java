package com.bytegem.snsmax.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bytegem.snsmax.main.app.bean.feed.MediaBean;
import com.bytegem.snsmax.main.app.bean.feed.MediaLinkContent;
import com.bytegem.snsmax.main.app.bean.topic.TopicBean;
import com.bytegem.snsmax.main.app.widget.TagEditTextView;
import com.bytegem.snsmax.main.app.widget.TagTextView;
import com.bytegem.snsmax.main.mvp.ui.adapter.CreateImageAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.bytegem.snsmax.main.di.component.DaggerCreatNewsComponent;
import com.bytegem.snsmax.main.mvp.contract.CreatNewsContract;
import com.bytegem.snsmax.main.mvp.presenter.CreatNewsPresenter;

import com.bytegem.snsmax.R;
import com.lzy.imagepicker.GlideImageLoader;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;


import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.bytegem.snsmax.main.mvp.ui.activity.CreatNewsActivity.FeedType.CAMERA;
import static com.bytegem.snsmax.main.mvp.ui.activity.CreatNewsActivity.FeedType.DEFAULT;
import static com.bytegem.snsmax.main.mvp.ui.activity.CreatNewsActivity.FeedType.VIDEO;
import static com.jess.arms.utils.Preconditions.checkNotNull;


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
public class CreatNewsActivity extends BaseActivity<CreatNewsPresenter> implements CreatNewsContract.View {
    @BindView(R.id.recycle_view)
    RecyclerView recycle_view;

    @BindView(R.id.address)
    LinearLayout address;
    @BindView(R.id.url)
    FrameLayout url;

    @BindView(R.id.content)
    TagEditTextView content;

    @BindView(R.id.camera)
    ImageView camera;
    @BindView(R.id.video)
    ImageView video;
    @BindView(R.id.link)
    ImageView link;


    @BindView(R.id.url_text)
    TextView url_text;
    @BindView(R.id.group_name)
    TextView group_name;
    @BindView(R.id.address_txt)
    TextView address_txt;
    MediaBean mediaBean = new MediaBean();
    @Inject
    CreateImageAdapter adapter;
    FeedType feedType = DEFAULT;
    MaterialDialog materialDialog;

    public enum FeedType {
        CAMERA, VIDEO, LINK, DEFAULT
    }

    @OnClick({R.id.toolbar_send, R.id.select_group, R.id.camera, R.id.video, R.id.link, R.id.topic, R.id.down, R.id.delect_url})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_send:
                String content_str = content.getContent();
                if (content_str.isEmpty()) {
                    showMessage("请输入内容！");
                    return;
                }
                mPresenter.checkSend(content_str, mediaBean, feedType, content.getTopicBean());
                break;
            case R.id.select_group://选择圈子
                break;
            case R.id.camera://图片
                if (feedType == FeedType.CAMERA)
                    return;
                url.setVisibility(View.GONE);
                video.setImageResource(R.drawable.ic_ico_post_video);
                link.setImageResource(R.drawable.ic_ico_post_link);
                camera.setImageResource(R.drawable.ic_ico_post_camera_high);
                feedType = FeedType.CAMERA;
                adapter.setFeedType(feedType);
                mediaBean.setType("image");
                openPhotos();
                break;
            case R.id.video://视频
                if (true) {
                    showMessage("正在开发中...");
                    return;
                }
                if (feedType == FeedType.VIDEO)
                    return;
                url.setVisibility(View.GONE);
                video.setImageResource(R.drawable.ic_ico_post_video_high);
                link.setImageResource(R.drawable.ic_ico_post_link);
                camera.setImageResource(R.drawable.ic_ico_post_camera);
                feedType = FeedType.VIDEO;
                adapter.setFeedType(feedType);
                mediaBean.setType("video");
                break;
            case R.id.link://链接
                if (feedType == FeedType.LINK)
                    return;
                startActivityForResult(new Intent(this, CreatFeedAddUrlActivity.class), 103);
                break;
            case R.id.topic://选择话题
                startActivityForResult(new Intent(this, SelectTopicActivity.class), 104);
                break;
            case R.id.down://收起/打开
                break;
            case R.id.delect_url://取消link分享
                if (feedType == FeedType.LINK) {
                    feedType = DEFAULT;
                    link.setImageResource(R.drawable.ic_ico_post_link);
                    url.setVisibility(View.GONE);
                    mediaBean.setType("");
                }
                break;
        }

    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCreatNewsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_creat_news; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("编辑动态");
//        content.setListener(mPresenter);//话题的点击事件
        initImagePicker();
        recycle_view.setLayoutManager(new GridLayoutManager(this, 4));// 布局管理器
        recycle_view.setAdapter(adapter);
        recycle_view.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(mPresenter);
        adapter.setOnItemChildClickListener(mPresenter);
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setMultiMode(true);
        imagePicker.setShowCamera(true);
        imagePicker.setSelectLimit(9);
        imagePicker.setCrop(false);
    }

    @Override
    //已选择图片预览
    public void watchImagePicker(int position) {
        ArrayList<ImageItem> imageItems = (ArrayList<ImageItem>) adapter.getData();
        if (imageItems.get(imageItems.size() - 1).path.equals("add"))
            imageItems.remove(imageItems.size() - 1);
        Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
        intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, imageItems);
        intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
        intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);

        startActivityForResult(intentPreview, 903);

    }

    @Override
    public void openPhotos() {
        ArrayList<ImageItem> imageItems = (ArrayList<ImageItem>) adapter.getData();
        if (imageItems != null && imageItems.size() > 0) {//最后一个是默认的添加，需要移除
            ImageItem imageItem = imageItems.get(imageItems.size() - 1);
            if (imageItem.path.equals("add"))
                imageItems.remove(imageItem);
        }
        Intent intent1 = new Intent(this, ImageGridActivity.class);
        intent1.putParcelableArrayListExtra(ImageGridActivity.EXTRAS_IMAGES, imageItems);
        startActivityForResult(intent1, 1001);
    }

    @Override
    public void remove(int position) {
        adapter.remove(position);
        mediaBean.setImageItems(adapter.getData());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ArrayList<ImageItem> imageItems = null;
        if (requestCode == 1001) {
            if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
                imageItems = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            }
            setAdapterData(imageItems);
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == 903) {
                imageItems = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
            }
            setAdapterData(imageItems);
        } else if (resultCode == 103) {
            MediaLinkContent mediaLinkContent = (MediaLinkContent) data.getSerializableExtra("url");
            if (mediaLinkContent == null) ;
            else
                showUrl(mediaLinkContent);
        } else if (resultCode == 104) {
            TopicBean topicBean = (TopicBean) data.getSerializableExtra("topic");
            if (topicBean == null) ;
            else
                showTopic(topicBean);
        }
    }

    private void showUrl(MediaLinkContent mediaLinkContent) {
        video.setImageResource(R.drawable.ic_ico_post_video);
        camera.setImageResource(R.drawable.ic_ico_post_camera);
        link.setImageResource(R.drawable.ic_ico_post_link_high);
        feedType = FeedType.LINK;
        mediaBean.setType("url");
        mediaBean.setMediaLink(mediaLinkContent);
        url_text.setText(mediaLinkContent.getTitle());
        url.setVisibility(View.VISIBLE);
    }

    private void showTopic(TopicBean topicBean) {
        content.setObject(topicBean, true);
    }

    public void setAdapterData(ArrayList<ImageItem> imageItems) {
        if (imageItems == null)
            imageItems = new ArrayList<>();
        mediaBean.setImageItems(imageItems);
        if (feedType == CAMERA) {
            if (imageItems.size() < 9 && !imageItems.get(imageItems.size() - 1).path.equals("add")) {
                ImageItem imageItem = new ImageItem();
                imageItem.path = "add";
                imageItems.add(imageItem);
            }
        } else if (feedType == VIDEO) {
            ImageItem imageItem = new ImageItem();
            imageItem.path = "add";
            imageItems.add(imageItem);
        }
        adapter.setFeedType(feedType, imageItems);
    }

    @Override
    public void showLoading() {
        hideLoading();
        materialDialog = new MaterialDialog.Builder(this)
//                .title("正在上传图片")
                .content("上传中···")
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .canceledOnTouchOutside(false)
                .show();
    }

    @Override
    public void hideLoading() {
        if (materialDialog != null && materialDialog.isShowing()) materialDialog.dismiss();
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
}
