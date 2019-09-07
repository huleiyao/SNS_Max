package com.bytegem.snsmax.main.app.config;

import com.bytegem.snsmax.main.app.bean.login.LoginData;
import com.bytegem.snsmax.main.app.bean.NetDefaultBean;
import com.bytegem.snsmax.main.app.bean.user.DATAUser;
import com.bytegem.snsmax.main.app.bean.user.MyCircleDTO;
import com.bytegem.snsmax.main.app.utils.HttpMvcHelper;
import com.bytegem.snsmax.main.mvp.model.HelperModel;
import com.bytegem.snsmax.main.mvp.model.ProposalModel;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {
    //发送手机验证码
    @POST("/send-verification-code")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<NetDefaultBean> getCode(@Body RequestBody requestBody);

    //登录
    @POST("/auth/login")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<LoginData> login(@Body RequestBody requestBody);

    //刷新token
    @GET("/auth/refresh")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<LoginData> tokenRefresh(@Header("Authorization") String authorization);

    //注册
    @POST("/auth/register")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<LoginData> register(@Body RequestBody requestBody);

    //绑定手机号，未绑定的才可使用
    @PUT("/user/phone")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<NetDefaultBean> bindPhone(@Header("Authorization") String authorization, @Body RequestBody requestBody);

    //更换手机号
    @PATCH("/user/phone")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<NetDefaultBean> changePhone(@Header("Authorization") String authorization, @Body RequestBody requestBody);

    //更新用户信息
    @PATCH("/user")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<NetDefaultBean> updateUserData(@Header("Authorization") String authorization, @Body RequestBody requestBody);

    //获取当前登录用户资料
    @GET("/user")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<DATAUser> getUser(@Header("Authorization") String authorization);

    //通过id获取用户资料
    @GET("/users/{id}")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<DATAUser> getUserFromId(@Header("Authorization") String authorization, @Path("id") int id);

    //取消/关注一个用户
    @PUT("/user/followings/{id}")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<NetDefaultBean> changeUserFollowState(@Header("Authorization") String authorization, @Path("id") int id);    //取消/关注一个用户

    //取消/关注一个用户
    @DELETE("/user/followings/{id}")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<NetDefaultBean> changeUserUnfollowState(@Header("Authorization") String authorization, @Path("id") int id);

    //获取我的我的圈子
    @GET("/user/groups")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<MyCircleDTO> getMyClicle(@Header("Authorization") String authorization);

    //退出登录
    @GET("/auth/logout")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<NetDefaultBean> signOut(@Header("Authorization") String authorization);

    //获取帮助列表
    @GET("/helps")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<HelperModel> getHelper(@Header("Authorization") String authorization);

    //获取帮助详情
    @GET("/helps/{id}")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<ProposalModel> getHelperDetails(@Header("Authorization") String authorization, @Path("id") int id);

}
