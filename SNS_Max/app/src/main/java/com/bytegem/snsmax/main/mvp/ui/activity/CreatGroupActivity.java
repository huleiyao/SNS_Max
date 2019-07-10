package com.bytegem.snsmax.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bytegem.snsmax.common.View.SwitchButton;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.bytegem.snsmax.main.di.component.DaggerCreatGroupComponent;
import com.bytegem.snsmax.main.mvp.contract.CreatGroupContract;
import com.bytegem.snsmax.main.mvp.presenter.CreatGroupPresenter;

import com.bytegem.snsmax.R;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;


import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/01/2019 11:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class CreatGroupActivity extends BaseActivity<CreatGroupPresenter> implements CreatGroupContract.View {

    @BindView(R.id.group_cover)
    ImageView group_cover;
    @BindView(R.id.group_name)
    EditText group_name;
    @BindView(R.id.group_detail)
    EditText group_detail;
    @BindView(R.id.switch_button)
    SwitchButton switch_button;
    @BindView(R.id.creat_group)
    Button creat_group;
    public static final int SELECT_IMAGE = 802;
    MaterialDialog materialDialog;
    boolean isHaveCover = false;

    @OnClick({R.id.uploading_header, R.id.creat_group})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.uploading_header:
                //选图片并上传
                startActivityForResult(new Intent(this, ImageGridActivity.class), SELECT_IMAGE);
                break;
            case R.id.creat_group:
                String name = group_name.getText().toString();
                String detail = group_detail.getText().toString();
                if (isHaveCover || name.isEmpty() || detail.isEmpty())
                    return;
                mPresenter.createGroup(name, detail, switch_button.getCurrstate() == SwitchButton.CLOSE ? 0 : 1);
                break;
        }
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCreatGroupComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_creat_group; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("创建圈子");
        ChangeListener changeListener = new ChangeListener();
        group_name.addTextChangedListener(changeListener);
        group_detail.addTextChangedListener(changeListener);
    }

    class ChangeListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            checkCreate();
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    private void checkCreate() {
        if (isHaveCover && !group_name.getText().toString().isEmpty() && !group_detail.getText().toString().isEmpty()) {
            creat_group.setBackground(getResources().getDrawable(R.drawable.shape_group_create_bg));
            creat_group.setTextColor(getResources().getColor(R.color.white));
            creat_group.setTextColor(getResources().getColor(R.color.create_group_txt_unclickable));
        } else {
            creat_group.setBackground(getResources().getDrawable(R.drawable.shape_group_not_create_bg));
            creat_group.setTextColor(getResources().getColor(R.color.create_group_txt_unclickable));
        }
    }

    @Override
    public void showLoading() {
        hideLoading();
        materialDialog = new MaterialDialog.Builder(this)
//                .title("正在上传图片")
                .content("上传图片中···")
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .canceledOnTouchOutside(false)
                .show();
    }

    @Override
    public void hideLoading() {
        if (materialDialog != null && materialDialog.isShowing()) materialDialog.dismiss();
    }

    ArrayList<ImageItem> imageItems;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE)
            if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
                imageItems = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (imageItems != null && imageItems.size() > 0) {
                    mPresenter.getSign(imageItems.get(0));
                }
            }
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

    @Override
    public void showGroupCover(String url) {
        GlideLoaderUtil.LoadRoundImage20(this, Utils.checkUrl(url), group_cover);
        isHaveCover = true;
        checkCreate();
    }
}
