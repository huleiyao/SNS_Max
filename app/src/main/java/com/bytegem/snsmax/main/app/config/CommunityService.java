package com.bytegem.snsmax.main.app.config;

import com.bytegem.snsmax.common.bean.MBaseBean;
import com.bytegem.snsmax.main.app.bean.FileSignBean;
import com.bytegem.snsmax.main.app.bean.chat.ChatList;
import com.bytegem.snsmax.main.app.bean.chat.ChatListResp;
import com.bytegem.snsmax.main.app.bean.chat.ChatMessageSendResp;
import com.bytegem.snsmax.main.app.bean.feed.LISTFeedComments;
import com.bytegem.snsmax.main.app.bean.feed.DataFeed;
import com.bytegem.snsmax.main.app.bean.feed.LISTFeeds;
import com.bytegem.snsmax.main.app.bean.feed.DATAFeedComment;
import com.bytegem.snsmax.main.app.bean.NetDefaultBean;
import com.bytegem.snsmax.main.app.bean.messages.ChatRoomResp;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.bytegem.snsmax.main.app.Api.FILE_UPDATA_DOMAIN_NAME;
import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

public interface CommunityService {
    String getCommentsListAndSendComment = "/feeds/{id}/comments";
    String getCommentsCommentListAndSendCommentComment = "/comments/{id}/children";

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
    Observable<Void> updataImage(
            @Header("Authorization") String authorization
            , @Header("Host") String host
            , @Header("Content-MD5") String md5
            , @Header("x-cos-acl") String cos
            , @Header("Content-Type") String mimetype
            , @Body RequestBody file
            , @Path("path") String path
    );

//    //发送消息
//    @FormUrlEncoded
//    @Headers({"Content-Type:application/x-www-form-urlencoded", "Accept:application/json"})
//    @PUT("/user/chat-rooms/{roomid}/messages")
//    Observable<ChatMessageSendResp> sendMessage(
//            @Header("Authorization") String authorization,
//            @Path("roomid") String roomid,
//            @Field("contents") String contents);
    //发送消息
    @Headers({"Content-Type:application/x-www-form-urlencoded", "Accept:application/json"})
    @PUT("/user/chat-rooms/{roomid}/messages")
    Observable<ChatMessageSendResp> sendMessage(
            @Header("Authorization") String authorization,
            @Path("roomid") String roomid,
            @Body RequestBody contents);

    //获取房间聊天记录
    @Headers({"Content-Type:application/x-www-form-urlencoded", "Accept:application/json"})
    @GET("/user/chat-rooms/{roomid}/messages")
    Observable<ChatListResp> getMessageList(
            @Header("Authorization") String authorization,
            @Path("roomid") String roomid,
            //默认值 desc。可选值 asc/desc 表示消息的排序方式，desc 为倒序，asc 为正序
            @Query("order") String order);

    //获取聊天列表（房间列表）
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    @GET("/user/chat-rooms")
    Observable<ChatList> getUserChatList(@Header("Authorization") String authorization, @Query("page") int page);

    //发送消息。创建或者进入聊天室
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    @GET("/users/{userid}/chat-room")
    Observable<ChatRoomResp> getChatRoom(@Header("Authorization") String authorization, @Path("userid") int userId);

    //获取推荐动态列表
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    @GET("/recommend/temporary-feeds")
    Observable<LISTFeeds> getRecommendList(@Header("Authorization") String authorization, @Query("per_page") String per_page, @Query("page") String page);

    //获取动态列表
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    @GET("/nearby/feeds")
    Observable<LISTFeeds> getList(@Header("Authorization") String authorization, @Query("latitude") String latitude, @Query("longitude") String longitude, @Query("per_page") String per_page, @Query("page") String page);

    //取消/喜欢动态
    @PUT("/feeds/{feedid}/like")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<NetDefaultBean> changeLikeState(@Header("Authorization") String authorization, @Path("feedid") int id);

    //取消/喜欢动态
    @DELETE("/feeds/{feedid}/like")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<NetDefaultBean> changeDislikeState(@Header("Authorization") String authorization, @Path("feedid") int id);

    //取消/喜欢评论
    @DELETE("/comments/{commentid}/like")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<NetDefaultBean> changeCommentDislikeState(@Header("Authorization") String authorization, @Path("commentid") int id);

    //取消/喜欢评论
    @PUT("/comments/{commentid}/like")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<NetDefaultBean> changeCommentLikeState(@Header("Authorization") String authorization, @Path("commentid") int id);

    //获取热门评论
    @GET("/feeds/{feedid}/hot-comment")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<DATAFeedComment> getHotComment(@Header("Authorization") String authorization, @Path("feedid") int id);

    //获取动态详情
    @GET("/feeds/{id}")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<DataFeed> getFeedDetails(@Header("Authorization") String authorization, @Path("id") int id);

    //发布动态
    @POST("/feeds")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<NetDefaultBean> send(@Header("Authorization") String authorization, @Body RequestBody requestBody);

    //发布动态评论的评论
    @POST(getCommentsCommentListAndSendCommentComment)
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<NetDefaultBean> sendFeedCommentsComment(@Header("Authorization") String authorization, @Path("id") int id, @Body RequestBody requestBody);

    //第一次获取评论的评论列表
    @GET(getCommentsCommentListAndSendCommentComment)
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<LISTFeedComments> getCommentsCommentsListDefault(@Header("Authorization") String authorization, @Path("id") int id, @Query("limit") int limit, @Query("order") String order);

    //获取正序评论的评论列表
    @GET(getCommentsCommentListAndSendCommentComment)
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<LISTFeedComments> getCommentsCommentsListAfter(@Header("Authorization") String authorization, @Path("id") int id, @Query("limit") int limit
            , @Query("order") String order, @Query("after") int after);

    //获取倒序评论的评论列表
    @GET(getCommentsCommentListAndSendCommentComment)
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<LISTFeedComments> getCommentsCommentsListBefore(@Header("Authorization") String authorization, @Path("id") int id, @Query("limit") int limit
            , @Query("order") String order, @Query("before") int before);

    //发布动态评论
    @POST(getCommentsListAndSendComment)
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<NetDefaultBean> sendFeedComment(@Header("Authorization") String authorization, @Path("id") int id, @Body RequestBody requestBody);

    //第一次获取评论列表
    @GET(getCommentsListAndSendComment)
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<LISTFeedComments> getCommentsListDefault(@Header("Authorization") String authorization, @Path("id") int id, @Query("limit") int limit, @Query("order") String order);

    //获取正序评论列表
    @GET(getCommentsListAndSendComment)
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<LISTFeedComments> getCommentsListAfter(@Header("Authorization") String authorization, @Path("id") int id, @Query("limit") int limit
            , @Query("order") String order, @Query("after") int after);

    //获取倒序评论列表
    @GET(getCommentsListAndSendComment)
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<LISTFeedComments> getCommentsListBefore(@Header("Authorization") String authorization, @Path("id") int id, @Query("limit") int limit
            , @Query("order") String order, @Query("before") int before);
}