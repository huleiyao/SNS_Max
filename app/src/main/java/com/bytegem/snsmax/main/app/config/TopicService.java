package com.bytegem.snsmax.main.app.config;

import com.bytegem.snsmax.main.app.bean.feed.LISTFeedComments;
import com.bytegem.snsmax.main.app.bean.feed.LISTFeeds;
import com.bytegem.snsmax.main.app.bean.NetDefaultBean;
import com.bytegem.snsmax.main.app.bean.topic.DATATopic;
import com.bytegem.snsmax.main.app.bean.topic.LISTTopics;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TopicService {
    String Topic = "/topics";
    String getTopicFeeds = "/topics/{id}/feeds";

    //搜索话题
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    @GET(Topic)
    Observable<LISTTopics> getTopicList(@Header("Authorization") String authorization, @Query("keyword") String keyword);

    //创建一个话题
    @POST(Topic)
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<NetDefaultBean> creatTopic(@Header("Authorization") String authorization, @Body RequestBody requestBody);

    //话题详情
    @GET(Topic + "/{id}")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<DATATopic> getTopicDetail(@Header("Authorization") String authorization, @Path("id") int id);

    //在话题下发布一个动态
    @POST(getTopicFeeds)
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<NetDefaultBean> sendFeedInTopic(@Header("Authorization") String authorization, @Path("id") int id, @Body RequestBody requestBody);

    //第一次获取话题下的动态列表
    @GET(getTopicFeeds)
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<LISTFeeds> getTopicFeedsListDefault(@Header("Authorization") String authorization,@Path("id") int id, @Query("limit") int limit, @Query("order") String order);

    //获取正序话题下的动态列表
    @GET(getTopicFeeds)
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<LISTFeeds> getTopicFeedsListAfter(@Header("Authorization") String authorization,@Path("id") int id, @Query("limit") int limit
            , @Query("order") String order, @Query("after") int after);

    //获取倒序话题下的动态列表
    @GET(getTopicFeeds)
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Observable<LISTFeeds> getTopicFeedsistBefore(@Header("Authorization") String authorization,@Path("id") int id, @Query("limit") int limit
            , @Query("order") String order, @Query("before") int before);

}