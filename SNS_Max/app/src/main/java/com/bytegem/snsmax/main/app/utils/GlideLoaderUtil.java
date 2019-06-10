package com.bytegem.snsmax.main.app.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.bytegem.snsmax.R;

import jp.wasabeef.glide.transformations.MaskTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * desc: .
 * author: Will .
 * date: 2017/7/27 .
 */
public class GlideLoaderUtil {

    /**
     * 常规使用
     *
     * @param context   上下文
     * @param url       图片链接
     * @param imageView 目标view
     */
    public static void LoadImage(Context context, Object url, ImageView imageView) {
        Glide.with(context).load(url)
                .apply(new RequestOptions()
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .placeholder(R.mipmap.default_img)
                                .error(R.mipmap.grid_camera)
                                .dontAnimate()
                )
                .transition(new DrawableTransitionOptions().crossFade(800))
                .into(imageView);
    }

    public static void LoadImage(Context context, Object url, ImageView imageView, int screenWidth, int screenHeight) {
        Glide.with(context).asBitmap().load(url).into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                int imageWidth = resource.getWidth();
                int imageHeight = resource.getHeight();
                int height = screenWidth * imageHeight / imageWidth;
                ViewGroup.LayoutParams para = imageView.getLayoutParams();
                para.height = height;
                para.width = screenWidth;
                imageView.setImageBitmap(ss(imageHeight, imageWidth, screenWidth, screenHeight, resource));
            }
        });
    }

    private static Bitmap ss(double height, double width, int aWidth, int aHeight, Bitmap bitmap) {
        if (width > aWidth) {
            double scale = (aWidth - 40d) / width;
            width = scale * width;
            height = scale * height;
            bitmap = big(bitmap, scale);
        }
        return bitmap;
    }

    public static Bitmap big(Bitmap b, double scale) {
        if (b == null) return b;
        int w = b.getWidth();
        int h = b.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale((float) scale, (float) scale); // 长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(b, 0, 0, w, h, matrix, true);
        return resizeBmp;
    }

    /**
     * 圆角图片
     *
     * @param context   上下文
     * @param url       图片链接
     * @param imageView 目标view
     */
    public static void LoadRoundImage(Context context, Object url, ImageView imageView) {
        Glide.with(context).load(url)
                .apply(new RequestOptions()
                        .transform(new GlideRoundTransform(context, 6))
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.mipmap.grid_camera)
                        .error(R.mipmap.grid_camera)
                        .dontAnimate())
                .transition(new DrawableTransitionOptions().crossFade(800))
                .into(imageView);

    }

    /**
     * 圆角图片
     *
     * @param context   上下文
     * @param url       图片链接
     * @param imageView 目标view
     */
    public static void LoadRoundImage20(Context context, Object url, ImageView imageView) {
        Glide.with(context).load(url)
                .apply(bitmapTransform(new MultiTransformation<>(new CenterCrop(),
                        new MaskTransformation(R.drawable.shape_frame_image_round20))))
                .transition(new DrawableTransitionOptions().crossFade(800))
                .into(imageView);

    }

    /**
     * 圆角图片
     *
     * @param context   上下文
     * @param url       图片链接
     * @param imageView 目标view
     */
    public static void LoadRoundImage8(Context context, Object url, ImageView imageView) {
        Glide.with(context).load(url)
                .apply(bitmapTransform(new MultiTransformation<>(new CenterCrop(),
                        new MaskTransformation(R.drawable.shape_frame_image_round8))))
                .transition(new DrawableTransitionOptions().crossFade(800))
                .into(imageView);

    }


    /**
     * 圆形图片
     *
     * @param context   上下文
     * @param url       图片链接
     * @param imageView 目标view
     */
    public static void LoadCircleImage(Context context, Object url, ImageView imageView) {
        Glide.with(context).load(url)
                .apply(new RequestOptions()
                        .transform(new GlideCircleTransform(context))
//                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .placeholder(R.mipmap.default_img)
//                        .error(R.mipmap.default_img)
                        .dontAnimate())
                .transition(new DrawableTransitionOptions().crossFade(800))
                .into(imageView);

    }


    /**
     * 自定义RequestOptions使用
     *
     * @param context        上下文
     * @param url            图片链接
     * @param requestOptions
     * @param imageView      目标view
     */
    public static void LoadImage(Context context, Object url, ImageView imageView, RequestOptions requestOptions) {
        Glide.with(context).load(url)
                .apply(requestOptions)
                .transition(new DrawableTransitionOptions().crossFade(800))
                .into(imageView);
    }

    /**
     * 自定义RequestOptions使用
     *
     * @param fragment
     * @param url            图片链接
     * @param requestOptions
     * @param imageView      目标view
     */
    public static void LoadImage(android.support.v4.app.Fragment fragment, Object url, ImageView imageView, RequestOptions requestOptions) {
        Glide.with(fragment).load(url)
                .apply(requestOptions)
                .transition(new DrawableTransitionOptions().crossFade(800))
                .into(imageView);
    }


    /**
     * 需要回调时使用
     *
     * @param context         上下文
     * @param url             图片链接
     * @param imageViewTarget 回调需求
     */
    public static void LoadImage(Context context, Object url, ImageViewTarget imageViewTarget) {
        Glide.with(context).load(url)
                .apply(new RequestOptions()
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .transition(new DrawableTransitionOptions().crossFade(800))
                .into(imageViewTarget);
    }

    /**
     * 需要回调时使用
     *
     * @param context   上下文
     * @param url       图片链接
     * @param imageView 回调需求
     */
    public static void LoadImage(Context context, Object url, ImageView imageView, RequestListener listener) {
        Glide.with(context).load(url)
                //.thumbnail(0.1f)
                .apply(new RequestOptions()
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .transition(new DrawableTransitionOptions().crossFade(800))
                .listener(listener)
                .into(imageView);
    }


}
