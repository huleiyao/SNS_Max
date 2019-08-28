package com.bytegem.snsmax.main.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.bean.user.MyCircleDTO;
import com.bytegem.snsmax.main.app.utils.HttpMvcHelper;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.bytegem.snsmax.main.di.component.DaggerMyCircleDetailsActivityComponent;
import com.bytegem.snsmax.main.mvp.contract.MyCircleDetailsActivityContract;
import com.bytegem.snsmax.main.mvp.presenter.MyCircleDetailsActivityPresenter;


import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * 此类已经放弃。因为存在新的详情类:{@link GroupDetailsActivity}
 * <p>
 * Created by MVPArmsTemplate on 08/28/2019 11:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Deprecated
public class MyCircleDetailsActivityActivity extends BaseActivity<MyCircleDetailsActivityPresenter> implements MyCircleDetailsActivityContract.View {

    public static final String ITEM_CLICK_KEY = "item_click";

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyCircleDetailsActivityComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    private MyCircleDTO.MyCircleDataItem currentClickItem;

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_circle_details; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        if (!intentData()) {
            killMyself();
            return;
        }

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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

    //初始化传递数据
    private boolean intentData() {
        String json = getIntent().getStringExtra(ITEM_CLICK_KEY);
        if (json == null) {
            return false;
        }
        currentClickItem = HttpMvcHelper.getGson().fromJson(json, MyCircleDTO.MyCircleDataItem.class);
        return currentClickItem != null;
    }
}
