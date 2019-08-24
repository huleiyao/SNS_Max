package com.bytegem.snsmax.main.app.mvc;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.mvp.ui.base.BaseActivity;

public class UserCircleActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private RelativeLayout btnBack;
    private TextView txtTitle;
    private ListView listView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_circle;
    }

    @Override
    public void initView() {
        btnBack = findViewById(R.id.title_back);
        txtTitle = findViewById(R.id.title_title);
        listView = findViewById(R.id.user_circle_list);
        txtTitle.setText("我的圈子");
        setListener();
    }


    private void setListener() {
        btnBack.setOnClickListener(this);
        listView.setOnItemClickListener(this);
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
