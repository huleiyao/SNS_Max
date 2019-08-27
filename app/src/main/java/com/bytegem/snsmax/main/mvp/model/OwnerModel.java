package com.bytegem.snsmax.main.mvp.model;

import android.app.Application;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.main.app.MApplication;
import com.bytegem.snsmax.main.app.bean.FileSignBean;
import com.bytegem.snsmax.main.app.bean.NetDefaultBean;
import com.bytegem.snsmax.main.app.bean.login.LoginData;
import com.bytegem.snsmax.main.app.bean.user.DATAUser;
import com.bytegem.snsmax.main.app.config.UpdataImageService;
import com.bytegem.snsmax.main.app.config.UserService;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.bytegem.snsmax.main.mvp.contract.OwnerContract;
import com.lzy.imagepicker.bean.ImageItem;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/10/2019 16:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class OwnerModel extends BaseModel implements OwnerContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public OwnerModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<DATAUser> getUserData() {
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .getUser(MApplication.getTokenOrType());
    }

    @Override
    public Observable<FileSignBean> getSign(String type, File file, long length, String md5) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("factor", file.getName(), requestBody);
        return mRepositoryManager
                .obtainRetrofitService(UpdataImageService.class)
                .getImageSign(
                        MApplication.getTokenOrType()
                        , MultipartBody.Part.createFormData("type", type)
                        , part
                        , MultipartBody.Part.createFormData("length", length + "")
                        , MultipartBody.Part.createFormData("md5", md5)
                );
    }

    @Override
    public Observable<MBaseBean> updataCover(FileSignBean fileSignBean, ImageItem imageItem) {
        String path = fileSignBean.getPath();
        if (path.indexOf("/") == 0)
            path = path.substring(1);
        return mRepositoryManager
                .obtainRetrofitService(UpdataImageService.class)
                .updataImage(
                        fileSignBean.getHeaders().getAuthorization()
                        , fileSignBean.getHeaders().getHost()
                        , fileSignBean.getHeaders().getMd5()
                        , fileSignBean.getHeaders().getCos()
                        , imageItem.mimeType
                        , RequestBody.create(MediaType.parse("application/otcet-stream"), new File(imageItem.path))
                        , path
                );
    }

    @Override
    public Observable<NetDefaultBean> updataUser(String jsonData) {
        return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .updataUserData(MApplication.getTokenOrType(), RequestBody.create(mediaType, jsonData));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}