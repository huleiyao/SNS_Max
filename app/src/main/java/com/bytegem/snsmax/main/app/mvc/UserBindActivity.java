package com.bytegem.snsmax.main.app.mvc;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.config.UserService;
import com.bytegem.snsmax.main.app.mvc.utils.NumberUtil;
import com.bytegem.snsmax.main.app.utils.HttpMvcHelper;
import com.bytegem.snsmax.main.mvp.model.UserBindModel;
import com.bytegem.snsmax.main.mvp.ui.activity.LoginActivity;
import com.bytegem.snsmax.main.mvp.ui.base.BaseActivity;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UserBindActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.title_title)
    TextView barTitle;
    @BindView(R.id.title_back)
    RelativeLayout barBack;
    @BindView(R.id.txt_phone)
    TextView txtPhone;
    @BindView(R.id.txt_wechat)
    TextView txtWechat;
    @BindView(R.id.btn_phone)
    TextView btnPhone;
    @BindView(R.id.btn_wechat)
    TextView btnWechat;
    private Intent intent = new Intent();
    private Context context;
    UserBindModel userBindModel;


    @Override
    public int getLayoutId() {
        context = this;
        return R.layout.activity_user_bind;
    }


    @Override
    public void initView() {
        barTitle.setText("账号与绑定");
        getType();
    }

    @Override
    public void setListener() {
        barBack.setOnClickListener(this);
        btnPhone.setOnClickListener(this);
        btnWechat.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.btn_phone:
                if (btnPhone.getText().equals("绑定手机号")) {
                    intent.setClass(context, BindPhoneNumber.class);
                    startActivity(intent);
                } else {
                    intent.setClass(context, UpdatePhoneNumber.class);
                    intent.putExtra("phone", userBindModel.getPhone().getNumber());
                    startActivity(intent);
                }
                break;
            case R.id.btn_wechat:
                break;
            default:
                break;
        }
    }

    private void getType() {
        HttpMvcHelper
                .obtainRetrofitService(UserService.class)
                .getUserBind(HttpMvcHelper.getTokenOrType())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(suc -> {
                    judgePhoneWechat(suc);
                }, err -> {
                    ArmsUtils.snackbarText("请先登录");
                    intent.setClass(context, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                });
    }

    private void judgePhoneWechat(UserBindModel userBindModel) {
        if (userBindModel != null) {
            this.userBindModel = userBindModel;
            if (userBindModel.getPhone().isHas_bind()) {
                txtPhone.setText(NumberUtil.settingPhone(userBindModel.getPhone().getNumber()));
                btnPhone.setText("修改手机号");
            } else {
                txtPhone.setText("");
                btnPhone.setText("绑定手机号");
            }
            if (userBindModel.getWx().isHas_bind()) {
                txtWechat.setText(userBindModel.getWx().getNumber());
                btnWechat.setText("解除绑定");
            } else {
                txtWechat.setText("");
                btnWechat.setText("绑定微信");
            }
        }
    }

}
