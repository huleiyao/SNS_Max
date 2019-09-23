package com.bytegem.snsmax.main.app.utils;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.bytegem.snsmax.main.app.MApplication;
import com.google.gson.Gson;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.utils.ArmsUtils;

/**
 * 在mvc中使用mvp的辅助类。也就是将mvp中的相关对象包装暴露到mvc中进行使用。防止使用不同的对象进行网络操作
 * 实现数据的可控性和集中性
 */
public class HttpMvcHelper{
    private static AppComponent appComponent;
    private static IRepositoryManager repositoryManager;
    private static Gson gson;


    /**
     * 包装获取token和类型再返回。方便在此类中能统一完成。作为对外暴露的统一入口
     * 也就是网络请求时候需要的token信息
     * @return
     */
    public static String getTokenOrType(){
        return MApplication.getTokenOrType();
    }

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param service Retrofit service class
     * @param <T>     Retrofit service 类型
     * @return Retrofit service
     */
    public static <T> T obtainRetrofitService(@NonNull Class<T> service) {
        return repositoryManager.obtainRetrofitService(service);
    }

    /**
     * 根据传入的 Class 获取对应的 RxCache service
     *
     * @param cache RxCache service class
     * @param <T>   RxCache service 类型
     * @return RxCache service
     */
//    public static <T> T obtainCacheService(@NonNull Class<T> cache) {
//        return repositoryManager.obtainCacheService(cache);
//    }

    /**
     * 清理所有缓存
     */
    public static void clearAllCache() {
        repositoryManager.clearAllCache();
    }

    /**
     * 获取 {@link Context}
     *
     * @return {@link Context}
     */
    public static Context getContext() {
        return repositoryManager.getContext();
    }

    /**
     * 获取当前系统中默认的Gson解析对象
     * @return
     */
    public static Gson getGson(){
        return gson;
    }

    /**
     * 初始化配置。初始化配置Http辅助类，需要在{@link Application#onCreate()}进行初始化
     */
    public static void initConfig(){
        appComponent = ArmsUtils.obtainAppComponentFromContext(MApplication.getInstance());
        repositoryManager = appComponent.repositoryManager();
        gson = appComponent.gson();
    }
}

