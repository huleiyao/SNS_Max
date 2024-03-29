package com.bytegem.snsmax.main.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bytegem.snsmax.common.adapter.VPFragmentAdapter;
import com.bytegem.snsmax.common.bean.FragmentBean;
import com.bytegem.snsmax.main.mvp.presenter.SearchActivityPresenter;
import com.bytegem.snsmax.main.mvp.ui.activity.SearchActivity;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.mvp.IView;
import com.jess.arms.utils.ArmsUtils;

import com.bytegem.snsmax.main.di.component.DaggerHomeFindComponent;
import com.bytegem.snsmax.main.mvp.contract.HomeFindContract;
import com.bytegem.snsmax.main.mvp.presenter.HomeFindPresenter;

import com.bytegem.snsmax.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/05/2019 16:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class HomeFindFragment extends BaseFragment<HomeFindPresenter> implements HomeFindContract.View {
    @BindView(R.id.tabs)
    SmartTabLayout tabs;
    @BindView(R.id.projectPager)
    ViewPager viewPager;
    @BindView(R.id.home_search)
    ImageView homeSearch;
    private static HomeFindFragment instance;

    public static HomeFindFragment newInstance() {
        if (instance == null)
            instance = new HomeFindFragment();
        return instance;
    }

    @OnClick({R.id.home_search})
    void click(View v){
        switch (v.getId()){
            case R.id.home_search:{
                //跳转到动态搜索
                SearchActivity.goToSearch((IView) getActivity(),SearchActivityPresenter.SearchType.circle);
                break;
            }
        }
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHomeFindComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_find, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ArrayList<FragmentBean> fragmentList = new ArrayList<>();
        fragmentList.add(new FragmentBean("圈子", GroupsFragment.newInstance()));
        fragmentList.add(new FragmentBean("热议", GroupHotMessageFragment.newInstance()));
        showFragment(fragmentList);
    }

    public void showFragment(ArrayList<FragmentBean> fragmenList) {
        viewPager.setAdapter(new VPFragmentAdapter(getChildFragmentManager(), fragmenList));
        viewPager.setOffscreenPageLimit(fragmenList.size() - 1);
//        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
//        tabs.setupWithViewPager(viewPager);
        tabs.setViewPager(viewPager);
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
    }

    int defaultPosition = 0;

    private void changeTextSize(boolean isSelect) {
        View view = tabs.getTabAt(defaultPosition).findViewById(R.id.custom_text);
        if (view != null)
            if (view instanceof TextView) {
                ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(isSelect ? R.dimen.sp_40 : R.dimen.sp_34));
            }
    }

    /**
     * 通过此方法可以使 Fragment 能够与外界做一些交互和通信, 比如说外部的 Activity 想让自己持有的某个 Fragment 对象执行一些方法,
     * 建议在有多个需要与外界交互的方法时, 统一传 {@link Message}, 通过 what 字段来区分不同的方法, 在 {@link #setData(Object)}
     * 方法中就可以 {@code switch} 做不同的操作, 这样就可以用统一的入口方法做多个不同的操作, 可以起到分发的作用
     * <p>
     * 调用此方法时请注意调用时 Fragment 的生命周期, 如果调用 {@link #setData(Object)} 方法时 {@link Fragment#onCreate(Bundle)} 还没执行
     * 但在 {@link #setData(Object)} 里却调用了 Presenter 的方法, 是会报空的, 因为 Dagger 注入是在 {@link Fragment#onCreate(Bundle)} 方法中执行的
     * 然后才创建的 Presenter, 如果要做一些初始化操作,可以不必让外部调用 {@link #setData(Object)}, 在 {@link #initData(Bundle)} 中初始化就可以了
     * <p>
     * Example usage:
     * <pre>
     * public void setData(@Nullable Object data) {
     *     if (data != null && data instanceof Message) {
     *         switch (((Message) data).what) {
     *             case 0:
     *                 loadData(((Message) data).arg1);
     *                 break;
     *             case 1:
     *                 refreshUI();
     *                 break;
     *             default:
     *                 //do something
     *                 break;
     *         }
     *     }
     * }
     *
     * // call setData(Object):
     * Message data = new Message();
     * data.what = 0;
     * data.arg1 = 1;
     * fragment.setData(data);
     * </pre>
     *
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
    @Override
    public void setData(@Nullable Object data) {

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

    }
}
