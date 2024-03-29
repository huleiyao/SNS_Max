package com.bytegem.snsmax.main.mvp.contract;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.main.app.bean.FileSignBean;
import com.bytegem.snsmax.main.app.bean.NetDefaultBean;
import com.bytegem.snsmax.main.app.bean.feed.MediaVideoContent;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.lzy.imagepicker.bean.ImageItem;

import java.io.File;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/22/2019 09:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface CreatNewsContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void watchImagePicker(int position);

        void openPhotos();

        void remove(int position);

        void showVideo(MediaVideoContent mediaVideoContent);

        void toCameraVideo();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<NetDefaultBean> sendNewFeed(String jsonData);

        Observable<NetDefaultBean> topicSend(int topicId, String jsonData);

        Observable<NetDefaultBean> groupSend(int topicId, String jsonData);

        Observable<MBaseBean> updataCover(FileSignBean fileSignBean, ImageItem imageItem);

        Observable<FileSignBean> getSign(String type, File file, long length, String md5);
    }
}
