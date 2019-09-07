package com.bytegem.snsmax.main.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.bean.help.HelperBean;
import com.bytegem.snsmax.main.app.config.UserService;
import com.bytegem.snsmax.main.app.utils.HttpMvcHelper;
import com.bytegem.snsmax.main.mvp.ui.adapter.HelperAdapter;
import com.bytegem.snsmax.main.mvp.ui.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProposalActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Context context;
    private List<HelperBean> helperList;
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
        getHelperDetails();

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

    private void getHelperDetails() {
        HttpMvcHelper
                .obtainRetrofitService(UserService.class)
                .getHelper(HttpMvcHelper.getTokenOrType())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(suc -> {
                    helperList = suc.getData();
                    HelperAdapter adapter = new HelperAdapter(helperList, context);
                    listHelper.setAdapter(adapter);
                }, err -> {
                    Object b = err;
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.setClass(context, proposalDetailActivity.class);
        intent.putExtra("proposal_id", helperList.get(position).getId()+"");
        startActivity(intent);
    }
}
