package com.bytegem.snsmax.main.mvp.ui.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.bytegem.snsmax.main.app.utils.StatusBarUtil;
import com.jess.arms.utils.NewStatusBarUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(this.getLayoutId());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        unbinder = ButterKnife.bind(this);
        setStatusBar();
        initView();
        setListener();
    }

    protected void setStatusBar() {
        //这里做了两件事情
        // 1.使状态栏透明并使contentView填充到状态栏
        // 2.预留出状态栏的位置，防止界面上的控件离顶部靠的太近。这样就可以实现开头说的第二种情况的沉浸式状态栏了
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        StatusBarUtil.setImmersiveStatusBar(this, true);
        Window window = getWindow();
        //设置虚拟键盘跟着屏幕自动
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (NewStatusBarUtil.isMeizu()) {
            NewStatusBarUtil.setMeizuStatusBar(window, true);
            NewStatusBarUtil.setStatusBarTranslucent(this, true);
        } else if (NewStatusBarUtil.isXiaomi()) {
            NewStatusBarUtil.setXiaomiStatusBar(window, true);
            NewStatusBarUtil.setStatusBarTranslucent(this, true);
        } else {
            NewStatusBarUtil.setStatusBarTranslucent(this, true);
        }
    }


    /**
     * 设置布局
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 初始化视图
     */
    public abstract void initView();

    /**
     * 初始化监听
     */
    public abstract void setListener();

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

}
