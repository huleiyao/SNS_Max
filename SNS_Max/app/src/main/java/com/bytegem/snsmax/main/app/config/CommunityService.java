package com.bytegem.snsmax.main.app.config;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.main.app.bean.CommunityPostList;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CommunityService {

    //获取动态列表
    @GET("/nearby/feeds")
//    Observable<CommunityPostList> getList(@Body RequestBody requestBody);
    Observable<CommunityPostList> getList(@Query("latitude") String latitude, @Query("longitude") String longitude, @Query("per_page") String per_page, @Query("page") String page);

    //发布动态
    @POST("/feeds")
    Observable<MBaseBean> send(@Body RequestBody requestBody);
}
