package com.bytegem.snsmax.main.app;

import android.content.Context;

import com.bytegem.snsmax.main.app.bean.location.LocationBean;
import com.bytegem.snsmax.main.app.bean.location.TencentMapLocationBean;
import com.facebook.stetho.Stetho;
import com.jess.arms.base.BaseApplication;
import com.lzy.imagepicker.GlideImageLoader;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.MediaLoader;
import com.lzy.okhttputils.OkHttpUtils;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;

import java.lang.ref.WeakReference;
import java.util.Locale;

import cn.ittiger.player.Config;
import cn.ittiger.player.PlayerManager;
import cn.ittiger.player.factory.ExoPlayerFactory;
import cn.jpush.android.api.JPushInterface;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

import static com.bytegem.snsmax.main.app.Api.FILE_UPDATA_DOMAIN;
import static com.bytegem.snsmax.main.app.Api.FILE_UPDATA_DOMAIN_NAME;

/**
 * Created by addis on 2018/3/28.
 */

public class MApplication extends BaseApplication {

    private static MApplication instance;
    private static WeakReference<Context> contexts;
    public static String token_type = "";
    public static String token = "";
    public static LocationBean location = new LocationBean();
    public static TencentMapLocationBean selectLocation;

    public final static String getTokenOrType() {
        return MApplication.token_type + " " + MApplication.token;
    }

    public static MApplication getInstance() {
        if (instance == null) {
            instance = new MApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitUrlManager.getInstance().putDomain(FILE_UPDATA_DOMAIN_NAME, FILE_UPDATA_DOMAIN);
        initUMeng();
        initImagePicker();
        OkHttpUtils.init(this);
        initVideoPlayer();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        Stetho.initializeWithDefaults(this);
    }

    public void initVideoPlayer() {

        //该配置最好在Application中实现
        PlayerManager.loadConfig(
                new Config.Builder(this)
                        .buildPlayerFactory(new ExoPlayerFactory(this))//使用ExoPlayer内核作为视频播放器，默认使用MediaPlayer
                        .enableSmallWindowPlay()//开启小窗口播放，默认不开其
                        .cache(true)//开启缓存功能，默认不开启
//                        .cacheProxy(HttpProxyCacheServer)//自定义缓存配置，不设置则采用默认的缓存配置
                        .build()
        );
    }

    public void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setMultiMode(true);
        imagePicker.setShowCamera(true);
        imagePicker.setSelectLimit(1);
        imagePicker.setCrop(false);

        Album.initialize(AlbumConfig.newBuilder(this)
                .setAlbumLoader(new MediaLoader())
                .setLocale(Locale.getDefault())
                .build()
        );
    }

    public static Context getContext() {
        return contexts == null ? null : contexts.get();
    }

    public void initUMeng() {

        //设置LOG开关，默认为false
//        UMConfigure.setLogEnabled(true);
//        try {
//            Class<?> aClass = Class.forName("com.umeng.commonsdk.UMConfigure");
//            Field[] fs = aClass.getDeclaredFields();
//            for (Field f : fs) {
////                Log.e("xxxxxx", "ff=" + f.getName() + "   " + f.getType().getName());
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        UMConfigure.init(this, "5a12384aa40fa3551f0001d1"
//                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
//
//        PlatformConfig.setWeixin("wxbbb961a0b0bf577a", "7ea0101aeabd53bc32859370cde278cc");
//        //注意代码这里配置的回调地址需要和微博开放平台授权回调页保持一致
//        PlatformConfig.setSinaWeibo("3997129963", "da07bcf6c9f30281e684f8abfd0b4fca", "http://sns.eduline.com/sina2/callback");
//        // 新浪微博 appkey appsecret
//        PlatformConfig.setQQZone("101400042", "a85c2fcd67839693d5c0bf13bec84779");
    }
}
