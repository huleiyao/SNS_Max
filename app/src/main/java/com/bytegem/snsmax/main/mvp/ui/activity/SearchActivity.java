package com.bytegem.snsmax.main.mvp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.common.adapter.VPFragmentAdapter;
import com.bytegem.snsmax.common.bean.FragmentBean;
import com.bytegem.snsmax.main.app.MApplication;
import com.bytegem.snsmax.main.mvp.ui.fragment.SearchCircelFragment;
import com.bytegem.snsmax.main.mvp.ui.fragment.SearchDiscussFragment;
import com.bytegem.snsmax.main.mvp.ui.fragment.SearchDynamicFragment;
import com.bytegem.snsmax.main.mvp.ui.fragment.SearchUserFragment;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.mvp.IView;
import com.jess.arms.utils.ArmsUtils;

import com.bytegem.snsmax.main.di.component.DaggerSearchActivityComponent;
import com.bytegem.snsmax.main.mvp.contract.SearchActivityContract;
import com.bytegem.snsmax.main.mvp.presenter.SearchActivityPresenter;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/29/2019 10:37
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SearchActivity extends BaseActivity<SearchActivityPresenter> implements SearchActivityContract.View {

    //进入当前页面的类型
    private static final String ENTER_TYPE = "enter_type";

    /**
     * 跳转到搜索页面。
     *
     * @param activity
     * @param type
     */
    public static void goToSearch(IView activity, SearchActivityPresenter.SearchType type) {
        activity.launchActivity(new Intent(MApplication.getInstance(), SearchActivity.class).putExtra(ENTER_TYPE, type.type));
    }

    @BindView(R.id.search_pager)
    ViewPager vPager;
    @BindView(R.id.search_tabs)
    SmartTabLayout tabs;
    @BindView(R.id.search_query_text)
    EditText queryText;
    @BindView(R.id.search_history_layout)
    ConstraintLayout searchHistory;

    //初始化进入的类型,默认是 "用户" 类型
    private int enterType;
    int defaultPosition = 0;
    ArrayList<FragmentBean> fragmentList = new ArrayList<>();

    @OnClick({R.id.search_query_cancel})
    void click(View view) {
        switch (view.getId()) {
            case R.id.search_query_cancel: {
                cancelQuery();
            }
        }
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSearchActivityComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_search; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        enterType = getIntent().getIntExtra(ENTER_TYPE, SearchActivityPresenter.SearchType.user.type);
        defaultPosition = enterType;
        buildTabsData();
        queryText.setOnClickListener(v->{
            searchHistory.setVisibility(View.VISIBLE);
        });
        queryText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                //搜索,通知當前選中的Fragment进行搜索
                for (FragmentBean fragmentBean : fragmentList) {
                    ((BaseFragment)fragmentBean.getFragment()).setData(this);
                }
                searchHistory.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                return true;
            }
            return false;
        });
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(searchHistory.getVisibility() == View.VISIBLE){
                searchHistory.setVisibility(View.GONE);
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if(searchHistory.getVisibility() == View.VISIBLE){
            searchHistory.setVisibility(View.GONE);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public String getQueryKey() {
        return queryText.getText() == null ? "" : queryText.getText().toString();
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

    //构建Tabs数据和对应的Fragment
    private void buildTabsData() {
        fragmentList.add(new FragmentBean("用户", SearchUserFragment.newInstance()));
        fragmentList.add(new FragmentBean("动态", SearchDynamicFragment.newInstance()));
        fragmentList.add(new FragmentBean("圈子", SearchCircelFragment.newInstance()));
        fragmentList.add(new FragmentBean("讨论", SearchDiscussFragment.newInstance()));
        vPager.setAdapter(new VPFragmentAdapter(getSupportFragmentManager(), fragmentList));
        vPager.setOffscreenPageLimit(fragmentList.size() - 1);
//        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
//        tabs.setupWithViewPager(viewPager);
        tabs.setViewPager(vPager);
        try {
            changeTextSize(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                if (i == defaultPosition) return;
                changeTextSize(false);
                defaultPosition = i;
                changeTextSize(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        vPager.setCurrentItem(defaultPosition, false);
    }

    //改变字体大小
    private void changeTextSize(boolean isSelect) {
        View view = tabs.getTabAt(defaultPosition).findViewById(R.id.custom_text);
        if (view != null)
            if (view instanceof TextView) {
                ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(isSelect ? R.dimen.sp_34 : R.dimen.sp_34));
            }
    }

    //取消搜索
    private void cancelQuery() {
        if(searchHistory.getVisibility() == View.VISIBLE){
            searchHistory.setVisibility(View.GONE);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
            return;
        }
        queryText.setText(""); //清空搜索内容
        queryText.clearFocus();
        queryText.setCursorVisible(false);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        finish();
    }
}
