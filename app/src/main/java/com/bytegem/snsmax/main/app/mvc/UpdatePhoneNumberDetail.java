package com.bytegem.snsmax.main.app.mvc;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.common.utils.M;
import com.bytegem.snsmax.main.app.bean.usertype.UserTypeDetailBean;
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

public class UpdatePhoneNumberDetail extends BaseActivity implements View.OnClickListener {

    Intent intent;
    String strHistoryCode;
    @BindView(R.id.title_back)
    RelativeLayout barBack;
    @BindView(R.id.title_title)
    TextView barTitle;
    @BindView(R.id.edt_new_phone)
    EditText edtNewPhone;
    @BindView(R.id.edt_new_code)
    EditText edtNewCode;
    @BindView(R.id.update_new_code)
    TextView btnNewCode;
    @BindView(R.id.update_new_number)
    TextView btnNewPhone;

    @Override
    public int getLayoutId() {
        return R.layout.activity_update_phone_detail;
    }

    @Override
    public void initView() {
        intent = getIntent();
        strHistoryCode = intent.getStringExtra("historyCode");
        barTitle.setText("绑定手机号");
    }

    @Override
    public void setListener() {
        barBack.setOnClickListener(this);
        btnNewCode.setOnClickListener(this);
        btnNewPhone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.update_new_code:
                if (NumberUtil.isMobileNO(edtNewPhone.getText().toString())) {
                    HttpMvcHelper
                            .obtainRetrofitService(UserService.class)
                            .getCode(RequestBody.create(
                                    MediaType.parse("application/json; charset=utf-8"),
                                    M.getMapString(
                                            "phone_number"
                                            , edtNewPhone.getText().toString()
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
            case R.id.update_new_number:
                //提交新手机号以及双验证码
                if (edtNewCode.getText().length()>0){
//                    HttpMvcHelper
//                            .obtainCacheService(UserService.class)
//                            .updatePhone(RequestBody.create(
//                                    MediaType.parse("application/json; charset=utf-8"),
//                                    M.getMapString("code", strHistoryCode,
//                                            "new", ""
//                                    )))
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe(suc -> {
//                                ArmsUtils.snackbarText("发送成功");
//                            }, err -> {
//                                ArmsUtils.snackbarText("发送失败");
//                            });
                }
                break;
            default:
                break;
        }
    }
}
