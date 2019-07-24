package com.bytegem.snsmax.main.mvp.contract;

import com.bytegem.snsmax.main.app.bean.feed.FeedCommentBean;
import com.bytegem.snsmax.main.app.bean.feed.LISTFeedComments;
import com.bytegem.snsmax.main.app.bean.feed.DATAFeedComment;
import com.bytegem.snsmax.main.app.bean.NetDefaultBean;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/23/2019 15:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface FeedDetailsContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void onFinishFreshAndLoad();

        void showHotComment(FeedCommentBean feedCommentBean);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<LISTFeedComments> getCommentList(int id, int limit, int commentId, boolean isDefaultOrder, boolean isFirst);

        Observable<DATAFeedComment> getHotComment(int id);

        Observable<NetDefaultBean> changeLikeState(int id, boolean isLike);

        Observable<NetDefaultBean> changeCommentLikeState(int id, boolean isLike);

        Observable<NetDefaultBean> changeUserFollowState(int id, boolean isFollow);

        Observable<NetDefaultBean> commitFeedComment(int id, String jsonData);
    }
}
