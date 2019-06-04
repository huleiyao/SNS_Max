package com.lzy.imagepicker;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.loader.ImageLoader;

import java.io.File;

//import com.lzy.ninegrid.NineGridView;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy
 * 版    本：1.0
 * 创建日期：16/9/5
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class GlideImageLoader implements ImageLoader/*, NineGridView.ImageLoader*/ {
 /*   @Override
    public void onDisplayImage(Context context, ImageView imageView, String url) {
        Glide.with(context).load(url)
                .into(imageView);


    }

    @Override
    public Bitmap getCacheImage(String url) {
        return null;
    }*/

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity).load(new File(path))
//                .placeholder(R.drawable.ic_default_color)//
//                .error(R.drawable.ic_default_color)//
//                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)//
                .into(imageView);

    }

    @Override
    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
        GlideLoaderUtil.LoadImage(activity,path,imageView);
    }

    @Override
    public void clearMemoryCache() {
    }
}
