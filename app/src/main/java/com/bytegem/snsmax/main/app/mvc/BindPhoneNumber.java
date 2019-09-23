package com.bytegem.snsmax.main.app.mvc;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.common.utils.M;
import com.bytegem.snsmax.main.app.MApplication;
import com.bytegem.snsmax.main.app.config.UserService;
import com.bytegem.snsmax.main.app.mvc.utils.NumberUtil;
import com.bytegem.snsmax.main.app.utils.HttpMvcHelper;
import com.bytegem.snsmax.main.mvp.ui.base.BaseActivity;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class BindPhoneNumber extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.title_title)
    TextView barTitle;
    @BindView(R.id.title_back)
    RelativeLayout barBack;
    @BindView(R.id.edt_bind_phone)
    EditText edtPhone;
    @BindView(R.id.edt_bind_code)
    EditText edtCode;
    @BindView(R.id.update_bind_code)
    TextView btnCode;
    @BindView(R.id.update_bind_number)
    TextView btnBind;
    private String strPhoneNum;
    TimeCount timeCount = new TimeCount(60000, 100);

    @Override
    public int getLayoutId() {
        return R.layout.activity_bind_phone;
    }

    @Override
    public void initView() {
        barTitle.setText("绑定手机号");
    }

    @Override
    public void setListener() {
        barBack.setOnClickListener(this);
        btnBind.setOnClickListener(this);
        btnCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                break;
            case R.id.update_bind_code:
                timeCount.start();
                strPhoneNum = edtPhone.getText().toString();
                if (NumberUtil.isMobileNO(strPhoneNum)) {
                    HttpMvcHelper
                            .obtainRetrofitService(UserService.class)
                            .getCode(RequestBody.create(
                                    MediaType.parse("application/json; charset=utf-8"),
                                    M.getMapString(
                                            "phone_number"
                                            , strPhoneNum
                                    )))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(suc -> {
                                ArmsUtils.snackbarText("发送成功");
                            }, err -> {
                                ArmsUtils.snackbarText("发送失败");
                            });
                } else {
                    ArmsUtils.snackbarText("请输入合法手机号");
                }
                break;
            case R.id.update_bind_number:
                if (NumberUtil.isMobileNO(strPhoneNum)) {
                    HttpMvcHelper.obtainRetrofitService(UserService.class)
                            .bindPhone(MApplication.getTokenOrType(), RequestBody.create(
                                    MediaType.parse("application/json; charset=utf-8"),
                                    M.getMapString("phone_number", strPhoneNum, "code", edtCode.getText().toString())))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(suc -> {
                            }, err -> {
                                if (err.toString().contains("Null is not a valid element")) {
                                    ArmsUtils.snackbarText("绑定成功");
                                } else {
                                    ArmsUtils.snackbarText("绑定失败");
                                }
                            });
                } else {
                    ArmsUtils.snackbarText("请输入合法手机号");
                }
                break;
            default:
                break;
        }
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btnCode.setClickable(false);
            btnCode.setText("(" + millisUntilFinished / 1000 + ") 秒后可重新发送");
        }

        @Override
        public void onFinish() {
            btnCode.setText("重新获取验证码");
            btnCode.setClickable(true);
        }
    }

}
