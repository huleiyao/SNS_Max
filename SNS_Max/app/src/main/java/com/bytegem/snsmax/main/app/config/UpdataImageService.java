package com.bytegem.snsmax.main.app.config;

import com.bytegem.snsmax.main.app.bean.CommunityPostList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UpdataImageService {
    //获取签名信息
    @GET("/cos/make-put-sign")
    Observable<CommunityPostList> getImageSign(@Query("per_page") String per_page, @Query("page") String page);
}
