package com.bytegem.snsmax.main.app.config;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.main.app.bean.CommunityCommentsList;
import com.bytegem.snsmax.main.app.bean.CommunityPostData;
import com.bytegem.snsmax.main.app.bean.CommunityPostList;
import com.bytegem.snsmax.main.app.bean.NetDefaultBean;
import com.bytegem.snsmax.main.mvp.model.CommunityPostDetailsModel;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.bytegem.snsmax.main.mvp.model.CommunityPostDetailsModel.gets;

public interface CommunityService {
    String getCommentsListAndSendComment = "/feeds/{id}/comments";
    String getCommentsCommentListAndSendCommentComment = "/comments/{id}/children";

    //获取动态列表
    @GET("/recommend/temporary-feeds")
    Observable<CommunityPostList> getRecommendList(@Query("per_page") String per_page, @Query("page") String page);

    //获取动态列表
    @GET("/nearby/feeds")
    Observable<CommunityPostList> getList(@Query("latitude") String latitude, @Query("longitude") String longitude, @Query("per_page") String per_page, @Query("page") String page);

    //取消/喜欢动态
    @PUT("/feeds/{feedid}/like")
    Observable<NetDefaultBean> changeLikeState(@Path("feedid") int id);

    //取消/喜欢动态
    @DELETE("/feeds/{feedid}/like")
    Observable<NetDefaultBean> changeDislikeState(@Path("feedid") int id);

    //获取动态详情
    @GET("/feeds/{id}")
    Observable<CommunityPostData> getFeedDetails(@Path("id") int id);

    //发布动态
    @POST("/feeds")
    Observable<NetDefaultBean> send(@Body RequestBody requestBody);

    //发布动态评论的评论
    @GET(getCommentsCommentListAndSendCommentComment)
    Observable<NetDefaultBean> sendFeedCommentsComment(@Path("id") int id, @Body RequestBody requestBody);

    //第一次获取评论的评论列表
    @GET(getCommentsCommentListAndSendCommentComment)
    Observable<CommunityCommentsList> getCommentsCommentsListDefault(@Path("id") int id, @Query("limit") int limit, @Query("order") String order);

    //获取正序评论的评论列表
    @GET(getCommentsCommentListAndSendCommentComment)
    Observable<CommunityCommentsList> getCommentsCommentsListAfter(@Path("id") int id, @Query("limit") int limit
            , @Query("order") String order, @Query("after") int after);

    //获取倒序评论的评论列表
    @GET(getCommentsCommentListAndSendCommentComment)
    Observable<CommunityCommentsList> getCommentsCommentsListBefore(@Path("id") int id, @Query("limit") int limit
            , @Query("order") String order, @Query("before") int before);

    //发布动态评论
    @GET(getCommentsListAndSendComment)
    Observable<NetDefaultBean> sendFeedComment(@Path("id") int id, @Body RequestBody requestBody);

    //第一次获取评论列表
    @GET(getCommentsListAndSendComment)
    Observable<CommunityCommentsList> getCommentsListDefault(@Path("id") int id, @Query("limit") int limit, @Query("order") String order);

    //获取正序评论列表
    @GET(getCommentsListAndSendComment)
    Observable<CommunityCommentsList> getCommentsListAfter(@Path("id") int id, @Query("limit") int limit
            , @Query("order") String order, @Query("after") int after);

    //获取倒序评论列表
    @GET(getCommentsListAndSendComment)
    Observable<CommunityCommentsList> getCommentsListBefore(@Path("id") int id, @Query("limit") int limit
            , @Query("order") String order, @Query("before") int before);
}