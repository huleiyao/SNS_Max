package com.bytegem.snsmax.main.mvp.ui.activity;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.mvp.ui.base.BaseActivity;

import butterknife.BindView;

public class ProposalActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Context context;
    @BindView(R.id.title_back)
    RelativeLayout btnBack;
    @BindView(R.id.title_title)
    TextView txtTitle;
    @BindView(R.id.btn_proposal)
    LinearLayout btnProposal;
    @BindView(R.id.list_helper)
    ListView listHelper;

    @Override
    public int getLayoutId() {
        return R.layout.activity_proposal;
    }

    @Override
    public void initView() {
        context = this;
        txtTitle.setText("帮助与反馈");
    }

    @Override
    public void setListener() {
        btnBack.setOnClickListener(this);
        listHelper.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
