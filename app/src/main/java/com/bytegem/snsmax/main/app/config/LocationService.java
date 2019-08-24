package com.bytegem.snsmax.main.app.config;

import com.bytegem.snsmax.main.app.bean.location.LISTTencentMapLocation;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
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