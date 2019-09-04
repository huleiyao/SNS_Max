package com.bytegem.snsmax.main.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bytegem.snsmax.main.app.Api;
import com.bytegem.snsmax.main.app.bean.user.DATAUser;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.bytegem.snsmax.main.app.utils.HttpMvcHelper;
import com.bytegem.snsmax.main.app.utils.UserInfoUtils;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.bytegem.snsmax.zxing.encode.CodeCreator;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.bytegem.snsmax.main.di.component.DaggerOwnerQRCodeComponent;
import com.bytegem.snsmax.main.mvp.contract.OwnerQRCodeContract;
import com.bytegem.snsmax.main.mvp.presenter.OwnerQRCodePresenter;

import com.bytegem.snsmax.R;


import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/12/2019 14:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class OwnerQRCodeActivity extends BaseActivity<OwnerQRCodePresenter> implements OwnerQRCodeContract.View {

    @BindView(R.id.owner_qrcode_qrcode)
    ImageView imgQrcode;
    @BindView(R.id.owner_qrcode_name)
    TextView txtName;
    @BindView(R.id.owner_qrcode_location)
    TextView txtLocation;
    @BindView(R.id.owner_qrcode_avatar)
    ImageView imgAvatar;
    Bitmap bitmap = null;
    private DATAUser dataUser;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOwnerQRCodeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_owner_qrcode; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("我的二维码");
        dataUser = UserInfoUtils.getUserInfo(HttpMvcHelper.getGson());
        if (dataUser != null && dataUser.getData().getId() != 0) {
            txtName.setText(dataUser.getData().getName());
            txtLocation.setText(dataUser.getData().getLocation());
            GlideLoaderUtil.LoadCircleImage(this, Utils.checkUrl(dataUser.getData().getAvatar()), imgAvatar);
            setBitmap(Api.SHARE_LOOK_DOMAIN + "/users/" + dataUser.getData().getId());
        } else {
            ArmsUtils.snackbarText("请先登录");
            Intent intent = new Intent();
            intent.setClass(getApplication(), LoginActivity.class);
            startActivity(intent);
        }
    }

    private void setBitmap(String contentEtString) {
        if (TextUtils.isEmpty(contentEtString)) {
            ArmsUtils.snackbarText("请输入要生成二维码图片");
            return;
        }
        bitmap = CodeCreator.createQRCode(contentEtString, 400, 400, null);
        if (bitmap != null) {
            imgQrcode.setImageBitmap(bitmap);
        }
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
