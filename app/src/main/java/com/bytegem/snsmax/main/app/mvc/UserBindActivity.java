package com.bytegem.snsmax.main.app.mvc;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.mvp.ui.base.BaseActivity;

public class UserBindActivity extends BaseActivity implements View.OnClickListener {
    private TextView barTitle, btnUpdatePhone, btnWechatBind;
    private RelativeLayout barBack;
    private Intent intent = new Intent();
    private Context context;

    @Override
    public int getLayoutId() {
        context = this;
        return R.layout.activity_user_bind;
    }

    @Override
    public void initView() {
        barBack = findViewById(R.id.title_back);
        barTitle = findViewById(R.id.title_title);
        barTitle.setText("账号与绑定");
        btnUpdatePhone = findViewById(R.id.update_phone);
        btnWechatBind = findViewById(R.id.wechat_bind);
    }

    @Override
    public void setListener() {
        barBack.setOnClickListener(this);
        btnUpdatePhone.setOnClickListener(this);
        btnWechatBind.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.update_phone:
                intent.setClass(context, UpdatePhoneNumber.class);
                startActivity(intent);
                break;
            case R.id.wechat_bind:
                break;
            default:
                break;
        }
    }
}
