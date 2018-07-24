package com.involvd.sdk.networking.retrofit;

import com.involvd.sdk.data.models.BaseReport;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jj on 11/02/17.
 */

public interface ApiService {

    /** Places **/

    @GET("/bugs")
    Flowable<List<BaseReport>> getBugs(@Query("id") String appId, @Query("apiKey") String apiKey, @Query("sig") String sig,
                                                        @Query("status") String status, @Query("page") String previousBugId);

    @GET("/createBugReport")
    Flowable<BaseReport> createBugReport(@Query("id") String appId, @Query("apiKey") String apiKey, @Query("sig") String sig, @Body BaseReport baseReport);

    @GET("/createFeatureRequest")
    Flowable<BaseReport> createFeatureRequest(@Query("id") String appId, @Query("apiKey") String apiKey, @Query("sig") String sig, @Body BaseReport baseReport);

}
