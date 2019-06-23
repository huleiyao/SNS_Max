package com.bytegem.snsmax.main.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.bytegem.snsmax.main.di.component.DaggerRegisterComponent;
import com.bytegem.snsmax.main.mvp.contract.RegisterContract;
import com.bytegem.snsmax.main.mvp.presenter.RegisterPresenter;

import com.bytegem.snsmax.R;


import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/18/2019 16:33
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {
    @BindView(R.id.error)
    TextView error;
    @BindView(R.id.get_code)
    TextView get_code;
    @BindView(R.id.phone_number)
    EditText phone_number;
    @BindView(R.id.code)
    EditText code;
    boolean isStartTimer = false;

    @OnClick({R.id.to_login, R.id.get_code, R.id.next, R.id.wechat_login, R.id.agreement})
    void onClick(View view) {
        String phone_ = phone_number.getText().toString();
        String code_ = code.getText().toString();
        switch (view.getId()) {
            case R.id.to_login:
                killMyself();
                break;
            case R.id.get_code:
                if (phone_ != null && phone_.isEmpty()) {
                    error.setText("请输入手机号码");
                    return;
                }
                if (isStartTimer) return;//开始计时了  说明不能点击获取验证码
                changeGetCodeView(true);
                mPresenter.getCode(phone_);
                break;
            case R.id.next:
                if (phone_ != null && phone_.isEmpty()) {
                    error.setText("请输入手机号码");
                    return;
                }
                if (code_ != null && code_.isEmpty()) {
                    error.setText("请输入验证码");
                    return;
                }
                mPresenter.register(phone_, code_);
                break;
            case R.id.wechat_login:

                break;
            case R.id.agreement:

                break;
        }
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRegisterComponent //如找不到该类,请编译一下项目
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
            if (msg.what > 0) {
                isStartTimer = true;
                get_code.setText("已发送(" + msg.what + ")");
                handler.sendEmptyMessageDelayed(msg.what - 1, 1000);
            } else changeGetCodeView(false);
        }
    };

    public void changeGetCodeView(boolean isOpenGetCode) {
        if (isOpenGetCode) {
            get_code.setText("获取验证码");
            get_code.setTextColor(getResources().getColor(R.color.color_151b26));
            get_code.setClickable(false);
        } else {
            get_code.setText("重新获取验证码");
            get_code.setTextColor(getResources().getColor(R.color.color_5D68EA));
            get_code.setClickable(true);
            isStartTimer = false;
        }
    }

    @Override
    public void toHome() {
        launchActivity(new Intent(this, HomeActivity.class));
        killMyself();
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_register; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
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
