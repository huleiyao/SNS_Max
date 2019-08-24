package com.bytegem.snsmax.main.app.mvc;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.mvp.ui.base.BaseActivity;

public class UpdatePhoneNumber extends BaseActivity implements View.OnClickListener {

    private TextView barTitle;
    private RelativeLayout barBack;

    @Override
    public int getLayoutId() {
        return R.layout.activity_update_phone;
    }

    @Override
    public void initView() {
        barBack = findViewById(R.id.title_back);
        barTitle = findViewById(R.id.title_title);
        barTitle.setText("修改手机");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            default:
                break;
        }
    }
}
