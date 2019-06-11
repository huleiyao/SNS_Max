package com.bytegem.snsmax.main.mvp.ui.view;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bytegem.snsmax.R;
import com.bytegem.snsmax.main.app.utils.GlideLoaderUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeBannerView {
    private View view;
    private Unbinder mUnbinder;
    @BindView(R.id.banner)
    Banner banner;
    Context mContext;

    public HomeBannerView(Context context) {
        mContext = context;
        this.view = View.inflate(mContext, R.layout.view_home_banner, null);
        mUnbinder = ButterKnife.bind(this, view);
    }

    public View getView() {
        return view;
    }

    public void showBanner(ArrayList<String> list) {
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                //设置图片加载器
                .setImageLoader(new GlideImageLoader())
                //设置图片集合
                .setImages(list)
                //设置banner动画效果
                .setBannerAnimation(Transformer.DepthPage)
                //设置标题集合（当banner样式有显示title时）
//                .setBannerTitles(list)
                //设置自动轮播，默认为true
                .isAutoPlay(true)
                //设置轮播时间
                .setDelayTime(3000)
                //设置指示器位置（当banner模式中有指示器时）
                .setIndicatorGravity(BannerConfig.CENTER)
                //banner设置方法全部调用完毕时最后调用
                .start();
    }

    public void unBind() {
        mUnbinder.unbind();
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            GlideLoaderUtil.LoadRoundImage20(context, path, imageView);
        }
    }
}
