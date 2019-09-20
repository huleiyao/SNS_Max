package com.bytegem.snsmax.main.app.mvc;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.common.utils.M;
import com.bytegem.snsmax.main.app.bean.NetDefaultBean;
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

public class UpdatePhoneNumber extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.title_back)
    RelativeLayout barBack;
    @BindView(R.id.title_title)
    TextView barTitle;
    @BindView(R.id.update_phone_code)
    TextView btnCode;
    @BindView(R.id.txt_history_phone)
    TextView txtPhone;
    @BindView(R.id.update_phone_number)
    TextView btnPhone;
    @BindView(R.id.edt_phone_code)
    EditText editCode;
    Intent intent;
    private String strPhoneNum;
    TimeCount timeCount = new TimeCount(60000, 100);

    @Override
    public int getLayoutId() {
        return R.layout.activity_update_phone;
    }

    @Override
    public void initView() {
        barTitle.setText("修改手机");
        intent = getIntent();
        strPhoneNum = intent.getStringExtra("phone");
        txtPhone.setText(NumberUtil.settingPhone(strPhoneNum));
    }

    @Override
    public void setListener() {
        barBack.setOnClickListener(this);
        btnCode.setOnClickListener(this);
        btnPhone.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.update_phone_number:
                //跳转修改手机号页面
                if (editCode.getText().length() > 0) {
                    intent.setClass(this, UpdatePhoneNumberDetail.class);
                    intent.putExtra("historyCode", editCode.getText());
                    startActivity(intent);
                }
                editCode.setText("");
                timeCount.onFinish();
                break;
            case R.id.update_phone_code:
                timeCount.start();
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
