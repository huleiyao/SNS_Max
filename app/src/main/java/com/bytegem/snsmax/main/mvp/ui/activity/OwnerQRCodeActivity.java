package com.bytegem.snsmax.main.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bytegem.snsmax.zxing.encode.CodeCreator;
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

    private ImageView imgQrcode;
    Bitmap bitmap = null;

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
        imgQrcode = findViewById(R.id.qrcode);
        setBitmap("https://byte-gem.com/users/1");
    }

    private void setBitmap(String contentEtString){
        if (TextUtils.isEmpty(contentEtString)){
            Toast.makeText(this, "请输入要生成二维码图片的字符串", Toast.LENGTH_SHORT).show();
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
