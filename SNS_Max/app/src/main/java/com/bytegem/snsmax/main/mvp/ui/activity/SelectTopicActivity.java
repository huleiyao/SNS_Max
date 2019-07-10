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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bytegem.snsmax.main.app.bean.topic.TopicBean;
import com.bytegem.snsmax.main.app.utils.Utils;
import com.bytegem.snsmax.main.mvp.ui.adapter.FeedsAdapter;
import com.bytegem.snsmax.main.mvp.ui.adapter.TopicsAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.bytegem.snsmax.main.di.component.DaggerSelectTopicComponent;
import com.bytegem.snsmax.main.mvp.contract.SelectTopicContract;
import com.bytegem.snsmax.main.mvp.presenter.SelectTopicPresenter;

import com.bytegem.snsmax.R;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/09/2019 18:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SelectTopicActivity extends BaseActivity<SelectTopicPresenter> implements SelectTopicContract.View {
    @BindView(R.id.keyword)
    EditText keyword;
    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;
    @BindView(R.id.empty)
    LinearLayout empty;
    @BindView(R.id.topic_name)
    TextView topic_name;
    @Inject
    TopicsAdapter adapter;

    @OnClick({R.id.cancel, R.id.creat_topic})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                killMyself();
                break;
            case R.id.creat_topic:
                //去创建新的话题
                startActivityForResult(new Intent(this, CreateTopicActivity.class), 105);
                break;
        }
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSelectTopicComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_select_topic; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));// 布局管理器
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(mPresenter);
        keyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//搜索按键action
                    Utils.hideKeyboard(SelectTopicActivity.this);
                    search();
                    return true;
                }
                return false;
            }
        });
    }

    public void search() {
        String content = keyword.getText().toString();
        if (content.isEmpty()) ;
        else {
            mPresenter.search(content);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        search();
    }

    @Override
    public void selectTopic(TopicBean topic) {
        setResult(104, new Intent().putExtra("topic", topic));
        killMyself();
    }

    @Override
    public void changeView(boolean isHaveData, String topic) {
        if (isHaveData) {
            empty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            topic_name.setText("#" + topic + "#");
            empty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
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
}
