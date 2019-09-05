package com.bytegem.snsmax.main.app.mvc;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.common.utils.M;
import com.bytegem.snsmax.main.app.bean.NetDefaultBean;
import com.bytegem.snsmax.main.app.config.UserService;
import com.bytegem.snsmax.main.app.utils.HttpMvcHelper;
import com.bytegem.snsmax.main.mvp.ui.base.BaseActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class UpdatePhoneNumber extends BaseActivity implements View.OnClickListener {

    private TextView barTitle;
    private RelativeLayout barBack;
    private TextView btnCode;

    @Override
    public int getLayoutId() {
        return R.layout.activity_update_phone;
    }

    @Override
    public void initView() {
        barBack = findViewById(R.id.title_back);
        barTitle = findViewById(R.id.title_title);
        btnCode = findViewById(R.id.update_phone_code);
        barTitle.setText("修改手机");
        setListener();
    }

    @Override
    public void setListener() {
        barBack.setOnClickListener(this);
        btnCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.update_phone_code:
                HttpMvcHelper
                        .obtainRetrofitService(UserService.class)
                        .getCode(RequestBody.create(
                                MediaType.parse("application/json; charset=utf-8"),
                                M.getMapString(
                                        "phone_number"
                                        , "15882633651"
                                )))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(suc -> {
                            NetDefaultBean a = suc;
                        }, err -> {

                        });
                break;
            default:
                break;
        }
    }
}
