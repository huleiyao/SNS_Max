package com.bytemax.snsmax.main.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.bytemax.snsmax.main.mvp.ui.fragment.HomeFragment;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.bytemax.snsmax.main.di.component.DaggerHomeComponent;
import com.bytemax.snsmax.main.mvp.contract.HomeContract;
import com.bytemax.snsmax.main.mvp.presenter.HomePresenter;

import com.bytemax.snsmax.R;
import com.next.easynavigation.constant.Anim;
import com.next.easynavigation.view.EasyNavigationBar;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/03/2019 15:24
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class HomeActivity extends BaseActivity<HomePresenter> implements HomeContract.View {
    @BindView(R.id.navigationBar)
    EasyNavigationBar navigationBar;
    private String[] tabText = {"首页", "发现", "", "消息", "我"};
    //未选中icon
    private int[] normalIcon = {R.drawable.ic_ico_tabbar_homepage, R.drawable.ic_ico_tabbar_discover, R.drawable.ic_ico_tabbar_post, R.drawable.ic_ico_tabbar_massage, R.drawable.ic_ico_tabbar_me};
    //选中时icon
    private int[] selectIcon = {R.drawable.ic_ico_tabbar_homepage_on, R.drawable.ic_ico_tabbar_discover_on, R.drawable.ic_ico_tabbar_post, R.drawable.ic_ico_tabbar_massage_on, R.drawable.ic_ico_tabbar_me};
    private List<Fragment> fragments = new ArrayList<>();

    /*navigationBar.titleItems(tabText)      //必传  Tab文字集合
                .normalIconItems(normalIcon)   //必传  Tab未选中图标集合
                    .selectIconItems(selectIcon)   //必传  Tab选中图标集合
                    .fragmentList(fragments)       //必传  fragment集合
                    .fragmentManager(getSupportFragmentManager())     //必传
                .iconSize(20)     //Tab图标大小
                    .tabTextSize(10)   //Tab文字大小
                    .tabTextTop(2)     //Tab文字距Tab图标的距离
                    .normalTextColor(Color.parseColor("#666666"))   //Tab未选中时字体颜色
                .selectTextColor(Color.parseColor("#333333"))   //Tab选中时字体颜色
                .scaleType(ImageView.ScaleType.CENTER_INSIDE)  //同 ImageView的ScaleType
                    .navigationBackground(Color.parseColor("#80000000"))   //导航栏背景色
                .onTabClickListener(new EasyNavigationBar.OnTabClickListener() {   //Tab点击事件  return true 页面不会切换
            @Override
            public boolean onTabClickEvent(View view, int position) {
                return false;
            }
        })
                .smoothScroll(false)  //点击Tab  Viewpager切换是否有动画
                    .canScroll(false)    //Viewpager能否左右滑动
                    .mode(EasyNavigationBar.MODE_ADD)   //默认MODE_NORMAL 普通模式  //MODE_ADD 带加号模式
                    .anim(Anim.ZoomIn)                //点击Tab时的动画
                    .addIconSize(36)    //中间加号图片的大小
                    .addLayoutHeight(100)   //包含加号的布局高度 背景透明  所以加号看起来突出一块
                    .navigationHeight(40)  //导航栏高度
                    .lineHeight(100)         //分割线高度  默认1px
                    .lineColor(Color.parseColor("#ff0000"))
                .addLayoutRule(EasyNavigationBar.RULE_BOTTOM) //RULE_CENTER 加号居中addLayoutHeight调节位置 EasyNavigationBar.RULE_BOTTOM 加号在导航栏靠下
                    .addLayoutBottom(10)   //加号到底部的距离
                    .hasPadding(true)    //true ViewPager布局在导航栏之上 false有重叠
                    .hintPointLeft(-3)  //调节提示红点的位置hintPointLeft hintPointTop（看文档说明）
                    .hintPointTop(-3)
                    .hintPointSize(6)    //提示红点的大小
                    .msgPointLeft(-10)  //调节数字消息的位置msgPointLeft msgPointTop（看文档说明）
                    .msgPointTop(-10)
                    .msgPointTextSize(9)  //数字消息中字体大小
                    .msgPointSize(18)    //数字消息红色背景的大小
                    .addAlignBottom(true)  //加号是否同Tab文字底部对齐  RULE_BOTTOM时有效；
                    .addTextTopMargin(50)  //加号文字距离加号图片的距离
                    .addTextSize(15)      //加号文字大小
                    .addNormalTextColor(Color.parseColor("#ff0000"))    //加号文字未选中时字体颜色
                .addSelectTextColor(Color.parseColor("#00ff00"))    //加号文字选中时字体颜色
                .build();*/
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_home; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        fragments.add(HomeFragment.newInstance());
        fragments.add(HomeFragment.newInstance());
        fragments.add(HomeFragment.newInstance());
        fragments.add(HomeFragment.newInstance());
        fragments.add(HomeFragment.newInstance());
        navigationBar.titleItems(tabText)      //必传  Tab文字集合
                .normalIconItems(normalIcon)   //必传  Tab未选中图标集合
                .selectIconItems(selectIcon)   //必传  Tab选中图标集合
                .fragmentList(fragments)       //必传  fragment集合
                .fragmentManager(getSupportFragmentManager())     //必传
                .iconSize(44)     //Tab图标大小
                .tabTextSize(20)   //Tab文字大小
                .tabTextTop(4)     //Tab文字距Tab图标的距离
                .normalTextColor(Color.parseColor("#B0B8BF"))   //Tab未选中时字体颜色
                .selectTextColor(Color.parseColor("#151B26"))   //Tab选中时字体颜色
                .scaleType(ImageView.ScaleType.CENTER_INSIDE)  //同 ImageView的ScaleType
                .navigationBackground(Color.parseColor("#ffffff"))   //导航栏背景色
                .onTabClickListener(new EasyNavigationBar.OnTabClickListener() {   //Tab点击事件  return true 页面不会切换
                    @Override
                    public boolean onTabClickEvent(View view, int position) {
                        if (position == 2) {
                            showMessage("+++++++++++++++++++");
                            return true;
                        }
                        return false;
                    }
                })
//                .smoothScroll(false)  //点击Tab  Viewpager切换是否有动画
//                .canScroll(false)    //Viewpager能否左右滑动
                .mode(EasyNavigationBar.MODE_ADD)   //默认MODE_NORMAL 普通模式  //MODE_ADD 带加号模式
                .anim(Anim.ZoomIn)                //点击Tab时的动画
                .addIconSize(70)    //中间加号图片的大小
                .addLayoutHeight(70)   //包含加号的布局高度 背景透明  所以加号看起来突出一块
                .navigationHeight(98)  //导航栏高度
//                .lineHeight(0)         //分割线高度  默认1px
//                .lineColor(Color.parseColor("#ff0000"))
//                .addLayoutRule(EasyNavigationBar.RULE_BOTTOM) //RULE_CENTER 加号居中addLayoutHeight调节位置 EasyNavigationBar.RULE_BOTTOM 加号在导航栏靠下
//                .addLayoutBottom(10)   //加号到底部的距离
//                .hasPadding(true)    //true ViewPager布局在导航栏之上 false有重叠
////                .hintPointLeft(-3)  //调节提示红点的位置hintPointLeft hintPointTop（看文档说明）
////                .hintPointTop(-3)
////                .hintPointSize(6)    //提示红点的大小
////                .msgPointLeft(-10)  //调节数字消息的位置msgPointLeft msgPointTop（看文档说明）
////                .msgPointTop(-10)
////                .msgPointTextSize(9)  //数字消息中字体大小
////                .msgPointSize(18)    //数字消息红色背景的大小
//                .addAlignBottom(true)  //加号是否同Tab文字底部对齐  RULE_BOTTOM时有效；
////                .addTextTopMargin(50)  //加号文字距离加号图片的距离
////                .addTextSize(15)      //加号文字大小
////                .addNormalTextColor(Color.parseColor("#ff0000"))    //加号文字未选中时字体颜色
////                .addSelectTextColor(Color.parseColor("#00ff00"))    //加号文字选中时字体颜色
                .build();
        navigationBar.selectTab(0);
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

}
