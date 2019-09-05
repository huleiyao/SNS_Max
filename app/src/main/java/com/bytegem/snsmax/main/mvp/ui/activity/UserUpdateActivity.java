package com.bytegem.snsmax.main.mvp.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.common.utils.M;
import com.bytegem.snsmax.main.app.MApplication;
import com.bytegem.snsmax.main.app.bean.user.DATAUser;
import com.bytegem.snsmax.main.app.config.UserService;
import com.bytegem.snsmax.main.app.utils.HttpMvcHelper;
import com.bytegem.snsmax.main.app.utils.UserInfoUtils;
import com.bytegem.snsmax.main.mvp.ui.base.BaseActivity;
import com.google.gson.Gson;
import com.jess.arms.utils.ArmsUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class UserUpdateActivity extends BaseActivity implements View.OnClickListener {

    private DATAUser userInfo = UserInfoUtils.getUserInfo(HttpMvcHelper.getGson());
    private RelativeLayout btnBack;
    private TextView txtTitle;
    private Button btnUpdate;
    private EditText edtUpdate;
    private Intent intent = new Intent();
    private String strType = "";

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_update;
    }

    @Override
    public void initView() {
        intent = getIntent();
        btnBack = findViewById(R.id.title_back);
        txtTitle = findViewById(R.id.title_title);
        strType = intent.getStringExtra("strType");
        setTxtTitle(strType);
        btnUpdate = findViewById(R.id.btn_user_update);
        edtUpdate = findViewById(R.id.edt_user_update);
        setListener();
    }

    private void setTxtTitle(String strType) {
        if (strType.equals("学院编辑") || strType.equals("简介编辑") || strType.equals("行业编辑")) {
            txtTitle.setText(strType);
        }
    }

    @Override
    public void setListener() {
        btnBack.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.btn_user_update:
                //提交当前修改的数据
                edtUpdate.getText();
                switch (strType) {
                    case "学院编辑":
                        updateType("school");
                        break;
                    case "简介编辑":
                        updateType("bio");
                        break;
                    case "行业编辑":
                        updateType("trade");
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }


    private void updateType(String strType) {
        HttpMvcHelper.obtainRetrofitService(UserService.class)
                .updateUserData(MApplication.getTokenOrType(), RequestBody.create(
                        MediaType.parse("application/json; charset=utf-8"),
                        M.getMapString(strType, edtUpdate.getText().toString())))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(suc -> {

                }, err -> {
                    if (err.toString().contains("Null is not a valid element")) {
                        ArmsUtils.snackbarText("保存成功");
                        switch (strType) {
                            case "school":
                                userInfo.getData().setSchool(edtUpdate.getText().toString());
                                break;
                            case "bio":
                                userInfo.getData().setBio(edtUpdate.getText().toString());
                                break;
                            case "trade":
                                userInfo.getData().setTrade(edtUpdate.getText().toString());
                                break;
                            default:
                                break;
                        }
                        finish();
                    }
                });
    }

}
