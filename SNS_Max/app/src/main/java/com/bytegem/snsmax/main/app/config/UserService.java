package com.bytegem.snsmax.main.app.config;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.main.app.bean.LoginData;
import com.bytegem.snsmax.main.app.bean.NetDefaultBean;
import com.bytegem.snsmax.main.app.bean.UserData;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {
    //发送手机验证码
    @POST("/send-verification-code")
    Observable<NetDefaultBean> getCode(@Body RequestBody requestBody);

    //登录
    @POST("/auth/login")
    Observable<LoginData> login(@Body RequestBody requestBody);

    //注册
    @POST("/auth/register")
    Observable<LoginData> register(@Body RequestBody requestBody);

    //绑定手机号，未绑定的才可使用
    @PUT("/user/phone")
    Observable<NetDefaultBean> bindPhone(@Body RequestBody requestBody);

    //更换手机号
    @PATCH("/user/phone")
    Observable<NetDefaultBean> changePhone(@Body RequestBody requestBody);

    //更新用户信息
    @PATCH("/user")
    Observable<NetDefaultBean> updataUserData(@Body RequestBody requestBody);

    //获取当前登录用户资料
    @GET("/user")
    Observable<UserData> getUser();

    //通过id获取用户资料
    @GET("/users/{id}")
    Observable<UserData> getUserFromId(@Path("id") int id);

    //取消/关注一个用户
    @PUT("/user/followings/{id}")
    Observable<NetDefaultBean> changeUserFollowState(@Path("id") int id);    //取消/关注一个用户

    //取消/关注一个用户
    @DELETE("/user/followings/{id}")
    Observable<NetDefaultBean> changeUserUnfollowState(@Path("id") int id);
}
