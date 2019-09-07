package com.bytegem.snsmax.main.mvp.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.config.UserService;
import com.bytegem.snsmax.main.app.utils.HttpMvcHelper;
import com.bytegem.snsmax.main.mvp.ui.adapter.HelperAdapter;
import com.bytegem.snsmax.main.mvp.ui.base.BaseActivity;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class proposalDetailActivity extends BaseActivity implements View.OnClickListener {

    private int id;
    @BindView(R.id.title_back)
    RelativeLayout btnBack;
    @BindView(R.id.title_proposal_detail)
    TextView txtDetailTitle;
    @BindView(R.id.txt_proposal_detail)
    TextView txtDetail;
    @BindView(R.id.btn_proposal_detail)
    LinearLayout btnProposal;

    @Override
    public int getLayoutId() {
        return R.layout.activity_proposal_detail;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        id = Integer.parseInt(intent.getStringExtra("proposal_id"));
        getProposalDetails();
    }

    private void getProposalDetails() {
        HttpMvcHelper
                .obtainRetrofitService(UserService.class)
                .getHelperDetails(HttpMvcHelper.getTokenOrType(), id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(suc -> {
                    Object c = suc;
                    txtDetailTitle.setText(suc.getData().getTitle());
                    txtDetail.setText(suc.getData().getBody());
                }, err -> {
                    Object d = err;
                });
    }

    @Override
    public void setListener() {
        btnBack.setOnClickListener(this);
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
}
