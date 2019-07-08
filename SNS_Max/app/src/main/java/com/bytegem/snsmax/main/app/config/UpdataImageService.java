package com.bytegem.snsmax.main.app.config;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.main.app.bean.FileSignBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

import static com.bytegem.snsmax.main.app.Api.FILE_UPDATA_DOMAIN_NAME;
import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

public interface UpdataImageService {
    //获取签名信息
    @Multipart
    @POST("/cos/make-put-sign")
    @Headers({/*"Content-Type:application/json",*/ "Accept:application/json"})
    Observable<FileSignBean> getImageSign(
            @Header("Authorization") String authorization
            , @Part MultipartBody.Part type
            , @Part MultipartBody.Part file
            , @Part MultipartBody.Part length
            , @Part MultipartBody.Part md5
    );

    @Headers({DOMAIN_NAME_HEADER + FILE_UPDATA_DOMAIN_NAME})
    @PUT("{path}")
    Observable<MBaseBean> updataImage(
            @Header("Authorization") String authorization
            , @Header("Host") String host
            , @Header("Content-MD5") String md5
            , @Header("x-cos-acl") String cos
            , @Header("Content-Type") String mimetype
            , @Body RequestBody file
            , @Path("path") String path
    );
}
