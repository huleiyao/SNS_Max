package com.bytegem.snsmax.main.app.config;

import com.bytegem.snsmax.main.app.bean.NetDefaultBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface GroupService {

    //创建圈子
    @POST("/groups")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<NetDefaultBean> createGroup(@Header("Authorization") String authorization, @Body RequestBody requestBody);
}
