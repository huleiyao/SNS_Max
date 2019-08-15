package com.bytegem.snsmax.main.mvp.model;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.main.app.MApplication;
import com.bytegem.snsmax.main.app.bean.FileSignBean;
import com.bytegem.snsmax.main.app.bean.NetDefaultBean;
import com.bytegem.snsmax.main.app.bean.feed.DATAFeedComment;
import com.bytegem.snsmax.main.app.bean.feed.DataFeed;
import com.bytegem.snsmax.main.app.bean.feed.LISTFeedComments;
import com.bytegem.snsmax.main.app.bean.feed.LISTFeeds;
import com.bytegem.snsmax.main.app.bean.group.LISTGroupFeeds;
import com.bytegem.snsmax.main.app.config.CommunityService;
import com.bytegem.snsmax.main.app.config.GroupService;
import com.bytegem.snsmax.main.app.config.TopicService;
import com.bytegem.snsmax.main.app.config.UpdataImageService;
import com.bytegem.snsmax.main.app.config.UserService;
import com.bytegem.snsmax.main.mvp.contract.CreatNewsContract;
import com.bytegem.snsmax.main.mvp.contract.FeedCommentsOfCommentContract;
import com.bytegem.snsmax.main.mvp.contract.FeedDetailContract;
import com.bytegem.snsmax.main.mvp.contract.FeedDetailsContract;
import com.bytegem.snsmax.main.mvp.contract.FeedNewCommentContract;
import com.bytegem.snsmax.main.mvp.contract.FeedsContract;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.lzy.imagepicker.bean.ImageItem;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FeedModel extends BaseModel implements FeedsContract.Model, FeedCommentsOfCommentContract.Model
        , FeedDetailsContract.Model, FeedNewCommentContract.Model, CreatNewsContract.Model, FeedDetailContract.Model {

    public FeedModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<LISTFeeds> getFeedList(String latitude, String longitude, String per_page, String page) {
        return mRepositoryManager
                .obtainRetrofitService(CommunityService.class)
                .getList(MApplication.getTokenOrType(), latitude, longitude, per_page, page);
    }

    @Override
    public Observable<LISTGroupFeeds> getGroupList(int groupId, int feedId) {
        if (feedId == -1)
            return mRepositoryManager
                    .obtainRetrofitService(GroupService.class)
                    .getGroupFeedList(MApplication.getTokenOrType(), groupId);
        else return mRepositoryManager
                .obtainRetrofitService(GroupService.class)
                .getGroupFeedList(MApplication.getTokenOrType(), groupId, feedId);
    }

    @Override
    public Observable<LISTFeeds> getRecommendList(String per_page, String page) {
        return mRepositoryManager
                .obtainRetrofitService(CommunityService.class)
                .getRecommendList(MApplication.getTokenOrType(), per_page, page);
    }

    @Override
    public Observable<NetDefaultBean> commitFeedComment(int id, String jsonData) {
        return mRepositoryManager
                .obtainRetrofitService(CommunityService.class)
                .sendFeedComment(MApplication.getTokenOrType(), id, RequestBody.create(mediaType, jsonData));
    }

    @Override
    public Observable<LISTFeedComments> getCommentList(int id, int limit, int commentId, boolean isDefaultOrder, boolean isFirst) {
        if (isFirst)
            return mRepositoryManager
                    .obtainRetrofitService(CommunityService.class)
                    .getCommentsListDefault(MApplication.getTokenOrType(), id, limit, isDefaultOrder ? "desc" : "asc");
        else if (isDefaultOrder) return mRepositoryManager
                .obtainRetrofitService(CommunityService.class)
                .getCommentsListBefore(MApplication.getTokenOrType(), id, limit, "desc", commentId);
        else return mRepositoryManager
                    .obtainRetrofitService(CommunityService.class)
                    .getCommentsListAfter(MApplication.getTokenOrType(), id, limit, "asc", commentId);
    }

    @Override
    public Observable<DATAFeedComment> getHotComment(int id) {
        return mRepositoryManager
                .obtainRetrofitService(CommunityService.class)
                .getHotComment(MApplication.getTokenOrType(), id);
    }

    @Override
    public Observable<DataFeed> getFeedInfo(int id) {
        return mRepositoryManager
                .obtainRetrofitService(CommunityService.class)
                .getFeedDetails(MApplication.getTokenOrType(), id);
    }

    @Override
    public Observable<NetDefaultBean> changeLikeState(int id, boolean isLike) {
        if (isLike)
            return mRepositoryManager
                    .obtainRetrofitService(CommunityService.class)
                    .changeDislikeState(MApplication.getTokenOrType(), id);
        else return mRepositoryManager
                .obtainRetrofitService(CommunityService.class)
                .changeLikeState(MApplication.getTokenOrType(), id);
    }

    @Override
    public Observable<NetDefaultBean> changeUserFollowState(int id, boolean isFollow) {
        if (isFollow)
            return mRepositoryManager
                    .obtainRetrofitService(UserService.class)
                    .changeUserUnfollowState(MApplication.getTokenOrType(), id);
        else return mRepositoryManager
                .obtainRetrofitService(UserService.class)
                .changeUserFollowState(MApplication.getTokenOrType(), id);
    }


    @Override
    public Observable<LISTFeedComments> getList(int feedId, int id, int limit, int commentId, boolean isDefaultOrder, boolean isFirst) {
        if (isFirst)
            return mRepositoryManager
                    .obtainRetrofitService(CommunityService.class)
                    .getCommentsCommentsListDefault(MApplication.getTokenOrType(), id, limit, isDefaultOrder ? "desc" : "asc");
        else if (isDefaultOrder) return mRepositoryManager
                .obtainRetrofitService(CommunityService.class)
                .getCommentsCommentsListBefore(MApplication.getTokenOrType(), id, limit, "desc", commentId);
        else return mRepositoryManager
                    .obtainRetrofitService(CommunityService.class)
                    .getCommentsCommentsListAfter(MApplication.getTokenOrType(), id, limit, "asc", commentId);
    }

    @Override
    public Observable<NetDefaultBean> commit(int feedId, int id, String jsonData) {
        return mRepositoryManager
                .obtainRetrofitService(CommunityService.class)
                .sendFeedCommentsComment(MApplication.getTokenOrType(), id, RequestBody.create(mediaType, jsonData));
    }

    @Override
    public Observable<NetDefaultBean> changeCommentLikeState(int id, boolean isLike) {
        if (isLike)
            return mRepositoryManager
                    .obtainRetrofitService(CommunityService.class)
                    .changeCommentDislikeState(MApplication.getTokenOrType(), id);
        else return mRepositoryManager
                .obtainRetrofitService(CommunityService.class)
                .changeCommentLikeState(MApplication.getTokenOrType(), id);
    }

    @Override
    public Observable<NetDefaultBean> sendNewFeed(String jsonData) {
        return mRepositoryManager
                .obtainRetrofitService(CommunityService.class)
                .send(MApplication.getTokenOrType(), RequestBody.create(mediaType, jsonData));
    }

    @Override
    public Observable<NetDefaultBean> topicSend(int topicId, String jsonData) {
        return mRepositoryManager
                .obtainRetrofitService(TopicService.class)
                .sendFeedInTopic(MApplication.getTokenOrType(), topicId, RequestBody.create(mediaType, jsonData));
    }

    @Override
    public Observable<NetDefaultBean> groupSend(int topicId, String jsonData) {
        return mRepositoryManager
                .obtainRetrofitService(GroupService.class)
                .sendFeedInGroup(MApplication.getTokenOrType(), topicId, RequestBody.create(mediaType, jsonData));
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

}
