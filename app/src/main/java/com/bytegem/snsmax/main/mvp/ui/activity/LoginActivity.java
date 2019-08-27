package com.bytegem.snsmax.main.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bytegem.snsmax.main.app.MApplication;
import com.bytegem.snsmax.main.mvp.ui.dialog.LoadingDialog;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.bytegem.snsmax.main.di.component.DaggerLoginComponent;
import com.bytegem.snsmax.main.mvp.contract.LoginContract;
import com.bytegem.snsmax.main.mvp.presenter.LoginPresenter;

import com.bytegem.snsmax.R;


import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/18/2019 09:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {
    @BindView(R.id.login_error)
    TextView login_error;
    @BindView(R.id.login_get_code)
    TextView login_get_code;
    @BindView(R.id.login_phone_number)
    EditText login_phone_number;
    @BindView(R.id.login_code)
    EditText login_code;
    boolean isStartTimer = false;

    @OnClick({R.id.to_register, R.id.login_get_code, R.id.login, R.id.login_wechat_login})
    void onClick(View view) {
        String phone_ = login_phone_number.getText().toString();
        String code_ = login_code.getText().toString();
        switch (view.getId()) {
            case R.id.to_register:
                launchActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.login_get_code:
                if (phone_ != null && phone_.isEmpty()) {
                    login_error.setText("请输入手机号码");
                    return;
                }
                if (isStartTimer) return;//开始计时了  说明不能点击获取验证码
                changeGetCodeView(true);
                mPresenter.getCode(phone_);
                break;
            case R.id.login:
                LoadingDialog dialog = new LoadingDialog(this, "正在加载...");
                dialog.show();
                if (phone_ != null && phone_.isEmpty()) {
                    login_error.setText("请输入手机号码");
                    return;
                }
                if (code_ != null && code_.isEmpty()) {
                    login_error.setText("请输入验证码");
                    return;
                }
                mPresenter.login(phone_, code_);
                break;
            case R.id.login_wechat_login:
                SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                MApplication.token = sharedPreferences.getString("token", null);
                MApplication.token_type = sharedPreferences.getString("token_type", null);
                if (MApplication.token == null || MApplication.token_type == null)
                    showMessage("没登陆过，需要先登录一次");
                else
                    toHome();
                break;
        }
    }

    @Override
    public void toHome() {
        getSharedPreferences("user", Context.MODE_PRIVATE)
                .edit()
                .putString("token", MApplication.token)
                .putString("token_type", MApplication.token_type)
                .commit();
        launchActivity(new Intent(this, HomeActivity.class));
        killMyself();
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (login_get_code == null || handler == null) return;
            if (msg.what > 0) {
                isStartTimer = true;
                login_get_code.setText("已发送(" + msg.what + ")");
                handler.sendEmptyMessageDelayed(msg.what - 1, 1000);
            } else changeGetCodeView(false);
        }
    };

    public void changeGetCodeView(boolean isOpenGetCode) {
        if (isOpenGetCode) {
            login_get_code.setText("获取验证码");
            login_get_code.setTextColor(getResources().getColor(R.color.color_151b26));
            login_get_code.setClickable(false);
        } else {
            login_get_code.setText("重新获取验证码");
            login_get_code.setTextColor(getResources().getColor(R.color.color_5D68EA));
            login_get_code.setClickable(true);
            isStartTimer = false;
        }
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

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
        finish();
    }

    @Override
    public void getCodeSucceed() {
        //计时器，允许输入code
        //开始计时
        if (!isStartTimer)
            handler.sendEmptyMessageDelayed(60, 1000);
    }

    @Override
    public void getCodeFaild() {
        //失败，提示，不可输入code，可以尝试重新获取
        changeGetCodeView(false);
    }
}
