package com.bytegem.snsmax.main.app.config;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.main.app.bean.LoginData;
import com.bytegem.snsmax.main.app.bean.NetDefaultBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface LoginService {
    //发送手机验证码
    @POST("/send-verification-code")
    Observable<NetDefaultBean> getCode(@Body RequestBody requestBody);

    //登录
    @POST("/auth/login")
    Observable<LoginData> login(@Body RequestBody requestBody);

    //注册
    @POST("/auth/register")
    Observable<LoginData> register(@Body RequestBody requestBody);
}
