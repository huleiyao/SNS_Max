package com.bytegem.snsmax.main.app.config;

import com.bytegem.snsmax.main.app.bean.CommunityPostList;
import com.bytegem.snsmax.main.app.bean.FileSignBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import io.reactivex.Observable;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UpdataImageService {
    //获取签名信息
    @Multipart
    @POST("/cos/make-put-sign")
    Observable<FileSignBean> getImageSign(@Part("type") String type, @Part("factor") File file, @Part("length") long length, @Part("md5") String md5);
}
