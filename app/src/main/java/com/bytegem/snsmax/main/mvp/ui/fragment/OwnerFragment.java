package com.bytegem.snsmax.main.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bytegem.snsmax.main.app.bean.user.UserBean;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.bytegem.snsmax.main.mvp.ui.activity.OwnerHomeActivity;
import com.bytegem.snsmax.main.mvp.ui.activity.OwnerQRCodeActivity;
import com.bytegem.snsmax.main.mvp.ui.activity.SettingsActivity;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.bytegem.snsmax.main.di.component.DaggerOwnerComponent;
import com.bytegem.snsmax.main.mvp.contract.OwnerContract;
import com.bytegem.snsmax.main.mvp.presenter.OwnerPresenter;

import com.bytegem.snsmax.R;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageCropActivity;
import com.lzy.imagepicker.ui.ImageGridActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/10/2019 16:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class OwnerFragment extends BaseFragment<OwnerPresenter> implements OwnerContract.View, View.OnClickListener {
    private static OwnerFragment instance;

    @BindView(R.id.user_name)
    TextView user_name;
    @BindView(R.id.user_content)
    TextView user_content;
    @BindView(R.id.user_zan_count)
    TextView user_zan_count;
    @BindView(R.id.user_follow_count)
    TextView user_follow_count;
    @BindView(R.id.user_fans_count)
    TextView user_fans_count;
    @BindView(R.id.user_cover)
    ImageView user_cover;
    BottomSheetDialog changeUserCoverBottomSheetDialog;

    @OnClick({R.id.setting, R.id.owner_qrcode, R.id.scan, R.id.user_cover
            , R.id.owner_group, R.id.owner_favorites, R.id.community_honor, R.id.owner_treasure
            , R.id.owner_drafts, R.id.owner_share, R.id.help_or_feedback, R.id.owner, R.id.user_detail})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting:
                launchActivity(new Intent(getContext(), SettingsActivity.class));
                break;
            case R.id.owner_qrcode:
                launchActivity(new Intent(getContext(), OwnerQRCodeActivity.class));
                break;
            case R.id.scan:
                showMessage("去扫码");
                break;
            case R.id.user_detail:
                launchActivity(new Intent(getContext(), OwnerHomeActivity.class).putExtra(OwnerHomeActivity.ISME, true));
                break;
            case R.id.user_cover:
                changeUserCoverBottomSheetDialog.show();
                break;
            case R.id.owner:
                showMessage("我的圈子");
                break;
            case R.id.owner_group:
                showMessage("我的迹记");
                break;
            case R.id.owner_favorites:
                showMessage("我的收藏");
                break;
            case R.id.community_honor:
                showMessage("社区荣誉");
                break;
            case R.id.owner_treasure:
                showMessage("财富积分");
                break;
            case R.id.owner_drafts:
                showMessage("草稿箱");
                break;
            case R.id.owner_share:
                showMessage("邀请分享");
                break;
            case R.id.help_or_feedback:
                showMessage("帮助与反馈");
                break;
            case R.id.tv_take_photo:
                changeUserCoverBottomSheetDialog.dismiss();
                ImagePicker.getInstance().setCrop(true);
                ImagePicker.getInstance().takePicture(getActivity(), ImagePicker.REQUEST_CODE_TAKE);
                break;
            case R.id.tv_take_pic:
                changeUserCoverBottomSheetDialog.dismiss();
                Intent intent = new Intent(getContext(), ImageGridActivity.class);
                startActivityForResult(intent, 801);
                break;
            case R.id.tv_cancel:
                changeUserCoverBottomSheetDialog.dismiss();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImagePicker.REQUEST_CODE_TAKE) {
            Intent intent = new Intent(getContext(), ImageCropActivity.class);
            startActivityForResult(intent, ImagePicker.REQUEST_CODE_CROP);  //单选需要裁剪，进入裁剪界面
        }
    }

    public static OwnerFragment newInstance() {
        if (instance == null)
            instance = new OwnerFragment();
        return instance;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerOwnerComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_owner, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.getUserData();
        initCommitBottomSheetDialog();
    }

    public void updataCover(ImageItem imageItem) {
        mPresenter.getSign("image", imageItem);
    }

    private void initCommitBottomSheetDialog() {
        changeUserCoverBottomSheetDialog = new BottomSheetDialog(getContext());
        changeUserCoverBottomSheetDialog.setContentView(R.layout.dialog_change_user_cover);
        changeUserCoverBottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet)
                .setBackgroundColor(getResources().getColor(R.color.albumTransparent));
        changeUserCoverBottomSheetDialog.findViewById(R.id.tv_take_photo).setOnClickListener(this);
        changeUserCoverBottomSheetDialog.findViewById(R.id.tv_take_pic).setOnClickListener(this);
        changeUserCoverBottomSheetDialog.findViewById(R.id.tv_cancel).setOnClickListener(this);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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

    }

    @Override
    public void showUserData(UserBean userBean) {
        user_name.setText(userBean.getName());
        user_zan_count.setText(userBean.getLikes_count() + "");
        user_follow_count.setText(userBean.getFollowers_count() + "");
        user_fans_count.setText(userBean.getFollowings_count() + "");
//        user_content.setText(userBean.get());
        if (userBean.getAvatar() == null || userBean.getAvatar().isEmpty())
            GlideLoaderUtil.LoadCircleImage(mContext, R.drawable.ic_deskicon, user_cover);
        else
            GlideLoaderUtil.LoadCircleImage(mContext, Utils.checkUrl(userBean.getAvatar()), user_cover);
    }
}
