package com.bytegem.snsmax.main.mvp.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bytegem.snsmax.R;
import com.bytegem.snsmax.common.View.SwitchButton;
import com.bytegem.snsmax.common.utils.M;
import com.bytegem.snsmax.main.app.bean.NetDefaultBean;
import com.bytegem.snsmax.main.app.config.UserService;
import com.bytegem.snsmax.main.app.mvc.UserBindActivity;
import com.bytegem.snsmax.main.app.mvc.utils.AppUtils;
import com.bytegem.snsmax.main.app.mvc.utils.DataCleanManager;
import com.bytegem.snsmax.main.app.utils.HttpMvcHelper;
import com.bytegem.snsmax.main.mvp.ui.base.BaseActivity;

import java.io.File;
import java.math.BigDecimal;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SettingsActivity extends BaseActivity implements View.OnClickListener {

    private TextView barTitle, txtUserCache, btnSignOut;
    private RelativeLayout barBack;
    private LinearLayout btnUserBind, btnUserAbout, btnUserRecommend, btnUserScore, btnUserClear;
    private Intent intent = new Intent();
    private Context context;
    private SwitchButton btnEye;


    @Override
    public int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    public void initView() {
        context = this;
        txtUserCache = findViewById(R.id.user_cache);
        barTitle = findViewById(R.id.title_title);
        barBack = findViewById(R.id.title_back);
        btnUserBind = findViewById(R.id.user_bind);
        btnUserAbout = findViewById(R.id.user_about);
        btnUserRecommend = findViewById(R.id.user_recommend);
        btnUserScore = findViewById(R.id.user_score);
        btnUserClear = findViewById(R.id.user_clear);
        btnEye = findViewById(R.id.user_eye);
        btnSignOut = findViewById(R.id.user_sign_out);
        barTitle.setText("设置");
        try {
            initEX();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setListener();
    }

    private void setListener() {
        barBack.setOnClickListener(this);
        btnUserBind.setOnClickListener(this);
        btnUserAbout.setOnClickListener(this);
        btnUserRecommend.setOnClickListener(this);
        btnUserScore.setOnClickListener(this);
        btnUserClear.setOnClickListener(this);
        btnEye.setOnClickListener(this);
        btnSignOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.user_bind:
                intent.setClass(this, UserBindActivity.class);
                startActivity(intent);
                break;
            case R.id.user_about:
                break;
            case R.id.user_recommend:
                break;
            case R.id.user_score:
                AppUtils.goAppShop(context, "com.tencent.mm", "");
                break;
            case R.id.user_clear:
                // 这里的属性可以一直设置，因为每次设置后返回的是一个builder对象
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                // 设置提示框的标题
                builder.setMessage("你确定清空缓存吗").
                        // 设置确定按钮
                                setPositiveButton("清除", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                DataCleanManager.cleanApplicationData(context);
                                Toast.makeText(context, "清除成功", Toast.LENGTH_SHORT).show();
                                txtUserCache.setText("0.0MB");
                            }
                        }).
                        // 设置取消按钮,null是什么都不做，并关闭对话框
                                setNegativeButton("取消", null);
                // 生产对话框
                AlertDialog alertDialog = builder.create();
                // 显示对话框
                alertDialog.show();
                break;
            case R.id.user_sign_out:
                HttpMvcHelper
                        .obtainRetrofitService(UserService.class)
                        .signOut(HttpMvcHelper.getTokenOrType())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(suc -> {
                            NetDefaultBean a = suc;
                            Intent intent = new Intent();
                            intent.setClass(context, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }, err -> {
                            Toast.makeText(context, "退出登录失败!", Toast.LENGTH_SHORT).show();
                        });
                break;
            default:
                break;
        }
    }


    private void initEX() throws Exception {
        long cacheSize = getFolderSize(getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(getExternalCacheDir());
        }
        txtUserCache.setText(getFormatSize(cacheSize).toString());//将获取到的大小set进去

    }

    // 获取文件
    //Context.getExternalFilesDir() --> SDCard/Android/data/com.bytegem.snsmax/files/ 目录，一般放一些长时间保存的数据
    //Context.getExternalCacheDir() --> SDCard/Android/data/com.bytegem.snsmax/cache/目录，一般存放临时缓存数据
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    public static String getFormatSize(double size) throws Exception {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";

    }

}
