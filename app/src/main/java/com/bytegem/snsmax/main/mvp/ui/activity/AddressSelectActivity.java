package com.bytegem.snsmax.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.bytegem.snsmax.main.di.component.DaggerAddressSelectComponent;
import com.bytegem.snsmax.main.mvp.contract.AddressSelectContract;
import com.bytegem.snsmax.main.mvp.presenter.AddressSelectPresenter;
import com.bytegem.snsmax.main.mvp.ui.adapter.AddressAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.liaoinstan.springview.widget.SpringView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/12/2019 17:48
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class AddressSelectActivity extends BaseActivity<AddressSelectPresenter> implements AddressSelectContract.View {
    @BindView(R.id.address_select_search_key)
    EditText key;
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    @Inject
    AddressAdapter adapter;
    @BindView(R.id.springview)
    SpringView springView;

    @OnEditorAction(R.id.address_select_search_key)
    boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {//搜索按键action
            Utils.hideKeyboard(AddressSelectActivity.this);
            mPresenter.search(textView.getText().toString());
            return true;
        }
        return false;
    }

    @OnClick({R.id.cancel})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                killMyself();
                break;
        }
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAddressSelectComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_address_select; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));// 布局管理器
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadmore() {
                mPresenter.search(true);
            }
        });
        adapter.setOnItemClickListener(mPresenter);
        mPresenter.search(false);
    }

    @Override
    public SpringView.DragHander getRefreshHeaderView() {
        return null;
    }

    MaterialDialog materialDialog;

    @Override
    public void showLoading() {
        hideLoading();
        materialDialog = getMaterialDialog("", "").show();
    }

    @Override
    public void hideLoading() {
        if (materialDialog != null && materialDialog.isShowing()) materialDialog.dismiss();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void onFinishFreshAndLoad() {
        springView.onFinishFreshAndLoad();
    }
}
