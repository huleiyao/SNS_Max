package com.bytegem.snsmax.main.app.config;

import com.bytegem.snsmax.main.app.bean.NetDefaultBean;
import com.bytegem.snsmax.main.app.bean.feed.DATAFeedComment;
import com.bytegem.snsmax.main.app.bean.feed.DataFeed;
import com.bytegem.snsmax.main.app.bean.feed.LISTFeedComments;
import com.bytegem.snsmax.main.app.bean.feed.LISTFeeds;
import com.bytegem.snsmax.main.app.bean.location.LISTTencentMapLocation;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LocationService {
    String url = "https://apis.map.qq.com/ws/place/v1/search";

    @Headers({"Content-Type:application/json", "Accept:application/json"})
    @GET(url)
    Observable<LISTTencentMapLocation> searchLocation(
            @Query("boundary") String boundary
            , @Query("page_size") int count
            , @Query("page_index") int page
            , @Query("key") String key
            , @Query("keyword") String keyword
    );

    @Headers({"Content-Type:application/json", "Accept:application/json"})
    @GET(url)
    Observable<LISTTencentMapLocation> searchLocation(
            @Query("boundary") String boundary
            , @Query("page_size") int count
            , @Query("page_index") int page
            , @Query("key") String key
    );
}