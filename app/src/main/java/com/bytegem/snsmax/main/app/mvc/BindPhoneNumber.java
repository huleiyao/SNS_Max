package com.bytegem.snsmax.main.app.mvc;

import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.mvp.ui.base.BaseActivity;

import butterknife.BindView;

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
    }

    @Override
    public void onClick(View v) {

    }
}
