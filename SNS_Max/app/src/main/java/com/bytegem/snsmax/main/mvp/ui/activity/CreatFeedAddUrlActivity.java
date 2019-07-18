package com.bytegem.snsmax.main.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bytegem.snsmax.main.app.bean.feed.MediaLinkContent;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.bytegem.snsmax.main.di.component.DaggerCreatFeedAddUrlComponent;
import com.bytegem.snsmax.main.mvp.contract.CreatFeedAddUrlContract;
import com.bytegem.snsmax.main.mvp.presenter.CreatFeedAddUrlPresenter;

import com.bytegem.snsmax.R;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/09/2019 13:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class CreatFeedAddUrlActivity extends BaseActivity<CreatFeedAddUrlPresenter> implements CreatFeedAddUrlContract.View {
    @BindView(R.id.add)
    TextView add;
    @BindView(R.id.url)
    EditText url;
    MaterialDialog materialDialog;
    MediaLinkContent mediaLinkContent;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1)
                showMessage("请检查网址是否错误！");
            else {
                setResult(103, new Intent().putExtra("url", mediaLinkContent));
                killMyself();
            }
            ;
        }
    };

    @OnClick(R.id.add)
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.add:
                String url_str = url.getText().toString();
                if (url_str.isEmpty()) ;
                else {
                    checkUrl(url_str);
                }
                break;
        }
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCreatFeedAddUrlComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    public String getUrlIcon(String url) {
        String[] split = url.split("/");
        if (split.length > 2)
            return split[0] + "//" + split[2] + "/" + "favicon.ico";
        return "";
    }

    public boolean getUrlTitle(String http, String url) {
        try {
            //还是一样先从一个URL加载一个Document对象。
            Document doc = Jsoup.connect(http + url).get();
            Elements links = doc.select("head");
            Elements titlelinks = links.get(0).select("title");
            String title = titlelinks.get(0).text();
            if (mediaLinkContent == null)
                mediaLinkContent = new MediaLinkContent();
            mediaLinkContent.setTitle(title);
            mediaLinkContent.setUrl(http + url);
            mediaLinkContent.setImage(getUrlIcon(http + url));
            handler.sendEmptyMessage(0);
            return true;
        } catch (Exception e) {
            //失败
            return false;
        }
    }

    public void checkUrl(String url) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                if (getUrlTitle("", url)) ;
                else if (getUrlTitle("http://", url)) ;
                else if (getUrlTitle("https://", url)) ;
                else handler.sendEmptyMessage(1);
            }
        }.start();
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_creat_feed_add_url; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("添加链接");
        url.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String content = editable.toString();
                if (content.isEmpty())
                    add.setBackground(getResources().getDrawable(R.drawable.shape_add_url_50_665d68ea));
                else
                    add.setBackground(getResources().getDrawable(R.drawable.shape_add_url_50_5d68ea));
            }
        });
    }

    @Override
    public void showLoading() {
        hideLoading();
        materialDialog = new MaterialDialog.Builder(this)
//                .title("正在上传图片")
                .content("验证网页地址中···")
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .canceledOnTouchOutside(false)
                .show();
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
}
